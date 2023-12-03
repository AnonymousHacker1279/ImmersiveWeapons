package tech.anonymoushacker1279.immersiveweapons.init;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecoratorType;
import net.neoforged.neoforge.registries.DeferredRegister;
import tech.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;
import tech.anonymoushacker1279.immersiveweapons.world.level.levelgen.feature.treedecorators.BurnedBranchDecorator;

import java.util.function.Supplier;

@SuppressWarnings({"unused"})
public class TreeDecoratorRegistry {

	// Tree Decorator Type Register
	public static final DeferredRegister<TreeDecoratorType<?>> TREE_DECORATORS = DeferredRegister.create(Registries.TREE_DECORATOR_TYPE, ImmersiveWeapons.MOD_ID);

	// Tree Decorators
	public static final Supplier<TreeDecoratorType<BurnedBranchDecorator>> BURNED_BRANCH_DECORATOR = TREE_DECORATORS.register("burned_branch", () -> new TreeDecoratorType<>(BurnedBranchDecorator.CODEC));
}