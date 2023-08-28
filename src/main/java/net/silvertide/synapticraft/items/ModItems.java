package net.silvertide.synapticraft.items;

import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.silvertide.synapticraft.Synapticraft;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Synapticraft.MOD_ID);
    public static final RegistryObject<Item> SKILL_BOOK = ITEMS.register("skill_book", () -> new SkillBookItem(new SkillBookItem.Properties().skill("rogue").setLevel(1)));

    public static void register(IEventBus eventBus){
        ITEMS.register(eventBus);
    }

}
