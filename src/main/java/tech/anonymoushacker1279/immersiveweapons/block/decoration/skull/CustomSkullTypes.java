package tech.anonymoushacker1279.immersiveweapons.block.decoration.skull;

import net.minecraft.world.level.block.SkullBlock;

public enum CustomSkullTypes implements SkullBlock.Type {
	MINUTEMAN("minuteman"),
	FIELD_MEDIC("field_medic"),
	DYING_SOLDIER("dying_soldier"),
	THE_COMMANDER("the_commander"),
	WANDERING_WARRIOR("wandering_warrior"),
	HANS("hans"),
	STORM_CREEPER("storm_creeper"),
	SKELETON_MERCHANT("skeleton_merchant");

	private final String name;

	CustomSkullTypes(String name) {
		this.name = name;
		TYPES.put(name, this);
	}

	@Override
	public String getSerializedName() {
		return name;
	}
}