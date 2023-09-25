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
import net.steelcodeteam.registries.ModBlockEntityRegister;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class RedstoneTableEntity extends BlockEntity implements MenuProvider {
    public ArrayList<Integer> recipes = new ArrayList<>() {{
        for (int i = 0; i <= 24; i++) {
            add(0);
        }
    }};


    private final LazyOptional<ItemStackHandler> optional = LazyOptional.of(() -> this.itemHandler);
    private LazyOptional<IItemHandler> lazyItemHandler = LazyOptional.empty();
    protected final ContainerData data;


    public LazyOptional<ItemStackHandler> getOptional() {
        return this.optional;
    }


    private final ItemStackHandler itemHandler = new ItemStackHandler(16) {
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
                    default -> 0;
                };
            }

            @Override
            public void set(int index, int value) {
                if (index >= 0 && index <= 24)
                    RedstoneTableEntity.this.recipes.set(index, value);
            }

            @Override
            public int getCount() {
                return 25;
            }
        };
    }


    @Override
    protected void saveAdditional(@NotNull CompoundTag tag) {

        tag.put("inventory", itemHandler.serializeNBT());

        tag.putInt("redstone_table.recipe_result.redstone_torch", this.recipes.get(0));
        tag.putInt("redstone_table.recipe_result.lever", this.recipes.get(1));
        tag.putInt("redstone_table.recipe_result.stone_pressure_plate", this.recipes.get(2));
        tag.putInt("redstone_table.recipe_result.light_weighted_pressure_plate", this.recipes.get(3));
        tag.putInt("redstone_table.recipe_result.heavy_weighted_pressure_plate", this.recipes.get(4));
        tag.putInt("redstone_table.recipe_result.repeater", this.recipes.get(5));
        tag.putInt("redstone_table.recipe_result.comparator", this.recipes.get(6));
        tag.putInt("redstone_table.recipe_result.observer", this.recipes.get(7));
        tag.putInt("redstone_table.recipe_result.dispenser", this.recipes.get(8));
        tag.putInt("redstone_table.recipe_result.dropper", this.recipes.get(9));
        tag.putInt("redstone_table.recipe_result.piston", this.recipes.get(10));
        tag.putInt("redstone_table.recipe_result.sticky_piston", this.recipes.get(11));
        tag.putInt("redstone_table.recipe_result.rail", this.recipes.get(12));
        tag.putInt("redstone_table.recipe_result.powered_rail", this.recipes.get(13));
        tag.putInt("redstone_table.recipe_result.detector_rail", this.recipes.get(14));
        tag.putInt("redstone_table.recipe_result.hopper", this.recipes.get(15));
        tag.putInt("redstone_table.recipe_result.clock", this.recipes.get(16));
        tag.putInt("redstone_table.recipe_result.compass", this.recipes.get(17));
        tag.putInt("redstone_table.recipe_result.redstone_lamp", this.recipes.get(18));
        tag.putInt("redstone_table.recipe_result.note_block", this.recipes.get(19));
        tag.putInt("redstone_table.recipe_result.lightning_rod", this.recipes.get(20));
        tag.putInt("redstone_table.recipe_result.daylight_detector", this.recipes.get(21));
        tag.putInt("redstone_table.recipe_result.calibrated_sculk_sensor", this.recipes.get(22));
        tag.putInt("redstone_table.recipe_result.tripwire_hooks", this.recipes.get(23));
        tag.putInt("redstone_table.recipe_result.target", this.recipes.get(24));

        super.saveAdditional(tag);
    }

    @Override
    public void load(@NotNull CompoundTag tag) {

        itemHandler.deserializeNBT(tag.getCompound("inventory"));
        this.recipes.set(0, tag.getInt("redstone_table.recipe_result.redstone_torch"));
        this.recipes.set(1, tag.getInt("redstone_table.recipe_result.lever"));
        this.recipes.set(2, tag.getInt("redstone_table.recipe_result.stone_pressure_plate"));
        this.recipes.set(3, tag.getInt("redstone_table.recipe_result.light_weighted_pressure_plate"));
        this.recipes.set(4, tag.getInt("redstone_table.recipe_result.heavy_weighted_pressure_plate"));
        this.recipes.set(5, tag.getInt("redstone_table.recipe_result.repeater"));
        this.recipes.set(6, tag.getInt("redstone_table.recipe_result.comparator"));
        this.recipes.set(7, tag.getInt("redstone_table.recipe_result.observer"));
        this.recipes.set(8, tag.getInt("redstone_table.recipe_result.dispenser"));
        this.recipes.set(9, tag.getInt("redstone_table.recipe_result.dropper"));
        this.recipes.set(10, tag.getInt("redstone_table.recipe_result.piston"));
        this.recipes.set(11, tag.getInt("redstone_table.recipe_result.sticky_piston"));
        this.recipes.set(12, tag.getInt("redstone_table.recipe_result.rail"));
        this.recipes.set(13, tag.getInt("redstone_table.recipe_result.powered_rail"));
        this.recipes.set(14, tag.getInt("redstone_table.recipe_result.detector_rail"));
        this.recipes.set(15, tag.getInt("redstone_table.recipe_result.hopper"));
        this.recipes.set(16, tag.getInt("redstone_table.recipe_result.clock"));
        this.recipes.set(17, tag.getInt("redstone_table.recipe_result.compass"));
        this.recipes.set(18, tag.getInt("redstone_table.recipe_result.redstone_lamp"));
        this.recipes.set(19, tag.getInt("redstone_table.recipe_result.note_block"));
        this.recipes.set(20, tag.getInt("redstone_table.recipe_result.lightning_rod"));
        this.recipes.set(21, tag.getInt("redstone_table.recipe_result.daylight_detector"));
        this.recipes.set(22, tag.getInt("redstone_table.recipe_result.calibrated_sculk_sensor"));
        this.recipes.set(23, tag.getInt("redstone_table.recipe_result.tripwire_hooks"));
        this.recipes.set(24, tag.getInt("redstone_table.recipe_result.target"));

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
        for (int index = 0; index <= 24; index++) {
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
