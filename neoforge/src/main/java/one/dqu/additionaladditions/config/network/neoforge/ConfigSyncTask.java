package one.dqu.additionaladditions.config.network.neoforge;

import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.protocol.configuration.ServerConfigurationPacketListener;
import net.minecraft.server.network.ConfigurationTask;
import net.neoforged.neoforge.network.configuration.ICustomConfigurationTask;
import one.dqu.additionaladditions.config.network.ConfigSyncS2CPayload;

import java.util.function.Consumer;

public record ConfigSyncTask(ServerConfigurationPacketListener listener) implements ICustomConfigurationTask {
    public static final ConfigurationTask.Type TYPE = new Type(ConfigSyncS2CPayload.ID);

    @Override
    public void run(Consumer<CustomPacketPayload> consumer) {
        consumer.accept(ConfigSyncS2CPayload.create());
        listener.finishCurrentTask(TYPE);
    }

    @Override
    public Type type() {
        return TYPE;
    }
}
