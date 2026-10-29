package cc.squall.cobalt.mixin.ModInteg;

import cc.squall.cobalt.Client;
import java.util.function.BooleanSupplier;

@SuppressWarnings("unused")
public class BadOptimizationsHook implements BooleanSupplier {

    private boolean lastState;

    @Override
    public boolean getAsBoolean() {
        boolean state = Client.getInstance().getFeatman().isEnabled("Fullbright");
        boolean changed = state != lastState;
        lastState = state;
        return changed;
    }
}