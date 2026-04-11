package one.dqu.additionaladditions.mixin.glint;

import com.mojang.blaze3d.vertex.ByteBufferBuilder;
import it.unimi.dsi.fastutil.objects.Object2ObjectLinkedOpenHashMap;
import net.minecraft.client.renderer.RenderBuffers;
import net.minecraft.client.renderer.rendertype.RenderType;
import one.dqu.additionaladditions.feature.glint.GlintRenderTypes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Collections;
import java.util.IdentityHashMap;
import java.util.Set;

/**
 * Registers colored glint render types in RenderBuffers.
 */
@Mixin(RenderBuffers.class)
public class RenderBuffersMixin {
    // some mods (e.g. Iris's HandRenderer) create their own RenderBuffers. track which maps have been injected to rather than hold a static boolean.
    @Unique
    private static final Set<Object> additionaladditions$injectedMaps = Collections.newSetFromMap(new IdentityHashMap<>());

    @Inject(method = "put", at = @At("HEAD"))
    private static void put(Object2ObjectLinkedOpenHashMap<RenderType, ByteBufferBuilder> object2ObjectLinkedOpenHashMap, RenderType renderType, CallbackInfo ci) {
        if (additionaladditions$injectedMaps.add(object2ObjectLinkedOpenHashMap)) {
            for (RenderType type : GlintRenderTypes.getRenderTypes()) {
                object2ObjectLinkedOpenHashMap.put(type, new ByteBufferBuilder(type.bufferSize()));
            }
        }
    }
}
