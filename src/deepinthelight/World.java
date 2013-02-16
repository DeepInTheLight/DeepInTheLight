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
        currentScreen.populateNeighbors();
    }

    public ArrayList<Element> getElements() {
        return currentScreen.getAllElements();
    }

    public void update() {
        Gunther gunther = GamePlay.getGamePlay().gunther;
        if (!currentScreen.isInScreen(gunther)) {
            currentScreen = currentScreen.getNextScreen(gunther);
            currentScreen.populateNeighbors();
        }
    }
}
