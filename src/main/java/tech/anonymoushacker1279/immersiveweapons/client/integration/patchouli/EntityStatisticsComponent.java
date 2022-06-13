package tech.anonymoushacker1279.immersiveweapons.client.integration.patchouli;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;
import vazkii.patchouli.api.*;

import java.util.function.UnaryOperator;

public class EntityStatisticsComponent implements ICustomComponent {
	private transient int x, y, textColor;
	private transient String statisticType;
	private transient ItemStack itemStackIcon;
	private transient EntityType<?> entityType;

	public IVariable color;
	public IVariable statType;
	public IVariable icon;
	public IVariable entity;

	@Override
	public void build(int componentX, int componentY, int pageNum) {
		x = componentX != -1 ? componentX : 7;
		y = componentY;
	}

	@Override
	public void render(@NotNull PoseStack poseStack, @NotNull IComponentRenderContext context,
	                   float partialTicks, int mouseX, int mouseY) {

		Font font = Minecraft.getInstance().font;
		TranslatableComponent stats;
		if (statisticType.equals("entities_killed")) {
			int entitiesKilled = EntityStatisticsProcessor.STATS_DICT.get(entityType)[0];
			stats = new TranslatableComponent("immersiveweapons.lorebook.entity_statistics.entities_killed", entitiesKilled);

			int wY = y + 15;
			for (FormattedCharSequence charSequence : font.split(KilledEntityTiers.getTier(entitiesKilled), 100)) {
				font.draw(poseStack, charSequence, x + 5, wY, textColor);
				wY += 9;
			}
		} else if (statisticType.equals("deaths_by_entity")) {
			int deathsByEntity = EntityStatisticsProcessor.STATS_DICT.get(entityType)[1];
			stats = new TranslatableComponent("immersiveweapons.lorebook.entity_statistics.deaths_by_entity", deathsByEntity);
		} else {
			stats = new TranslatableComponent("null");
		}

		int statsWidth = font.width(stats);
		context.renderItemStack(poseStack, statsWidth + 7, y - 4, 0, 0, itemStackIcon);

		font.draw(poseStack, stats, x, y, textColor);
	}

	@Override
	public void onVariablesAvailable(UnaryOperator<IVariable> lookup) {
		IVariable colorVar = lookup.apply(color);
		IVariable statTypeVar = lookup.apply(statType);
		IVariable iconVar = lookup.apply(icon);
		IVariable entityVar = lookup.apply(entity);

		textColor = Integer.parseInt(colorVar.unwrap().getAsString(), 16);
		statisticType = statTypeVar.unwrap().getAsString();
		itemStackIcon = new ItemStack(ForgeRegistries.ITEMS.getValue(ResourceLocation.tryParse(iconVar.unwrap().getAsString())));
		entityType = ForgeRegistries.ENTITIES.getValue(ResourceLocation.tryParse(entityVar.unwrap().getAsString()));
	}
}