package cc.squall.relapse.features.impl.render;

import cc.squall.relapse.Client;
import cc.squall.relapse.features.Feature;
import cc.squall.relapse.utils.render.animation.Animation;
import cc.squall.relapse.utils.render.animation.Direction;
import cc.squall.relapse.utils.render.animation.animations.EaseOutCirc;
import cc.squall.relapse.utils.render.components.Component;
import cc.squall.relapse.utils.render.components.TextComponent;
import net.minecraft.client.gui.GuiGraphicsExtractor;

import java.util.ArrayList;

public class HUD extends Feature {
    private static final String name = "relapse b0";
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

        ArrayList<Component> components = new ArrayList<>();
        components.add(new TextComponent(mc.font, name, x, 10, 0xFFFFFFFF));
        Client.getCompMan().drawComponents(graphics, partialTick, components);
    }

    @Override
    public void onEnable() {
        watermarkAnim.setDirection(Direction.FORWARDS);
        watermarkAnim.reset();
    }
}
