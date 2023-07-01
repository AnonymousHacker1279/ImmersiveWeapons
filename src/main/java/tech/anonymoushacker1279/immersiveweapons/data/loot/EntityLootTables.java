package tech.anonymoushacker1279.immersiveweapons.data.loot;

import net.minecraft.ChatFormatting;
import net.minecraft.advancements.critereon.EntityFlagsPredicate;
import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.data.loot.LootTableSubProvider;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.storage.loot.*;
import net.minecraft.world.level.storage.loot.LootTable.Builder;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.entries.TagEntry;
import net.minecraft.world.level.storage.loot.functions.*;
import net.minecraft.world.level.storage.loot.predicates.*;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import org.jetbrains.annotations.Nullable;
import tech.anonymoushacker1279.immersiveweapons.init.EntityRegistry;
import tech.anonymoushacker1279.immersiveweapons.init.ItemRegistry;

import java.util.function.BiConsumer;

public class EntityLootTables implements LootTableSubProvider {

	@Nullable
	private BiConsumer<ResourceLocation, LootTable.Builder> out;

	protected static final EntityPredicate.Builder ENTITY_ON_FIRE = EntityPredicate.Builder.entity().flags(EntityFlagsPredicate.Builder.flags().setOnFire(true).build());

	@Override
	public void generate(BiConsumer<ResourceLocation, Builder> out) {
		this.out = out;

		add(EntityRegistry.ROCK_SPIDER_ENTITY.get(), LootTable.lootTable()
				.withPool(LootPool.lootPool()
						.name("string")
						.setRolls(ConstantValue.exactly(1.0F))
						.add(LootItem.lootTableItem(Items.STRING)
								.apply(SetItemCountFunction.setCount(UniformGenerator.between(0.0F, 2.0F)))
								.apply(LootingEnchantFunction.lootingMultiplier(UniformGenerator.between(0.0F, 1.0F)))))
				.withPool(LootPool.lootPool()
						.name("stone")
						.setRolls(ConstantValue.exactly(1.0F))
						.add(LootItem.lootTableItem(Items.STONE)
								.apply(SetItemCountFunction.setCount(UniformGenerator.between(0.0F, 1.0F)))
								.apply(LootingEnchantFunction.lootingMultiplier(UniformGenerator.between(0.0F, 1.0F)))))
				.withPool(LootPool.lootPool()
						.name("spider_eye")
						.setRolls(ConstantValue.exactly(1.0F))
						.add(LootItem.lootTableItem(Items.SPIDER_EYE)
								.apply(SetItemCountFunction.setCount(UniformGenerator.between(-1.0F, 1.0F)))
								.apply(LootingEnchantFunction.lootingMultiplier(UniformGenerator.between(0.0F, 1.0F))))
						.when(LootItemKilledByPlayerCondition.killedByPlayer())));

		add(EntityRegistry.DYING_SOLDIER_ENTITY.get(), LootTable.lootTable()
				.withPool(LootPool.lootPool()
						.name("rotten_flesh")
						.setRolls(ConstantValue.exactly(1.0F))
						.add(LootItem.lootTableItem(Items.ROTTEN_FLESH)
								.apply(SetItemCountFunction.setCount(UniformGenerator.between(0.0F, 2.0F)))
								.apply(LootingEnchantFunction.lootingMultiplier(UniformGenerator.between(0.0F, 1.0F)))))
				.withPool(LootPool.lootPool()
						.name("iron_musket_ball")
						.setRolls(ConstantValue.exactly(1.0F))
						.add(LootItem.lootTableItem(ItemRegistry.IRON_MUSKET_BALL.get())
								.apply(SetItemCountFunction.setCount(UniformGenerator.between(0.0F, 4.0F)))
								.apply(LootingEnchantFunction.lootingMultiplier(UniformGenerator.between(0.0F, 1.0F))))
						.when(LootItemKilledByPlayerCondition.killedByPlayer()))
				.withPool(LootPool.lootPool()
						.name("flintlock_pistol")
						.setRolls(ConstantValue.exactly(1.0F))
						.add(LootItem.lootTableItem(ItemRegistry.FLINTLOCK_PISTOL.get()))
						.when(LootItemKilledByPlayerCondition.killedByPlayer())
						.when(LootItemRandomChanceWithLootingCondition.randomChanceAndLootingBoost(0.020F, 0.05F)))
				.withPool(LootPool.lootPool()
						.name("foods")
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
						.name("leather")
						.setRolls(ConstantValue.exactly(1.0F))
						.add(LootItem.lootTableItem(Items.LEATHER)
								.apply(SetItemCountFunction.setCount(UniformGenerator.between(0.0F, 3.0F)))
								.apply(LootingEnchantFunction.lootingMultiplier(UniformGenerator.between(0.0F, 2.0F))))
						.when(LootItemKilledByPlayerCondition.killedByPlayer())));

		add(EntityRegistry.HANS_ENTITY.get(), LootTable.lootTable()
				.withPool(LootPool.lootPool()
						.name("leather")
						.setRolls(ConstantValue.exactly(1.0F))
						.add(LootItem.lootTableItem(Items.LEATHER)
								.apply(SetItemCountFunction.setCount(UniformGenerator.between(0.0F, 3.0F)))
								.apply(LootingEnchantFunction.lootingMultiplier(UniformGenerator.between(0.0F, 2.0F))))
						.when(LootItemKilledByPlayerCondition.killedByPlayer()))
				.withPool(LootPool.lootPool()
						.name("iron_helmet")
						.setRolls(ConstantValue.exactly(1.0F))
						.add(LootItem.lootTableItem(Items.IRON_SWORD)
								.apply(SetNameFunction.setName(Component.translatable("loot.immersiveweapons.entity.hans.iron_sword")
										.withStyle(ChatFormatting.DARK_PURPLE))))
						.when(LootItemKilledByPlayerCondition.killedByPlayer())
						.when(LootItemRandomChanceWithLootingCondition.randomChanceAndLootingBoost(0.025F, 0.01F)))
				.withPool(LootPool.lootPool()
						.name("hans_blessing")
						.setRolls(ConstantValue.exactly(1.0F))
						.add(LootItem.lootTableItem(ItemRegistry.HANS_BLESSING.get()))
						.when(LootItemKilledByPlayerCondition.killedByPlayer())
						.when(LootItemRandomChanceWithLootingCondition.randomChanceAndLootingBoost(0.05F, 0.02F))));


		add(EntityRegistry.MINUTEMAN_ENTITY.get(), LootTable.lootTable()
				.withPool(LootPool.lootPool()
						.name("gunpowder")
						.setRolls(ConstantValue.exactly(1.0F))
						.add(LootItem.lootTableItem(Items.GUNPOWDER)
								.apply(SetItemCountFunction.setCount(UniformGenerator.between(0.0F, 2.0F)))
								.apply(LootingEnchantFunction.lootingMultiplier(UniformGenerator.between(0.0F, 1.0F)))))
				.withPool(LootPool.lootPool()
						.name("copper_musket_ball")
						.setRolls(ConstantValue.exactly(1.0F))
						.add(LootItem.lootTableItem(ItemRegistry.COPPER_MUSKET_BALL.get())
								.apply(SetItemCountFunction.setCount(UniformGenerator.between(0.0F, 4.0F)))
								.apply(LootingEnchantFunction.lootingMultiplier(UniformGenerator.between(0.0F, 1.0F))))
						.when(LootItemKilledByPlayerCondition.killedByPlayer()))
				.withPool(LootPool.lootPool()
						.name("blunderbuss")
						.setRolls(ConstantValue.exactly(1.0F))
						.add(LootItem.lootTableItem(ItemRegistry.BLUNDERBUSS.get()))
						.when(LootItemKilledByPlayerCondition.killedByPlayer())
						.when(LootItemRandomChanceWithLootingCondition.randomChanceAndLootingBoost(0.017F, 0.05F)))
				.withPool(LootPool.lootPool()
						.name("foods")
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
						.when(LootItemRandomChanceWithLootingCondition.randomChanceAndLootingBoost(0.045F, 0.02F))));

		add(EntityRegistry.FIELD_MEDIC_ENTITY.get(), LootTable.lootTable()
				.withPool(LootPool.lootPool()
						.name("used_syringe")
						.setRolls(ConstantValue.exactly(1.0F))
						.add(LootItem.lootTableItem(ItemRegistry.USED_SYRINGE.get()))
						.when(LootItemKilledByPlayerCondition.killedByPlayer())
						.when(LootItemRandomChanceWithLootingCondition.randomChanceAndLootingBoost(0.015F, 0.03F))));

		add(EntityRegistry.CELESTIAL_TOWER_ENTITY.get(), LootTable.lootTable()
				.withPool(LootPool.lootPool()
						.name("celestial_fragment")
						.setRolls(ConstantValue.exactly(1.0F))
						.add(LootItem.lootTableItem(ItemRegistry.CELESTIAL_FRAGMENT.get())
								.apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 3.0F)))
								.apply(LootingEnchantFunction.lootingMultiplier(UniformGenerator.between(0.0F, 2.0F))))
						.when(LootItemKilledByPlayerCondition.killedByPlayer()))
				.withPool(LootPool.lootPool()
						.name("celestial_spirit")
						.setRolls(ConstantValue.exactly(1.0F))
						.add(LootItem.lootTableItem(ItemRegistry.CELESTIAL_SPIRIT.get()))
						.when(LootItemKilledByPlayerCondition.killedByPlayer())
						.when(LootItemRandomChanceWithLootingCondition.randomChanceAndLootingBoost(0.05F, 0.02F))));

		add(EntityRegistry.STORM_CREEPER_ENTITY.get(), LootTable.lootTable()
				.withPool(LootPool.lootPool()
						.name("main")
						.setRolls(ConstantValue.exactly(1.0F))
						.add(LootItem.lootTableItem(Items.GUNPOWDER)
								.apply(SetItemCountFunction.setCount(UniformGenerator.between(0.0F, 2.0F)))
								.apply(LootingEnchantFunction.lootingMultiplier(UniformGenerator.between(0.0F, 1.0F))))
						.add(LootItem.lootTableItem(ItemRegistry.SULFUR.get())
								.apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 4.0F)))
								.apply(LootingEnchantFunction.lootingMultiplier(UniformGenerator.between(0.0F, 2.0F)))))
				.withPool(LootPool.lootPool()
						.name("music_discs")
						.add(TagEntry.expandTag(ItemTags.CREEPER_DROP_MUSIC_DISCS))
						.when(LootItemEntityPropertyCondition.hasProperties(LootContext.EntityTarget.KILLER,
								EntityPredicate.Builder.entity().of(EntityTypeTags.SKELETONS)))));

		add(EntityRegistry.EVIL_EYE_ENTITY.get(), LootTable.lootTable()
				.withPool(LootPool.lootPool()
						.name("main")
						.setRolls(ConstantValue.exactly(1.0F))
						.add(LootItem.lootTableItem(ItemRegistry.BROKEN_LENS.get())
								.apply(SetItemCountFunction.setCount(UniformGenerator.between(0.0F, 1.0F)))
								.apply(LootingEnchantFunction.lootingMultiplier(UniformGenerator.between(0.0F, 1.0F))))
						.add(LootItem.lootTableItem(Items.ENDER_EYE)
								.apply(SetItemCountFunction.setCount(UniformGenerator.between(0.0F, 1.0F)))
								.apply(LootingEnchantFunction.lootingMultiplier(UniformGenerator.between(0.0F, 1.0F))))
						.add(LootItem.lootTableItem(Items.ENDER_PEARL)
								.apply(SetItemCountFunction.setCount(UniformGenerator.between(0.0F, 1.0F)))
								.apply(LootingEnchantFunction.lootingMultiplier(UniformGenerator.between(0.0F, 1.0F)))))
		);
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