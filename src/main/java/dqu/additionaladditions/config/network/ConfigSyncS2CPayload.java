package dqu.additionaladditions.config.network;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import dqu.additionaladditions.AdditionalAdditions;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;

import java.util.HashMap;
import java.util.Map;

public record ConfigSyncS2CPayload(Map<ResourceLocation, JsonElement> config) implements CustomPacketPayload {
    public static final ResourceLocation ID = ResourceLocation.fromNamespaceAndPath(AdditionalAdditions.namespace, "config_sync");
    public static final Type<ConfigSyncS2CPayload> TYPE = new Type<>(ID);

    private static final StreamCodec<ByteBuf, JsonElement> JSON_ELEMENT_STREAM_CODEC =
            ByteBufCodecs.STRING_UTF8.map(
                    JsonParser::parseString,
                    JsonElement::toString
            );

    public static final StreamCodec<FriendlyByteBuf, ConfigSyncS2CPayload> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.map(
                    HashMap::new,
                    ResourceLocation.STREAM_CODEC,
                    JSON_ELEMENT_STREAM_CODEC
            ),
            ConfigSyncS2CPayload::config,
            ConfigSyncS2CPayload::new
    );

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
