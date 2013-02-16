/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package deepinthelight;

import org.newdawn.slick.geom.Circle;
import org.newdawn.slick.Image;
import org.newdawn.slick.Renderable;
import org.newdawn.slick.SlickException;

/**
 *
 * @author peniblec
 */
public class Gunther extends Element {

    public enum Direction {
        NONE, LEFT, RIGHT, UP, DOWN,
            LEFTUP, LEFTDOWN, RIGHTUP, RIGHTDOWN
    }

    private final int RADIUS = 5;
    private final String IMAGE_PATH = "wherever/the/fuck/lol.png";
    private Image image;

    private final int BASE_ENERGY = 42;
    private final int ENERGY_DEC = 5;
    private int energyLeft = BASE_ENERGY;
    
    private Direction currentDir = Direction.NONE;
    private final float SPEED = 5;
    private final float DIAG_SPEED = SPEED/(float)java.lang.Math.sqrt(2);

    public Gunther() {
        box = new Circle(0, 0, RADIUS);
        image = new Image(IMAGE_PATH);
    }

    @Override
    public void update() {
        energyLeft-= ENERGY_DEC;
    }

    @Override
    public void render(float offsetX, float offsetY) {
        
    }

    @Override
    public void collide() {
        // useless?
    }

    public void move(Direction newDir) {
        currentDir = newDir;
        
        float curX = box.getCenterX();
        float curY = box.getCenterY();
        
        switch(currentDir) {
        case LEFT :
            box.setCenterX( curX - SPEED );
            break;
        case RIGHT :
            box.setCenterX( curX + SPEED );
            break;
        case DOWN :
            box.setCenterY( curY - SPEED );
            break;
        case UP :
            box.setCenterY( curY + SPEED );
            break;
        case LEFTUP :
            box.setCenterX( curX - DIAG_SPEED );
            box.setCenterY( curY + DIAG_SPEED );
            break;
        case LEFTDOWN :
            box.setCenterX( curX - DIAG_SPEED );
            box.setCenterY( curY - DIAG_SPEED );
            break;
        case RIGHTUP :
            box.setCenterX( curX + DIAG_SPEED );
            box.setCenterY( curY + DIAG_SPEED );
            break;
        case RIGHTDOWN :
            box.setCenterX( curX + DIAG_SPEED );
            box.setCenterY( curY - DIAG_SPEED );
            break;
        }
        
    }
}
