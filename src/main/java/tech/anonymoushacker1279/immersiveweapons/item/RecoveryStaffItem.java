package tech.anonymoushacker1279.immersiveweapons.item;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.EventBusSubscriber.Bus;
import tech.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;
import tech.anonymoushacker1279.immersiveweapons.config.CommonConfig;
import tech.anonymoushacker1279.immersiveweapons.init.ItemRegistry;

@EventBusSubscriber(modid = ImmersiveWeapons.MOD_ID, bus = Bus.GAME)
public class RecoveryStaffItem extends Item implements SummoningStaff {

	private float healAmount = 4.0f;

	public RecoveryStaffItem(Properties properties) {
		super(properties);
	}

	@Override
	public InteractionResultHolder<ItemStack> use(Level level, Player player,
	                                              InteractionHand hand) {

		if (hand != InteractionHand.MAIN_HAND) {
			return InteractionResultHolder.pass(player.getItemInHand(hand));
		}

		Vec3 eyePos = player.getEyePosition();
		Vec3 lookVec = player.getViewVector(1.0F);
		Vec3 normalized = lookVec.normalize();

		// Check if there is an entity in the path of the recovery beam
		EntityHitResult hitResult = ProjectileUtil.getEntityHitResult(level, player,
				eyePos,
				eyePos.add(lookVec.scale(getMaxRange())),
				player.getBoundingBox()
						.expandTowards(lookVec.scale(getMaxRange()))
						.inflate(1.0D),
				(entity) -> !entity.isSpectator() && entity.isPickable() && player.hasLineOfSight(entity));

		if (hitResult != null && hitResult.getEntity() instanceof LivingEntity nearestEntity) {
			double particleDistance = eyePos.distanceTo(nearestEntity.position());

			if (level instanceof ServerLevel serverLevel) {
				for (int i = 1; i < particleDistance; ++i) {
					Vec3 particlePos = eyePos.add(normalized.scale(i));
					serverLevel.sendParticles(ParticleTypes.SPORE_BLOSSOM_AIR,
							particlePos.x, particlePos.y, particlePos.z,
							3, 0.0, 0.0, 0.0, 0.0);
				}
			}

			nearestEntity.heal(healAmount);
		} else {
			player.heal(healAmount);
		}

		if (!level.isClientSide) {
			healAmount = 4;
		}

		player.playSound(SoundEvents.AMETHYST_BLOCK_CHIME, 3.0F, 1.0F);

		handleCooldown(this, player, hand);

		return InteractionResultHolder.sidedSuccess(player.getItemInHand(hand), level.isClientSide());
	}

	@Override
	public int getStaffCooldown() {
		return 150;
	}

	@Override
	public int getMaxRange() {
		return CommonConfig.recoveryStaffMaxUseRange;
	}

	@Override
	public boolean isValidRepairItem(ItemStack toRepair, ItemStack repair) {
		return Ingredient.of(ItemRegistry.HANSIUM_INGOT.get()).test(repair) || super.isValidRepairItem(toRepair, repair);
	}

	@Override
	public int getEnchantmentValue(ItemStack stack) {
		return 1;
	}

	public void setHealAmount(float healAmount) {
		this.healAmount = healAmount;
	}

	public float getHealAmount() {
		return healAmount;
	}

	// TODO: reimplement
	/*@SubscribeEvent
	public static void livingHurtEvent(LivingHurtEvent event) {
		if (event.getEntity() instanceof Player player) {
			player.getInventory().items.stream()
					.filter(stack -> stack.getItem() instanceof RecoveryStaffItem)
					.findFirst().ifPresent(stack -> {
						RecoveryStaffItem staff = (RecoveryStaffItem) stack.getItem();
						staff.setHealAmount((event.getAmount() / 2) + 4);
					});
		}
	}*/
}