package one.dqu.additionaladditions.gametest;

import net.minecraft.core.component.DataComponentInitializers;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import one.dqu.additionaladditions.config.Config;
import one.dqu.additionaladditions.config.type.ArmorItemConfig;
import one.dqu.additionaladditions.config.type.ToolItemConfig;
import one.dqu.additionaladditions.registry.AAItems;

import java.util.function.Supplier;

public class ConfigTests {
    // reruns every item component initializer like a datapack reload does
    private static void reinit(GameTestHelper ctx) {
        BuiltInRegistries.DATA_COMPONENT_INITIALIZERS.build(ctx.getLevel().registryAccess())
                .forEach(DataComponentInitializers.PendingComponents::apply);
    }

    private static int maxDamage(Supplier<? extends Item> item) {
        return item.get().components().getOrDefault(DataComponents.MAX_DAMAGE, -1);
    }

    public static void roseGoldPickaxeReload(GameTestHelper ctx) {
        ToolItemConfig original = Config.ROSE_GOLD_PICKAXE.get();
        int bumped = original.durability() + 500;
        try {
            ctx.assertTrue(maxDamage(AAItems.ROSE_GOLD_PICKAXE) == original.durability(),
                    Component.literal("Pickaxe MAX_DAMAGE should start at config durability " + original.durability()));

            Config.ROSE_GOLD_PICKAXE.set(new ToolItemConfig(original.attackSpeed(), original.attackDamage(), original.blockBreakSpeed(), bumped));
            reinit(ctx);

            ctx.assertTrue(maxDamage(AAItems.ROSE_GOLD_PICKAXE) == bumped,
                    Component.literal("Pickaxe MAX_DAMAGE should be " + bumped + " after reload but was " + maxDamage(AAItems.ROSE_GOLD_PICKAXE)));
        } finally {
            Config.ROSE_GOLD_PICKAXE.set(original);
            reinit(ctx);
        }
        ctx.succeed();
    }

    public static void roseGoldArmorReload(GameTestHelper ctx) {
        ArmorItemConfig original = Config.ROSE_GOLD_HELMET.get();
        int bumped = original.durability() + 500;
        try {
            ctx.assertTrue(maxDamage(AAItems.ROSE_GOLD_HELMET) == original.durability(),
                    Component.literal("Helmet MAX_DAMAGE should start at config durability " + original.durability()));

            Config.ROSE_GOLD_HELMET.set(new ArmorItemConfig(original.protection(), bumped));
            reinit(ctx);

            ctx.assertTrue(maxDamage(AAItems.ROSE_GOLD_HELMET) == bumped,
                    Component.literal("Helmet MAX_DAMAGE should be " + bumped + " after reload but was " + maxDamage(AAItems.ROSE_GOLD_HELMET)));
        } finally {
            Config.ROSE_GOLD_HELMET.set(original);
            reinit(ctx);
        }
        ctx.succeed();
    }
}
