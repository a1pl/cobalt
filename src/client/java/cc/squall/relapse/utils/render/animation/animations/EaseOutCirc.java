package cc.squall.relapse.utils.render.animation.animations;


import cc.squall.relapse.utils.render.animation.Animation;
import cc.squall.relapse.utils.render.animation.Direction;

public class EaseOutCirc extends Animation {

    public EaseOutCirc(int ms, double endPoint) {
        super(ms, endPoint);
    }

    public EaseOutCirc(int ms, double endPoint, Direction direction) {
        super(ms, endPoint, direction);
    }

    @Override
    protected boolean correctOutput() {
        return true;
    }

    protected double getEquation(double x) {
        return Math.sqrt(1 - Math.pow(x - 1, 2));
    }
}