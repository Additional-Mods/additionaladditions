package one.dqu.additionaladditions.item;

import one.dqu.additionaladditions.AdditionalAdditions;
import java.util.List;

import one.dqu.additionaladditions.config.Config;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;

public class MysteriousBundleItem extends Item {
    public MysteriousBundleItem(Properties settings) {
        super(settings);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level world, Player user, InteractionHand hand) {
        if (!Config.MYSTERIOUS_BUNDLE.get().enabled()) { return InteractionResultHolder.fail(user.getItemInHand(hand)); }
        if (world.isClientSide()) return InteractionResultHolder.success(user.getItemInHand(hand));
        ResourceKey lootTableID = ResourceKey.createRegistryKey(ResourceLocation.tryBuild(AdditionalAdditions.NAMESPACE, "mysterious_bundle"));
        LootParams lootParams = (new LootParams.Builder((ServerLevel) world)).create(LootContextParamSets.EMPTY);
        LootTable lootTable = ((ServerLevel) world).getServer().reloadableRegistries().getLootTable(lootTableID);
        List<ItemStack> stackList = lootTable.getRandomItems(lootParams);

        for (ItemStack stack : stackList) {
            ItemEntity entity = new ItemEntity(world, user.getX(), user.getY(), user.getZ(), stack);
            world.addFreshEntity(entity);
        }

        user.getItemInHand(hand).shrink(1);
        return InteractionResultHolder.success(user.getItemInHand(hand));
    }
}
