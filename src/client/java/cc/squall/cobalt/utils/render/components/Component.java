package cc.squall.cobalt.utils.render.components;

import net.minecraft.client.gui.GuiGraphicsExtractor;

public abstract class Component {

    public void draw(GuiGraphicsExtractor graphics, float partialTick) {

    }

    /** Draws with the top-left corner at x, y. Used by {@link Lifecycle}. */
    public void draw(GuiGraphicsExtractor graphics, float x, float y, float partialTick) {

    }

    public float width() {
        return 0;
    }

    public float height() {
        return 0;
    }
}
