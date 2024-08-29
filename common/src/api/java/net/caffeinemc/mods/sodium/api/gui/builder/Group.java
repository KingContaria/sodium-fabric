package net.caffeinemc.mods.sodium.api.gui.builder;

import java.util.Collection;

public interface Group {
    Group addEntry(Entry entry);

    Collection<Entry> getEntries();
}
