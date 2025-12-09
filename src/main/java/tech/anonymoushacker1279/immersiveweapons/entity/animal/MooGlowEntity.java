package tech.anonymoushacker1279.immersiveweapons.entity.animal;

import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.animal.AbstractCow;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.component.SuspiciousStewEffects;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.gameevent.GameEvent;
import net.neoforged.neoforge.common.IShearable;
import net.neoforged.neoforge.event.EventHooks;
import org.jetbrains.annotations.Nullable;
import tech.anonymoushacker1279.immersiveweapons.entity.GrantAdvancementOnDiscovery;
import tech.anonymoushacker1279.immersiveweapons.init.*;

import java.util.List;

public class MooGlowEntity extends AbstractCow implements IShearable, GrantAdvancementOnDiscovery {

	private final SuspiciousStewEffects stewEffects = new SuspiciousStewEffects(List.of(new SuspiciousStewEffects.Entry(MobEffects.GLOWING, 10)));

	public MooGlowEntity(EntityType<? extends AbstractCow> type, Level level) {
		super(type, level);
	}

	@Override
	public InteractionResult mobInteract(Player player, InteractionHand hand) {
		ItemStack itemInHand = player.getItemInHand(hand);
		if (itemInHand.is(Items.BOWL) && !isBaby()) {
			ItemStack stew = new ItemStack(Items.SUSPICIOUS_STEW);
			stew.set(DataComponents.SUSPICIOUS_STEW_EFFECTS, stewEffects);

			ItemStack filledStew = ItemUtils.createFilledResult(itemInHand, player, stew, false);
			player.setItemInHand(hand, filledStew);

			this.playSound(SoundEvents.MOOSHROOM_MILK_SUSPICIOUSLY, 1.0F, 1.0F);
			return InteractionResult.SUCCESS;
		} else if (itemInHand.is(Items.SHEARS) && isShearable(player, itemInHand, level(), blockPosition())) {
			if (level() instanceof ServerLevel serverlevel) {
				onSheared(player, itemInHand, serverlevel, blockPosition());
				gameEvent(GameEvent.SHEAR, player);
				itemInHand.hurtAndBreak(1, player, hand.asEquipmentSlot());
			}

			return InteractionResult.SUCCESS;
		} else {
			return super.mobInteract(player, hand);
		}
	}

	@Override
	public List<ItemStack> onSheared(@Nullable Player player, ItemStack item, Level level, BlockPos pos) {
		level.playSound(null, this, SoundEvents.MOOSHROOM_SHEAR, SoundSource.AMBIENT, 1.0F, 1.0F);
		if (!EventHooks.canLivingConvert(this, EntityType.COW, (timer) -> {
		})) {
			return List.of();
		}

		convertTo(EntityType.COW, ConversionParams.single(this, false, false), cow -> {
			EventHooks.onLivingConvert(this, cow);
		});

		for (int i = 0; i < 12; i++) {
			level.addParticle(ParticleTypesRegistry.MOONGLOW_PARTICLE.get(), getX(), getY() + 0.5D, getZ(),
					random.nextGaussian() * 0.02D, random.nextGaussian() * 0.02D, random.nextGaussian() * 0.02D);
		}

		if (level instanceof ServerLevel serverLevel) {
			int flowerCount = 2 + random.nextInt(3);
			for (int i = 0; i < flowerCount; i++) {
				spawnAtLocation(serverLevel, BlockItemRegistry.MOONGLOW_ITEM.get());
			}
		}

		return List.of();
	}

	@Override
	public boolean isShearable(@Nullable Player player, ItemStack item, Level level, BlockPos pos) {
		return isAlive() && !isBaby();
	}

	@Nullable
	@Override
	public AgeableMob getBreedOffspring(ServerLevel level, AgeableMob otherParent) {
		return EntityRegistry.MOOGLOW_ENTITY.get().create(level, EntitySpawnReason.BREEDING);
	}

	@Override
	public void tick() {
		super.tick();

		if (tickCount % 8 == 0) {
			level().addParticle(ParticleTypesRegistry.MOONGLOW_PARTICLE.get(),
					getX() + (0.3D * random.nextGaussian()),
					getY() + getEyeHeight() + (0.15D * random.nextGaussian()),
					getZ() + (0.3D * random.nextGaussian()),
					(0.03D * random.nextGaussian()),
					(0.01D * random.nextGaussian()),
					(0.03D * random.nextGaussian()));
		}
	}

	@Override
	public void aiStep() {
		super.aiStep();
		checkForDiscovery(this);
	}

	public static boolean checkSpawnRules(EntityType<? extends Animal> entityType, LevelAccessor level, EntitySpawnReason spawnReason, BlockPos pos, RandomSource random) {
		boolean brightEnough = EntitySpawnReason.ignoresLightRequirements(spawnReason) || level.getRawBrightness(pos, 0) > 4;
		return level.getBlockState(pos.below()).is(BlockTags.ANIMALS_SPAWNABLE_ON) && brightEnough;
	}
}