package net.caffeinemc.mods.sodium.client.gui.widgets;

import net.caffeinemc.mods.sodium.client.util.Dim2i;
import net.minecraft.client.gui.ComponentPath;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Renderable;
import net.minecraft.client.gui.navigation.CommonInputs;
import net.minecraft.client.gui.navigation.FocusNavigationEvent;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public class FlatButtonWidget extends AbstractWidget implements Renderable {
    private final Runnable action;
    private final boolean drawBackground;
    private final boolean leftAlign;

    private @NotNull Style style = Style.defaults();

    private boolean selected;
    private boolean enabled = true;
    private boolean visible = true;

    private Component label;

    public FlatButtonWidget(Dim2i dim, Component label, Runnable action, boolean drawBackground, boolean leftAlign) {
        super(dim);
        this.label = label;
        this.action = action;
        this.drawBackground = drawBackground;
        this.leftAlign = leftAlign;
    }

    @Override
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float delta) {
        if (!this.visible) {
            return;
        }

        this.hovered = this.isMouseOver(mouseX, mouseY);

        int backgroundColor = this.enabled ? (this.hovered ? this.style.bgHovered : this.style.bgDefault) : this.style.bgDisabled;
        int textColor = this.enabled ? this.style.textDefault : this.style.textDisabled;

        int strWidth = this.font.width(this.label);

        if (drawBackground) {
            this.drawRect(graphics, this.getX(), this.getY(), this.getLimitX(), this.getLimitY(), backgroundColor);
        }

        this.drawString(graphics, this.label, this.leftAlign ? this.getX() + 5 : (this.getCenterX() - (strWidth / 2)), this.getCenterY() - 4, textColor);

        if (this.enabled && this.selected) {
            this.drawRect(graphics, this.getX(), this.getLimitY() - 1, this.getLimitX(), this.getLimitY(), 0xFF94E4D3);
        }

        if (!drawBackground) {
            this.drawBorder(graphics, this.getX(), this.getY(), this.getLimitX(), this.getLimitY(), 0x8000FFEE);

        }
    }

    public void setStyle(@NotNull Style style) {
        Objects.requireNonNull(style);

        this.style = style;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (!this.enabled || !this.visible) {
            return false;
        }

        if (button == 0 && this.isMouseOver(mouseX, mouseY)) {
            doAction();

            return true;
        }

        return false;
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if (!this.isFocused())
            return false;

        if (CommonInputs.selected(keyCode)) {
            doAction();
            return true;
        }

        return false;
    }

    private void doAction() {
        this.action.run();
        this.playClickSound();
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public void setLabel(Component text) {
        this.label = text;
    }

    public Component getLabel() {
        return this.label;
    }

    @Override
    public @Nullable ComponentPath nextFocusPath(FocusNavigationEvent event) {
        if (!this.enabled || !this.visible)
            return null;
        return super.nextFocusPath(event);
    }

    public static class Style {
        public int bgHovered, bgDefault, bgDisabled;
        public int textDefault, textDisabled;

        public static Style defaults() {
            var style = new Style();
            style.bgHovered = 0xE0000000;
            style.bgDefault = 0x90000000;
            style.bgDisabled = 0x60000000;
            style.textDefault = 0xFFFFFFFF;
            style.textDisabled = 0x90FFFFFF;

            return style;
        }
    }
}
