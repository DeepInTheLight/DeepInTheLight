package deepinthelight;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class Obstacle extends Element {

    private Image img;
    private static final String[] obstacleImgRes = { "coucou1", "coucou2",
                                                     "coucou3", "coucou4",
                                                     "coucou5", "coucou6",
                                                     "coucou7", "coucou8" };
    public Obstacle(int type, float centerX, float centerY)
                    throws SlickException {
        img = new Image(obstacleImgRes[type%8]);
    }

    public boolean collide() {
        return true; // obstacles block Gunther
    }
    
    public void update() {
    }

    @Override
    public void render(float offsetX, float offsetY) {
        img.draw(this.box.getX() - offsetX, this.box.getY() - offsetY);
    }

    @Override
    public int getSize() {
        return 1; //TODO:change -> dynamic, depend de l'obstacle
    }

}
