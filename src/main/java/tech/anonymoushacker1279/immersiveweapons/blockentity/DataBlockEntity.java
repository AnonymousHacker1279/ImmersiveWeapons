package tech.anonymoushacker1279.immersiveweapons.blockentity;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.Clearable;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import tech.anonymoushacker1279.immersiveweapons.init.BlockEntityRegistry;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;

public class DataBlockEntity extends BlockEntity implements EntityBlock, Clearable {

	private final Map<String, Object> data = new HashMap<>(5);

	/**
	 * Constructor for DataBlockEntity. It will store basic data in NBT.
	 */
	public DataBlockEntity(BlockPos blockPos, BlockState blockState) {
		super(BlockEntityRegistry.DATA_BLOCK_ENTITY.get(), blockPos, blockState);
	}

	/**
	 * Add a value to the data map.
	 *
	 * @param key   the key to store the value under
	 * @param value the value to store
	 * @return true if the value was added, false if the key already exists
	 */
	public boolean addValue(String key, Object value) {
		if (data.containsKey(key)) {
			return false;
		}
		data.put(key, value);
		return true;
	}

	/**
	 * Remove a value from the data map.
	 *
	 * @param key the key to remove the value from
	 * @return true if the value was removed, false if the key does not exist
	 */
	public boolean removeValue(String key) {
		if (!data.containsKey(key)) {
			return false;
		}
		data.remove(key);
		return true;
	}

	/**
	 * Edit a value in the data map. If the key does not exist, it will be added.
	 */
	public void editValue(String key, Object value) {
		if (data.containsKey(key)) {
			data.replace(key, value);
		} else {
			addValue(key, value);
		}
	}

	/**
	 * Get a value from the data map.
	 *
	 * @param key the key to get the value from
	 * @return the value stored under the key
	 */
	public Object getValue(String key) {
		return data.get(key);
	}

	/**
	 * Get the data map.
	 *
	 * @return Map containing stored data
	 */
	public Map<String, Object> getData() {
		return data;
	}

	/**
	 * Load NBT data.
	 *
	 * @param nbt the <code>CompoundNBT</code> to load
	 */
	@Override
	public void load(CompoundTag nbt) {
		super.load(nbt);
		data.clear();
		for (String key : nbt.getAllKeys()) {
			if (nbt.contains(key, 8)) {
				data.put(key, nbt.getString(key));
			} else if (nbt.contains(key, 3)) {
				data.put(key, nbt.getInt(key));
			} else if (nbt.contains(key, 6)) {
				data.put(key, nbt.getFloat(key));
			} else if (nbt.contains(key, 5)) {
				data.put(key, nbt.getDouble(key));
			} else if (nbt.contains(key, 1)) {
				data.put(key, nbt.getBoolean(key));
			}
		}
	}

	/**
	 * Save NBT data.
	 *
	 * @param tag the <code>CompoundNBT</code> to save
	 */
	@Override
	protected void saveAdditional(CompoundTag tag) {
		super.saveAdditional(tag);
		for (Map.Entry<String, Object> entry : data.entrySet()) {
			if (entry.getValue() instanceof String value) {
				tag.putString(entry.getKey(), value);
			} else if (entry.getValue() instanceof Integer value) {
				tag.putInt(entry.getKey(), value);
			} else if (entry.getValue() instanceof Float value) {
				tag.putFloat(entry.getKey(), value);
			} else if (entry.getValue() instanceof Double value) {
				tag.putDouble(entry.getKey(), value);
			} else if (entry.getValue() instanceof Boolean value) {
				tag.putBoolean(entry.getKey(), value);
			}
		}
	}

	/**
	 * Get the entity update packet.
	 *
	 * @return ClientboundBlockEntityDataPacket
	 */
	@Override
	public ClientboundBlockEntityDataPacket getUpdatePacket() {
		return ClientboundBlockEntityDataPacket.create(this);
	}

	/**
	 * Get the update tag.
	 *
	 * @return CompoundTag
	 */
	@Override
	public CompoundTag getUpdateTag() {
		CompoundTag tag = new CompoundTag();
		super.saveAdditional(tag);
		return tag;
	}

	/**
	 * Clear the data map.
	 */
	@Override
	public void clearContent() {
		data.clear();
	}

	@Nullable
	@Override
	public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
		return new DataBlockEntity(pos, state);
	}
}