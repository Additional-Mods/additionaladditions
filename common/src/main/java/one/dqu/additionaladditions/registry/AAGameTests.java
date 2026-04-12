package one.dqu.additionaladditions.registry;

import one.dqu.additionaladditions.gametest.tests.CopperPatinaTests;
import one.dqu.additionaladditions.gametest.tests.MiscTests;
import one.dqu.additionaladditions.gametest.tests.RopeArrowTests;
import one.dqu.additionaladditions.gametest.tests.WrenchTests;
import one.dqu.additionaladditions.gametest.AAGameTest;

public class AAGameTests {
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
    }
}