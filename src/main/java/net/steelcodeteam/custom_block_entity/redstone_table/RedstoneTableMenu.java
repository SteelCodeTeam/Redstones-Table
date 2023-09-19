package net.steelcodeteam.custom_block_entity.redstone_table;

import com.google.common.collect.Lists;
import net.minecraft.core.NonNullList;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.StonecutterRecipe;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.items.SlotItemHandler;
import net.steelcodeteam.recipes.RedstoneTableRecipe;
import net.steelcodeteam.registries.ModBlockRegister;
import net.steelcodeteam.registries.ModMenuRegister;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;


public class RedstoneTableMenu extends AbstractContainerMenu {
    private final RedstoneTableEntity blockEntity;
    private final ContainerLevelAccess levelAccess;
    private final Level level;
    //slots
    private final List<Slot> inputSlots = Lists.newArrayList();
    private Slot resultSlot;
    ResultContainer resultContainer = new ResultContainer();
    private List<ItemStack> inputs = NonNullList.withSize(16, ItemStack.EMPTY);

    private final DataSlot selectedRecipeIndex = DataSlot.standalone();
    private List<RedstoneTableRecipe> recipes = Lists.newArrayList();

    Runnable slotUpdateListener = () -> {
    };
    public final Container container = new SimpleContainer(16) {
        @Override
        public void setChanged() {
            super.setChanged();
            RedstoneTableMenu.this.slotsChanged(this);
            RedstoneTableMenu.this.slotUpdateListener.run();
        }

        @Override
        public int getMaxStackSize() {
            return 512;
        }

        @Override
        public boolean canPlaceItem(int slot, ItemStack stack) {
            return switch (slot) {
                case 0 -> Items.REDSTONE == stack.getItem();
                case 1 -> Items.STONE == stack.getItem();
                case 2 -> Items.COBBLESTONE == stack.getItem();
                case 3 -> Items.ACACIA_PLANKS == stack.getItem();
                case 4 -> Items.GOLD_INGOT == stack.getItem();
                case 5 -> Items.IRON_INGOT == stack.getItem();
                case 6 -> Items.SLIME_BALL == stack.getItem();
                case 7 -> Items.STRING == stack.getItem();
                case 8 -> Items.QUARTZ == stack.getItem();
                case 9 -> Items.GLASS == stack.getItem();
                case 10 -> Items.CHEST == stack.getItem();
                case 11 -> Items.REDSTONE_TORCH == stack.getItem();
                case 12 -> Items.HAY_BLOCK == stack.getItem();
                case 13 -> Items.STICK == stack.getItem();
                case 14 -> Items.GLOWSTONE == stack.getItem();
                case 15 -> Items.COPPER_INGOT == stack.getItem();
                default -> false;
            };
        }
    };

    public RedstoneTableMenu(int menuId, Inventory playerInv, FriendlyByteBuf additionalData) {
        this(menuId, playerInv, (RedstoneTableEntity) playerInv.player.level().getBlockEntity(additionalData.readBlockPos()));
    }

    public RedstoneTableMenu(int menuId, Inventory inventory, RedstoneTableEntity blockEntity) {
        super(ModMenuRegister.REDSTONE_TABLE_MENU.get(), menuId);

        this.blockEntity = blockEntity;
        this.levelAccess = ContainerLevelAccess.create(blockEntity.getLevel(), blockEntity.getBlockPos());
        this.level = inventory.player.level();
        for (int i = 0 ; i< inputs.size();i++) {
            inputs.set(i, ItemStack.EMPTY);
        }

        addPlayerInventory(inventory);
        addPlayerHotbar(inventory);
        addTableMenu();

    }

    private static final int HOTBAR_SLOT_COUNT = 9;
    private static final int PLAYER_INVENTORY_ROW_COUNT = 3;
    private static final int PLAYER_INVENTORY_COLUMN_COUNT = 9;
    private static final int PLAYER_INVENTORY_SLOT_COUNT = PLAYER_INVENTORY_COLUMN_COUNT * PLAYER_INVENTORY_ROW_COUNT;
    private static final int VANILLA_SLOT_COUNT = HOTBAR_SLOT_COUNT + PLAYER_INVENTORY_SLOT_COUNT;
    private static final int VANILLA_FIRST_SLOT_INDEX = 0;
    private static final int TE_INVENTORY_FIRST_SLOT_INDEX = VANILLA_FIRST_SLOT_INDEX + VANILLA_SLOT_COUNT;

    // THIS YOU HAVE TO DEFINE!
    private static final int TE_INVENTORY_SLOT_COUNT = 16;  // must be the number of slots you have!

    @Override
    public ItemStack quickMoveStack(Player playerIn, int index) {
        Slot sourceSlot = slots.get(index);
        if (sourceSlot == null || !sourceSlot.hasItem()) return ItemStack.EMPTY;  //EMPTY_ITEM
        ItemStack sourceStack = sourceSlot.getItem();
        ItemStack copyOfSourceStack = sourceStack.copy();

        // Check if the slot clicked is one of the vanilla container slots
        if (index < VANILLA_FIRST_SLOT_INDEX + VANILLA_SLOT_COUNT) {
            // This is a vanilla container slot so merge the stack into the tile inventory
            if (!moveItemStackTo(sourceStack, TE_INVENTORY_FIRST_SLOT_INDEX, TE_INVENTORY_FIRST_SLOT_INDEX
                    + TE_INVENTORY_SLOT_COUNT, false)) {
                return ItemStack.EMPTY;  // EMPTY_ITEM
            }
        } else if (index < TE_INVENTORY_FIRST_SLOT_INDEX + TE_INVENTORY_SLOT_COUNT) {
            // This is a TE slot so merge the stack into the players inventory
            if (!moveItemStackTo(sourceStack, VANILLA_FIRST_SLOT_INDEX, VANILLA_FIRST_SLOT_INDEX + VANILLA_SLOT_COUNT, false)) {
                return ItemStack.EMPTY;
            }
        } else {
            System.out.println("Invalid slotIndex:" + index);
            return ItemStack.EMPTY;
        }
        // If stack size == 0 (the entire stack was moved) set slot contents to null
        if (sourceStack.getCount() == 0) {
            sourceSlot.set(ItemStack.EMPTY);
        } else {
            sourceSlot.setChanged();
        }
        sourceSlot.onTake(playerIn, sourceStack);
        return copyOfSourceStack;
    }

    @Override
    public boolean stillValid(@NotNull Player player) {
        return stillValid(this.levelAccess, player, ModBlockRegister.REDSTONE_TABLE.get());
    }
    private void addPlayerInventory(Inventory playerInventory) {
        for (int i = 0; i < 3; ++i) {
            for (int l = 0; l < 9; ++l) {
                this.addSlot(new Slot(playerInventory, l + i * 9 + 9, 7 + l * 18, 112 + i * 18));
            }
        }
    }
    private void addPlayerHotbar(Inventory playerInventory) {
        for (int i = 0; i < 9; ++i) {
            this.addSlot(new Slot(playerInventory, i, 7 + i * 18, 170));
        }
    }
    private void addTableMenu() {

        int slotOffsetX = 16;
        int slotOffsetY = 18;
        for (int j = 0; j < 2; j++) {
            for (int i = 0; i < 8; i++) {
                //0 to 7 first line
                //8 to 15 second line
                this.inputSlots.add(i + (j * 8) , this.addSlot(new Slot(this.container,  i + (j * 8), slotOffsetX, slotOffsetY)));
                slotOffsetX += 18;
            }
            slotOffsetX = 16;
            slotOffsetY = 36;
        }
        //16 result slot
        this.resultSlot = this.addSlot(new Slot(this.resultContainer,  16, 145, 73));
    }

    public List<RedstoneTableRecipe> getRecipes() {
        return recipes;
    }

    private void setupRecipeList(SimpleContainer container, List<ItemStack> itemStacks) {

        this.recipes.clear();
        this.selectedRecipeIndex.set(-1);
        this.resultSlot.set(ItemStack.EMPTY);
        if (itemStacks.stream().anyMatch(itemStack -> !itemStack.equals(Items.AIR.getDefaultInstance()))) {
            //todo funciona solo en lado cliente o solo en servidor, el stonecutter devuelve las recetas correctamente en ambas
            this.recipes = level.getRecipeManager().getRecipesFor(RedstoneTableRecipe.Type.INSTANCE, container, level);
        }
    }



    @Override
    public void slotsChanged(Container cont) {
        List<ItemStack> list = this.inputSlots.stream().map(Slot::getItem).toList();

        if (!this.inputs.equals(list)) {
            this.inputs = list;
            this.setupRecipeList((SimpleContainer) cont, this.inputs);
        }
    }

    public void registerUpdateListener(Runnable runnable) {
        this.slotUpdateListener = runnable;
    }

    public boolean hasInputItem() {
        return (!this.recipes.isEmpty() && this.inputs.stream().anyMatch(itemStack -> !itemStack.equals(ItemStack.EMPTY)));
    }
}
