package tech.anonymoushacker1279.immersiveweapons.init;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.crafting.Recipe;
import net.neoforged.neoforge.common.extensions.IMenuTypeExtension;
import net.neoforged.neoforge.registries.DeferredRegister;
import tech.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;
import tech.anonymoushacker1279.immersiveweapons.menu.*;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class MenuTypeRegistry {

	// Menu Type Register
	public static final DeferredRegister<MenuType<?>> MENU_TYPES = DeferredRegister.create(Registries.MENU, ImmersiveWeapons.MOD_ID);

	// Menus
	public static final Supplier<MenuType<SmallPartsMenu>> SMALL_PARTS_TABLE_MENU = MENU_TYPES.register("small_parts_table", () -> IMenuTypeExtension.create((id, inv, data) -> new SmallPartsMenu(id, inv)));
	public static final Supplier<MenuType<TeslaSynthesizerMenu>> TESLA_SYNTHESIZER_MENU = MENU_TYPES.register("tesla_synthesizer", () -> IMenuTypeExtension.create((id, inv, data) -> new TeslaSynthesizerMenu(id, inv)));
	public static final Supplier<MenuType<AmmunitionTableMenu>> AMMUNITION_TABLE_MENU = MENU_TYPES.register("ammunition_table", () -> IMenuTypeExtension.create((id, inv, data) -> new AmmunitionTableMenu(id, inv)));
	public static final Supplier<MenuType<StarForgeMenu>> STAR_FORGE_MENU = MENU_TYPES.register("star_forge", () -> IMenuTypeExtension.create((id, inv, data) -> {
		List<ResourceKey<Recipe<?>>> availableRecipes = new ArrayList<>(25);
		int recipes = data.readVarInt();

		for (int i = 0; i < recipes; i++) {
			availableRecipes.add(data.readResourceKey(Registries.RECIPE));
		}

		return new StarForgeMenu(id, inv, availableRecipes);
	}));
	public static final Supplier<MenuType<CelestialAltarMenu>> CELESTIAL_ALTAR_MENU = MENU_TYPES.register("celestial_altar", () -> IMenuTypeExtension.create((id, inv, data) -> new CelestialAltarMenu(id, inv, inv.player.registryAccess().lookupOrThrow(Registries.ENCHANTMENT))));
}