package dev.tauri.jsg.datagen;

import dev.tauri.jsg.JSG;
import dev.tauri.jsg.common.registry.*;
import dev.tauri.jsgcore.common.registry.helper.RegistryObjectWrapper;
import dev.tauri.jsgcore.datagen.CoreLanguageProvider;
import net.minecraft.ChatFormatting;
import net.minecraft.data.PackOutput;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.entity.npc.VillagerType;
import net.minecraft.world.item.Item;

public class JSGLanguageProvider extends CoreLanguageProvider {
    public JSGLanguageProvider(PackOutput output, String locate) {
        super(output, JSG.MOD_ID, locate);
    }

    protected void addCartridgeItem(RegistryObjectWrapper<? extends Item> object) {
        add(object.get(), pathToName(object.getId().getPath()));
        add(object.get().getDescriptionId() + ".empty", "Empty " + pathToName(object.getId().getPath()));
    }

    protected void addVillagerProfession(RegistryObjectWrapper<? extends VillagerProfession> object) {
        var modID = object.getId().getNamespace();
        var key = "entity.minecraft.villager." + modID + "." + object.getId().getPath();
        var name = pathToName(object.getId().getPath());
        add(key, name);
    }

    protected  void addVillagerType(RegistryObjectWrapper<VillagerType> object) {
        var modID = object.getId().getNamespace();

        var key = "entity.minecraft.villager." + modID + "." + object.getId().getPath();
        var name = pathToName(object.getId().getPath());
        add(key, name);
    }

    @Override
    protected void addTranslations() {
        //SPECIAL BLOCKS
        addBlockWithCustomName(JSGBlocks.IRIS_BLOCK, "Iris / Shield block");
        addBlockWithCustomName(JSGBlocks.ABYDOS_SAND, "Sand");

        //UTILITIES BLOCKS
        addBlockWithTooltip(JSGBlocks.SG_REDSTONE_DIALER_I_BLOCK,"Dial a stargate using redstone signal" ,"To dial a symbol you need to set symbol ID%nl%using listed inputs then engage it.%nl%Selected symbol id = ((Right * 16) + Front)%nl%%nl%" +ChatFormatting.BOLD + "Inputs:" + "%nl%Top: Engage gate%nl%Bottom: Enable DHD Dialing animation%nl%Left: Engage symbol%nl%Right: 2nd bit of symbol ID%nl%Front: 1st bit of symbol ID");
        addBlockWithTooltip(JSGBlocks.SG_REDSTONE_STATE_O_BLOCK,"Get redstone output by current stargate state",ChatFormatting.BOLD + "Output:" + "%nl%0: Idle%nl%1: Dialing DHD%nl%2: Dialing Computer%nl%3: Engaged Incoming%nl%4: Engaged Outgoing%nl%5: Unstable%nl%6: Failing%nl%7: Incoming Wormhole");
        addBlockWithTooltip(JSGBlocks.TOASTER, "Orlin still owes Carter a new one.", null);
        addBlockWithTooltip(JSGBlocks.PRINTER, "Useful to print notebook pages", null);

        //STARGATE BLOCKS
        addBlock(JSGBlocks.STARGATE_MILKYWAY_BASE_BLOCK);
        addBlock(JSGBlocks.STARGATE_MILKYWAY_CHEVRON_BLOCK);
        addBlock(JSGBlocks.STARGATE_MILKYWAY_RING_BLOCK);
        addBlock(JSGBlocks.STARGATE_PEGASUS_BASE_BLOCK);
        addBlock(JSGBlocks.STARGATE_PEGASUS_CHEVRON_BLOCK);
        addBlock(JSGBlocks.STARGATE_PEGASUS_RING_BLOCK);
        addBlock(JSGBlocks.STARGATE_UNIVERSE_BASE_BLOCK);
        addBlock(JSGBlocks.STARGATE_UNIVERSE_CHEVRON_BLOCK);
        addBlock(JSGBlocks.STARGATE_UNIVERSE_RING_BLOCK);
        addBlock(JSGBlocks.STARGATE_MOVIE_BASE_BLOCK);
        addBlock(JSGBlocks.STARGATE_MOVIE_CHEVRON_BLOCK);
        addBlock(JSGBlocks.STARGATE_MOVIE_RING_BLOCK);
        addBlock(JSGBlocks.STARGATE_TOLLAN_BASE_BLOCK);
        addBlock(JSGBlocks.STARGATE_TOLLAN_CHEVRON_BLOCK);
        addBlock(JSGBlocks.STARGATE_TOLLAN_RING_BLOCK);

        addBlockWithCustomName(JSGBlocks.STARGATE_ORLIN_BASE_BLOCK, "Orlin's Stargate Base");
        addBlockWithCustomName(JSGBlocks.STARGATE_ORLIN_MEMBER_BLOCK, "Orlin's Stargate Ring");

        //ICONS FOR ADVANCEMENTS
        addItem(JSGItems.ICON_KAWOOSH_DEATH);
        addItem(JSGItems.ICON_WORMHOLE);
        addItem(JSGItems.ICON_RA_REALM);
        addItem(JSGItems.ICON_PRIEST_TRADE);

        //DHD PARTS
        addItem(JSGItems.MILKYWAY_DHD_CONTROL_CRYSTALS);
        addItem(JSGItems.MILKYWAY_DHD_BUTTONS_CONSOLE);
        addItemWithCustomNameAndTooltip(JSGItems.MILKYWAY_DHD_MAIN_CRYSTAL, "Milky Way DHD Main Control Crystal","Main control crystal of DHD","Insert this crystal into DHD (Dial Home Device)%nl%to make the DHD functional and be able%nl%to link with the nearest gate.");
        addItemWithCustomName(JSGItems.MILKYWAY_DHD_ACTIVATION_BUTTON, "Bright Red Button (BRB)");
        addItemWithCustomName(JSGItems.MILKYWAY_DHD_UPGRADES_COVER, "Milky Way Upgrades Cover Plate");
        addBlockWithCustomNameAndTooltip(JSGBlocks.DHD_MILKYWAY, "Milky Way Home Device (DHD)", "Control panel for Milky Way, Tollan and Movie gates", null);


        addItem(JSGItems.PEGASUS_DHD_CONTROL_CRYSTALS);
        addItem(JSGItems.PEGASUS_DHD_BUTTONS_CONSOLE);
        addItemWithCustomNameAndTooltip(JSGItems.PEGASUS_DHD_MAIN_CRYSTAL, "Pegasus DHD Main Control Crystal","Main control crystal of DHD","Insert this crystal into DHD (Dial Home Device)%nl%to make the DHD functional and be able%nl%to link with the nearest gate.");
        addItemWithCustomName(JSGItems.PEGASUS_DHD_ACTIVATION_BUTTON, "Bright Blue Button (BBB)");
        addItemWithCustomName(JSGItems.PEGASUS_DHD_UPGRADES_COVER, "Pegasus Upgrades Cover Plate");
        addBlockWithCustomNameAndTooltip(JSGBlocks.DHD_PEGASUS, "Pegasus Dial Home Device (DHD)", "Control panel for Pegasus gates", null);

        addItem(JSGItems.DHD_NAQUADAH_TANK);

        //ADDRESS AND UPGRADE CRYSTALS
        addItemWithTooltip(JSGItems.CRYSTAL_ADDRESS_DIALING_EXTENDED, "Upgrade for DHDs", "This crystal allows you to " + ChatFormatting.BOLD + "DIAL" + "%nl%8 and 9 symbol addresses when%nl%placed into a DHD.%nl%%nl%" + ChatFormatting.ITALIC +"This upgrade can be combined%nl%" + ChatFormatting.ITALIC + "with other DHD upgrades!");

        addItem(JSGItems.CRYSTAL_ADDRESS_STATIC);
        addItem(JSGItems.CRYSTAL_ADDRESS_EXTENDED);
        addItem(JSGItems.CRYSTAL_GLYPH_MILKYWAY);
        addItem(JSGItems.CRYSTAL_GLYPH_PEGASUS);
        addItem(JSGItems.CRYSTAL_GLYPH_UNIVERSE);

        addItemWithTooltip(JSGItems.UPGRADE_IRIS, "Iris upgrade tier I", "Iris works as in lore.%nl%Just install this into any gate.");
        addItemWithTooltip(JSGItems.UPGRADE_IRIS_TRINIUM, "Iris upgrade tier II", "Iris works as in lore.%nl%Just install this into any gate.");
        addItemWithTooltip(JSGItems.UPGRADE_SHIELD, "Iris upgrade tier III", "Shield blocks incoming travellers.%nl%Just install this into any gate.%nl%Instead of durability%nl%it consumes energy from the gate buffer.");
        addItemWithTooltip(JSGItems.UPGRADE_IRIS_CREATIVE, "Iris with infinite durability", null);

        //TOOLS
        addItemWithTooltip(JSGItems.ADMIN_CONTROLLER, "Stargate controller and debugger", "This item shows you whole SG network when linked to a gate.%nl%It's capable of dialing the linked gate%nl%by spinning the ring, locking symbols like DHD%nl%and dialing all symbols at once like Nox did in series.");
        addItemWithTooltip(JSGItems.UNIVERSE_DIALER, "A controller that works with Universe gates, along with much more!", null);
        addItemWithCustomNameAndTooltip(JSGItems.GDO, "Garage Door Opener (GDO)",  "GDO is capable of sending an IDC code through an open wormhole to open the iris.", null);

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

        add("item." + JSG.MOD_ID + ".incomplete_chocolate_bar", "Incomplete Chocolate Bar");

        //TABS
        add("itemGroup." + JSG.MOD_ID + ".machines", "JSG: Machines");

        //MAIN MENU
        add("menu.about", "About JSG Mod");
        add("menu.updater.download", "Download");
        add("menu.updater.close", "Close");
        add("menu.ram.help", "Help");
        add("menu.music.volume", "Music volume:");

        add("menu.tip.kawoosh", "TIP: Try to avoid standing in front of a Stargate as it activates.");
        add("menu.tip.unstable_eh", "TIP: Travelling through an unstable wormhole can result in death.");
        add("menu.tip.abydos_cartouche", "TIP: Explore Abydos to find cartouches with stargate addresses.");

        //GENERAL TOOLTIPS, HOTBAR MESSAGES
        add("item." + JSG.MOD_ID + ".dhd.assembly_helper.needed", "Missing %s");
        add("item." + JSG.MOD_ID + ".iris.tooltip.integrity", "Integrity:");
        add("item." + JSG.MOD_ID + ".iris.tooltip.durability", "Durability:");


        add("item." + JSG.MOD_ID + ".iris_blade_stone.tooltip", "Use this blade to craft cast");


        //SDG - STARGATE DIMENSION GENERATOR
        add("createWorld.stargates_generating", "Generating stargates in dimensions... %s%%");
        add("createWorld.stargates_generating.trying", "Trying generation in %s");
        add("createWorld.stargates_generating.searching", "Searching for structure in %s");
        add("createWorld.stargates_generating.generating", "Generating stargate in %s");
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

        add("createWorld.stargates_generating.dim_status.ok", "Generated");
        add("createWorld.stargates_generating.dim_status.already_there", "Already there");
        add("createWorld.stargates_generating.dim_status.no_structure", "No structure available");
        add("createWorld.stargates_generating.dim_status.skipped", "Skipped by config");
        add("createWorld.stargates_generating.dim_status.error", "Error");

        //IN-GAME CONFIG
        add("gui." + JSG.MOD_ID + ".config", "Just Stargate Mod Configuration");
        add("gui." + JSG.MOD_ID + ".config.child.debug", "Debug Settings");
        add("gui." + JSG.MOD_ID + ".config.child.general", "General Configuration");
        add("gui." + JSG.MOD_ID + ".config.child.dialhomedevide", "DHD Configuration");
        add("gui." + JSG.MOD_ID + ".config.child.stargate", "Stargates Configuration");
        add("gui." + JSG.MOD_ID + ".config.child.computersintegration", "Computers Integration Configuration");

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
        addEntity(JSGEntities.MASTADGE);

        addVillagerProfession(JSGVillagers.PRIEST_DESERT);
        addVillagerProfession(JSGVillagers.PRIEST_JUNGLE);
        addVillagerProfession(JSGVillagers.PRIEST_PLAINS);
        addVillagerProfession(JSGVillagers.PRIEST_SAVANNA);
        addVillagerProfession(JSGVillagers.PRIEST_SNOW);
        addVillagerProfession(JSGVillagers.PRIEST_SWAMP);
        addVillagerProfession(JSGVillagers.PRIEST_TAIGA);
        addVillagerProfession(JSGVillagers.SLAVE_MINER);

        addVillagerType(JSGVillagers.ABYDOS_TYPE);



                 /*"block.jsg.iris_block": "Iris / Shield block",
                "block.jsg.abydos_sand": "Sand",*/
    }
}
