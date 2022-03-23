package dqu.additionaladditions.registry;

import dqu.additionaladditions.AdditionalAdditions;
import dqu.additionaladditions.block.CopperPatinaBlock;
import dqu.additionaladditions.block.GlowStickBlock;
import dqu.additionaladditions.block.RopeBlock;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.level.block.RedstoneLampBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.material.Material;

public class AdditionalBlocks {
    public static final RopeBlock ROPE_BLOCK = new RopeBlock(FabricBlockSettings.of(Material.BAMBOO).noCollission().sound(SoundType.WOOL));
    public static final RedstoneLampBlock AMETHYST_LAMP = new RedstoneLampBlock(FabricBlockSettings.of(Material.BUILDABLE_GLASS).sound(SoundType.GLASS).strength(0.3f));
    public static final CopperPatinaBlock COPPER_PATINA = new CopperPatinaBlock(FabricBlockSettings.of(Material.CLOTH_DECORATION).noCollission().sound(SoundType.TUFF));
    public static final GlowStickBlock GLOW_STICK_BLOCK = new GlowStickBlock(FabricBlockSettings.of(Material.CLOTH_DECORATION).noCollission().lightLevel((state) -> 12).instabreak());

    public static void registerAll() {
        Registry.register(Registry.BLOCK, new ResourceLocation(AdditionalAdditions.namespace, "glow_stick"), GLOW_STICK_BLOCK);
        Registry.register(Registry.BLOCK, new ResourceLocation(AdditionalAdditions.namespace, "copper_patina"), COPPER_PATINA);
        Registry.register(Registry.ITEM, new ResourceLocation(AdditionalAdditions.namespace, "copper_patina"),
                new BlockItem(COPPER_PATINA, new FabricItemSettings().tab(CreativeModeTab.TAB_REDSTONE)));
        Registry.register(Registry.BLOCK, new ResourceLocation(AdditionalAdditions.namespace, "rope"), ROPE_BLOCK);
        Registry.register(Registry.ITEM, new ResourceLocation(AdditionalAdditions.namespace, "rope"),
                new BlockItem(ROPE_BLOCK, new FabricItemSettings().tab(CreativeModeTab.TAB_MISC)));
        Registry.register(Registry.BLOCK, new ResourceLocation(AdditionalAdditions.namespace, "amethyst_lamp"), AMETHYST_LAMP);
        Registry.register(Registry.ITEM, new ResourceLocation(AdditionalAdditions.namespace, "amethyst_lamp"),
                new BlockItem(AMETHYST_LAMP, new FabricItemSettings().tab(CreativeModeTab.TAB_REDSTONE)));
    }
}
