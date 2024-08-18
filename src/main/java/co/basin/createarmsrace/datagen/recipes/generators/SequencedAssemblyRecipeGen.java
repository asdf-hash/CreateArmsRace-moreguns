package co.basin.createarmsrace.datagen.recipes.generators;

import co.basin.createarmsrace.Config;
import co.basin.createarmsrace.datagen.recipes.ModRecipeProvider;
import co.basin.createarmsrace.items.ModItems;
import com.simibubi.create.AllTags;
import com.simibubi.create.content.kinetics.deployer.DeployerApplicationRecipe;
import com.simibubi.create.content.kinetics.press.PressingRecipe;
import com.simibubi.create.content.processing.recipe.ProcessingRecipe;
import com.simibubi.create.content.processing.recipe.ProcessingRecipeBuilder;
import com.simibubi.create.content.processing.sequenced.SequencedAssemblyRecipeBuilder;
import com.simibubi.create.foundation.data.recipe.CreateRecipeProvider;
import com.tacz.guns.api.item.builder.AmmoItemBuilder;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.common.data.LanguageProvider;

import java.util.ArrayList;
import java.util.List;
import java.util.function.UnaryOperator;

public class SequencedAssemblyRecipeGen extends ModRecipeProvider {
    public SequencedAssemblyRecipeGen(PackOutput output) {
        super(output);
    }
    public List<CreateRecipeProvider.GeneratedRecipe> createAllSequencedAssemblyRecipes() {
        List<CreateRecipeProvider.GeneratedRecipe> recipes = new ArrayList<>();

        // Casing + Gunpowder + Payload = Ammo
        for (int i = 0; i < Config.ammo_types.length; i++) {
            final int index = i;
            ItemStack output = AmmoItemBuilder.create().setId(new ResourceLocation("tacz", Config.ammo_types[index])).build();
            recipes.add(create(Config.ammo_types[i], b -> {
                b.require(ModItems.CASING_ITEMS.get(index).get())
                        .transitionTo(ModItems.INCOMPLETE_CASING_ITEMS.get(index).get())
                        .addOutput(output, 100)
                        .loops(1);

                return addSteps(b, DeployerApplicationRecipe::new, rb -> rb.require(Items.GUNPOWDER), Config.gunpowder_amounts[index])
                        .addStep(DeployerApplicationRecipe::new, rb -> rb.require(ModItems.PAYLOAD_ITEMS.get(index).get()));
            }));
        }

        // Casing Recipes
        for (int i = 0; i < Config.ammo_types.length; i++) {
            final int index = i;
            ItemStack output = new ItemStack(ModItems.CASING_ITEMS.get(index).get());
            if (Config.ammo_amounts[i] == 0) { continue; }
            output.setCount(Config.ammo_amounts[i]);
            recipes.add(create(Config.ammo_types[i] + "_casing", b ->
                    //b.require(Ingredient.of(AllTags.forgeItemTag("plates/brass")))
                    b.require(ModItems.INCOMPLETE_CASING_ITEMS.get(index).get())
                    .transitionTo(ModItems.INCOMPLETE_CASING_ITEMS.get(index).get())
                    .addOutput(output, 100)
                    .addStep(PressingRecipe::new, rb -> rb)
                    .loops(1)));
        }

        // Payload Recipes
        for (int i = 0; i < Config.ammo_types.length; i++) {
            final int index = i;
            ItemStack output = new ItemStack(ModItems.PAYLOAD_ITEMS.get(index).get());
            if (Config.ammo_amounts[index] == 0) { continue; }
            output.setCount(Config.ammo_amounts[index]);
            recipes.add(create(Config.ammo_types[i] + "_payload", b ->
                    b.require(ModItems.INCOMPLETE_PAYLOAD_ITEMS.get(index).get())
                            .transitionTo(ModItems.INCOMPLETE_PAYLOAD_ITEMS.get(index).get())
                            .addOutput(output, 100)
                            .addStep(PressingRecipe::new, rb -> rb)
                            .addStep(DeployerApplicationRecipe::new, rb -> rb.require(Ingredient.of(AllTags.forgeItemTag("nuggets/lead"))))
                            .addStep(PressingRecipe::new, rb -> rb)
                            .loops(3)));
        }
        return recipes;
    }

    private <T extends ProcessingRecipe<?>> SequencedAssemblyRecipeBuilder addSteps(SequencedAssemblyRecipeBuilder b, ProcessingRecipeBuilder.ProcessingRecipeFactory<T> factory, UnaryOperator<ProcessingRecipeBuilder<T>> builder, int n) {
        if (n == 0) { return b; }
        b.addStep(factory, builder);
        return addSteps(b, factory, builder, n - 1);
    }

    protected CreateRecipeProvider.GeneratedRecipe create(String name, UnaryOperator<SequencedAssemblyRecipeBuilder> transform) {
        CreateRecipeProvider.GeneratedRecipe recipe =
                c -> transform.apply(new SequencedAssemblyRecipeBuilder(new ResourceLocation("createarmsrace", name)))
                        .build(c);
        all.add(recipe);
        return recipe;
    }
}
