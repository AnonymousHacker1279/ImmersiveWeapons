package tech.anonymoushacker1279.immersiveweapons.init;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.inventory.MenuType;
import net.neoforged.neoforge.common.extensions.IMenuTypeExtension;
import net.neoforged.neoforge.registries.DeferredRegister;
import tech.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;
import tech.anonymoushacker1279.immersiveweapons.menu.*;

import java.util.function.Supplier;

@SuppressWarnings({"unused"})
public class MenuTypeRegistry {

	// Menu Type Register
	public static final DeferredRegister<MenuType<?>> MENU_TYPES = DeferredRegister.create(Registries.MENU, ImmersiveWeapons.MOD_ID);

	// Menus
	public static final Supplier<MenuType<SmallPartsMenu>> SMALL_PARTS_TABLE_MENU = MENU_TYPES.register("small_parts_table", () -> IMenuTypeExtension.create((id, inv, data) -> new SmallPartsMenu(id, inv)));
	public static final Supplier<MenuType<TeslaSynthesizerMenu>> TESLA_SYNTHESIZER_MENU = MENU_TYPES.register("tesla_synthesizer", () -> IMenuTypeExtension.create((id, inv, data) -> new TeslaSynthesizerMenu(id, inv)));
	public static final Supplier<MenuType<AmmunitionTableMenu>> AMMUNITION_TABLE_MENU = MENU_TYPES.register("ammunition_table", () -> IMenuTypeExtension.create((id, inv, data) -> new AmmunitionTableMenu(id, inv)));
}