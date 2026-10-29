package cc.squall.cobalt.features.impl.render;

import cc.squall.cobalt.features.Feature;
import cc.squall.cobalt.utils.render.ColorUtil;
import cc.squall.cobalt.utils.render.animation.Animation;
import cc.squall.cobalt.utils.render.animation.Direction;
import cc.squall.cobalt.utils.render.animation.animations.EaseOutCirc;
import cc.squall.cobalt.utils.datatypes.tuples.immutable.ImmutablePair;
import cc.squall.cobalt.utils.render.components.Lifecycle;
import cc.squall.cobalt.utils.render.components.TextComponent;
import net.minecraft.client.gui.GuiGraphicsExtractor;

public class HUD extends Feature {
    private static final String name = "c0balt";
    private static final int watermarkX = 8;
    private final Animation watermarkAnim = new EaseOutCirc(400, 0);

    public HUD() {
        super("HUD", "Renders various informative/decorative things on your screen", Category.Render);
    }

    @Override
    public void onRender2d(GuiGraphicsExtractor graphics, float partialTick) {
        int width = mc.font.width(name);
        watermarkAnim.setEndPoint(width + watermarkX);
        int x = (int) (-width + watermarkAnim.getOutput());

        new Lifecycle().draw(graphics, partialTick, ImmutablePair.of((float) x, 10f),
                new TextComponent(mc.font, name, ColorUtil.parseHex("4A6DA8")));
    }

    @Override
    public void onEnable() {
        watermarkAnim.setDirection(Direction.FORWARDS);
        watermarkAnim.reset();
    }
}
