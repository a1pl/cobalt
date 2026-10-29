package cc.squall.cobalt.features;

import cc.squall.cobalt.Client;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;

public abstract class Feature {
    @Getter
    @Setter
    public boolean enabled;
    @Getter
    public String description;
    @Getter
    public String name;
    @Getter
    public Category category;
    @Getter
    protected final Minecraft mc = Minecraft.getInstance();

    public Feature(String name, String description, Category category) {
        this.name = name;
        this.description = description;
        this.category = category;
        this.enabled = false;
    }
    public Feature(String name, Category category) {
        this.name = name;
        this.description = "A feature";
        this.category = category;
        this.enabled = false;
    }

    public void onUpdate() {}
    public void onRender2d(GuiGraphicsExtractor graphics, float partialTick) {}
    public void onEnable() {}
    public void onDisable() {}
    public void onToggle() {}
    public void toggle() {
        if (enabled) disable();
        else enable();
    }
    public void enable() {
        if (enabled) return;
        enabled = true;
        Client.getInstance().getEventBus().subscribe(this);
        onEnable();
        onToggle();
    }
    public void disable() {
        if (!enabled) return;
        enabled = false;
        Client.getInstance().getEventBus().unsubscribe(this);
        onDisable();
        onToggle();
    }
    public boolean isDisabled() {
        return !isEnabled();
    }


    public enum Category {
        Combat, Movement, Render, Player, Misc
    }
}
