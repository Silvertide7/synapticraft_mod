package net.silvertide.synapticraft.core;

import net.minecraft.world.entity.player.Player;

public interface UseRequirement {
    abstract boolean meetsRequirement(Player player);

}
