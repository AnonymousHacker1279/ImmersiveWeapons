package tech.anonymoushacker1279.immersiveweapons.init;

import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.registries.*;
import tech.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;
import tech.anonymoushacker1279.immersiveweapons.menu.*;

@SuppressWarnings({"unused"})
public class MenuTypeRegistry {

	// Menu Type Register
	public static final DeferredRegister<MenuType<?>> MENU_TYPES = DeferredRegister.create(ForgeRegistries.MENU_TYPES, ImmersiveWeapons.MOD_ID);

	// Menus
	public static final RegistryObject<MenuType<SmallPartsMenu>> SMALL_PARTS_TABLE_MENU = MENU_TYPES.register("small_parts_table", () -> IForgeMenuType.create((id, inv, data) -> new SmallPartsMenu(id, inv)));
	public static final RegistryObject<MenuType<TeslaSynthesizerMenu>> TESLA_SYNTHESIZER_MENU = MENU_TYPES.register("tesla_synthesizer", () -> IForgeMenuType.create((id, inv, data) -> new TeslaSynthesizerMenu(id, inv)));
	public static final RegistryObject<MenuType<AmmunitionTableMenu>> AMMUNITION_TABLE_MENU = MENU_TYPES.register("ammunition_table", () -> IForgeMenuType.create((id, inv, data) -> new AmmunitionTableMenu(id, inv)));
}