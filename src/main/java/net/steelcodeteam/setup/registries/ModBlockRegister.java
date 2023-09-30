package net.steelcodeteam.setup.registries;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.minecraftforge.registries.RegistryObject;
import net.steelcodeteam.RedstonesTable;
import net.steelcodeteam.modules.custom_blocks.RedstoneTableBlock;

public class ModBlockRegister {

    public static RegistryObject<Block> REDSTONE_TABLE;

    public static void register() {
        REDSTONE_TABLE = RedstonesTable.registerBlock("redstone_table", () -> {
            Block block = new RedstoneTableBlock(BlockBehaviour.Properties.copy(Blocks.STONECUTTER)
                    .strength(10f, 10f)
                    .sound(SoundType.BASALT)
                    .randomTicks()
                    .requiresCorrectToolForDrops()
                    .destroyTime(15f));
            return block;
        });

    }
}
