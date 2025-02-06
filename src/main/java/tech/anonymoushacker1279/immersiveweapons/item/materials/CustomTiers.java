package tech.anonymoushacker1279.immersiveweapons.item.materials;

import com.google.common.base.Suppliers;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Block;
import tech.anonymoushacker1279.immersiveweapons.data.groups.immersiveweapons.IWBlockTagGroups;
import tech.anonymoushacker1279.immersiveweapons.init.ItemRegistry;

import java.util.function.Supplier;

public enum CustomTiers implements Tier {
	COPPER(IWBlockTagGroups.INCORRECT_FOR_COPPER_TOOL, 180, 5.9F, 1.0F, 12, () -> Ingredient.of(Items.COPPER_INGOT)),
	COBALT(IWBlockTagGroups.INCORRECT_FOR_COBALT_TOOL, 300, 6.2F, 2.0F, 15, () -> Ingredient.of(ItemRegistry.COBALT_INGOT.get())),
	MOLTEN(IWBlockTagGroups.INCORRECT_FOR_MOLTEN_TOOL, 1900, 10.2F, 5.0F, 17, () -> Ingredient.of(ItemRegistry.MOLTEN_INGOT.get())),
	TESLA(IWBlockTagGroups.INCORRECT_FOR_TESLA_TOOL, 2700, 20.0F, 7.0F, 20, () -> Ingredient.of(ItemRegistry.TESLA_INGOT.get())),
	VENTUS(IWBlockTagGroups.INCORRECT_FOR_VENTUS_TOOL, 1900, 12.6F, 5.0F, 16, () -> Ingredient.of(ItemRegistry.VENTUS_SHARD.get())),
	ASTRAL(IWBlockTagGroups.INCORRECT_FOR_ASTRAL_TOOL, 600, 24.0F, 4.0F, 22, () -> Ingredient.of(ItemRegistry.ASTRAL_INGOT.get())),
	STARSTORM(IWBlockTagGroups.INCORRECT_FOR_STARSTORM_TOOL, 1800, 14.0F, 7.0F, 20, () -> Ingredient.of(ItemRegistry.STARSTORM_INGOT.get())),
	VOID(IWBlockTagGroups.INCORRECT_FOR_VOID_TOOL, 2500, 28.0f, 9.0F, 24, () -> Ingredient.of(ItemRegistry.VOID_INGOT.get())),
	HANSIUM(IWBlockTagGroups.INCORRECT_FOR_HANSIUM_TOOL, 3000, 28.0F, 20.0F, 24, () -> Ingredient.of(ItemRegistry.HANSIUM_INGOT.get()));

	private final TagKey<Block> incorrectBlocksForDrops;
	private final int uses;
	private final float speed;
	private final float damage;
	private final int enchantmentValue;
	private final Supplier<Ingredient> repairIngredient;

	CustomTiers(TagKey<Block> incorrectBlocksTag, int uses, float speed, float damage, int enchantmentValue, Supplier<Ingredient> repairItem) {
		this.incorrectBlocksForDrops = incorrectBlocksTag;
		this.uses = uses;
		this.speed = speed;
		this.damage = damage;
		this.enchantmentValue = enchantmentValue;
		this.repairIngredient = Suppliers.memoize(repairItem::get);
	}

	@Override
	public int getUses() {
		return this.uses;
	}

	@Override
	public float getSpeed() {
		return this.speed;
	}

	@Override
	public float getAttackDamageBonus() {
		return this.damage;
	}

	@Override
	public TagKey<Block> getIncorrectBlocksForDrops() {
		return this.incorrectBlocksForDrops;
	}

	@Override
	public int getEnchantmentValue() {
		return this.enchantmentValue;
	}

	@Override
	public Ingredient getRepairIngredient() {
		return this.repairIngredient.get();
	}
}