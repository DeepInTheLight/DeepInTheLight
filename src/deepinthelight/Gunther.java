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

    public final int RADIUS = 40;
    private final String IMAGE_PATH = "images/gunther/Gunther-finalblue.png";
    private final float IMAGE_SCALE = 0.09f;

    public Image image;

    private final int BASE_ENERGY = 42;
    public final int MAX_ENERGY = 100;
    private final int MAX_DECREASE = MAX_ENERGY / 20;
    private final int DECREASE_THRESHOLD = MAX_ENERGY / 2;
    private int energyLeft = BASE_ENERGY;
    private long lastDecrease = new Date().getTime();

    private final int MAX_HEALTH = 100;

    private int health = MAX_HEALTH;

    private Direction currentDir = Direction.NONE;
    private final float SPEED = 5;
    private final float DIAG_SPEED = SPEED/(float)java.lang.Math.sqrt(2);

    private float oldX, oldY;

    public Gunther() throws SlickException {
        box = new Circle(Main.width / 2, Main.height / 2, RADIUS);
        image = new Image(IMAGE_PATH);

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
        energyLeft+= ( energyLeft + amount > MAX_ENERGY ? MAX_ENERGY
                       : energyLeft + amount );
    }

    @Override
    public void render(float offsetX, float offsetY) {

        image.setCenterOfRotation( image.getWidth() * IMAGE_SCALE / 2,
                                   image.getHeight() * IMAGE_SCALE / 2 );
        float rotate = 0;

        switch (currentDir) {
        case LEFT :
            rotate = 180;
            break;
        case RIGHT :
            rotate = 0;
            break;
        case DOWN :
            rotate = 90;
            break;
        case UP :
            rotate = 270;
            break;
        case LEFTUP :
            rotate = 135;
            break;
        case LEFTDOWN :
            rotate = 225;
            break;
        case RIGHTUP :
            rotate = 45;
            break;
        case RIGHTDOWN :
            rotate = 315;
            break;
        }

        float angle = rotate - image.getRotation();
        image.rotate(angle);
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

    public void changeHealth(int amount) {
        if ( amount < 0 ) {
            if ( health + amount < 0 ) {
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
    

    public void die() {
        // TODO : gameover ?
    }

    @Override
    public int getSize() {
        return 1;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }
    
    
}
