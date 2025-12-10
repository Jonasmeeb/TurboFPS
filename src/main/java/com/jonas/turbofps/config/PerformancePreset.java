package com.jonas.turbofps.config;

public enum PerformancePreset {
	POTATO("Potato PC", "Maximum FPS - Ultra Low Settings", 4, "For very weak hardware"),
	LOW_END("Low End", "High FPS - Low Settings", 6, "For older computers"),
	BALANCED("Balanced", "Balanced FPS & Quality", 10, "Recommended for most users"),
	HIGH_END("High End", "High Quality - Good FPS", 16, "For powerful computers"),
	ULTRA("Ultra", "Maximum Quality", 24, "For high-end systems");

	private final String displayName;
	private final String description;
	private final int defaultRenderDistance;
	private final String recommendation;

	PerformancePreset(String displayName, String description, int defaultRenderDistance, String recommendation) {
		this.displayName = displayName;
		this.description = description;
		this.defaultRenderDistance = defaultRenderDistance;
		this.recommendation = recommendation;
	}

	public String getDisplayName() {
		return displayName;
	}

	public String getDescription() {
		return description;
	}

	public int getDefaultRenderDistance() {
		return defaultRenderDistance;
	}

	public String getRecommendation() {
		return recommendation;
	}

	@Override
	public String toString() {
		return displayName;
	}
}