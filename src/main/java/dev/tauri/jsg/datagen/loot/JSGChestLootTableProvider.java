package dev.tauri.jsg.datagen.loot;

import dev.tauri.jsg.JSG;
import dev.tauri.jsg.common.registry.JSGItems;
import dev.tauri.jsgcore.common.definition.crystal.CrystalType;
import dev.tauri.jsgcore.common.definition.material.MaterialItemType;
import dev.tauri.jsgcore.common.definition.material.MaterialType;
import dev.tauri.jsgcore.common.registry.CoreItems;
import dev.tauri.jsgcore.mapping.JSGMapping;
import net.minecraft.data.loot.LootTableSubProvider;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.TagParser;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.functions.SetNbtFunction;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;

import java.util.function.BiConsumer;

@SuppressWarnings("deprecation")
public class JSGChestLootTableProvider implements LootTableSubProvider {

    @Override
    public void generate(BiConsumer<ResourceLocation, LootTable.Builder> writer) {

        //Abydos Campfire
        var abydosCampfireChest = JSGMapping.rl(JSG.MOD_ID, "chests/abydos_campfire");

        LootTable.Builder builder = LootTable.lootTable()
                .setParamSet(LootContextParamSets.CHEST)
                .setRandomSequence(abydosCampfireChest)

                .withPool(LootPool.lootPool()
                        .setRolls(UniformGenerator.between(3.0F, 8.0F))
                        .setBonusRolls(ConstantValue.exactly(0.0F))

                        .add(LootItem.lootTableItem(Items.RABBIT_STEW).setWeight(3))

                        .add(LootItem.lootTableItem(Items.LEATHER).setWeight(4))

                        .add(LootItem.lootTableItem(Items.BOWL).setWeight(10)
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 4.0F), false)))

                        .add(LootItem.lootTableItem(Items.WHEAT).setWeight(10)
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 7.0F), false)))

                        .add(LootItem.lootTableItem(Items.BREAD).setWeight(10)
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 4.0F), false)))

                        .add(LootItem.lootTableItem(Items.DEAD_BUSH).setWeight(2)
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 3.0F), false)))

                        .add(LootItem.lootTableItem(MaterialType.TITANIUM.getItem(MaterialItemType.INGOT).get()).setWeight(7)
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 2.0F), false)))

                        .add(LootItem.lootTableItem(JSGItems.FOOD_CHOCOLATE_BAR.get()).setWeight(4))

                        .add(LootItem.lootTableItem(MaterialType.NAQUADAH.getItem(MaterialItemType.RAW).get()).setWeight(5)
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 3.0F), false)))
                );

        writer.accept(abydosCampfireChest, builder);

        //Abydos Cartouche
        var abydosCartoucheChest = JSGMapping.rl(JSG.MOD_ID, "chests/abydos_cartouche");

        builder = LootTable.lootTable()
                .setParamSet(LootContextParamSets.CHEST)
                .setRandomSequence(abydosCartoucheChest)

                .withPool(LootPool.lootPool()
                        .setRolls(UniformGenerator.between(3.0F, 8.0F))
                        .setBonusRolls(ConstantValue.exactly(0.0F))

                        .add(LootItem.lootTableItem(Items.SAND).setWeight(15)
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(5.0F, 15.0F), false)))

                        .add(LootItem.lootTableItem(Items.WHEAT).setWeight(7)
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 7.0F), false)))

                        .add(LootItem.lootTableItem(Items.FLOWER_POT).setWeight(5)
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 2.0F), false)))

                        .add(LootItem.lootTableItem(Items.DEAD_BUSH).setWeight(2)
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 3.0F), false)))

                        .add(LootItem.lootTableItem(Items.BREAD).setWeight(8)
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 4.0F), false)))

                        .add(LootItem.lootTableItem(Items.DEAD_BUSH).setWeight(2)
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 3.0F), false)))

                        .add(LootItem.lootTableItem(MaterialType.TITANIUM.getItem(MaterialItemType.INGOT).get()).setWeight(2)
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 3.0F), false)))

                        .add(LootItem.lootTableItem(MaterialType.NAQUADAH.getItem(MaterialItemType.RAW).get()).setWeight(3))

                        .add(LootItem.lootTableItem(MaterialType.NAQUADAH.getItem(MaterialItemType.NUGGET).get()).setWeight(10)
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(3.0F, 7.0F), false)))
                );

        writer.accept(abydosCartoucheChest, builder);

        //Abydos Dungeon
        var abydosDungeonChest = JSGMapping.rl(JSG.MOD_ID, "chests/abydos_dungeon");

        builder = LootTable.lootTable()
                .setParamSet(LootContextParamSets.CHEST)
                .setRandomSequence(abydosDungeonChest)

                .withPool(LootPool.lootPool()
                        .setRolls(UniformGenerator.between(1.0F, 4.0F))
                        .setBonusRolls(ConstantValue.exactly(0.0F))

                        .add(LootItem.lootTableItem(Items.SAND).setWeight(1)
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 4.0F), false)))
                )

                .withPool(LootPool.lootPool()
                        .setRolls(UniformGenerator.between(4.0F, 7.0F))
                        .setBonusRolls(ConstantValue.exactly(0.0F))

                        .add(LootItem.lootTableItem(Items.REDSTONE).setWeight(2)
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 4.0F))))

                        .add(LootItem.lootTableItem(Items.ROTTEN_FLESH).setWeight(9)
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 3.0F))))

                        .add(LootItem.lootTableItem(MaterialType.NAQUADAH.getItem(MaterialItemType.NUGGET).get()).setWeight(7)
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 4.0F), false)))

                        .add(LootItem.lootTableItem(MaterialType.NAQUADAH.getItem(MaterialItemType.INGOT).get()).setWeight(7)
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 2.0F), false)))

                        .add(LootItem.lootTableItem(MaterialType.NAQUADAH.getItem(MaterialItemType.NUGGET).get()).setWeight(6)
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(2.0F, 4.0F), false)))

                        .add(LootItem.lootTableItem(MaterialType.NAQUADAH_ALLOY.getItem(MaterialItemType.NUGGET).get()).setWeight(5)
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 4.0F), false)))

                        .add(LootItem.lootTableItem(MaterialType.REFINED_NAQUADAH.getItem(MaterialItemType.NUGGET).get()).setWeight(5))

                        .add(LootItem.lootTableItem(CoreItems.COPPER_INGOT_OXIDIZED.get()).setWeight(4)
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(2.0F, 4.0F), false)))

                        .add(LootItem.lootTableItem(CoreItems.NOTEBOOK_PAGE_EMPTY.get()).setWeight(2)
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 5.0F), false)))
                );

        writer.accept(abydosDungeonChest, builder);

        //Abydos City
        var abydosCityChest = JSGMapping.rl(JSG.MOD_ID, "chests/abydos_city");

        builder = LootTable.lootTable()
                .setParamSet(LootContextParamSets.CHEST)
                .setRandomSequence(abydosCityChest)

                .withPool(LootPool.lootPool()
                        .setRolls(UniformGenerator.between(1.0F, 4.0F))
                        .setBonusRolls(ConstantValue.exactly(0.0F))

                        .add(LootItem.lootTableItem(Items.SAND).setWeight(2))

                        .add(LootItem.lootTableItem(Items.WHEAT).setWeight(5)
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 7.0F), false)))

                        .add(LootItem.lootTableItem(Items.BREAD).setWeight(5)
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 4.0F), false)))

                        .add(LootItem.lootTableItem(Items.DEAD_BUSH).setWeight(1)
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 3.0F), false)))

                        .add(LootItem.lootTableItem(MaterialType.TITANIUM.getItem(MaterialItemType.INGOT).get()).setWeight(1)
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 3.0F), false)))

                        .add(LootItem.lootTableItem(MaterialType.NAQUADAH.getItem(MaterialItemType.NUGGET).get()).setWeight(10)
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 4.0F), false)))
                )

                .withPool(LootPool.lootPool()
                        .setRolls(UniformGenerator.between(1.0F, 5.0F))
                        .setBonusRolls(ConstantValue.exactly(0.0F))

                        .add(LootItem.lootTableItem(Items.PAPER).setWeight(3)
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(4.0F, 9.0F))))

                        .add(LootItem.lootTableItem(Items.CHARCOAL).setWeight(2)
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 4.0F))))

                        .add(LootItem.lootTableItem(Items.IRON_INGOT).setWeight(2)
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 4.0F), false)))

                        .add(LootItem.lootTableItem(Items.REDSTONE).setWeight(3)
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(3.0F, 5.0F), false)))
                );

        writer.accept(abydosCityChest, builder);

        //Abydos Eye chest
        CompoundTag bookNBT;
        try{
            bookNBT = TagParser.parseTag("{title:\"Legends of Egypt god Ra\",author:\"Thovt\",pages:['[[\"\\\\nIn ancient times, when the world was still young and full of mysteries, the sun god Ra ruled over Egypt. His power was immense, his rays brought life to all corners of the earth, warmed the fields and gave strength to plants.\"]]','[[\"\\\\nEvery day, Ra traveled through the sky in a colorfully decorated solar chariot pulled by divine roosters. His journey began in the east, where he was born from darkness and ignited the world with his golden light.\"]]','[[\"\\\\nTo the left of his chariot stood 2 goddesses, protectors of Ra\\'s path. Each of them had 3 golden ankhs on a necklace, which in the bright rays of the sun illuminated the path of the sun god and drove away the dark forces of the night.\"]]','[[\"\\\\nBehind them were 2 figures of warriors, ready to defend Ra against all enemies.\\\\nRa\\'s retinue heads beyond the horizon to rest in the underworld at night, 1 goddess was waiting for him, responsible for his safe return.\"]]','[[\"\\\\nBehind them stood the spirits of the dead, who tried to approach the sun god and obtain one of the greatest treasures of Ra\\'s treasury. \\\\nAt night, Ra met with the god of the underworld, Osiris.\\\\nand in a long duel they fought for\"]]','[[\"\\\\ndomination over the world. \\\\nTo the right of Ra\\'s chariot stood 3 figures of demons, who tried to betray Ra and force him to surrender.\\\\nBehind them were 2 goddesses of darkness, whose task was to weaken Ra\\'s\"]]','[[\"\\\\nlight beam and prepare the world for the domination of night.\\\\nRa\\'s 3 most devoted servants also stood in the fight against the demons.\\\\nEvery day this battle between the 2 sides of light and darkness was repeated.\"]]','[[\"\\\\nAnd so Ra, the sun god, continued to rule over Egypt, bringing life and light to the world, even though his path was full of dangers and hardships.\"]]']}");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        var abydosEyeChest = JSGMapping.rl(JSG.MOD_ID, "chests/abydos_eye_hint");

        builder = LootTable.lootTable()
                .setParamSet(LootContextParamSets.CHEST)
                .setRandomSequence(abydosEyeChest)

                .withPool(LootPool.lootPool()
                        .setRolls(UniformGenerator.between(1.0F, 4.0F))
                        .setBonusRolls(ConstantValue.exactly(0.0F))

                        .add(LootItem.lootTableItem(Items.SAND).setWeight(1)
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 3.0F))))
                )

                .withPool(LootPool.lootPool()
                        .setRolls(UniformGenerator.between(4.0F, 7.0F))
                        .setBonusRolls(ConstantValue.exactly(0.0F))

                        .add(LootItem.lootTableItem(MaterialType.NAQUADAH.getItem(MaterialItemType.NUGGET).get()).setWeight(7)
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 4.0F), false)))

                        .add(LootItem.lootTableItem(MaterialType.NAQUADAH.getItem(MaterialItemType.INGOT).get()).setWeight(7)
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 2.0F), false)))

                        .add(LootItem.lootTableItem(MaterialType.NAQUADAH.getItem(MaterialItemType.INGOT).get()).setWeight(7)
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 2.0F), false)))

                        .add(LootItem.lootTableItem(MaterialType.NAQUADAH_ALLOY.getItem(MaterialItemType.NUGGET).get()).setWeight(6)
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(2.0F, 4.0F), false)))

                        .add(LootItem.lootTableItem(MaterialType.REFINED_NAQUADAH.getItem(MaterialItemType.NUGGET).get()).setWeight(5))
                )

                .withPool(LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1.0F))
                        .setBonusRolls(ConstantValue.exactly(0.0F))
                        .add(LootItem.lootTableItem(Items.WRITTEN_BOOK)
                                .apply(SetNbtFunction.setTag(bookNBT))
                        )
                );

        writer.accept(abydosEyeChest, builder);

        //Abydos Treasure Rooms
        var abydosTreasureRoomsPublicChest = JSGMapping.rl(JSG.MOD_ID, "chests/abydos_treasure_rooms");

        builder = LootTable.lootTable()
                .setParamSet(LootContextParamSets.CHEST)
                .setRandomSequence(abydosTreasureRoomsPublicChest)

                .withPool(LootPool.lootPool()
                        .setRolls(UniformGenerator.between(1.0F, 4.0F))
                        .setBonusRolls(ConstantValue.exactly(0.0F))

                        .add(LootItem.lootTableItem(Items.SAND).setWeight(1)
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 3.0F), false)))
                )

                .withPool(LootPool.lootPool()
                        .setRolls(UniformGenerator.between(4.0F, 7.0F))
                        .setBonusRolls(ConstantValue.exactly(0.0F))

                        .add(LootItem.lootTableItem(MaterialType.NAQUADAH.getItem(MaterialItemType.NUGGET).get()).setWeight(7)
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 4.0F), false)))

                        .add(LootItem.lootTableItem(MaterialType.NAQUADAH.getItem(MaterialItemType.INGOT).get()).setWeight(7)
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 2.0F), false)))

                        .add(LootItem.lootTableItem(MaterialType.NAQUADAH_ALLOY.getItem(MaterialItemType.NUGGET).get()).setWeight(6)
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(2.0F, 4.0F), false)))

                        .add(LootItem.lootTableItem(MaterialType.REFINED_NAQUADAH.getItem(MaterialItemType.NUGGET).get()).setWeight(5))

                        .add(LootItem.lootTableItem(CrystalType.YELLOW.getCrystalItem().get()).setWeight(3))
                );

        writer.accept(abydosTreasureRoomsPublicChest, builder);

        //Abydos Secret Treasure Rooms
        var abydosTreasureRoomsSecretChest = JSGMapping.rl(JSG.MOD_ID, "chests/secret_abydos_treasure_chest");

        builder = LootTable.lootTable()
                .setParamSet(LootContextParamSets.CHEST)
                .setRandomSequence(abydosTreasureRoomsSecretChest)

                .withPool(LootPool.lootPool()
                        .setRolls(UniformGenerator.between(1.0F, 4.0F))
                        .setBonusRolls(ConstantValue.exactly(0.0F))

                        .add(LootItem.lootTableItem(Items.SAND).setWeight(1)
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 3.0F), false)))
                )

                .withPool(LootPool.lootPool()
                        .setRolls(UniformGenerator.between(4.0F, 7.0F))
                        .setBonusRolls(ConstantValue.exactly(0.0F))

                        .add(LootItem.lootTableItem(MaterialType.NAQUADAH_ALLOY.getItem(MaterialItemType.NUGGET).get()).setWeight(7)
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 4.0F), false)))

                        .add(LootItem.lootTableItem(MaterialType.REFINED_NAQUADAH.getItem(MaterialItemType.INGOT).get()).setWeight(5))

                        .add(LootItem.lootTableItem(CrystalType.YELLOW.getCrystalItem().get()).setWeight(3)
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 2.0F), false)))

                        .add(LootItem.lootTableItem(CoreItems.MORTAR_AND_PESTLE.get()).setWeight(2))

                        .add(LootItem.lootTableItem(CoreItems.CIRCUIT_CONTROL_NAQUADAH.get()).setWeight(3))
                );

        writer.accept(abydosTreasureRoomsSecretChest, builder);



    }
}
