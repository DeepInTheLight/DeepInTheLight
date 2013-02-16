package deepinthelight;


import org.newdawn.slick.geom.Shape;

import java.util.ArrayList;
import java.util.List;

public class World {

    private final String imageUrl = "images/baleine.jpg";
    private Screen currentScreen;

    private ArrayList<Element> elements = new ArrayList<Element>();
    private ArrayList<Element> allElements = new ArrayList<Element>();

    public World() {
        GamePlay gc = GamePlay.getGamePlay();
        currentScreen = Screen.init(gc.screenX, gc.screenY);
        currentScreen.populateNeighbors();
        allElements.clear();
        allElements.addAll(elements);
        allElements.addAll(currentScreen.getAllElements());
    }

    public ArrayList<Element> getElements() {
        return allElements;
    }

    public void update() {
        Gunther gunther = GamePlay.getGamePlay().gunther;
        //System.out.println("gunther pos : " + gunther.getBox().getCenterX() + ", " + gunther.getBox().getCenterY());
        if (!currentScreen.isInScreen(gunther)) {
            currentScreen = currentScreen.getNextScreen(gunther);
            currentScreen.populateNeighbors();
            allElements.clear();
            allElements.addAll(elements);
            allElements.addAll(currentScreen.getAllElements());
        } else if (currentScreen.elementChanged()) {
            allElements.clear();
            allElements.addAll(elements);
            allElements.addAll(currentScreen.getAllElements());
        }
    }
}
