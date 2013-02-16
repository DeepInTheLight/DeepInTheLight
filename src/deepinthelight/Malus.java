package deepinthelight;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Circle;
import org.newdawn.slick.geom.Rectangle;

/**
 *
 * @author jonas
 */
public class Malus extends Element {

    private Image img;
    private int damage = 10;
    private final float IMAGE_SCALE = 0.09f;

    public Malus(float posX, float posY, Screen screen) throws SlickException {
        int res = (int) (Math.random() * 3);
        boolean flip = ((int) Math.random()) == 0 ? true : false;

        switch (res) {
            case 0:
                this.img = new Image("images/malus/danger1.png", flip);
                this.box = new Circle(posX, posY, 25);
                this.boxOffsetX = 2;
                this.boxOffsetY = 12;
                break;
            case 1:
                this.img = new Image("images/malus/danger2.png", flip);
                this.box = new Circle(posX, posY, 25);
                this.boxOffsetX = 10;
                this.boxOffsetY = 10;
                break;
            case 2:
                this.img = new Image("images/malus/danger3.png", flip);
                this.box = new Circle(posX, posY, 25);
                this.boxOffsetX = 4;
                this.boxOffsetY = 7;
                break;
        }

        this.screen = screen;
    }

    @Override
    public void update() {
    }

    @Override
    public void render(float offsetX, float offsetY) {
        img.draw(this.box.getX() - offsetX - boxOffsetX, this.box.getY() - offsetY - boxOffsetY, IMAGE_SCALE);
    }

    @Override
    public boolean collide() {
        GamePlay.getGamePlay().gunther.changeHealth(-damage);
        screen.deleteElement(this);
        // TODO: call "hurting" or "eating" animation or make gunther blink?
        return false;
    }

    @Override
    public int getSize() {
        return 1;
    }
}
