package deepinthelight;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.geom.Circle;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

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

}
