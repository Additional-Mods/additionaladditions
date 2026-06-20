package one.dqu.additionaladditions.registry;

import net.minecraft.core.registries.Registries;
import net.minecraft.gametest.framework.TestEnvironmentDefinition;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import one.dqu.additionaladditions.AdditionalAdditions;
import one.dqu.additionaladditions.core.builder.AAGameTest;
import one.dqu.additionaladditions.gametest.*;

public class AAGameTests {
    // data/additionaladditions/test_environment/fast_growth.json
    private static final ResourceKey<TestEnvironmentDefinition<?>> FAST_GROWTH = ResourceKey.create(
            Registries.TEST_ENVIRONMENT,
            Identifier.fromNamespaceAndPath(AdditionalAdditions.NAMESPACE, "fast_growth")
    );

    public static void registerAll() {
        // misc
        new AAGameTest().function(MiscTests::example).create("example");
        new AAGameTest().function(MiscTests::turkishLocale).create("turkish_locale");

        // copper patina
        new AAGameTest().function(CopperPatinaTests::decay).create("copper_patina_decay");
        new AAGameTest().function(CopperPatinaTests::interferenceCrossing).create("copper_patina_interference_crossing");
        new AAGameTest().function(CopperPatinaTests::interferenceDirectTransition).create("copper_patina_interference_direct_transition");
        new AAGameTest().function(CopperPatinaTests::interferenceSoftPower).create("copper_patina_interference_soft_power");
        new AAGameTest().maxTicks(200).function(CopperPatinaTests::transmit).create("copper_patina_transmit");
        new AAGameTest().function(CopperPatinaTests::scrape).create("copper_patina_scrape");
        new AAGameTest().function(CopperPatinaTests::oxidize).create("copper_patina_oxidize");

        // rope arrow
        new AAGameTest().structure("empty_big").function(RopeArrowTests::placementPickup).create("rope_arrow_placement_pickup");
        new AAGameTest().structure("empty_big").function(RopeArrowTests::placementNoPickup).create("rope_arrow_placement_no_pickup");
        new AAGameTest().structure("empty_big").function(RopeArrowTests::badPlacement).create("rope_arrow_bad_placement");
        new AAGameTest().function(RopeArrowTests::entityHitPickup).create("rope_arrow_entity_hit_pickup");
        new AAGameTest().function(RopeArrowTests::entityHitNoPickup).create("rope_arrow_entity_hit_no_pickup");
        new AAGameTest().structure("empty_big").function(RopeArrowTests::partialPlacementPickup).create("rope_arrow_partial_placement_pickup");
        new AAGameTest().structure("empty_big").function(RopeArrowTests::partialPlacementNoPickup).create("rope_arrow_partial_placement_no_pickup");

        // wrench
        new AAGameTest().function(WrenchTests::dispenser).create("wrench_dispenser");
        new AAGameTest().maxTicks(200).function(WrenchTests::hopper).create("wrench_hopper");
        new AAGameTest().function(WrenchTests::piston).create("wrench_piston");
        new AAGameTest().function(WrenchTests::player).create("wrench_player");

        // sniffer plants
        new AAGameTest().structure("empty_big").function(SnifferPlantsTests::stages).create("sniffer_plants_stages");
        new AAGameTest().structure("empty_big").maxTicks(2000).environment(FAST_GROWTH)
                .function(SnifferPlantsTests::naturalGrowth).create("sniffer_plants_natural_growth");

        // config
        new AAGameTest().function(ConfigTests::roseGoldPickaxeReload).create("config_rose_gold_pickaxe_reload");
        new AAGameTest().function(ConfigTests::roseGoldArmorReload).create("config_rose_gold_armor_reload");

        // watering can
        new AAGameTest().structure("empty_big").function(WateringCanTests::fillWaterFull).create("watering_can_fill_water_full");
        new AAGameTest().structure("empty_big").function(WateringCanTests::fillWaterPartial).create("watering_can_fill_water_partial");
        new AAGameTest().structure("empty_big").function(WateringCanTests::fillWaterCauldron).create("watering_can_fill_water_cauldron");
        new AAGameTest().structure("empty_big").function(WateringCanTests::interactFarmland).create("watering_can_interact_farmland");
        new AAGameTest().structure("empty_big").function(WateringCanTests::interactCrop).create("watering_can_interact_crop");
        new AAGameTest().structure("empty_big").function(WateringCanTests::interactGrownCrop).create("watering_can_interact_grown_crop");
        new AAGameTest().structure("empty_big").function(WateringCanTests::interactNoFarmland).create("watering_can_interact_no_farmland");
    }
}