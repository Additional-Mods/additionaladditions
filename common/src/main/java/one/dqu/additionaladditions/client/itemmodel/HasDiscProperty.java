package one.dqu.additionaladditions.client.itemmodel;

import com.mojang.serialization.MapCodec;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.item.properties.conditional.ConditionalItemModelProperty;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import one.dqu.additionaladditions.feature.album.AlbumContents;
import one.dqu.additionaladditions.item.PocketJukeboxItem;
import one.dqu.additionaladditions.registry.AAMisc;
import org.jetbrains.annotations.Nullable;

@Environment(EnvType.CLIENT)
public record HasDiscProperty() implements ConditionalItemModelProperty {
    public static final MapCodec<HasDiscProperty> MAP_CODEC = MapCodec.unit(new HasDiscProperty());

    @Override
    public boolean get(ItemStack stack, @Nullable ClientLevel level, @Nullable LivingEntity entity, int seed, ItemDisplayContext context) {
        // album
        AlbumContents contents = stack.get(AAMisc.ALBUM_CONTENTS_COMPONENT.get());
        if (contents != null) return !contents.items().isEmpty();

        // pocket jukebox
        return PocketJukeboxItem.hasDisc(stack);
    }

    @Override
    public MapCodec<HasDiscProperty> type() {
        return MAP_CODEC;
    }
}
