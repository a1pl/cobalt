package cc.squall.relapse.features.impl.render;

import cc.squall.relapse.Client;
import cc.squall.relapse.features.Feature;
import cc.squall.relapse.utils.render.components.Component;
import cc.squall.relapse.utils.render.components.ComponentManager;
import cc.squall.relapse.utils.render.components.TextComponent;
import cc.squall.relapse.utils.time.TimerUtil;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphicsExtractor;

import java.util.ArrayList;

public class HUD extends Feature {
    public HUD() {
        super("HUD", "Renders various informative/decorative things on your screen", Category.Render);
    }
    enum watermarkAnimationState {
        still,
        enable
    }
    private final TimerUtil watermarkAnimStep = new TimerUtil();
    private watermarkAnimationState toAnim = watermarkAnimationState.still;
    int watermarkX;
    @Override
    public void onRender2d(GuiGraphicsExtractor graphics, float partialTick) {
        TextComponent Watermark;
        // so idea shuts up even though the if loop has an else statement
        Watermark = new TextComponent(mc.font, "relapse b0", 8, 10, 0xFFFFFFFF);

        if (toAnim == watermarkAnimationState.enable) {
            if (watermarkX != 8) {
                watermarkX = (int) (watermarkAnimStep.getTime() * 0.01);
                Watermark = new TextComponent(mc.font, "relapse b0", watermarkX, 10, 0xFFFFFFFF);
            } else {
                toAnim = watermarkAnimationState.still;
            }
        } else {
            Watermark = new TextComponent(mc.font, "relapse b0", 8, 10, 0xFFFFFFFF);
        }

        ArrayList<Component> components = new ArrayList<>();
        components.add(Watermark);
        Client.getCompMan().drawComponents(graphics, partialTick, components);
    }

    @Override
    public void onEnable() {
        toAnim = watermarkAnimationState.enable;
        watermarkAnimStep.reset();
    }
    @Override
    public void onDisable() {
        toAnim =watermarkAnimationState.still;
        watermarkAnimStep.reset();
    }
}