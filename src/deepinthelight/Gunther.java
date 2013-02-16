/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package deepinthelight;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Circle;

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
    private final String IMAGE_PATH = "images/baleine.jpg";
    private Image image;

    private final int BASE_ENERGY = 42;
    private final int MAX_ENERGY = 100;
    private final int MAX_DECREASE = MAX_ENERGY / 20;
    private final int DECREASE_THRESHOLD = MAX_ENERGY / 2;
    private int energyLeft = BASE_ENERGY;
    
    private Direction currentDir = Direction.NONE;
    private final float SPEED = 5;
    private final float DIAG_SPEED = SPEED/(float)java.lang.Math.sqrt(2);

    public Gunther() throws SlickException {
        box = new Circle(0, 0, RADIUS);
        image = new Image(IMAGE_PATH);
    }

    @Override
    public void update() {
        energyLeft-= getEnergyDecrease();
    }

    private int getEnergyDecrease() {

        return ( energyLeft > DECREASE_THRESHOLD ? MAX_DECREASE :
                 ( energyLeft * MAX_DECREASE ) / MAX_DECREASE );
    }

    public void recharge(int amount) {
        energyLeft+= ( energyLeft + amount > MAX_ENERGY ? MAX_ENERGY : energyLeft + amount );
    }

    @Override
    public void render(float offsetX, float offsetY) {
        image.draw( box.getX() - offsetX, box.getY() - offsetY );
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
            box.setCenterY( curY + SPEED );
            break;
        case UP :
            box.setCenterY( curY - SPEED );
            break;
        case LEFTUP :
            box.setCenterX( curX - DIAG_SPEED );
            box.setCenterY( curY - DIAG_SPEED );
            break;
        case LEFTDOWN :
            box.setCenterX( curX - DIAG_SPEED );
            box.setCenterY( curY + DIAG_SPEED );
            break;
        case RIGHTUP :
            box.setCenterX( curX + DIAG_SPEED );
            box.setCenterY( curY - DIAG_SPEED );
            break;
        case RIGHTDOWN :
            box.setCenterX( curX + DIAG_SPEED );
            box.setCenterY( curY + DIAG_SPEED );
            break;
        }
        
    }

    @Override
    public int getSize() {
        return 1;
    }
}
