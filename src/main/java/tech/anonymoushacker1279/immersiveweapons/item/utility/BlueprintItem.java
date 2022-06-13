package tech.anonymoushacker1279.immersiveweapons.item.utility;

public class BlueprintItem extends BasicContainerItem {

	/**
	 * Constructor for BlueprintItem.
	 *
	 * @param properties the <code>Properties</code> for the item
	 */
	public BlueprintItem(Properties properties) {
		super(properties);
		properties.craftRemainder(asItem());
	}
}