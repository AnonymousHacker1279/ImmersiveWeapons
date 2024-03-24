package tech.anonymoushacker1279.immersiveweapons.world.level.loot;

import com.google.common.base.Suppliers;
import com.google.gson.JsonParseException;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraftforge.common.loot.IGlobalLootModifier;
import net.minecraftforge.common.loot.LootModifier;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

public class UndeadMobDropModifierHandler extends LootModifier {

	public static final Supplier<Codec<UndeadMobDropModifierHandler>> CODEC = Suppliers.memoize(() ->
			RecordCodecBuilder.create(inst -> codecStart(inst).and(
							ItemStack.CODEC.fieldOf("item").forGetter(m -> m.itemStack))
					.apply(inst, UndeadMobDropModifierHandler::new)
			));

	private final ItemStack itemStack;

	public UndeadMobDropModifierHandler(LootItemCondition[] conditionsIn, ItemStack itemStack) {
		super(conditionsIn);
		this.itemStack = itemStack;

		if (!ForgeRegistries.ITEMS.containsValue(itemStack.getItem())) {
			throw new JsonParseException("item must exist in the registry");
		}
	}

	@Override
	protected @NotNull ObjectArrayList<ItemStack> doApply(ObjectArrayList<ItemStack> generatedLoot, LootContext context) {
		// Check if the entity is undead
		if (context.getParamOrNull(LootContextParams.THIS_ENTITY) instanceof Mob mob && mob.getMobType() == MobType.UNDEAD) {
			generatedLoot.add(itemStack);
		}

		return generatedLoot;
	}

	@Override
	public Codec<? extends IGlobalLootModifier> codec() {
		return CODEC.get();
	}
}