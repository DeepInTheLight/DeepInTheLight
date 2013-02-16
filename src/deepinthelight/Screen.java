package deepinthelight;

import java.util.HashMap;

public class Screen {

    public static HashMap<String, Screen> ScreenMap = null;
    private final int maxObstacleSize = 20;
    private int obstacleSize = 0;

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
