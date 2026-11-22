package dev.tauri.jsg.gametest;

import dev.tauri.jsg.JSG;
import net.minecraftforge.event.RegisterGameTestsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.gametest.GameTestHolder;

@GameTestHolder(JSG.MOD_ID)
@Mod.EventBusSubscriber(modid = JSG.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class JSGGameTests {
    public static final String EMPTY_TEMPLATE = "empty";

    @SubscribeEvent
    public static void onRegisterGameTests(RegisterGameTestsEvent event) {
        event.register(StargateTests.class);
        JSG.logger.info("Test registered");
    }
}
