package dev.tauri.jsg.datagen;

import dev.tauri.jsg.JSG;
import dev.tauri.jsg.common.registry.*;
import dev.tauri.jsgcore.common.registry.helper.RegistryObjectWrapper;
import dev.tauri.jsgcore.datagen.CoreLanguageProvider;
import net.minecraft.ChatFormatting;
import net.minecraft.data.PackOutput;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.entity.npc.VillagerType;
import net.minecraft.world.item.Item;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

public class JSGLanguageProvider extends CoreLanguageProvider {
    public JSGLanguageProvider(PackOutput output, String locate) {
        super(output, JSG.MOD_ID, locate);
    }

    protected void addCartridgeItem(RegistryObjectWrapper<? extends Item> object) {
        add(object.get(), pathToName(object.getId().getPath()));
        add(object.get().getDescriptionId() + ".empty", "Empty " + pathToName(object.getId().getPath()));
    }

    //TODO: Move AddVillagers and Subtitles to CORE
    protected void addVillagerProfession(RegistryObjectWrapper<? extends VillagerProfession> object) {
        var modID = object.getId().getNamespace();
        var key = "entity.minecraft.villager." + modID + "." + object.getId().getPath();
        var name = pathToName(object.getId().getPath());
        add(key, name);
    }

    protected void addVillagerType(RegistryObjectWrapper<VillagerType> object) {
        var modID = object.getId().getNamespace();

        var key = "entity.minecraft.villager." + modID + "." + object.getId().getPath();
        var name = pathToName(object.getId().getPath());
        add(key, name);
    }

    public void addAllSubtitles(Class<?> soundEventsClass) {
        for (Field field : soundEventsClass.getDeclaredFields()) {
            if (!Modifier.isPublic(field.getModifiers())) continue;
            if (!Modifier.isStatic(field.getModifiers())) continue;
            if (field.getType() != SoundEvent.class) continue;
            if (field.getName().startsWith("RECORD_") || field.getName().startsWith("SIREN_")) {continue;}

            try {
                var sound = (SoundEvent) field.get(null);
                var rl = sound.getLocation();
                var modId = rl.getNamespace();
                var path = rl.getPath();

                var formattedPath = path.replace('.', '_');
                var generatedSubtitle = pathToName(formattedPath);

                generatedSubtitle = generatedSubtitle
                        .replace("Block ", "")
                        .replace("Item ", "")
                        .replace("Misc ", "");

                add(modId + ".subtitle." + path, generatedSubtitle);

            } catch (IllegalAccessException e) {
                JSG.logger.error("Failed to automatically generate subtitle for field: {}", field.getName(), e);
            }
        }
    }

    @Override
    protected void addTranslations() {
        //SPECIAL BLOCKS
        addBlockWithCustomName(JSGBlocks.IRIS_BLOCK, "Iris / Shield block");
        addBlockWithCustomName(JSGBlocks.ABYDOS_SAND, "Sand");

        //UTILITIES BLOCKS
        addBlockWithTooltip(JSGBlocks.SG_REDSTONE_DIALER_I_BLOCK, "Dial a stargate using redstone signal", "To dial a symbol you need to set symbol ID%nl%using listed inputs then engage it.%nl%Selected symbol id = ((Right * 16) + Front)%nl%%nl%" + ChatFormatting.BOLD + "Inputs:" + "%nl%Top: Engage gate%nl%Bottom: Enable DHD Dialing animation%nl%Left: Engage symbol%nl%Right: 2nd bit of symbol ID%nl%Front: 1st bit of symbol ID");
        addBlockWithTooltip(JSGBlocks.SG_REDSTONE_STATE_O_BLOCK, "Get redstone output by current stargate state", ChatFormatting.BOLD + "Output:" + "%nl%0: Idle%nl%1: Dialing DHD%nl%2: Dialing Computer%nl%3: Engaged Incoming%nl%4: Engaged Outgoing%nl%5: Unstable%nl%6: Failing%nl%7: Incoming Wormhole");
        addBlockWithTooltip(JSGBlocks.TOASTER, "Orlin still owes Carter a new one.", null);
        addBlockWithTooltip(JSGBlocks.PRINTER, "Useful to print notebook pages", null);

        //STARGATE BLOCKS
        addBlockWithTooltip(JSGBlocks.STARGATE_MILKYWAY_BASE_BLOCK, "The main block of Milky Way gate", null);
        addBlockWithTooltip(JSGBlocks.STARGATE_MILKYWAY_CHEVRON_BLOCK, "The locking mechanism of Milky Way gate", null);
        addBlockWithTooltip(JSGBlocks.STARGATE_MILKYWAY_RING_BLOCK, "The construction block of Milky Way gate", null);
        addBlockWithTooltip(JSGBlocks.STARGATE_PEGASUS_BASE_BLOCK, "The main block of Pegasus gate", null);
        addBlockWithTooltip(JSGBlocks.STARGATE_PEGASUS_CHEVRON_BLOCK, "The locking mechanism of Pegasus gate", null);
        addBlockWithTooltip(JSGBlocks.STARGATE_PEGASUS_RING_BLOCK, "The construction block of Pegasus gate", null);
        addBlockWithTooltip(JSGBlocks.STARGATE_UNIVERSE_BASE_BLOCK, "The main block of Universe gate", null);
        addBlockWithTooltip(JSGBlocks.STARGATE_UNIVERSE_CHEVRON_BLOCK, "The locking mechanism of Universe gate", null);
        addBlockWithTooltip(JSGBlocks.STARGATE_UNIVERSE_RING_BLOCK, "The construction block of Universe gate", null);
        addBlockWithTooltip(JSGBlocks.STARGATE_MOVIE_BASE_BLOCK, "The main block of Movie gate", null);
        addBlockWithTooltip(JSGBlocks.STARGATE_MOVIE_CHEVRON_BLOCK, "The locking mechanism of Movie gate", null);
        addBlockWithTooltip(JSGBlocks.STARGATE_MOVIE_RING_BLOCK, "The construction block of Movie gate", null);
        addBlockWithTooltip(JSGBlocks.STARGATE_TOLLAN_BASE_BLOCK, "The main block of Tollan gate", null);
        addBlockWithTooltip(JSGBlocks.STARGATE_TOLLAN_CHEVRON_BLOCK, "The locking mechanism of Tollan gate", null);
        addBlockWithTooltip(JSGBlocks.STARGATE_TOLLAN_RING_BLOCK, "The construction block of Tollan gate", null);
        add("block." + JSG.MOD_ID + ".test", "You need this block to complete building a new stargate.");
        add("block." + JSG.MOD_ID + ".test1", "Place this block first when building a stargate.%nl%When placed, it will show you a diagram%nl%of how the stargate should be built.");

        addBlockWithCustomNameAndTooltip(JSGBlocks.STARGATE_ORLIN_BASE_BLOCK, "Orlin's Stargate Base", "The main block of Orlin's gate", null);
        addBlockWithCustomNameAndTooltip(JSGBlocks.STARGATE_ORLIN_MEMBER_BLOCK, "Orlin's Stargate Ring", "The construction block of Orlin's gate", null);
        add("block." + JSG.MOD_ID + ".stargate_orlin_base_block.open_count", "Open count: %s / %s");

        //ICONS FOR ADVANCEMENTS
        addItem(JSGItems.ICON_KAWOOSH_DEATH);
        addItem(JSGItems.ICON_WORMHOLE);
        addItem(JSGItems.ICON_RA_REALM);
        addItem(JSGItems.ICON_PRIEST_TRADE);

        //DHD PARTS
        addItem(JSGItems.MILKYWAY_DHD_CONTROL_CRYSTALS);
        addItem(JSGItems.MILKYWAY_DHD_BUTTONS_CONSOLE);
        addItemWithCustomNameAndTooltip(JSGItems.MILKYWAY_DHD_MAIN_CRYSTAL, "Milky Way DHD Main Control Crystal", "Main control crystal of DHD", "Insert this crystal into DHD (Dial Home Device)%nl%to make the DHD functional and be able%nl%to link with the nearest gate.");
        addItemWithCustomName(JSGItems.MILKYWAY_DHD_ACTIVATION_BUTTON, "Bright Red Button (BRB)");
        addItemWithCustomName(JSGItems.MILKYWAY_DHD_UPGRADES_COVER, "Milky Way Upgrades Cover Plate");
        addBlockWithCustomNameAndTooltip(JSGBlocks.DHD_MILKYWAY, "Milky Way Home Device (DHD)", "Control panel for Milky Way, Tollan and Movie gates", null);


        addItem(JSGItems.PEGASUS_DHD_CONTROL_CRYSTALS);
        addItem(JSGItems.PEGASUS_DHD_BUTTONS_CONSOLE);
        addItemWithCustomNameAndTooltip(JSGItems.PEGASUS_DHD_MAIN_CRYSTAL, "Pegasus DHD Main Control Crystal", "Main control crystal of DHD", "Insert this crystal into DHD (Dial Home Device)%nl%to make the DHD functional and be able%nl%to link with the nearest gate.");
        addItemWithCustomName(JSGItems.PEGASUS_DHD_ACTIVATION_BUTTON, "Bright Blue Button (BBB)");
        addItemWithCustomName(JSGItems.PEGASUS_DHD_UPGRADES_COVER, "Pegasus Upgrades Cover Plate");
        addBlockWithCustomNameAndTooltip(JSGBlocks.DHD_PEGASUS, "Pegasus Dial Home Device (DHD)", "Control panel for Pegasus gates", null);

        addItem(JSGItems.DHD_NAQUADAH_TANK);

        add("item." + JSG.MOD_ID + ".dhd.assembly_helper.needed", "Missing %s");

        //ADDRESS AND UPGRADE CRYSTALS
        addItemWithTooltip(JSGItems.CRYSTAL_ADDRESS_DIALING_EXTENDED, "Upgrade for DHDs", "This crystal allows you to " + ChatFormatting.BOLD + "DIAL" + "%nl%8 and 9 symbol addresses when%nl%placed into a DHD.%nl%%nl%" + ChatFormatting.ITALIC + "This upgrade can be combined%nl%" + ChatFormatting.ITALIC + "with other DHD upgrades!");

        addItemWithTooltip(JSGItems.CRYSTAL_ADDRESS_STATIC, "Upgrade for Stargates", "This crystal allows you to " + ChatFormatting.BOLD + "ONLY DISPLAY" + "%nl%symbols of the gate's 9 symbols static address when%nl%placed into a stargate.%nl%%nl%§oThis upgrade can be combined%nl%%nl%" + ChatFormatting.ITALIC + "with other stargate upgrades!");
        addItemWithTooltip(JSGItems.CRYSTAL_ADDRESS_EXTENDED, "Upgrade for Stargates", "This crystal allows you to " + ChatFormatting.BOLD + "ONLY DISPLAY" + "%nl%8th symbol of the gate's address when%nl%placed into a stargate.%nl%%nl%§oThis upgrade can be combined%nl%%nl%" + ChatFormatting.ITALIC + "with other stargate upgrades!");
        addItemWithTooltip(JSGItems.CRYSTAL_GLYPH_MILKYWAY, "Upgrade for Stargates", "This crystal allows you to " + ChatFormatting.BOLD + "ONLY DISPLAY" + "%nl%gate's Milky Way address when%nl%placed into a stargate.%nl%%nl%" + ChatFormatting.ITALIC + "with other stargate upgrades!");
        addItemWithTooltip(JSGItems.CRYSTAL_GLYPH_PEGASUS, "Upgrade for Stargates", "This crystal allows you to " + ChatFormatting.BOLD + "ONLY DISPLAY" + "%nl%gate's Pegasus address when%nl%placed into a stargate.%nl%%nl%" + ChatFormatting.ITALIC + "with other stargate upgrades!");
        addItemWithTooltip(JSGItems.CRYSTAL_GLYPH_UNIVERSE, "Upgrade for Stargates", "This crystal allows you to " + ChatFormatting.BOLD + "ONLY DISPLAY" + "%nl%gate's Universe address when%nl%placed into a stargate.%nl%%nl%" + ChatFormatting.ITALIC + "with other stargate upgrades!");

        addItemWithTooltip(JSGItems.UPGRADE_IRIS, "Iris upgrade tier I", "Iris works as in lore.%nl%Just install this into any gate.");
        addItemWithTooltip(JSGItems.UPGRADE_IRIS_TRINIUM, "Iris upgrade tier II", "Iris works as in lore.%nl%Just install this into any gate.");
        addItemWithTooltip(JSGItems.UPGRADE_SHIELD, "Iris upgrade tier III", "Shield blocks incoming travellers.%nl%Just install this into any gate.%nl%Instead of durability%nl%it consumes energy from the gate buffer.");
        addItemWithTooltip(JSGItems.UPGRADE_IRIS_CREATIVE, "Iris with infinite durability", null);

        add("item." + JSG.MOD_ID + ".iris.tooltip.integrity", "Integrity:");
        add("item." + JSG.MOD_ID + ".iris.tooltip.durability", "Durability:");

        //TOOLS
        addItemWithTooltip(JSGItems.ADMIN_CONTROLLER, "Stargate controller and debugger", "This item shows you whole SG network when linked to a gate.%nl%It's capable of dialing the linked gate%nl%by spinning the ring, locking symbols like DHD%nl%and dialing all symbols at once like Nox did in series.");
        addItemWithTooltip(JSGItems.UNIVERSE_DIALER, "A controller that works with Universe gates, along with much more!", null);
        addItemWithCustomNameAndTooltip(JSGItems.GDO, "Garage Door Opener (GDO)", "GDO is capable of sending an IDC code through an open wormhole to open the iris.", null);
        addItemWithTooltip(JSGItems.DIALING_GLOVES, "Allows to manually dial Milky Way stargate", null);

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
        add("item." + JSG.MOD_ID + ".iris_blade_stone.tooltip", "Use this blade to craft cast");

        add("item." + JSG.MOD_ID + ".incomplete_chocolate_bar", "Incomplete Chocolate Bar");

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

        //TABS
        add("itemGroup." + JSG.MOD_ID + ".machines", "JSG: Machines");


        //ADVANCEMENTS
        addAdvancement(JSGAdvancements.CRAFT_PRINTER, "Not enough Magenta!" , "Works like regular printer, but it's more advanced");
        addAdvancement(JSGAdvancements.TRADE_WITH_PRIEST, "Sacrifices to their gods" , "Trade with Priest Villager");
        addAdvancement(JSGAdvancements.VISIT_ABYDOS, "Welcome to Ra's realm" , "Visit Abydos");
        addAdvancement(JSGAdvancements.USE_GDO, "Code accepted!" , "Use a GDO to open the iris");
        addAdvancement(JSGAdvancements.OBTAIN_TOASTER, "Carter's Toaster" , "You wouldn't believe the things that you can make from common items.");
        addAdvancement(JSGAdvancements.ACTIVATE_ORLIN_GATE, "So you built one?" , "Ohh... and you're going to need a new toaster!");
        addAdvancement(JSGAdvancements.OBTAIN_EXTENDED_ADDRESS_CRYSTAL, "Not Incorrect, Incomplete" , "Obtain Extended Address Crystal");
        addAdvancement(JSGAdvancements.OBTAIN_EXTENDED_ADDRESS_DIALING_CRYSTAL, "We Determined 7th Symbol" , "Obtain Extended Address Dialing Crystal");
        addAdvancement(JSGAdvancements.OBTAIN_STATIC_ADDRESS_CRYSTAL, "Its Not Address, Its a Code" , "Obtain Static Address Crystal");
        addAdvancement(JSGAdvancements.ACTIVATE_MOVIE_GATE, "Giza, 1928" , "Activate Movie Stargate");
        addAdvancement(JSGAdvancements.WORMHOLE_GO, "What's a wormhole? And why a worm?" , "Enter the Event Horizon of a Stargate");
        addAdvancement(JSGAdvancements.UNSTABLE_GO, "Unstable Wormhole?!" , "For a moment i thought we were in trouble");
        addAdvancement(JSGAdvancements.ACTIVATE_MILKYWAY_GATE, "Pyramid with Sun above it" , "Activate Milky Way Stargate");
        addAdvancement(JSGAdvancements.ACTIVATE_TOLLAN_GATE, "Ours is bigger" , "Activate Tollan Stargate");
        addAdvancement(JSGAdvancements.KAWOOSH_DEATH, "Escaping Hadante" , "Get cremated by the Unstable Vortex");
        addAdvancement(JSGAdvancements.IRIS_IMPACT, "Like bugs on a windshield" , "Witness an object hit the iris");
        addAdvancement(JSGAdvancements.HIT_WITH_GLOVES, "Gotta bend those kosars" , "Hit player or mob while wearing dialing gloves");
        addAdvancement(JSGAdvancements.ACTIVATE_PEGASUS_GATE, "Bon Voyage!" , "Activate Pegasus Stargate");
        addAdvancement(JSGAdvancements.ACTIVATE_UNIVERSE_GATE, "Several billion light years" , "Activate Universe Stargate");
        addAdvancement(JSGAdvancements.CRAFT_SHIELD, "Using power, using power..." , "Obtain stargate shield");

        //CHAT AND HOTBAR MESSAGES:
        add("chat.orlins.energyStored", "Energy stored: %s FE (Required: %s FE, %s s");

        add("block." + JSG.MOD_ID + ".dhd_milkyway.no_crystal_warn", "DHD has no control crystal");
        add("block." + JSG.MOD_ID + ".dhd_milkyway.not_linked_warn", "DHD is not linked to the gate");
        add("block." + JSG.MOD_ID + ".dhd_milkyway.not_enough_power", "Insufficient power");
        add("block." + JSG.MOD_ID + ".dhd_milkyway.incoming_wormhole_warn", "Unable to close an incoming wormhole");
        add("block." + JSG.MOD_ID + ".dhd_milkyway.computer_dial", "Computer dial is in progress");
        add("tile." + JSG.MOD_ID + ".dhd_block.incoming_wormhole_warn", "Unable to close an incoming wormhole");

        add("item." + JSG.MOD_ID + ".universe_dialer.dial_start", "Dialing the gate...");
        add("item." + JSG.MOD_ID + ".universe_dialer.aborting", "Aborting dial");
        add("item." + JSG.MOD_ID + ".universe_dialer.gate_busy", "Stargate is busy");


        add("block." + JSG.MOD_ID + ".printer.empty_input", "Insert a notebook page");
        add("block." + JSG.MOD_ID + ".printer.no_ink.general", "Cartridge is missing!");
        add("block." + JSG.MOD_ID + ".printer.no_ink.black", "Not enough black color!");
        add("block." + JSG.MOD_ID + ".printer.no_ink.cyan", "Not enough cyan color!");
        add("block." + JSG.MOD_ID + ".printer.no_ink.magenta", "Not enough magenta color!");
        add("block." + JSG.MOD_ID + ".printer.no_ink.yellow", "Not enough yellow color!");
        add("block." + JSG.MOD_ID + ".printer.busy", "Printer is busy");
        add("block." + JSG.MOD_ID + ".printer.full_output", "Take the printed page from the printer");

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

        //STARGATE GUI
        add("gui.stargate.energy_crystals", "Energy Crystals");
        add("gui.stargate." + JSG.MOD_ID + ".milkyway.title", "Milky Way");
        add("gui.stargate." + JSG.MOD_ID + ".pegasus.title", "Pegasus");
        add("gui.stargate." + JSG.MOD_ID + ".universe.title", "Universe");
        add("gui.stargate." + JSG.MOD_ID + ".tollan.title", "Tollan");
        add("gui.stargate." + JSG.MOD_ID + ".movie.title", "Movie");

        add("gui.stargate." + JSG.MOD_ID + ".milkyway_address", "Milky Way address");
        add("gui.stargate." + JSG.MOD_ID + ".pegasus_address", "Pegasus address");
        add("gui.stargate." + JSG.MOD_ID + ".universe_address", "Universe address");

        add("gui.stargate.iris_code", "Iris Code");
        add("gui.stargate.iris.help_title", "Current mode:");
        add("gui.stargate.iris.opened", "Open");
        add("gui.stargate.iris.closed", "Closed");
        add("gui.stargate.iris.auto", "Automatic");
        add("gui.stargate.iris.oc", "CC / OC");
        add("gui.stargate.iris.opened_help", "Iris is permanently open");
        add("gui.stargate.iris.closed_help", "Iris is permanently closed");
        add("gui.stargate.iris.auto_help", "Iris automatically closes on incoming wormhole,");
        add("gui.stargate.iris.auto1_help", "it can be opened with GDO by sending code set in field");
        add("gui.stargate.iris.oc_help", "Iris can be controlled by CC or OC computers");

        add("gui.stargate.info", "Stargate information");
        add("gui.stargate.state_closed", "Stargate closed!");
        add("gui.stargate.state_opened", "Open time:");
        add("gui.stargate.state.gate_temp", "Gate temp:");
        add("gui.stargate.state.iris_temp", "Iris temp:");

        //GDO - GUI
        add("item." + JSG.MOD_ID + ".gdo.in_menu.alert", "Type code or press ESC");
        add("item." + JSG.MOD_ID + ".gdo.code_accepted", "CODE INVALID");
        add("item." + JSG.MOD_ID + ".gdo.iris_busy", "Iris is busy!");
        add("item." + JSG.MOD_ID + ".gdo.iris_opened", "Iris is open!");
        add("item." + JSG.MOD_ID + ".gdo.operator", "T-Mobile 5G");
        add("item." + JSG.MOD_ID + ".gdo.code", "Code:");

        //UNIVERSE DIALER - GUI
        add("item." + JSG.MOD_ID + ".universe_dialer.in_menu.alert", "Edit entry name or press ESC");
        add("item." + JSG.MOD_ID + ".universe_dialer.saved_gates", "Saved gates: %s");
        add("item." + JSG.MOD_ID + ".universe_dialer.mode_scan", "Nearby");
        add("item." + JSG.MOD_ID + ".universe_dialer.mode_saved", "Memory");
        add("item." + JSG.MOD_ID + ".universe_dialer.mode_info", "Status");
        add("item." + JSG.MOD_ID + ".universe_dialer.manual_dialing", "Dialing");
        add("item." + JSG.MOD_ID + ".universe_dialer.manula_dialing.delete", "Delete");

        //ADMIN CONTROLLER - GUI
        add("gui.admincontroller.name","Admin Controller");

        add("gui.admincontroller.tab.dialing.name","Dialling");
        add("gui.admincontroller.tab.diagnostics.name","Diagnostics");
        add("gui.admincontroller.tab.network.name","Network");

        add("gui.admincontroller.tab.dialing.gate.not_linked","OFFLINE");
        add("gui.admincontroller.tab.dialing.abort.name","Abort dialing");
        add("gui.admincontroller.tab.dialing.close.name","Close stargate");
        add("gui.admincontroller.tab.dialing.iris.name","Toggle the gate's iris");

        add("gui.admincontroller.tab.diagnostics.state","Stargate status:");
        add("gui.admincontroller.tab.diagnostics.subspace","Subspace status:");
        add("gui.admincontroller.tab.diagnostics.energy","Energy (FE):");
        add("gui.admincontroller.tab.diagnostics.energy_consumption","Energy consumption (FE/tick):");
        add("gui.admincontroller.tab.diagnostics.seconds_open","Open time:");
        add("gui.admincontroller.tab.diagnostics.seconds_to_close","Seconds until closure:");

        add("gui.admincontroller.tab.network.address_list.name","Addresses");
        add("gui.admincontroller.tab.network.map.name","Network map");
        add("gui.admincontroller.tab.network.map.you_are_here","You are here");
        add("gui.admincontroller.tab.network.map.stargate.name","Name:");
        add("gui.admincontroller.tab.network.map.stargate.pos","Position:");
        add("gui.admincontroller.tab.network.map.stargate.type","Type:");
        add("gui.admincontroller.tab.network.map.stargate.dimension","Dimension:");
        add("gui.admincontroller.tab.network.dimension_select.all.name","All dimensions");
        add("gui.admincontroller.tab.network.address_list.slow_dail.name","Slow dialing");
        add("gui.admincontroller.tab.network.address_list.fast_dial.name","Fast dialing");
        add("gui.admincontroller.tab.network.address_list.nox_dialing.name","Nox dialing");
        add("gui.admincontroller.tab.network.address_list.address.name","Get address");
        add("gui.admincontroller.tab.network.address_list.teleport.name","Teleport to gate");

        add("gui.admincontroller.response.error","✘");
        add("gui.admincontroller.response.success","✔");
        add("gui.admincontroller.response.stargate.not_linked.error","Controller not linked to a gate");
        add("gui.admincontroller.response.stargate.not_merged.error","Stargate must be merged");
        add("gui.admincontroller.response.stargate.dial_address.error.busy","Stargate is busy");
        add("gui.admincontroller.response.stargate.dial_address.error.already_engaged","One or more symbols have already been engaged");
        add("gui.admincontroller.response.stargate.dial_address.error.blocked_by_event","Dialling blocked by an error (Check logs)");
        add("gui.admincontroller.response.stargate.dial_address.success","Begun dialling...");
        add("gui.admincontroller.stargate.give_address.success","Giving address book...");
        add("gui.admincontroller.response.stargate.abort.error","Stargate is not dialing");
        add("gui.admincontroller.response.stargate.abort.success","Dialing aborted");
        add("gui.admincontroller.response.stargate.iris.empty","Stargate doesn't have an iris");
        add("gui.admincontroller.response.stargate.iris.busy","Stargate's iris is busy");
        add("gui.admincontroller.response.stargate.iris.close","Closing the iris...");
        add("gui.admincontroller.response.stargate.iris.open","Opening the iris...");
        add("gui.admincontroller.response.stargate.close.success","Closing gate...");
        add("gui.admincontroller.response.stargate.close.error.not_open","Stargate is not open");
        add("gui.admincontroller.response.stargate.close.error.blocked_by_event","Closing blocked by an error (Check logs)");

        //CONFIG
        add("key.categories." + JSG.MOD_ID, "Just Stargate Mod");
        add("config." + JSG.MOD_ID + ".address.down", "Universe dialer address down");
        add("config." + JSG.MOD_ID + ".address.edit", "Open address edit menu");
        add("config." + JSG.MOD_ID + ".address.scroll", "Universe dialer address scroll");
        add("config." + JSG.MOD_ID + ".address.up", "Universe dialer address up");
        add("config." + JSG.MOD_ID + ".mode.down", "Universe dialer mode down");
        add("config." + JSG.MOD_ID + ".mode.scroll", "Universe dialer mode scroll");
        add("config." + JSG.MOD_ID + ".mode.up", "Universe dialer mode up");
        add("config." + JSG.MOD_ID + ".rename.entry", "Universe dialer rename entry");

        //DEATH MESSAGES
        add("death.attack.kawoosh", "%s was disintegrated by the Unstable Vortex");
        add("death.attack.unstable_eh", "%s didn't make it to the other side");
        add("death.attack.wrong_side", "%s entered the wrong side of the gate");
        add("death.attack.iris", "%s slammed into the Iris");

        //COMMANDS
        add("commands.prepare.not_preparable","Targeted block is not prepare-able");
        add("commands.prepare.success","Blocks successfully prepared for saving into NBT");
        add("commands.prepare.error","Error while preparing block to saving into NBT");
        add("commands.rig.not_stargate","No Stargate nearby that could initiate RIG");
        add("commands.rig.already.active","Stargate already has an active RIG");
        add("commands.sgsetaddress.notstargate","Target block isn't stargate base block");
        add("commands.sgsetaddress.wrongsymbol","Wrong glyph name or Map");
        add("commands.sgsetaddress.success","Target stargate address is successfully changed");

        //SUBTITLES
        addAllSubtitles(JSGSoundEvents.class);

    }
}
