package tech.anonymoushacker1279.immersiveweapons.world.level.loot;

import com.google.common.base.Suppliers;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.core.BlockPos;
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
import net.minecraftforge.common.loot.IGlobalLootModifier;
import net.minecraftforge.common.loot.LootModifier;
import org.jetbrains.annotations.NotNull;
import tech.anonymoushacker1279.immersiveweapons.init.ItemRegistry;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class MoltenToolSmeltingModifierHandler extends LootModifier {

	public static final Supplier<Codec<MoltenToolSmeltingModifierHandler>> CODEC = Suppliers.memoize(() ->
			RecordCodecBuilder.create(inst -> codecStart(inst).apply(inst, MoltenToolSmeltingModifierHandler::new))
	);

	private final List<Item> MOLTEN_TOOLS = new ArrayList<>(5);

	MoltenToolSmeltingModifierHandler(LootItemCondition[] conditions) {
		super(conditions);

		MOLTEN_TOOLS.add(ItemRegistry.MOLTEN_SWORD.get());
		MOLTEN_TOOLS.add(ItemRegistry.MOLTEN_PICKAXE.get());
		MOLTEN_TOOLS.add(ItemRegistry.MOLTEN_AXE.get());
		MOLTEN_TOOLS.add(ItemRegistry.MOLTEN_SHOVEL.get());
		MOLTEN_TOOLS.add(ItemRegistry.MOLTEN_HOE.get());
	}

	@Override
	public @NotNull ObjectArrayList<ItemStack> doApply(ObjectArrayList<ItemStack> generatedLoot, LootContext context) {
		// If a molten tool is used and the player is crouching, the block is smelted
		if (context.getParamOrNull(LootContextParams.THIS_ENTITY) instanceof Player player) {
			if (player.isCrouching() && MOLTEN_TOOLS.contains(player.getItemInHand(InteractionHand.MAIN_HAND).getItem())) {
				// Query smelting recipes to see if the block can be smelted
				RecipeManager manager = player.level.getRecipeManager();

				// If the block can be smelted, smelt it
				BlockState state = context.getParamOrNull(LootContextParams.BLOCK_STATE);
				if (state != null) {
					ItemStack blockItemStack = state.getBlock().asItem().getDefaultInstance();
					if (manager.getRecipeFor(RecipeType.SMELTING, new SimpleContainer(blockItemStack), player.level).isPresent()) {
						// Get the smelted item
						ItemStack smeltedItem = manager.getRecipeFor(RecipeType.SMELTING, new SimpleContainer(blockItemStack), player.level)
								.get().assemble(new SimpleContainer(blockItemStack));

						// Drop the smelted item
						Vec3 origin = context.getParamOrNull(LootContextParams.ORIGIN);
						if (origin != null) {
							BlockPos dropPos = new BlockPos(origin);
							Block.popResource(player.level, dropPos, smeltedItem);

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