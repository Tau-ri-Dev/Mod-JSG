package dev.tauri.jsg;

import dev.faststats.ErrorTracker;
import dev.faststats.Metrics;
import dev.faststats.neoforge.NeoForgeContext;

import java.util.Arrays;

public class JSGMetrics {
    public static final ErrorTracker ERROR_TRACKER = ErrorTracker.contextAware()
            .addExclusionRule((thread, error) -> Arrays.stream(error.getStackTrace())
                    .noneMatch(element -> element.getClassName().startsWith(JSGMetrics.class.getPackageName() + ".")));


    private static final NeoForgeContext CONTEXT = new NeoForgeContext.Factory(JSG.MOD_ID, "cde31746dd3fdcfc6f40a935edcf1d83")
            .requireConsent()
            .errorTrackerService(ERROR_TRACKER)
            .metrics(Metrics.Factory::create)
            .create();

    public static void load() {
    }
}
