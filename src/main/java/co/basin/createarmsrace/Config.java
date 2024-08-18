package co.basin.createarmsrace;

import com.tacz.guns.api.TimelessAPI;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.config.ModConfigEvent;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.*;
import java.util.stream.Collectors;

// An example config class. This is not required, but it's a good idea to have one to keep your config organized.
// Demonstrates how to use Forge's config APIs
@Mod.EventBusSubscriber(modid = CreateArmsRace.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class Config
{
    public static final String[] weapon_types = {
            "m95", "ai_awp", "scar_h", "hk_g3",
            "db_long", "db_short", "m249", "sks_tactical",
            "rpk", "deagle", "m16a1", "deagle_golden",
            "ak47", "vector45", "ump45", "m16a4",
            "m4a1", "aa12", "rpg7", "hk_mp5a5",
            "cz75", "uzi", "glock_17"
    };

    public static final String[] firing_modes = {
            "SEMI", "SEMI", "SEMI", "SEMI",
            "SEMI", "BURST", "AUTO", "SEMI",
            "AUTO", "SEMI", "AUTO", "SEMI",
            "AUTO", "AUTO", "AUTO", "BURST",
            "AUTO", "SEMI", "SEMI", "AUTO",
            "AUTO", "AUTO", "SEMI",
    };

    public static final String[] ammo_types = {
            "9mm", "12g", "30_06", "45acp",
            "46x30", "50ae", "50bmg", "58x42",
            "68x51fury", "308", "338", "357mag",
            "556x45", "762x25", "762x39", "762x54"
    };

    public static final String[] attachment_types = {
            "bayonet_6h3", "bayonet_m9", "deagle_golden_long_barrel", "extended_mag_1",
            "extended_mag_2", "extended_mag_3", "grip_magpul_afg_2", "grip_vertical_talon",
            "light_extended_mag_1", "light_extended_mag_2", "light_extended_mag_3", "muzzle_brake_cthulhu",
            "muzzle_brake_cyclone_d2", "muzzle_brake_pioneer", "muzzle_brake_trex", "muzzle_compensator_trident",
            "muzzle_silence_mirage", "muzzle_silence_phantom_s1", "oem_stock_heavy", "oem_stock_light",
            "oem_stock_tactical", "scope_acog_ta31", "scope_elcan_4x", "scope_lpvo_1_6",
            "scope_retro_2x", "scope_standard_8x", "sight_552", "sight_coyote",
            "sight_exp3", "sight_rmr_dot", "sight_sro_dot", "sight_t1",
            "sight_t2", "sight_uh1", "sniper_extended_mag_1", "sniper_extended_mag_2",
            "sniper_extended_mag_3", "stock_carbon_bone_c5", "stock_militech_b5", "stock_tactical_ar",
    };

    private static final int[] DEFAULT_RESEARCH_TIMES = {
            // Weapons
            245000, 204800, 155623, 154770,
            141512, 141512, 114073, 113512,
            105800, 105800, 102152, 99331,
            96800, 95207, 90506, 84050,
            83809, 80000, 80000, 77356,
            74756, 64800, 31250,

            // Ammo
            13417, 7454, 1000000, 3750,
            1000000, 5500, 16406, 1000000,
            1000000, 4456, 10500, 4781,
            3414, 1000000, 4200, 1000000
    };

    public static final String[] SELECTION_DISPLAY_NAMES = {
            "M95", "AWM", "SCAR-H Rifle", "HK G3 Rifle",
            "DB-4 Ursus", "DB-2 Durin", "M249 MG", "Sks Tactical",
            "RPK", "Deagle 50", "M16A1 Rifle", "Gold Deagle",
            "AKM", "Vector SMG", "UMP45 SMG", "M16A4 Rifle",
            "M4A1 Carbine", "AA12 Shotgun", "RPG-7", "HK-MP5A5",
            "CZ 75", "UZI", "Glock 17",

            "9mm", "12 Gauge", ".30-06 SF", ".45 ACP",
            "4.6mm AP", ".50 AE", ".50 BMG", "5.8mm DBP87",
            "6.8×51mm", ".308", ".338 Lapua", ".357 Magnum",
            "5.56x45mm", "7.62x25mm", "7.62x39mm", "7.62x54mm"
    };

    public static final int[] gunpowder_amounts = { 1, 2, 1, 1, 1, 2, 5, 1, 1, 2, 3, 2, 1, 1, 2, 1 };
    // Recipes that craft 0 are skipped; Mostly because they aren't used in tacz yet;
    public static final int[] ammo_amounts =      { 8, 2, 0, 6, 0, 2, 1, 0, 0, 4, 2, 3, 7, 0, 5, 0 };

    public static final String[] researchables = createResearchables();

    private static String[] createResearchables() {
        int weapon_count = Config.weapon_types.length;
        int ammo_count = Config.ammo_types.length;
        String[] researchables = new String[weapon_count + ammo_count];
        System.arraycopy(Config.weapon_types, 0, researchables, 0, weapon_count);
        System.arraycopy(Config.ammo_types, 0, researchables, weapon_count, weapon_count + ammo_count - weapon_count);
        return researchables;
    }

    private static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
//    private static final ForgeConfigSpec.ConfigValue<List<? extends Integer>> RESEARCH_LENGTHS = BUILDER
//            .comment("A list of items to log on common setup.")
//            .defineListAllowEmpty("bossEntities", List.of(0), Config::validateResearchTime);
//    private static boolean validateResearchTime(final Object obj) {
//        return obj instanceof final Integer value && value > 0 && value < Integer.MAX_VALUE;
//    }

    private static final ForgeConfigSpec.IntValue[] RESEARCH_LENGTHS = buildDefaultResearchLengths();
    private static ForgeConfigSpec.IntValue[] buildDefaultResearchLengths() {
        ForgeConfigSpec.IntValue[] research_lengths = new ForgeConfigSpec.IntValue[Config.weapon_types.length + Config.ammo_types.length];
        for (int i = 0; i < Config.weapon_types.length; i++) {
            research_lengths[i] = BUILDER
                    .comment("How long this weapon will take to research at the Research Table; Provided in Game Ticks i.e 1200tick == 1min")
                    .defineInRange("weapons." + Config.weapon_types[i] + ".research time", DEFAULT_RESEARCH_TIMES[i], 0, Integer.MAX_VALUE);
        }
        for (int i = 0; i < Config.ammo_types.length; i++) {
            research_lengths[i + weapon_types.length] = BUILDER
                    .comment("How long this ammo type will take to research at the Research Table; Provided in Game Ticks; i.e 1200tick == 1min")
                    .defineInRange("ammo." + Config.ammo_types[i] + ".research time", DEFAULT_RESEARCH_TIMES[i + weapon_types.length], 0, Integer.MAX_VALUE);
        }
        return research_lengths;
    }

    static final ForgeConfigSpec SPEC = BUILDER.build();
    public static List<Integer> research_lengths;

    @SubscribeEvent
    static void onLoad(final ModConfigEvent event)
    {
        research_lengths = Arrays.stream(RESEARCH_LENGTHS).map(ForgeConfigSpec.ConfigValue::get).collect(Collectors.toList());
        for (int length : research_lengths) {
            CreateArmsRace.log(String.valueOf(length));
        }
    }
}
