package tech.anonymoushacker1279.iwcompatbridge.data.lang;

import net.minecraft.data.PackOutput;
import tech.anonymoushacker1279.iwcompatbridge.IWCompatBridge;

public class IWCBLanguageGenerator extends IWCBLanguageProvider {

	public IWCBLanguageGenerator(PackOutput output) {
		super(output, IWCompatBridge.MOD_ID);
	}

	@Override
	protected void addTranslations() {
		addJEICategories();
		addJEIItemInformation();
		addWTHITTooltips();
		addConfigDescriptions();

		add("curios.identifier.spirit", "Spirit");
	}

	private void addJEICategories() {
		addJEICategory("small_parts", "Small Parts Crafting");
		addJEICategory("tesla_synthesizer", "Tesla Synthesizing", "*16x speed");
		addJEICategory("astral_crystal", "Astral Crystal Sorcery");
		addJEICategory("barrel_tap_fermenting", "Barrel Fermenting");
		addJEICategory("piston_crushing", "Piston Crushing", "%s-%s drops");
		addJEICategory("ammunition_table", "Ammunition Crafting");
		addJEICategory("star_forge", "Star Forge Smelting");
	}

	private void addJEIItemInformation() {
		addJEIItemInfo("tesla_synthesizer", "The Tesla Synthesizer is used to create Electric Ingots, eliminating the need to search for Abandoned Factory structures to find them.");
		addJEIItemInfo("astral_crystal", "Astral Crystals can be used in a sorcery process with the right setup to create Astral Ingots.\nA 3x3 base of Lapis Lazuli and Redstone blocks must be formed, with the crystal placed in the center. The Lapis must form an X shape, with the Redstone filling the gaps.\nIngredients may be inserted by right-clicking the crystal, and a catalyst is dropped onto it, completing the process.\nThe crystal will be consumed in the process.");
		addJEIItemInfo("barrel_tap", "Barrels can be used to ferment certain items, such as wheat, into alcohol.\nPlace a sufficient amount of a fermentable item into a barrel, and right-click the barrel tap with an empty bottle to fill it.");
		addJEIItemInfo("piston_crushing", "Pistons can be used to crush certain blocks into other items.\nPlace a piston facing down onto a block and activate it. The yield of each crushing process may vary.");
		addJEIItemInfo("ammunition_table", "The Ammunition Table is used to craft musket balls. Place materials of the same type into the materials section, adjust the density slider as you wish, and collect your ammunition.\nHigher density values will consume more material, but provide some extra punch in your shots at the expense of higher drop over distance.");
		addJEIItemInfo("star_forge", "The Star Forge is a multiblock structure used for smelting endgame materials.\nTo build the structure, create a 3x3 floor of Star Forge Bricks. The next layer is a ring of bricks with a Star Forge Controller in the front, then another ring of bricks. Add Iron Bars on the corners, and another ring of bricks above. In the center of the topmost layer, place a Solar Lens.\nWhen the structure is complete, the controller will report that it is active.");
	}

	private void addWTHITTooltips() {
		addWTHITTooltip("damageable_block.health", "Health: %s/%s");
		addWTHITTooltip("damageable_block.stage", "Stage: %s/%s");
		addWTHITTooltip("merchant.trade_refresh_time", "Trades refresh in: %s:%s");
		addWTHITTooltip("tesla_synthesizer.progress", "Time remaining: %s:%s");
		addWTHITTooltip("star_forge.progress", "Time remaining: %ss");
	}

	private void addConfigDescriptions() {
		addConfigField("title", "Immersive Weapons Compatibility Bridge Configuration");
		addConfigField("Curios", "Curios Settings");
		addConfigField("accessoryStacking", "Accessory Stacking");
		addConfigField("RyoamicLights", "RyoamicLights Settings");
		addConfigField("flareLightLevel", "Light level of flares");
		addConfigField("fireflyLightLevel", "Light level of fireflies");
		addConfigField("meteorLightLevel", "Light level of meteors");
		addConfigField("starWolfLightLevel", "Light level of star wolves");
		addConfigField("muzzleFlashLightLevel", "Light level of muzzle flashes");
		addConfigField("mortarLightLevel", "Light level of mortars");
		addConfigField("throwableLightLevel", "Light level of throwable items");
	}
}