package com.jonas.turbofps.mixin;

import com.jonas.turbofps.TurboFPSClient;
import net.minecraft.client.render.WorldRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(WorldRenderer.class)
public class WorldRendererMixin {
	
	@ModifyVariable(
		method = "render",
		at = @At("HEAD"),
		argsOnly = true,
		ordinal = 0
	)
	private boolean modifyRenderSky(boolean renderSky) {
		if (TurboFPSClient.getOptimizationManager() == null) {
			return renderSky;
		}
		
		if (TurboFPSClient.getOptimizationManager().getRenderOptimizer().shouldReduceSkyUpdates()) {
			// Only render sky every few frames
			return renderSky && (System.currentTimeMillis() % 3 == 0);
		}
		
		return renderSky;
	}
}