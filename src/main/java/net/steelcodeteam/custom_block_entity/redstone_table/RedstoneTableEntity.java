package net.steelcodeteam.custom_block_entity.redstone_table;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.Containers;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import net.steelcodeteam.data.enums.RecipeEnum;
import net.steelcodeteam.recipes.RedstoneTableRecipe;
import net.steelcodeteam.setup.registries.ModBlockEntityRegister;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class RedstoneTableEntity extends BlockEntity implements MenuProvider {
    public ArrayList<Integer> recipes = new ArrayList<>() {{
        for (int i = 0; i < RecipeEnum.values().length; i++) {
            add(0);
        }
    }};


    private final LazyOptional<ItemStackHandler> optional = LazyOptional.of(() -> this.itemHandler);
    private LazyOptional<IItemHandler> lazyItemHandler = LazyOptional.empty();
    protected final ContainerData data;

    private int recipeSelected = -1;


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
                case 1 -> Items.STICK == stack.getItem();
                case 2 -> Items.IRON_INGOT == stack.getItem();
                case 3 -> Items.GOLD_INGOT == stack.getItem();
                case 4 -> Items.COPPER_INGOT == stack.getItem();
                case 5 -> Items.AMETHYST_SHARD == stack.getItem();
                case 6 -> Items.SLIME_BALL == stack.getItem();
                case 7 -> Items.QUARTZ == stack.getItem();
                case 8 -> Items.STRING == stack.getItem();
                case 9 -> stack.getItem().getDescription().toString().contains("planks");
                case 10 -> Items.STONE == stack.getItem();
                case 11 -> Items.COBBLESTONE == stack.getItem();
                case 12 -> Items.GLASS == stack.getItem();
                case 13 -> Items.GLOWSTONE == stack.getItem();
                case 14 -> Items.HAY_BLOCK == stack.getItem();
                case 15 -> Items.SCULK_SENSOR == stack.getItem();

                default -> false;
            };
        }

        @Override
        public int getSlotLimit(int slot) {
            return 512;
        }
    };




    public RedstoneTableEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntityRegister.REDSTONE_TABLE_ENTITY.get(), pos, state);

        this.data = new ContainerData() {
            @Override
            public int get(int index) {
                return switch (index) {
                    case 0 -> RedstoneTableEntity.this.recipes.get(0);
                    case 1 -> RedstoneTableEntity.this.recipes.get(1);
                    case 2 -> RedstoneTableEntity.this.recipes.get(2);
                    case 3 -> RedstoneTableEntity.this.recipes.get(3);
                    case 4 -> RedstoneTableEntity.this.recipes.get(4);
                    case 5 -> RedstoneTableEntity.this.recipes.get(5);
                    case 6 -> RedstoneTableEntity.this.recipes.get(6);
                    case 7 -> RedstoneTableEntity.this.recipes.get(7);
                    case 8 -> RedstoneTableEntity.this.recipes.get(8);
                    case 9 -> RedstoneTableEntity.this.recipes.get(9);
                    case 10 -> RedstoneTableEntity.this.recipes.get(10);
                    case 11 -> RedstoneTableEntity.this.recipes.get(11);
                    case 12 -> RedstoneTableEntity.this.recipes.get(12);
                    case 13 -> RedstoneTableEntity.this.recipes.get(13);
                    case 14 -> RedstoneTableEntity.this.recipes.get(14);
                    case 15 -> RedstoneTableEntity.this.recipes.get(15);
                    case 16 -> RedstoneTableEntity.this.recipes.get(16);
                    case 17 -> RedstoneTableEntity.this.recipes.get(17);
                    case 18 -> RedstoneTableEntity.this.recipes.get(18);
                    case 19 -> RedstoneTableEntity.this.recipes.get(19);
                    case 20 -> RedstoneTableEntity.this.recipes.get(20);
                    case 21 -> RedstoneTableEntity.this.recipes.get(21);
                    case 22 -> RedstoneTableEntity.this.recipes.get(22);
                    case 23 -> RedstoneTableEntity.this.recipes.get(23);
                    case 24 -> RedstoneTableEntity.this.recipes.get(24);
                    case 25 -> RedstoneTableEntity.this.recipeSelected;
                    default -> 0;
                };
            }

            @Override
            public void set(int index, int value) {
                if (index >= 0 && index < RecipeEnum.values().length) {
                    RedstoneTableEntity.this.recipes.set(index, value);
                } else if (index == 25){
                    RedstoneTableEntity.this.recipeSelected = value;
                }
            }

            @Override
            public int getCount() {
                return RecipeEnum.values().length + 1;
            }
        };
    }


    @Override
    protected void saveAdditional(@NotNull CompoundTag tag) {

        tag.put("inventory", itemHandler.serializeNBT());
        for (RecipeEnum recipeEnum : RecipeEnum.values()) {
            tag.putInt("redstone_table.recipe_result." + recipeEnum.name().toLowerCase(), this.recipes.get(recipeEnum.getId()));
        }
        tag.putInt("redstone_table.recipe_selected", this.recipeSelected);

        super.saveAdditional(tag);
    }

    @Override
    public void load(@NotNull CompoundTag tag) {

        itemHandler.deserializeNBT(tag.getCompound("inventory"));
        for (RecipeEnum recipeEnum : RecipeEnum.values()) {
            this.recipes.set(recipeEnum.getId(), tag.getInt("redstone_table.recipe_result."+recipeEnum.name().toLowerCase()));
        }

        this.recipeSelected = tag.getInt("redstone_table.recipe_selected");

        super.load(tag);
    }

    @Override
    public void handleUpdateTag(CompoundTag tag) {
        load(tag);
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
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if(cap == ForgeCapabilities.ITEM_HANDLER) {
            return lazyItemHandler.cast();
        }

        return super.getCapability(cap, side);
    }

    @Override
    public Component getDisplayName() {
        return Component.literal("Redstone Table");
    }

    public void drops() {
        SimpleContainer inventory = new SimpleContainer(itemHandler.getSlots());
        for (int i = 0; i < itemHandler.getSlots(); i++) {
            inventory.setItem(i, itemHandler.getStackInSlot(i));
        }

        Containers.dropContents(this.level, this.worldPosition, inventory);
    }

    public static void tick(Level level, BlockPos blockPos, BlockState state, RedstoneTableEntity entity) {
        if (!level.isClientSide()) {

            initializeList(entity);

            List<RedstoneTableRecipe> recipesList = hasRecipes(entity);

            if (!recipesList.isEmpty()) {
                recipesList.forEach(
                    redstoneTableRecipe -> {
                        for (RecipeEnum enumValue : RecipeEnum.values()) {
                            if (redstoneTableRecipe.getResultItem().getItem().equals(enumValue.getOutput().getItem())) {
                                entity.recipes.set(enumValue.getId(), 1);
                            }
                        }
                    });
            }

            setChanged(level, blockPos, state);

        }
    }

    private static void initializeList(RedstoneTableEntity entity) {
        for (int index = 0; index < RecipeEnum.values().length; index++) {
            entity.recipes.set(index, 0);
        }
    }

    private static List<RedstoneTableRecipe> hasRecipes(RedstoneTableEntity entity) {
        SimpleContainer simpleContainer = new SimpleContainer(entity.itemHandler.getSlots());
        for (int i = 0; i < entity.itemHandler.getSlots(); i++) {
            simpleContainer.setItem(i, entity.itemHandler.getStackInSlot(i));
        }

        return entity.level.getRecipeManager().getRecipesFor(RedstoneTableRecipe.Type.INSTANCE, simpleContainer, entity.level);

    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int id, Inventory inventory, Player player) {
        return new RedstoneTableMenu(id, inventory,this, this.data);
    }

    public ArrayList<Integer> getList(RedstoneTableEntity entity) {
        return this.recipes;
    }

}
