package deepinthelight;

import org.newdawn.slick.geom.Shape;

public abstract class Element {

    public enum Direction {
        NONE, LEFT, RIGHT, UP, DOWN,
            LEFTUP, LEFTDOWN, RIGHTUP, RIGHTDOWN
    }

    protected Shape box = null;
    protected Screen screen = null;

    protected float boxOffsetX = 0;
    protected float boxOffsetY = 0;
    
    public abstract void update(int delta);

    public abstract void render(float offsetX, float offsetY);

    // true: can't go through ; false: can go through
    public abstract boolean collide();

    public abstract int getSize();

    public Shape getBox() {
        return this.box;
    }

    public float getDistance(Element a) {
        if (box == null) {
            return 0;
        }

        float xa = a.getBox().getCenterX();
        float xb = this.getBox().getCenterX();
        float ya = a.getBox().getCenterY();
        float yb = this.getBox().getCenterY();

        return computeNorm(xb-xa, yb-ya);
    }

    public static float computeNorm(float x, float y) {
        return (float)Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
    }

    public Screen getScreen() {
        return this.screen;
    }

}
