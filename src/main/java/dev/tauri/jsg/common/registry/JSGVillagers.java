package dev.tauri.jsg.common.registry;

import com.google.common.collect.ImmutableSet;
import dev.tauri.jsg.JSG;
import dev.tauri.jsg.api.JSGApi;
import dev.tauri.jsg.common.entity.VillagerTradesBuilder;
import dev.tauri.jsg.common.registry.tags.JSGBiomeTags;
import dev.tauri.jsg.common.registry.tags.JSGStructureTags;
import dev.tauri.jsgcore.common.definition.crystal.CrystalType;
import dev.tauri.jsgcore.common.definition.material.MaterialItemType;
import dev.tauri.jsgcore.common.definition.material.MaterialOreVariant;
import dev.tauri.jsgcore.common.definition.material.MaterialType;
import dev.tauri.jsgcore.common.helper.ItemHelper;
import dev.tauri.jsgcore.common.registry.CoreBlocks;
import dev.tauri.jsgcore.common.registry.CoreItems;
import dev.tauri.jsgcore.common.registry.helper.DeferredRegisterWrapper;
import dev.tauri.jsgcore.common.registry.helper.RegistryObjectWrapper;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.entity.npc.VillagerType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.saveddata.maps.MapDecoration;
import net.minecraftforge.event.village.VillagerTradesEvent;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;

public class JSGVillagers {
    private static final DeferredRegisterWrapper<VillagerProfession> PROFESSION_REGISTER = JSGApi.REGISTRY_HELPER.villagerProfession();
    private static final DeferredRegisterWrapper<VillagerType> TYPE_REGISTER = JSGApi.REGISTRY_HELPER.villagerType();
    private static final DeferredRegisterWrapper<PoiType> POI_REGISTER = JSGApi.REGISTRY_HELPER.poi();

    public static final RegistryObjectWrapper<PoiType> NAQUADAH_CAULDRON_POI = POI_REGISTER.register("naquadah_cauldron", () -> new PoiType(ImmutableSet.copyOf(CoreBlocks.CAULDRON.get().getStateDefinition().getPossibleStates()), 4, 1));

    public static final RegistryObjectWrapper<VillagerProfession> PRIEST_DESERT = PROFESSION_REGISTER.register("desert_priest", () -> new VillagerProfession("priest_desert", holder -> false, holder -> false, ImmutableSet.of(), ImmutableSet.of(), null));
    public static final RegistryObjectWrapper<VillagerProfession> PRIEST_JUNGLE = PROFESSION_REGISTER.register("jungle_priest", () -> new VillagerProfession("priest_jungle", holder -> false, holder -> false, ImmutableSet.of(), ImmutableSet.of(), null));
    public static final RegistryObjectWrapper<VillagerProfession> PRIEST_PLAINS = PROFESSION_REGISTER.register("plains_priest", () -> new VillagerProfession("priest_plains", holder -> false, holder -> false, ImmutableSet.of(), ImmutableSet.of(), null));
    public static final RegistryObjectWrapper<VillagerProfession> PRIEST_SAVANNA = PROFESSION_REGISTER.register("savanna_priest", () -> new VillagerProfession("priest_savanna", holder -> false, holder -> false, ImmutableSet.of(), ImmutableSet.of(), null));
    public static final RegistryObjectWrapper<VillagerProfession> PRIEST_SNOW = PROFESSION_REGISTER.register("snow_priest", () -> new VillagerProfession("priest_snow", holder -> false, holder -> false, ImmutableSet.of(), ImmutableSet.of(), null));
    public static final RegistryObjectWrapper<VillagerProfession> PRIEST_SWAMP = PROFESSION_REGISTER.register("swamp_priest", () -> new VillagerProfession("priest_swamp", holder -> false, holder -> false, ImmutableSet.of(), ImmutableSet.of(), null));
    public static final RegistryObjectWrapper<VillagerProfession> PRIEST_TAIGA = PROFESSION_REGISTER.register("taiga_priest", () -> new VillagerProfession("priest_taiga", holder -> false, holder -> false, ImmutableSet.of(), ImmutableSet.of(), null));

    public static final RegistryObjectWrapper<VillagerProfession> SLAVE_MINER = PROFESSION_REGISTER.register("slave_miner", () -> new VillagerProfession("slave_miner", holder -> holder.is(Objects.requireNonNull(NAQUADAH_CAULDRON_POI.getKey())), holder ->
            holder.is(Objects.requireNonNull(NAQUADAH_CAULDRON_POI.getKey())),
            ImmutableSet.of(Items.IRON_PICKAXE, MaterialType.NAQUADAH.getItem(MaterialItemType.RAW).get()),
            ImmutableSet.copyOf(Arrays.stream(MaterialOreVariant.values()).map(v -> MaterialType.NAQUADAH.getOre(v).get()).toList()), null));

    public static final RegistryObjectWrapper<VillagerType> ABYDOS_TYPE = TYPE_REGISTER.register("abydonian", () -> new VillagerType("abydonian"));


    public static void init() {
        // this stuff here is to initialize the fucking TRADES map BEFORE our custom villager types are registered into the minecraft registry...
        // it's because Mojang thought that if there is type that is not in their fucking default, it's good idea to throw an exception while init of the TRADES map...
        // to actually fix the trades for the FISHERMAN (that is causing the exception), we need than to manually override its trades in the VillagerTradesEvent... well done Mojang
        if (VillagerTrades.TRADES.isEmpty())
            JSG.logger.info("Trades of villagers is empty map!");
    }

    @NotNull
    public static Optional<VillagerType> checkAndGetCustomType(Villager villager) {
        var biome = villager.level().getBiome(villager.blockPosition());
        if (biome.is(JSGBiomeTags.IS_ABYDOS)) {
            return Optional.of(ABYDOS_TYPE.get());
        }
        return Optional.empty();
    }


    public static boolean isPriest(VillagerProfession profession) {
        return profession == PRIEST_DESERT.get() || profession == PRIEST_JUNGLE.get() || profession == PRIEST_PLAINS.get()
                || profession == PRIEST_SAVANNA.get() || profession == PRIEST_SNOW.get() || profession == PRIEST_SWAMP.get() || profession == PRIEST_TAIGA.get();
    }

    public static void registerTrades(VillagerTradesEvent event) {
        VillagerTradesBuilder.create(event)
                .setForLevels(5)
                .setForProfessions(VillagerProfession.CARTOGRAPHER)
                .register((trader, random) -> {
                     if (!(trader.level() instanceof ServerLevel serverLevel))
                        return null;
                     if(serverLevel.dimension() == JSGDimensions.ABYDOS) {
                        var map = ItemHelper.getMapForTarget(JSGStructureTags.ABYDOS_MARKED_ON_MAP, Component.translatable("filled_map.abydos_treasure"), MapDecoration.Type.RED_X, serverLevel, trader.blockPosition());
                        if(map == null) return null;
                        return new MerchantOffer(new ItemStack(Items.FLINT_AND_STEEL), new ItemStack(JSGItems.FOOD_CHOCOLATE_BAR.get(), 24), map, 3, 2, 0.1F);
                     }
                     if(serverLevel.dimension() == Level.OVERWORLD) {
                        var map = ItemHelper.getMapForTarget(JSGStructureTags.OVERWORLD_MARKED_ON_MAP, Component.translatable("filled_map.burried_stargate"), MapDecoration.Type.RED_X, serverLevel, trader.blockPosition());
                        if(map == null) return null;
                        return new MerchantOffer(new ItemStack(Items.BOOKSHELF, 16), new ItemStack(Items.GOLDEN_APPLE, 8), map, 3, 2, 0.1F);
                     }
                     return null;
                })

                .setInput1(new ItemStack(MaterialType.TRINIUM.getItem(MaterialItemType.INGOT).get(), 20))
                .setOutput(new ItemStack(Items.EMERALD, 1))
                .setMaxUses(12)
                .setXpPerTrade(1)
                .setPriceMul(0.02f)
                .setForLevels(1)
                .setPriestOnly()
                .register()

                .setInput1(new ItemStack(Items.EMERALD, 1))
                .setInput2(new ItemStack(Items.COCOA_BEANS, 3))
                .setOutput(new ItemStack(JSGItems.FOOD_CHOCOLATE_BAR.get(), 6))
                .setMaxUses(12)
                .setXpPerTrade(1)
                .setPriceMul(0.02f)
                .setForLevels(1)
                .setPriestOnly()
                .register()

                .setInput1(new ItemStack(MaterialType.NAQUADAH.getItem(MaterialItemType.RAW).get(), 24))
                .setOutput(new ItemStack(Items.EMERALD, 1))
                .setMaxUses(12)
                .setXpPerTrade(1)
                .setPriceMul(0.02f)
                .setForLevels(1)
                .setPriestOnly()
                .register()

                .setInput1(new ItemStack(Items.EMERALD,1))
                .setInput2(new ItemStack(Items.PAPER,4))
                .setOutput(new ItemStack(CoreItems.NOTEBOOK_PAGE_EMPTY.get(), 8))
                .setMaxUses(12)
                .setXpPerTrade(1)
                .setPriceMul(0.02f)
                .setForLevels(1)
                .setPriestOnly()
                .register()

                .setInput1(new ItemStack(Items.EMERALD, 2))
                .setOutput(new ItemStack(Items.CALCITE,12))
                .setMaxUses(12)
                .setXpPerTrade(1)
                .setPriceMul(0.02f)
                .setForLevels(1)
                .setPriestOnly()
                .register()

                .setInput1(new ItemStack(Items.EMERALD, 1))
                .setOutput(new ItemStack(Items.APPLE,4))
                .setMaxUses(12)
                .setXpPerTrade(1)
                .setPriceMul(0.02f)
                .setForLevels(1)
                .setPriestOnly()
                .register()

                .setInput1(new ItemStack(Items.EMERALD, 1))
                .setInput2(new ItemStack(CoreItems.SLICE_LEMON.get(),6))
                .setOutput(new ItemStack(Items.MUSHROOM_STEW,1))
                .setMaxUses(5)
                .setXpPerTrade(15)
                .setPriceMul(0.05f)
                .setForLevels(2)
                .setPriestOnly()
                .register()

                .setInput1(new ItemStack(Items.CALCITE,4))
                .setInput2(new ItemStack(Items.EMERALD,1))
                .setOutput(new ItemStack(CoreItems.BLACK_CHALK.get(),1))
                .setMaxUses(12)
                .setXpPerTrade(10)
                .setPriceMul(0.02f)
                .setForLevels(3)
                .setPriestOnly()
                .register()

                .setInput1(new ItemStack(Items.EMERALD,6))
                .setInput2(new ItemStack(CrystalType.PEGASUS.getCrystalItem().get(),4))
                .setOutput(new ItemStack(JSGItems.PEGASUS_DHD_MAIN_CRYSTAL.get(),1))
                .setMaxUses(6)
                .setXpPerTrade(20)
                .setPriceMul(0.02f)
                .setForLevels(3)
                .setPriestOnly()
                .register()

                .setInput1(new ItemStack(Items.EMERALD,1))
                .setOutput(new ItemStack(Items.BREAD,6))
                .setMaxUses(12)
                .setXpPerTrade(10)
                .setPriceMul(0.02f)
                .setForLevels(3)
                .setPriestOnly()
                .register()

                .setInput1(new ItemStack(Items.IRON_INGOT, 8))
                .setInput2(new ItemStack(Items.EMERALD,1))
                .setOutput(new ItemStack(Items.CAULDRON,1))
                .setMaxUses(12)
                .setXpPerTrade(15)
                .setPriceMul(0.02f)
                .setForLevels(4)
                .setPriestOnly()
                .register()

                .setInput1(new ItemStack(Items.EMERALD,4))
                .setInput2(new ItemStack(MaterialType.TRINIUM.getItem(MaterialItemType.INGOT).get(),2))
                .setOutput(new ItemStack(CoreItems.CIRCUIT_CONTROL_CRYSTAL.get(),1))
                .setMaxUses(12)
                .setXpPerTrade(15)
                .setPriceMul(0.02f)
                .setForLevels(4)
                .setPriestOnly()
                .register()

                .setInput1(new ItemStack(Items.EMERALD, 4))
                .setInput2(new ItemStack(MaterialType.NAQUADAH_ALLOY.getItem(MaterialItemType.INGOT).get(),3))
                .setOutput(new ItemStack(CoreItems.CIRCUIT_CONTROL_NAQUADAH.get(),1))
                .setMaxUses(12)
                .setXpPerTrade(15)
                .setPriceMul(0.02f)
                .setForLevels(4)
                .setPriestOnly()
                .register()

                .setInput1(new ItemStack(Items.EMERALD,4))
                .setOutput(new ItemStack(MaterialType.NAQUADAH_ALLOY.getItem(MaterialItemType.INGOT).get(),3))
                .setMaxUses(10)
                .setXpPerTrade(10)
                .setPriceMul(0.02f)
                .setForLevels(4)
                .setPriestOnly()
                .register()

                .setInput1(new ItemStack(Items.EMERALD, 4))
                .setOutput(new ItemStack(CrystalType.RED.getCrystalItem().get(),1))
                .setMaxUses(10)
                .setXpPerTrade(30)
                .setPriceMul(0.02f)
                .setForLevels(5)
                .setPriestOnly()
                .register()

                .setInput1(new ItemStack(Items.EMERALD,2))
                .setInput2(new ItemStack(CrystalType.RED.getCrystalItem().get(),1))
                .setOutput(new ItemStack(CrystalType.RED.getSmallCrystalItem().get(),2))
                .setMaxUses(12)
                .setXpPerTrade(30)
                .setPriceMul(0.02f)
                .setForLevels(5)
                .setPriestOnly()
                .register()

                .setInput1(new ItemStack(Items.EMERALD, 3))
                .setOutput(new ItemStack(CrystalType.PEGASUS.getCrystalItem().get(),2))
                .setMaxUses(4)
                .setXpPerTrade(30)
                .setPriceMul(0.02f)
                .setForLevels(5)
                .setPriestOnly()
                .register()

                .setInput1(new ItemStack(Items.EMERALD,2))
                .setInput2(new ItemStack(CrystalType.PEGASUS.getCrystalItem().get(),1))
                .setOutput(new ItemStack(CrystalType.PEGASUS.getSmallCrystalItem().get(),1))
                .setMaxUses(8)
                .setXpPerTrade(30)
                .setPriceMul(0.02f)
                .setForLevels(5)
                .setPriestOnly()
                .register()

                .setInput1(new ItemStack(Items.EMERALD,1))
                .setOutput(new ItemStack(CrystalType.ENDER.getCrystalItem().get(),2))
                .setMaxUses(5)
                .setXpPerTrade(12)
                .setPriceMul(0.02f)
                .setForLevels(2)
                .setForProfessions(PRIEST_PLAINS.get())
                .register()

                .setInput1(new ItemStack(Items.EMERALD,1))
                .setOutput(new ItemStack(MaterialType.TRINIUM.getItem(MaterialItemType.NUGGET).get(),8))
                .setMaxUses(5)
                .setXpPerTrade(12)
                .setPriceMul(0.02f)
                .setForLevels(2)
                .setForProfessions(PRIEST_PLAINS.get())
                .register()

                .setInput1(new ItemStack(Items.EMERALD,3))
                .setInput2(new ItemStack(CrystalType.ENDER.getCrystalItem().get(),1))
                .setOutput(new ItemStack(CrystalType.ENDER.getSmallCrystalItem().get(),2))
                .setMaxUses(5)
                .setXpPerTrade(12)
                .setPriceMul(0.2f)
                .setForLevels(2)
                .setForProfessions(PRIEST_PLAINS.get())
                .register()

                .setInput1(new ItemStack(Items.EMERALD,1))
                .setOutput(new ItemStack(CrystalType.YELLOW.getCrystalItem().get(),3))
                .setMaxUses(5)
                .setXpPerTrade(12)
                .setPriceMul(0.02f)
                .setForLevels(2)
                .setForProfessions(PRIEST_DESERT.get())
                .register()

                .setInput1(new ItemStack(Items.EMERALD,1))
                .setInput2(new ItemStack(CrystalType.YELLOW.getCrystalItem().get(),1))
                .setOutput(new ItemStack(CrystalType.YELLOW.getSmallCrystalItem().get(),2))
                .setMaxUses(5)
                .setXpPerTrade(12)
                .setPriceMul(0.02f)
                .setForLevels(2)
                .setForProfessions(PRIEST_DESERT.get())
                .register()

                .setInput1(new ItemStack(Items.IRON_INGOT, 6))
                .setInput2(new ItemStack(MaterialType.TITANIUM.getItem(MaterialItemType.NUGGET).get(),1))
                .setOutput(new ItemStack(CoreItems.JSG_WRENCH.get(),1))
                .setMaxUses(5)
                .setXpPerTrade(12)
                .setPriceMul(0.02f)
                .setForLevels(2)
                .setForProfessions(PRIEST_DESERT.get())
                .register()

                .setInput1(new ItemStack(Items.EMERALD, 2))
                .setOutput(new ItemStack(CrystalType.WHITE.getCrystalItem().get(),3))
                .setMaxUses(5)
                .setXpPerTrade(12)
                .setPriceMul(0.02f)
                .setForLevels(2)
                .setForProfessions(PRIEST_SAVANNA.get())
                .register()

                .setInput1(new ItemStack(Items.EMERALD,2))
                .setInput2(new ItemStack(CrystalType.WHITE.getCrystalItem().get(),1))
                .setOutput(new ItemStack(CrystalType.WHITE.getSmallCrystalItem().get(),3))
                .setMaxUses(5)
                .setXpPerTrade(12)
                .setPriceMul(0.02f)
                .setForLevels(2)
                .setForProfessions(PRIEST_SAVANNA.get())
                .register()

                .setInput1(new ItemStack(Items.EMERALD, 1))
                .setInput2(new ItemStack(Items.WHEAT,48))
                .setOutput(new ItemStack(CoreItems.SLICE_LEMON.get(),1))
                .setMaxUses(5)
                .setXpPerTrade(12)
                .setPriceMul(0.02f)
                .setForLevels(2)
                .setForProfessions(PRIEST_SAVANNA.get())
                .register()

                .setInput1(new ItemStack(Items.EMERALD,1))
                .setOutput(new ItemStack(CrystalType.BLUE.getCrystalItem().get(),4))
                .setMaxUses(5)
                .setXpPerTrade(12)
                .setPriceMul(0.02f)
                .setForLevels(2)
                .setForProfessions(PRIEST_TAIGA.get())
                .register()

                .setInput1(new ItemStack(Items.EMERALD,1))
                .setInput2(new ItemStack(CrystalType.BLUE.getCrystalItem().get(),1))
                .setOutput(new ItemStack(CrystalType.BLUE.getSmallCrystalItem().get(),2))
                .setMaxUses(5)
                .setXpPerTrade(12)
                .setPriceMul(0.02f)
                .setForLevels(2)
                .setForProfessions(PRIEST_TAIGA.get())
                .register()

                .setInput1(new ItemStack(Items.EMERALD,1))
                .setInput2(new ItemStack(Items.COPPER_INGOT,16))
                .setOutput(new ItemStack(CoreItems.COPPER_INGOT_WEATHERED.get(),8))
                .setMaxUses(5)
                .setXpPerTrade(12)
                .setPriceMul(0.02f)
                .setForLevels(2)
                .setForProfessions(PRIEST_TAIGA.get())
                .register()

                .setInput1(new ItemStack(Items.EMERALD,2))
                .setOutput(new ItemStack(CrystalType.BLUE.getCrystalItem().get(),3))
                .setMaxUses(5)
                .setXpPerTrade(12)
                .setPriceMul(0.02f)
                .setForLevels(2)
                .setForProfessions(PRIEST_SNOW.get())
                .register()

                .setInput1(new ItemStack(Items.EMERALD,1))
                .setInput2(new ItemStack(CrystalType.BLUE.getCrystalItem().get(),1))
                .setOutput(new ItemStack(CrystalType.BLUE.getSmallCrystalItem().get(),2))
                .setMaxUses(5)
                .setXpPerTrade(12)
                .setPriceMul(0.02f)
                .setForLevels(2)
                .setForProfessions(PRIEST_SNOW.get())
                .register()

                .setInput1(new ItemStack(Items.COPPER_INGOT,16))
                .setInput2(new ItemStack(Items.EMERALD,1))
                .setOutput(new ItemStack(CoreItems.COPPER_INGOT_OXIDIZED.get(),12))
                .setMaxUses(5)
                .setXpPerTrade(12)
                .setPriceMul(0.02f)
                .setForLevels(2)
                .setForProfessions(PRIEST_SNOW.get())
                .register()

                .setInput1(new ItemStack(Items.EMERALD,2))
                .setOutput(new ItemStack(CrystalType.ENDER.getCrystalItem().get(),4))
                .setMaxUses(5)
                .setXpPerTrade(12)
                .setPriceMul(0.02f)
                .setForLevels(2)
                .setForProfessions(PRIEST_JUNGLE.get())
                .register()

                .setInput1(new ItemStack(Items.EMERALD,1))
                .setInput2(new ItemStack(CrystalType.ENDER.getCrystalItem().get(),2))
                .setOutput(new ItemStack(CrystalType.ENDER.getSmallCrystalItem().get(),4))
                .setMaxUses(5)
                .setXpPerTrade(12)
                .setPriceMul(0.02f)
                .setForLevels(2)
                .setForProfessions(PRIEST_JUNGLE.get())
                .register()

                .setInput1(new ItemStack(Items.IRON_INGOT,32))
                .setOutput(new ItemStack(CoreItems.MORTAR_AND_PESTLE.get(),1))
                .setMaxUses(5)
                .setXpPerTrade(12)
                .setPriceMul(0.02f)
                .setForLevels(2)
                .setForProfessions(PRIEST_JUNGLE.get())
                .register()

                .setInput1(new ItemStack(Items.EMERALD,1))
                .setInput2(new ItemStack(Items.WHEAT,8))
                .setOutput(new ItemStack(Items.COCOA_BEANS,2))
                .setMaxUses(5)
                .setXpPerTrade(12)
                .setPriceMul(0.02f)
                .setForLevels(2)
                .setForProfessions(PRIEST_JUNGLE.get())
                .register()

                .setInput1(new ItemStack(Items.EMERALD,1))
                .setInput2(new ItemStack(Items.COPPER_INGOT,4))
                .setOutput(new ItemStack(CoreItems.COPPER_INGOT_OXIDIZED.get(),8))
                .setMaxUses(5)
                .setXpPerTrade(12)
                .setPriceMul(0.02f)
                .setForLevels(2)
                .setForProfessions(PRIEST_SWAMP.get())
                .register()

                .setInput1(new ItemStack(Items.EMERALD,1))
                .setOutput(new ItemStack(CoreItems.JSG_HAMMER.get(),1))
                .setMaxUses(5)
                .setXpPerTrade(12)
                .setPriceMul(0.02f)
                .setForLevels(2)
                .setForProfessions(PRIEST_SWAMP.get())
                .register()

                .setInput1(new ItemStack(CrystalType.RED.getCrystalItem().get(),4))
                .setInput2(new ItemStack(Items.EMERALD,3))
                .setOutput(new ItemStack(JSGItems.MILKYWAY_DHD_MAIN_CRYSTAL.get(),1))
                .setMaxUses(5)
                .setXpPerTrade(12)
                .setPriceMul(0.02f)
                .setForLevels(2)
                .setForProfessions(PRIEST_SWAMP.get())
                .register()

                //Slave Miner
                .setInput1(new ItemStack(Items.CARROT,4))
                .setOutput(new ItemStack(MaterialType.NAQUADAH.getItem(MaterialItemType.NUGGET).get(), 1))
                .setMaxUses(8)
                .setXpPerTrade(1)
                .setPriceMul(0.02f)
                .setForLevels(1)
                .setForProfessions(SLAVE_MINER.get())
                .register()

                .setInput1(new ItemStack(Items.SWEET_BERRIES, 6))
                .setOutput(new ItemStack(MaterialType.NAQUADAH.getItem(MaterialItemType.INGOT).get(),2))
                .setMaxUses(6)
                .setXpPerTrade(1)
                .setPriceMul(0.02f)
                .setForLevels(1)
                .setForProfessions(SLAVE_MINER.get())
                .register()

                .setInput1(new ItemStack(Items.APPLE, 8))
                .setOutput(new ItemStack(MaterialType.NAQUADAH.getItem(MaterialItemType.RAW).get(), 3))
                .setMaxUses(10)
                .setXpPerTrade(1)
                .setPriceMul(0.02f)
                .setForLevels(1)
                .setForProfessions(SLAVE_MINER.get())
                .register()

                .setInput1(new ItemStack(Items.COOKIE, 3))
                .setOutput(new ItemStack(Items.STONE_SHOVEL,1))
                .setMaxUses(5)
                .setXpPerTrade(15)
                .setPriceMul(0.05f)
                .setForLevels(2)
                .setForProfessions(SLAVE_MINER.get())
                .register()

                .setInput1(new ItemStack(Items.BEETROOT,20))
                .setOutput(new ItemStack(MaterialType.NAQUADAH.getItem(MaterialItemType.DUST).get(),2))
                .setMaxUses(5)
                .setXpPerTrade(12)
                .setPriceMul(0.02f)
                .setForLevels(2)
                .setForProfessions(SLAVE_MINER.get())
                .register()

                .setInput1(new ItemStack(Items.POTATO,16))
                .setOutput(new ItemStack(MaterialType.NAQUADAH.getItem(MaterialItemType.NUGGET).get(),2))
                .setMaxUses(6)
                .setXpPerTrade(8)
                .setPriceMul(0.02f)
                .setForLevels(2)
                .setForProfessions(SLAVE_MINER.get())
                .register()

                .setInput1(new ItemStack(Items.BREAD,3))
                .setOutput(new ItemStack(Items.STONE_PICKAXE,1))
                .setMaxUses(12)
                .setXpPerTrade(10)
                .setPriceMul(0.02f)
                .setForLevels(3)
                .setForProfessions(SLAVE_MINER.get())
                .register()

                .setInput1(new ItemStack(Items.COOKED_CHICKEN,4))
                .setOutput(new ItemStack(MaterialType.NAQUADAH.getItem(MaterialItemType.RAW).get(),2))
                .setMaxUses(6)
                .setXpPerTrade(20)
                .setPriceMul(0.02f)
                .setForLevels(3)
                .setForProfessions(SLAVE_MINER.get())
                .register()

                .setInput1(new ItemStack(Items.COOKED_RABBIT,6))
                .setOutput(new ItemStack(MaterialType.NAQUADAH.getItem(MaterialItemType.NUGGET).get(),27))
                .setMaxUses(12)
                .setXpPerTrade(10)
                .setPriceMul(0.02f)
                .setForLevels(3)
                .setForProfessions(SLAVE_MINER.get())
                .register()

                .setInput1(new ItemStack(Items.COOKED_MUTTON,12))
                .setOutput(new ItemStack(MaterialType.NAQUADAH.getItem(MaterialItemType.DUST).get(),4))
                .setMaxUses(12)
                .setXpPerTrade(15)
                .setPriceMul(0.02f)
                .setForLevels(4)
                .setForProfessions(SLAVE_MINER.get())
                .register()

                .setInput1(new ItemStack(Items.COOKED_BEEF, 6))
                .setOutput(new ItemStack(Items.IRON_SHOVEL,1))
                .setMaxUses(8)
                .setXpPerTrade(15)
                .setPriceMul(0.02f)
                .setForLevels(4)
                .setForProfessions(SLAVE_MINER.get())
                .register()

                .setInput1(new ItemStack(Items.COOKED_PORKCHOP,8))
                .setOutput(new ItemStack(MaterialType.NAQUADAH.getItem(MaterialItemType.DUST).get(),3))
                .setMaxUses(10)
                .setXpPerTrade(10)
                .setPriceMul(0.02f)
                .setForLevels(4)
                .setForProfessions(SLAVE_MINER.get())
                .register()

                .setInput1(new ItemStack(Items.CAKE,1))
                .setOutput(new ItemStack(MaterialType.NAQUADAH.getRaw().get(),1))
                .setMaxUses(10)
                .setXpPerTrade(20)
                .setPriceMul(0.02f)
                .setForLevels(5)
                .setForProfessions(SLAVE_MINER.get())
                .register()

                .setInput1(new ItemStack(Items.PUMPKIN_PIE, 16))
                .setOutput(new ItemStack(Items.IRON_PICKAXE,1))
                .setMaxUses(4)
                .setXpPerTrade(32)
                .setPriceMul(0.02f)
                .setForLevels(5)
                .setForProfessions(SLAVE_MINER.get())
                .register()

                .setInput1(new ItemStack(JSGItems.FOOD_CHOCOLATE_BAR.get(),4))
                .setOutput(new ItemStack(MaterialType.NAQUADAH.getItem(MaterialItemType.RAW).get(),2))
                .setMaxUses(8)
                .setXpPerTrade(30)
                .setPriceMul(0.02f)
                .setForLevels(5)
                .setForProfessions(SLAVE_MINER.get())
                .register();
    }
}
