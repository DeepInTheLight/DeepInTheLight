/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package deepinthelight;

import java.util.Date;
import java.util.Random;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Circle;

/**
 *
 * @author peniblec
 */
public class BonusFish extends Element {

    private final int RADIUS = 5;
    private final String IMAGE_PATH = "images/gunther/Gunther-eyelight-color.png"; // TODO change image
    private Image image;

    private final int ENERGY_BONUS = 20;

    private Direction currentDir = Direction.NONE;
    private final float SPEED = 2;
    private final float DIAG_SPEED = SPEED/(float)java.lang.Math.sqrt(2);

    private float oldX, oldY;
    long lastMove = new Date().getTime();

    public BonusFish() throws SlickException {
        box = new Circle(0, 0, RADIUS);
        image = new Image(IMAGE_PATH);

        oldX = 0;
        oldY = 0;
    }

    @Override
    public void update() {
        long now = new Date().getTime();
        if ( now - lastMove >= 5000 ) {
            changeDirection();
        }
        move(currentDir);
    }

    private void changeDirection() {

        switch (new Random().nextInt(8)) {

        case 0:
            currentDir = Direction.LEFT;
            break;
        case 1:
            currentDir = Direction.RIGHT;
            break;
        case 2:
            currentDir = Direction.UP;
            break;
        case 3:
            currentDir = Direction.DOWN;
            break;
        case 4:
            currentDir = Direction.LEFTUP;
            break;
        case 5:
            currentDir = Direction.LEFTDOWN;
            break;
        case 6:
            currentDir = Direction.RIGHTUP;
            break;
        case 7:
            currentDir = Direction.RIGHTDOWN;
            break;
        }
    }


    @Override
    public void render(float offsetX, float offsetY) {
        image.draw( box.getX() - offsetX, box.getY() - offsetY );
    }

    @Override
    public boolean collide() {
        GamePlay.getGamePlay().gunther.eat(this);
        GamePlay.getGamePlay().gunther.recharge(ENERGY_BONUS);
        return true;
    }

    public void moveBack() {
        box.setCenterX(oldX);
        box.setCenterY(oldY);
    }

    public void move(Direction newDir) {
        currentDir = newDir;
        
        oldX = box.getCenterX();
        oldY = box.getCenterY();
        
        switch(currentDir) {
        case LEFT :
            box.setCenterX( oldX - SPEED );
            break;
        case RIGHT :
            box.setCenterX( oldX + SPEED );
            break;
        case DOWN :
            box.setCenterY( oldY + SPEED );
            break;
        case UP :
            box.setCenterY( oldY - SPEED );
            break;
        case LEFTUP :
            box.setCenterX( oldX - DIAG_SPEED );
            box.setCenterY( oldY - DIAG_SPEED );
            break;
        case LEFTDOWN :
            box.setCenterX( oldX - DIAG_SPEED );
            box.setCenterY( oldY + DIAG_SPEED );
            break;
        case RIGHTUP :
            box.setCenterX( oldX + DIAG_SPEED );
            box.setCenterY( oldY - DIAG_SPEED );
            break;
        case RIGHTDOWN :
            box.setCenterX( oldX + DIAG_SPEED );
            box.setCenterY( oldY + DIAG_SPEED );
            break;
        }
    }
    
    public int getSize() {
        return 1;
    }
    
}
