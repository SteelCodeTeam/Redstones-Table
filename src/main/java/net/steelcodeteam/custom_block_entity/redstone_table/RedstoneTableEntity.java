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
import net.steelcodeteam.recipes.RedstoneTableRecipe;
import net.steelcodeteam.registries.ModBlockEntityRegister;
import org.antlr.v4.runtime.atn.SemanticContext;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class RedstoneTableEntity extends BlockEntity implements MenuProvider {

    public RedstoneTableEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntityRegister.REDSTONE_TABLE_ENTITY.get(), pos, state);
            }

    @Override
    public Component getDisplayName() {
        return Component.literal("titulo");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int id, Inventory inventory, Player player) {
        return new RedstoneTableMenu(id, inventory,this);
    }



}
