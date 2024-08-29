package net.caffeinemc.mods.sodium.client.guinew.impl.builder.entry;

import net.caffeinemc.mods.sodium.api.gui.builder.Description;
import net.caffeinemc.mods.sodium.api.gui.builder.Entry;
import net.caffeinemc.mods.sodium.api.gui.render.DrawUtil;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.Nullable;

public class DefaultEntryImpl implements Entry {
    private final Component name;
    private final String value;
    private final Description description;

    public DefaultEntryImpl(Component name, String value, @Nullable Description description) {
        this.name = name;
        this.value = value;
        this.description = description;
    }

    @Override
    public int getHeight() {
        return 20;
    }

    @Override
    public void render(DrawUtil drawUtil, GuiGraphics graphics, int x, int y, int width, int height) {
        drawUtil.renderEntryBackground(graphics, x, y, width, height);
        drawUtil.renderString(graphics, drawUtil.limitWidth(this.name.getString(), (width * 2) / 3), x + 5, y + 5);
        drawUtil.renderString(graphics, this.value, x + 5 + ((width * 2) / 3), y + 5);
    }
}
