package net.silvertide.synapticraft.core;

import net.minecraft.world.entity.player.Player;

public class ExperienceRequirement implements UseRequirement{
    @Override
    public boolean meetsRequirement(Player player) {
        return false;
    }
}
