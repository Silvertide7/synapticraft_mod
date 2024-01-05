package net.silvertide.synapticraft.tabs;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.silvertide.synapticraft.Synapticraft;

public class ModCreativeModeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, Synapticraft.MOD_ID);

//    public static final RegistryObject<CreativeModeTab> SYNAPTICRAFT_TAB = CREATIVE_MODE_TABS.register("synapticraft_tab",
//            () -> CreativeModeTab.builder().icon(() -> new ItemStack(ModItems.WAYFINDER_WAND.get()))
//                    .title(Component.translatable("creative_tab.synapticraft"))
//                    .displayItems((displayParameters, output) -> {
//                        output.accept(ModItems.WAYFINDER_WAND.get());
//                    }).build());
    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TABS.register(eventBus);
    }
}
