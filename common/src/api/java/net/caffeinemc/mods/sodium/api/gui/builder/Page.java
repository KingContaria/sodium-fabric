package net.caffeinemc.mods.sodium.api.gui.builder;

import net.minecraft.network.chat.Component;

import java.util.Collection;

public interface Page {
    Page addGroup(Group group);

    Component getName();
    Collection<Group> getGroups();
}
