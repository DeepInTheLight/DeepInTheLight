package deepinthelight;

import org.newdawn.slick.geom.Shape;

public abstract class Element {

    protected Shape box = null;

    public abstract void update();

    public abstract void render();

    public abstract void collide();

    public Shape getBox() {
        return this.box;
    }
}
