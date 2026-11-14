package dev.tauri.jsg;

import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.repository.Pack;
import net.minecraft.server.packs.repository.PackSource;
import net.minecraftforge.event.AddPackFindersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

import org.apache.logging.log4j.core.config.builder.api.Component;

import dev.tauri.jsgcore.mapping.JSGMapping;


@EventBusSubscriber (modid = JSG.MOD_ID, bus = EventBusSubscriber.Bus.MOD)
public class JSGPackEvents {
    @SubscribeEvent 
    public static void onAddPackFinders(AddPackFindersEvent event) {
        event.addPackFinders(
            JSGMapping.rl(JSG.MOD_ID, "data/"+JSG.MOD_ID+"/datapacks/gate_presedence_experiment"),
            PackType.SERVER_DATA,
            Component.translatable("pack.jsg.gate_precedence_experiment.title"),
            PackSource.FEATURE,
            false
        );
    }
    
}
