package co.basin.createarmsrace.blocks.entity;

import co.basin.createarmsrace.CreateArmsRace;
import co.basin.createarmsrace.blocks.ModBlocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, CreateArmsRace.MODID);

    public static final RegistryObject<BlockEntityType<ResearchTableBlockEntity>> RESEARCH_TABLE = BLOCK_ENTITIES.register("research_table", () ->
            BlockEntityType.Builder.of(ResearchTableBlockEntity::new, ModBlocks.RESEARCH_TABLE.get()).build(null));
}
