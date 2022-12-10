package tech.anonymoushacker1279.immersiveweapons.world.level.levelgen.surface;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.SurfaceRules;
import net.minecraft.world.level.levelgen.SurfaceRules.RuleSource;
import net.minecraft.world.level.levelgen.placement.CaveSurface;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.function.Supplier;

public class SurfaceRuleBuilder {

	public static final RuleSource[] RULE_SOURCES = new RuleSource[0];
	private static final Map<String, SurfaceRuleEntry> RULES_CACHE = Maps.newHashMap();
	private static final SurfaceRuleBuilder INSTANCE = new SurfaceRuleBuilder();
	private final List<SurfaceRuleEntry> rules = Lists.newArrayList();
	@Nullable
	private SurfaceRuleEntry entryInstance;
	@Nullable
	private ResourceKey<Biome> biomeKey;

	private SurfaceRuleBuilder() {
	}

	public static SurfaceRuleBuilder start() {
		INSTANCE.biomeKey = null;
		INSTANCE.rules.clear();
		return INSTANCE;
	}

	/**
	 * Restricts surface to only one biome.
	 *
	 * @param biomeKey {@link ResourceKey} for the {@link Biome}.
	 * @return same {@link SurfaceRuleBuilder} instance.
	 */
	public SurfaceRuleBuilder biome(ResourceKey<Biome> biomeKey) {
		this.biomeKey = biomeKey;
		return this;
	}

	/**
	 * Set biome surface with specified {@link BlockState}. Example - block of grass in the Overworld biomes
	 *
	 * @param state {@link BlockState} for the ground cover.
	 * @return same {@link SurfaceRuleBuilder} instance.
	 */
	public SurfaceRuleBuilder surface(BlockState state) {
		entryInstance = getFromCache("surface_" + state, () -> {
			RuleSource rule = SurfaceRules.state(state);
			rule = SurfaceRules.ifTrue(SurfaceRules.ON_FLOOR, rule);
			rule = SurfaceRules.ifTrue(SurfaceRules.waterBlockCheck(1, 0), rule);
			rule = SurfaceRules.ifTrue(SurfaceRules.abovePreliminarySurface(), rule);
			return new SurfaceRuleEntry(2, rule);
		});
		rules.add(entryInstance);
		return this;
	}

	/**
	 * Set biome subsurface with specified {@link BlockState}. Example - dirt in the Overworld biomes.
	 *
	 * @param state {@link BlockState} for the subterranean layer.
	 * @param depth block layer depth.
	 * @return same {@link SurfaceRuleBuilder} instance.
	 */
	public SurfaceRuleBuilder subsurface(BlockState state, int depth) {
		entryInstance = getFromCache("subsurface_" + depth + "_" + state, () -> {
			RuleSource rule = SurfaceRules.state(state);
			rule = SurfaceRules.ifTrue(SurfaceRules.stoneDepthCheck(depth, false, 0, CaveSurface.FLOOR), rule);
			rule = SurfaceRules.ifTrue(SurfaceRules.waterBlockCheck(1, 0), rule);
			rule = SurfaceRules.ifTrue(SurfaceRules.abovePreliminarySurface(), rule);
			return new SurfaceRuleEntry(3, rule);
		});
		rules.add(entryInstance);
		return this;
	}

	/**
	 * Set biome filler with specified {@link BlockState}. Example - stone in the Overworld biomes. The rule is added with priority 10.
	 *
	 * @param state {@link BlockState} for filling.
	 * @return same {@link SurfaceRuleBuilder} instance.
	 */
	public SurfaceRuleBuilder filler(BlockState state) {
		entryInstance = getFromCache("fill_" + state, () -> new SurfaceRuleEntry(10, SurfaceRules.state(state)));
		rules.add(entryInstance);
		return this;
	}

	/**
	 * Finalize rule building process.
	 *
	 * @return {@link SurfaceRules.RuleSource}.
	 */
	public SurfaceRules.RuleSource build() {
		Collections.sort(rules);
		List<RuleSource> ruleList = rules.stream().map(SurfaceRuleEntry::getRule).toList();
		SurfaceRules.RuleSource[] ruleArray = ruleList.toArray(RULE_SOURCES);
		SurfaceRules.RuleSource rule = SurfaceRules.sequence(ruleArray);
		if (biomeKey != null) {
			rule = SurfaceRules.ifTrue(SurfaceRules.isBiome(biomeKey), rule);
		}
		return rule;
	}

	/**
	 * Internal function, will take entry from cache or create it if necessary.
	 *
	 * @param name     {@link String} entry internal name.
	 * @param supplier {@link Supplier} for {@link SurfaceRuleEntry}.
	 * @return new or existing {@link SurfaceRuleEntry}.
	 */
	private static SurfaceRuleEntry getFromCache(String name, Supplier<SurfaceRuleEntry> supplier) {
		SurfaceRuleEntry entry = RULES_CACHE.get(name);
		if (entry == null) {
			entry = supplier.get();
			RULES_CACHE.put(name, entry);
		}
		return entry;
	}

	/**
	 * Allows adding a custom rule.
	 *
	 * @param priority rule priority, lower values = higher priority (rule will be applied before others).
	 * @param rule     custom {@link SurfaceRules.RuleSource}.
	 * @return same {@link SurfaceRuleBuilder} instance.
	 */
	public SurfaceRuleBuilder rule(int priority, SurfaceRules.RuleSource rule) {
		rules.add(new SurfaceRuleEntry(priority, rule));
		return this;
	}

	public static class SurfaceRuleEntry implements Comparable<SurfaceRuleEntry> {
		private final SurfaceRules.RuleSource rule;
		private final byte priority;

		public SurfaceRuleEntry(int priority, SurfaceRules.RuleSource rule) {
			this.priority = (byte) priority;
			this.rule = rule;
		}

		protected SurfaceRules.RuleSource getRule() {
			return rule;
		}

		@Override
		public int compareTo(SurfaceRuleEntry entry) {
			return Integer.compare(priority, entry.priority);
		}
	}
}