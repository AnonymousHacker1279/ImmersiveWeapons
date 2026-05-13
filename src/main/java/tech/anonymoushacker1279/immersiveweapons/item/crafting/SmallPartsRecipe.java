package tech.anonymoushacker1279.immersiveweapons.item.crafting;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;
import tech.anonymoushacker1279.immersiveweapons.init.RecipeSerializerRegistry;
import tech.anonymoushacker1279.immersiveweapons.init.RecipeTypeRegistry;

import java.util.List;

public class SmallPartsRecipe implements Recipe<RecipeInput> {

	public static final MapCodec<SmallPartsRecipe> CODEC = RecordCodecBuilder.mapCodec(
			instance -> instance.group(
					Codec.STRING.optionalFieldOf("group", "").forGetter(SmallPartsRecipe::group),
					Ingredient.CODEC.fieldOf("material").forGetter(SmallPartsRecipe::input),
					Codec.list(ItemStackTemplate.CODEC).fieldOf("craftables").forGetter(SmallPartsRecipe::craftables)
			).apply(instance, SmallPartsRecipe::new)
	);
	public static final StreamCodec<RegistryFriendlyByteBuf, SmallPartsRecipe> STREAM_CODEC = StreamCodec.composite(
			ByteBufCodecs.STRING_UTF8,
			SmallPartsRecipe::group,
			Ingredient.CONTENTS_STREAM_CODEC,
			SmallPartsRecipe::input,
			ItemStackTemplate.STREAM_CODEC.apply(ByteBufCodecs.list()),
			SmallPartsRecipe::craftables,
			SmallPartsRecipe::new
	);
	public static final RecipeSerializer<SmallPartsRecipe> SERIALIZER = new RecipeSerializer<>(CODEC, STREAM_CODEC);

	private final Ingredient input;
	private final List<ItemStackTemplate> craftables;
	private final String group;
	@Nullable
	private PlacementInfo placementInfo;

	public SmallPartsRecipe(String group, Ingredient material, List<ItemStackTemplate> craftables) {
		this.group = group;
		this.input = material;
		this.craftables = craftables;
	}

	public String group() {
		return group;
	}

	public Ingredient input() {
		return input;
	}

	public List<ItemStackTemplate> craftables() {
		return craftables;
	}

	@Override
	public RecipeSerializer<SmallPartsRecipe> getSerializer() {
		return RecipeSerializerRegistry.SMALL_PARTS_RECIPE_SERIALIZER.get();
	}

	@Override
	public RecipeType<SmallPartsRecipe> getType() {
		return RecipeTypeRegistry.SMALL_PARTS_RECIPE_TYPE.get();
	}

	@Override
	public RecipeBookCategory recipeBookCategory() {
		return RecipeBookCategories.CRAFTING_MISC;
	}

	@Override
	public boolean matches(RecipeInput input, Level level) {
		return this.input.test(input.getItem(0));
	}

	@Override
	public ItemStack assemble(RecipeInput input) {
		return ItemStack.EMPTY;
	}

	@Override
	public boolean isSpecial() {
		return true;
	}

	@Override
	public boolean showNotification() {
		return false;
	}

	@Override
	public PlacementInfo placementInfo() {
		if (placementInfo == null) {
			placementInfo = PlacementInfo.create(input);
		}

		return placementInfo;
	}
}