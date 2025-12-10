package com.jonas.turbofps.mixin;

import com.jonas.turbofps.config.ModMenuIntegration;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.option.OptionsScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.GridWidget;
import net.minecraft.client.gui.widget.ThreePartsLayoutWidget;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(OptionsScreen.class)
public class OptionsScreenMixin extends Screen {
    @Shadow
    private ThreePartsLayoutWidget layout;

    protected OptionsScreenMixin(Text title) {
        super(title);
    }

    @Inject(method = "init", at = @At("RETURN"))
    private void addTurboFPSButton(CallbackInfo ci) {
        if (this.client == null) return;

        GridWidget gridWidget = this.layout.addBody(new GridWidget());
        gridWidget.getMainPositioner().marginX(4).marginY(4);
        GridWidget.Adder adder = gridWidget.createAdder(2);

        // Add TurboFPS button
        adder.add(ButtonWidget.builder(Text.literal("TurboFPS Settings..."), button -> {
            this.client.setScreen(new ModMenuIntegration.TurboConfigScreen(this));
        }).build());
    }
}