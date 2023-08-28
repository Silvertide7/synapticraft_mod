package net.silvertide.synapticraft.items.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.*;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.EnchantmentMenu;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.EnchantmentTableBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

import javax.annotation.Nullable;

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
            if(pLevel.isClientSide) {
                pPlayer.sendSystemMessage(Component.literal("This requires " + xpLevelRequired + " levels of experience to use."));
            }
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

//            player.openMenu(getMenuProvider(pLevel, pPos));

            for(int i = 0; i < 5; ++i) {
                serverlevel.sendParticles(ParticleTypes.SONIC_BOOM, player.getX() + pLevel.random.nextDouble(), (double)(player.getY() + 1), (double)player.getZ() + pLevel.random.nextDouble(), 1, 0.0D, 0.0D, 0.0D, 1.0D);
            }
        } else {
            player.sendSystemMessage(Component.literal("You've gained the power of Thor!"));

        }

        return pStack;
    }

    @Nullable
    public MenuProvider getMenuProvider(BlockState pState, Level pLevel, BlockPos pPos) {
        BlockEntity blockentity = pLevel.getBlockEntity(pPos);
        if (blockentity instanceof EnchantmentTableBlockEntity) {
            Component component = ((Nameable) blockentity).getDisplayName();
            return new SimpleMenuProvider((p_207906_, p_207907_, p_207908_) -> new EnchantmentMenu(p_207906_, p_207907_, ContainerLevelAccess.create(pLevel, pPos)), component);
        } else {
            return null;
        }
    }

    @Override
    public int getUseDuration(ItemStack pStack) {
        return USE_DURATION;
    }

    public UseAnim getUseAnimation(ItemStack pStack) {
        return UseAnim.BOW;
    }
}
