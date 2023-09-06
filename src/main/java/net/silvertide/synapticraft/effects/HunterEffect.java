package net.silvertide.synapticraft.effects;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;

public class HunterEffect extends MobEffect {
    double distanceScanned;

    protected HunterEffect(MobEffectCategory category, int color, double distanceScanned) {
        super(category, color);
        this.distanceScanned = distanceScanned;
    }

    @Override
    public void applyEffectTick(LivingEntity pLivingEntity, int pAmplifier) {

        if (!pLivingEntity.level().isClientSide()) {
            Level level = pLivingEntity.level();
            if(!level.isClientSide()) {
                level.getNearbyEntities(Mob.class, TargetingConditions.forCombat(), pLivingEntity, AABB.ofSize(pLivingEntity.position(), distanceScanned, distanceScanned, distanceScanned)).forEach(e -> {
                    e.addEffect(new MobEffectInstance(MobEffects.GLOWING, 25));
                });
            }
        }

        super.applyEffectTick(pLivingEntity, pAmplifier);
    }

    @Override
    public boolean isDurationEffectTick(int pDuration, int pAmplifier) {
        int j = 20 >> pAmplifier;
        if (j > 0) {
            return pDuration % j == 0;
        } else {
            return true;
        }
    }
}
