package co.basin.createarmsrace.worldgen;

import co.basin.createarmsrace.CreateArmsRace;
import co.basin.createarmsrace.blocks.ModBlocks;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.TagMatchTest;

import java.util.List;

public class ModConfiguredFeatures {
    public static final ResourceKey<ConfiguredFeature<?, ?>> OVERWORLD_LEAD_ORE_KEY = registerKey("lead_ore");
    public static final ResourceKey<ConfiguredFeature<?, ?>> OVERWORLD_BAUXITE_ORE_KEY = registerKey("bauxite_ore");

    public static void bootstrap(BootstapContext<ConfiguredFeature<?, ?>> context) {
        RuleTest stoneReplacables = new TagMatchTest(BlockTags.STONE_ORE_REPLACEABLES);
        RuleTest deepSlateReplacables = new TagMatchTest(BlockTags.DEEPSLATE_ORE_REPLACEABLES);

        List<OreConfiguration.TargetBlockState> overworldLeadOre = List.of(
                OreConfiguration.target(stoneReplacables, ModBlocks.LEAD_ORE.get().defaultBlockState()),
                OreConfiguration.target(deepSlateReplacables, ModBlocks.DEEPSLATE_LEAD_ORE.get().defaultBlockState())
        );

        List<OreConfiguration.TargetBlockState> overworldBauxiteOre = List.of(
                OreConfiguration.target(stoneReplacables, ModBlocks.BAUXITE_ORE.get().defaultBlockState()),
                OreConfiguration.target(deepSlateReplacables, ModBlocks.DEEPSLATE_BAUXITE_ORE.get().defaultBlockState())
        );

        register(context, OVERWORLD_LEAD_ORE_KEY, Feature.ORE, new OreConfiguration(overworldLeadOre, 8));
        register(context, OVERWORLD_BAUXITE_ORE_KEY, Feature.ORE, new OreConfiguration(overworldBauxiteOre, 6));
    }


    public static ResourceKey<ConfiguredFeature<?, ?>> registerKey(String name) {
        return ResourceKey.create(Registries.CONFIGURED_FEATURE, new ResourceLocation(CreateArmsRace.MODID, name));
    }

    private static <FC extends FeatureConfiguration, F extends Feature<FC>> void register(BootstapContext<ConfiguredFeature<?, ?>> context, ResourceKey<ConfiguredFeature<?, ?>> key, F feature, FC configuration) {
        context.register(key, new ConfiguredFeature<>(feature, configuration));
    }
}
