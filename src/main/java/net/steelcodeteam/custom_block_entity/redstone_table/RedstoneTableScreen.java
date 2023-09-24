package net.steelcodeteam.custom_block_entity.redstone_table;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Inventory;
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
        renderBackground(guiGraphics);
        int x = (width - SIZE_GUI.getBottomRight().x) / 2;
        int y = (height - SIZE_GUI.getBottomRight().y) / 2;
        int offsetY = 14;
        guiGraphics.blit(TEXTURE, x - 1, y + offsetY, SIZE_GUI.getTopLeft().x, SIZE_GUI.getTopLeft().y, SIZE_GUI.getBottomRight().x, SIZE_GUI.getBottomRight().y);


        int xIzquierda = x + 16; //esquinas del recuadro de recetas
        int yAbajo = y + 76;
        int j1 = this.startIndex + 12;

        this.renderButtons(guiGraphics,xIzquierda,yAbajo,j1);
        this.renderRecipes(guiGraphics, xIzquierda, yAbajo + 1, j1);
    }

    private void renderRecipes(GuiGraphics guiGraphics, int xIzquierda, int yAbajo, int j1) {
        List<Integer> recipes = this.menu.getRecipes();
        int xPosition = xIzquierda;
        int yPosition = yAbajo;
        for(int index = 0; index < recipes.size(); index++) {
            //renderiza el item de salida en el menu de seleccion
            if (recipes.get(index) == 1) {
                guiGraphics.renderItem(RecipeEnum.values()[index].getOutput(), xPosition, yPosition);
                xPosition += 16;
            }
            if (xPosition >= xIzquierda + (16 * 6)) {
                xPosition = xIzquierda;
                yPosition += 19;
            }
        }
    }

    private void renderButtons(GuiGraphics guiGraphics, int xIzquierda, int yAbajo, int j1) {
        List<Integer> recipes = this.menu.getRecipes();
        int xPosition = xIzquierda;
        int yPosition = yAbajo;
        for(int index = 0; index < recipes.size(); index++) {
            //renderiza el item de salida en el menu de seleccion
            if (recipes.get(index) == 1) {
                guiGraphics.blit(TEXTURE, xPosition, yPosition, 176, 15, 16, 18);
                xPosition += 16;
            }
            if (xPosition >= xIzquierda + (16 * 6)) {
                xPosition = xIzquierda;
                yPosition += 19;
            }
        }
    }


    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
        super.render(guiGraphics, mouseX, mouseY, partialTicks);
        renderTooltip(guiGraphics, mouseX, mouseY);
    }

}
