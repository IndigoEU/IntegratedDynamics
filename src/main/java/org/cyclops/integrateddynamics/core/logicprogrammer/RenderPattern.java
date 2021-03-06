package org.cyclops.integrateddynamics.core.logicprogrammer;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import lombok.Getter;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.inventory.container.Container;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.apache.commons.lang3.tuple.Pair;
import org.cyclops.cyclopscore.helper.RenderHelpers;
import org.cyclops.integrateddynamics.api.client.gui.subgui.IGuiInputElement;
import org.cyclops.integrateddynamics.api.client.gui.subgui.ISubGuiBox;
import org.cyclops.integrateddynamics.api.logicprogrammer.IConfigRenderPattern;
import org.cyclops.integrateddynamics.core.client.gui.subgui.SubGuiBox;

/**
 * Sub gui for rendering logic programmer elements.
 * @author rubensworks
 */
@OnlyIn(Dist.CLIENT)
public class RenderPattern<E extends IGuiInputElement, G extends AbstractGui, C extends Container> extends SubGuiBox implements ISubGuiBox {

    @Getter
    protected final E element;
    private final int x, y;
    protected final G gui;
    protected final C container;

    public RenderPattern(E element, int baseX, int baseY, int maxWidth, int maxHeight,
                         G gui, C container) {
        super(SubGuiBox.Box.LIGHT);
        this.element = element;
        IConfigRenderPattern configRenderPattern = element.getRenderPattern();
        this.x = baseX + (maxWidth  - configRenderPattern.getWidth()) / 2;
        this.y = baseY + (maxHeight - configRenderPattern.getHeight()) / 2;
        this.gui = gui;
        this.container = container;
    }

    protected void drawSlot(MatrixStack matrixStack, int x, int y) {
        this.blit(matrixStack, x, y, 19, 0, 18, 18);
    }

    @Override
    public void init(int guiLeft, int guiTop) {
        super.init(guiLeft, guiTop);
    }

    @Override
    public void tick() {

    }

    protected boolean drawRenderPattern() {
        return true;
    }

    @Override
    public void drawGuiContainerBackgroundLayer(MatrixStack matrixStack, int guiLeft, int guiTop, TextureManager textureManager, FontRenderer fontRenderer, float partialTicks, int mouseX, int mouseY) {
        super.drawGuiContainerBackgroundLayer(matrixStack, guiLeft, guiTop, textureManager, fontRenderer, partialTicks, mouseX, mouseY);
        if (drawRenderPattern()) {
            IConfigRenderPattern configRenderPattern = element.getRenderPattern();

            int baseX = getX() + guiLeft;
            int baseY = getY() + guiTop;

            for (Pair<Integer, Integer> slot : configRenderPattern.getSlotPositions()) {
                drawSlot(matrixStack, baseX + slot.getLeft(), baseY + slot.getRight());
            }

            if (configRenderPattern.getSymbolPosition() != null) {
                RenderHelpers.drawScaledCenteredString(matrixStack, fontRenderer, element.getSymbol(),
                        baseX + configRenderPattern.getSymbolPosition().getLeft(),
                        baseY + configRenderPattern.getSymbolPosition().getRight() + 8,
                        0, 1, 0);
            }
            RenderSystem.color3f(1, 1, 1);
        }
    }

    @Override
    public int getX() {
        return this.x;
    }

    @Override
    public int getY() {
        return this.y;
    }

    @Override
    public int getWidth() {
        return element.getRenderPattern().getWidth();
    }

    @Override
    public int getHeight() {
        return element.getRenderPattern().getHeight();
    }

}
