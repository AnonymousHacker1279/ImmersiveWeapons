package tech.anonymoushacker1279.immersiveweapons.item.pike;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.HumanoidModel.ArmPose;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;

import java.util.function.Consumer;

public class PikeItem extends TieredItem implements Vanishable {

	public final Ingredient repairIngredient;
	public final double damage;
	public final double attackSpeed;

	public PikeItem(Tier tier, Properties properties, double damageBonus, double attackSpeed, Ingredient repairIngredient) {
		super(tier, properties);

		this.repairIngredient = repairIngredient;
		damage = damageBonus + tier.getAttackDamageBonus();
		this.attackSpeed = attackSpeed;
	}

	@Override
	public boolean canAttackBlock(BlockState state, Level level, BlockPos pos, Player player) {
		return !player.isCreative();
	}

	/**
	 * Runs when an enemy is hurt.
	 *
	 * @param stack    the <code>ItemStack</code> instance
	 * @param target   the <code>LivingEntity</code> target
	 * @param attacker the <code>LivingEntity</code> attacker
	 * @return boolean
	 */
	@Override
	public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
		stack.hurtAndBreak(1, attacker, (entity) -> entity.broadcastBreakEvent(EquipmentSlot.MAINHAND));
		return true;
	}

	/**
	 * Runs when a block is mined.
	 *
	 * @param stack        the <code>ItemStack</code> instance
	 * @param level        the <code>Level</code> the block is in
	 * @param state        the <code>BlockState</code> of the block
	 * @param pos          the <code>BlockPos</code> the block is at
	 * @param livingEntity the <code>LivingEntity</code> destroying the block
	 * @return boolean
	 */
	@Override
	public boolean mineBlock(ItemStack stack, Level level, BlockState state, BlockPos pos, LivingEntity livingEntity) {
		if (state.getDestroySpeed(level, pos) != 0.0D) {
			stack.hurtAndBreak(2, livingEntity, (entity) -> entity.broadcastBreakEvent(EquipmentSlot.MAINHAND));
		}
		return true;
	}

	/**
	 * Check if the repair item is valid.
	 *
	 * @param toRepair the <code>ItemStack</code> to repair
	 * @param repair   the <code>ItemStack</code> to repair the first item
	 * @return boolean
	 */
	@Override
	public boolean isValidRepairItem(ItemStack toRepair, ItemStack repair) {
		return repairIngredient.test(repair) || super.isValidRepairItem(toRepair, repair);
	}

	@Override
	public UseAnim getUseAnimation(ItemStack pStack) {
		return UseAnim.CUSTOM;
	}

	@Override
	public void initializeClient(Consumer<IClientItemExtensions> consumer) {
		// Handle arm posing for holding the weapons
		consumer.accept(new IClientItemExtensions() {

			private static final ArmPose HOLD_PIKE = ArmPose.create("HOLD_PIKE", true, (model, entity, arm) -> {
				// Hold the pike with both hands, like a spear
				if (!entity.swinging) {
					if (arm == HumanoidArm.RIGHT) {
						model.rightArm.xRot = -0.35F;
						model.rightArm.yRot = -0.4F;

						model.leftArm.xRot = -0.85F;
						model.leftArm.yRot = 0.15F;
						model.leftArm.zRot = 0.75F;
						// Make the left arm slightly longer
						model.leftArm.yScale = 1.15F;
					} else {
						model.leftArm.xRot = -0.35F;
						model.leftArm.yRot = 0.4F;

						model.rightArm.xRot = -0.85F;
						model.rightArm.yRot = -0.15F;
						model.rightArm.zRot = -0.75F;
						// Make the right arm slightly longer
						model.rightArm.yScale = 1.15F;
					}
				} else {
					// As this is a spear-like item, it needs to stab in a thrusting motion
					float swingProgress = entity.getAttackAnim(model.attackTime);
					float armRotation = Mth.sin(swingProgress * (float) Math.PI);

					// Disable the arm swinging animation
					model.attackTime = 0.0F;

					// Animate the arms moving forward in a thrust motion
					if (arm == HumanoidArm.RIGHT) {
						// Interpolate from the idle position to the thrust position
						model.rightArm.xRot = -0.15F + armRotation * 0.75F;
						model.rightArm.yRot = -0.4F + armRotation * 0.175F;
					} else {
						model.leftArm.xRot = -0.15F + armRotation * 0.75F;
						model.leftArm.yRot = 0.4F - armRotation * 0.175F;
					}
				}
			});

			@Override
			public ArmPose getArmPose(LivingEntity entity, InteractionHand hand, ItemStack itemStack) {
				if (!itemStack.isEmpty()) {
					return HOLD_PIKE;
				}
				return ArmPose.EMPTY;
			}

			@Override
			public boolean applyForgeHandTransform(PoseStack poseStack, LocalPlayer player, HumanoidArm arm,
			                                       ItemStack itemInHand, float partialTick, float equipProcess, float swingProcess) {

				// Don't use custom transform until it is fully equipped
				if (equipProcess < 1.0f) {
					return false;
				}

				applyItemArmTransform(poseStack, player, arm, partialTick, swingProcess);
				return true;
			}

			private void applyItemArmTransform(PoseStack poseStack, LocalPlayer player, HumanoidArm arm, float partialTick, float swingProcess) {
				int i = arm == HumanoidArm.RIGHT ? 1 : -1;
				poseStack.translate(i * 0.56F, -0.52F, -0.72F);

				// Thrust the pike forwards if the player is attacking
				if (swingProcess > 0.0F) {
					// Make sure to interpolate the swing process
					float swingProgress = player.getAttackAnim(partialTick);
					swingProgress = Mth.sin(swingProgress * (float) Math.PI);

					// Thrust the pike forwards
					poseStack.translate(i * 0.125F * swingProgress, 0.0F, -1.125F * swingProgress);
				}
			}
		});
	}
}