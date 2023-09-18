package net.steelcodeteam.custom_block_entity.redstone_table;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.Containers;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import net.steelcodeteam.RedstonesTable;
import net.steelcodeteam.recipes.RedstoneTableRecipe;
import net.steelcodeteam.registries.ModBlockEntityRegister;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class RedstoneTableEntity extends BlockEntity implements MenuProvider {

    private final ItemStackHandler itemHandler = new ItemStackHandler(17) {

        @Override
        protected void onContentsChanged(int slot) {
            setupRecipeList();
        }

        @Override
        public @NotNull ItemStack insertItem(int slot, @NotNull ItemStack stack, boolean simulate) {
            return super.insertItem(slot, stack, simulate);
        }

        @Override
        public int getSlotLimit(int slot) {
            return 512;
        }

        @Override
        public boolean isItemValid(int slot, @NotNull ItemStack stack) {
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

    private LazyOptional<IItemHandler> lazyItemHandler = LazyOptional.empty();

    public RedstoneTableEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntityRegister.REDSTONE_TABLE_ENTITY.get(), pos, state);

    }
    private final LazyOptional<ItemStackHandler> optional = LazyOptional.of(() -> this.itemHandler);
    public LazyOptional<ItemStackHandler> getOptional() {
        return this.optional;
    }

    public void drops() {
        SimpleContainer inventory = new SimpleContainer(itemHandler.getSlots());
        for (int i = 0; i < itemHandler.getSlots(); i++) {
            inventory.setItem(i, itemHandler.getStackInSlot(i));
        }

        Containers.dropContents(this.level, this.worldPosition, inventory);
    }
    @Override
    public void onLoad() {
        super.onLoad();
        lazyItemHandler = LazyOptional.of(() -> itemHandler);
    }

    @Override
    public void invalidateCaps() {
        super.invalidateCaps();
        lazyItemHandler.invalidate();
    }
    @Override
    public void load(@NotNull CompoundTag nbt) {
        super.load(nbt);
        CompoundTag compoundTag = nbt.getCompound(RedstonesTable.MODID);
        this.itemHandler.deserializeNBT(compoundTag.getCompound("Inventory"));
    }

    @Override
    protected void saveAdditional(@NotNull CompoundTag nbt) {
        super.saveAdditional(nbt);
        CompoundTag compoundTag = new CompoundTag();
        compoundTag.put("Inventory", this.itemHandler.serializeNBT());
        nbt.put(RedstonesTable.MODID, compoundTag);
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap) {
        return cap == ForgeCapabilities.ITEM_HANDLER ? this.optional.cast() : super.getCapability(cap);
    }


    public ItemStackHandler getInventory() {
        return this.itemHandler;
    }

    public ItemStack getStackInSlot(int slot) {
        return this.itemHandler.getStackInSlot(slot);
    }

    public void setStackInSlot(int slot, ItemStack stack) {
        this.itemHandler.setStackInSlot(slot, stack);
    }

    @Override
    public Component getDisplayName() {
        return Component.literal("titulo");
    }

    private List<RedstoneTableRecipe> recipes = new ArrayList<>();
    public void setupRecipeList() {

        SimpleContainer container = new SimpleContainer(16);
        for (int i = 0; i < container.getContainerSize(); i++) {
            container.setItem(i, itemHandler.getStackInSlot(i));
        }
        this.recipes.clear();

        this.recipes = level.getRecipeManager().getRecipesFor(RedstoneTableRecipe.Type.INSTANCE , container, level);
    }

    public List<RedstoneTableRecipe> getRecipes() {
        return recipes;
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int id, Inventory inventory, Player player) {
        return new RedstoneTableMenu(id, inventory,this);
    }

}
