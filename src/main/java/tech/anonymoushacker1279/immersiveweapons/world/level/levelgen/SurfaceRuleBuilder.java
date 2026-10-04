package tech.anonymoushacker1279.immersiveweapons.world.level.levelgen;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import net.minecraft.core.HolderGetter;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.material.MaterialRules;
import net.minecraft.world.level.levelgen.material.rule.MaterialRule;
import net.minecraft.world.level.levelgen.placement.CaveSurface;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

public class SurfaceRuleBuilder {

	private static final Map<String, SurfaceRuleEntry> RULES_CACHE = Maps.newHashMap();
	private static final SurfaceRuleBuilder INSTANCE = new SurfaceRuleBuilder();
	private final List<SurfaceRuleEntry> rules = Lists.newArrayList();
	@Nullable
	private SurfaceRuleEntry entryInstance;
	@Nullable
	private ResourceKey<Biome> biomeKey;
	private HolderGetter<Biome> biomeGetter;

	private SurfaceRuleBuilder() {
	}

	public static SurfaceRuleBuilder start(HolderGetter<Biome> biomeGetter) {
		INSTANCE.biomeKey = null;
		INSTANCE.rules.clear();
		INSTANCE.biomeGetter = biomeGetter;
		return INSTANCE;
	}

	/// Internal function, will take entry from cache or create it if necessary.
	///
	/// @param name     [String] entry internal name.
	/// @param supplier [Supplier] for [SurfaceRuleEntry].
	/// @return new or existing [SurfaceRuleEntry].
	private static SurfaceRuleEntry getFromCache(String name, Supplier<SurfaceRuleEntry> supplier) {
		SurfaceRuleEntry entry = RULES_CACHE.get(name);
		if (entry == null) {
			entry = supplier.get();
			RULES_CACHE.put(name, entry);
		}
		return entry;
	}

	/// Restricts surface to only one biome.
	///
	/// @param biomeKey [ResourceKey] for the [Biome].
	/// @return same [SurfaceRuleBuilder] instance.
	public SurfaceRuleBuilder biome(ResourceKey<Biome> biomeKey) {
		this.biomeKey = biomeKey;
		return this;
	}

	/// Set biome surface with specified [BlockState]. Example - block of grass in the Overworld biomes
	///
	/// @param state [BlockState] for the ground cover.
	/// @return same [SurfaceRuleBuilder] instance.
	public SurfaceRuleBuilder surface(BlockState state) {
		entryInstance = getFromCache("surface_" + state, () -> {
			MaterialRule rule = MaterialRules.state(state);
			rule = MaterialRules.ifTrue(MaterialRules.stoneDepthCheck(0, false, CaveSurface.FLOOR), rule);
			rule = MaterialRules.ifTrue(MaterialRules.waterBlockCheck(1, 0), rule);
			rule = MaterialRules.ifTrue(MaterialRules.abovePreliminarySurface(), rule);
			return new SurfaceRuleEntry(2, rule);
		});
		rules.add(entryInstance);
		return this;
	}

	/// Set biome subsurface with specified [BlockState]. Example - dirt in the Overworld biomes.
	///
	/// @param state [BlockState] for the subterranean layer.
	/// @param depth block layer depth.
	/// @return same [SurfaceRuleBuilder] instance.
	public SurfaceRuleBuilder subsurface(BlockState state, int depth) {
		entryInstance = getFromCache("subsurface_" + depth + "_" + state, () -> {
			MaterialRule rule = MaterialRules.state(state);
			rule = MaterialRules.ifTrue(MaterialRules.stoneDepthCheck(depth, false, 0, CaveSurface.FLOOR), rule);
			rule = MaterialRules.ifTrue(MaterialRules.waterBlockCheck(1, 0), rule);
			rule = MaterialRules.ifTrue(MaterialRules.abovePreliminarySurface(), rule);
			return new SurfaceRuleEntry(3, rule);
		});
		rules.add(entryInstance);
		return this;
	}

	/// Set biome filler with specified [BlockState]. Example - stone in the Overworld biomes. The rule is added with
	/// priority 10.
	///
	/// @param state [BlockState] for filling.
	/// @return same [SurfaceRuleBuilder] instance.
	public SurfaceRuleBuilder filler(BlockState state) {
		entryInstance = getFromCache("fill_" + state, () -> new SurfaceRuleEntry(10, MaterialRules.state(state)));
		rules.add(entryInstance);
		return this;
	}

	/// Finalize rule building process.
	///
	/// @return [MaterialRule].
	public MaterialRule build() {
		Collections.sort(rules);
		List<MaterialRule> ruleList = rules.stream().map(SurfaceRuleEntry::rule).toList();
		MaterialRule rule = MaterialRules.sequence(ruleList);
		if (biomeKey != null) {
			rule = MaterialRules.ifTrue(MaterialRules.isBiome(biomeGetter, biomeKey), rule);
		}
		return rule;
	}

	/// Allows adding a custom rule.
	///
	/// @param priority rule priority, lower values = higher priority (rule will be applied before others).
	/// @param rule     custom [MaterialRule].
	/// @return same [SurfaceRuleBuilder] instance.
	public SurfaceRuleBuilder rule(int priority, MaterialRule rule) {
		rules.add(new SurfaceRuleEntry(priority, rule));
		return this;
	}

	public record SurfaceRuleEntry(int priority, MaterialRule rule) implements Comparable<SurfaceRuleEntry> {

		@Override
		public int compareTo(SurfaceRuleEntry entry) {
			return Integer.compare(priority, entry.priority);
		}
	}
}
