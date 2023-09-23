package net.steelcodeteam.recipes;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import net.steelcodeteam.RedstonesTable;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

public class RedstoneTableRecipe implements Recipe<SimpleContainer> {
    private final ResourceLocation id;
    private final ItemStack output;
    private final NonNullList<Ingredient> recipeItems;

    public RedstoneTableRecipe(ResourceLocation id, ItemStack output, NonNullList<Ingredient> recipeItems) {
        this.id = id;
        this.output = output;
        this.recipeItems = recipeItems;
    }

    @Override
    public boolean matches(SimpleContainer container, Level level) {
        if (level.isClientSide()) {
            return false;
        }

        List<ItemStack> recipeItemStack = new ArrayList<>();

        List<ItemStack> containerItemStack = new ArrayList<>();

        for (int i = 0; i < container.getContainerSize(); i++) {
            containerItemStack.add(container.getItem(i));
        }

        List<ItemStack[]> opt = recipeItems.stream().map(Ingredient::getItems).toList();

        for (ItemStack[] stacks : opt) {
            recipeItemStack.addAll(Arrays.asList(stacks));
        }

        return containerItemStack.stream().map(itemStack -> itemStack.getItem()).toList().containsAll(recipeItemStack.stream().map(itemStack -> itemStack.getItem()).toList());
/*
        boolean[] itsAll = new boolean[recipeItems.size()];
        Arrays.fill(itsAll, false);
        int index=0;
        for (Ingredient ingredient : recipeItems) {
            for (int i = 0; i < container.getContainerSize(); i++) {

                if (ingredient.test(container.getItem(i).copy())) {
                    itsAll[index] = true;
                }

            }
            index++;
        }
        for (boolean b : itsAll) {
            if (!b) {
                return false;
            }
        }
        return true;*/
    }

    @Override
    public ItemStack assemble(SimpleContainer container, RegistryAccess access) {
        return output.copy();
    }

    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return true;
    }

    @Override
    public ItemStack getResultItem(RegistryAccess access) {
        return output.copy();
    }

    public ItemStack getResultItem() {
        return output.copy();
    }


    @Override
    public ResourceLocation getId() {
        return id;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return Serializer.INSTANCE;
    }

    @Override
    public RecipeType<?> getType() {
        return Type.INSTANCE;
    }

    public static class Type implements RecipeType<RedstoneTableRecipe> {
        private Type() {}

        public static final Type INSTANCE = new Type();

        public static final String ID = "redstone_table_recipe_type";
    }

    public static class Serializer implements RecipeSerializer<RedstoneTableRecipe> {
        public static final Serializer INSTANCE = new Serializer();
        public static final ResourceLocation ID = new ResourceLocation(RedstonesTable.MOD_ID, "redstone_table_recipe");


        @Override
        public RedstoneTableRecipe fromJson(ResourceLocation location, JsonObject json) {
            ItemStack output = ShapedRecipe.itemStackFromJson(GsonHelper.getAsJsonObject(json, "output"));//
            JsonArray ingredients = GsonHelper.getAsJsonArray(json, "ingredients");

            NonNullList<Ingredient> inputs = NonNullList.withSize(ingredients.size(), Ingredient.EMPTY);
            for (int i = 0; i < inputs.size(); i++) {
                inputs.set(i, Ingredient.fromJson(ingredients.get(i)));
            }
            return new RedstoneTableRecipe(ID.withSuffix("_" + location.getPath()), output, inputs);
        }

        @Override
        public @Nullable RedstoneTableRecipe fromNetwork(ResourceLocation location, FriendlyByteBuf buf) {
            NonNullList<Ingredient> inputs = NonNullList.withSize(buf.readInt(), Ingredient.EMPTY);

            for (int i = 0; i < inputs.size(); i++) {
                inputs.set(i, Ingredient.fromNetwork(buf));
            }

            ItemStack output = buf.readItem();
            return new RedstoneTableRecipe(ID.withSuffix("_" + location.getPath()), output, inputs);
        }

        @Override
        public void toNetwork(FriendlyByteBuf buf, RedstoneTableRecipe recipe) {
            buf.writeInt(recipe.getIngredients().size());

            for (Ingredient ing : recipe.getIngredients()) {
                ing.toNetwork(buf);
            }

            buf.writeItemStack(recipe.getResultItem(), false);
        }
    }
}
