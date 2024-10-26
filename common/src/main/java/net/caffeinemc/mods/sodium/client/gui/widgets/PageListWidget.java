package net.caffeinemc.mods.sodium.client.gui.widgets;

import net.caffeinemc.mods.sodium.client.config.ConfigManager;
import net.caffeinemc.mods.sodium.client.config.structure.OptionPage;
import net.caffeinemc.mods.sodium.client.gui.VideoSettingsScreen;
import net.caffeinemc.mods.sodium.client.util.Dim2i;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.NotNull;

public class PageListWidget extends AbstractParentWidget {
    private final VideoSettingsScreen parent;
    private ScrollbarWidget scrollbar;
    private FlatButtonWidget search;

    public PageListWidget(VideoSettingsScreen parent, Dim2i dim) {
        super(dim);
        this.parent = parent;
        this.rebuild();
    }

    @Override
    public void render(@NotNull GuiGraphics graphics, int mouseX, int mouseY, float delta) {
        graphics.fillGradient(this.getX(), this.getY(), this.getWidth(), this.getHeight(), 0x40000000, 0x90000000);
        graphics.enableScissor(this.getX(), this.getY(), this.getLimitX(), this.getLimitY() - 30);
        super.render(graphics, mouseX, mouseY, delta);
        graphics.disableScissor();
        this.search.render(graphics, mouseX, mouseY, delta);
    }

    public void rebuild() {
        int x = this.getX();
        int y = this.getY();
        int width = this.getWidth();
        int height = this.getHeight();

        this.clearChildren();
        this.scrollbar = this.addRenderableChild(new ScrollbarWidget(new Dim2i(x + width - 5, y, 5, height - 30)));
        this.search = this.addChild(new FlatButtonWidget(new Dim2i(x, y + height - 30, width, 20), Component.literal("Search...").withStyle(ChatFormatting.ITALIC, ChatFormatting.GRAY), () -> {
            // TODO: implement search
        }, true, true));

        int listHeight = 5;
        int entryHeight = this.font.lineHeight * 2;
        for (var modConfig : ConfigManager.CONFIG.getModConfigs()) {
            CenteredFlatWidget header = new EntryWidget(new Dim2i(x, y + listHeight, width, entryHeight), Component.literal(modConfig.name()), () -> {
            }, false);

            listHeight += entryHeight;

            this.addRenderableChild(header);

            for (OptionPage page : modConfig.pages()) {
                CenteredFlatWidget button = new EntryWidget(new Dim2i(x, y + listHeight, width, entryHeight), page.name(), () -> this.parent.setPage(page), true);
                button.setSelected(this.parent.getPage() == page);

                listHeight += entryHeight;

                this.addRenderableChild(button);
            }
        }

        this.scrollbar.setScrollbarContext(height - 30, listHeight + 5);
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double horizontalAmount, double verticalAmount) {
        this.scrollbar.scroll((int) (-verticalAmount * 10));
        return true;
    }

    public class EntryWidget extends CenteredFlatWidget {
        public EntryWidget(Dim2i dim, Component label, Runnable action, boolean isSelectable) {
            super(dim, label, action, isSelectable);
        }

        @Override
        public int getY() {
            return super.getY() - PageListWidget.this.scrollbar.getScrollAmount();
        }
    }
}
