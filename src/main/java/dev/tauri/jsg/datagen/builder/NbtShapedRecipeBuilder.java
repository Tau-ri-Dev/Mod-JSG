package dev.tauri.jsg.datagen.builder;

import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;

import javax.annotation.ParametersAreNonnullByDefault;

/**
 * On 1.21.1 version the {@link ShapedRecipeBuilder} support ItemStack as a result - meaning this whole class is useless
 */
@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class NbtShapedRecipeBuilder extends ShapedRecipeBuilder {
    public NbtShapedRecipeBuilder(RecipeCategory category, ItemLike result, int count) {
        super(category, result, count);
    }

    public NbtShapedRecipeBuilder(RecipeCategory p_249996_, ItemStack result) {
        super(p_249996_, result);
    }

    public static ShapedRecipeBuilder shaped(RecipeCategory category, ItemStack result, int count) {
        return new ShapedRecipeBuilder(category, result.copyWithCount(count));
    }
}
