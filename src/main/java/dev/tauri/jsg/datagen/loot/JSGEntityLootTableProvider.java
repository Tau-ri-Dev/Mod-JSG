package dev.tauri.jsg.datagen.loot;

import dev.tauri.jsg.JSG;
import dev.tauri.jsgcore.mapping.JSGMapping;
import net.minecraft.advancements.critereon.EntityFlagsPredicate;
import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.loot.LootTableSubProvider;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.EnchantedCountIncreaseFunction;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.functions.SmeltItemFunction;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.predicates.LootItemEntityPropertyCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;

import java.util.function.BiConsumer;

public class JSGEntityLootTableProvider implements LootTableSubProvider {
    private final HolderLookup.Provider registries;

    public JSGEntityLootTableProvider(HolderLookup.Provider registries) {
        this.registries = registries;
    }

    @Override
    public void generate(BiConsumer<ResourceKey<LootTable>, LootTable.Builder> writer) {

        //Mastadge
        var mastadgeLoot = ResourceKey.create(Registries.LOOT_TABLE, JSGMapping.rl(JSG.MOD_ID, "entities/mastadge"));

        LootTable.Builder builder = LootTable.lootTable()
                .setParamSet(LootContextParamSets.ENTITY)
                .setRandomSequence(mastadgeLoot.location())

                .withPool(LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1.0F))
                        .setBonusRolls(ConstantValue.exactly(0.0F))

                        .add(LootItem.lootTableItem(Items.LEATHER)
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(0.0F, 3.0F), false))
                                .apply(EnchantedCountIncreaseFunction.lootingMultiplier(registries, UniformGenerator.between(0.0F, 1.0F)))
                        )
                )

                .withPool(LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1.0F))
                        .setBonusRolls(ConstantValue.exactly(0.0F))

                        .add(LootItem.lootTableItem(Items.BEEF)
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 3.0F), false))
                                .apply(SmeltItemFunction.smelted().when(
                                        LootItemEntityPropertyCondition.hasProperties(
                                                LootContext.EntityTarget.THIS,
                                                EntityPredicate.Builder.entity().flags(EntityFlagsPredicate.Builder.flags().setOnFire(true))
                                        )
                                ))
                                .apply(EnchantedCountIncreaseFunction.lootingMultiplier(registries, UniformGenerator.between(0.0F, 1.0F)))
                        )
                );
        writer.accept(mastadgeLoot, builder);
    }
}
