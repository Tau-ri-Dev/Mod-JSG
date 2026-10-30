package dev.tauri.jsg.datagen;

import dev.tauri.jsg.JSG;
import dev.tauri.jsg.common.registry.JSGItems;
import dev.tauri.jsg.common.registry.JSGSoundEvents;
import dev.tauri.jsgcore.common.registry.helper.RegistryObjectWrapper;
import dev.tauri.jsgcore.datagen.CoreLanguageProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.Item;
import org.jetbrains.annotations.Nullable;

public class JSGLanguageProvider extends CoreLanguageProvider {
    public JSGLanguageProvider(PackOutput output, String locate) {
        super(output, JSG.MOD_ID, locate);
    }

    protected void addCartridgeItem(RegistryObjectWrapper<? extends Item> object) {
        add(object.get(), pathToName(object.getId().getPath()));
        add(object.get().getDescriptionId() + ".empty", "Empty " + pathToName(object.getId().getPath()));
    }

    protected void addMusicDiscItem(RegistryObjectWrapper<? extends Item> object, String tooltip, @Nullable String extendedTooltip) {
        String name = object.getId().getPath().contains("siren") ? "§3Siren Disc" : "§3Music Disc";
        add(object.get(), name);

        String tooltipKey = object.get().getDescriptionId() + ".desc";
        add(tooltipKey, tooltip);

        if (extendedTooltip != null) {
            String extendedKey = object.get().getDescriptionId() + ".desc.extended";
            add(extendedKey, extendedTooltip);
        }
    }

    @Override
    protected void addTranslations() {
        //ICONS FOR ADVANCEMENTS
        addItem(JSGItems.ICON_KAWOOSH_DEATH);
        addItem(JSGItems.ICON_WORMHOLE);
        addItem(JSGItems.ICON_RA_REALM);
        addItem(JSGItems.ICON_PRIEST_TRADE);

        //DHD PARTS
        addItem(JSGItems.MILKYWAY_DHD_CONTROL_CRYSTALS);
        addItem(JSGItems.MILKYWAY_DHD_BUTTONS_CONSOLE);
        addItem(JSGItems.MILKYWAY_DHD_MAIN_CRYSTAL);
        addItem(JSGItems.MILKYWAY_DHD_ACTIVATION_BUTTON);
        addItem(JSGItems.MILKYWAY_DHD_UPGRADES_COVER);
        add("block." + JSG.MOD_ID + ".milkyway_dhd", "Milky Way Dial Home Device (DHD)");

        addItem(JSGItems.PEGASUS_DHD_CONTROL_CRYSTALS);
        addItem(JSGItems.PEGASUS_DHD_BUTTONS_CONSOLE);
        addItem(JSGItems.PEGASUS_DHD_MAIN_CRYSTAL);
        addItem(JSGItems.PEGASUS_DHD_ACTIVATION_BUTTON);
        addItem(JSGItems.PEGASUS_DHD_UPGRADES_COVER);
        add("block." + JSG.MOD_ID + ".milkyway_dhd", "Pegasus Dial Home Device (DHD)");

        addItem(JSGItems.DHD_NAQUADAH_TANK);
        add("item." + JSG.MOD_ID + ".dhd.assembly_helper.needed", "Missing %s");


        //ADDRESS AND UPGRADE CRYSTALS
        addItem(JSGItems.CRYSTAL_ADDRESS_DIALING_EXTENDED);
        addItem(JSGItems.CRYSTAL_ADDRESS_STATIC);
        addItem(JSGItems.CRYSTAL_ADDRESS_EXTENDED);
        addItem(JSGItems.CRYSTAL_GLYPH_MILKYWAY);
        addItem(JSGItems.CRYSTAL_GLYPH_PEGASUS);
        addItem(JSGItems.CRYSTAL_GLYPH_UNIVERSE);

        addItem(JSGItems.UPGRADE_IRIS);
        addItem(JSGItems.UPGRADE_IRIS_TRINIUM);
        addItem(JSGItems.UPGRADE_SHIELD);
        addItem(JSGItems.UPGRADE_IRIS_CREATIVE);

        //TOOLS
        addItem(JSGItems.ADMIN_CONTROLLER);
        addItem(JSGItems.UNIVERSE_DIALER);
        add("item." + JSG.MOD_ID + ".gdo", "Garage Door Opener (GDO)");

        //CRAFTING ITEMS
        addItem(JSGItems.HOLDER_CRYSTAL);
        addItem(JSGItems.HOLDER_CRYSTAL_PEGASUS);
        addItem(JSGItems.FRAGMENT_MILKYWAY);
        addItem(JSGItems.FRAGMENT_PEGASUS);
        addItem(JSGItems.FRAGMENT_UNIVERSE);
        addItem(JSGItems.FRAGMENT_TOLLAN);

        addItem(JSGItems.IRIS_BLADE);
        addItem(JSGItems.QUAD_IRIS_BLADE);
        addItem(JSGItems.IRIS_BLADE_TRINIUM);
        addItem(JSGItems.QUAD_IRIS_BLADE_TRINIUM);
        addItem(JSGItems.SHIELD_EMITTER);

        //FOOD
        addItemWithTooltip(JSGItems.FOOD_CHOCOLATE_BAR, "5th Avenue Chocolate Bar", "Bahni? Bahni-wae!");
        addItem(JSGItems.JIBBARAN_ENHANCER);

        //SPAWN EGGS
        addItem(JSGItems.EGG_MASTADGE);

        //CARTRIDGES
        addCartridgeItem(JSGItems.CARTRIDGE_BLACK);
        addCartridgeItem(JSGItems.CARTRIDGE_CYAN);
        addCartridgeItem(JSGItems.CARTRIDGE_MAGENTA);
        addCartridgeItem(JSGItems.CARTRIDGE_YELLOW);

        //SCHEMATICS
        addItem(JSGItems.SCHEMATIC_MILKYWAY);
        addItem(JSGItems.SCHEMATIC_PEGASUS);
        addItem(JSGItems.SCHEMATIC_UNIVERSE);
        addItem(JSGItems.SCHEMATIC_TOLLAN);

        //MUSIC DISCS
        addMusicDiscItem(JSGItems.RECORDS.get(JSGSoundEvents.RECORD_SGC_THEME), "Joel Goldsmith - Entering the Stargate", null);
        addMusicDiscItem(JSGItems.RECORDS.get(JSGSoundEvents.RECORD_CONTINUUM_OPENING), "Stargate Continuum - Intro", null);
        addMusicDiscItem(JSGItems.RECORDS.get(JSGSoundEvents.RECORD_ORIGINS_THEME), "Filip Olejka - Origins", null);
        addMusicDiscItem(JSGItems.RECORDS.get(JSGSoundEvents.RECORD_ATLANTIS_THEME), "Stargate Atlantis - Theme", null);
        addMusicDiscItem(JSGItems.RECORDS.get(JSGSoundEvents.RECORD_ATLANTIS_DIALING), "Stargate Atlantis - Dialing from Earth", null);
        addMusicDiscItem(JSGItems.RECORDS.get(JSGSoundEvents.RECORD_DESTINY_DIALING), "Joel Goldsmith - Dialing Earth", null);
        addMusicDiscItem(JSGItems.RECORDS.get(JSGSoundEvents.RECORD_DESTINY_GAUNTLET), "Joel Goldsmith - Stargate Universe Gauntlet", null);
        addMusicDiscItem(JSGItems.RECORDS.get(JSGSoundEvents.RECORD_DESTINY_OPENING), "Joel Goldsmith - Stargate Universe Intro", null);

        addMusicDiscItem(JSGItems.RECORDS.get(JSGSoundEvents.SIREN_SGC_OFFWORLD), "SGC Offworld Alarm", null);
        addMusicDiscItem(JSGItems.RECORDS.get(JSGSoundEvents.SIREN_SGC_DIALING), "SGC Gate Active Alarm", null);

        //COMPATIBILITY ITEMS
        add("item." + JSG.MOD_ID + ".iris_blade_golden_cast", "Iris Blade Golden Cast");
        add("item." + JSG.MOD_ID + ".iris_blade_sand_cast", "Iris Blade Sand Cast");
        add("item." + JSG.MOD_ID + ".iris_blade_red_sand_cast", "Iris Blade Red Sand Cast");
        add("item." + JSG.MOD_ID + ".iris_blade_stone", "Stone Iris Blade");
        add("pattern." + JSG.MOD_ID + ".iris_blade", "Iris Blade");

        add("pattern." + JSG.MOD_ID + ".incomplete_chocolate_bar", "Incomplete Chocolate Bar");

        //MAIN MENU
        add("menu.about", "About JSG Mod");
        add("menu.updater.download", "Download");
        add("menu.updater.close", "Close");
        add("menu.ram.help", "Help");
        add("menu.music.volume", "Music volume:");

        add("menu.tip.kawoosh", "TIP: Try to avoid standing in front of a Stargate as it activates.");
        add("menu.tip.unstable_eh", "TIP: Travelling through an unstable wormhole can result in death.");
        add("menu.tip.abydos_cartouche", "TIP: Explore Abydos to find cartouches with stargate addresses.");


        //SDG - STARGATE DIMENSION GENERATOR
        add("createWorld.stargates_generating", "Generating stargates in dimensions... %s%%");
        add("createWorld.stargates_generating.trying", "Trying generation in %s");
        add("createWorld.stargates_generating.searching", "Searching for structure in %s");
        add("createWorld.stargates_generating.genearating", "Generating stargate in %s");
        add("createWorld.stargates_generating.searching_stargate", "Searching for stargate in %s (%s) attempt %s");
        add("createWorld.stargates_generating.running_sg_regen", "Running stargate regeneration...");

        add("createWorld.stargate_disassemble.milkyway.title", "Milky Way Stargate");
        add("createWorld.stargate_disassemble.milkyway.chevron_frame", "Chevron Frame");
        add("createWorld.stargate_disassemble.milkyway.chevron_light", "Chevron Light");
        add("createWorld.stargate_disassemble.milkyway.chevron_lock", "Chevron Lock");
        add("createWorld.stargate_disassemble.milkyway.glyph_ring", "Glyph Ring");
        add("createWorld.stargate_disassemble.milkyway.gate_frame", "Stargate frame");

        add("createWorld.dhd_disassemble.milkyway.title", "Milky Way Dial Home Device");
        add("createWorld.dhd_disassemble.milkyway.dhd_table", "DHD Table");
        add("createWorld.dhd_disassemble.milkyway.crystals_holder", "Crystals Holder");
        add("createWorld.dhd_disassemble.milkyway.crystals", "DHD Crystals");
        add("createWorld.dhd_disassemble.milkyway.control_crystal", "The Control Crystal");
        add("createWorld.dhd_disassemble.milkyway.buttons_plate", "Buttons Plate");
        add("createWorld.dhd_disassemble.milkyway.buttons", "DHD Buttons");

        add("createWorld..stargates_generating.dim_status.ok", "Generated");
        add("createWorld..stargates_generating.dim_status.already_there", "Already there");
        add("createWorld..stargates_generating.dim_status.no_structure", "No structure available");
        add("createWorld..stargates_generating.dim_status.skipped", "Skipped by config");
        add("createWorld..stargates_generating.dim_status.error", "Error");

        //IN-GAME CONFIG
        add("gui." + JSG.MOD_ID + ",config", "Just Stargate Mod Configuration");
        add("gui." + JSG.MOD_ID + ",config.child.debug", "Debug Settings");
        add("gui." + JSG.MOD_ID + ",config.child.general", "General Configuration");
        add("gui." + JSG.MOD_ID + ",config.child.dialhomedevide", "DHD Configuration");
        add("gui." + JSG.MOD_ID + ",config.child.stargate", "Stargates Configuration");
        add("gui." + JSG.MOD_ID + ",config.child.computersintegration", "Computers Integration Configuration");

        add("gui." + JSG.MOD_ID + ".ingame_config.default_value", "Default: %s");
        add("gui." + JSG.MOD_ID + ".ingame_config.time_limit_mode.title", "Stargate Time Limit Mode");
        add("gui." + JSG.MOD_ID + ".ingame_config.time_limit_mode.comment", "Specifies what should the stargate do when the gate opened for more than Stargate Time Limit Time");
        add("gui." + JSG.MOD_ID + ".ingame_config.time_limit_time.title", "Stargate Time Limit Time");
        add("gui." + JSG.MOD_ID + ".ingame_config.time_limit_time.comment", "How many seconds can be opened before closing/drawing more power (set up by Stargate Time Limit Mode");
        add("gui." + JSG.MOD_ID + ".ingame_config.time_limit_power.title", "Stargate Time Limit Power Draw Multiplier");
        add("gui." + JSG.MOD_ID + ".ingame_config.time_limit_power.comment", "Specifies multiplier of stargate's power draw when the gate opened for more than Stargate Time Limit Time and Stargate Time Limit Mode is set to DRAW_MORE_POWER");
        add("gui." + JSG.MOD_ID + ".ingame_config.spin_speed.title", "Stargate's Ring Rotation Speed");
        add("gui." + JSG.MOD_ID + ".ingame_config.spin_speed.comment", "Ring rotation speed in %");

        add("gui." + JSG.MOD_ID + ".ingame_config.force_unstable.title", "Force Unstable Event Horizon");
        add("gui." + JSG.MOD_ID + ".ingame_config.force_unstable.comment", "If set to Enabled, stargate's event horizon will be unstable permanently (this also effects connected gate on the other side)");
        add("gui." + JSG.MOD_ID + ".ingame_config.enable_bury_state.title", "Enable Bury State");
        add("gui." + JSG.MOD_ID + ".ingame_config.enable_bury_state.comment", "When the inner space of the stargate is filled with blocks, should the gate become undialable?");
        add("gui." + JSG.MOD_ID + ".ingame_config.allow_rig.title", "Random Incoming Generator");
        add("gui." + JSG.MOD_ID + ".ingame_config.allow_rig.comment", "Allow spawning random incoming wormholes with mobs waves");
        add("gui." + JSG.MOD_ID + ".ingame_config.max_energy_crystals.title", "Enabled Energy Crystals Slots");
        add("gui." + JSG.MOD_ID + ".ingame_config.max_energy_crystals.comment", "Number of enabled energy crystals slots");
        add("gui." + JSG.MOD_ID + ".ingame_config.dhd_oc_press_sound.title", "DHD Button Sound when dialing");
        add("gui." + JSG.MOD_ID + ".ingame_config.dhd_oc_press_sound.comment", "Play DHD button press sound when dialing via other method than DHD itself");
        add("gui." + JSG.MOD_ID + ".ingame_config.allow_incoming_animation.title", "Incoming Animations");
        add("gui." + JSG.MOD_ID + ".ingame_config.allow_incoming_animation.comment", "Allow incoming animations to play on this gate");
        add("gui." + JSG.MOD_ID + ".ingame_config.dhd_last_lock.title", "DHD Last Chevron Lock");
        add("gui." + JSG.MOD_ID + ".ingame_config.dhd_last_lock.comment", "Play chevron locking animation on the final chevron when dialing by DHD");
        add("gui." + JSG.MOD_ID + ".ingame_config.spin_ring_incoming.title", "Spin Ring by Incoming Animation");
        add("gui." + JSG.MOD_ID + ".ingame_config.spin_ring_incoming.comment", "Start spinning the ring when incoming wormhole occurs. This require the Incoming Animations option to be set to Enabled.");
        add("gui." + JSG.MOD_ID + ".ingame_config.point_of_origin.title", "Point of Origin Variant");
        add("gui." + JSG.MOD_ID + ".ingame_config.point_of_origin.comment", "Variant of the PoO to be used on this gate. This will not change any gate's behaviour - it's only a visual");
        add("gui." + JSG.MOD_ID + ".ingame_config.fast_dialing.title", "Fast Dialing");
        add("gui." + JSG.MOD_ID + ".ingame_config.fast_dialing.comment", "Switch to fast dialing on this gate when dialing by a Remote");
        add("gui." + JSG.MOD_ID + ".ingame_config.orange_shield.title", "Orange Shield");
        add("gui." + JSG.MOD_ID + ".ingame_config.orange_shield.comment", "Switch shield to orange color on this gate");

        //TREASURE MAPS
                add("filled_map.burried_stargate", "Chappa'ai Location Map");
                add("filled_map.abydos_treasure", "Old Abydos Map");

        //ENTITIES
        //TODO: Add Method to get villager type and professions!
        //TODO: check out other providers -> maybe do it same way as here...
                /*"entity.minecraft.villager.jsg.priest_desert": "Priest",
                "entity.minecraft.villager.jsg.priest_jungle": "Priest",
                "entity.minecraft.villager.jsg.priest_plains": "Priest",
                "entity.minecraft.villager.jsg.priest_savanna": "Priest",
                "entity.minecraft.villager.jsg.priest_snow": "Priest",
                "entity.minecraft.villager.jsg.priest_swamp": "Priest",
                "entity.minecraft.villager.jsg.priest_taiga": "Priest",
                "entity.minecraft.villager.jsg.slave_miner": "Slave Miner",

                "entity.jsg.mastadge": "Mastadge",*/


                 /*"block.jsg.iris_block": "Iris / Shield block",
                "block.jsg.abydos_sand": "Sand",*/
    }
}
