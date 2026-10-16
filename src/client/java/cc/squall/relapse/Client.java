package cc.squall.relapse;

import cc.squall.relapse.features.Feature;
import cc.squall.relapse.features.FeatureManager;
import cc.squall.relapse.features.impl.misc.ClientCommands;
import cc.squall.relapse.features.impl.movement.Sprint;
import cc.squall.relapse.utils.render.components.ComponentManager;
import lombok.Getter;
import lombok.Setter;
import meteordevelopment.orbit.EventBus;
import meteordevelopment.orbit.IEventBus;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.rendering.v1.hud.HudElementRegistry;
import net.fabricmc.fabric.api.client.screen.v1.ScreenEvents;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.metadata.ModMetadata;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.Identifier;
import org.spongepowered.asm.mixin.MixinEnvironment;

import java.lang.invoke.MethodHandles;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Client implements ClientModInitializer {
    public static final String MOD_ID = "relapse";
    @Getter
    public String clientName = "relapse";

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
        log.warn("relapse starting");

        eventBus.registerLambdaFactory("cc.squall.relapse", (lookupInMethod, klass) -> (MethodHandles.Lookup) lookupInMethod.invoke(null, klass, MethodHandles.lookup()));

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
        });
        ScreenEvents.AFTER_INIT.register((client, screen, w, h) ->
                ScreenEvents.afterExtract(screen).register((scr, graphics, mouseX, mouseY, tickDelta) -> {
                    for (Feature m : featman.getFeatures()) {
                        if (m.isEnabled()) m.onRender2d(graphics, tickDelta);
                    }
                }));
        featman.createFeatures();
    }
}
