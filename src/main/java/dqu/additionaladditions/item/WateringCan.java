package dqu.additionaladditions.item;

import net.minecraft.block.BlockState;
import net.minecraft.block.Fertilizable;
import net.minecraft.block.FluidDrainable;
import net.minecraft.block.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.World;

public class WateringCan extends Item {
    public WateringCan(Settings settings) {
        super(settings);
        this.getDefaultStack().setDamage(100);
    }

    // Refilling
    public TypedActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
        ItemStack stack = player.getStackInHand(hand);
        BlockHitResult blockHitResult = raycast(world, player, RaycastContext.FluidHandling.SOURCE_ONLY);
        if (blockHitResult.getType() == HitResult.Type.BLOCK) {
            BlockPos blockPos = blockHitResult.getBlockPos();
            if (world.canPlayerModifyAt(player, blockPos)) {
                BlockState blockState = world.getBlockState(blockPos);
                if (blockState.getBlock() instanceof FluidDrainable && blockState.getMaterial() == Material.WATER ) {
                    stack.setDamage(0);
                    FluidDrainable fluid = (FluidDrainable) blockState.getBlock();
                    fluid.tryDrainFluid(world, blockPos, blockState);
                    fluid.getBucketFillSound().ifPresent((sound) -> {
                        player.playSound(sound, 1.0f, 1.0f);
                    });
                    player.swingHand(hand);
                }
            }
        }
        return TypedActionResult.pass(stack);
    }

    // Watering
    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        System.out.println("WATERING start");
        World world = context.getWorld();
        BlockPos pos = context.getBlockPos();
        BlockState state = world.getBlockState(pos);
        ItemStack stack = context.getStack();

        if (stack.getDamage() > 99) {
            use(world, context.getPlayer(), context.getHand());
            return ActionResult.FAIL;
        }

        if (state.getBlock() instanceof Fertilizable) {
            Fertilizable fertilizable = (Fertilizable) state.getBlock();
            if (fertilizable.isFertilizable(world, pos, state, world.isClient())) {
                if (!world.isClient()) {
                    if (fertilizable.canGrow(world, world.random, pos, state)) {
                        fertilizable.grow((ServerWorld) world, world.random, pos, state);
                    }

                    stack.setDamage(stack.getDamage() + 10);
                }
                return ActionResult.SUCCESS;
            }
        } else {
            use(world, context.getPlayer(), context.getHand());
        }

        return ActionResult.FAIL;
    }
}
