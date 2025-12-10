package com.jonas.turbofps.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jonas.turbofps.TurboFPSClient;
import net.fabricmc.loader.api.FabricLoader;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class TurboConfig {
	private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
	private static final Path CONFIG_PATH = FabricLoader.getInstance().getConfigDir().resolve("turbofps.json");

	// Preset selection
	private PerformancePreset currentPreset = PerformancePreset.BALANCED;

	// Custom settings
	private boolean entityCulling = true;
	private boolean particleReduction = true;
	private boolean renderOptimization = true;
	private boolean autoRenderDistance = true;
	private boolean autoGraphicsSettings = true;
	private int renderDistance = -1; // -1 means use preset default

	public static TurboConfig load() {
		if (Files.exists(CONFIG_PATH)) {
			try {
				String json = Files.readString(CONFIG_PATH);
				TurboConfig config = GSON.fromJson(json, TurboConfig.class);
				TurboFPSClient.LOGGER.info("Loaded config from {}", CONFIG_PATH);
				return config;
			} catch (IOException e) {
				TurboFPSClient.LOGGER.error("Failed to load config, using defaults", e);
			}
		}
		TurboConfig config = new TurboConfig();
		config.save();
		return config;
	}

	public void save() {
		try {
			String json = GSON.toJson(this);
			Files.createDirectories(CONFIG_PATH.getParent());
			Files.writeString(CONFIG_PATH, json);
			TurboFPSClient.LOGGER.info("Saved config to {}", CONFIG_PATH);
		} catch (IOException e) {
			TurboFPSClient.LOGGER.error("Failed to save config", e);
		}
	}

	public void applyPreset(PerformancePreset preset) {
		this.currentPreset = preset;
		
		// Reset render distance to use preset default
		this.renderDistance = -1;
		
		// Enable all optimizations for the preset
		this.entityCulling = true;
		this.particleReduction = true;
		this.renderOptimization = true;
		this.autoRenderDistance = true;
		this.autoGraphicsSettings = true;
		
		save();
		TurboFPSClient.LOGGER.info("Applied {} preset", preset);
	}

	// Getters and setters
	public PerformancePreset getCurrentPreset() {
		return currentPreset;
	}

	public void setCurrentPreset(PerformancePreset preset) {
		this.currentPreset = preset;
	}

	public boolean isEntityCulling() {
		return entityCulling;
	}

	public void setEntityCulling(boolean entityCulling) {
		this.entityCulling = entityCulling;
	}

	public boolean isParticleReduction() {
		return particleReduction;
	}

	public void setParticleReduction(boolean particleReduction) {
		this.particleReduction = particleReduction;
	}

	public boolean isRenderOptimization() {
		return renderOptimization;
	}

	public void setRenderOptimization(boolean renderOptimization) {
		this.renderOptimization = renderOptimization;
	}

	public boolean isAutoRenderDistance() {
		return autoRenderDistance;
	}

	public void setAutoRenderDistance(boolean autoRenderDistance) {
		this.autoRenderDistance = autoRenderDistance;
	}

	public boolean isAutoGraphicsSettings() {
		return autoGraphicsSettings;
	}

	public void setAutoGraphicsSettings(boolean autoGraphicsSettings) {
		this.autoGraphicsSettings = autoGraphicsSettings;
	}

	public int getRenderDistance() {
		if (renderDistance > 0) {
			return renderDistance;
		}
		// Return preset default
		return currentPreset.getDefaultRenderDistance();
	}

	public void setRenderDistance(int renderDistance) {
		this.renderDistance = renderDistance;
	}
}