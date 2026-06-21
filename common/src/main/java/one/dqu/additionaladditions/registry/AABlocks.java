package one.dqu.additionaladditions.registry;

import com.google.common.base.Suppliers;
import com.google.common.collect.ImmutableSet;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.ai.village.poi.PoiTypes;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import one.dqu.additionaladditions.AdditionalAdditions;
import one.dqu.additionaladditions.feature.copper_patina.CopperPatinaBlock;
import one.dqu.additionaladditions.feature.copper_patina.CopperPatinaContent;
import one.dqu.additionaladditions.feature.copper_patina.PatinaBlock;
import one.dqu.additionaladditions.feature.glow_stick.GlowStickBlock;
import one.dqu.additionaladditions.feature.glow_stick.GlowStickContent;
import one.dqu.additionaladditions.feature.redstone_lamp.RedstoneLampContent;
import one.dqu.additionaladditions.feature.redstone_lamp.TintedRedstoneLampBlock;
import one.dqu.additionaladditions.feature.rope.RopeBlock;
import one.dqu.additionaladditions.feature.rope.RopeContent;
import one.dqu.additionaladditions.feature.rose_gold.RoseGoldContent;
import one.dqu.additionaladditions.feature.sniffer_plants.SnifferPlantsContent;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import one.dqu.additionaladditions.core.util.Registrar;

import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Collectors;

@SuppressWarnings("unused")
public class AABlocks {
    public static final Supplier<RopeBlock> ROPE_BLOCK = RopeContent.ropeBlock();

    public static final Supplier<TintedRedstoneLampBlock> TINTED_REDSTONE_LAMP = RedstoneLampContent.tintedRedstoneLamp();

    public static final Supplier<CopperPatinaBlock> COPPER_PATINA = CopperPatinaContent.copperPatinaBlock();

    public static final Supplier<GlowStickBlock> GLOW_STICK_BLOCK = GlowStickContent.glowStickBlock();

    public static final Supplier<PatinaBlock> PATINA_BLOCK = CopperPatinaContent.patinaBlock();

    public static final Supplier<Block> ROSE_GOLD_BLOCK = RoseGoldContent.roseGoldBlock();

    // sniffer plants

    public static final Supplier<Block> COTTONSHIVER = SnifferPlantsContent.cottonshiver();
    public static final Supplier<Block> COTTONSHIVER_CROP = SnifferPlantsContent.cottonshiverCrop();

    public static final Supplier<Block> MUDFLOWER = SnifferPlantsContent.mudflower();
    public static final Supplier<Block> MUDFLOWER_CROP = SnifferPlantsContent.mudflowerCrop();

    public static final Supplier<Block> CRIMSON_BLOSSOM = SnifferPlantsContent.crimsonBlossom();
    public static final Supplier<Block> CRIMSON_BLOSSOM_CROP = SnifferPlantsContent.crimsonBlossomCrop();

    public static final Supplier<Block> AMBER_BLOSSOM = SnifferPlantsContent.amberBlossom();
    public static final Supplier<Block> AMBER_BLOSSOM_CROP = SnifferPlantsContent.amberBlossomCrop();

    public static final Supplier<Block> BULBUS = SnifferPlantsContent.bulbus();
    public static final Supplier<Block> BULBUS_CROP = SnifferPlantsContent.bulbusCrop();

    public static final Supplier<Block> SAWTOOTH_FERN = SnifferPlantsContent.sawtoothFern();
    public static final Supplier<Block> SAWTOOTH_FERN_CROP = SnifferPlantsContent.sawtoothFernCrop();

    public static final Supplier<Block> FROSTLEAF = SnifferPlantsContent.frostleaf();
    public static final Supplier<Block> FROSTLEAF_CROP = SnifferPlantsContent.frostleafCrop();

    public static final Supplier<Block> WISTERIA = SnifferPlantsContent.wisteria();
    public static final Supplier<Block> WISTERIA_CROP = SnifferPlantsContent.wisteriaCrop();

    public static final Supplier<Block> SPIKEBLOSSOM = SnifferPlantsContent.spikeblossom();
    public static final Supplier<Block> SPIKEBLOSSOM_CROP = SnifferPlantsContent.spikeblossomCrop();

    public static final Supplier<Block> SNAPDRAGON = SnifferPlantsContent.snapdragon();
    public static final Supplier<Block> SNAPDRAGON_CROP = SnifferPlantsContent.snapdragonCrop();

    public static final Supplier<Block> LOTUS_LILY = SnifferPlantsContent.lotusLily();
    public static final Supplier<Block> LOTUS_LILY_CROP = SnifferPlantsContent.lotusLilyCrop();

    // amethyst lamp poi

    private static final Supplier<Set<BlockState>> AMETHYST_LAMP_POI_STATES = Suppliers.memoize(() -> ImmutableSet.copyOf(AABlocks.TINTED_REDSTONE_LAMP.get().getStateDefinition().getPossibleStates().stream().filter(state -> state.getValue(RedstoneLampBlock.LIT)).toList()));
    public static ResourceKey<PoiType> AMETHYST_LAMP_POI_KEY = ResourceKey.create(
            Registries.POINT_OF_INTEREST_TYPE, Identifier.tryBuild(AdditionalAdditions.NAMESPACE, "amethyst_lamp_poi")
    );
    public static Supplier<PoiType> AMETHYST_LAMP_POI = AARegistries.POI.register(
            AMETHYST_LAMP_POI_KEY.identifier(),
            () -> new PoiType(AMETHYST_LAMP_POI_STATES.get(), 0, 0)
    );

    public static void registerAll() {
        // amethyst lamp poi
        Registrar.defer(() -> {
            Holder<PoiType> poi = BuiltInRegistries.POINT_OF_INTEREST_TYPE.getOrThrow(AMETHYST_LAMP_POI_KEY);

            // collect unregistered states. on neoforge they seem to get registered automatically, but not on fabric?
            Set<BlockState> states = AMETHYST_LAMP_POI_STATES.get().stream()
                    .filter(state -> PoiTypes.forState(state).orElse(null) == null)
                    .collect(Collectors.toUnmodifiableSet());

            PoiTypes.registerBlockStates(poi, states);
        });
    }
}
