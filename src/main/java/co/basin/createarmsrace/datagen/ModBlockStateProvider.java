package co.basin.createarmsrace.datagen;

import co.basin.createarmsrace.CreateArmsRace;
import co.basin.createarmsrace.blocks.ModBlocks;
import net.minecraft.data.PackOutput;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;

import java.rmi.registry.Registry;

public class ModBlockStateProvider extends BlockStateProvider {
    public ModBlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, CreateArmsRace.MODID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        blockWithItem(ModBlocks.DEEPSLATE_LEAD_ORE);
        blockWithItem(ModBlocks.LEAD_ORE);
        blockWithItem(ModBlocks.DEEPSLATE_BAUXITE_ORE);
        blockWithItem(ModBlocks.BAUXITE_ORE);

        //blockWithItem(ModBlocks.RESEARCH_TABLE);
    }

    private void blockWithItem(RegistryObject<Block> blockRegistryObject) {
        simpleBlockWithItem(blockRegistryObject.get(), cubeAll(blockRegistryObject.get()));
    }
}
