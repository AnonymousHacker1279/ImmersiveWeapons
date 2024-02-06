package tech.anonymoushacker1279.immersiveweapons.world.level.loot;

import com.google.common.base.Suppliers;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.common.loot.IGlobalLootModifier;
import net.neoforged.neoforge.common.loot.LootModifier;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

public class ToolSmeltingModifierHandler extends LootModifier {

	public static final Supplier<Codec<ToolSmeltingModifierHandler>> CODEC = Suppliers.memoize(() ->
			RecordCodecBuilder.create(inst -> codecStart(inst).and(
							TagKey.codec(Registries.ITEM).fieldOf("toolsTag").forGetter(m -> m.tools))
					.apply(inst, ToolSmeltingModifierHandler::new))
	);

	private final TagKey<Item> tools;

	public ToolSmeltingModifierHandler(LootItemCondition[] conditions, TagKey<Item> tools) {
		super(conditions);
		this.tools = tools;
	}

	@Override
	public @NotNull ObjectArrayList<ItemStack> doApply(ObjectArrayList<ItemStack> generatedLoot, LootContext context) {
		// If a molten tool is used and the player is crouching, the block is smelted
		if (context.getParamOrNull(LootContextParams.THIS_ENTITY) instanceof Player player) {
			if (player.isCrouching() && player.getItemInHand(InteractionHand.MAIN_HAND).is(tools)) {
				// Query smelting recipes to see if the block can be smelted
				RecipeManager manager = player.level().getRecipeManager();

				// If the block can be smelted, smelt it
				BlockState state = context.getParamOrNull(LootContextParams.BLOCK_STATE);
				if (state != null) {
					ItemStack blockItemStack = state.getBlock().asItem().getDefaultInstance();
					if (manager.getRecipeFor(RecipeType.SMELTING, new SimpleContainer(blockItemStack), player.level()).isPresent()) {
						// Get the smelted item
						ItemStack smeltedItem = manager.getRecipeFor(RecipeType.SMELTING, new SimpleContainer(blockItemStack), player.level())
								.get().value().assemble(new SimpleContainer(blockItemStack), player.level().registryAccess());

						// Drop the smelted item
						Vec3 origin = context.getParamOrNull(LootContextParams.ORIGIN);
						if (origin != null) {
							BlockPos dropPos = BlockPos.containing(origin.x, origin.y, origin.z);
							Block.popResource(player.level(), dropPos, smeltedItem);

							// The smelted drop is removed from the loot table
							generatedLoot.clear();
						}
					}
				}
			}
		}
		return generatedLoot;
	}

	@Override
	public Codec<? extends IGlobalLootModifier> codec() {
		return CODEC.get();
	}
}