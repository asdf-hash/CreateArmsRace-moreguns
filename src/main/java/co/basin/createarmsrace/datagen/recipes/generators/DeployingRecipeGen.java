package co.basin.createarmsrace.datagen.recipes.generators;

import co.basin.createarmsrace.Config;
import co.basin.createarmsrace.CreateArmsRace;
import co.basin.createarmsrace.items.ModItems;
import com.simibubi.create.AllRecipeTypes;
import com.simibubi.create.AllTags;
import com.simibubi.create.Create;
import com.simibubi.create.foundation.data.recipe.ProcessingRecipeGen;
import com.simibubi.create.foundation.utility.RegisteredObjects;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class DeployingRecipeGen extends ProcessingRecipeGen {

    public List<GeneratedRecipe> createAllDeployingRecipes() {
        List<GeneratedRecipe> recipes = new ArrayList<>();

        // Payloads
        for (int i = Config.weapon_types.length; i < ModItems.COMPLETED_SCHEMATICS.size(); i++) {
            final int index = i;
            Item output = ModItems.INCOMPLETE_PAYLOAD_ITEMS.get(index - Config.weapon_types.length).get();
            recipes.add(this.createWithDeferredId(idWithSuffix(output, "_from_schematic"),
                    (b) -> b.require(Ingredient.of(AllTags.forgeItemTag("plates/copper")))
                            .require(ModItems.COMPLETED_SCHEMATICS.get(index).get()).toolNotConsumed()
                            .output(output)));
        }

        // Casings
        for (int i = Config.weapon_types.length; i < ModItems.COMPLETED_SCHEMATICS.size(); i++) {
            final int index = i;
            Item output = ModItems.INCOMPLETE_CASING_ITEMS.get(index - Config.weapon_types.length).get();
            recipes.add(this.createWithDeferredId(idWithSuffix(output, "_from_schematic"),
                    (b) -> b.require(Ingredient.of(AllTags.forgeItemTag("plates/brass")))
                            .require(ModItems.COMPLETED_SCHEMATICS.get(index).get()).toolNotConsumed()
                            .output(output)));
        }

        return recipes;
    }

    public DeployingRecipeGen(PackOutput output) {
        super(output);
    }

    protected Supplier<ResourceLocation> idWithSuffix(Item item, String suffix) {
        return () -> {
            ResourceLocation registryName = RegisteredObjects.getKeyOrThrow(item);
            return new ResourceLocation(CreateArmsRace.MODID, registryName.getPath() + suffix);
        };
    }

    protected AllRecipeTypes getRecipeType() {
        return AllRecipeTypes.DEPLOYING;
    }
}

