package net.steelcodeteam.data.enums;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public enum RecipeEnum {
    R1(0, Items.REDSTONE_TORCH.getDefaultInstance()),
    R2(1, Items.OBSERVER.getDefaultInstance());


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
