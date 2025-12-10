package com.jonas.turbofps.optimization;

import com.jonas.turbofps.config.TurboConfig;

public class RenderOptimizer {
	private final TurboConfig config;
	private boolean reduceFoliage;
	private boolean optimizeChunkLoading;
	private boolean reduceSkyUpdates;
	private int chunkUpdateThreads;

	public RenderOptimizer(TurboConfig config) {
		this.config = config;
	}

	public void apply() {
		switch (config.getCurrentPreset()) {
			case POTATO:
				reduceFoliage = true;
				optimizeChunkLoading = true;
				reduceSkyUpdates = true;
				chunkUpdateThreads = 1;
				break;
			case LOW_END:
				reduceFoliage = true;
				optimizeChunkLoading = true;
				reduceSkyUpdates = true;
				chunkUpdateThreads = 2;
				break;
			case BALANCED:
				reduceFoliage = false;
				optimizeChunkLoading = true;
				reduceSkyUpdates = false;
				chunkUpdateThreads = 3;
				break;
			case HIGH_END:
				reduceFoliage = false;
				optimizeChunkLoading = false;
				reduceSkyUpdates = false;
				chunkUpdateThreads = 4;
				break;
			case ULTRA:
				reduceFoliage = false;
				optimizeChunkLoading = false;
				reduceSkyUpdates = false;
				chunkUpdateThreads = 6;
				break;
		}
	}

	public boolean shouldReduceFoliage() {
		return reduceFoliage && config.isRenderOptimization();
	}

	public boolean shouldOptimizeChunkLoading() {
		return optimizeChunkLoading && config.isRenderOptimization();
	}

	public boolean shouldReduceSkyUpdates() {
		return reduceSkyUpdates && config.isRenderOptimization();
	}

	public int getChunkUpdateThreads() {
		return chunkUpdateThreads;
	}
}