package deepinthelight;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class Obstacle extends Element {

    private Image img;

    public Obstacle(String source, float centerX,
                    float centerY, float radius) throws SlickException {
        img = new Image(source);
    }

    public void collide() {
    }

    public void render() {
    }

    public void update() {
    }

    @Override
    public void render(float offsetX, float offsetY) {
    }

}
