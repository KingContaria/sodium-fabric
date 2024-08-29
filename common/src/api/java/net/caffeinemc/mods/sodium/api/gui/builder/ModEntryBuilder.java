package net.caffeinemc.mods.sodium.api.gui.builder;

import net.caffeinemc.mods.sodium.api.gui.PerformanceImpact;
import net.minecraft.network.chat.Component;

public interface ModEntryBuilder {
    Page createPage(Component name);
    Page createPage(String name);

    Group createGroup();

    Entry createDefaultEntry(Component name, String value);
    Entry createDefaultEntry(Component name, String value, Description description);

    Description createDescription(Component description, PerformanceImpact impact);
    Description createDescription(Component description);

    ModEntryBuilder addPage(Page page);
}
