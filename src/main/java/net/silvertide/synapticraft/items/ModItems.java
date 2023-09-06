package net.silvertide.synapticraft.items;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.silvertide.synapticraft.Synapticraft;
import net.silvertide.synapticraft.items.custom.WayfinderWand;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Synapticraft.MOD_ID);

    public static final RegistryObject<Item> WAYFINDER_WAND = ITEMS.register("wayfinder_wand", () -> new WayfinderWand(new Item.Properties().stacksTo(1).fireResistant().rarity(Rarity.UNCOMMON)));

    public static void register(IEventBus eventBus){
        ITEMS.register(eventBus);
    }

}
