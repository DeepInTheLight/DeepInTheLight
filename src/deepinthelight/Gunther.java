/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package deepinthelight;

import java.util.Date;
import org.newdawn.slick.Animation;
import org.newdawn.slick.Image;
import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;
import org.newdawn.slick.geom.Circle;

/**
 *
 * @author peniblec
 */
public class Gunther extends Element {

    public final int RADIUS = 40;
    public static final String IMAGE_PATH = "images/gunther/Gunther-static.png";
    private final String EATING_ANIM = "images/gunther/Gunther-eating-";
    private final float IMAGE_SCALE = 0.18f;
    private Image image;

    private enum Anim {

        STATIC, EATING
    }
    Anim currentAnim;
    Animation eatingAnim;
    Sound eatingSound;
    long lastFrameUpdate = new Date().getTime();
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
    private final float DIAG_SPEED = SPEED / (float) java.lang.Math.sqrt(2);
    private float oldX, oldY;

    public Gunther() throws SlickException {
        box = new Circle(Main.width / 2, Main.height / 2, RADIUS);
        image = new Image(IMAGE_PATH);
        image.setCenterOfRotation(image.getWidth() * IMAGE_SCALE / 2,
                image.getHeight() * IMAGE_SCALE / 2);

        Image anim[] = new Image[6];
        for (int i = 0; i < anim.length; i++) {
            anim[i] = new Image(EATING_ANIM + i + ".png");
        }
        eatingAnim = new Animation(anim, 50, false);
        eatingAnim.setLooping(false);
        eatingSound = new Sound("music/gro 02.wav");

        currentAnim = Anim.STATIC;

        oldX = Main.width / 2;
        oldY = Main.height / 2;

        boxOffsetX = 20;
        boxOffsetY = 20;
    }

    @Override
    public void update(int delta) {
        long now = new Date().getTime();
        if (now - lastDecrease >= 1000) {
            setEnergyLeft(energyLeft - getEnergyDecrease());
            lastDecrease = now;
        }
    }

    private int getEnergyDecrease() {
        if (energyLeft > DECREASE_THRESHOLD) {
            return MAX_DECREASE;
        }

        int dec = (energyLeft * MAX_DECREASE) / DECREASE_THRESHOLD;
        if (dec < 1 && energyLeft > 0) {
            return 1;
        } else {
            return dec;
        }
    }

    public void eat() {
        eatingSound.play(1, 0.25f);
        eatingAnim.restart();
        currentAnim = Anim.EATING;
        lastFrameUpdate = new Date().getTime();
    }

    public void recharge(int amount) {
        setEnergyLeft(energyLeft + amount > MAX_ENERGY ? MAX_ENERGY
                : energyLeft + amount);
    }

    // considering up and down as facing right
    private boolean isFacingLeft() {
        return (currentDir == Direction.LEFT || currentDir == Direction.LEFTUP
                || currentDir == Direction.LEFTDOWN);
    }

    private float getAngle() {
        switch (currentDir) {
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
            default:
                return 0;
        }
    }

    private void setBoxOffset() {
        switch (currentDir) {
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

        Image toRender;
        switch (currentAnim) {

            case STATIC:
                toRender = image;
                break;
            case EATING:
                long now = new Date().getTime();
                if (now - lastFrameUpdate > 50) {
                    eatingAnim.update(now - lastFrameUpdate);
                    lastFrameUpdate = now;
                }
                toRender = eatingAnim.getCurrentFrame();
                break;
            default:
                toRender = image;
                break;
        }
        toRender = toRender.getFlippedCopy(left, false);

        toRender.setCenterOfRotation(toRender.getWidth() * IMAGE_SCALE / 2,
                toRender.getHeight() * IMAGE_SCALE / 2);
        setBoxOffset();
        toRender.setRotation(getAngle());

        toRender.draw(box.getX() - offsetX - boxOffsetX, box.getY() - offsetY - boxOffsetY, IMAGE_SCALE);
    }

    @Override
    public boolean collide() {
        return false; // Gunther can't collide with itself
    }

    public void moveBack() {
        box.setCenterX(oldX);
        box.setCenterY(oldY);
    }

    public void move(Direction newDir, int delta) {
        //oldDir = currentDir;
        currentDir = newDir;

        oldX = box.getCenterX();
        oldY = box.getCenterY();

        switch (currentDir) {
            case LEFT:
                box.setCenterX(oldX - (SPEED * (delta / 17f)));
                break;
            case RIGHT:
                box.setCenterX(oldX + (SPEED * (delta / 17f)));
                break;
            case DOWN:
                box.setCenterY(oldY + (SPEED * (delta / 17f)));
                break;
            case UP:
                box.setCenterY(oldY - (SPEED * (delta / 17f)));
                break;
            case LEFTUP:
                box.setCenterX(oldX - (DIAG_SPEED * (delta / 17f)));
                box.setCenterY(oldY - (DIAG_SPEED * (delta / 17f)));
                break;
            case LEFTDOWN:
                box.setCenterX(oldX - (DIAG_SPEED * (delta / 17f)));
                box.setCenterY(oldY + (DIAG_SPEED * (delta / 17f)));
                break;
            case RIGHTUP:
                box.setCenterX(oldX + (DIAG_SPEED * (delta / 17f)));
                box.setCenterY(oldY - (DIAG_SPEED * (delta / 17f)));
                break;
            case RIGHTDOWN:
                box.setCenterX(oldX + (DIAG_SPEED * (delta / 17f)));
                box.setCenterY(oldY + (DIAG_SPEED * (delta / 17f)));
                break;
        }
    }

    public void setRelativePos(float x, float y) {
        oldX = box.getCenterX();
        oldY = box.getCenterY();
        box.setCenterX(oldX + x);
        box.setCenterX(oldY + y);
    }

    public void changeHealth(int amount) {
        if (amount < 0) {
            if (health + amount <= 0) {
                health = 0;
                die();
            } else {
                health += amount;
            }
        }
        if (amount > 0) {
            health = (health + amount > MAX_HEALTH ? MAX_HEALTH
                    : health + amount);
        }
    }

    public int getHealth() {
        return health;
    }

    public int getEnergyLeft() {
        return energyLeft;
    }
    private int oldEnergyLevel = 3;

    public void setEnergyLeft(int en) {
        energyLeft = en;

        if (getLevel(energyLeft) != oldEnergyLevel) {
            float pos = Main.music.getPosition();

            try {
                if (getLevel(energyLeft) == 1) {
                    Main.music = new Music("music/niveau1.wav");
                } else if (getLevel(energyLeft) == 2) {
                    Main.music = new Music("music/niveau2.wav");
                } else {
                    Main.music = new Music("music/niveau3.wav");
                }
            } catch (SlickException ex) {
                ex.printStackTrace();
            }

            Main.music.setPosition(pos);
            Main.music.setVolume(0.2f);
            Main.music.fade(700, 1, false);
            Main.music.loop();

            oldEnergyLevel = getLevel(energyLeft);
        }
    }

    private int getLevel(int energie) {
        if (energie > 66) {
            return 1;
        } else if (energie > 33) {
            return 2;
        } else {
            return 3;
        }
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
