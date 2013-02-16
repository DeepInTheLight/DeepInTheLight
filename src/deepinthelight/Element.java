package deepinthelight;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Shape;

public abstract class Element {

    protected Shape box = null;

    public abstract void update();

    public abstract void render(float offsetX, float offsetY, Graphics g);

    public abstract void collide();

    public abstract int getSize();

    public Shape getBox() {
        return this.box;
    }
}
