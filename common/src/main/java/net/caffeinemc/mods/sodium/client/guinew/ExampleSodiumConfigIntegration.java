package net.caffeinemc.mods.sodium.client.guinew;

import net.caffeinemc.mods.sodium.api.gui.SodiumConfigIntegration;
import net.caffeinemc.mods.sodium.api.gui.PerformanceImpact;
import net.caffeinemc.mods.sodium.api.gui.builder.ModEntryBuilder;
import net.minecraft.network.chat.Component;

public class ExampleSodiumConfigIntegration implements SodiumConfigIntegration {
    @Override
    public void create(ModEntryBuilder builder) {
        builder.addPage(
                builder.createPage(Component.literal("First Page"))
                        .addGroup(builder.createGroup()
                                .addEntry(builder.createDefaultEntry(
                                        Component.literal("Option 1"),
                                        "placeholder"
                                ))
                                .addEntry(builder.createDefaultEntry(
                                        Component.literal("Option after 1"),
                                        "value"
                                ))
                                .addEntry(builder.createDefaultEntry(
                                        Component.literal("Option 3"),
                                        "value"
                                ))
                        )
                        .addGroup(builder.createGroup()
                                .addEntry(builder.createDefaultEntry(
                                        Component.literal("Option 4"),
                                        "placeholder",
                                        builder.createDescription(
                                                Component.literal("This is a very cool option that will triple your FPS!"),
                                                PerformanceImpact.HIGH
                                        )
                                ))
                        )
        ).addPage(
                builder.createPage(Component.literal("Second Page"))
                        .addGroup(builder.createGroup()
                                .addEntry(builder.createDefaultEntry(
                                        Component.literal("Option 5"),
                                        "placeholder",
                                        builder.createDescription(
                                                Component.literal("This is a cosmetic option that doesn't have a performance impact.")
                                        )
                                ))
                        )
                        .addGroup(builder.createGroup()
                                .addEntry(builder.createDefaultEntry(
                                        Component.literal("Option 6"),
                                        "placeholder"
                                ))
                        )
        );
    }
}
