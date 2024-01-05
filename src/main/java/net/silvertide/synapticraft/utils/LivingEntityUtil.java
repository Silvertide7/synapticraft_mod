package net.silvertide.synapticraft.utils;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stats;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.damagesource.CombatRules;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

public class LivingEntityUtil {

    public static float getDamageMitigated(LivingHurtEvent hurtEvent) {
        ServerPlayer serverPlayer = (ServerPlayer) hurtEvent.getEntity();
        DamageSource damageSource = hurtEvent.getSource();
        float damageToTake = hurtEvent.getAmount();
        if (damageToTake <= 0) return 0.0f;

        damageToTake = LivingEntityUtil.getDamageAfterArmorAbsorb(serverPlayer, damageSource, damageToTake);
        damageToTake = LivingEntityUtil.getDamageAfterMagicAbsorb(serverPlayer, damageSource, damageToTake);
        damageToTake = Math.max(damageToTake - serverPlayer.getAbsorptionAmount(), 0.0F);
        return hurtEvent.getAmount() - damageToTake;
    }
    private static float getDamageAfterMagicAbsorb(ServerPlayer entity, DamageSource pDamageSource, float pDamageAmount) {
        if (pDamageSource.is(DamageTypeTags.BYPASSES_EFFECTS)) {
            return pDamageAmount;
        } else {
            if (entity.hasEffect(MobEffects.DAMAGE_RESISTANCE) && !pDamageSource.is(DamageTypeTags.BYPASSES_RESISTANCE)) {
                int i = (entity.getEffect(MobEffects.DAMAGE_RESISTANCE).getAmplifier() + 1) * 5;
                int j = 25 - i;
                float f = pDamageAmount * (float)j;
                float f1 = pDamageAmount;
                pDamageAmount = Math.max(f / 25.0F, 0.0F);
                float f2 = f1 - pDamageAmount;
                if (f2 > 0.0F && f2 < 3.4028235E37F) {
                    ((ServerPlayer)entity).awardStat(Stats.CUSTOM.get(Stats.DAMAGE_RESISTED), Math.round(f2 * 10.0F));
                }
            }

            if (pDamageAmount <= 0.0F) {
                return 0.0F;
            } else if (pDamageSource.is(DamageTypeTags.BYPASSES_ENCHANTMENTS)) {
                return pDamageAmount;
            } else {
                int k = EnchantmentHelper.getDamageProtection(entity.getArmorSlots(), pDamageSource);
                if (k > 0) {
                    pDamageAmount = CombatRules.getDamageAfterMagicAbsorb(pDamageAmount, (float)k);
                }

                return pDamageAmount;
            }
        }
    }

    private static float getDamageAfterArmorAbsorb(ServerPlayer player, DamageSource pDamageSource, float pDamageAmount) {
        if (!pDamageSource.is(DamageTypeTags.BYPASSES_ARMOR)) {
            pDamageAmount = CombatRules.getDamageAfterAbsorb(pDamageAmount, (float)player.getArmorValue(), (float)player.getAttributeValue(Attributes.ARMOR_TOUGHNESS));
        }

        return pDamageAmount;
    }
}
