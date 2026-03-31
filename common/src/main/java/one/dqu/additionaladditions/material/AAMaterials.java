package one.dqu.additionaladditions.material;

import net.minecraft.world.item.equipment.ArmorType;
import one.dqu.additionaladditions.config.Config;

import java.util.Map;

public class AAMaterials {
    public static final AAMaterial ROSE_GOLD = new AAMaterial(
            "rose_gold",
            Config.ROSE_GOLD_ARMOR_MATERIAL::get,
            Map.of(
                    ToolType.SWORD, Config.ROSE_GOLD_SWORD::get,
                    ToolType.PICKAXE, Config.ROSE_GOLD_PICKAXE::get,
                    ToolType.AXE, Config.ROSE_GOLD_AXE::get,
                    ToolType.SHOVEL, Config.ROSE_GOLD_SHOVEL::get,
                    ToolType.HOE, Config.ROSE_GOLD_HOE::get
            ),
            Map.of(
                    ArmorType.HELMET, Config.ROSE_GOLD_HELMET::get,
                    ArmorType.CHESTPLATE, Config.ROSE_GOLD_CHESTPLATE::get,
                    ArmorType.LEGGINGS, Config.ROSE_GOLD_LEGGINGS::get,
                    ArmorType.BOOTS, Config.ROSE_GOLD_BOOTS::get,
                    ArmorType.BODY, Config.ROSE_GOLD_BODY_ARMOR::get
            )
    );
}
