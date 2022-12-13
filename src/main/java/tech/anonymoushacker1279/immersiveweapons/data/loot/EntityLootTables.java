package tech.anonymoushacker1279.immersiveweapons.data.loot;

import com.google.common.collect.ImmutableSet;
import net.minecraft.advancements.critereon.EntityFlagsPredicate;
import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.data.loot.LootTableSubProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.storage.loot.*;
import net.minecraft.world.level.storage.loot.LootTable.Builder;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.*;
import net.minecraft.world.level.storage.loot.predicates.*;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.Nullable;
import tech.anonymoushacker1279.immersiveweapons.init.EntityRegistry;
import tech.anonymoushacker1279.immersiveweapons.init.ItemRegistry;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class EntityLootTables implements LootTableSubProvider {

	@Nullable
	private BiConsumer<ResourceLocation, LootTable.Builder> out;

	protected static final EntityPredicate.Builder ENTITY_ON_FIRE = EntityPredicate.Builder.entity().flags(EntityFlagsPredicate.Builder.flags().setOnFire(true).build());
	private static final Set<EntityType<?>> SPECIAL_LOOT_TABLE_TYPES = ImmutableSet.of(EntityType.PLAYER, EntityType.ARMOR_STAND, EntityType.IRON_GOLEM, EntityType.SNOW_GOLEM, EntityType.VILLAGER);

	@Override
	public void generate(BiConsumer<ResourceLocation, Builder> out) {
		this.out = out;

		add(EntityRegistry.ROCK_SPIDER_ENTITY.get(), LootTable.lootTable()
				.withPool(LootPool.lootPool()
						.setRolls(ConstantValue.exactly(1.0F))
						.add(LootItem.lootTableItem(Items.STRING)
								.apply(SetItemCountFunction.setCount(UniformGenerator.between(0.0F, 2.0F)))
								.apply(LootingEnchantFunction.lootingMultiplier(UniformGenerator.between(0.0F, 1.0F)))))
				.withPool(LootPool.lootPool()
						.setRolls(ConstantValue.exactly(1.0F))
						.add(LootItem.lootTableItem(Items.STONE)
								.apply(SetItemCountFunction.setCount(UniformGenerator.between(0.0F, 1.0F)))
								.apply(LootingEnchantFunction.lootingMultiplier(UniformGenerator.between(0.0F, 1.0F)))))
				.withPool(LootPool.lootPool()
						.setRolls(ConstantValue.exactly(1.0F))
						.add(LootItem.lootTableItem(Items.SPIDER_EYE)
								.apply(SetItemCountFunction.setCount(UniformGenerator.between(-1.0F, 1.0F)))
								.apply(LootingEnchantFunction.lootingMultiplier(UniformGenerator.between(0.0F, 1.0F))))
						.when(LootItemKilledByPlayerCondition.killedByPlayer())));

		add(EntityRegistry.DYING_SOLDIER_ENTITY.get(), LootTable.lootTable()
				.withPool(LootPool.lootPool()
						.setRolls(ConstantValue.exactly(1.0F))
						.add(LootItem.lootTableItem(Items.ROTTEN_FLESH)
								.apply(SetItemCountFunction.setCount(UniformGenerator.between(0.0F, 2.0F)))
								.apply(LootingEnchantFunction.lootingMultiplier(UniformGenerator.between(0.0F, 1.0F)))))
				.withPool(LootPool.lootPool()
						.setRolls(ConstantValue.exactly(1.0F))
						.add(LootItem.lootTableItem(ItemRegistry.IRON_MUSKET_BALL.get())
								.apply(SetItemCountFunction.setCount(UniformGenerator.between(0.0F, 4.0F)))
								.apply(LootingEnchantFunction.lootingMultiplier(UniformGenerator.between(0.0F, 1.0F))))
						.when(LootItemKilledByPlayerCondition.killedByPlayer()))
				.withPool(LootPool.lootPool()
						.setRolls(ConstantValue.exactly(1.0F))
						.add(LootItem.lootTableItem(ItemRegistry.CHOCOLATE_BAR.get())
								.apply(LootingEnchantFunction.lootingMultiplier(UniformGenerator.between(0.0F, 1.0F))))
						.add(LootItem.lootTableItem(Items.CARROT)
								.apply(LootingEnchantFunction.lootingMultiplier(UniformGenerator.between(0.0F, 1.0F))))
						.add(LootItem.lootTableItem(Items.POTATO)
								.apply(LootingEnchantFunction.lootingMultiplier(UniformGenerator.between(0.0F, 1.0F))))
						.apply(SmeltItemFunction.smelted()
								.when(LootItemEntityPropertyCondition.hasProperties(LootContext.EntityTarget.THIS, ENTITY_ON_FIRE)))
						.when(LootItemKilledByPlayerCondition.killedByPlayer())
						.when(LootItemRandomChanceWithLootingCondition.randomChanceAndLootingBoost(0.035F, 0.02F))));

		add(EntityRegistry.WANDERING_WARRIOR_ENTITY.get(), LootTable.lootTable()
				.withPool(LootPool.lootPool()
						.setRolls(ConstantValue.exactly(1.0F))
						.add(LootItem.lootTableItem(Items.LEATHER)
								.apply(SetItemCountFunction.setCount(UniformGenerator.between(0.0F, 3.0F)))
								.apply(LootingEnchantFunction.lootingMultiplier(UniformGenerator.between(0.0F, 2.0F))))
						.when(LootItemKilledByPlayerCondition.killedByPlayer())));

		add(EntityRegistry.HANS_ENTITY.get(), LootTable.lootTable()
				.withPool(LootPool.lootPool()
						.setRolls(ConstantValue.exactly(1.0F))
						.add(LootItem.lootTableItem(Items.LEATHER)
								.apply(SetItemCountFunction.setCount(UniformGenerator.between(0.0F, 3.0F)))
								.apply(LootingEnchantFunction.lootingMultiplier(UniformGenerator.between(0.0F, 2.0F))))
						.when(LootItemKilledByPlayerCondition.killedByPlayer()))
				.withPool(LootPool.lootPool()
						.setRolls(ConstantValue.exactly(1.0F))
						.add(LootItem.lootTableItem(Items.IRON_HELMET))
						.when(LootItemKilledByPlayerCondition.killedByPlayer())
						.when(LootItemRandomChanceWithLootingCondition.randomChanceAndLootingBoost(0.025F, 0.01F))));

		add(EntityRegistry.MINUTEMAN_ENTITY.get(), LootTable.lootTable()
				.withPool(LootPool.lootPool()
						.setRolls(ConstantValue.exactly(1.0F))
						.add(LootItem.lootTableItem(Items.GUNPOWDER)
								.apply(SetItemCountFunction.setCount(UniformGenerator.between(0.0F, 2.0F)))
								.apply(LootingEnchantFunction.lootingMultiplier(UniformGenerator.between(0.0F, 1.0F)))))
				.withPool(LootPool.lootPool()
						.setRolls(ConstantValue.exactly(1.0F))
						.add(LootItem.lootTableItem(ItemRegistry.COPPER_MUSKET_BALL.get())
								.apply(SetItemCountFunction.setCount(UniformGenerator.between(0.0F, 4.0F)))
								.apply(LootingEnchantFunction.lootingMultiplier(UniformGenerator.between(0.0F, 1.0F))))
						.when(LootItemKilledByPlayerCondition.killedByPlayer()))
				.withPool(LootPool.lootPool()
						.setRolls(ConstantValue.exactly(1.0F))
						.add(LootItem.lootTableItem(ItemRegistry.BLUNDERBUSS.get()))
						.when(LootItemKilledByPlayerCondition.killedByPlayer())
						.when(LootItemRandomChanceWithLootingCondition.randomChanceAndLootingBoost(0.017F, 0.05F)))
				.withPool(LootPool.lootPool()
						.setRolls(ConstantValue.exactly(1.0F))
						.add(LootItem.lootTableItem(ItemRegistry.CHOCOLATE_BAR.get())
								.apply(LootingEnchantFunction.lootingMultiplier(UniformGenerator.between(0.0F, 1.0F))))
						.add(LootItem.lootTableItem(Items.CARROT)
								.apply(LootingEnchantFunction.lootingMultiplier(UniformGenerator.between(0.0F, 1.0F))))
						.add(LootItem.lootTableItem(Items.POTATO)
								.apply(LootingEnchantFunction.lootingMultiplier(UniformGenerator.between(0.0F, 1.0F))))
						.apply(SmeltItemFunction.smelted()
								.when(LootItemEntityPropertyCondition.hasProperties(LootContext.EntityTarget.THIS, ENTITY_ON_FIRE)))
						.when(LootItemKilledByPlayerCondition.killedByPlayer())
						.when(LootItemRandomChanceWithLootingCondition.randomChanceAndLootingBoost(0.035F, 0.02F))));

		add(EntityRegistry.FIELD_MEDIC_ENTITY.get(), LootTable.lootTable()
				.withPool(LootPool.lootPool()
						.setRolls(ConstantValue.exactly(1.0F))
						.add(LootItem.lootTableItem(ItemRegistry.USED_SYRINGE.get()))
						.when(LootItemKilledByPlayerCondition.killedByPlayer())
						.when(LootItemRandomChanceWithLootingCondition.randomChanceAndLootingBoost(0.015F, 0.03F))));

		add(EntityRegistry.DYING_SOLDIER_ENTITY.get(), LootTable.lootTable()
				.withPool(LootPool.lootPool()
						.setRolls(ConstantValue.exactly(1.0F))
						.add(LootItem.lootTableItem(ItemRegistry.FLINTLOCK_PISTOL.get()))
						.when(LootItemKilledByPlayerCondition.killedByPlayer())
						.when(LootItemRandomChanceWithLootingCondition.randomChanceAndLootingBoost(0.020F, 0.05F))));

		add(EntityRegistry.CELESTIAL_TOWER_ENTITY.get(), LootTable.lootTable()
				.withPool(LootPool.lootPool()
						.setRolls(ConstantValue.exactly(1.0F))
						.add(LootItem.lootTableItem(ItemRegistry.CELESTIAL_FRAGMENT.get())
								.apply(SetItemCountFunction.setCount(UniformGenerator.between(0.0F, 3.0F)))
								.apply(LootingEnchantFunction.lootingMultiplier(UniformGenerator.between(0.0F, 2.0F))))
						.when(LootItemKilledByPlayerCondition.killedByPlayer())));
	}

	protected List<RegistryObject<EntityType<?>>> getKnownEntities() {
		return StreamSupport.stream(Spliterators.spliteratorUnknownSize(EntityRegistry.ENTITY_TYPES.getEntries().stream().iterator(), 0),
				false).collect(Collectors.toList());
	}

	protected boolean isNonLiving(EntityType<?> entityType) {
		return !SPECIAL_LOOT_TABLE_TYPES.contains(entityType) && entityType.getCategory() == MobCategory.MISC;
	}

	protected void add(EntityType<?> pEntityType, LootTable.Builder pLootTableBuilder) {
		add(pEntityType.getDefaultLootTable(), pLootTableBuilder);
	}

	protected void add(ResourceLocation pLootTableId, LootTable.Builder pLootTableBuilder) {
		if (out != null) {
			out.accept(pLootTableId, pLootTableBuilder);
		}
	}
}