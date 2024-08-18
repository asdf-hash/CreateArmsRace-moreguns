package co.basin.createarmsrace.datagen.recipes.generators;

import com.simibubi.create.AllRecipeTypes;
import com.simibubi.create.foundation.data.recipe.CreateRecipeProvider;
import com.simibubi.create.foundation.data.recipe.ProcessingRecipeGen;
import net.minecraft.data.PackOutput;

import java.util.ArrayList;
import java.util.List;

public class CompactingRecipeGen extends ProcessingRecipeGen {

    public List<GeneratedRecipe> createAllCompactingRecipes() {
        List<CreateRecipeProvider.GeneratedRecipe> recipes = new ArrayList<>();

//        for (int i = 0; i < Config.ammo_types.length; i++) {
//            final int index = i;
//            ItemStack output = new ItemStack(ModItems.PAYLOAD_ITEMS.get(index).get());
//            if (Config.ammo_amounts[index] == 0) { continue; }
//            output.setCount(Config.ammo_amounts[index]);
//            recipes.add(create(ModRecipeProvider.resource(Config.ammo_types[index] + "_payload"), b ->
//                    b.require(Ingredient.of(AllTags.forgeItemTag("plates/copper")))
//                            .require(Ingredient.of(AllTags.forgeItemTag("nuggets/lead")))
//                            .require(Ingredient.of(AllTags.forgeItemTag("nuggets/lead")))
//                            .require(Ingredient.of(AllTags.forgeItemTag("nuggets/lead")))
//                            .output(output)));
//        }

        return recipes;
    }

    public CompactingRecipeGen(PackOutput output) {
        super(output);
    }

    protected AllRecipeTypes getRecipeType() {
        return AllRecipeTypes.COMPACTING;
    }
}

