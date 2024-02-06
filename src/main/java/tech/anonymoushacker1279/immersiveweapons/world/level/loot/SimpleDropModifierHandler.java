package tech.anonymoushacker1279.immersiveweapons.world.level.loot;

import com.google.common.base.Suppliers;
import com.google.gson.JsonParseException;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.neoforged.neoforge.common.loot.IGlobalLootModifier;
import net.neoforged.neoforge.common.loot.LootModifier;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.Supplier;

public class SimpleDropModifierHandler extends LootModifier {
	public static final Supplier<Codec<SimpleDropModifierHandler>> CODEC = Suppliers.memoize(() ->
			RecordCodecBuilder.create(inst -> codecStart(inst).and(
							inst.group(
									ItemStack.CODEC.fieldOf("item").forGetter(m -> m.itemStack),
									Codec.STRING.fieldOf("mob_type").forGetter(m -> m.mobType)
							))
					.apply(inst, SimpleDropModifierHandler::new)
			));

	private final ItemStack itemStack;
	private final String mobType;

	public SimpleDropModifierHandler(LootItemCondition[] itemConditions, ItemStack itemStack) {
		this(itemConditions, itemStack, "");
	}

	public SimpleDropModifierHandler(LootItemCondition[] itemConditions, ItemStack itemStack, String type) {
		super(itemConditions);
		this.itemStack = itemStack;
		this.mobType = type;

		if (!BuiltInRegistries.ITEM.containsValue(itemStack.getItem())) {
			throw new JsonParseException("item must exist in the registry");
		}
	}

	@Override
	protected @NotNull ObjectArrayList<ItemStack> doApply(ObjectArrayList<ItemStack> generatedLoot, LootContext context) {
		MobType type = stringToMobType(mobType);
		if (type != null) {
			if (context.getParamOrNull(LootContextParams.THIS_ENTITY) instanceof Mob mob && mob.getMobType() == type) {
				generatedLoot.add(itemStack);
			}
		} else {
			generatedLoot.add(itemStack);
		}

		return generatedLoot;
	}

	@Override
	public Codec<? extends IGlobalLootModifier> codec() {
		return CODEC.get();
	}

	@Nullable
	private static MobType stringToMobType(String mobType) {
		return switch (mobType) {
			case "undefined" -> MobType.UNDEFINED;
			case "undead" -> MobType.UNDEAD;
			case "arthropod" -> MobType.ARTHROPOD;
			case "illager" -> MobType.ILLAGER;
			case "water" -> MobType.WATER;
			default -> null;
		};
	}
}