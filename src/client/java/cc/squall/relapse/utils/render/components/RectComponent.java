package cc.squall.relapse.utils.render.components;

import net.minecraft.client.gui.GuiGraphicsExtractor;

public class RectComponent extends Component {

    private final float width;
    private final float height;
    private final int color;

    public RectComponent(float width, float height, int color) {
        this.width = width;
        this.height = height;
        this.color = color;
    }

    @Override
    public void draw(GuiGraphicsExtractor graphics, float x, float y, float partialTick) {
        graphics.fill((int) x, (int) y, (int) (x + width), (int) (y + height), color);
    }

    @Override
    public float width() {
        return width;
    }

    @Override
    public float height() {
        return height;
    }
}
