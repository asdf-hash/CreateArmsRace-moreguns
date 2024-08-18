package co.basin.createarmsrace.items;

import co.basin.createarmsrace.Config;
import co.basin.createarmsrace.CreateArmsRace;
import net.minecraft.client.gui.screens.inventory.LoomScreen;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.ArrayList;
import java.util.List;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, CreateArmsRace.MODID);
    public static final RegistryObject<Item> STEEL_INGOT = ITEMS.register("steel_ingot", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> PIG_IRON = ITEMS.register("pig_iron", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> STEEL_SHEET = ITEMS.register("steel_sheet", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> STEEL_SCRAP = ITEMS.register("steel_scrap", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> ALUMINUM_INGOT = ITEMS.register("aluminum_ingot", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> ALUMINUM_SHEET = ITEMS.register("aluminum_sheet", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> LEAD_INGOT = ITEMS.register("lead_ingot", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> LEAD_SHEET = ITEMS.register("lead_sheet", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> LEAD_NUGGET = ITEMS.register("lead_nugget", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> RAW_LEAD = ITEMS.register("raw_lead", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> RAW_BAUXITE = ITEMS.register("raw_bauxite", () -> new Item(new Item.Properties()));

    public static final List<RegistryObject<Item>> CASING_ITEMS = register_casings();
    public static final List<RegistryObject<Item>> INCOMPLETE_CASING_ITEMS = register_incomplete_casings();
    public static final List<RegistryObject<Item>> PAYLOAD_ITEMS = register_payloads();
    public static final List<RegistryObject<Item>> INCOMPLETE_PAYLOAD_ITEMS = register_incomplete_payloads();
    public static final List<RegistryObject<Item>> COMPLETED_SCHEMATICS = register_completed_schematics();

    private static List<RegistryObject<Item>> register_payloads() { // hello you are very cute :)
        List<RegistryObject<Item>> items = new ArrayList<>();
        for (String type : Config.ammo_types) {
            items.add(ITEMS.register(type + "_" + "payload", () -> new Item(new Item.Properties())));
        }
        return items;
    }

    private static List<RegistryObject<Item>> register_incomplete_payloads() {
        List<RegistryObject<Item>> items = new ArrayList<>();
        for (String type : Config.ammo_types) {
            items.add(ITEMS.register(type + "_" + "incomplete_payload", () -> new Item(new Item.Properties())));
        }
        return items;
    }

    private static List<RegistryObject<Item>> register_casings() {
        List<RegistryObject<Item>> items = new ArrayList<>();
        for (String type : Config.ammo_types) {
            items.add(ITEMS.register(type + "_" + "casing", () -> new Item(new Item.Properties())));
        }
        return items;
    }

    private static List<RegistryObject<Item>> register_incomplete_casings() {
        List<RegistryObject<Item>> items = new ArrayList<>();
        for (String type : Config.ammo_types) {
            items.add(ITEMS.register(type + "_" + "incomplete_casing", () -> new Item(new Item.Properties())));
        }
        return items;
    }

    private static List<RegistryObject<Item>> register_completed_schematics() {
        List<RegistryObject<Item>> items = new ArrayList<>();
        for (String researchable : Config.researchables) {
            items.add(ITEMS.register(researchable + "_" + "completed_schematic", () -> new Item(new Item.Properties().stacksTo(1))));
        }
        return items;
    }
}
