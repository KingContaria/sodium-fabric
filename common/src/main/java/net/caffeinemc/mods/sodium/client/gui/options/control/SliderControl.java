package net.caffeinemc.mods.sodium.client.gui.options.control;

import com.mojang.blaze3d.platform.InputConstants;
import net.caffeinemc.mods.sodium.api.config.option.ControlValueFormatter;
import net.caffeinemc.mods.sodium.client.config.structure.Option;
import net.caffeinemc.mods.sodium.client.gui.widgets.OptionListWidget;
import net.caffeinemc.mods.sodium.client.util.Dim2i;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Style;
import net.minecraft.util.Mth;
import org.apache.commons.lang3.Validate;

public class SliderControl implements Control<Integer> {
    private final Option<Integer> option;

    private final int min, max, interval;

    private final ControlValueFormatter mode;

    public SliderControl(Option<Integer> option, int min, int max, int interval, ControlValueFormatter mode) {
        Validate.isTrue(max > min, "The maximum value must be greater than the minimum value");
        Validate.isTrue(interval > 0, "The slider interval must be greater than zero");
        Validate.isTrue(((max - min) % interval) == 0, "The maximum value must be divisable by the interval");
        Validate.notNull(mode, "The slider mode must not be null");

        this.option = option;
        this.min = min;
        this.max = max;
        this.interval = interval;
        this.mode = mode;
    }

    @Override
    public ControlElement<Integer> createElement(OptionListWidget list, Dim2i dim) {
        return new Button(list, this.option, dim, this.min, this.max, this.interval, this.mode);
    }

    @Override
    public Option<Integer> getOption() {
        return this.option;
    }

    @Override
    public int getMaxWidth() {
        return 170;
    }

    private static class Button extends ControlElement<Integer> {
        private static final int THUMB_WIDTH = 2, TRACK_HEIGHT = 1;

        private int contentWidth;
        private final ControlValueFormatter formatter;

        private final int min;
        private final int max;
        private final int range;
        private final int interval;

        private double thumbPosition;

        private boolean sliderHeld;

        public Button(OptionListWidget list, Option<Integer> option, Dim2i dim, int min, int max, int interval, ControlValueFormatter formatter) {
            super(list, option, dim);

            this.min = min;
            this.max = max;
            this.range = max - min;
            this.interval = interval;
            this.thumbPosition = this.getThumbPositionForValue(option.getValidatedValue());
            this.formatter = formatter;

            this.sliderHeld = false;
        }

        @Override
        public void render(GuiGraphics graphics, int mouseX, int mouseY, float delta) {
            int sliderX = this.getSliderX();
            int sliderY = this.getSliderY();
            int sliderWidth = this.getSliderWidth();
            int sliderHeight = this.getSliderHeight();

            var value = this.option.getValidatedValue();
            var isEnabled = this.option.isEnabled();

            var label = this.formatter.format(value).copy();

            if (!isEnabled) {
                label.setStyle(Style.EMPTY
                        .withColor(ChatFormatting.GRAY)
                        .withItalic(true));
            }

            int labelWidth = this.font.width(label);

            boolean drawSlider = isEnabled && (this.hovered || this.isFocused());
            if (drawSlider) {
                this.contentWidth = sliderWidth + labelWidth;
            } else {
                this.contentWidth = labelWidth;
            }

            // render the label first and then the slider to prevent the highlight rect from darkening the slider
            super.render(graphics, mouseX, mouseY, delta);

            if (drawSlider) {
                this.thumbPosition = this.getThumbPositionForValue(value);

                double thumbOffset = Mth.clamp((double) (this.getIntValue() - this.min) / this.range * sliderWidth, 0, sliderWidth);

                int thumbX = (int) (sliderX + thumbOffset - THUMB_WIDTH);
                int trackY = (int) (sliderY + (sliderHeight / 2f) - ((double) TRACK_HEIGHT / 2));

                this.drawRect(graphics, thumbX, sliderY, thumbX + (THUMB_WIDTH * 2), sliderY + sliderHeight, 0xFFFFFFFF);
                this.drawRect(graphics, sliderX, trackY, sliderX + sliderWidth, trackY + TRACK_HEIGHT, 0xFFFFFFFF);

                this.drawString(graphics, label, sliderX - labelWidth - 6, sliderY + (sliderHeight / 2) - 4, 0xFFFFFFFF);
            } else {
                this.drawString(graphics, label, sliderX + sliderWidth - labelWidth, sliderY + (sliderHeight / 2) - 4, 0xFFFFFFFF);
            }
        }

        public int getSliderX() {
            return this.getLimitX() - 96;
        }

        public int getSliderY() {
            return this.getCenterY() - 5;
        }

        public int getSliderWidth() {
            return 90;
        }

        public int getSliderHeight() {
            return 10;
        }

        public boolean isMouseOverSlider(double mouseX, double mouseY) {
            return mouseX >= this.getSliderX() && mouseX < this.getSliderX() + this.getSliderWidth() && mouseY >= this.getSliderY() && mouseY < this.getSliderY() + this.getSliderHeight();
        }

        @Override
        public int getContentWidth() {
            return this.contentWidth;
        }

        public int getIntValue() {
            return this.min + (this.interval * (int) Math.round(this.getSnappedThumbPosition() / this.interval));
        }

        public double getSnappedThumbPosition() {
            return this.thumbPosition / (1.0D / this.range);
        }

        public double getThumbPositionForValue(int value) {
            return (value - this.min) * (1.0D / this.range);
        }

        @Override
        public boolean mouseClicked(double mouseX, double mouseY, int button) {
            this.sliderHeld = false;

            if (this.option.isEnabled() && button == 0 && this.isMouseOver(mouseX, mouseY)) {
                if (this.isMouseOverSlider(mouseX, mouseY)) {
                    this.setValueFromMouse(mouseX);
                    this.sliderHeld = true;
                }

                return true;
            }

            return false;
        }

        private void setValueFromMouse(double d) {
            this.setValue((d - (double) this.getSliderX()) / (double) this.getSliderWidth());
        }

        public void setValue(double d) {
            this.thumbPosition = Mth.clamp(d, 0.0D, 1.0D);

            int value = this.getIntValue();

            if (this.option.getValidatedValue() != value) {
                this.option.modifyValue(value);
            }
        }

        @Override
        public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
            if (!isFocused()) return false;

            if (keyCode == InputConstants.KEY_LEFT) {
                this.option.modifyValue(Mth.clamp(this.option.getValidatedValue() - this.interval, this.min, this.max));
                return true;
            } else if (keyCode == InputConstants.KEY_RIGHT) {
                this.option.modifyValue(Mth.clamp(this.option.getValidatedValue() + this.interval, this.min, this.max));
                return true;
            }

            return false;
        }

        @Override
        public boolean mouseDragged(double mouseX, double mouseY, int button, double deltaX, double deltaY) {
            if (this.option.isEnabled() && button == 0) {
                if (this.sliderHeld) {
                    this.setValueFromMouse(mouseX);
                }

                return true;
            }

            return false;
        }
    }

}
