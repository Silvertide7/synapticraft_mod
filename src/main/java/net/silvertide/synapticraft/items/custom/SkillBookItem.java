package net.silvertide.synapticraft.items.custom;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;

import static net.silvertide.synapticraft.Synapticraft.MOD_LOGGER;

public class SkillBookItem extends Item {
    private static final int USE_DURATION = 32;
    private int xpLevelRequired = 0;

    public SkillBookItem(Properties pProperties, int xpLevelRequired) {
        super(pProperties);
        this.xpLevelRequired = xpLevelRequired;
    }
    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        ItemStack itemstack = pPlayer.getItemInHand(pUsedHand);

        if(pPlayer.experienceLevel >= xpLevelRequired){
            pPlayer.startUsingItem(pUsedHand);
            return InteractionResultHolder.consume(itemstack);
        } else {
            pPlayer.sendSystemMessage(Component.literal("This requires " + xpLevelRequired + " levels of xp to use."));
            return InteractionResultHolder.fail(itemstack);
        }
    }

    public ItemStack finishUsingItem(ItemStack pStack, Level pLevel, LivingEntity pEntityLiving) {
        Player player = pEntityLiving instanceof Player ? (Player)pEntityLiving : null;

        if (player != null) {
            if (!player.getAbilities().instabuild) {
                pStack.shrink(1);
            }
        }

        if (!pLevel.isClientSide) {
            ServerLevel serverlevel = (ServerLevel)pLevel;
            player.sendSystemMessage(Component.literal("You've gained the power of Thor!"));

            for(int i = 0; i < 5; ++i) {
                serverlevel.sendParticles(ParticleTypes.SONIC_BOOM, player.getX() + pLevel.random.nextDouble(), (double)(player.getY() + 1), (double)player.getZ() + pLevel.random.nextDouble(), 1, 0.0D, 0.0D, 0.0D, 1.0D);
            }
        }

        return pStack;
    }

    @Override
    public int getUseDuration(ItemStack pStack) {
        return USE_DURATION;
    }

    public UseAnim getUseAnimation(ItemStack pStack) {
        return UseAnim.BOW;
    }
}
