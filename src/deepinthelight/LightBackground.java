package deepinthelight;

import java.util.ArrayList;
import java.util.List;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

public class LightBackground {

    public static final int MIN_RADIUS = 150;
    
    //number of tiles in our simple horizontal sprite sheet
    public static final int TILE_COUNT = 5;
    //width/height of tile in pixels
    public static final int TILE_SIZE = 40;
    //size of alpha map (for use with sprite sheet)
    public static final int ALPHA_MAP_SIZE = 256;
    //space after tile, before next tile
    public static final int TILE_SPACING = 2;
    //the "sprite sheet" or texture atlas image
    private Image background;
    private Image spot;
    
    //our lights
    private List<Light> lights = new ArrayList<Light>();


    public int getID() {
        return 0;
    }

    public LightBackground() {
    }

    public void init(GameContainer container) throws SlickException {

        //To reduce texture binds, our alpha map and tilesheet will be in the same texture
        //Most games will implement their own SpriteSheet class, but for simplicity's sake:
        //map tiles are in a horizontal row starting at (0, 0)
        //alpha map is located below the tiles, at (0, TILE_SIZE+TILE_SPACING)
        //spriteSheet = new Image("images/light.png", false, Image.FILTER_NEAREST);
        background = new Image("images/white.png", false);
        spot = new Image("images/spot.png", true);

        lights.add(new Light(0, 0, 1f, new Color(0, 0, 1)));
    }

    public void render(GameContainer container, StateBasedGame sbg, Graphics g) throws SlickException {
        GamePlay gp = GamePlay.getGamePlay();

        float enregy = gp.gunther.getEnergyLeft();
        float radius = gp.gunther.RADIUS;
        float lightRadius = radius + enregy;
        float x = gp.gunther.getBox().getCenterX();
        float y = gp.gunther.getBox().getCenterY();

        //Draw the clip
        g.setClip((int) Math.ceil( (double)(x - lightRadius - MIN_RADIUS/2 - gp.screenX)+10),(int) Math.ceil( (double)(y - lightRadius -MIN_RADIUS/2 - gp.screenY)+10),(int)Math.floor((double)(lightRadius*2f + MIN_RADIUS))-25,(int) Math.floor((double)(lightRadius*2f + MIN_RADIUS))-25);

        //Draw the background
        background.draw(0,0,Color.blue);

        //Dessiner les elements
        for (Element e : gp.world.getElements()) {
            e.render(gp.screenX, gp.screenY);
        }
        
        //render gunther
        gp.gunther.render(gp.screenX, gp.screenY);

        //draw the spot
        spot.draw(x - lightRadius - gp.screenX - MIN_RADIUS/2, y - lightRadius - gp.screenY - MIN_RADIUS/2, lightRadius*2f + MIN_RADIUS, lightRadius*2f + MIN_RADIUS, Color.blue);

        g.setDrawMode(Graphics.MODE_ALPHA_BLEND);
        
        g.clearClip();

        //reset the mode to normal before continuing..
        g.setDrawMode(Graphics.MODE_NORMAL);

        g.setColor(Color.white);
    }

    public Light addLight(float x, float y, float scale, Color color) {
        Light l = new Light(x, y, scale, color);
        lights.add(l);
        return l;
    }
}