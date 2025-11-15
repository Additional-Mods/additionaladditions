package dqu.additionaladditions.mixin.glint;

import com.mojang.blaze3d.vertex.ByteBufferBuilder;
import dqu.additionaladditions.glint.GlintRenderType;
import it.unimi.dsi.fastutil.objects.Object2ObjectLinkedOpenHashMap;
import net.minecraft.client.renderer.RenderBuffers;
import net.minecraft.client.renderer.RenderType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(RenderBuffers.class)
public class RenderBuffersMixin {
    @Unique
    private static boolean injected = false;

    @Inject(method = "put", at = @At("HEAD"))
    private static void put(Object2ObjectLinkedOpenHashMap<RenderType, ByteBufferBuilder> object2ObjectLinkedOpenHashMap, RenderType renderType, CallbackInfo ci) {
        if (!injected) {
            injected = true;
            for (RenderType type : GlintRenderType.getRenderTypes()) {
                object2ObjectLinkedOpenHashMap.put(type, new ByteBufferBuilder(type.bufferSize()));
            }
        }
    }
}
