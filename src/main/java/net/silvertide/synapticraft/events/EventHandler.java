package net.silvertide.synapticraft.events;

import harmonised.pmmo.api.APIUtils;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.silvertide.synapticraft.Synapticraft;
import net.silvertide.synapticraft.config.Config;
import net.silvertide.synapticraft.utils.LivingEntityUtil;

import java.util.HashMap;
import java.util.Map;

@Mod.EventBusSubscriber(modid= Synapticraft.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class EventHandler {
    @SubscribeEvent
    public static void entityHurtEvent(LivingHurtEvent hurtEvent) {
        LivingEntity entity = hurtEvent.getEntity();
        if(entity instanceof ServerPlayer serverPlayer) {
            Synapticraft.MOD_LOGGER.info(serverPlayer.getName().getString() + " hurt by " + hurtEvent.getSource().type().msgId());
            float damageMitigated = LivingEntityUtil.getDamageMitigated(hurtEvent);
            if(damageMitigated > 0.0f) {
                String sourceName = hurtEvent.getSource().type().msgId();
                Map<String, String> skillMap = new HashMap<>();
                skillMap.put("mob", "defense");
                skillMap.put("arrow", "defense");
                skillMap.put("fall", "acrobatics");
                skillMap.put("magic", "warding");
                skillMap.put("indirectMagic", "warding");

                if(skillMap.get(sourceName) != null) {
                    String skillName = skillMap.get(sourceName);
                    float multiplier = skillName.equals("warding") ? Config.MAGICAL_DAMAGE_MITIGATED_REWARD.get() : Config.PHYSICAL_DAMAGE_MITIGATED_REWARD.get();
                    float xpAward = damageMitigated * multiplier;
                    APIUtils.addXp(skillName, serverPlayer, (long) xpAward);
                }
            }
        }
    }
}
