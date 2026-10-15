package tech.anonymoushacker1279.immersiveweapons.entity.neutral;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Difficulty;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.RangedAttackMob;
import net.minecraft.world.entity.npc.InventoryCarrier;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import tech.anonymoushacker1279.immersiveweapons.entity.ai.goal.FleeWithoutAmmunitionGoal;
import tech.anonymoushacker1279.immersiveweapons.entity.ai.goal.PickUpAmmunitionGoal;
import tech.anonymoushacker1279.immersiveweapons.entity.ai.goal.RangedGunAttackGoal;
import tech.anonymoushacker1279.immersiveweapons.entity.ai.goal.ReloadFirearmGoal;
import tech.anonymoushacker1279.immersiveweapons.init.DataComponentTypeRegistry;
import tech.anonymoushacker1279.immersiveweapons.init.ItemRegistry;
import tech.anonymoushacker1279.immersiveweapons.item.gun.AbstractGunItem;
import tech.anonymoushacker1279.immersiveweapons.item.gun.AbstractGunItem.PowderType;
import tech.anonymoushacker1279.immersiveweapons.item.gun.FlareGunItem;
import tech.anonymoushacker1279.immersiveweapons.item.gun.HandCannonItem;
import tech.anonymoushacker1279.immersiveweapons.item.gun.LoadedAmmunition;
import tech.anonymoushacker1279.immersiveweapons.item.projectile.BulletItem;

import java.util.List;
import java.util.function.Predicate;

/// A soldier armed with a firearm. Like a player, it reloads the firearm (in or out of combat), holds it loaded until
/// it fires, and can misfire. Ammunition and powder come from a small supply carried by the soldier, which it tops up
/// by picking items up off the ground. A soldier with no supply left flees from its target.
public abstract class RangedSoldierEntity extends SoldierEntity implements RangedAttackMob, InventoryCarrier {

	/// How many shots worth of ammunition and powder a soldier starts with, and the amount it stops looking for more
	/// at. Ammunition is counted per shot, so a firearm that fires several balls at once gets proportionally more.
	public static final int STARTING_SHOTS = 8;

	/// How often a soldier regains ammunition and powder, and how many shots it gets back each time.
	private static final int SUPPLY_REGEN_INTERVAL = 60 * 20;
	private static final int SUPPLY_REGEN_SHOTS = 4;

	private final SimpleContainer supply = new SimpleContainer(6);
	private final MeleeAttackGoal meleeAttackGoal = new MeleeAttackGoal(this, 1.1D, false);
	private int ticksUntilSupplyRegen = SUPPLY_REGEN_INTERVAL;

	protected RangedSoldierEntity(EntityType<? extends PathfinderMob> entityType, Level level, List<Class<? extends Entity>> ignoresDamageFrom) {
		super(entityType, level, ignoresDamageFrom);
	}

	/// The ammunition for a full supply of shots: a shot uses as many as the firearm fires at once.
	private static int getStartingAmmunition(AbstractGunItem gun) {
		return STARTING_SHOTS * Math.max(1, gun.getMaxBulletsToFire());
	}

	@Override
	protected void registerGoals() {
		super.registerGoals();

		goalSelector.addGoal(0, new FleeWithoutAmmunitionGoal(this, 1.25D, 20.0F));
		goalSelector.addGoal(2, new ReloadFirearmGoal(this));
		goalSelector.addGoal(3, new PickUpAmmunitionGoal(this, 1.0D, 14));
	}

	@Override
	protected void populateDefaultEquipmentSlots(RandomSource randomSource, DifficultyInstance difficulty) {
		super.populateDefaultEquipmentSlots(randomSource, difficulty);
		setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(getDefaultGunItem()));
		fillStartingSupply();
	}

	public void prepareForCombat() {
		if (level() instanceof ServerLevel serverLevel) {
			goalSelector.removeGoal(getRangedGunAttackGoal());
			goalSelector.removeGoal(meleeAttackGoal);

			if (getItemBySlot(EquipmentSlot.MAINHAND).isEmpty()) {
				populateDefaultEquipmentSlots(random, serverLevel.getCurrentDifficultyAt(blockPosition()));
			}

			ItemStack itemInHand = getItemInHand(InteractionHand.MAIN_HAND);

			if (itemInHand.is(getGunItem())) {
				getRangedGunAttackGoal().setMinAttackInterval(getAttackInterval(level().getDifficulty()));
				goalSelector.addGoal(1, getRangedGunAttackGoal());
			} else {
				goalSelector.addGoal(1, meleeAttackGoal);
			}
		}
	}

	protected ItemStack getGun() {
		ItemStack mainHandItem = getItemBySlot(EquipmentSlot.MAINHAND);
		if (mainHandItem.getItem() instanceof AbstractGunItem) {
			return mainHandItem;
		} else {
			return new ItemStack(getDefaultGunItem());
		}
	}

	protected AbstractGunItem getGunItem() {
		return (AbstractGunItem) getGun().getItem();
	}

	/// The firearm in the main hand, or empty if there isn't one.
	public ItemStack getFirearm() {
		ItemStack mainHandItem = getItemBySlot(EquipmentSlot.MAINHAND);
		return mainHandItem.getItem() instanceof AbstractGunItem ? mainHandItem : ItemStack.EMPTY;
	}

	public boolean isFirearmLoaded() {
		return getFirearm().has(DataComponentTypeRegistry.LOADED_AMMUNITION.get());
	}

	/// Whether soldiers of this kind run out of ammunition. Bosses can override this to never run out.
	protected boolean usesSupply() {
		return true;
	}

	/// Whether there is enough ammunition and powder to reload the firearm.
	public boolean canReload() {
		ItemStack gun = getFirearm();
		if (!(gun.getItem() instanceof AbstractGunItem gunItem)) {
			return false;
		}

		return !usesSupply() || (!findSupply(gunItem::isAmmunition).isEmpty() && !findSupply(AbstractGunItem::isPowder).isEmpty());
	}

	/// Whether the soldier can still shoot: it is loaded, or can reload.
	public boolean canFireOrReload() {
		return isFirearmLoaded() || canReload();
	}

	/// Whether the soldier would like to carry more ammunition or powder.
	public boolean wantsMoreSupply() {
		if (!usesSupply() || !(getFirearm().getItem() instanceof AbstractGunItem gunItem)) {
			return false;
		}

		return countSupply(gunItem::isAmmunition) < getStartingAmmunition(gunItem) || countSupply(AbstractGunItem::isPowder) < STARTING_SHOTS;
	}

	private ItemStack findSupply(Predicate<ItemStack> predicate) {
		for (int i = 0; i < supply.getContainerSize(); i++) {
			ItemStack stack = supply.getItem(i);
			if (!stack.isEmpty() && predicate.test(stack)) {
				return stack;
			}
		}

		return ItemStack.EMPTY;
	}

	private int countSupply(Predicate<ItemStack> predicate) {
		int count = 0;
		for (int i = 0; i < supply.getContainerSize(); i++) {
			ItemStack stack = supply.getItem(i);
			if (predicate.test(stack)) {
				count += stack.getCount();
			}
		}

		return count;
	}

	/// Load the firearm from the supply. Called when a reload completes.
	public void finishReload() {
		ItemStack gun = getFirearm();
		if (!(gun.getItem() instanceof AbstractGunItem gunItem)) {
			return;
		}

		if (usesSupply()) {
			ItemStack ammo = findSupply(gunItem::isAmmunition);
			ItemStack powder = findSupply(AbstractGunItem::isPowder);

			if (!ammo.isEmpty() && !powder.isEmpty()) {
				gunItem.loadFromSupply(gun, ammo, powder, true);
				supply.setChanged();
			}
		} else {
			gunItem.loadFromSupply(gun, new ItemStack(getSupplyAmmunition(gunItem)), getPowderType().powder().copy(), false);
		}
	}

	/// The ammunition to supply to a soldier for the given firearm.
	protected Item getSupplyAmmunition(AbstractGunItem gun) {
		if (gun instanceof FlareGunItem) {
			return ItemRegistry.FLARE.get();
		} else if (gun instanceof HandCannonItem) {
			return ItemRegistry.CANNONBALL.get();
		}

		return getDefaultBulletItem();
	}

	private void fillStartingSupply() {
		if (!supply.isEmpty() || !usesSupply()) {
			return;
		}

		supply.addItem(new ItemStack(getSupplyAmmunition(getGunItem()), getStartingAmmunition(getGunItem())));
		supply.addItem(getPowderType().powder().copyWithCount(STARTING_SHOTS));
	}

	@Override
	public void aiStep() {
		super.aiStep();

		if (level() instanceof ServerLevel && usesSupply()) {
			ticksUntilSupplyRegen--;
			if (ticksUntilSupplyRegen <= 0) {
				ticksUntilSupplyRegen = SUPPLY_REGEN_INTERVAL;
				regenerateSupply();
			}
		}
	}

	/// Restore a few shots' worth of ammunition and powder, without going past a full supply.
	private void regenerateSupply() {
		if (!(getFirearm().getItem() instanceof AbstractGunItem gunItem)) {
			return;
		}

		int ammoMissing = getStartingAmmunition(gunItem) - countSupply(gunItem::isAmmunition);
		int ammoToAdd = Math.min(ammoMissing, SUPPLY_REGEN_SHOTS * Math.max(1, gunItem.getMaxBulletsToFire()));
		if (ammoToAdd > 0) {
			ItemStack ammo = new ItemStack(getSupplyAmmunition(gunItem), ammoToAdd);
			if (supply.canAddItem(ammo)) {
				supply.addItem(ammo);
			}
		}

		int powderToAdd = Math.min(STARTING_SHOTS - countSupply(AbstractGunItem::isPowder), SUPPLY_REGEN_SHOTS);
		if (powderToAdd > 0) {
			ItemStack powder = getPowderType().powder().copyWithCount(powderToAdd);
			if (supply.canAddItem(powder)) {
				supply.addItem(powder);
			}
		}
	}

	@Override
	public SimpleContainer getInventory() {
		return supply;
	}

	@Override
	public boolean wantsToPickUp(ServerLevel level, ItemStack stack) {
		if (isSupplyItem(stack)) {
			return usesSupply() && supply.canAddItem(stack);
		}

		return super.wantsToPickUp(level, stack);
	}

	@Override
	protected void pickUpItem(ServerLevel level, ItemEntity entity) {
		if (isSupplyItem(entity.getItem())) {
			InventoryCarrier.pickUpItem(level, this, this, entity);
		} else {
			super.pickUpItem(level, entity);
		}
	}

	/// Whether an item is ammunition or powder for the firearm this soldier holds.
	public boolean isSupplyItem(ItemStack stack) {
		return getFirearm().getItem() instanceof AbstractGunItem gunItem && (gunItem.isAmmunition(stack) || AbstractGunItem.isPowder(stack));
	}

	@Override
	public void addAdditionalSaveData(ValueOutput output) {
		super.addAdditionalSaveData(output);
		writeInventoryToTag(output);
	}

	@Override
	public void readAdditionalSaveData(ValueInput input) {
		super.readAdditionalSaveData(input);
		readInventoryFromTag(input);

		// Soldiers saved before they carried a supply start with one
		if (input.list(TAG_INVENTORY, ItemStack.CODEC).isEmpty()) {
			fillStartingSupply();
		}
	}

	@Override
	public boolean canUseNonMeleeWeapon(ItemStack stack) {
		return stack.is(getGunItem());
	}

	/// Fire the loaded firearm at the target. Does nothing if it isn't loaded.
	@Override
	public void performRangedAttack(LivingEntity target, float velocity) {
		ItemStack gun = getFirearm();
		LoadedAmmunition loaded = gun.get(DataComponentTypeRegistry.LOADED_AMMUNITION.get());
		if (loaded == null || !(gun.getItem() instanceof AbstractGunItem gunItem)) {
			return;
		}

		lookAt(target, 30.0F, 30.0F);
		gun.remove(DataComponentTypeRegistry.LOADED_AMMUNITION.get());

		if (level() instanceof ServerLevel serverLevel) {
			gunItem.fireLoaded(serverLevel, this, InteractionHand.MAIN_HAND, gun, loaded);
		}
	}

	/// How long to wait between a shot and the next, on top of the reload.
	protected int getAttackInterval(Difficulty difficulty) {
		return getAttackIntervalModifier(difficulty);
	}

	protected abstract int getAttackIntervalModifier(Difficulty difficulty);

	protected abstract AbstractGunItem getDefaultGunItem();

	protected abstract BulletItem<?> getDefaultBulletItem();

	protected PowderType getPowderType() {
		return new PowderType(ItemRegistry.BLACKPOWDER.get().getDefaultInstance());
	}

	protected abstract RangedGunAttackGoal<? extends RangedSoldierEntity> getRangedGunAttackGoal();
}