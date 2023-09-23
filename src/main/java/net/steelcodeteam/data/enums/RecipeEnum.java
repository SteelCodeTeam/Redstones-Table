package net.steelcodeteam.data.enums;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public enum RecipeEnum {
    REDSTONE_TORCH(0, Items.REDSTONE_TORCH.getDefaultInstance()),
    OBSERVER(1, Items.OBSERVER.getDefaultInstance()),
    OBSERVER2(2, Items.OBSERVER.getDefaultInstance()),
    OBSERVER3(3, Items.OBSERVER.getDefaultInstance()),
    OBSERVER4(4, Items.OBSERVER.getDefaultInstance()),
    OBSERVER5(5, Items.OBSERVER.getDefaultInstance()),
    OBSERVER6(6, Items.OBSERVER.getDefaultInstance()),
    OBSERVER7(7, Items.OBSERVER.getDefaultInstance()),
    OBSERVER8(8, Items.OBSERVER.getDefaultInstance()),
    OBSERVER9(9, Items.OBSERVER.getDefaultInstance()),
    OBSERVER10(10, Items.OBSERVER.getDefaultInstance()),
    OBSERVER51(11, Items.OBSERVER.getDefaultInstance()),
    OBSERVER62(12, Items.OBSERVER.getDefaultInstance()),
    OBSERVER73(13, Items.OBSERVER.getDefaultInstance()),
    OBSERVER84(14, Items.OBSERVER.getDefaultInstance()),
    OBSERVER95(15, Items.OBSERVER.getDefaultInstance()),
    OBSERVER106(16, Items.OBSERVER.getDefaultInstance());




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
