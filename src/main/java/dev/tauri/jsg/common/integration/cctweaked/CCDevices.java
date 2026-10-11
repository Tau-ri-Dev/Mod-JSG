package dev.tauri.jsg.common.integration.cctweaked;

import dev.tauri.jsgcore.common.integration.cctweaked.CCDevice;

public class CCDevices {
    public static final CCDevice STARGATE_ABSTRACT = new CCDevice("STARGATE_ABSTRACT", "stargate", StargateAbstractCCMethods::new);
    public static final CCDevice STARGATE_CLASSIC = new CCDevice("STARGATE_CLASSIC", "stargate", StargateClassicCCMethods::new);
    public static final CCDevice PRINTER = new CCDevice("PRINTER", "printer", PrinterCCMethods::new);

    public static void load() {

    }
}
