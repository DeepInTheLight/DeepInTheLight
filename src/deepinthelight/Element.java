package deepinthelight;

import org.newdawn.slick.geom.Shape;

public abstract class Element {

    protected Shape box = null;

    public abstract void update();

    public abstract void render(float offsetX, float offsetY);

    // true: gunther can go through ; false: collision!
    public abstract boolean collide();

    public abstract int getSize();

    public Shape getBox() {
        return this.box;
    }
}
