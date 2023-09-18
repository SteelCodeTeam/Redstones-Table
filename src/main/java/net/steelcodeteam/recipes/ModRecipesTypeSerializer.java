package net.steelcodeteam.recipes;

import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.steelcodeteam.RedstonesTable;

public class ModRecipesTypeSerializer {
    public static final DeferredRegister<RecipeType<?>> RECIPE_TYPE =
            DeferredRegister.create(ForgeRegistries.RECIPE_TYPES, RedstonesTable.MODID);
    public static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZERS =
            DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, RedstonesTable.MODID);

    public static final RegistryObject<RecipeSerializer<RedstoneTableRecipe>> REDSTONE_TABLE_RECIPE_SERIALIZER
            = RECIPE_SERIALIZERS.register("redstone_table_craft", () -> RedstoneTableRecipe.Serializer.INSTANCE);



    public static void register(IEventBus eventBus) {
        RECIPE_SERIALIZERS.register(eventBus);

    }
}
