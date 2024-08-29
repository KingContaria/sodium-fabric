package net.caffeinemc.mods.sodium.api.gui.builder;

import net.caffeinemc.mods.sodium.api.gui.render.DrawUtil;
import net.minecraft.client.gui.GuiGraphics;

public interface Entry {
    void render(DrawUtil drawUtil, GuiGraphics graphics, int x, int y, int width, int height);
    int getHeight();
}
