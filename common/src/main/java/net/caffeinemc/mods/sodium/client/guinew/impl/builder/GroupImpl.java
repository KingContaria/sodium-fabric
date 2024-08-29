package net.caffeinemc.mods.sodium.client.guinew.impl.builder;

import net.caffeinemc.mods.sodium.api.gui.builder.Entry;
import net.caffeinemc.mods.sodium.api.gui.builder.Group;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class GroupImpl implements Group {
    private final List<Entry> entries;

    public GroupImpl() {
        this.entries = new ArrayList<>();
    }

    @Override
    public Collection<Entry> getEntries() {
        return this.entries;
    }

    @Override
    public Group addEntry(Entry entry) {
        this.entries.add(entry);
        return this;
    }
}
