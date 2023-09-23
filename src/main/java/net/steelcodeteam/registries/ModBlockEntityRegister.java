package net.steelcodeteam.registries;

import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.steelcodeteam.RedstonesTable;
import net.steelcodeteam.custom_block_entity.redstone_table.RedstoneTableEntity;

public class ModBlockEntityRegister {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, RedstonesTable.MOD_ID);

    public static final RegistryObject<BlockEntityType<RedstoneTableEntity>> REDSTONE_TABLE_ENTITY =
            BLOCK_ENTITIES.register("redstone_table_entity", () ->
                    BlockEntityType.Builder.of(RedstoneTableEntity::new,
                            ModBlockRegister.REDSTONE_TABLE.get()).build(null));

    public static void register(IEventBus eventBus) {
        BLOCK_ENTITIES.register(eventBus);
    }
}
