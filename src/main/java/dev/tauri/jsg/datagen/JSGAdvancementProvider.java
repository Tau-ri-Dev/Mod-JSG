package dev.tauri.jsg.datagen;

import dev.tauri.jsg.JSG;
import dev.tauri.jsg.common.advancements.JSGCriterions;
import dev.tauri.jsg.common.registry.*;
import dev.tauri.jsgcore.common.registry.CoreAdvancements;
import dev.tauri.jsgcore.datagen.CoreAdvancementProvider;
import dev.tauri.jsgcore.mapping.JSGMapping;
import net.minecraft.advancements.*;
import net.minecraft.advancements.critereon.*;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.neoforge.common.data.AdvancementProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

// TODO: Fix depr
public class JSGAdvancementProvider extends CoreAdvancementProvider {

    @Override
    @ParametersAreNonnullByDefault
    public void generate(HolderLookup.Provider registries, Consumer<AdvancementHolder> saver, ExistingFileHelper fileHelper) {
        Advancement.Builder.advancement()
                .parent(CoreAdvancements.PAGE_EMPTY)
                .display(
                        JSGBlocks.PRINTER.get(),
                        Component.translatable(getAdvancementKey(JSGAdvancements.CRAFT_PRINTER, "title")),
                        Component.translatable(getAdvancementKey(JSGAdvancements.CRAFT_PRINTER, "description")),
                        null,
                        AdvancementType.TASK,
                        true,
                        true,
                        false
                )
                .addCriterion("craft_printer", RecipeCraftedTrigger.TriggerInstance.craftedItem(JSGMapping.rl(JSG.MOD_ID, "printer")))
                .save(saver, JSGAdvancements.CRAFT_PRINTER, fileHelper);


        var allPriestCriteria = new String[]{"trade_desert", "trade_jungle", "trade_plains", "trade_savanna", "trade_snow", "trade_swamp", "trade_taiga"};

        Advancement.Builder.advancement()
                .parent(CoreAdvancements.PAGE_EMPTY)
                .display(
                        JSGItems.ICON_PRIEST_TRADE.get(),
                        Component.translatable(getAdvancementKey(JSGAdvancements.TRADE_WITH_PRIEST, "title")),
                        Component.translatable(getAdvancementKey(JSGAdvancements.TRADE_WITH_PRIEST, "description")),
                        null,
                        AdvancementType.TASK,
                        true,
                        true,
                        false
                )
                .addCriterion("trade_desert", createVillagerTradeCriterion(Objects.requireNonNull(JSGVillagers.PRIEST_DESERT.getKey()).location()))
                .addCriterion("trade_jungle", createVillagerTradeCriterion(Objects.requireNonNull(JSGVillagers.PRIEST_JUNGLE.getKey()).location()))
                .addCriterion("trade_plains", createVillagerTradeCriterion(Objects.requireNonNull(JSGVillagers.PRIEST_PLAINS.getKey()).location()))
                .addCriterion("trade_savanna", createVillagerTradeCriterion(Objects.requireNonNull(JSGVillagers.PRIEST_SAVANNA.getKey()).location()))
                .addCriterion("trade_snow", createVillagerTradeCriterion(Objects.requireNonNull(JSGVillagers.PRIEST_SNOW.getKey()).location()))
                .addCriterion("trade_swamp", createVillagerTradeCriterion(Objects.requireNonNull(JSGVillagers.PRIEST_SWAMP.getKey()).location()))
                .addCriterion("trade_taiga", createVillagerTradeCriterion(Objects.requireNonNull(JSGVillagers.PRIEST_TAIGA.getKey()).location()))
                .requirements(AdvancementRequirements.anyOf(Arrays.stream(allPriestCriteria).toList()))

                .save(saver, JSGAdvancements.TRADE_WITH_PRIEST, fileHelper);


        Advancement.Builder.advancement()
                .parent(CoreAdvancements.PAGE_EMPTY)
                .display(
                        JSGItems.ICON_RA_REALM.get(),
                        Component.translatable(getAdvancementKey(JSGAdvancements.VISIT_ABYDOS, "title")),
                        Component.translatable(getAdvancementKey(JSGAdvancements.VISIT_ABYDOS, "description")),
                        null,
                        AdvancementType.TASK,
                        true,
                        true,
                        false
                )
                .addCriterion("visit_abydos", ChangeDimensionTrigger.TriggerInstance.changedDimensionTo(JSGDimensions.ABYDOS))
                .save(saver, JSGAdvancements.VISIT_ABYDOS, fileHelper);

        Advancement.Builder.advancement()
                .parent(CoreAdvancements.COPPER)
                .display(
                        JSGItems.GDO.get(),
                        Component.translatable(getAdvancementKey(JSGAdvancements.USE_GDO, "title")),
                        Component.translatable(getAdvancementKey(JSGAdvancements.USE_GDO, "description")),
                        null,
                        AdvancementType.TASK,
                        true,
                        true,
                        false
                )
                .addCriterion("use_gdo", JSGCriterions.GDO_USED.createCriterion())
                .save(saver, JSGAdvancements.USE_GDO, fileHelper);

        var toaster = Advancement.Builder.advancement()
                .parent(CoreAdvancements.COPPER)
                .display(
                        JSGBlocks.TOASTER.get(),
                        Component.translatable(getAdvancementKey(JSGAdvancements.OBTAIN_TOASTER, "title")),
                        Component.translatable(getAdvancementKey(JSGAdvancements.OBTAIN_TOASTER, "description")),
                        null,
                        AdvancementType.TASK,
                        true,
                        true,
                        false
                )
                .addCriterion("get_toaster", InventoryChangeTrigger.TriggerInstance.hasItems(JSGBlocks.TOASTER.get()))
                .save(saver, JSGAdvancements.OBTAIN_TOASTER, fileHelper);

        Advancement.Builder.advancement()
                .parent(toaster)
                .display(
                        JSGBlocks.STARGATE_ORLIN_BASE_BLOCK.get(),
                        Component.translatable(getAdvancementKey(JSGAdvancements.ACTIVATE_ORLIN_GATE, "title")),
                        Component.translatable(getAdvancementKey(JSGAdvancements.ACTIVATE_ORLIN_GATE, "description")),
                        null,
                        AdvancementType.TASK,
                        true,
                        true,
                        false
                )
                .addCriterion("activate_orlin_gate", JSGCriterions.ACTIVATE_ORLIN_GATE.createCriterion())
                .save(saver, JSGAdvancements.ACTIVATE_ORLIN_GATE, fileHelper);

        var extraGlyphGate = Advancement.Builder.advancement()
                .parent(CoreAdvancements.CIRCUIT_CONTROL_CRYSTAL)
                .display(
                        JSGItems.CRYSTAL_ADDRESS_EXTENDED.get(),
                        Component.translatable(getAdvancementKey(JSGAdvancements.OBTAIN_EXTENDED_ADDRESS_CRYSTAL, "title")),
                        Component.translatable(getAdvancementKey(JSGAdvancements.OBTAIN_EXTENDED_ADDRESS_CRYSTAL, "description")),
                        null,
                        AdvancementType.TASK,
                        true,
                        true,
                        false
                )
                .addCriterion("get_extended_address_crystal", InventoryChangeTrigger.TriggerInstance.hasItems(JSGItems.CRYSTAL_ADDRESS_EXTENDED.get()))
                .save(saver, JSGAdvancements.OBTAIN_EXTENDED_ADDRESS_CRYSTAL, fileHelper);

        Advancement.Builder.advancement()
                .parent(extraGlyphGate)
                .display(
                        JSGItems.CRYSTAL_ADDRESS_STATIC.get(),
                        Component.translatable(getAdvancementKey(JSGAdvancements.OBTAIN_STATIC_ADDRESS_CRYSTAL, "title")),
                        Component.translatable(getAdvancementKey(JSGAdvancements.OBTAIN_STATIC_ADDRESS_CRYSTAL, "description")),
                        null,
                        AdvancementType.TASK,
                        true,
                        true,
                        false
                )
                .addCriterion("get_static_address_crystal", InventoryChangeTrigger.TriggerInstance.hasItems(JSGItems.CRYSTAL_ADDRESS_EXTENDED.get()))
                .save(saver, JSGAdvancements.OBTAIN_STATIC_ADDRESS_CRYSTAL, fileHelper);


        Advancement.Builder.advancement()
                .parent(extraGlyphGate)
                .display(
                        JSGItems.CRYSTAL_ADDRESS_DIALING_EXTENDED.get(),
                        Component.translatable(getAdvancementKey(JSGAdvancements.OBTAIN_EXTENDED_ADDRESS_DIALING_CRYSTAL, "title")),
                        Component.translatable(getAdvancementKey(JSGAdvancements.OBTAIN_EXTENDED_ADDRESS_DIALING_CRYSTAL, "description")),
                        null,
                        AdvancementType.TASK,
                        true,
                        true,
                        false
                )
                .addCriterion("get_dialing_extended_address_crystal", InventoryChangeTrigger.TriggerInstance.hasItems(JSGItems.CRYSTAL_ADDRESS_DIALING_EXTENDED.get()))
                .save(saver, JSGAdvancements.OBTAIN_EXTENDED_ADDRESS_DIALING_CRYSTAL, fileHelper);

        var movie_gate = Advancement.Builder.advancement()
                .parent(CoreAdvancements.TITANIUM)
                .display(
                        JSGBlocks.STARGATE_MOVIE_BASE_BLOCK.get(),
                        Component.translatable(getAdvancementKey(JSGAdvancements.ACTIVATE_MOVIE_GATE, "title")),
                        Component.translatable(getAdvancementKey(JSGAdvancements.ACTIVATE_MOVIE_GATE, "description")),
                        null,
                        AdvancementType.TASK,
                        true,
                        true,
                        false
                )
                .addCriterion("activate_movie_gate", JSGCriterions.ACTIVATE_MOVIE_GATE.createCriterion())
                .save(saver, JSGAdvancements.ACTIVATE_MOVIE_GATE, fileHelper);

        var wormhole = Advancement.Builder.advancement()
                .parent(movie_gate)
                .display(
                        JSGItems.ICON_WORMHOLE.get(),
                        Component.translatable(getAdvancementKey(JSGAdvancements.WORMHOLE_GO, "title")),
                        Component.translatable(getAdvancementKey(JSGAdvancements.WORMHOLE_GO, "description")),
                        null,
                        AdvancementType.TASK,
                        true,
                        true,
                        false
                )
                .addCriterion("wormhole_go", JSGCriterions.WORMHOLE_GO.createCriterion())
                .save(saver, JSGAdvancements.WORMHOLE_GO, fileHelper);

        Advancement.Builder.advancement()
                .parent(wormhole)
                .display(
                        Blocks.SKELETON_SKULL,
                        Component.translatable(getAdvancementKey(JSGAdvancements.UNSTABLE_GO, "title")),
                        Component.translatable(getAdvancementKey(JSGAdvancements.UNSTABLE_GO, "description")),
                        null,
                        AdvancementType.TASK,
                        true,
                        true,
                        false
                )
                .addCriterion("unstable_go", JSGCriterions.UNSTABLE_SURVIVE.createCriterion())
                .save(saver, JSGAdvancements.UNSTABLE_GO, fileHelper);

        var mw_gate = Advancement.Builder.advancement()
                .parent(movie_gate)
                .display(
                        JSGBlocks.STARGATE_MILKYWAY_BASE_BLOCK.get(),
                        Component.translatable(getAdvancementKey(JSGAdvancements.ACTIVATE_MILKYWAY_GATE, "title")),
                        Component.translatable(getAdvancementKey(JSGAdvancements.ACTIVATE_MILKYWAY_GATE, "description")),
                        null,
                        AdvancementType.TASK,
                        true,
                        true,
                        false
                )
                .addCriterion("activate_milkyway_gate", JSGCriterions.ACTIVATE_MILKYWAY_GATE.createCriterion())
                .save(saver, JSGAdvancements.ACTIVATE_MILKYWAY_GATE, fileHelper);

        Advancement.Builder.advancement()
                .parent(mw_gate)
                .display(
                        JSGBlocks.STARGATE_TOLLAN_BASE_BLOCK.get(),
                        Component.translatable(getAdvancementKey(JSGAdvancements.ACTIVATE_TOLLAN_GATE, "title")),
                        Component.translatable(getAdvancementKey(JSGAdvancements.ACTIVATE_TOLLAN_GATE, "description")),
                        null,
                        AdvancementType.TASK,
                        true,
                        true,
                        false
                )
                .addCriterion("activate_tollan_gate", JSGCriterions.ACTIVATE_TOLLAN_GATE.createCriterion())
                .save(saver, JSGAdvancements.ACTIVATE_TOLLAN_GATE, fileHelper);

        Advancement.Builder.advancement()
                .parent(mw_gate)
                .display(
                        JSGItems.ICON_KAWOOSH_DEATH.get(),
                        Component.translatable(getAdvancementKey(JSGAdvancements.KAWOOSH_DEATH, "title")),
                        Component.translatable(getAdvancementKey(JSGAdvancements.KAWOOSH_DEATH, "description")),
                        null,
                        AdvancementType.TASK,
                        true,
                        true,
                        false
                )
                .addCriterion("kawoosh_death", JSGCriterions.KAWOOSH_CREMATION.createCriterion())
                .save(saver, JSGAdvancements.KAWOOSH_DEATH, fileHelper);

        Advancement.Builder.advancement()
                .parent(mw_gate)
                .display(
                        JSGItems.UPGRADE_IRIS.get(),
                        Component.translatable(getAdvancementKey(JSGAdvancements.IRIS_IMPACT, "title")),
                        Component.translatable(getAdvancementKey(JSGAdvancements.IRIS_IMPACT, "description")),
                        null,
                        AdvancementType.TASK,
                        true,
                        true,
                        false
                )
                .addCriterion("iris_impact", JSGCriterions.IRIS_IMPACT.createCriterion())
                .save(saver, JSGAdvancements.IRIS_IMPACT, fileHelper);

        Advancement.Builder.advancement()
                .parent(mw_gate)
                .display(
                        JSGItems.DIALING_GLOVES.get(),
                        Component.translatable(getAdvancementKey(JSGAdvancements.HIT_WITH_GLOVES, "title")),
                        Component.translatable(getAdvancementKey(JSGAdvancements.HIT_WITH_GLOVES, "description")),
                        null,
                        AdvancementType.TASK,
                        true,
                        true,
                        false
                )
                .addCriterion("hit_with_glove", PlayerHurtEntityTrigger.TriggerInstance.playerHurtEntity(Optional.of(
                        new DamagePredicate.Builder()
                                .type(DamageSourcePredicate.Builder.damageType()
                                        .source(new EntityPredicate.Builder()
                                                .equipment(new EntityEquipmentPredicate.Builder()
                                                        .mainhand(ItemPredicate.Builder.item()
                                                                .of(JSGItems.DIALING_GLOVES.get())
                                                        )
                                                        .build()))
                                        .build())
                                .build()), Optional.empty()))
                .save(saver, JSGAdvancements.HIT_WITH_GLOVES, fileHelper);

        var pg_gate = Advancement.Builder.advancement()
                .parent(mw_gate)
                .display(
                        JSGBlocks.STARGATE_PEGASUS_BASE_BLOCK.get(),
                        Component.translatable(getAdvancementKey(JSGAdvancements.ACTIVATE_PEGASUS_GATE, "title")),
                        Component.translatable(getAdvancementKey(JSGAdvancements.ACTIVATE_PEGASUS_GATE, "description")),
                        null,
                        AdvancementType.TASK,
                        true,
                        true,
                        false
                )
                .addCriterion("activate_pegasus_gate", JSGCriterions.ACTIVATE_PEGASUS_GATE.createCriterion())
                .save(saver, JSGAdvancements.ACTIVATE_PEGASUS_GATE, fileHelper);

        Advancement.Builder.advancement()
                .parent(pg_gate)
                .display(
                        JSGBlocks.STARGATE_UNIVERSE_BASE_BLOCK.get(),
                        Component.translatable(getAdvancementKey(JSGAdvancements.ACTIVATE_UNIVERSE_GATE, "title")),
                        Component.translatable(getAdvancementKey(JSGAdvancements.ACTIVATE_UNIVERSE_GATE, "description")),
                        null,
                        AdvancementType.TASK,
                        true,
                        true,
                        false
                )
                .addCriterion("activate_universe_gate", JSGCriterions.ACTIVATE_UNIVERSE_GATE.createCriterion())
                .save(saver, JSGAdvancements.ACTIVATE_UNIVERSE_GATE, fileHelper);

        Advancement.Builder.advancement()
                .parent(pg_gate)
                .display(
                        JSGItems.UPGRADE_SHIELD.get(),
                        Component.translatable(getAdvancementKey(JSGAdvancements.CRAFT_SHIELD, "title")),
                        Component.translatable(getAdvancementKey(JSGAdvancements.CRAFT_SHIELD, "description")),
                        null,
                        AdvancementType.TASK,
                        true,
                        true,
                        false
                )
                .addCriterion("craft_shield", RecipeCraftedTrigger.TriggerInstance.craftedItem(JSGMapping.rl(JSG.MOD_ID, "upgrade_shield")))
                .save(saver, JSGAdvancements.CRAFT_SHIELD, fileHelper);
    }

    protected static Criterion<TradeTrigger.TriggerInstance> createVillagerTradeCriterion(ResourceLocation professionLocation) {
        var villagerData = new CompoundTag();
        villagerData.putString("profession", professionLocation.toString());

        var rootNbt = new CompoundTag();
        rootNbt.put("VillagerData", villagerData);

        var villagerPredicate = EntityPredicate.Builder.entity()
                .nbt(new NbtPredicate(rootNbt))
                .build();

        var wrappedVillager = EntityPredicate.wrap(villagerPredicate);

        return CriteriaTriggers.TRADE.createCriterion(new TradeTrigger.TriggerInstance(
                Optional.empty(),
                Optional.of(wrappedVillager),
                Optional.empty()
        ));
    }

    public static AdvancementProvider create(PackOutput output, CompletableFuture<HolderLookup.Provider> registries, ExistingFileHelper helper) {
        return new AdvancementProvider(output, registries, helper, List.of(new JSGAdvancementProvider()));
    }
}
