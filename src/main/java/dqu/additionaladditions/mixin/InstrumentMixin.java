package dqu.additionaladditions.mixin;

import dqu.additionaladditions.config.Config;
import dqu.additionaladditions.config.value.ConfigValues;
import dqu.additionaladditions.misc.AdditionalInstrument;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.enums.Instrument;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
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

@Mixin(Instrument.class)
public class InstrumentMixin {
    @SuppressWarnings("InvokerTarget")
    @Invoker("<init>")
    private static Instrument newInstrument(String internalName, int internalId, String name, SoundEvent sound) {
        throw new AssertionError();
    }

    @SuppressWarnings("ShadowTarget")
    @Shadow
    @Mutable
    private static @Final Instrument[] field_12652;

    @SuppressWarnings("UnresolvedMixinReference")
    @Inject(method = "<clinit>", at = @At(value = "FIELD", opcode = Opcodes.PUTSTATIC, target = "Lnet/minecraft/block/enums/Instrument;field_12652:[Lnet/minecraft/block/enums/Instrument;", shift = At.Shift.AFTER))
    private static void addCustomInstrument(CallbackInfo ci) {
        var variants = new ArrayList<>(Arrays.asList(field_12652));
        var last = variants.get(variants.size() - 1);
        var amethyst = newInstrument("AMETHYST", last.ordinal()+1, "amethyst", SoundEvents.BLOCK_AMETHYST_CLUSTER_PLACE);
        AdditionalInstrument.AMETHYST = amethyst;
        variants.add(amethyst);
        field_12652 = variants.toArray(new Instrument[0]);
    }

    @Inject(method = "fromBlockState", at = @At("RETURN"), cancellable = true)
    private static void amethystInstrument(BlockState state, CallbackInfoReturnable<Instrument> cir) {
        if (state.isOf(Blocks.AMETHYST_BLOCK) && Config.getBool(ConfigValues.NOTE_BLOCK_AMETHYST_SOUNDS))
            cir.setReturnValue(AdditionalInstrument.AMETHYST);
    }
}