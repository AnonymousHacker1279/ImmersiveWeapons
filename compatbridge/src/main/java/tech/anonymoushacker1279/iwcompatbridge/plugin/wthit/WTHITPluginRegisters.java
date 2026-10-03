package tech.anonymoushacker1279.iwcompatbridge.plugin.wthit;

import mcp.mobius.waila.api.IClientRegistrar;
import mcp.mobius.waila.api.ICommonRegistrar;
import tech.anonymoushacker1279.immersiveweapons.block.PitfallBlock;
import tech.anonymoushacker1279.immersiveweapons.block.core.DamageableBlock;
import tech.anonymoushacker1279.immersiveweapons.block.crafting.StarForgeControllerBlock;
import tech.anonymoushacker1279.immersiveweapons.blockentity.DamageableBlockEntity;
import tech.anonymoushacker1279.immersiveweapons.entity.npc.AbstractMerchantEntity;
import tech.anonymoushacker1279.iwcompatbridge.plugin.wthit.components.DamageableBlockComponent;
import tech.anonymoushacker1279.iwcompatbridge.plugin.wthit.components.MerchantEntityComponent;
import tech.anonymoushacker1279.iwcompatbridge.plugin.wthit.components.StarForgeComponent;
import tech.anonymoushacker1279.iwcompatbridge.plugin.wthit.data_provider.DamageableBlockDataProvider;
import tech.anonymoushacker1279.iwcompatbridge.plugin.wthit.data_provider.StarForgeBlockDataProvider;
import tech.anonymoushacker1279.iwcompatbridge.plugin.wthit.overrides.PitfallBlockOverride;

public class WTHITPluginRegisters {

	protected static void registerCommonPlugin(ICommonRegistrar registrar) {
		registrar.blockData(new DamageableBlockDataProvider(), DamageableBlockEntity.class);
		registrar.blockData(new StarForgeBlockDataProvider(), StarForgeControllerBlock.class);
	}

	protected static void registerClientPlugin(IClientRegistrar registrar) {
		registrar.override(new PitfallBlockOverride(), PitfallBlock.class);

		registrar.body(new DamageableBlockComponent(), DamageableBlock.class);
		registrar.body(new MerchantEntityComponent(), AbstractMerchantEntity.class);
		registrar.body(new StarForgeComponent(), StarForgeControllerBlock.class);
	}
}