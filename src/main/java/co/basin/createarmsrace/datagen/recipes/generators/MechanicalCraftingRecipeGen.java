package co.basin.createarmsrace.datagen.recipes.generators;

import co.basin.createarmsrace.Config;
import co.basin.createarmsrace.CreateArmsRace;
import co.basin.createarmsrace.datagen.recipes.builders.MechanicalCraftingStackRecipeBuilder;
import co.basin.createarmsrace.items.ModItems;
import com.simibubi.create.AllItems;
import com.simibubi.create.AllTags;
import com.simibubi.create.foundation.data.recipe.CreateRecipeProvider;
import com.simibubi.create.foundation.utility.RegisteredObjects;
import com.tacz.guns.api.item.builder.AttachmentItemBuilder;
import com.tacz.guns.item.AttachmentItem;
import com.tacz.guns.resource.CommonGunPackLoader;
import net.minecraft.data.PackOutput;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.common.Tags;

import java.util.*;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;

import static com.tacz.guns.init.ModItems.MODERN_KINETIC_GUN;

public class MechanicalCraftingRecipeGen extends CreateRecipeProvider {
    public MechanicalCraftingRecipeGen(PackOutput output) {
        super(output);
    }

    public List<GeneratedRecipe> createAllMechanicalCraftingRecipes() {
        List<CreateRecipeProvider.GeneratedRecipe> recipes = new ArrayList<>();
//        for (int i = 0; i < Config.weapon_types.length; i++) {
//            final int index = i;
//            ItemStack stack = new ItemStack(MODERN_KINETIC_GUN.get());
//            CompoundTag tag = stack.getOrCreateTag();
//            tag.putString("GunId", "tacz:" + Config.weapon_types[index]);
//            tag.putBoolean("HasBulletInBarrel", false);
//            tag.putString("GunFireMode", Config.firing_modes[index]);
//            tag.putInt("GunCurrentAmmoCount", 0);
//            recipes.add(create(() -> stack).returns(1)
//                    .withSuffix("_" + Config.weapon_types[index])
//                    .recipe(b -> b
//                            .key('P', Ingredient.of(AllItems.PRECISION_MECHANISM))
//                            .key('C', Ingredient.of(ModItems.COMPLETED_SCHEMATICS.get(index).get()))
//                            .key('A', Ingredient.of(AllTags.forgeItemTag("plates/aluminum")))
//                            .key('S', Ingredient.of(AllTags.forgeItemTag("plates/steel")))
//                            .patternLine("  AA  ")
//                            .patternLine("SSSCSS")
//                            .patternLine("  S PS")
//                    )
//            );
//        }

//        for (int i = 0; i < Config.attachment_types.length; i++) {
//            final int index = i;
//            ItemStack attachment = AttachmentItemBuilder.create().setId(new ResourceLocation("tacz", Config.attachment_types[i])).build();
//            recipes.add(create(() -> attachment).returns(1)
//                    .withSuffix("_" + Config.attachment_types[index])
//                    .recipe(b -> b
//                            .key('S', Ingredient.of(AllTags.forgeItemTag("plates/steel")))
//                            .key('G', Ingredient.of(Tags.Items.GLASS))
//                            .patternLine("SSS")
//                            .patternLine("SGS")
//                            .patternLine("SSS")
//                    )
//            );
//        }
        return recipes;
    }

    GeneratedRecipeBuilder create(Supplier<ItemStack> result) {
        return new GeneratedRecipeBuilder(result);
    }

    class GeneratedRecipeBuilder {

        private String suffix;
        private Supplier<ItemStack> result;
        private int amount;

        public GeneratedRecipeBuilder(Supplier<ItemStack> result) {
            this.suffix = "";
            this.result = result;
            this.amount = 1;
        }

        GeneratedRecipeBuilder returns(int amount) {
            this.amount = amount;
            return this;
        }

        GeneratedRecipeBuilder withSuffix(String suffix) {
            this.suffix = suffix;
            return this;
        }

        public GeneratedRecipe recipe(UnaryOperator<MechanicalCraftingStackRecipeBuilder> builder) {
            return register(consumer -> {
                MechanicalCraftingStackRecipeBuilder b =
                        builder.apply(MechanicalCraftingStackRecipeBuilder.shapedRecipe(result.get(), amount));
                ResourceLocation location = new ResourceLocation("createarmsrace",
                        "mechanical_crafting/" + RegisteredObjects.getKeyOrThrow(result.get().getItem())
                                .getPath()
                                + suffix
                );
                b.build(consumer, location);
            });
        }
    }
}
