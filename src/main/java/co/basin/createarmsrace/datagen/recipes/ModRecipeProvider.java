package co.basin.createarmsrace.datagen.recipes;

import co.basin.createarmsrace.CreateArmsRace;
import co.basin.createarmsrace.blocks.ModBlocks;
import co.basin.createarmsrace.datagen.recipes.generators.*;
import co.basin.createarmsrace.items.ModItems;
import com.simibubi.create.foundation.data.recipe.CreateRecipeProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.AbstractCookingRecipe;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.crafting.conditions.IConditionBuilder;
import net.minecraftforge.registries.RegistryObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;

public class ModRecipeProvider extends RecipeProvider implements IConditionBuilder {
    protected PackOutput packOutput;
    protected final List<CreateRecipeProvider.GeneratedRecipe> all = new ArrayList<>();
    private final List<ItemLike> LEAD_SMELTABLES = List.of(
                ModItems.RAW_LEAD.get(),
                ModBlocks.LEAD_ORE.get(),
                ModBlocks.DEEPSLATE_LEAD_ORE.get()
            );

    private final List<ItemLike> BAUXITE_SMELTABLES = List.of(
            ModItems.RAW_BAUXITE.get(),
            ModBlocks.BAUXITE_ORE.get(),
            ModBlocks.DEEPSLATE_BAUXITE_ORE.get()
    );

    public ModRecipeProvider(PackOutput packOutput) {
        super(packOutput);
        this.packOutput = packOutput;
    }

    @Override
    protected void buildRecipes(Consumer<FinishedRecipe> consumer) {
        oreBlasting(consumer, LEAD_SMELTABLES, RecipeCategory.MISC, ModItems.LEAD_INGOT.get(), 0.25f, 100, "lead");
        oreSmelting(consumer, LEAD_SMELTABLES, RecipeCategory.MISC, ModItems.LEAD_INGOT.get(), 0.25f, 200, "lead");
        oreBlasting(consumer, BAUXITE_SMELTABLES, RecipeCategory.MISC, ModItems.ALUMINUM_INGOT.get(), 0.25f, 100, "bauxite");
        oreSmelting(consumer, BAUXITE_SMELTABLES, RecipeCategory.MISC, ModItems.ALUMINUM_INGOT.get(), 0.25f, 200, "bauxite");

        for (RegistryObject<Item> item : ModItems.COMPLETED_SCHEMATICS) {
            ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, item.get(), 2)
                    .requires(item.get())
                    .requires(Items.PAPER)
                    .unlockedBy(getHasName(item.get()), has(item.get()))
                    .group("schematic_copying")
                    .save(consumer, item.getId() + "_copying");
        }

        all.addAll(new SequencedAssemblyRecipeGen(packOutput).createAllSequencedAssemblyRecipes());
        all.addAll(new MechanicalCraftingRecipeGen(packOutput).createAllMechanicalCraftingRecipes());
        all.addAll(new PressingRecipeGen(packOutput).createAllPressingRecipes());
        all.addAll(new CompactingRecipeGen(packOutput).createAllCompactingRecipes());
        all.addAll(new DeployingRecipeGen(packOutput).createAllDeployingRecipes());
        all.forEach(c -> c.register(consumer));
    }

    protected static void oreSmelting(Consumer<FinishedRecipe> p_250654_, List<ItemLike> p_250172_, RecipeCategory p_250588_, ItemLike p_251868_, float p_250789_, int p_252144_, String p_251687_) {
        oreCooking(p_250654_, RecipeSerializer.SMELTING_RECIPE, p_250172_, p_250588_, p_251868_, p_250789_, p_252144_, p_251687_, "_from_smelting");
    }

    protected static void oreBlasting(Consumer<FinishedRecipe> p_248775_, List<ItemLike> p_251504_, RecipeCategory p_248846_, ItemLike p_249735_, float p_248783_, int p_250303_, String p_251984_) {
        oreCooking(p_248775_, RecipeSerializer.BLASTING_RECIPE, p_251504_, p_248846_, p_249735_, p_248783_, p_250303_, p_251984_, "_from_blasting");
    }

    protected static void oreCooking(Consumer<FinishedRecipe> p_250791_, RecipeSerializer<? extends AbstractCookingRecipe> p_251817_, List<ItemLike> p_249619_, RecipeCategory p_251154_, ItemLike p_250066_, float p_251871_, int p_251316_, String p_251450_, String p_249236_) {
        Iterator var9 = p_249619_.iterator();

        while(var9.hasNext()) {
            ItemLike itemlike = (ItemLike)var9.next();
            SimpleCookingRecipeBuilder.generic(Ingredient.of(new ItemLike[]{itemlike}), p_251154_, p_250066_, p_251871_, p_251316_, p_251817_).group(p_251450_).unlockedBy(getHasName(itemlike), has(itemlike))
                    .save(p_250791_, CreateArmsRace.MODID + ":" + (p_250066_) + p_249236_ + "_" + getItemName(itemlike));
        }

    }

    public static ResourceLocation resource(String name) {
        return new ResourceLocation(CreateArmsRace.MODID, name);
    }
}
