package net.silvertide.synapticraft.config;

import net.minecraftforge.common.ForgeConfigSpec;

public class Config {
    private static final ForgeConfigSpec.Builder BUILDER;
    public static final ForgeConfigSpec COMMON_CONFIG;
    public static final ForgeConfigSpec.ConfigValue<Integer> PHYSICAL_DAMAGE_MITIGATED_REWARD;

    static {
        BUILDER = new ForgeConfigSpec.Builder();

        BUILDER.push("Damage mitigation XP Configs");
        PHYSICAL_DAMAGE_MITIGATED_REWARD = BUILDER.defineInRange("Mitigated physical damage Xp Reward", 20, 0, Integer.MAX_VALUE);
        BUILDER.pop();
        COMMON_CONFIG = BUILDER.build();
    }
}
