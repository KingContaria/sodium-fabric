package net.caffeinemc.mods.sodium.neoforge.gui;

import net.caffeinemc.mods.sodium.api.gui.SodiumConfigIntegration;
import net.caffeinemc.mods.sodium.client.SodiumClientMod;
import net.caffeinemc.mods.sodium.client.guinew.ModInfo;
import net.caffeinemc.mods.sodium.client.guinew.SodiumConfigIntegrationAPI;
import net.neoforged.fml.ModList;
import net.neoforged.neoforgespi.language.IModInfo;
import org.jetbrains.annotations.ApiStatus;

import java.lang.reflect.Constructor;

@ApiStatus.Internal
public class SodiumConfigIntegrationAPIForge {
    public static void initConfigIntegrations() {
        for (IModInfo mod : ModList.get().getMods()) {
            Object modProperty = mod.getModProperties().get(SodiumConfigIntegrationAPI.JSON_KEY_SODIUM_CONFIG_INTEGRATIONS);
            if (modProperty == null) {
                continue;
            }
            if (!(modProperty instanceof String)) {
                SodiumClientMod.logger().warn("Mod '{}' provided a custom config integration but the value is of the wrong type: {}", mod.getModId(), modProperty.getClass());
                continue;
            }
            Class<?> configIntegrationClass;
            try {
                configIntegrationClass = Class.forName((String) modProperty);
            } catch (ClassNotFoundException e) {
                SodiumClientMod.logger().warn("Mod '{}' provided a custom config integration but the class is missing: {}", mod.getModId(), (String) modProperty);
                continue;
            }
            if (!SodiumConfigIntegration.class.isAssignableFrom(configIntegrationClass)) {
                SodiumClientMod.logger().warn("Mod '{}' provided a custom config integration but the class is of the wrong type: {}", mod.getModId(), configIntegrationClass);
                continue;
            }
            SodiumConfigIntegration configIntegration;
            try {
                configIntegration = constructConfigIntegration(configIntegrationClass);
            } catch (ReflectiveOperationException e) {
                SodiumClientMod.logger().warn("Mod '{}' provided a custom config integration but the class could not be constructed: {}", mod.getModId(), configIntegrationClass);
                continue;
            }
            SodiumConfigIntegrationAPI.addConfigIntegration(new ModInfo(mod.getModId(), mod.getDisplayName(), mod.getModId().equals("sodium") ? "0.6.0" : mod.getVersion().toString()), configIntegration);
        }
    }

    private static SodiumConfigIntegration constructConfigIntegration(Class<?> aClass) throws ReflectiveOperationException {
        Constructor<?> constructor = aClass.getDeclaredConstructor();
        constructor.setAccessible(true);
        return (SodiumConfigIntegration) constructor.newInstance();
    }
}
