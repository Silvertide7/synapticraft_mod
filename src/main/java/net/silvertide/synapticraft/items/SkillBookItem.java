package net.silvertide.synapticraft.items;

import harmonised.pmmo.api.APIUtils;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.*;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.silvertide.synapticraft.core.SkillBookEffect;
import net.silvertide.synapticraft.core.UseRequirement;

import java.util.ArrayList;
import java.util.List;

public class SkillBookItem extends Item {
    private static final int USE_DURATION = 32;
    private String skill;
    private SkillBookEffect effect;
    private long effectValue;
    private List<UseRequirement> requirements;
    private String description;

    public SkillBookItem(Properties pProperties) {
        super(new Item.Properties().stacksTo(1).fireResistant().rarity(Rarity.RARE));
        this.skill = pProperties.skill;
        this.effect = pProperties.effect;
        this.effectValue = pProperties.effectValue;
        this.requirements = pProperties.requirements;
    }
    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        ItemStack itemstack = pPlayer.getItemInHand(pUsedHand);
        if(playerCanUseSkillBook(pPlayer)){
            pPlayer.startUsingItem(pUsedHand);
            return InteractionResultHolder.consume(itemstack);
        } else {
            if(pLevel.isClientSide) {
                pPlayer.sendSystemMessage(Component.literal(getRequirementDescription(pPlayer)));
            }
            return InteractionResultHolder.fail(itemstack);
        }
    }

    public ItemStack finishUsingItem(ItemStack pStack, Level pLevel, LivingEntity pEntityLiving) {
        Player player = pEntityLiving instanceof Player ? (Player)pEntityLiving : null;
        boolean clientSide = pLevel.isClientSide;

        if (player != null) {
            if (!player.getAbilities().instabuild) {
                pStack.shrink(1);
            }
            useSkillBook(player);

            if(clientSide) player.sendSystemMessage(Component.literal(getEffectDescription(player)));
        }

        if (!clientSide) {
            ServerLevel serverlevel = (ServerLevel)pLevel;

//            player.openMenu(getMenuProvider(pLevel, pPos));

            for(int i = 0; i < 5; ++i) {
                serverlevel.sendParticles(ParticleTypes.SONIC_BOOM, player.getX() + pLevel.random.nextDouble(), (double)(player.getY() + 1), (double)player.getZ() + pLevel.random.nextDouble(), 1, 0.0D, 0.0D, 0.0D, 1.0D);
            }
        }

        return pStack;
    }

    private boolean playerCanUseSkillBook(Player player) {
        return true;
    }

    private void useSkillBook(Player player) {

        switch (this.effect) {
            case ADD_LEVEL:
                int currentLevel = APIUtils.getLevel(this.skill, player);
                APIUtils.setLevel(this.skill, player, (int) this.effectValue + currentLevel);
                break;
            case SET_LEVEL:
                APIUtils.setLevel(this.skill, player, (int) this.effectValue);
                break;
            case ADD_XP:
                APIUtils.addXp(this.skill, player, this.effectValue);
        }
    }

    private String getEffectDescription(Player player) {
        switch (this.effect) {
            case ADD_LEVEL:
                int currentLevel = APIUtils.getLevel(this.skill, player);
                return "Your " + this.skill + "skill is now level " + (int) this.effectValue + currentLevel;
            case SET_LEVEL:
                return "Your " + this.skill + "skill is now level " + (int) this.effectValue;
            case ADD_XP:
                return "You have gained  " + this.effectValue + " " + this.skill + " experience.";
            default:
                return "Nothing seemed to happen.";
        }
    }
    private String getRequirementDescription(Player player) {
        switch (this.effect) {
            case ADD_LEVEL:
                int currentLevel = APIUtils.getLevel(this.skill, player);
                return "Your " + this.skill + "skill is now level " + (int) this.effectValue + currentLevel;
            case SET_LEVEL:
                return "Your " + this.skill + "skill is now level " + (int) this.effectValue;
            case ADD_XP:
                return "You have gained  " + this.effectValue + " " + this.skill + " experience.";
            default:
                return "Nothing seemed to happen.";
        }
    }


//    @Nullable
//    public MenuProvider getMenuProvider(BlockState pState, Level pLevel, BlockPos pPos) {
//        BlockEntity blockentity = pLevel.getBlockEntity(pPos);
//        if (blockentity instanceof EnchantmentTableBlockEntity) {
//            Component component = ((Nameable) blockentity).getDisplayName();
//            return new SimpleMenuProvider((p_207906_, p_207907_, p_207908_) -> new EnchantmentMenu(p_207906_, p_207907_, ContainerLevelAccess.create(pLevel, pPos)), component);
//        } else {
//            return null;
//        }
//    }

    @Override
    public int getUseDuration(ItemStack pStack) {
        return USE_DURATION;
    }

    public UseAnim getUseAnimation(ItemStack pStack) {
        return UseAnim.BOW;
    }
    @Override
    public void appendHoverText(ItemStack pStack, @org.jetbrains.annotations.Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        if(Screen.hasShiftDown()){
            pTooltipComponents.add(Component.literal("tooltip.synapticraft.skill_book.tooltip.shift"));
        } else {
            pTooltipComponents.add(Component.literal("tooltip.synapticraft.skill_book.tooltip"));
        }
        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
    }


    public static class Properties {
        String skill;
        SkillBookEffect effect = SkillBookEffect.ADD_XP;
        long effectValue = 0;
        List<UseRequirement> requirements = new ArrayList<>();
        String description;

        public SkillBookItem.Properties skill(String skill) {
            this.skill = skill;
            return this;
        }

        public SkillBookItem.Properties giveXP(int xp) {
            this.effect = SkillBookEffect.ADD_XP;
            this.effectValue = xp;
            return this;
        }

        public SkillBookItem.Properties addLevel(int level) {
            this.effect = SkillBookEffect.ADD_LEVEL;
            this.effectValue = level;
            return this;
        }

        public SkillBookItem.Properties setLevel(int level) {
            this.effect = SkillBookEffect.SET_LEVEL;
            this.effectValue = level;
            return this;
        }

        public SkillBookItem.Properties addRequirement(UseRequirement requirement){
            this.requirements.add(requirement);
            return this;
        }

        public SkillBookItem.Properties addDescription(String description){
            this.description = description;
            return this;
        }



    }
}
