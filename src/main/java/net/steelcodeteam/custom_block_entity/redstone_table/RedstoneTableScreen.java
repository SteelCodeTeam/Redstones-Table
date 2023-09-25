package net.steelcodeteam.custom_block_entity.redstone_table;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.Item;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.steelcodeteam.RedstonesTable;
import net.steelcodeteam.data.enums.RecipeEnum;
import net.steelcodeteam.modules.Square;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.util.List;

@OnlyIn(Dist.CLIENT)
public class RedstoneTableScreen extends AbstractContainerScreen<RedstoneTableMenu> {

    private static final int SCROLLER_WIDTH = 12;    //TODO ANCHO
    private static final int SCROLLER_HEIGHT = 15;   //TODO ALTO
    private static final int RECIPES_X = 17;         //TODO AHNCHO DE RECETA
    private static final int RECIPES_Y = 72;         //TODO ALTO DE RECETA
    private static final int RECIPES_COLUMNS = 6;    //TODO COLUMNAS MAXIMAS
    private static final int RECIPES_ROWS = 2;      //TODO FILAS MAXIMAS

    private static final int RECIPES_IMAGE_SIZE_WIDTH = 16; //TODO TAMAÑO DE IMAGEN DE RECETA
    private static final int RECIPES_IMAGE_SIZE_HEIGHT = 18;//TODO

    private boolean displayRecipes;

    private float scrollOffs;
    private boolean scrolling;
    private int startIndex;
    private static final ResourceLocation TEXTURE =
            new ResourceLocation(RedstonesTable.MOD_ID, "textures/gui/redstone_table_gui.png");
    private static final Square SIZE_GUI = new Square(new Point(0,0), new Point(176, 0), new Point(0, 194), new Point(176, 194));

    public RedstoneTableScreen(RedstoneTableMenu menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, title);
        this.inventoryLabelY+=30;
    }

    @Override
    protected void renderBg(@NotNull GuiGraphics guiGraphics, float partialTicks, int mouseX, int mouseY) {
        hasRecipe();
        renderBackground(guiGraphics);
        int x = (width - SIZE_GUI.getBottomRight().x) / 2;
        int y = (height - SIZE_GUI.getBottomRight().y) / 2;
        int offsetY = 14;
        int scrollVertical = (int) (21.0F * this.scrollOffs);;
        //fondo
        guiGraphics.blit(TEXTURE, x - 1, y + offsetY, SIZE_GUI.getTopLeft().x, SIZE_GUI.getTopLeft().y, SIZE_GUI.getBottomRight().x, SIZE_GUI.getBottomRight().y);
        //scroll
        guiGraphics.blit(TEXTURE, x + 116, y + 76 + scrollVertical, 176 + (this.isScrollBarActive() ? 0 : 12),0, 12, 15);


        int xIzquierda = x + 16; //esquinas del recuadro de recetas
        int yAbajo = y + 76;
        this.renderButtons(guiGraphics,xIzquierda,yAbajo);
        this.renderRecipes(guiGraphics, xIzquierda, yAbajo + 1);
    }

    private void renderRecipes(GuiGraphics guiGraphics, int xIzquierda, int yAbajo) {
        List<Integer> recipes = this.menu.getRecipes();
        int xPosition = xIzquierda;
        int yPosition = yAbajo;
        //int index = 0;

        int draw = 0;
        int cantRecipes = 0;


        for (int index = 0; index < recipes.size(); index++) {
            if (recipes.get(index) == 1) {
                if (this.startIndex <= cantRecipes && draw < 12) {
                    guiGraphics.renderItem(RecipeEnum.values() [index].getOutput(), xPosition, yPosition);
                    xPosition += 16;
                    draw++;
                }
                cantRecipes ++;
                if (xPosition >= xIzquierda + (16 * 6)) {
                    xPosition = xIzquierda;
                    yPosition += 18;
                }
            }
        }



        /*for (Integer val : recipes) {
            if (val == 1) {
                if (this.startIndex <= index && draw < 12) {
                    guiGraphics.renderItem(RecipeEnum.values() [index].getOutput(), xPosition, yPosition);
                    draw++;
                    xPosition += 16;
                }
                index++;
            }
            if (xPosition >= xIzquierda + (16 * 6)) {
                xPosition = xIzquierda;
                yPosition += 18;
            }
        }*/

    }

    private void renderButtons(GuiGraphics guiGraphics, int xIzquierda, int yAbajo) {
        List<Integer> recipes = this.menu.getRecipes();
        int xPosition = xIzquierda;
        int yPosition = yAbajo;
        int cantRecipes = 0;
        int draw = 0;

        for (int index = 0; index < recipes.size(); index++) {
            if (recipes.get(index) == 1) {
                if (this.startIndex <= cantRecipes && draw < 12) {
                    //index se puede usar para comparar si la receta de index esta seleccionada
                    guiGraphics.blit(TEXTURE, xPosition, yPosition, 176, 15, 16, 18);
                    xPosition += 16;
                    draw++;
                }
                cantRecipes++;
                if (xPosition >= xIzquierda + (16 * 6)) {
                    xPosition = xIzquierda;
                    yPosition += 18;
                }
            }
        }
        /*for (Integer val : recipes) {
            if (val == 1) {
                if (this.startIndex <= cont  && draw < 12) {
                    guiGraphics.blit(TEXTURE, xPosition, yPosition, 176, 15, 16, 18);
                    draw++;
                    xPosition += 16;
                }
                cont ++;
            }
            if (xPosition >= xIzquierda + (16 * 6)) {
                xPosition = xIzquierda;
                yPosition += 18;
            }
        }*/
    }


    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
        super.render(guiGraphics, mouseX, mouseY, partialTicks);
        renderTooltip(guiGraphics, mouseX, mouseY);
    }

    @Override
    public boolean mouseDragged(double p_99322_, double p_99323_, int p_99324_, double p_99325_, double p_99326_) {
        if (this.scrolling && this.isScrollBarActive()) {
            int i = this.topPos + 14;
            int j = i + 54;
            this.scrollOffs = ((float)p_99323_ - (float)i - 7.5F) / ((float)(j - i) - 15.0F);
            this.scrollOffs = Mth.clamp(this.scrollOffs, 0.0F, 1.0F);
            this.startIndex = (int)((double)(this.scrollOffs * (float)this.getOffscreenRows()) + 0.5D) * 4;
            return true;
        } else {
            return super.mouseDragged(p_99322_, p_99323_, p_99324_, p_99325_, p_99326_);
        }
    }

    @Override
    public boolean mouseScrolled(double p_99314_, double p_99315_, double p_99316_) {
        if (this.isScrollBarActive()) {
            int i = this.getOffscreenRows();
            float f = (float)p_99316_ / (float) i;
            this.scrollOffs = Mth.clamp(this.scrollOffs - f, 0.0F, 1.0F);
            this.startIndex = (int)((double)(this.scrollOffs * (float)i) + 0.5D) * 6;
        }

        return true;
    }
    private boolean isScrollBarActive() {
        return this.displayRecipes && (int) this.menu.getRecipes().stream().filter(val -> val == 1).count() > 12;
    }

    private int getOffscreenRows() {
        return ((int) this.menu.getRecipes().stream().filter(val -> val == 1).count() + 6 - 1) / 6 - 2;
    }

    private void hasRecipe() {
        this.displayRecipes = this.menu.getRecipes().stream().anyMatch(val -> val == 1);
        if (!this.displayRecipes) {
            this.scrollOffs = 0.0F;
            this.startIndex = 0;
        }

    }
}
