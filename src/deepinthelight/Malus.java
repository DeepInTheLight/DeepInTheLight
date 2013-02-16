package deepinthelight;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;

/**
 *
 * @author jonas
 */
public class Malus extends Element {

    private Image img;
    private int damage = 10;

    public Malus(float posX, float posY, Screen screen) throws SlickException {
        int res = (int) Math.random() * 3;
        boolean flip = ((int) Math.random()) == 0 ? true : false;

        switch (res) {
            case 0:
                this.img = new Image("images/baleine.png", flip);
                this.box = new Rectangle(posX, posY, 40, 40);
                break;
            case 1:
                this.img = new Image("images/baleine.png", flip);
                this.box = new Rectangle(posX, posY, 40, 40);
                break;
            case 2:
                this.img = new Image("images/baleine.png", flip);
                this.box = new Rectangle(posX, posY, 40, 40);
                break;
        }

        this.screen = screen;
    }

    @Override
    public void update() {
    }

    @Override
    public void render(float offsetX, float offsetY) {
        img.draw(this.box.getX() - offsetX, this.box.getY() - offsetY);
    }

    @Override
    public boolean collide() {
        GamePlay.getGamePlay().gunther.changeHealth(-damage);
        GamePlay.getGamePlay().world.queueForDeletion(this);
        // TODO: call "hurting" or "eating" animation or make gunther blink?
        return false;
    }

    @Override
    public int getSize() {
        return 1;
    }
}
