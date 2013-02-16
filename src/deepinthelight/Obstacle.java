package deepinthelight;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class Obstacle extends Element {

    private Image img;

    public Obstacle(String source, float centerX,
                    float centerY, int size) throws SlickException {
        img = new Image(source);
    }

    public void collide() {
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
