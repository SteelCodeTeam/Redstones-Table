package net.steelcodeteam.data.enums;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public enum RecipeEnum {
    REDSTONE_TORCH(0, Items.REDSTONE_TORCH.getDefaultInstance()),
    LEVER(1, Items.LEVER.getDefaultInstance()),
    STONE_PRESSURE_PLATE(2, Items.STONE_PRESSURE_PLATE.getDefaultInstance()),
    LIGHT_WEIGHTED_PRESSURE_PLATE(3, Items.LIGHT_WEIGHTED_PRESSURE_PLATE.getDefaultInstance()),
    HEAVY_WEIGHTED_PRESSURE_PLATE(4, Items.HEAVY_WEIGHTED_PRESSURE_PLATE.getDefaultInstance()),
    REPEATER(5, Items.REPEATER.getDefaultInstance()),
    COMPARATOR(6, Items.COMPARATOR.getDefaultInstance()),
    OBSERVER(7, Items.OBSERVER.getDefaultInstance()),
    DISPENSER(8, Items.DISPENSER.getDefaultInstance()),
    DROPPER(9, Items.DROPPER.getDefaultInstance()),
    PISTON(10, Items.PISTON.getDefaultInstance()),
    STICKY_PISTON(11, Items.STICKY_PISTON.getDefaultInstance()),
    RAIL(12, Items.RAIL.getDefaultInstance()),
    POWERED_RAIL(13, Items.POWERED_RAIL.getDefaultInstance()),
    DETECTOR_RAIL(14, Items.DETECTOR_RAIL.getDefaultInstance()),
    HOPPER(15, Items.HOPPER.getDefaultInstance()),
    CLOCK(16, Items.CLOCK.getDefaultInstance()),
    COMPASS(17, Items.COMPASS.getDefaultInstance()),
    REDSTONE_LAMP(18, Items.REDSTONE_LAMP.getDefaultInstance()),
    NOTE_BLOCK(19, Items.NOTE_BLOCK.getDefaultInstance()),
    LIGHTNING_ROD(20, Items.LIGHTNING_ROD.getDefaultInstance()),
    DAYLIGHT_DETECTOR(21, Items.DAYLIGHT_DETECTOR.getDefaultInstance()),
    CALIBRATED_SCULK_SENSOR(22, Items.CALIBRATED_SCULK_SENSOR.getDefaultInstance()),
    TRIPWIRE_HOOK(23, Items.TRIPWIRE_HOOK.getDefaultInstance()),
    TARGET(24, Items.TARGET.getDefaultInstance());

    private final Integer id;
    private final ItemStack output;

    RecipeEnum(Integer id, ItemStack output) {
        this.id = id;
        this.output = output;
    }

    public Integer getId() {
        return id;
    }

    public ItemStack getOutput() {
        return output;
    }
}
