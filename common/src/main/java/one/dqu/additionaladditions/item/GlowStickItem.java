package one.dqu.additionaladditions.item;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.level.block.Block;
import one.dqu.additionaladditions.config.Config;
import one.dqu.additionaladditions.entity.GlowStickEntity;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class GlowStickItem extends BlockItem {
    public GlowStickItem(Block block, Properties settings) {
        super(block, settings);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack itemStack = player.getItemInHand(hand);

        if (!Config.GLOW_STICK.get().enabled()) {
            return InteractionResultHolder.fail(itemStack);
        }


        if (!level.isClientSide()) {
            GlowStickEntity glowStickEntity = new GlowStickEntity(level, player);
            glowStickEntity.setItem(itemStack);
            glowStickEntity.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0F, 1.5F, 0F);
            level.addFreshEntity(glowStickEntity);
        }

        player.awardStat(Stats.ITEM_USED.get(this));
        if (!player.isCreative()) {
            itemStack.shrink(1);
        }

        return InteractionResultHolder.sidedSuccess(itemStack, level.isClientSide());
    }
}
