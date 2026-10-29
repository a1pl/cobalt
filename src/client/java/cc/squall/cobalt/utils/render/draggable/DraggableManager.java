package cc.squall.cobalt.utils.render.draggable;

import cc.squall.cobalt.Client;
import cc.squall.cobalt.features.Feature;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.ChatScreen;

import java.util.ArrayList;
import java.util.List;

public final class DraggableManager {
    private static final float CENTER_SNAP_DISTANCE = 10.0f;
    private static final float EDGE_SNAP_DISTANCE = 2.0f;
    private static final int HIGHLIGHT = 0xFFFFFFFF;

    private static final List<DraggableGui> GUIS = new ArrayList<>();

    private static Draggable dragging;
    private static float dragOffsetX;
    private static float dragOffsetY;
    private static boolean lastLeft;

    private DraggableManager() {
    }

    public static void registerGui(DraggableGui gui) {
        GUIS.add(gui);
    }

    public static void unregisterGui(DraggableGui gui) {
        GUIS.remove(gui);
    }

    public static List<DraggableGui> getGuis() {
        return GUIS;
    }

    public static void render(GuiGraphicsExtractor graphics) {
        Minecraft mc = Minecraft.getInstance();
        if (!(mc.gui.screen() instanceof ChatScreen)) {
            dragging = null;
            lastLeft = false;
            return;
        }

        int width = graphics.guiWidth();
        int height = graphics.guiHeight();
        float mouseX = (float) mc.mouseHandler.getScaledXPos(mc.getWindow());
        float mouseY = (float) mc.mouseHandler.getScaledYPos(mc.getWindow());
        boolean left = mc.mouseHandler.isLeftPressed();

        if (left && !lastLeft) {
            dragging = findAt(mouseX, mouseY);
            if (dragging != null) {
                dragOffsetX = mouseX - dragging.getX();
                dragOffsetY = mouseY - dragging.getY();
            }
        } else if (left && dragging != null) {
            float w = dragging.getWidth();
            float h = dragging.getHeight();
            dragging.setPosition(
                    snap(clamp(mouseX - dragOffsetX, 0, width - w), w, width),
                    snap(clamp(mouseY - dragOffsetY, 0, height - h), h, height));
        } else if (!left) {
            dragging = null;
        }
        lastLeft = left;

        Draggable highlighted = dragging != null ? dragging : findAt(mouseX, mouseY);
        if (highlighted != null) {
            graphics.outline((int) highlighted.getX() - 1, (int) highlighted.getY() - 1,
                    (int) highlighted.getWidth() + 2, (int) highlighted.getHeight() + 2, HIGHLIGHT);
        }
    }

    private static Draggable findAt(float mouseX, float mouseY) {
        List<Feature> features = Client.getInstance().getFeatman().getFeatures();
        for (int i = features.size() - 1; i >= 0; i--) {
            if (features.get(i) instanceof Draggable d && d.isEnabled() && d.isHovered(mouseX, mouseY)) {
                return d;
            }
        }
        return null;
    }

    private static float snap(float value, float size, int screenSize) {
        float center = (screenSize - size) / 2.0f;
        if (Math.abs(value - center) < CENTER_SNAP_DISTANCE) value = center;
        if (Math.abs(value) < EDGE_SNAP_DISTANCE) value = 0;
        else if (Math.abs(value + size - screenSize) < EDGE_SNAP_DISTANCE) value = screenSize - size;
        return value;
    }

    private static float clamp(float value, float min, float max) {
        return Math.max(min, Math.min(value, max));
    }
}
