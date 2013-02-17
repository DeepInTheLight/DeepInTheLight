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
        currentScreen.populateNeighbors(2); // Populating 25 square might be a little slow, and we
                                            // don't really need it there IMO
        allElements.clear();
        allElements.addAll(elements);
        allElements.addAll(currentScreen.getAllElements(2));
    }

    public ArrayList<Element> getElements() {
        return allElements;
    }

    public void update(int delta) {
        Gunther gunther = GamePlay.getGamePlay().gunther;
        //System.out.println("gunther pos : " + gunther.getBox().getCenterX() + ", " + gunther.getBox().getCenterY());
        if (!currentScreen.isInScreen(gunther)) {
            currentScreen = currentScreen.getNextScreen(gunther);
            currentScreen.populateNeighbors(2);
            allElements.clear();
            allElements.addAll(elements);
            allElements.addAll(currentScreen.getAllElements(2));
        } else if (currentScreen.elementChanged()) {
            allElements.clear();
            allElements.addAll(elements);
            allElements.addAll(currentScreen.getAllElements(2));
        }
    }

    public Screen getCurrentScreen() {
        return currentScreen;
    }
}
