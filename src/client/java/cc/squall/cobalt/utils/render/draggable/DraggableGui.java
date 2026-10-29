package cc.squall.cobalt.utils.render.draggable;

import com.google.gson.JsonObject;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.Screen;

public abstract class DraggableGui {
    protected final Minecraft mc = Minecraft.getInstance();

    private final float defaultX;
    private final float defaultY;
    @Getter
    @Setter
    private Screen owner;
    @Getter
    private float width;
    @Getter
    private float height;
    @Getter
    private float x;
    @Getter
    private float y;
    private boolean initialized;

    protected DraggableGui(float width, float height, float defaultX, float defaultY) {
        this.width = width;
        this.height = height;
        this.defaultX = defaultX;
        this.defaultY = defaultY;
        this.owner = mc.gui.screen();
        DraggableManager.registerGui(this);
    }

    public abstract void render(GuiGraphicsExtractor graphics, float partialTick);

    public void onClick(int mouseX, int mouseY, int button) {
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

    public void remove() {
        DraggableManager.unregisterGui(this);
    }

    public JsonObject save() {
        JsonObject object = new JsonObject();
        object.addProperty("x", x);
        object.addProperty("y", y);
        object.addProperty("initialized", initialized);
        return object;
    }

    public void load(JsonObject object) {
        if (object == null) {
            return;
        }

        if (object.has("x")) {
            x = object.get("x").getAsFloat();
        }

        if (object.has("y")) {
            y = object.get("y").getAsFloat();
        }

        if (object.has("initialized")) {
            initialized = object.get("initialized").getAsBoolean();
        }
    }
}
