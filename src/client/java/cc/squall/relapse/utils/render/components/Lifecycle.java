package cc.squall.relapse.utils.render.components;

import cc.squall.relapse.utils.datatypes.tuples.immutable.ImmutablePair;
import net.minecraft.client.gui.GuiGraphicsExtractor;

public final class Lifecycle {

    public ImmutablePair<Float, Float> draw(GuiGraphicsExtractor graphics, float partialTick,
                                            ImmutablePair<Float, Float> position, Component... components) {
        float width = 0;
        float height = 0;
        float y = position.getSecond();
        for (Component c : components) {
            width = Math.max(width, c.width());
            height += c.height();
            c.draw(graphics, position.getFirst(), y, partialTick);
            y += c.height();
        }
        return ImmutablePair.of(width, height);
    }
}
