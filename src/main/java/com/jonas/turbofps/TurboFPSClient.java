package com.jonas.turbofps;

import com.jonas.turbofps.config.TurboConfig;
import com.jonas.turbofps.optimization.OptimizationManager;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TurboFPSClient implements ClientModInitializer {
    public static final String MOD_ID = "turbofps";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    private static TurboConfig config;
    private static OptimizationManager optimizationManager;

    private static boolean optimizationsApplied = false;

    @Override
    public void onInitializeClient() {
        LOGGER.info("TurboFPS is initializing...");

        // Load config
        config = TurboConfig.load();
        LOGGER.info("Loaded preset: {}", config.getCurrentPreset());

        // Initialize optimization manager
        optimizationManager = new OptimizationManager(config);

        // Apply optimizations when client is fully ready
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (!optimizationsApplied && client.player != null && client.options != null) {
                optimizationManager.applyOptimizations();
                optimizationsApplied = true;
                LOGGER.info("TurboFPS optimizations applied after client loaded.");
            }
        });

        LOGGER.info("TurboFPS initialized successfully.");
    }

    public static TurboConfig getConfig() {
        return config;
    }

    public static OptimizationManager getOptimizationManager() {
        return optimizationManager;
    }

    public static void saveConfig() {
        if (config != null) {
            config.save();
            if (optimizationManager != null) {
                optimizationManager.applyOptimizations();
            }
        }
    }
}
