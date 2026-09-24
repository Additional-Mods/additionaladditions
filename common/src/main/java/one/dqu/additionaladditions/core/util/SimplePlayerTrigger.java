package one.dqu.additionaladditions.core.util;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.advancements.triggers.SimpleCriterionTrigger;
import net.minecraft.core.Holder;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;

import java.util.Optional;

public class SimplePlayerTrigger extends SimpleCriterionTrigger<SimplePlayerTrigger.TriggerInstance> {
    public void trigger(ServerPlayer player) {
        this.trigger(player, instance -> true);
    }

    @Override
    public Codec<TriggerInstance> codec() {
        return TriggerInstance.CODEC;
    }

    public record TriggerInstance(
            Optional<Holder<LootItemCondition>> player
    ) implements SimpleCriterionTrigger.SimpleInstance {
        public static final Codec<TriggerInstance> CODEC = RecordCodecBuilder.create(instance ->
                instance.group(
                        LootItemCondition.CODEC.optionalFieldOf("player").forGetter(TriggerInstance::player)
                ).apply(instance, TriggerInstance::new)
        );
    }
}
