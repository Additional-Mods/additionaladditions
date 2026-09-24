package one.dqu.additionaladditions.mixin;

import net.minecraft.commands.Commands;
import net.minecraft.core.LayeredRegistryAccess;
import net.minecraft.core.Registry;
import net.minecraft.server.RegistryLayer;
import net.minecraft.server.ReloadableServerResources;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.permissions.PermissionSet;
import net.minecraft.world.flag.FeatureFlagSet;
import one.dqu.additionaladditions.config.io.ConfigLoader;
import one.dqu.additionaladditions.core.util.LootAdder;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

/**
 * Reloads the config and runs prepare on LootAdder to load the custom loot table injection files
 * on every datapack (re)load, before the reloadable registries are loaded.
 * This is because resource reload listeners run after the loot table modify event is fired.
 */
@Mixin(ReloadableServerResources.class)
public class ReloadableServerResourcesMixin {
    @Inject(method = "loadResources", at = @At("HEAD"))
    private static void additionaladditions$onLoadResources(ResourceManager resourceManager, LayeredRegistryAccess<RegistryLayer> layeredRegistryAccess, List<Registry.PendingTags<?>> list, FeatureFlagSet featureFlagSet, Commands.CommandSelection commandSelection, PermissionSet permissionSet, Executor executor, Executor executor2, CallbackInfoReturnable<CompletableFuture<ReloadableServerResources>> cir) {
        ConfigLoader.load();
        LootAdder.INSTANCE.prepare(resourceManager);
    }
}
