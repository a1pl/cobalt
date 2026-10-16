package cc.squall.relapse.utils.render.components;

import net.minecraft.client.gui.GuiGraphicsExtractor;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class ComponentManager {
    public void drawComponents(GuiGraphicsExtractor graphics, float partialTick, ArrayList<Component> components) {
        for (Component component : components) {
            component.draw(graphics, partialTick);
        }
    }
}
