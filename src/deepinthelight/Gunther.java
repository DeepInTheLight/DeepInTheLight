/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package deepinthelight;

import java.util.Date;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Circle;

/**
 *
 * @author peniblec
 */
public class Gunther extends Element {

    public final int RADIUS = 40;
    public static final String IMAGE_PATH = "images/gunther/Gunther-finalblue_SMALL.png";
    private final float IMAGE_SCALE = 0.18f;

    public Image imageRight;
    public Image imageLeft;

    private final int BASE_ENERGY = 80;
    public final int MAX_ENERGY = 100;
    private final int MAX_DECREASE = MAX_ENERGY / 20;
    private final int DECREASE_THRESHOLD = MAX_ENERGY / 2;
    private int energyLeft = BASE_ENERGY;
    private long lastDecrease = new Date().getTime();

    private final int MAX_HEALTH = 100;

    private int health = MAX_HEALTH;

    private Direction currentDir = Direction.NONE;
    //private Direction oldDir = Direction.NONE;

    private final float SPEED = 5;
    private final float DIAG_SPEED = SPEED/(float)java.lang.Math.sqrt(2);

    private float oldX, oldY;

    public Gunther() throws SlickException {
        box = new Circle(Main.width / 2, Main.height / 2, RADIUS);
        imageRight = new Image(IMAGE_PATH);
        imageRight.setCenterOfRotation( imageRight.getWidth() * IMAGE_SCALE / 2,
                                   imageRight.getHeight() * IMAGE_SCALE / 2 );
        imageLeft = imageRight.getFlippedCopy(true, false);
        imageLeft.setCenterOfRotation( imageLeft.getWidth() * IMAGE_SCALE / 2,
                                   imageLeft.getHeight() * IMAGE_SCALE / 2 );


        oldX = Main.width / 2;
        oldY = Main.height / 2;

        boxOffsetX = 20;
        boxOffsetY = 20;
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
        if ( energyLeft > DECREASE_THRESHOLD ) {
            return MAX_DECREASE;
        }

        int dec = ( energyLeft * MAX_DECREASE ) / DECREASE_THRESHOLD;
        if ( dec < 1 && energyLeft > 0 ) {
            return 1;
        }
        else {
            return dec;
        }
    }

    public void eat(Element e) {
        // TODO: change animation
    }

    public void recharge(int amount) {
        energyLeft= ( energyLeft + amount > MAX_ENERGY ? MAX_ENERGY
                       : energyLeft + amount );
    }

    // considering up and down as facing right
    private boolean isFacingLeft() {
        return ( currentDir == Direction.LEFT || currentDir == Direction.LEFTUP 
                || currentDir == Direction.LEFTDOWN );
    }

    private float getAngle() {
        switch ( currentDir ) {
        case LEFT:
        case RIGHT:
            return 0;
        case LEFTUP:
        case RIGHTDOWN:
            return 45;
        case RIGHTUP:
        case LEFTDOWN:
            return 315;
        case UP:
            return 270;
        case DOWN:
            return 90;
        default: return 0;
        }
    }

    private void setBoxOffset() {
        switch ( currentDir ) {
        case LEFT:
            boxOffsetX = 10;
            boxOffsetY = 20;
            break;
        case RIGHT:
            boxOffsetX = 20;
            boxOffsetY = 20;
            break;
        case UP:
            boxOffsetX = 22;
            boxOffsetY = 5;
            break;
        case DOWN:
            boxOffsetX = 5;
            boxOffsetY = 18;
            break;
        case LEFTUP:
            boxOffsetX = 5;
            boxOffsetY = 12;
            break;
        case LEFTDOWN:
            boxOffsetX = 17;
            boxOffsetY = 22;
            break;
        case RIGHTUP:
            boxOffsetX = 25;
            boxOffsetY = 14;
            break;
        case RIGHTDOWN:
            boxOffsetX = 13;
            boxOffsetY = 24;
            break;
        }
    }
    
    @Override
    public void render(float offsetX, float offsetY) {

        boolean left = isFacingLeft();
        Image image = ( left ? imageLeft : imageRight );

        setBoxOffset();
        image.setRotation( getAngle() );
        
        image.draw(box.getX() - offsetX - boxOffsetX, box.getY() - offsetY - boxOffsetY, IMAGE_SCALE);
    }

    @Override
    public boolean collide() {
        return false; // Gunther can't collide with itself
    }

    public void moveBack() {
        box.setCenterX(oldX);
        box.setCenterY(oldY);
    }

    public void move(Direction newDir) {
        //oldDir = currentDir;
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
    
    public void setRelativePos(float x, float y) {
        oldX = box.getCenterX();
        oldY = box.getCenterY();
        box.setCenterX( oldX + x );
        box.setCenterX( oldY + y );
    }

    public void changeHealth(int amount) {
        if ( amount < 0 ) {
            if ( health + amount <= 0 ) {
                health = 0;
                die();
            }
            else {
                health+= amount;
            }
        }
        if ( amount > 0 ) {
            health = ( health + amount > MAX_HEALTH ? MAX_HEALTH
                       : health + amount );
        }
    }
    
    public int getHealth() {
        return health;
    }
    
    public int getEnergyLeft() {
        return energyLeft;
    }
    
    public void setEnergyLeft(int en) {
        energyLeft = en;
    }
    

    public void die() {
        GamePlay.getGamePlay().sbg.enterState(Main.GAMEOVER);
        // TODO : gameover ?
    }

    @Override
    public int getSize() {
        return 1;
    }
/*
    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }
  */  
    
}
