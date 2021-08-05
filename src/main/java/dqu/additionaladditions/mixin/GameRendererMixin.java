package dqu.additionaladditions.mixin;

import dqu.additionaladditions.AdditionalAdditions;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.GameRenderer;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(GameRenderer.class)
public class GameRendererMixin {
    @Shadow @Final private MinecraftClient client;

    @Inject(method = "getFov(Lnet/minecraft/client/render/Camera;FZ)D", at = @At("RETURN"), cancellable = true)
    private void getFov(CallbackInfoReturnable<Double> callbackInfo) {
        if (AdditionalAdditions.zoom && this.client.options.getPerspective().isFirstPerson()) {
            double fov = callbackInfo.getReturnValue();
            callbackInfo.setReturnValue(fov * 0.2);
        }
    }
}
