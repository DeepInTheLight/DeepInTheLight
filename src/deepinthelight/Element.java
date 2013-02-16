package deepinthelight;

import org.newdawn.slick.Graphics;
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
    
    public abstract void update();

    public abstract void render(float offsetX, float offsetY);

    // true: can't go through ; false: can go through
    public abstract boolean collide();

    public abstract int getSize();

    public Shape getBox() {
        return this.box;
    }
}
