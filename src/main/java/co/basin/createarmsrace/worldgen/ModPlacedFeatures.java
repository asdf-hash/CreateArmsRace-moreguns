package co.basin.createarmsrace.worldgen;

import co.basin.createarmsrace.CreateArmsRace;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.data.worldgen.placement.OrePlacements;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.HeightRangePlacement;
import net.minecraft.world.level.levelgen.placement.HeightmapPlacement;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.placement.PlacementModifier;

import java.util.List;

public class ModPlacedFeatures {
    public static final ResourceKey<PlacedFeature> LEAD_ORE_PLACED_KEY = registerKey("lead_ore_placed");
    public static final ResourceKey<PlacedFeature> BAUXITE_ORE_PLACED_KEY = registerKey("bauxite_ore_placed");

    public static void bootstrap(BootstapContext<PlacedFeature> context) {
        HolderGetter<ConfiguredFeature<?, ?>> configuredFeature = context.lookup(Registries.CONFIGURED_FEATURE);
        register(context, LEAD_ORE_PLACED_KEY, configuredFeature.getOrThrow(ModConfiguredFeatures.OVERWORLD_LEAD_ORE_KEY),
                ModOrePlacement.commonOrePlacement(34, HeightRangePlacement.triangle(VerticalAnchor.absolute(-80), VerticalAnchor.absolute(48))));

        register(context, BAUXITE_ORE_PLACED_KEY, configuredFeature.getOrThrow(ModConfiguredFeatures.OVERWORLD_BAUXITE_ORE_KEY),
                ModOrePlacement.commonOrePlacement(10, HeightRangePlacement.uniform(VerticalAnchor.absolute(-64), VerticalAnchor.absolute(112))));
    }

    private static ResourceKey<PlacedFeature> registerKey(String name) {
        return ResourceKey.create(Registries.PLACED_FEATURE, new ResourceLocation(CreateArmsRace.MODID, name));
    }

    private static void register(BootstapContext<PlacedFeature> context, ResourceKey<PlacedFeature> key, Holder<ConfiguredFeature<?, ?>> configuration, List<PlacementModifier> modifiers) {
        context.register(key, new PlacedFeature(configuration, List.copyOf(modifiers)));
    }
}
