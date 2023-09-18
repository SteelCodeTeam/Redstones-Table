package net.steelcodeteam.custom_block_entity.redstone_table;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.crafting.StonecutterRecipe;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.steelcodeteam.RedstonesTable;
import net.steelcodeteam.modules.Square;
import net.steelcodeteam.recipes.RedstoneTableRecipe;
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
            new ResourceLocation(RedstonesTable.MODID, "textures/gui/redstone_table_gui.png");
    private static final Square SIZE_GUI = new Square(new Point(0,0), new Point(176, 0), new Point(0, 194), new Point(176, 194));

    public RedstoneTableScreen(RedstoneTableMenu menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, title);
    }

    @Override
    protected void renderBg(@NotNull GuiGraphics guiGraphics, float partialTicks, int mouseX, int mouseY) {
        renderBackground(guiGraphics);
        int x = (width - SIZE_GUI.getBottomRight().x) / 2;
        int y = (height - SIZE_GUI.getBottomRight().y) / 2;
        int offsetY = 14;
        guiGraphics.blit(TEXTURE, x - 1, y + offsetY, SIZE_GUI.getTopLeft().x, SIZE_GUI.getTopLeft().y, SIZE_GUI.getBottomRight().x, SIZE_GUI.getBottomRight().y);


        int topLeft = SIZE_GUI.getBottomLeft().x + 16;
        int botLeft = SIZE_GUI.getBottomLeft().y + 62;
        int j1 = this.startIndex + 12;

        this.renderRecipes(guiGraphics, topLeft, botLeft, j1);
    }

    private void renderRecipes(GuiGraphics guiGraphics, int topLeft, int botLeft, int j1) {


        List<RedstoneTableRecipe> list = this.menu.getBlockEntity().getRecipes();
        for(int i = this.startIndex; i < j1 && i < list.size(); i++) {
            int j = i - this.startIndex;
            int k = topLeft + j % 4 * 16;
            int l = j / 4;
            int i1 = botLeft + l * 18 + 2;
            //renderiza el item de salida en el menu de seleccion
            guiGraphics.renderItem(list.get(i).getResultItem(this.minecraft.level.registryAccess()), k, i1);
        }
    }


    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
        super.render(guiGraphics, mouseX, mouseY, partialTicks);
        renderTooltip(guiGraphics, mouseX, mouseY);
    }

    public boolean mouseScrolled(double p_99314_, double p_99315_, double p_99316_) {
        if (this.isScrollBarActive()) {
            int i = this.getOffscreenRows();
            float f = (float)p_99316_ / (float)i;
            this.scrollOffs = Mth.clamp(this.scrollOffs - f, 0.0F, 1.0F);
            this.startIndex = (int)((double)(this.scrollOffs * (float)i) + 0.5D) * 4;
        }

        return true;
    }

    protected int getOffscreenRows() {
        return (this.getMenu().getBlockEntity().getRecipes().size() + 6 - 1) / 6 - 2;
    }

    private boolean isScrollBarActive() {
        return this.displayRecipes && this.getMenu().getBlockEntity().getRecipes().size() > 12;
    }

    @Override
    protected void renderLabels(GuiGraphics p_281635_, int p_282681_, int p_283686_) {

    }
}
