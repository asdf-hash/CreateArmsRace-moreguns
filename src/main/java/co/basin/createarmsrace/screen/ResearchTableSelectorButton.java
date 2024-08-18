package co.basin.createarmsrace.screen;

import co.basin.createarmsrace.Config;
import co.basin.createarmsrace.CreateArmsRace;
import co.basin.createarmsrace.network.ModPacketHandler;
import co.basin.createarmsrace.network.ResearchTableMessage;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractButton;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.Renderable;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.narration.NarratableEntry;
import net.minecraft.client.gui.narration.NarratedElementType;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.gui.screens.inventory.EnchantmentScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.awt.*;

public class ResearchTableSelectorButton extends AbstractButton implements GuiEventListener, NarratableEntry, Renderable {
    private static final ResourceLocation TEXTURE = new ResourceLocation(CreateArmsRace.MODID, "textures/gui/research_table_gui.png");

    private final String[] selections;
    public final int buttonHeight;
    private double scrollPos = 0;
    private final int scrollBound;
    private int mouseY = 0;

    private final AbstractContainerMenu menu;

    public ResearchTableSelectorButton(int pX, int pY, int width, int height, int buttonHeight, String[] selections, AbstractContainerMenu menu) {
        super(pX, pY, width, height, Component.empty());
        this.scrollBound = buttonHeight * selections.length;
        this.buttonHeight = buttonHeight;
        this.selections = selections;
        this.menu = menu;
    }

    @Override
    protected void renderWidget(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick) {}

    public void renderBg(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
        this.mouseY = pMouseY;
        for (int i = 0; i < selections.length; i++) {
            int ry = i * buttonHeight + getY() + (int) scrollPos;
            if (ry >= this.getY() + this.height || ry < this.getY() - 9) { continue; }
            pGuiGraphics.blit(TEXTURE, this.getX(), ry, 187, 168, 69, 10);
            if (ry >= this.getY() + this.height - 9 || ry < this.getY()) { continue; }
            pGuiGraphics.drawString(Minecraft.getInstance().font, selections[i], this.getX() + 1, ry + 1, this.getFGColor() | Mth.ceil(this.alpha * 255.0F) << 24);
        }
    }

    @Override
    public boolean mouseScrolled(double pMouseX, double pMouseY, double pDelta) {
        scrollPos = Mth.clamp(scrollPos + pDelta * 4, this.height - this.scrollBound, 0);
        return true;
    }

    @Nullable
    @Override
    public Tooltip getTooltip() {
        return null;
    }

    @Override
    public @NotNull NarrationPriority narrationPriority() {
        return NarrationPriority.HOVERED;
    }

    @Override
    protected void updateWidgetNarration(@NotNull NarrationElementOutput narrationElementOutput) {
        narrationElementOutput.add(NarratedElementType.USAGE, "grid");
    }

    public int mousePosToIndex(int mouseY) {
        return (mouseY - getY() - (int) scrollPos) / buttonHeight;
    }

    @Override
    public void onPress() {
        int index = mousePosToIndex(mouseY);
        if (index >= selections.length) { return; }
        if (index < Config.weapon_types.length) {
            CreateArmsRace.log(Config.weapon_types[index]);
        } else {
            CreateArmsRace.log(Config.ammo_types[index - Config.weapon_types.length]);
        }
        ModPacketHandler.INSTANCE.sendToServer(new ResearchTableMessage(index, ((ResearchTableMenu) menu).blockEntity.getBlockPos()));
    }



}
