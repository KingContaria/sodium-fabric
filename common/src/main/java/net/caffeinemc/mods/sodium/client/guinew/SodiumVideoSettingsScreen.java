package net.caffeinemc.mods.sodium.client.guinew;

import net.caffeinemc.mods.sodium.api.gui.builder.Entry;
import net.caffeinemc.mods.sodium.api.gui.builder.Group;
import net.caffeinemc.mods.sodium.api.gui.builder.Page;
import net.caffeinemc.mods.sodium.api.gui.render.DrawUtil;
import net.caffeinemc.mods.sodium.client.guinew.impl.ModEntry;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class SodiumVideoSettingsScreen extends Screen implements DrawUtil {
    private final Screen parent;
    private final List<ModEntry> modEntries;

    private int currentModEntry = 0;
    private int currentPage = 0;

    protected SodiumVideoSettingsScreen(Screen parent, List<ModEntry> modEntries) {
        super(Component.literal("Sodium Video Settings"));
        this.parent = parent;
        this.modEntries = modEntries;
    }

    @Override
    public void render(@NotNull GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        super.render(guiGraphics, mouseX, mouseY, partialTick);
        int sideBarWidth = this.width / 5;
        this.renderEntryBackground(guiGraphics, 0, 0, sideBarWidth, this.height);
        int y = 5;
        for (ModEntry modEntry : this.modEntries) {
            this.renderString(guiGraphics, modEntry.name(), 5, y);
            y += 10;
            this.renderString(guiGraphics, modEntry.version(), 5, y);
            y += 18;
            if (this.currentModEntry == this.modEntries.indexOf(modEntry)) {
                for (Page page : modEntry.pages()) {
                    this.renderText(guiGraphics, page.getName(), 10, y);
                    y += 15;
                }
            }
            y += 5;
        }
        y = 5;
        for (Group group : this.modEntries.get(this.currentModEntry).pages().get(this.currentPage).getGroups()) {
            for (Entry entry : group.getEntries()) {
                int height = entry.getHeight();
                entry.render(this, guiGraphics, sideBarWidth + 10, y, this.width / 2, height);
                y += height;
            }
            y += 5;
        }
    }

    @Override
    public void renderEntryBackground(GuiGraphics graphics, int x, int y, int width, int height) {
        graphics.fill(x, y, x + width, y + height, 0x90000000);
    }

    @Override
    public void renderDescriptionBackground(GuiGraphics graphics, int x, int y, int width, int height) {
        graphics.fill(x, y, x + width, y + height, 0xE0000000);
    }

    @Override
    public void renderText(GuiGraphics graphics, Component text, int x, int y) {
        graphics.drawString(this.font, text, x, y, 0xFFFFFFFF);
    }

    @Override
    public void renderString(GuiGraphics graphics, String string, int x, int y) {
        graphics.drawString(this.font, string, x, y, 0xFFFFFFFF);
    }

    @Override
    public String limitWidth(String string, int width) {
        while (this.font.width(string + "...") > width) {
            string = string.substring(0, string.length() - 1);
        }
        return string;
    }
}
