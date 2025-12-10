package com.jonas.turbofps.optimization;

import com.jonas.turbofps.config.TurboConfig;

public class ParticleOptimizer {
	private final TurboConfig config;
	private float particleMultiplier;
	private int maxParticles;

	public ParticleOptimizer(TurboConfig config) {
		this.config = config;
	}

	public void apply() {
		switch (config.getCurrentPreset()) {
			case POTATO:
				particleMultiplier = 0.1f;
				maxParticles = 100;
				break;
			case LOW_END:
				particleMultiplier = 0.25f;
				maxParticles = 500;
				break;
			case BALANCED:
				particleMultiplier = 0.5f;
				maxParticles = 2000;
				break;
			case HIGH_END:
				particleMultiplier = 0.75f;
				maxParticles = 4000;
				break;
			case ULTRA:
				particleMultiplier = 1.0f;
				maxParticles = 8000;
				break;
		}
	}

	public boolean shouldSpawnParticle(float chance) {
		if (!config.isParticleReduction()) {
			return true;
		}
		return Math.random() < (chance * particleMultiplier);
	}

	public float getParticleMultiplier() {
		return particleMultiplier;
	}

	public int getMaxParticles() {
		return maxParticles;
	}
}