package cc.squall.cobalt.mixin;

import cc.squall.cobalt.Client;
import net.minecraft.client.Minecraft;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Minecraft.class)
public class MinecraftMixin {
    @Unique
    private long cobalt$lastFrame = System.nanoTime();

    @Inject(method = "runTick", at = @At("HEAD"))
    private void cobalt$updateDeltaTime(boolean advanceGameTime, CallbackInfo ci) {
        long now = System.nanoTime();
        float deltaMs = (now - cobalt$lastFrame) / 1_000_000f;
        cobalt$lastFrame = now;

        Client client = Client.getInstance();
        if (client != null) client.setRenderDeltaTime(deltaMs);
    }
}
