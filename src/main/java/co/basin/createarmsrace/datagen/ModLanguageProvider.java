package co.basin.createarmsrace.datagen;

import co.basin.createarmsrace.Config;
import co.basin.createarmsrace.CreateArmsRace;
import co.basin.createarmsrace.blocks.ModBlocks;
import co.basin.createarmsrace.blocks.entity.ModBlockEntities;
import co.basin.createarmsrace.items.ModItems;
import com.tacz.guns.init.ModEntities;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.LanguageProvider;
import org.apache.commons.lang3.text.WordUtils;

public class ModLanguageProvider extends LanguageProvider {
    public ModLanguageProvider(PackOutput output, String locale) {
        super(output, CreateArmsRace.MODID, locale);
    }

    @Override
    protected void addTranslations() {
        add(ModItems.STEEL_INGOT.get(), formatName(ModItems.STEEL_INGOT.getId().getPath()));
        add(ModItems.PIG_IRON.get(), formatName(ModItems.PIG_IRON.getId().getPath()));
        add(ModItems.STEEL_SHEET.get(), formatName(ModItems.STEEL_SHEET.getId().getPath()));
        add(ModItems.STEEL_SCRAP.get(), formatName(ModItems.STEEL_SCRAP.getId().getPath()));
        add(ModItems.ALUMINUM_INGOT.get(), formatName(ModItems.ALUMINUM_INGOT.getId().getPath()));
        add(ModItems.ALUMINUM_SHEET.get(), formatName(ModItems.ALUMINUM_SHEET.getId().getPath()));
        add(ModItems.LEAD_INGOT.get(), formatName(ModItems.LEAD_INGOT.getId().getPath()));
        add(ModItems.LEAD_SHEET.get(), formatName(ModItems.LEAD_SHEET.getId().getPath()));
        add(ModItems.LEAD_NUGGET.get(), formatName(ModItems.LEAD_NUGGET.getId().getPath()));
        add(ModItems.RAW_LEAD.get(), formatName(ModItems.RAW_LEAD.getId().getPath()));
        add(ModItems.RAW_BAUXITE.get(), formatName(ModItems.RAW_BAUXITE.getId().getPath()));

        for (int i = 0; i < ModItems.CASING_ITEMS.size(); i++) {
            add(ModItems.CASING_ITEMS.get(i).get(), Config.SELECTION_DISPLAY_NAMES[i + Config.weapon_types.length] + " Casing");
        }

        for (int i = 0; i < ModItems.INCOMPLETE_CASING_ITEMS.size(); i++) {
            add(ModItems.INCOMPLETE_CASING_ITEMS.get(i).get(), "Incomplete " + Config.SELECTION_DISPLAY_NAMES[i + Config.weapon_types.length] + " Casing");
        }

        for (int i = 0; i < ModItems.PAYLOAD_ITEMS.size(); i++) {
            add(ModItems.PAYLOAD_ITEMS.get(i).get(), Config.SELECTION_DISPLAY_NAMES[i + Config.weapon_types.length] + " Payload");
        }

        for (int i = 0; i < ModItems.INCOMPLETE_PAYLOAD_ITEMS.size(); i++) {
            add(ModItems.INCOMPLETE_PAYLOAD_ITEMS.get(i).get(), "Incomplete " + Config.SELECTION_DISPLAY_NAMES[i + Config.weapon_types.length] + " Payload");
        }

        for (int i = 0; i < ModItems.COMPLETED_SCHEMATICS.size(); i++) {
            add(ModItems.COMPLETED_SCHEMATICS.get(i).get(), Config.SELECTION_DISPLAY_NAMES[i] + " Schematic");
        }

        for (var entityType : ModBlockEntities.BLOCK_ENTITIES.getEntries()) {
            add(CreateArmsRace.MODID + ".block.entity." + entityType.getId().getPath(), formatName(entityType.getId().getPath()));
        }

        for (var block : ModBlocks.BLOCKS.getEntries()) {
            add(block.get(), formatName(block.getId().getPath()));
        }
    }

    private static String formatName(String name) {
        return WordUtils.capitalizeFully(name.replace("_", " "));
    }
}
