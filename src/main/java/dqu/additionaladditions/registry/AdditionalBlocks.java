package dqu.additionaladditions.registry;

import dqu.additionaladditions.AdditionalAdditions;
import dqu.additionaladditions.Config;
import dqu.additionaladditions.block.CopperPatinaBlock;
import dqu.additionaladditions.block.GlowStickBlock;
import dqu.additionaladditions.block.RopeBlock;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Material;
import net.minecraft.block.RedstoneLampBlock;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemGroup;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class AdditionalBlocks {
    public static final RopeBlock ROPE_BLOCK = new RopeBlock(FabricBlockSettings.of(Material.BAMBOO).noCollision().sounds(BlockSoundGroup.WOOL));
    public static final RedstoneLampBlock AMETHYST_LAMP = new RedstoneLampBlock(FabricBlockSettings.of(Material.REDSTONE_LAMP).sounds(BlockSoundGroup.GLASS));
    public static final CopperPatinaBlock COPPER_PATINA = new CopperPatinaBlock(FabricBlockSettings.of(Material.CARPET).noCollision().sounds(BlockSoundGroup.TUFF));
    public static final GlowStickBlock GLOW_STICK_BLOCK = new GlowStickBlock(FabricBlockSettings.of(Material.CARPET).noCollision().luminance(12).breakInstantly());

    public static void registerAll() {
        if(Config.get("GlowStick")) Registry.register(Registry.BLOCK, new Identifier(AdditionalAdditions.namespace, "glow_stick"), GLOW_STICK_BLOCK);
        if (Config.get("CopperPatina")) {
            Registry.register(Registry.BLOCK, new Identifier(AdditionalAdditions.namespace, "copper_patina"), COPPER_PATINA);
            Registry.register(Registry.ITEM, new Identifier(AdditionalAdditions.namespace, "copper_patina"),
                    new BlockItem(COPPER_PATINA, new FabricItemSettings().group(ItemGroup.REDSTONE)));
        }
        if (Config.get("Ropes")) {
            Registry.register(Registry.BLOCK, new Identifier(AdditionalAdditions.namespace, "rope"), ROPE_BLOCK);
            Registry.register(Registry.ITEM, new Identifier(AdditionalAdditions.namespace, "rope"),
                    new BlockItem(ROPE_BLOCK, new FabricItemSettings().group(ItemGroup.MISC)));
        }
        if (Config.get("AmethystLamp")) {
            Registry.register(Registry.BLOCK, new Identifier(AdditionalAdditions.namespace, "amethyst_lamp"), AMETHYST_LAMP);
            Registry.register(Registry.ITEM, new Identifier(AdditionalAdditions.namespace, "amethyst_lamp"),
                    new BlockItem(AMETHYST_LAMP, new FabricItemSettings().group(ItemGroup.REDSTONE)));
        }
    }
}
