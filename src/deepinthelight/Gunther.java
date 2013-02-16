/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package deepinthelight;

import java.util.Date;
import org.newdawn.slick.Graphics;
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
    private final String IMAGE_PATH = "images/gunther/Gunther-bulblight-color.png";
    private Image image;

    private final int BASE_ENERGY = 42;
    private final int MAX_ENERGY = 100;
    private final int MAX_DECREASE = MAX_ENERGY / 20;
    private final int DECREASE_THRESHOLD = MAX_ENERGY / 2;
    private int energyLeft = BASE_ENERGY;
    private long lastDecrease = new Date().getTime();

    private final int MAX_HEALTH = 1000;
    private int health = MAX_HEALTH;

    private Direction currentDir = Direction.NONE;
    private final float SPEED = 5;
    private final float DIAG_SPEED = SPEED/(float)java.lang.Math.sqrt(2);

    public Gunther() throws SlickException {
        box = new Circle(0, 0, RADIUS);
        image = new Image(IMAGE_PATH);
    }

    @Override
    public void update() {

        long now = new Date().getTime();
        if ( now - lastDecrease >= 1000 ) {
            energyLeft-= getEnergyDecrease();
            lastDecrease = now;
        }
    }

    private int getEnergyDecrease() {

        if ( energyLeft > DECREASE_THRESHOLD )
            return MAX_DECREASE;

        int dec = ( energyLeft * MAX_DECREASE ) / DECREASE_THRESHOLD;
        if ( dec < 1 && energyLeft > 0 ) {
            return 1;
        }
        else
            return dec;
    }

    public void recharge(int amount) {
        energyLeft+= ( energyLeft + amount > MAX_ENERGY ? MAX_ENERGY
                       : energyLeft + amount );
    }

    public void render(float offsetX, float offsetY, Graphics g) {
           
        image.draw( box.getX() - offsetX, box.getY() - offsetY, 0.3f);        
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

    public int getHealth() {
        return health;
    }
    
    @Override
    public int getSize() {
        return 1;
    }
}
