package net.caffeinemc.mods.sodium.api.gui.render;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;

public interface DrawUtil {
    void renderEntryBackground(GuiGraphics graphics, int x, int y, int width, int height);
    void renderDescriptionBackground(GuiGraphics graphics, int x, int y, int width, int height);

    void renderText(GuiGraphics graphics, Component component, int x, int y);
    void renderString(GuiGraphics graphics, String string, int x, int y);

    String limitWidth(String string, int width);
}
