package net.caffeinemc.mods.sodium.client.guinew;

import net.caffeinemc.mods.sodium.api.gui.SodiumConfigIntegration;
import net.caffeinemc.mods.sodium.client.guinew.impl.ModEntry;
import net.caffeinemc.mods.sodium.client.guinew.impl.builder.ModEntryBuilderImpl;
import net.minecraft.client.gui.screens.Screen;
import org.jetbrains.annotations.ApiStatus;

import java.util.*;

@ApiStatus.Internal
public class SodiumConfigIntegrationAPI {
    public static final String JSON_KEY_SODIUM_CONFIG_INTEGRATIONS = "sodium:config_integrations";
    private static final Map<ModInfo, SodiumConfigIntegration> CONFIG_INTEGRATIONS = new LinkedHashMap<>();

//    static {
//        CONFIG_INTEGRATIONS.put(new ModInfo("sodium", "Sodium", "0.6.0"), new ExampleSodiumConfigIntegration());
//    }

    public static void addConfigIntegration(ModInfo modInfo, SodiumConfigIntegration configIntegration) {
        CONFIG_INTEGRATIONS.put(modInfo, configIntegration);
    }

    public static SodiumVideoSettingsScreen createConfigScreen(Screen parent) {
        List<ModEntry> modEntries = new ArrayList<>();
        for (Map.Entry<ModInfo, SodiumConfigIntegration> integration : CONFIG_INTEGRATIONS.entrySet()) {
            ModEntryBuilderImpl builder = new ModEntryBuilderImpl(integration.getKey());
            integration.getValue().create(builder);
            modEntries.add(builder.build());
        }
        return new SodiumVideoSettingsScreen(parent, modEntries);
    }
}
