package net.caffeinemc.mods.sodium.client.guinew.impl.builder;

import net.caffeinemc.mods.sodium.api.gui.builder.Description;
import net.caffeinemc.mods.sodium.api.gui.PerformanceImpact;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.Nullable;

public class DescriptionImpl implements Description {
    private final Component text;
    @Nullable
    private final PerformanceImpact impact;

    public DescriptionImpl(Component text, @Nullable PerformanceImpact impact) {
        this.text = text;
        this.impact = impact;
    }
}
