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
    private final String IMAGE_PATH = "images/gunther/Gunther-eyelight-color.png"; // TODO change image
    private Image image;

    private Image ligth;

    private final int ENERGY_BONUS = 20;

    private Direction currentDir;
    private final float SPEED = 2;
    private final float DIAG_SPEED = SPEED/(float)java.lang.Math.sqrt(2);

    long lastDirChange = new Date().getTime();
    float oldX, oldY;
    
    public BonusFish(float posX, float posY, Screen screen) throws SlickException {
        int res = (int) Math.random() * 3;
        boolean flip = ((int) Math.random()) == 0 ? true : false;

        switch (res) {
            case 0:
                this.image = new Image("images/baleine.png", flip);
                this.box = new Rectangle(posX, posY, 40, 40);
                break;
            case 1:
                this.image = new Image("images/baleine.png", flip);
                this.box = new Rectangle(posX, posY, 40, 40);
                break;
            case 2:
                this.image = new Image("images/baleine.png", flip);
                this.box = new Rectangle(posX, posY, 40, 40);
                break;
        }

        this.ligth = new Image("images/ligth-small.png");

        this.screen = screen;
        
        oldX = posX;
        oldY = posY;
        box.setCenterX(oldX);
        box.setCenterY(oldY);

        currentDir = Direction.NONE;
    }

    @Override
    public void update() {
        long now = new Date().getTime();
        if ( now - lastDirChange >= 4000 ) {
            changeDirection();
            lastDirChange = now;
        }

        boolean moved = false;
        int trial = 0;
        while(!moved && trial < 10) {

            move();
            trial++;
            moved = true;

            for (Element e : GamePlay.getGamePlay().world.getElements()) {
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
        System.out.println("norm : " + norm);
        if (norm < Main.height) {
            float calpha = getCosAlpha(gunther, currentDir);
            System.out.println("cos alpha : " + calpha);
            if (calpha > 0.707) { // alpha < Pi/4
                System.out.println("ERMARGHERD! GUNTHER! ");
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


    @Override
    public void render(float offsetX, float offsetY) {
        image.draw( box.getX() - offsetX, box.getY() - offsetY );
    }

    @Override
    public boolean collide() {
        GamePlay.getGamePlay().gunther.recharge(ENERGY_BONUS);
        screen.deleteElement(this);
        return false; // Gunther isn't blocked by bonus fish
    }

    public void move() {
        
        oldX = box.getCenterX();
        oldY = box.getCenterY();
        float newX = oldX;
        float newY = oldY;
        
        switch(currentDir) {
        case LEFT :
            newX = oldX - SPEED;
            break;
        case RIGHT :
            newX = oldX + SPEED;
            break;
        case DOWN :
            newY = oldY + SPEED;
            break;
        case UP :
            newY = oldY - SPEED;
            break;
        case LEFTUP :
            newY = oldY - DIAG_SPEED;
            newX = oldX - DIAG_SPEED;
            break;
        case LEFTDOWN :
            newY = oldY + DIAG_SPEED;
            newX = oldX - DIAG_SPEED;
            break;
        case RIGHTUP :
            newY = oldY - DIAG_SPEED;
            newX = oldX + DIAG_SPEED;
            break;
        case RIGHTDOWN :
            newY = oldY + DIAG_SPEED;
            newX = oldX + DIAG_SPEED;
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
        return 1;
    }

    public void drawLight(float offsetX, float offsetY) {
        ligth.draw(box.getX() - offsetX, box.getY() - offsetY );
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
