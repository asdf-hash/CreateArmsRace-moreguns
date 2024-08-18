package co.basin.createarmsrace.datagen;

import co.basin.createarmsrace.CreateArmsRace;
import co.basin.createarmsrace.items.ModItems;
import com.simibubi.create.AllTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class ModItemTagGenerator extends ItemTagsProvider {

    public ModItemTagGenerator(PackOutput p_275343_, CompletableFuture<HolderLookup.Provider> p_275729_, CompletableFuture<TagLookup<Block>> p_275322_, @Nullable ExistingFileHelper existingFileHelper) {
        super(p_275343_, p_275729_, p_275322_, CreateArmsRace.MODID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        addItemToForgeTag(ModItems.LEAD_NUGGET, "nuggets/lead");
        addItemToForgeTag(ModItems.STEEL_INGOT, "ingots/steel");
        addItemToForgeTag(ModItems.ALUMINUM_INGOT, "ingots/aluminum");
        addItemToForgeTag(ModItems.LEAD_INGOT, "ingots/lead");
        addItemToForgeTag(ModItems.LEAD_SHEET, "plates/lead");
        addItemToForgeTag(ModItems.ALUMINUM_SHEET, "plates/aluminum");
        addItemToForgeTag(ModItems.STEEL_SHEET, "plates/steel");
        //addItemToForgeTag(ModItems.PIG_IRON, "ingots/pig_iron");
        //addItemToForgeTag(ModItems.STEEL_SCRAP);
        //addItemToForgeTag(ModItems.RAW_LEAD);
        //addItemToForgeTag(ModItems.RAW_BAUXITE);
    }

    private void addItemToForgeTag(RegistryObject<Item> item, String tag) {
        this.tag(ItemTags.create(new ResourceLocation("forge", tag))).add(item.get());
    }
}
