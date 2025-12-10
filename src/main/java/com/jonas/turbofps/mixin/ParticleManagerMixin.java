package com.jonas.turbofps.mixin;

import com.jonas.turbofps.TurboFPSClient;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleManager;
import net.minecraft.particle.ParticleEffect;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ParticleManager.class)
public class ParticleManagerMixin {
	
	@Inject(method = "addParticle(Lnet/minecraft/client/particle/Particle;)V", at = @At("HEAD"), cancellable = true)
	private void onAddParticle(Particle particle, CallbackInfo ci) {
		if (TurboFPSClient.getOptimizationManager() == null) {
			return;
		}
		
		if (!TurboFPSClient.getOptimizationManager().getParticleOptimizer().shouldSpawnParticle(1.0f)) {
			ci.cancel();
		}
	}
}