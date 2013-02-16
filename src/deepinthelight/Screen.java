package deepinthelight;

import java.util.ArrayList;
import java.util.HashMap;

import org.newdawn.slick.geom.Shape;

public class Screen {

    public static HashMap<String, Screen> ScreenMap = null;
    private final int maxObstacleSize = 10;
    private int obstacleSize = 0;
    private boolean populated;
    private ArrayList<Element> elements;

    public enum Zone {
        UP, DOWN, LEFT, RIGHT, UPLEFT, UPRIGHT, DOWNLEFT, DOWNRIGHT
    }

    private float x;
    private float y;

    private Screen(float x, float y) {
        this.x = x;
        this.y = y;
        populated = false;
        elements = new ArrayList<Element>();
        ScreenMap.put(this.serialize(), this);
    }

    public ArrayList<Element> getAllElements() {
        ArrayList<Element> allElements = new ArrayList<Element>();
        int i = 0;
        for (Zone where : Zone.values()) {
            allElements.addAll(getNextScreen(where).getElements());
        }

        return allElements;
    }

    public ArrayList<Element> getElements() {
        return elements;
    }

    public static Screen init(float screenX, float screenY) {
        if (ScreenMap != null) {
            return null;
        }

        ScreenMap = new HashMap<String, Screen>(100);
        return new Screen(screenY, screenX);
    }

    public Screen getNextScreen(Gunther gunther) {
        float top = y;
        float bottom = y + Main.height;
        float left = x;
        float right = x + Main.width;
        Shape box = gunther.getBox();
        Screen nextScreen = null;
        if (box.getCenterY() > top) {
            if (box.getCenterX() < left) {
                nextScreen = getNextScreen(Zone.UPLEFT);
            } else if (box.getCenterX() > right) {
                nextScreen = getNextScreen(Zone.UPRIGHT);
            }

            nextScreen = getNextScreen(Zone.UP);
        } else if (box.getCenterY() < bottom) {
            if (box.getCenterX() < left) {
                nextScreen = getNextScreen(Zone.DOWNLEFT);
            } else if (box.getCenterX() > right) {
                nextScreen = getNextScreen(Zone.DOWNRIGHT);
            }

            nextScreen = getNextScreen(Zone.DOWN);
        } else if (box.getCenterX() < left) {
            nextScreen = getNextScreen(Zone.LEFT);
        } else if (box.getCenterX() > right) {
            nextScreen = getNextScreen(Zone.RIGHT);
        }

        return nextScreen;
    }

    public Screen getNextScreen(Zone where) {
        float nextx = x;
        float nexty = y;
        switch(where) {
        case UPLEFT:
            nextx -= Main.width;
        case UP :
            nexty -= Main.height;
            break;
        case DOWNRIGHT :
            nextx += Main.width;
        case DOWN :
            nexty += Main.height;
            break;
        case DOWNLEFT:
            nexty += Main.height;
        case LEFT :
            nextx -= Main.width;
            break;
        case UPRIGHT :
            nexty -= Main.height;
        case RIGHT :
            nextx += Main.width;
            break;
        }

        return getScreen(nextx, nexty);
    }

    public boolean isInScreen(Gunther gunther) {
        float top = y;
        float bottom = y + Main.height;
        float left = x;
        float right = x + Main.width;
        Shape box = gunther.getBox();
        if (box.getCenterY() > top || box.getCenterY() < bottom ||
            box.getCenterX() < left || box.getCenterX() > right) {
            return true;
        }

        return false;
    }

    public void populateNeighbors() {
        for (Zone where : Zone.values()) {
            this.getNextScreen(where).populate();
        }
    }

    public void populate() {
        if (populated) {
            return;
        }

        populated = true;
    }

    private Screen getScreen(float x, float y) {
        Screen nextScreen = ScreenMap.get(Screen.serialize(x, y));
        if (nextScreen == null) {
            nextScreen = new Screen(x, y);
        }

        return nextScreen;
    }

    private String serialize() {
        return Screen.serialize(x, y);
    }

    private static String serialize(float x, float y) {
        return new String("X" + Math.round(x) + "Y" + Math.round(y));
    }
}
