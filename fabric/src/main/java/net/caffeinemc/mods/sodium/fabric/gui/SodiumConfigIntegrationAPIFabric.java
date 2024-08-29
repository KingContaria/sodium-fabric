package net.caffeinemc.mods.sodium.fabric.gui;

import net.caffeinemc.mods.sodium.api.gui.SodiumConfigIntegration;
import net.caffeinemc.mods.sodium.client.SodiumClientMod;
import net.caffeinemc.mods.sodium.client.guinew.ExampleSodiumConfigIntegration;
import net.caffeinemc.mods.sodium.client.guinew.ModInfo;
import net.caffeinemc.mods.sodium.client.guinew.SodiumConfigIntegrationAPI;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.ModContainer;
import net.fabricmc.loader.api.metadata.CustomValue;
import net.fabricmc.loader.api.metadata.ModMetadata;
import org.jetbrains.annotations.ApiStatus;

import java.lang.reflect.Constructor;

@ApiStatus.Internal
public class SodiumConfigIntegrationAPIFabric {
    public static void initConfigIntegrations() {
        for (ModContainer mod : FabricLoader.getInstance().getAllMods()) {
            ModMetadata metadata = mod.getMetadata();
            if (!metadata.containsCustomValue(SodiumConfigIntegrationAPI.JSON_KEY_SODIUM_CONFIG_INTEGRATIONS)) {
                continue;
            }
            CustomValue customValue = metadata.getCustomValue(SodiumConfigIntegrationAPI.JSON_KEY_SODIUM_CONFIG_INTEGRATIONS);
            if (customValue.getType() != CustomValue.CvType.STRING) {
                SodiumClientMod.logger().warn("Mod '{}' provided a custom config integration but the value is of the wrong type: {}", metadata.getId(), customValue.getType());
                continue;
            }
            Class<?> configIntegrationClass;
            try {
                configIntegrationClass = Class.forName(customValue.getAsString());
            } catch (ClassNotFoundException e) {
                System.out.println(ExampleSodiumConfigIntegration.class);
                SodiumClientMod.logger().warn("Mod '{}' provided a custom config integration but the class is missing: {}", metadata.getId(), customValue.getAsString());
                continue;
            }
            if (!SodiumConfigIntegration.class.isAssignableFrom(configIntegrationClass)) {
                SodiumClientMod.logger().warn("Mod '{}' provided a custom config integration but the class is of the wrong type: {}", metadata.getId(), configIntegrationClass);
                continue;
            }
            SodiumConfigIntegration configIntegration;
            try {
                configIntegration = constructConfigIntegration(configIntegrationClass);
            } catch (ReflectiveOperationException e) {
                SodiumClientMod.logger().warn("Mod '{}' provided a custom config integration but the class could not be constructed: {}", metadata.getId(), configIntegrationClass);
                continue;
            }
            SodiumConfigIntegrationAPI.addConfigIntegration(new ModInfo(metadata.getId(), metadata.getName(), metadata.getVersion().getFriendlyString()), configIntegration);
        }
    }

    private static SodiumConfigIntegration constructConfigIntegration(Class<?> aClass) throws ReflectiveOperationException {
        Constructor<?> constructor = aClass.getDeclaredConstructor();
        constructor.setAccessible(true);
        return (SodiumConfigIntegration) constructor.newInstance();
    }
}
