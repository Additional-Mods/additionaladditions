package dqu.additionaladditions.mixin;

import dqu.additionaladditions.AdditionalAdditions;
import dqu.additionaladditions.behaviour.BehaviourManager;
import dqu.additionaladditions.material.GildedNetheriteArmorMaterial;
import dqu.additionaladditions.material.RoseGoldArmorMaterial;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Item.class)
public abstract class ItemMixin {
    @Inject(method = "getMaxDamage", at = @At("RETURN"), cancellable = true)
    private void modifyItemDurability(CallbackInfoReturnable<Integer> cir) {
        ResourceLocation resourceLocation = Registry.ITEM.getKey((Item) (Object) this);

        if (resourceLocation.getNamespace().equals(AdditionalAdditions.namespace)) {
            String path = resourceLocation.getPath();

            if (path.startsWith(GildedNetheriteArmorMaterial.NAME)) {
                String name = GildedNetheriteArmorMaterial.NAME + "/" + path.substring(GildedNetheriteArmorMaterial.NAME.length() + 1);
                System.out.println(name);
                int durability = BehaviourManager.INSTANCE.getBehaviourValue(name, "durability");
                cir.setReturnValue(durability);
            } else if (path.startsWith(RoseGoldArmorMaterial.NAME)) {
                String name = RoseGoldArmorMaterial.NAME + "/" + path.substring(RoseGoldArmorMaterial.NAME.length() + 1);
                System.out.println(name);
                int durability = BehaviourManager.INSTANCE.getBehaviourValue(name, "durability");
                cir.setReturnValue(durability);
            }
        }
    }
}
