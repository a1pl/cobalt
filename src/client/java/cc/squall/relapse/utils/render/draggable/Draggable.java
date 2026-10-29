package cc.squall.relapse.utils.render.draggable;

import cc.squall.relapse.features.Feature;
import com.google.gson.JsonObject;
import lombok.Getter;
import net.minecraft.client.gui.GuiGraphicsExtractor;

public abstract class Draggable extends Feature {
    private final float defaultX;
    private final float defaultY;
    @Getter
    private float width;
    @Getter
    private float height;
    @Getter
    private float x;
    @Getter
    private float y;
    private boolean initialized;

    protected Draggable(String name, String description, Category category,
                        float width, float height, float defaultX, float defaultY) {
        super(name, description, category);
        this.width = width;
        this.height = height;
        this.defaultX = defaultX;
        this.defaultY = defaultY;
    }

    public abstract void render(GuiGraphicsExtractor graphics, float partialTick);

    @Override
    public void onRender2d(GuiGraphicsExtractor graphics, float partialTick) {
        initialize(mc.getWindow().getGuiScaledWidth(), mc.getWindow().getGuiScaledHeight());
        render(graphics, partialTick);
    }

    public void initialize(int screenWidth, int screenHeight) {
        if (initialized) {
            return;
        }

        initialized = true;
        this.x = defaultX * (screenWidth - width);
        this.y = defaultY * (screenHeight - height);
    }

    public void center() {
        setPosition((mc.getWindow().getGuiScaledWidth() - width) / 2f,
                (mc.getWindow().getGuiScaledHeight() - height) / 2f);
    }

    public void resetPosition() {
        setPosition(defaultX * (mc.getWindow().getGuiScaledWidth() - width),
                defaultY * (mc.getWindow().getGuiScaledHeight() - height));
    }

    public void setPosition(float x, float y) {
        initialized = true;
        this.x = x;
        this.y = y;
    }

    public void setBounds(float width, float height) {
        this.width = width;
        this.height = height;
    }

    public boolean isHovered(float mouseX, float mouseY) {
        return mouseX >= x && mouseX <= x + width
                && mouseY >= y && mouseY <= y + height;
    }

    public JsonObject save() {
        JsonObject object = new JsonObject();
        JsonObject saved = new JsonObject();
        saved.addProperty("x", x);
        saved.addProperty("y", y);
        saved.addProperty("initialized", initialized);
        object.add("Position", saved);
        return object;
    }

    public void load(JsonObject object) {
        if (object == null || !object.has("Position")) {
            return;
        }

        JsonObject saved = object.getAsJsonObject("Position");

        if (saved.has("x")) {
            x = saved.get("x").getAsFloat();
        }

        if (saved.has("y")) {
            y = saved.get("y").getAsFloat();
        }

        if (saved.has("initialized")) {
            initialized = saved.get("initialized").getAsBoolean();
        }
    }
}
