package cc.squall.relapse.utils.render.components;

import cc.squall.relapse.utils.render.ColorUtil;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphicsExtractor;

import java.awt.*;

public class TextComponent extends Component{

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

    @Override
    public void draw(GuiGraphicsExtractor graphics, float partialTick) {
        graphics.text(mcFont, text, x, y, color);
    }
}
