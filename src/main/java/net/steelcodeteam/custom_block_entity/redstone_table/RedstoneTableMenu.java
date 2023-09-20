package net.steelcodeteam.custom_block_entity.redstone_table;

import com.google.common.collect.Lists;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.items.SlotItemHandler;
import net.steelcodeteam.data.enums.RecipeEnum;
import net.steelcodeteam.registries.ModBlockRegister;
import net.steelcodeteam.registries.ModMenuRegister;
import org.jetbrains.annotations.NotNull;

import java.util.*;


public class RedstoneTableMenu extends AbstractContainerMenu {
    private final RedstoneTableEntity blockEntity;
    private final Level level;
    private final List<Slot> inputSlots = Lists.newArrayList();
    private Slot resultSlot;
    private final ContainerData data;
    ResultContainer resultContainer = new ResultContainer();
    private final DataSlot selectedRecipeIndex = DataSlot.standalone();


    public RedstoneTableMenu(int menuId, Inventory playerInv, FriendlyByteBuf additionalData) {
        this(menuId, playerInv, (RedstoneTableEntity) playerInv.player.level().getBlockEntity(additionalData.readBlockPos()), new SimpleContainerData(17));
    }

    public RedstoneTableMenu(int menuId, Inventory inventory, RedstoneTableEntity blockEntity, ContainerData data) {
        super(ModMenuRegister.REDSTONE_TABLE_MENU.get(), menuId);
        this.blockEntity = blockEntity;
        this.level = inventory.player.level();
        this.data = data;
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
        return stillValid(ContainerLevelAccess.create(level, blockEntity.getBlockPos()),
                player, ModBlockRegister.REDSTONE_TABLE.get());
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

        this.blockEntity.getOptional().ifPresent(itemStackHandler -> {
            int slotOffsetX = 16;
            int slotOffsetY = 18;
            for (int j = 0; j < 2; j++) {
                for (int i = 0; i < 8; i++) {
                    //0 to 7 first line
                    //8 to 15 second line
                    this.addSlot(new SlotItemHandler(itemStackHandler,  i + (j * 8), slotOffsetX, slotOffsetY));
                    slotOffsetX += 18;
                }
                slotOffsetX = 16;
                slotOffsetY = 36;
            }
            //16 result slot
            this.addSlot(new SlotItemHandler(itemStackHandler,  16, 145, 73));
        });
    }

    public List<RecipeEnum> recipeEnums = new ArrayList<>();

    public void generateRecipes() {
        recipeEnums.clear();
        for (RecipeEnum recipeEnum : RecipeEnum.values()) {
            if (this.data.get(recipeEnum.getId()) == 1) {
                recipeEnums.add(recipeEnum);
            }
        }
    }

    public List<RecipeEnum> getRecipes() {
        return this.recipeEnums;
    }
}
