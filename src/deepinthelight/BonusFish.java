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
        
        posX = 0;
        posY = 0;

        currentDir = Direction.NONE;
    }

    @Override
    public void update() {
        long now = new Date().getTime();
        if ( now - lastDirChange >= 2500 ) {
            changeDirection();
            lastDirChange = now;
        }

        boolean moved = false;
        while(!moved) {

            move();
            moved = true;

            for (Element e : GamePlay.getGamePlay().world.getElements()) {
                if (e.getBox().intersects(this.getBox()) && e.collide()) {
                    box.setCenterX(oldX);
                    box.setCenterY(oldY);
                    changeDirection();
                    moved = false;
                    break;
                }
            }

        }
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
        GamePlay.getGamePlay().gunther.recharge(ENERGY_BONUS);
        screen.deleteElement(this);
        return false; // Gunther isn't blocked by bonus fish
    }

    public void move() {
        
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

    public void drawLight(float offsetX, float offsetY) {
        ligth.draw(box.getX() - offsetX, box.getY() - offsetY );
    }
    
}
