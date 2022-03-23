package dqu.additionaladditions.mixin;

import dqu.additionaladditions.AdditionalAdditions;
import dqu.additionaladditions.config.Config;
import dqu.additionaladditions.config.ConfigValues;
import dqu.additionaladditions.registry.AdditionalItems;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.text.TranslatableText;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(InGameHud.class)
public abstract class InGameHudMixin {
    @Shadow @Final private MinecraftClient client;

    @Redirect(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/network/ClientPlayerEntity;isUsingSpyglass()Z"))
    private boolean spyglassOverlay(ClientPlayerEntity clientPlayerEntity) {
        return AdditionalAdditions.zoom || clientPlayerEntity.isUsingSpyglass();
    }

    @Inject(method = "render", at = @At("TAIL"))
    private void depthMeterMessage(CallbackInfo ci) {
        if (!Config.getBool(ConfigValues.DEPTH_METER, "enabled")) return;
        if (client.player.isHolding(AdditionalItems.DEPTH_METER_ITEM)) {
            if (Config.getBool(ConfigValues.DEPTH_METER, "displayElevationAlways")) {
                String level = String.valueOf((int) client.player.getY());
                client.player.sendMessage(new TranslatableText("depth_meter.elevation", level), true);
            }
        }
    }
}
