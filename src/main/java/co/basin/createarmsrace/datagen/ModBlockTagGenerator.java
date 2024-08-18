package co.basin.createarmsrace.datagen;

import co.basin.createarmsrace.CreateArmsRace;
import co.basin.createarmsrace.blocks.ModBlocks;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.BlockTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.fml.common.Mod;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class ModBlockTagGenerator extends BlockTagsProvider {
    public ModBlockTagGenerator(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, CreateArmsRace.MODID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        this.tag(Tags.Blocks.ORES)
                .add(ModBlocks.DEEPSLATE_BAUXITE_ORE.get())
                .add(ModBlocks.BAUXITE_ORE.get())
                .add(ModBlocks.DEEPSLATE_LEAD_ORE.get())
                .add(ModBlocks.LEAD_ORE.get());

        this.tag(BlockTags.NEEDS_IRON_TOOL)
                .add(ModBlocks.DEEPSLATE_BAUXITE_ORE.get())
                .add(ModBlocks.BAUXITE_ORE.get())
                .add(ModBlocks.DEEPSLATE_LEAD_ORE.get())
                .add(ModBlocks.LEAD_ORE.get());

        this.tag(BlockTags.MINEABLE_WITH_PICKAXE)
                .add(ModBlocks.DEEPSLATE_BAUXITE_ORE.get())
                .add(ModBlocks.BAUXITE_ORE.get())
                .add(ModBlocks.DEEPSLATE_LEAD_ORE.get())
                .add(ModBlocks.LEAD_ORE.get());
    }
}
