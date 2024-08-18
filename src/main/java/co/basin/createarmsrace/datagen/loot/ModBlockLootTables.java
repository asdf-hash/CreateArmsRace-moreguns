package co.basin.createarmsrace.datagen.loot;

import co.basin.createarmsrace.blocks.ModBlocks;
import co.basin.createarmsrace.items.ModItems;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;

import java.util.Set;

public class ModBlockLootTables extends BlockLootSubProvider {
    public ModBlockLootTables() {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags());
    }

    @Override
    protected void generate() {
        this.add(ModBlocks.BAUXITE_ORE.get(),
                block -> createOreDrop(ModBlocks.BAUXITE_ORE.get(), ModItems.RAW_BAUXITE.get()));
        this.add(ModBlocks.DEEPSLATE_BAUXITE_ORE.get(),
                block -> createOreDrop(ModBlocks.DEEPSLATE_BAUXITE_ORE.get(), ModItems.RAW_BAUXITE.get()));
        this.add(ModBlocks.LEAD_ORE.get(),
                block -> createOreDrop(ModBlocks.LEAD_ORE.get(), ModItems.RAW_LEAD.get()));
        this.add(ModBlocks.DEEPSLATE_LEAD_ORE.get(),
                block -> createOreDrop(ModBlocks.DEEPSLATE_LEAD_ORE.get(), ModItems.RAW_LEAD.get()));

        this.add(ModBlocks.RESEARCH_TABLE.get(), block -> createSingleItemTable(ModBlocks.RESEARCH_TABLE.get()));
    }

    @Override
    protected @NotNull Iterable<Block> getKnownBlocks() {
        return ModBlocks.BLOCKS.getEntries().stream().map(RegistryObject::get)::iterator;
    }
}
