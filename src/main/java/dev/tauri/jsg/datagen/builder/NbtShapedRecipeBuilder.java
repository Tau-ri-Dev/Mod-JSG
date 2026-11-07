package dev.tauri.jsg.datagen.builder;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.mojang.brigadier.StringReader;
import dev.tauri.jsgcore.mapping.JSGMapping;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.CriterionTriggerInstance;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.TagParser;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.Nullable;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.*;
import java.util.function.Consumer;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class NbtShapedRecipeBuilder extends ShapedRecipeBuilder {
    private final ItemStack resultStack;
    private final List<String> rows = new ArrayList<>();
    private final Map<Character, Ingredient> key = new LinkedHashMap<>();
    private final Advancement.Builder advancement = Advancement.Builder.advancement();
    private String group = "";

    public NbtShapedRecipeBuilder(RecipeCategory category, ItemStack resultStack, int count) {
        super(category, resultStack.getItem(), count);
        this.resultStack = resultStack.copy();
        this.resultStack.setCount(count);
    }

    public static NbtShapedRecipeBuilder shaped(RecipeCategory category, ItemStack resultStack, int count) {
        return new NbtShapedRecipeBuilder(category, resultStack, count);
    }

    @Override
    public NbtShapedRecipeBuilder define(Character symbol, TagKey<Item> tag) {
        super.define(symbol, tag);
        this.key.put(symbol, Ingredient.of(tag));
        return this;
    }

    @Override
    public NbtShapedRecipeBuilder define(Character symbol, ItemLike item) {
        super.define(symbol, item);
        this.key.put(symbol, Ingredient.of(item));
        return this;
    }

    @Override
    public NbtShapedRecipeBuilder define(Character symbol, Ingredient ingredient) {
        super.define(symbol, ingredient);
        this.key.put(symbol, ingredient);
        return this;
    }

    @Override
    public NbtShapedRecipeBuilder pattern(String patternString) {
        super.pattern(patternString);
        this.rows.add(patternString);
        return this;
    }

    @Override
    public NbtShapedRecipeBuilder unlockedBy(String name, CriterionTriggerInstance trigger) {
        super.unlockedBy(name, trigger);
        this.advancement.addCriterion(name, trigger);
        return this;
    }

    @Override
    public NbtShapedRecipeBuilder group(@Nullable String groupName) {
        super.group(groupName);
        this.group = groupName == null ? "" : groupName;
        return this;
    }

    @Override
    public void save(Consumer<FinishedRecipe> finishedRecipeConsumer, ResourceLocation recipeId) {
        ResourceLocation advancementId = JSGMapping.rl(recipeId.getNamespace(), "recipes/" + recipeId.getPath());

        finishedRecipeConsumer.accept(new Result(
                recipeId,
                this.resultStack,
                this.rows,
                this.key,
                this.group,
                this.advancement,
                advancementId
        ));
    }

    public static class Result implements FinishedRecipe {
        private final ResourceLocation id;
        private final ItemStack resultStack;
        private final List<String> pattern;
        private final Map<Character, Ingredient> key;
        private final String group;
        private final Advancement.Builder advancement;
        private final ResourceLocation advancementId;

        public Result(ResourceLocation id, ItemStack resultStack, List<String> pattern,
                      Map<Character, Ingredient> key, String group,
                      Advancement.Builder advancement, ResourceLocation advancementId) {
            this.id = id;
            this.resultStack = resultStack;
            this.pattern = pattern;
            this.key = key;
            this.group = group;
            this.advancement = advancement;
            this.advancementId = advancementId;
        }

        @Override
        public void serializeRecipeData(JsonObject json) {
            if (!this.group.isEmpty()) {
                json.addProperty("group", this.group);
            }

            JsonArray jsonArray = new JsonArray();
            for (String s : this.pattern) {
                jsonArray.add(s);
            }
            json.add("pattern", jsonArray);

            JsonObject jsonObject = new JsonObject();
            for (Map.Entry<Character, Ingredient> entry : this.key.entrySet()) {
                jsonObject.add(String.valueOf(entry.getKey()), entry.getValue().toJson());
            }
            json.add("key", jsonObject);

            JsonObject resultObj = new JsonObject();
            resultObj.addProperty("item", Objects.requireNonNull(ForgeRegistries.ITEMS.getKey(resultStack.getItem())).toString());
            resultObj.addProperty("count", resultStack.getCount());

            if (resultStack.hasTag()) {
                try {
                    StringReader reader = new StringReader(Objects.requireNonNull(resultStack.getTag()).toString());
                    CompoundTag parsedTag = new TagParser(reader).readStruct();
                    JsonObject nbtJson = com.google.gson.JsonParser.parseString(parsedTag.toString()).getAsJsonObject();
                    resultObj.add("nbt", nbtJson);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            json.add("result", resultObj);
        }

        @Override
        public ResourceLocation getId() {
            return this.id;
        }

        @Override
        public RecipeSerializer<?> getType() {
            return RecipeSerializer.SHAPED_RECIPE;
        }

        @Override
        public @Nullable JsonObject serializeAdvancement() {
            return this.advancement.serializeToJson();
        }

        @Override
        public @Nullable ResourceLocation getAdvancementId() {
            return this.advancementId;
        }
    }
}
