package cc.squall.cobalt.utils.render.components;

import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphicsExtractor;

public class TextComponent extends Component {

    private final String text;
    private final int x;
    private final int y;
    private final Font mcFont;
    private final int color;

    public TextComponent(Font font, String text, int x, int y, int color) {
        this.mcFont = font;
        this.text = text;
        this.x = x;
        this.y = y;
        this.color = color;
    }

    public TextComponent(Font font, String text, int color) {
        this(font, text, 0, 0, color);
    }

    @Override
    public void draw(GuiGraphicsExtractor graphics, float partialTick) {
        draw(graphics, x, y, partialTick);
    }

    @Override
    public void draw(GuiGraphicsExtractor graphics, float x, float y, float partialTick) {
        graphics.text(mcFont, text, (int) x, (int) y, color, true);
    }

    @Override
    public float width() {
        return mcFont.width(text);
    }

    @Override
    public float height() {
        return mcFont.lineHeight;
    }
}
