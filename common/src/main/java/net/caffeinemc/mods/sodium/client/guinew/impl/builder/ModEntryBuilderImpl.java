package net.caffeinemc.mods.sodium.client.guinew.impl.builder;

import net.caffeinemc.mods.sodium.api.gui.PerformanceImpact;
import net.caffeinemc.mods.sodium.api.gui.builder.*;
import net.caffeinemc.mods.sodium.client.guinew.ModInfo;
import net.caffeinemc.mods.sodium.client.guinew.impl.ModEntry;
import net.caffeinemc.mods.sodium.client.guinew.impl.builder.entry.DefaultEntryImpl;
import net.minecraft.network.chat.Component;

import java.util.ArrayList;
import java.util.List;

public class ModEntryBuilderImpl implements ModEntryBuilder {
    private final ModInfo modInfo;
    private final List<Page> pages;

    public ModEntryBuilderImpl(ModInfo modInfo) {
        this.modInfo = modInfo;
        this.pages = new ArrayList<>();
    }

    public ModEntry build() {
        return new ModEntry(this.modInfo.id(), this.modInfo.name(), this.modInfo.version(), this.pages);
    }

    @Override
    public Page createPage(Component name) {
        return new PageImpl(name);
    }

    @Override
    public Page createPage(String name) {
        return new PageImpl(Component.literal(name));
    }

    @Override
    public Group createGroup() {
        return new GroupImpl();
    }

    @Override
    public Entry createDefaultEntry(Component name, String value) {
        return new DefaultEntryImpl(name, value, null);
    }

    @Override
    public Entry createDefaultEntry(Component name, String value, Description description) {
        return new DefaultEntryImpl(name, value, description);
    }

    @Override
    public Description createDescription(Component description, PerformanceImpact impact) {
        return new DescriptionImpl(description, impact);
    }

    @Override
    public Description createDescription(Component description) {
        return new DescriptionImpl(description, null);
    }

    @Override
    public ModEntryBuilder addPage(Page page) {
        this.pages.add(page);
        return this;
    }
}
