package cc.squall.relapse.utils.render.animation.animations;

import cc.squall.relapse.utils.render.animation.Animation;
import cc.squall.relapse.utils.render.animation.Direction;
import cc.squall.relapse.utils.render.animation.Easing;

//pandaware wrapper
public class EasingAnimation extends Animation {
    private final Easing easing;

    public EasingAnimation(Easing easing, int ms, double endPoint) {
        super(ms, endPoint);
        this.easing = easing;
    }

    public EasingAnimation(Easing easing, int ms, double endPoint, Direction direction) {
        super(ms, endPoint, direction);
        this.easing = easing;
    }

    @Override
    protected boolean correctOutput() {
        return true;
    }

    @Override
    protected double getEquation(double x) {
        float t = (float) Math.clamp(x, 0, 1);
        return easing.ease(t, 0, 1, 1);
    }
}
