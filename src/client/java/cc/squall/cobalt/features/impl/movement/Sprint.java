package cc.squall.cobalt.features.impl.movement;

import cc.squall.cobalt.features.Feature;

public class Sprint extends Feature {
    public Sprint() {
        super("Sprint", "Sprints for you", Category.Movement);
    }

    @Override
    public void onUpdate() {
        if (mc.player == null) return;
        if (!mc.options.keySprint.isDown()) {
            mc.options.keySprint.setDown(true);
        }
    }
}
