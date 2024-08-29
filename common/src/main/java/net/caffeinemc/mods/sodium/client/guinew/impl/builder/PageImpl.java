package net.caffeinemc.mods.sodium.client.guinew.impl.builder;

import net.caffeinemc.mods.sodium.api.gui.builder.Group;
import net.caffeinemc.mods.sodium.api.gui.builder.Page;
import net.minecraft.network.chat.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class PageImpl implements Page {
    private final Component name;
    private final List<Group> groups;

    public PageImpl(Component name) {
        this.name = name;
        this.groups = new ArrayList<>();
    }

    @Override
    public Component getName() {
        return this.name;
    }

    @Override
    public Collection<Group> getGroups() {
        return this.groups;
    }

    @Override
    public Page addGroup(Group group) {
        this.groups.add(group);
        return this;
    }
}
