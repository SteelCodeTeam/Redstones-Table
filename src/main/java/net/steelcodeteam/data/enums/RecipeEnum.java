package net.steelcodeteam.data.enums;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public enum RecipeEnum {
    REDSTONE_TORCH(0, Items.REDSTONE_TORCH.getDefaultInstance()),
    LEVER(1, Items.LEVER.getDefaultInstance()),
    REPEATER(2, Items.REPEATER.getDefaultInstance()),
    COMPARATOR(3, Items.COMPARATOR.getDefaultInstance()),
    OBSERVER(4, Items.OBSERVER.getDefaultInstance()),
    DISPENSER(5, Items.DISPENSER.getDefaultInstance()),
    DROPPER(6, Items.DROPPER.getDefaultInstance()),
    PISTON(7, Items.PISTON.getDefaultInstance()),
    STICKY_PISTON(8, Items.STICKY_PISTON.getDefaultInstance()),
    RAIL(9, Items.RAIL.getDefaultInstance()),
    POWERED_RAIL(10, Items.POWERED_RAIL.getDefaultInstance()),
    DETECTOR_RAIL(11, Items.DETECTOR_RAIL.getDefaultInstance()),
    HOPPER(12, Items.HOPPER.getDefaultInstance()),
    CLOCK(13, Items.CLOCK.getDefaultInstance()),
    COMPASS(14, Items.COMPASS.getDefaultInstance()),
    REDSTONE_LAMP(15, Items.REDSTONE_LAMP.getDefaultInstance()),
    NOTE_BLOCK(16, Items.NOTE_BLOCK.getDefaultInstance()),
    LIGHTNING_ROD(17, Items.LIGHTNING_ROD.getDefaultInstance()),
    DAYLIGHT_DETECTOR(18, Items.DAYLIGHT_DETECTOR.getDefaultInstance());

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
