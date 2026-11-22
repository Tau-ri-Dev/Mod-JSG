package dev.tauri.jsg.gametest;

import dev.tauri.jsg.JSG;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.RegisterGameTestsEvent;
import net.neoforged.neoforge.gametest.GameTestHolder;

@GameTestHolder(JSG.MOD_ID)
@EventBusSubscriber(modid = JSG.MOD_ID)
public class JSGGameTests {
    public static final String EMPTY_TEMPLATE = "empty";

    @SubscribeEvent
    public static void onRegisterGameTests(RegisterGameTestsEvent event) {
        event.register(StargateTests.class);
        JSG.logger.info("Test registered");
    }
}
