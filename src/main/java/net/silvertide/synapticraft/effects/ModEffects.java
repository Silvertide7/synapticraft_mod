package net.silvertide.synapticraft.effects;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.silvertide.synapticraft.Synapticraft;

public class ModEffects {

    public static final DeferredRegister<MobEffect> MOB_EFFECTS = DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, Synapticraft.MOD_ID);
    public static final RegistryObject<MobEffect> HUNTER = MOB_EFFECTS.register("hunter", () -> new HunterEffect(MobEffectCategory.BENEFICIAL, 3124687, 16.0));
    public static final RegistryObject<MobEffect> HUNTER_FAR = MOB_EFFECTS.register("hunter_far", () -> new HunterEffect(MobEffectCategory.BENEFICIAL, 3124687, 24.0));
    public static final RegistryObject<MobEffect> HUNTER_FURTHEST = MOB_EFFECTS.register("hunter_furthest", () -> new HunterEffect(MobEffectCategory.BENEFICIAL, 3124687, 32.0));

    public static void register(IEventBus eventBus) {
        MOB_EFFECTS.register(eventBus);
    }

}
