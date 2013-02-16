package deepinthelight;

import java.util.HashMap;

import org.newdawn.slick.geom.Shape;

public class Screen {

    public static HashMap<String, Screen> ScreenMap = null;
    private final int maxObstacleSize = 20;
    private int obstacleSize = 0;
    private boolean populated;

    public enum Zone {
        UP, DOWN, LEFT, RIGHT
    }

    private float top;
    private float bottom;
    private float left;
    private float right;

    private Screen(float top, float bottom, float left, float right) {
        this.top = top;
        this.bottom = bottom;
        this.left = left;
        this.right = right;
        populated = false;
        ScreenMap.put(this.serialize(), this);
    }

    public static Screen init(float screenX, float screenY) {
        if (ScreenMap != null) {
            return null;
        }

        ScreenMap = new HashMap<String, Screen>(100);
        return new Screen(screenY, screenY + Main.height,
                          screenX, screenX + Main.height);
    }

    public Screen getNextScreen(Gunther gunther) {
        Shape box = gunther.getBox();
        Screen nextScreen = null;
        if (box.getCenterY() > top) {
            nextScreen = getNextScreen(Zone.UP);
        } else if (box.getCenterY() < bottom) {
            nextScreen = getNextScreen(Zone.DOWN);
        } else if (box.getCenterX() < left) {
            nextScreen = getNextScreen(Zone.LEFT);
        } else if (box.getCenterX() > right) {
            nextScreen = getNextScreen(Zone.RIGHT);
        }

        if (!nextScreen.isInScreen(gunther)) {
            return nextScreen.getNextScreen(gunther);
        }

        return nextScreen;
    }

    public Screen getNextScreen(Zone where) {
        float nexttop = top;
        float nextbottom = bottom;
        float nextleft = left;
        float nextright = right;
        switch(where) {
        case UP :
            nexttop += Main.height;
            break;
        case DOWN :
            nextbottom += Main.height;
            break;
        case LEFT :
            nextleft += Main.width;
            break;
        case RIGHT :
            nextright += Main.width;
            break;
        }

        return getScreen(nexttop, nextbottom, nextleft, nextright);
    }

    public boolean isInScreen(Gunther gunther) {
        Shape box = gunther.getBox();
        if (box.getCenterY() > top || box.getCenterY() < bottom ||
            box.getCenterX() < left || box.getCenterX() > right) {
            return true;
        }

        return false;
    }

    public void populateNeighbors() {
        this.getNextScreen(Zone.UP).populate();
        this.getNextScreen(Zone.DOWN).populate();
        this.getNextScreen(Zone.LEFT).populate();
        this.getNextScreen(Zone.RIGHT).populate();
    }

    public void populate() {
        if (populated) {
            return;
        }

        populated = true;
    }

    private Screen getScreen(float top, float bottom, float left, float right) {
        Screen nextScreen = ScreenMap.get(Screen.serialize(top, bottom,
                                                           left, right));
        if (nextScreen == null) {
            nextScreen = new Screen(top, bottom, left, right);
        }

        return nextScreen;
    }

    private String serialize() {
        return Screen.serialize(top, bottom, left, right);
    }

    private static String serialize(float top, float bottom, float left, float right) {
        return new String("T" + Math.round(top) + "B" + Math.round(bottom) +
                          "L" + Math.round(left) + "R" + Math.round(right));
    }
}
