package dqu.additionaladditions.item;

import dqu.additionaladditions.AdditionalAdditions;
import dqu.additionaladditions.config.Config;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.context.LootContext;
import net.minecraft.loot.context.LootContextTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

import java.util.List;

public class MysteriousBundleItem extends Item {
    public MysteriousBundleItem(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if (!Config.get("GlowSticks")) { return TypedActionResult.fail(user.getStackInHand(hand)); }
        if (world.isClient()) return TypedActionResult.success(user.getStackInHand(hand));
        Identifier lootTableID = new Identifier(AdditionalAdditions.namespace, "mysterious_bundle");
        LootContext lootContext = (new LootContext.Builder((ServerWorld) world)).random(world.random).build(LootContextTypes.EMPTY);
        LootTable lootTable = ((ServerWorld) world).getServer().getLootManager().getTable(lootTableID);
        List<ItemStack> stackList = lootTable.generateLoot(lootContext);

        for (ItemStack stack : stackList) {
            ItemEntity entity = new ItemEntity(world, user.getX(), user.getY(), user.getZ(), stack);
            world.spawnEntity(entity);
        }

        user.getStackInHand(hand).decrement(1);
        return TypedActionResult.success(user.getStackInHand(hand));
    }
}
