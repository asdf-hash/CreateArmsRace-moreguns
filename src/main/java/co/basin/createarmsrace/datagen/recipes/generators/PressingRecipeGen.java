package co.basin.createarmsrace.datagen.recipes.generators;

import com.simibubi.create.AllRecipeTypes;
import com.simibubi.create.foundation.data.recipe.CreateRecipeProvider;
import com.simibubi.create.foundation.data.recipe.ProcessingRecipeGen;
import net.minecraft.data.PackOutput;

import java.util.ArrayList;
import java.util.List;

public class PressingRecipeGen extends ProcessingRecipeGen {

    public List<GeneratedRecipe> createAllPressingRecipes() {
        List<CreateRecipeProvider.GeneratedRecipe> recipes = new ArrayList<>();
//        recipes.add(this.create("brass_ingot", (b) -> {
//            return b.require(I.brass()).output((ItemLike)AllItems.BRASS_SHEET.get());
//        }))
        return recipes;
    }

    public PressingRecipeGen(PackOutput output) {
        super(output);
    }

    protected AllRecipeTypes getRecipeType() {
        return AllRecipeTypes.PRESSING;
    }
}

