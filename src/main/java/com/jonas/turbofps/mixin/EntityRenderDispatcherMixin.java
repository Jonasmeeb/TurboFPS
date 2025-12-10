package com.jonas.turbofps.mixin;

import com.jonas.turbofps.TurboFPSClient;
import net.minecraft.client.render.Frustum;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(EntityRenderDispatcher.class)
public class EntityRenderDispatcherMixin {

    @Inject(method = "shouldRender", at = @At("HEAD"), cancellable = true)
    private void shouldRenderEntity(Entity entity, Frustum frustum,
                                    double cameraX, double cameraY, double cameraZ,
                                    CallbackInfoReturnable<Boolean> cir) {

        if (TurboFPSClient.getOptimizationManager() == null) return;

        double distanceSquared = entity.squaredDistanceTo(cameraX, cameraY, cameraZ);

        if (!TurboFPSClient.getOptimizationManager()
                .getEntityOptimizer()
                .shouldRenderEntity(distanceSquared)) {

            cir.setReturnValue(false);
            cir.cancel();
        }
    }
}
