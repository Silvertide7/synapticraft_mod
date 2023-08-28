package net.silvertide.synapticraft.tabs;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import net.silvertide.synapticraft.Synapticraft;
import net.silvertide.synapticraft.items.ModItems;

public class ModCreativeModeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, Synapticraft.MOD_ID);

    public static final RegistryObject<CreativeModeTab> SKILL_BOOK_TAB = CREATIVE_MODE_TABS.register("skill_book_tab",
            () -> CreativeModeTab.builder().icon(() -> new ItemStack(ModItems.SKILL_BOOK.get()))
                    .title(Component.translatable("creative_tab.skill_books"))
                    .displayItems((displayParameters, output) -> {
                        // Items
                        output.accept(ModItems.SKILL_BOOK.get());
                    }).build());
    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TABS.register(eventBus);
    }
}
