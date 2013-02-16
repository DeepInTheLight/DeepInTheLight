package deepinthelight;


import org.newdawn.slick.geom.Shape;

import java.util.ArrayList;
import java.util.List;

public class World {

    private final String imageUrl = "images/baleine.jpg";
    private Screen currentScreen;

    private List<Element> elements = new ArrayList<Element>();
    private List<Element> toDelete = new ArrayList<Element>();

    public World() {
        GamePlay gc = GamePlay.getGamePlay();
        currentScreen = Screen.init(gc.screenX, gc.screenY);
        currentScreen.populateNeighbors();
    }

    public ArrayList<Element> getElements() {
        return currentScreen.getAllElements();
    }

    public void update() {
        Gunther gunther = GamePlay.getGamePlay().gunther;
        //System.out.println("gunther pos : " + gunther.getBox().getCenterX() + ", " + gunther.getBox().getCenterY());
        if (!currentScreen.isInScreen(gunther)) {
            currentScreen = currentScreen.getNextScreen(gunther);
            currentScreen.populateNeighbors();
        }
    }

    public void queueForDeletion(Element e) {
        if ( elements.remove(e) ) {
            toDelete.add(e);
        }
    }
    
    public void deletePending() {
        toDelete.clear();
    }
}
