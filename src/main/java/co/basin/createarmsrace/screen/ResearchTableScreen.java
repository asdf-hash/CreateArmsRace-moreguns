package co.basin.createarmsrace.screen;

import co.basin.createarmsrace.Config;
import co.basin.createarmsrace.CreateArmsRace;
import co.basin.createarmsrace.items.ModItems;
import com.mojang.blaze3d.systems.RenderSystem;
import com.tacz.guns.api.item.builder.AmmoItemBuilder;
import com.tacz.guns.api.item.builder.GunItemBuilder;
import com.tacz.guns.item.ModernKineticGunItem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

import java.text.DecimalFormat;

public class ResearchTableScreen extends AbstractContainerScreen<ResearchTableMenu> {
    private static final ResourceLocation TEXTURE = new ResourceLocation(CreateArmsRace.MODID, "textures/gui/research_table_gui.png");

    private static final DecimalFormat DF = new DecimalFormat("##.00");

    private ResearchTableSelectorButton researchTableSelectorButton = null;

    public ResearchTableScreen(ResearchTableMenu menu, Inventory inventory, Component component) {
        super(menu, inventory, component);
        imageWidth = 256;
        imageHeight = 168;
    }

    @Override
    protected void init() {
        super.init();
        researchTableSelectorButton = addRenderableWidget(new ResearchTableSelectorButton(this.leftPos + 180, this.topPos + 12, 69, 146, 10, Config.SELECTION_DISPLAY_NAMES, this.menu));
    }

    @Override
    protected void renderBg(GuiGraphics graphics, float partialTick, int mouseX, int mouseY) {
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        researchTableSelectorButton.renderBg(graphics, mouseX, mouseY, partialTick);
        graphics.blit(TEXTURE, this.leftPos, this.topPos, 0, 0, imageWidth, imageHeight);
        String name = Config.SELECTION_DISPLAY_NAMES[menu.getResearchSelectionIndex()];
        String time = menu.getTimeRemaining() > 13d ? "Cannot Research" : DF.format(menu.getTimeRemaining()) + " Hours";
        graphics.drawString(Minecraft.getInstance().font, name, this.leftPos + 89 - (name.length() * 5f / 2f), this.topPos + 22, 4210752, false);
        graphics.drawString(Minecraft.getInstance().font, time, this.leftPos + 89 - (time.length() * 5f / 2f), this.topPos + 55, 4210752, false);
        renderProgressArrow(graphics, this.leftPos, this.topPos);
    }

    private void renderProgressArrow(GuiGraphics graphics, int x, int y) {
        if (menu.isCrafting()) {
            graphics.blit(TEXTURE, x + 72, y + 39, 222, 178, menu.getScaledProgress(), 10);
        }
    }

    @Override
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float delta) {
        renderBackground(graphics);
        super.render(graphics, mouseX, mouseY, delta);
        renderTooltip(graphics, mouseX, mouseY);
    }
}

