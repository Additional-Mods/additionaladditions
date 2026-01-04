package one.dqu.additionaladditions.registry;

import com.google.common.base.Suppliers;
import com.google.common.collect.ImmutableSet;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.ai.village.poi.PoiTypes;
import net.minecraft.world.level.block.state.BlockState;
import one.dqu.additionaladditions.AdditionalAdditions;
import one.dqu.additionaladditions.block.*;
import one.dqu.additionaladditions.config.Config;
import one.dqu.additionaladditions.item.CopperPatinaItem;
import one.dqu.additionaladditions.util.CreativeAdder;
import one.dqu.additionaladditions.util.LootAdder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.item.*;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RedstoneLampBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import one.dqu.additionaladditions.util.Registrar;

import java.util.List;
import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Collectors;

@SuppressWarnings("unused")
public class AABlocks {
    public static final Supplier<RopeBlock> ROPE_BLOCK = AARegistries.BLOCKS.register(
                    ResourceLocation.tryBuild(AdditionalAdditions.NAMESPACE, "rope"),
                    () -> new RopeBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_BROWN).pushReaction(PushReaction.DESTROY).noCollission().sound(SoundType.WOOL).explosionResistance(0f).destroyTime(0.2f))
            );

    public static final Supplier<TintedRedstoneLampBlock> TINTED_REDSTONE_LAMP = AARegistries.BLOCKS.register(
            ResourceLocation.tryBuild(AdditionalAdditions.NAMESPACE, "tinted_redstone_lamp"),
            () -> new TintedRedstoneLampBlock(BlockBehaviour.Properties.of().mapColor(MapColor.TERRACOTTA_PURPLE).pushReaction(PushReaction.NORMAL).sound(SoundType.GLASS).strength(0.3f))
    );

    public static final Supplier<CopperPatinaBlock> COPPER_PATINA = AARegistries.BLOCKS.register(
            ResourceLocation.tryBuild(AdditionalAdditions.NAMESPACE, "copper_patina"),
            () -> new CopperPatinaBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_CYAN).pushReaction(PushReaction.DESTROY).noCollission().sound(SoundType.TUFF).instabreak())
    );

    public static final Supplier<GlowStickBlock> GLOW_STICK_BLOCK = AARegistries.BLOCKS.register(
            ResourceLocation.tryBuild(AdditionalAdditions.NAMESPACE, "glow_stick"),
            () -> new GlowStickBlock(BlockBehaviour.Properties.of().mapColor(MapColor.NONE).pushReaction(PushReaction.DESTROY).noCollission().lightLevel((state) -> 12).instabreak())
    );

    public static final Supplier<PatinaBlock> PATINA_BLOCK = AARegistries.BLOCKS.register(
            ResourceLocation.tryBuild(AdditionalAdditions.NAMESPACE, "patina_block"),
            () -> new PatinaBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_CYAN).pushReaction(PushReaction.NORMAL).sound(SoundType.ROOTED_DIRT).strength(0.5f))
    );

    public static final Supplier<Block> ROSE_GOLD_BLOCK = AARegistries.BLOCKS.register(
            ResourceLocation.tryBuild(AdditionalAdditions.NAMESPACE, "rose_gold_block"),
            () -> new Block(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_PINK).requiresCorrectToolForDrops().strength(3.0f, 6.0f).sound(SoundType.METAL))
    );

    // amethyst lamp poi
    private static final Supplier<Set<BlockState>> AMETHYST_LAMP_POI_STATES = Suppliers.memoize(() -> ImmutableSet.copyOf(AABlocks.TINTED_REDSTONE_LAMP.get().getStateDefinition().getPossibleStates().stream().filter(state -> state.getValue(RedstoneLampBlock.LIT)).toList()));
    public static ResourceKey<PoiType> AMETHYST_LAMP_POI_KEY = ResourceKey.create(
            Registries.POINT_OF_INTEREST_TYPE, ResourceLocation.tryBuild(AdditionalAdditions.NAMESPACE, "amethyst_lamp_poi")
    );
    public static Supplier<PoiType> AMETHYST_LAMP_POI = AARegistries.POI.register(
            AMETHYST_LAMP_POI_KEY.location(),
            () -> new PoiType(AMETHYST_LAMP_POI_STATES.get(), 0, 0)
    );

    public static void registerAll() {
        // amethyst lamp poi
        Registrar.defer(() -> {
            Holder<PoiType> poi = BuiltInRegistries.POINT_OF_INTEREST_TYPE.getHolderOrThrow(AMETHYST_LAMP_POI_KEY);

            // collect unregistered states. on neoforge they seem to get registered automatically, but not on fabric?
            Set<BlockState> states = AMETHYST_LAMP_POI_STATES.get().stream()
                    .filter(state -> PoiTypes.forState(state).orElse(null) == null)
                    .collect(Collectors.toUnmodifiableSet());

            PoiTypes.registerBlockStates(poi, states);
        });
    }
}
