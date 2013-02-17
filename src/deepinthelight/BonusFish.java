/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package deepinthelight;

import java.util.Date;
import java.util.Random;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;

/**
 *
 * @author peniblec
 */
public class BonusFish extends Element {

    private final int RADIUS = 2;

    private Image image;
    private final float IMAGE_SCALE = 0.18f;

    private Image light;

    private final int ENERGY_BONUS = 20;

    private Direction currentDir;
    private final float SPEED = 2;
    private final float DIAG_SPEED = SPEED/(float)java.lang.Math.sqrt(2);

    long lastDirChange = new Date().getTime();
    float oldX, oldY;
    
    public BonusFish(float posX, float posY, Screen screen) throws SlickException {
        int res = (int) (Math.random() * 3);

        switch (res) {
            case 0:
                this.image = new Image("images/bonus/foodfish1_SMALL.png");
                this.box = new Rectangle(posX, posY, 40, 40);
                break;
            case 1:
                this.image = new Image("images/bonus/foodfish2_SMALL.png");
                this.box = new Rectangle(posX, posY, 40, 40);
                break;
            case 2:
                this.image = new Image("images/bonus/foodfish3_SMALL.png");
                this.box = new Rectangle(posX, posY, 40, 40);
                break;
        }

        this.light = new Image("images/light-small.png");

        this.screen = screen;
        
        oldX = posX;
        oldY = posY;
        box.setCenterX(oldX);
        box.setCenterY(oldY);

        currentDir = Direction.NONE;
    }

    @Override
    public void update(int delta) {
        long now = new Date().getTime();
        if ( now - lastDirChange >= 4000 ) {
            changeDirection();
            lastDirChange = now;
        }

        boolean moved = false;
        int trial = 0;
        while(!moved && trial < 10) {

            move(delta);
            trial++;
            moved = true;

            for (Element e : GamePlay.getGamePlay().world.getElements()) {
                if (e.getScreen() != GamePlay.getGamePlay().world.getCurrentScreen() && e.getSize() == 1) {
                    continue;
                }
                if ( e.getBox().intersects(this.getBox())
                     && e.getClass()==Obstacle.class ) {
                    abortMove();
                    moved = false;
                    break;
                }
            }

        }
    }

    private void abortMove() {
        box.setCenterX(oldX);
        box.setCenterY(oldY);
        changeDirection();
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

        // If we are too close to gunther, we should check if we're not running into him
        Gunther gunther = GamePlay.getGamePlay().gunther;
        float norm = gunther.getDistance(this);
        // System.out.println("norm : " + norm);
        if (norm < Main.height) {
            float calpha = getCosAlpha(gunther, currentDir);
            // System.out.println("cos alpha : " + calpha);
            if (calpha > 0.707) { // alpha < Pi/4
                switch(currentDir) {
                case UP :
                    currentDir = Direction.DOWN;
                    break;
                case DOWN :
                    currentDir = Direction.UP;
                    break;
                case LEFT :
                    currentDir = Direction.RIGHT;
                    break;
                case RIGHT :
                    currentDir = Direction.LEFT;
                    break;
                case LEFTUP:
                    currentDir = Direction.RIGHTDOWN;
                    break;
                case RIGHTUP :
                    currentDir = Direction.LEFTDOWN;
                    break;
                case LEFTDOWN :
                    currentDir = Direction.RIGHTUP;
                    break;
                case RIGHTDOWN :
                    currentDir = Direction.LEFTUP;
                    break;
                }
            }
        }
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

    @Override
    public void render(float offsetX, float offsetY) {
        boolean left = isFacingLeft();

        Image toRender = image.getFlippedCopy((!left), false );

        toRender.setCenterOfRotation( toRender.getWidth() * IMAGE_SCALE / 2,
                                      toRender.getHeight() * IMAGE_SCALE / 2 );
        //setBoxOffset();
        toRender.setRotation( getAngle() );
        
        toRender.draw(box.getX() - offsetX - boxOffsetX, box.getY() - offsetY - boxOffsetY, IMAGE_SCALE);
    }

    @Override
    public boolean collide() {
        Gunther gunther = GamePlay.getGamePlay().gunther;
        gunther.recharge(ENERGY_BONUS);
        gunther.eat();
        
        screen.deleteElement(this);

        GamePlay.getGamePlay().score += 10;

        return false; // Gunther isn't blocked by bonus fish
    }

    public void move(int delta) {
        
        oldX = box.getCenterX();
        oldY = box.getCenterY();
        float newX = oldX;
        float newY = oldY;
        
        switch(currentDir) {
        case LEFT :
            newX = oldX - (SPEED * (delta / 17f));
            break;
        case RIGHT :
            newX = oldX + (SPEED * (delta / 17f));
            break;
        case DOWN :
            newY = oldY + (SPEED * (delta / 17f));
            break;
        case UP :
            newY = oldY - (SPEED * (delta / 17f));
            break;
        case LEFTUP :
            newY = oldY - (DIAG_SPEED * (delta / 17f));
            newX = oldX - (DIAG_SPEED * (delta / 17f));
            break;
        case LEFTDOWN :
            newY = oldY + (DIAG_SPEED * (delta / 17f));
            newX = oldX - (DIAG_SPEED * (delta / 17f));
            break;
        case RIGHTUP :
            newY = oldY - (DIAG_SPEED * (delta / 17f));
            newX = oldX + (DIAG_SPEED * (delta / 17f));
            break;
        case RIGHTDOWN :
            newY = oldY + (DIAG_SPEED * (delta / 17f));
            newX = oldX + (DIAG_SPEED * (delta / 17f));
            break;
        }
        
        if (newX < screen.getLeftBoundary() || newX > screen.getRightBoundary()) {
            // WE HAVE TO GO BACK!
            float diff = oldX - newX;
            newX = oldX + diff;
        }

        if (newY < screen.getTopBoundary() || newY > screen.getBottomBoundary()) {
            float diff = oldY - newY;
            newY = oldY + diff;
        }

        box.setCenterY(newY);
        box.setCenterX(newX);
    }
    
    public int getSize() {
        return 3;
    }

    public void drawLight(float offsetX, float offsetY) {
        light.draw(box.getX() - offsetX, box.getY() - offsetY );
    }
 
    public float getCosAlpha(Element a, Direction where) {
        if (box == null) {
            return 0;
        }

        float x1 = a.getBox().getCenterX() - this.getBox().getCenterX();
        float y1 = a.getBox().getCenterY() - this.getBox().getCenterY();
        float x2 = 0;
        float y2 = 0;

        switch(where) {
        case UP :
            y2 = -1;
            break;
        case DOWN :
            y2 = 1;
            break;
        case LEFT :
            x2 = -1;
            break;
        case RIGHT :
            x2 = 1;
            break;
        case LEFTDOWN :
            x2 = -1;
            y2 = 1;
            break;
        case LEFTUP :
            x2 = -1;
            y2 = -1;
            break;
        case RIGHTDOWN :
            x2 = 1;
            y2 = 1;
            break;
        case RIGHTUP :
            x2 = 1;
            y2 = -1;
            break;
        }

        float dotProduct = x1*x2 + y1*y2;
        return dotProduct/(Element.computeNorm(x1,y1)*Element.computeNorm(x2, y2));
    }
}
