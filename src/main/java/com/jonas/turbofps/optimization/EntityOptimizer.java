package com.jonas.turbofps.optimization;

import com.jonas.turbofps.config.TurboConfig;

public class EntityOptimizer {
	private final TurboConfig config;
	private int maxEntityRenderDistance;
	private boolean cullHiddenEntities;
	private boolean reduceEntityAnimations;

	public EntityOptimizer(TurboConfig config) {
		this.config = config;
	}

	public void apply() {
		switch (config.getCurrentPreset()) {
			case POTATO:
				maxEntityRenderDistance = 32;
				cullHiddenEntities = true;
				reduceEntityAnimations = true;
				break;
			case LOW_END:
				maxEntityRenderDistance = 64;
				cullHiddenEntities = true;
				reduceEntityAnimations = true;
				break;
			case BALANCED:
				maxEntityRenderDistance = 96;
				cullHiddenEntities = true;
				reduceEntityAnimations = false;
				break;
			case HIGH_END:
				maxEntityRenderDistance = 128;
				cullHiddenEntities = false;
				reduceEntityAnimations = false;
				break;
			case ULTRA:
				maxEntityRenderDistance = 256;
				cullHiddenEntities = false;
				reduceEntityAnimations = false;
				break;
		}
	}

	public boolean shouldRenderEntity(double distanceSquared) {
		if (!config.isEntityCulling()) {
			return true;
		}
		return distanceSquared <= (maxEntityRenderDistance * maxEntityRenderDistance);
	}

	public boolean shouldCullHiddenEntities() {
		return cullHiddenEntities && config.isEntityCulling();
	}

	public boolean shouldReduceAnimations() {
		return reduceEntityAnimations && config.isEntityCulling();
	}

	public int getMaxRenderDistance() {
		return maxEntityRenderDistance;
	}
}