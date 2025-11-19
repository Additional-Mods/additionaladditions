package one.dqu.additionaladditions.util.neoforge;

import com.google.common.base.Suppliers;
import com.mojang.serialization.MapCodec;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootTable;
import net.neoforged.neoforge.common.loot.IGlobalLootModifier;
import one.dqu.additionaladditions.AdditionalAdditions;
import one.dqu.additionaladditions.util.LootHandler;

import java.util.Optional;
import java.util.function.Supplier;

public class AdditionalLootModifier implements IGlobalLootModifier {
    private static final ResourceLocation VIRTUAL_TABLE = ResourceLocation.fromNamespaceAndPath(AdditionalAdditions.NAMESPACE, "virtual");
    public static final Supplier<MapCodec<AdditionalLootModifier>> CODEC =
            Suppliers.memoize(() -> MapCodec.unit(AdditionalLootModifier::new));

    @Override
    public ObjectArrayList<ItemStack> apply(ObjectArrayList<ItemStack> generatedLoot, LootContext context) {
        if (VIRTUAL_TABLE.equals(context.getQueriedLootTableId())) {
            return generatedLoot;
        }

        LootTable.Builder lootTable = LootTable.lootTable();
        LootContext lootContext = new LootContext.Builder(context)
                .withQueriedLootTableId(VIRTUAL_TABLE)
                .create(Optional.empty());

        LootHandler.handle(
                context.getQueriedLootTableId(),
                lootTable,
                context.getLevel().registryAccess()
        );
        lootTable.build().getRandomItems(lootContext, generatedLoot::add);

        return generatedLoot;
    }

    @Override
    public MapCodec<? extends IGlobalLootModifier> codec() {
        return CODEC.get();
    }
}
