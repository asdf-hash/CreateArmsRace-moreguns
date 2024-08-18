package co.basin.createarmsrace.blocks;

import co.basin.createarmsrace.CreateArmsRace;
import co.basin.createarmsrace.items.ModItems;
import com.simibubi.create.AllBlocks;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class ModBlocks {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, CreateArmsRace.MODID);
    public static final RegistryObject<Block> DEEPSLATE_BAUXITE_ORE = registerBlock("deepslate_bauxite_ore", () -> new Block(BlockBehaviour.Properties.copy(Blocks.DEEPSLATE_COAL_ORE)));
    public static final RegistryObject<Block> BAUXITE_ORE = registerBlock("bauxite_ore", () -> new Block(BlockBehaviour.Properties.copy(Blocks.COAL_ORE)));
    public static final RegistryObject<Block> DEEPSLATE_LEAD_ORE = registerBlock("deepslate_lead_ore", () -> new Block(BlockBehaviour.Properties.copy(Blocks.DEEPSLATE_COAL_ORE)));
    public static final RegistryObject<Block> LEAD_ORE = registerBlock("lead_ore", () -> new Block(BlockBehaviour.Properties.copy(Blocks.COAL_ORE)));

    public static final RegistryObject<Block> RESEARCH_TABLE = registerBlock("research_table", () -> new ResearchTableBlock(
            BlockBehaviour.Properties
                    .copy(Blocks.DARK_OAK_PLANKS)
                    .isViewBlocking((s, g, p) -> false)
                    .noOcclusion()
                    .isValidSpawn((s, g, p, a) -> false)));


    private static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> block) {
        RegistryObject<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn);
        return toReturn;
    }

    private static <T extends Block> RegistryObject<Item> registerBlockItem(String name, RegistryObject<T> block) {
        return ModItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
    }
}
