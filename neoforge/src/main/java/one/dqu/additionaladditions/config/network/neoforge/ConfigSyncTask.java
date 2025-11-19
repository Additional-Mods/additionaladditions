package one.dqu.additionaladditions.config.network.neoforge;

import com.google.gson.JsonElement;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.protocol.configuration.ServerConfigurationPacketListener;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.network.ConfigurationTask;
import net.neoforged.neoforge.network.configuration.ICustomConfigurationTask;
import one.dqu.additionaladditions.config.ConfigProperty;
import one.dqu.additionaladditions.config.network.ConfigSyncS2CPayload;

import java.util.Map;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public record ConfigSyncTask(ServerConfigurationPacketListener listener) implements ICustomConfigurationTask {
    public static final ConfigurationTask.Type TYPE = new Type(ConfigSyncS2CPayload.ID);

    @Override
    public void run(Consumer<CustomPacketPayload> consumer) {
        Map<ResourceLocation, JsonElement> map = ConfigProperty.getAll().stream()
                .collect(Collectors.toMap(
                        ConfigProperty::path,
                        property -> property.serialize().getOrThrow()
                ));
        consumer.accept(new ConfigSyncS2CPayload(map));
        listener.finishCurrentTask(TYPE);
    }

    @Override
    public Type type() {
        return TYPE;
    }
}
