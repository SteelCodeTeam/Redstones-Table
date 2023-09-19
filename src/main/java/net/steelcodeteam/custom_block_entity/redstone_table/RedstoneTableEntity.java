package net.steelcodeteam.custom_block_entity.redstone_table;

import ca.weblite.objc.Proxy;
import com.google.common.collect.Lists;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.Containers;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
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
import net.steelcodeteam.data.enums.RecipeEnum;
import net.steelcodeteam.recipes.RedstoneTableRecipe;
import net.steelcodeteam.registries.ModBlockEntityRegister;
import org.antlr.v4.runtime.atn.SemanticContext;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class RedstoneTableEntity extends BlockEntity implements MenuProvider {

    private List<RedstoneTableRecipe> recipes = Lists.newArrayList();
    private int selectedRecipeIndex;
    private final LazyOptional<ItemStackHandler> optional = LazyOptional.of(() -> this.itemHandler);
    private LazyOptional<IItemHandler> lazyItemHandler = LazyOptional.empty();
    protected final ContainerData data;
    public LazyOptional<ItemStackHandler> getOptional() {
        return this.optional;
    }
    private final ItemStackHandler itemHandler = new ItemStackHandler(17) {
        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
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

        @Override
        public int getSlotLimit(int slot) {
            return 512;
        }
    };

    private final boolean[] recipeListValues = new boolean[RecipeEnum.values().length];

    public RedstoneTableEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntityRegister.REDSTONE_TABLE_ENTITY.get(), pos, state);

        this.data = new ContainerData() {
            @Override
            public int get(int index) {
                if (RedstoneTableEntity.this.recipeListValues[index]) {
                    return 1;
                } else {
                    return 0;
                }
            }

            @Override
            public void set(int index, int value) {
                if (value == 1) {
                    RedstoneTableEntity.this.recipeListValues[index] = true;
                } else {
                    RedstoneTableEntity.this.recipeListValues[index] = false;
                }
            }

            @Override
            public int getCount() {
                return RedstoneTableEntity.this.recipeListValues.length;
            }
        };
    }


    @Override
    protected void saveAdditional(CompoundTag tag) {
        tag.put("inventory", itemHandler.serializeNBT());
        int cont = 0;
        for (boolean val : recipeListValues) {
            tag.putBoolean("recipe_"+cont, val);
            cont++;
        }
        super.saveAdditional(tag);
    }

    @Override
    public void load(CompoundTag tag) {
        itemHandler.deserializeNBT(tag.getCompound("inventory"));
        for(int index = 0; index< recipeListValues.length; index++) {
            recipeListValues[index] = tag.getBoolean("recipe_"+ index);
        }
        super.load(tag);
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
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap) {
        return cap == ForgeCapabilities.ITEM_HANDLER ? this.optional.cast() : super.getCapability(cap);
    }

    @Override
    public Component getDisplayName() {
        return Component.literal("titulo");
    }

    public void drops() {
        SimpleContainer inventory = new SimpleContainer(itemHandler.getSlots());
        for (int i = 0; i < itemHandler.getSlots(); i++) {
            inventory.setItem(i, itemHandler.getStackInSlot(i));
        }

        Containers.dropContents(this.level, this.worldPosition, inventory);
    }

    private void setupRecipeList(RedstoneTableEntity entity) {

        SimpleContainer inventory = new SimpleContainer(entity.itemHandler.getSlots());
        for (int i = 0; i < entity.itemHandler.getSlots(); i++) {
            inventory.setItem(i, entity.itemHandler.getStackInSlot(i));
        }

        this.recipes.clear();
        this.recipes = level.getRecipeManager().getRecipesFor(RedstoneTableRecipe.Type.INSTANCE, inventory, level);

        for (RecipeEnum recipeEnum : RecipeEnum.values()) {
            for (RedstoneTableRecipe recipe: this.recipes){
                if (recipeEnum.getOutput().equals(recipe.getOutput())) {
                    recipeListValues[recipeEnum.getId()] = true;
                }
            }
        }

    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int id, Inventory inventory, Player player) {
        return new RedstoneTableMenu(id, inventory,this, this.data);
    }

}
