package tech.anonymoushacker1279.immersiveweapons.init;

import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecoratorType;
import net.neoforged.neoforge.registries.*;
import tech.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;
import tech.anonymoushacker1279.immersiveweapons.world.level.levelgen.feature.treedecorators.BurnedBranchDecorator;

@SuppressWarnings({"unused"})
public class TreeDecoratorRegistry {

	// Tree Decorator Type Register
	public static final DeferredRegister<TreeDecoratorType<?>> TREE_DECORATORS = DeferredRegister.create(ForgeRegistries.TREE_DECORATOR_TYPES, ImmersiveWeapons.MOD_ID);

	// Tree Decorators
	public static final RegistryObject<TreeDecoratorType<BurnedBranchDecorator>> BURNED_BRANCH_DECORATOR = TREE_DECORATORS.register("burned_branch", () -> new TreeDecoratorType<>(BurnedBranchDecorator.CODEC));
}