package dqu.additionaladditions.mixin;

import dqu.additionaladditions.config.Config;
import dqu.additionaladditions.config.ConfigValues;
import dqu.additionaladditions.misc.AdditionalInstrument;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.gen.Invoker;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.ArrayList;
import java.util.Arrays;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;

@Mixin(NoteBlockInstrument.class)
public class NoteBlockInstrumentMixin {
    @SuppressWarnings("InvokerTarget")
    @Invoker("<init>")
    private static NoteBlockInstrument newInstrument(String internalName, int internalId, String name, SoundEvent sound) {
        throw new AssertionError();
    }

    @SuppressWarnings("ShadowTarget")
    @Shadow
    @Mutable
    private static @Final NoteBlockInstrument[] field_12652;

    @SuppressWarnings("UnresolvedMixinReference")
    @Inject(method = "<clinit>", at = @At(value = "FIELD", opcode = Opcodes.PUTSTATIC, target = "Lnet/minecraft/world/level/block/state/properties/NoteBlockInstrument;field_12652:[Lnet/minecraft/world/level/block/state/properties/NoteBlockInstrument;", shift = At.Shift.AFTER))
    private static void addCustomInstrument(CallbackInfo ci) {
        var variants = new ArrayList<>(Arrays.asList(field_12652));
        var last = variants.get(variants.size() - 1);
        var amethyst = newInstrument("AMETHYST", last.ordinal()+1, "amethyst", SoundEvents.AMETHYST_CLUSTER_PLACE);
        AdditionalInstrument.AMETHYST = amethyst;
        variants.add(amethyst);
        field_12652 = variants.toArray(new NoteBlockInstrument[0]);
    }

    @Inject(method = "byState", at = @At("RETURN"), cancellable = true)
    private static void amethystInstrument(BlockState state, CallbackInfoReturnable<NoteBlockInstrument> cir) {
        if (state.is(Blocks.AMETHYST_BLOCK) && Config.getBool(ConfigValues.NOTE_BLOCK_AMETHYST_SOUNDS))
            cir.setReturnValue(AdditionalInstrument.AMETHYST);
    }
}