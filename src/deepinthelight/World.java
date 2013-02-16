package deepinthelight;


import org.newdawn.slick.geom.Shape;

import java.util.ArrayList;

public class World {

    private final String imageUrl = "images/baleine.jpg";
    private Screen currentScreen;

    private ArrayList<Element> elements;

    public World() {
        GamePlay gc = GamePlay.getGamePlay();
        elements = new ArrayList<Element>();
        currentScreen = Screen.init(gc.screenX, gc.screenY);
    }

    public ArrayList<Element> getElements() {
        return elements;
    }

    public void update() {
        Gunther gunther = GamePlay.getGamePlay().gunther;
        if (!currentScreen.isInScreen(gunther)) {
            currentScreen = currentScreen.getNextScreen(gunther);
            currentScreen.populateNeighbors();
        }
    }

    private Obstacle genObstacles(Gunther gunther) {
        int centerX = 0;
        int centerY = 0;
        Obstacle newObstacle = null;
        try {
            newObstacle = new Obstacle(imageUrl, centerX, centerY, 1);
        } catch (Exception ex) {
            System.out.println("Bad obstacle image! " + ex.getMessage());
        }

        return newObstacle;
    }

}
