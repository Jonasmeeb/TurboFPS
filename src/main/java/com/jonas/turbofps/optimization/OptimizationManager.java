package com.jonas.turbofps.optimization;

import com.jonas.turbofps.TurboFPSClient;
import com.jonas.turbofps.config.TurboConfig;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.GameOptions;

public class OptimizationManager {
    private final TurboConfig config;
    private final EntityOptimizer entityOptimizer;
    private final ParticleOptimizer particleOptimizer;
    private final RenderOptimizer renderOptimizer;

    public OptimizationManager(TurboConfig config) {
        this.config = config;
        this.entityOptimizer = new EntityOptimizer(config);
        this.particleOptimizer = new ParticleOptimizer(config);
        this.renderOptimizer = new RenderOptimizer(config);
    }

    public void applyOptimizations() {
        TurboFPSClient.LOGGER.info("Applying {} preset optimizations", config.getCurrentPreset());

        MinecraftClient client = MinecraftClient.getInstance();
        if (client == null || client.options == null) {
            TurboFPSClient.LOGGER.warn("Client options not yet available — skipping optimization.");
            return;
        }

        GameOptions options = client.options;

        // Render settings
        if (config.isAutoRenderDistance()) {
            applyRenderDistance(options);
        }

        // Graphics preset settings
        if (config.isAutoGraphicsSettings()) {
            applyGraphicsSettings(options);
        }

        // Entity, particles, rendering
        entityOptimizer.apply();
        particleOptimizer.apply();
        renderOptimizer.apply();

        TurboFPSClient.LOGGER.info("Optimizations applied successfully.");
    }

    private void applyRenderDistance(GameOptions options) {
        if (options == null) return;

        int renderDistance = config.getRenderDistance();
        if (renderDistance > 0) {
            options.getViewDistance().setValue(renderDistance);
            options.getSimulationDistance().setValue(Math.max(5, renderDistance - 2));
            TurboFPSClient.LOGGER.info("Set render distance to {} chunks", renderDistance);
        }
    }

    private void applyGraphicsSettings(GameOptions options) {
        if (options == null) return;

        switch (config.getCurrentPreset()) {
            case POTATO:
                options.getGraphicsMode().setValue(net.minecraft.client.option.GraphicsMode.FAST);
                options.getAo().setValue(false);
                options.getBiomeBlendRadius().setValue(0);
                options.getCloudRenderMode().setValue(net.minecraft.client.option.CloudRenderMode.OFF);
                options.getEntityShadows().setValue(false);
                options.getEntityDistanceScaling().setValue(0.5);
                break;

            case LOW_END:
                options.getGraphicsMode().setValue(net.minecraft.client.option.GraphicsMode.FAST);
                options.getAo().setValue(true);
                options.getBiomeBlendRadius().setValue(1);
                options.getCloudRenderMode().setValue(net.minecraft.client.option.CloudRenderMode.FAST);
                options.getEntityShadows().setValue(false);
                options.getEntityDistanceScaling().setValue(0.75);
                break;

            case BALANCED:
                options.getGraphicsMode().setValue(net.minecraft.client.option.GraphicsMode.FANCY);
                options.getAo().setValue(true);
                options.getBiomeBlendRadius().setValue(3);
                options.getCloudRenderMode().setValue(net.minecraft.client.option.CloudRenderMode.FANCY);
                options.getEntityShadows().setValue(true);
                options.getEntityDistanceScaling().setValue(1.0);
                break;

            case HIGH_END:
            case ULTRA:
                options.getGraphicsMode().setValue(net.minecraft.client.option.GraphicsMode.FANCY);
                options.getAo().setValue(true);
                options.getBiomeBlendRadius().setValue(5);
                options.getCloudRenderMode().setValue(net.minecraft.client.option.CloudRenderMode.FANCY);
                options.getEntityShadows().setValue(true);
                options.getEntityDistanceScaling().setValue(1.0);
                break;
        }
    }

    public EntityOptimizer getEntityOptimizer() {
        return entityOptimizer;
    }

    public ParticleOptimizer getParticleOptimizer() {
        return particleOptimizer;
    }

    public RenderOptimizer getRenderOptimizer() {
        return renderOptimizer;
    }
}
