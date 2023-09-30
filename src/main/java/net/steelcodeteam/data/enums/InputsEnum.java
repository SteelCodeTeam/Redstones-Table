package net.steelcodeteam.data.enums;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public enum InputsEnum {
    REDSTONE(0, Items.REDSTONE.getDefaultInstance()),
    STICK(1, Items.STICK.getDefaultInstance()),
    IRON_INGOT(2, Items.IRON_INGOT.getDefaultInstance()),
    GOLD_INGOT(3, Items.GOLD_INGOT.getDefaultInstance()),
    COPPER_INGOT(4, Items.COPPER_INGOT.getDefaultInstance()),
    AMETHYST_SHARD(5, Items.AMETHYST_SHARD.getDefaultInstance()),
    REDSTONE_TORCH(6, Items.SLIME_BALL.getDefaultInstance()),
    QUARTZ(7, Items.QUARTZ.getDefaultInstance()),
    STRING(8, Items.STRING.getDefaultInstance()),
    ACACIA_PLANKS(9, Items.ACACIA_PLANKS.getDefaultInstance()),
    STONE(10, Items.STONE.getDefaultInstance()),
    COBBLESTONE(11, Items.COBBLESTONE.getDefaultInstance()),
    GLASS(12, Items.GLASS.getDefaultInstance()),
    GLOWSTONE(13, Items.GLOWSTONE.getDefaultInstance()),
    HAY_BLOCK(14, Items.HAY_BLOCK.getDefaultInstance()),
    SCULK_SENSOR(15, Items.SCULK_SENSOR.getDefaultInstance());


    private final Integer id;
    private final ItemStack input;

    InputsEnum(Integer id, ItemStack input) {
        this.id = id;
        this.input = input;
    }

    public Integer getId() {
        return id;
    }

    public ItemStack getInput() {
        return input;
    }

    public static ItemStack getOutputForId(int id) {
        for (InputsEnum input : InputsEnum.values()) {
            if (input.getId() == id) {
                return input.getInput();
            }
        }
        return ItemStack.EMPTY;
    }
}
