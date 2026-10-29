package cc.squall.cobalt;

import cc.squall.cobalt.features.Feature;
import cc.squall.cobalt.features.FeatureManager;
import cc.squall.cobalt.features.impl.misc.ClientCommands;
import cc.squall.cobalt.features.impl.movement.Sprint;
import cc.squall.cobalt.utils.render.components.ComponentManager;
import cc.squall.cobalt.utils.render.draggable.DraggableManager;
import lombok.Getter;
import lombok.Setter;
import meteordevelopment.orbit.EventBus;
import meteordevelopment.orbit.IEventBus;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.rendering.v1.hud.HudElementRegistry;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.metadata.ModMetadata;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.Identifier;
import org.spongepowered.asm.mixin.MixinEnvironment;

import java.lang.invoke.MethodHandles;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Client implements ClientModInitializer {
    public static final String MOD_ID = "cobalt";
    @Getter
    public String clientName = "cobalt";

    @Getter
    public final IEventBus eventBus = new EventBus();

    @Getter
    public boolean devMode = true;
    @Getter
    public static Minecraft mc;
    public static final Logger log;
    public static final ModMetadata modMeta;
    public static final String name;
    @Getter
    public FeatureManager featman;
    @Getter
    private static Client instance;
    @Getter
    private static ComponentManager compMan;
    @Getter
    @Setter
    private float renderDeltaTime;
    //meteor paste

    static {
        modMeta = FabricLoader.getInstance().getModContainer(MOD_ID).orElseThrow().getMetadata();

        name = modMeta.getName();
        log = LoggerFactory.getLogger(name);
        String versionString = modMeta.getVersion().getFriendlyString();
        // dont use intellij play, use gradle through intellij instead
        if (versionString.contains("-")) versionString = versionString.split("-")[0];
    }
    @Override
    public void onInitializeClient() {
        instance = this;
        compMan = new ComponentManager();
        mc = Minecraft.getInstance();
        if (FabricLoader.getInstance().isDevelopmentEnvironment()) {
            log.info("Force loading mixins");
            MixinEnvironment.getCurrentEnvironment().audit();
        }
        log.warn("cobalt starting");

        eventBus.registerLambdaFactory("cc.squall.cobalt", (lookupInMethod, klass) -> (MethodHandles.Lookup) lookupInMethod.invoke(null, klass, MethodHandles.lookup()));

        featman = new FeatureManager();
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            for (Feature m : featman.getFeatures()) {
                if (m.isEnabled()) m.onUpdate();
            }
        });
        HudElementRegistry.addLast(Identifier.fromNamespaceAndPath(MOD_ID, "features"), (graphics, deltaTracker) -> {
            for (Feature m : featman.getFeatures()) {
                if (m.isEnabled()) m.onRender2d(graphics, deltaTracker.getRealtimeDeltaTicks());
            }
            DraggableManager.render(graphics);
        });
        featman.createFeatures();
    }
}
