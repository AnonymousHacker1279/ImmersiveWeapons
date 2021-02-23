package com.anonymoushacker1279.immersiveweapons.init;

import java.util.List;

import javax.annotation.Nonnull;

import com.anonymoushacker1279.immersiveweapons.util.GeneralUtilities;
import com.google.gson.JsonObject;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootContext;
import net.minecraft.loot.conditions.ILootCondition;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.loot.GlobalLootModifierSerializer;
import net.minecraftforge.common.loot.LootModifier;
import net.minecraftforge.registries.ForgeRegistries;

public class LootTableHandler {

	public static class LogShardsLootModifierHandler extends LootModifier {
		
	    private final int numShardsToConvert;
	    private final Item itemReward;
	    public LogShardsLootModifierHandler(ILootCondition[] conditionsIn, int numShards, Item reward) {
	        super(conditionsIn);
	        numShardsToConvert = numShards;
	        itemReward = reward;
	    }

	    @Nonnull
	    @Override
	    public List<ItemStack> doApply(List<ItemStack> generatedLoot, LootContext context) {
	        //
	        // Additional conditions can be checked, though as much as possible should be parameterized via JSON data.
	        // It is better to write a new ILootCondition implementation than to do things here.
	        //
	        int numShards = 0;
	        for(ItemStack stack : generatedLoot) {
	            if(stack.getItem().isIn(ItemTags.makeWrapperTag("minecraft:logs"))) {
	            	numShards+=stack.getCount() + GeneralUtilities.getRandomNumber(2, 5);
	            }
	        }
	        if(numShards >= numShardsToConvert) {
	            generatedLoot.add(new ItemStack(itemReward, (numShards/numShardsToConvert)));
	            numShards = numShards%numShardsToConvert;
	            if(numShards > 0)
	                generatedLoot.add(new ItemStack(itemReward, numShards));
	            	generatedLoot.remove(0); // The log item shouldn't drop, so remove it from the loot list
	        }
	        return generatedLoot;
	    }

	    public static class Serializer extends GlobalLootModifierSerializer<LogShardsLootModifierHandler> {

	        @Override
	        public LogShardsLootModifierHandler read(ResourceLocation name, JsonObject object, ILootCondition[] conditionsIn) {
	            int numShards = JSONUtils.getInt(object, "numShards");
	            Item replacementItem = ForgeRegistries.ITEMS.getValue(new ResourceLocation(JSONUtils.getString(object, "replacement")));
	            return new LogShardsLootModifierHandler(conditionsIn, numShards, replacementItem);
	        }

			@Override
			public JsonObject write(LogShardsLootModifierHandler instance) {
				return null;
			}
	    }
	}
}
