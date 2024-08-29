package net.caffeinemc.mods.sodium.client.guinew.impl;

import net.caffeinemc.mods.sodium.api.gui.builder.Page;

import java.util.List;

public record ModEntry(String id, String name, String version, List<Page> pages) {
}
