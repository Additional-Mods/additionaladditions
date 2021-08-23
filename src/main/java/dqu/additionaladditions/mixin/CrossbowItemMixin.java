package dqu.additionaladditions.mixin;

import dqu.additionaladditions.config.Config;
import dqu.additionaladditions.registry.AdditionalEnchantments;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.CrossbowItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(CrossbowItem.class)
public class CrossbowItemMixin {
    @ModifyVariable(method = "shootAll", at = @At("HEAD"), index = 5, argsOnly = true)
    private static float shootAll(float original, World world, LivingEntity entity, Hand hand, ItemStack stack, float speed, float divergence) {
        if (!Config.get("EnchantmentPrecision")) return original;
        int level = EnchantmentHelper.getLevel(AdditionalEnchantments.ENCHANTMENT_PRECISION, stack);
        if (level <= 0) return original;
        float precision = (float) ( (level * 3) * 0.1 );
        return original - precision;
    }
}
