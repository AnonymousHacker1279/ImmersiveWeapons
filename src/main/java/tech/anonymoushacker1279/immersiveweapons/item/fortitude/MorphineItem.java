package tech.anonymoushacker1279.immersiveweapons.item.fortitude;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;
import tech.anonymoushacker1279.immersiveweapons.init.EffectRegistry;
import tech.anonymoushacker1279.immersiveweapons.init.ItemRegistry;

import java.util.ArrayList;
import java.util.List;

public class MorphineItem extends AbstractFortitudeItem {

	/**
	 * Constructor for MorphineItem.
	 *
	 * @param properties the <code>Properties</code> for the item
	 */
	public MorphineItem(Properties properties) {
		super(properties, false);
	}

	@Override
	public int getCooldown() {
		return 2400;
	}

	@Override
	public @Nullable ItemStack getContainerItem() {
		return new ItemStack(ItemRegistry.MORPHINE.get());
	}

	@Override
	public List<MobEffectInstance> effects() {
		List<MobEffectInstance> effects = new ArrayList<>(2);

		effects.add(new MobEffectInstance(EffectRegistry.MORPHINE_EFFECT, 1800, 0, false, true));

		return effects;
	}
}