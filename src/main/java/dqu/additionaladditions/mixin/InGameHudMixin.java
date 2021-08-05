package dqu.additionaladditions.mixin;

import dqu.additionaladditions.AdditionalAdditions;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.network.ClientPlayerEntity;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(InGameHud.class)
public abstract class InGameHudMixin {
    @Shadow @Final private MinecraftClient client;

    @Redirect(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/network/ClientPlayerEntity;isUsingSpyglass()Z"))
    private boolean spyglassOverlay(ClientPlayerEntity clientPlayerEntity) {
        if (AdditionalAdditions.zoom || clientPlayerEntity.isUsingSpyglass()) {
            client.options.smoothCameraEnabled = true;
            return true;
        } else {
            client.options.smoothCameraEnabled = false;
            return false;
        }
    }

}
