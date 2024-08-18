package co.basin.createarmsrace.datagen;

import co.basin.createarmsrace.CreateArmsRace;
import co.basin.createarmsrace.items.ModItems;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;

public class ModItemModelProvider extends ItemModelProvider {
    public ModItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, CreateArmsRace.MODID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        simpleItem(ModItems.STEEL_INGOT);
        simpleItem(ModItems.PIG_IRON);
        simpleItem(ModItems.STEEL_SHEET);
        simpleItem(ModItems.STEEL_SCRAP);
        simpleItem(ModItems.ALUMINUM_INGOT);
        simpleItem(ModItems.ALUMINUM_SHEET);
        simpleItem(ModItems.LEAD_INGOT);
        simpleItem(ModItems.LEAD_SHEET);
        simpleItem(ModItems.LEAD_NUGGET);
        simpleItem(ModItems.RAW_LEAD);
        simpleItem(ModItems.RAW_BAUXITE);

        for (RegistryObject<Item> item : ModItems.CASING_ITEMS) {
            casingItem(item);
        }

        for (RegistryObject<Item> item : ModItems.PAYLOAD_ITEMS) {
            payloadItem(item);
        }

        for (RegistryObject<Item> item : ModItems.INCOMPLETE_CASING_ITEMS) {
            incompleteCasingItem(item);
        }

        for (RegistryObject<Item> item : ModItems.INCOMPLETE_PAYLOAD_ITEMS) {
            incompletePayloadItem(item);
        }

        for (RegistryObject<Item> item : ModItems.COMPLETED_SCHEMATICS) {
            withExistingParent(item.getId().getPath(),
                    new ResourceLocation("item/generated")).texture("layer0",
                    new ResourceLocation(CreateArmsRace.MODID, "item/completed_schematic"));
        }
    }

    private ItemModelBuilder simpleItem(RegistryObject<Item> item) {
        return withExistingParent(item.getId().getPath(),
                new ResourceLocation("item/generated")).texture("layer0",
                new ResourceLocation(CreateArmsRace.MODID, "item/" + item.getId().getPath()));
    }

    private ItemModelBuilder incompleteCasingItem(RegistryObject<Item> item) {
        String path = item.getId().getPath().replace("_incomplete", "");
        return withExistingParent(item.getId().getPath(),
                new ResourceLocation("item/generated")).texture("layer0",
                new ResourceLocation(CreateArmsRace.MODID, "item/ammo/casing/" + path));
    }

    private ItemModelBuilder casingItem(RegistryObject<Item> item) {
        return withExistingParent(item.getId().getPath(),
                new ResourceLocation("item/generated")).texture("layer0",
                new ResourceLocation(CreateArmsRace.MODID, "item/ammo/casing/" + item.getId().getPath()));
    }

    private ItemModelBuilder incompletePayloadItem(RegistryObject<Item> item) {
        String path = item.getId().getPath().replace("_incomplete", "");
        return withExistingParent(item.getId().getPath(),
                new ResourceLocation("item/generated")).texture("layer0",
                new ResourceLocation(CreateArmsRace.MODID, "item/ammo/payload/" + path));
    }

    private ItemModelBuilder payloadItem(RegistryObject<Item> item) {
        return withExistingParent(item.getId().getPath(),
                new ResourceLocation("item/generated")).texture("layer0",
                new ResourceLocation(CreateArmsRace.MODID, "item/ammo/payload/" + item.getId().getPath()));
    }
}
