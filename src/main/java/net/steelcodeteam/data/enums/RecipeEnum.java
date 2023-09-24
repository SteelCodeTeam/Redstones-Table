package net.steelcodeteam.data.enums;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public enum RecipeEnum {
    REDSTONE_TORCH(0, Items.REDSTONE_TORCH.getDefaultInstance()),
    REPEATER(1, Items.REPEATER.getDefaultInstance()),
    COMPARATOR(2, Items.COMPARATOR.getDefaultInstance()),
    OBSERVER(3, Items.OBSERVER.getDefaultInstance()),
    DISPENSER(4, Items.DISPENSER.getDefaultInstance()),
    DROPPER(5, Items.DROPPER.getDefaultInstance()),
    PISTON(6, Items.PISTON.getDefaultInstance()),
    STICKY_PISTON(7, Items.STICKY_PISTON.getDefaultInstance()),
    RAIL(8, Items.RAIL.getDefaultInstance()),
    POWERED_RAIL(9, Items.POWERED_RAIL.getDefaultInstance()),
    DETECTOR_RAIL(10, Items.DETECTOR_RAIL.getDefaultInstance()),
    HOPPER(11, Items.HOPPER.getDefaultInstance()),
    CLOCK(12, Items.CLOCK.getDefaultInstance()),
    COMPASS(13, Items.COMPASS.getDefaultInstance()),
    REDSTONE_LAMP(14, Items.REDSTONE_LAMP.getDefaultInstance()),
    NOTE_BLOCK(15, Items.NOTE_BLOCK.getDefaultInstance()),
    LIGHTNING_ROD(16, Items.LIGHTNING_ROD.getDefaultInstance());

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
