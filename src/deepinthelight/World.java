package deepinthelight;


import org.newdawn.slick.geom.Shape;

import java.util.ArrayList;

public class World {

    private final String imageUrl = "images/baleine.jpg";
    private final int maxObstacleSize = 20;

    private ArrayList<Element> elements;

    public World() {
        elements = new ArrayList<Element>();
    }

    public ArrayList<Element> getElements() {
        return elements;
    }

    public void update() {
        int totalObstaclesSize = 0;
        for (Element el : elements) {
            if (isInGenScreen(el)) {
                totalObstaclesSize += el.getSize();
            }
        }

        if (totalObstaclesSize < maxObstacleSize) {
            elements.add(genObstacles(GamePlay.getGamePlay().gunther));
        }
    }

    public Obstacle genObstacles(Gunther gunther) {
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

    private boolean isInGenScreen(Element el) {
        GamePlay gp = GamePlay.getGamePlay();
        Shape box = el.getBox();
        float top = gp.screenY - Main.height;
        float bottom = gp.screenY + 2*Main.height;
        float left = gp.screenX - Main.width;
        float right = gp.screenX + Main.width;
        if (top > box.getCenterY() && bottom < box.getCenterY() &&
            right > box.getCenterX() && left < box.getCenterX()) {
            return true;
        }

        return false;
    }

}
