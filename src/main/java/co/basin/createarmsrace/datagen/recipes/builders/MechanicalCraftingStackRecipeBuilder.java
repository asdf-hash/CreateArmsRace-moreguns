//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package co.basin.createarmsrace.datagen.recipes.builders;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.simibubi.create.AllRecipeTypes;
import com.simibubi.create.foundation.utility.RegisteredObjects;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;
import javax.annotation.Nullable;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.common.crafting.conditions.ICondition;
import net.minecraftforge.common.crafting.conditions.ModLoadedCondition;
import net.minecraftforge.common.crafting.conditions.NotCondition;

public class MechanicalCraftingStackRecipeBuilder {
    private final ItemStack result;
    private final int count;
    private final List<String> pattern = Lists.newArrayList();
    private final Map<Character, Ingredient> key = Maps.newLinkedHashMap();
    private boolean acceptMirrored;
    private List<ICondition> recipeConditions;

    public MechanicalCraftingStackRecipeBuilder(ItemStack p_i48261_1_, int p_i48261_2_) {
        this.result = p_i48261_1_;
        this.count = p_i48261_2_;
        this.acceptMirrored = true;
        this.recipeConditions = new ArrayList();
    }

    public static MechanicalCraftingStackRecipeBuilder shapedRecipe(ItemStack p_200470_0_) {
        return shapedRecipe(p_200470_0_, 1);
    }

    public static MechanicalCraftingStackRecipeBuilder shapedRecipe(ItemStack p_200468_0_, int p_200468_1_) {
        return new MechanicalCraftingStackRecipeBuilder(p_200468_0_, p_200468_1_);
    }

    public MechanicalCraftingStackRecipeBuilder key(Character p_200469_1_, TagKey<Item> p_200469_2_) {
        return this.key(p_200469_1_, Ingredient.of(p_200469_2_));
    }

    public MechanicalCraftingStackRecipeBuilder key(Character p_200462_1_, ItemLike p_200462_2_) {
        return this.key(p_200462_1_, Ingredient.of(new ItemLike[]{p_200462_2_}));
    }

    public MechanicalCraftingStackRecipeBuilder key(Character p_200471_1_, Ingredient p_200471_2_) {
        if (this.key.containsKey(p_200471_1_)) {
            throw new IllegalArgumentException("Symbol '" + p_200471_1_ + "' is already defined!");
        } else if (p_200471_1_ == ' ') {
            throw new IllegalArgumentException("Symbol ' ' (whitespace) is reserved and cannot be defined");
        } else {
            this.key.put(p_200471_1_, p_200471_2_);
            return this;
        }
    }

    public MechanicalCraftingStackRecipeBuilder patternLine(String p_200472_1_) {
        if (!this.pattern.isEmpty() && p_200472_1_.length() != ((String)this.pattern.get(0)).length()) {
            throw new IllegalArgumentException("Pattern must be the same width on every line!");
        } else {
            this.pattern.add(p_200472_1_);
            return this;
        }
    }

    public MechanicalCraftingStackRecipeBuilder disallowMirrored() {
        this.acceptMirrored = false;
        return this;
    }

    public void build(Consumer<FinishedRecipe> p_200464_1_) {
        this.build(p_200464_1_, RegisteredObjects.getKeyOrThrow(this.result.getItem()));
    }

    public void build(Consumer<FinishedRecipe> p_200466_1_, String p_200466_2_) {
        ResourceLocation resourcelocation = RegisteredObjects.getKeyOrThrow(this.result.getItem());
        if ((new ResourceLocation(p_200466_2_)).equals(resourcelocation)) {
            throw new IllegalStateException("Shaped Recipe " + p_200466_2_ + " should remove its 'save' argument");
        } else {
            this.build(p_200466_1_, new ResourceLocation(p_200466_2_));
        }
    }

    public void build(Consumer<FinishedRecipe> p_200467_1_, ResourceLocation p_200467_2_) {
        this.validate(p_200467_2_);
        p_200467_1_.accept(new Result(p_200467_2_, this.result, this.count, this.pattern, this.key, this.acceptMirrored, this.recipeConditions));
    }

    private void validate(ResourceLocation p_200463_1_) {
        if (this.pattern.isEmpty()) {
            throw new IllegalStateException("No pattern is defined for shaped recipe " + p_200463_1_ + "!");
        } else {
            Set<Character> set = Sets.newHashSet(this.key.keySet());
            set.remove(' ');
            Iterator var3 = this.pattern.iterator();

            while(var3.hasNext()) {
                String s = (String)var3.next();

                for(int i = 0; i < s.length(); ++i) {
                    char c0 = s.charAt(i);
                    if (!this.key.containsKey(c0) && c0 != ' ') {
                        throw new IllegalStateException("Pattern in recipe " + p_200463_1_ + " uses undefined symbol '" + c0 + "'");
                    }

                    set.remove(c0);
                }
            }

            if (!set.isEmpty()) {
                throw new IllegalStateException("Ingredients are defined but not used in pattern for recipe " + p_200463_1_);
            }
        }
    }

    public MechanicalCraftingStackRecipeBuilder whenModLoaded(String modid) {
        return this.withCondition(new ModLoadedCondition(modid));
    }

    public MechanicalCraftingStackRecipeBuilder whenModMissing(String modid) {
        return this.withCondition(new NotCondition(new ModLoadedCondition(modid)));
    }

    public MechanicalCraftingStackRecipeBuilder withCondition(ICondition condition) {
        this.recipeConditions.add(condition);
        return this;
    }

    public class Result implements FinishedRecipe {
        private final ResourceLocation id;
        private final ItemStack result;
        private final int count;
        private final List<String> pattern;
        private final Map<Character, Ingredient> key;
        private final boolean acceptMirrored;
        private List<ICondition> recipeConditions;

        public Result(ResourceLocation p_i48271_2_, ItemStack p_i48271_3_, int p_i48271_4_, List<String> p_i48271_6_, Map<Character, Ingredient> p_i48271_7_, boolean asymmetrical, List<ICondition> recipeConditions) {
            this.id = p_i48271_2_;
            this.result = p_i48271_3_;
            this.count = p_i48271_4_;
            this.pattern = p_i48271_6_;
            this.key = p_i48271_7_;
            this.acceptMirrored = asymmetrical;
            this.recipeConditions = recipeConditions;
        }

        public void serializeRecipeData(JsonObject p_218610_1_) {
            JsonArray jsonarray = new JsonArray();
            Iterator var3 = this.pattern.iterator();

            while(var3.hasNext()) {
                String s = (String)var3.next();
                jsonarray.add(s);
            }

            p_218610_1_.add("pattern", jsonarray);
            JsonObject jsonobject = new JsonObject();
            Iterator var7 = this.key.entrySet().iterator();

            while(var7.hasNext()) {
                Map.Entry<Character, Ingredient> entry = (Map.Entry)var7.next();
                jsonobject.add(String.valueOf(entry.getKey()), ((Ingredient)entry.getValue()).toJson());
            }

            p_218610_1_.add("key", jsonobject);
            JsonObject jsonobject1 = new JsonObject();
            jsonobject1.addProperty("item", RegisteredObjects.getKeyOrThrow(this.result.getItem()).toString());

            if (this.result.hasTag()) {
                JsonObject jsonObject2 = new JsonObject();
                for (String key : this.result.getOrCreateTag().getAllKeys()) {
                    jsonObject2.addProperty(key, this.result.getOrCreateTag().get(key).getAsString());
                }
                jsonobject1.add("nbt", jsonObject2);
            }

            if (this.count > 1) {
                jsonobject1.addProperty("count", this.count);
            }

            p_218610_1_.add("result", jsonobject1);
            p_218610_1_.addProperty("acceptMirrored", this.acceptMirrored);
            if (!this.recipeConditions.isEmpty()) {
                JsonArray conds = new JsonArray();
                this.recipeConditions.forEach((c) -> {
                    conds.add(CraftingHelper.serialize(c));
                });
                p_218610_1_.add("conditions", conds);
            }
        }

        public RecipeSerializer<?> getType() {
            return AllRecipeTypes.MECHANICAL_CRAFTING.getSerializer();
        }

        public ResourceLocation getId() {
            return this.id;
        }

        @Nullable
        public JsonObject serializeAdvancement() {
            return null;
        }

        @Nullable
        public ResourceLocation getAdvancementId() {
            return null;
        }
    }
}
