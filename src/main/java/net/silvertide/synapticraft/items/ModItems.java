package net.silvertide.synapticraft.items;

import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.silvertide.synapticraft.Synapticraft;
import net.silvertide.synapticraft.items.custom.SkillBookItem;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Synapticraft.MOD_ID);
    public static final RegistryObject<Item> SKILL_BOOK = ITEMS.register("skill_book", () -> new SkillBookItem(new Item.Properties().stacksTo(1), 10));

    public static void register(IEventBus eventBus){
        ITEMS.register(eventBus);
    }

}
