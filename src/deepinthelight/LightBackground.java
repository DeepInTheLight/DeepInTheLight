package deepinthelight;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

/**
 * davedes' Tutorials
 * Alpha Map Lighting
 * http://slick.cokeandcode.com/wiki/doku.php?id=alpha_maps
 * 
 * @author davedes
 */
public class LightBackground {

    //number of tiles in our simple horizontal sprite sheet
    public static final int TILE_COUNT = 5;
    //width/height of tile in pixels
    public static final int TILE_SIZE = 40;
    //size of alpha map (for use with sprite sheet)
    public static final int ALPHA_MAP_SIZE = 256;
    //space after tile, before next tile
    public static final int TILE_SPACING = 2;
    //the "sprite sheet" or texture atlas image
    private Image spriteSheet;
    private Image background;
    private Image foregroundBlack;
    private Image spot;
    
    //the sub-images of our sprite sheet
    private Image[] tileSprites;
    //our 2D map array
    private Image[][] tileMap;
    //map size in tiles
    private int mapWidth, mapHeight;
    //our alpha map image; just a feathered black circle on a transparent background
    private Image alphaMap;
    private Random random = new Random();
    //our lights
    private List<Light> lights = new ArrayList<Light>();
    //a timer used for simple light scaling effect
    private long elapsed;
    //a shared instance of Color so we don't need to create a new one each frame
    private Color sharedColor = new Color(1f, 1f, 1f, 1f);

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
        spriteSheet = new Image("images/light.png", false, Image.FILTER_NEAREST);
        background = new Image("images/white.png", false);
        foregroundBlack = new Image("images/Black.png", false);
        spot = new Image("images/spot.png", true);

        //grab the tiles
        tileSprites = new Image[TILE_COUNT];
        for (int i = 0; i < tileSprites.length; i++) {
            tileSprites[i] = spriteSheet.getSubImage(i * (TILE_SIZE + TILE_SPACING), 0, TILE_SIZE, TILE_SIZE);
        }

        //grab the alpha map
        alphaMap= spriteSheet.getSubImage(0, TILE_SIZE + TILE_SPACING, ALPHA_MAP_SIZE, ALPHA_MAP_SIZE);
        //spot = spriteSheet.getSubImage(0, TILE_SIZE + TILE_SPACING, ALPHA_MAP_SIZE, ALPHA_MAP_SIZE);

        //reset the lighting
        lights.add(new Light(0, 0, 0.3f, new Color(0, 0, 1)));
    }

    Image randomTile() {
        int r = random.nextInt(100);
        if (r < 5) {
            return tileSprites[1 + random.nextInt(4)];
        } else {
            return tileSprites[0];
        }
    }

    void randomizeMap(GameContainer container) {
        // create the map
        mapWidth = container.getWidth() / TILE_SIZE + 1;
        mapHeight = container.getHeight() / TILE_SIZE + 1;
        tileMap = new Image[mapWidth][mapHeight];
        for (int x = 0; x < mapWidth; x++) {
            for (int y = 0; y < mapHeight; y++) {
                tileMap[x][y] = randomTile();
            }
        }
    }

    public void render(GameContainer container, StateBasedGame sbg, Graphics g) throws SlickException {
        //each light requires a new pass to blend with the previous lights
        //FPS will be affected with too many lights at once
        GamePlay gp = GamePlay.getGamePlay();
        for (int i = 0; i < lights.size(); i++) {
            Light light = lights.get(i);

            //set up our alpha map for the light
            //g.setDrawMode(Graphics.MODE_ALPHA_MAP);
            //clear the alpha map before we draw to it...
            //g.clearAlphaMap();

            //centre the light
            int alphaW = (int) (alphaMap.getWidth() * light.getScale());
            int alphaH = (int) (alphaMap.getHeight() * light.getScale());
            int alphaX = (int) (light.x - alphaW / 2f);
            int alphaY = (int) (light.y - alphaH / 2f);

            //we apply the light alpha here; RGB will be ignored
            sharedColor.a = light.alpha;

            //draw the alpha map
            //alphaMap.draw(alphaX, alphaY, alphaW, alphaH, sharedColor);

            //start blending in our tiles
           
            //g.setDrawMode(Graphics.MODE_ALPHA_MAP);
            //we'll clip to the alpha rectangle, since anything outside of it will be transparent
            g.setClip(alphaX, alphaY, alphaW, alphaH);
            
            
            //we'll use startUse/drawEmbedded/endUse for efficiency
//            spriteSheet.startUse();
            //after startUse, we can apply a new tint like so..
            //since we're in MODE_ALPHA_BLEND, the alpha value will be ignored (hence applying it above)
            //light.tint.bind();
            
            background.draw(0,0,Color.blue);
            //gp.gunther.render(gp.screenX, gp.screenY, g);

            //foregroundBlack.draw();
            g.setColor(Color.white);
            gp.gunther.render(gp.screenX, gp.screenY);
            
            spot.draw(0, 0, 1000f,1000f, Color.blue);
            
            g.setDrawMode(Graphics.MODE_ALPHA_MAP);
            background.draw();

            g.clearClip();
            
        }

        //reset the mode to normal before continuing..
        g.setDrawMode(Graphics.MODE_NORMAL);

        g.setColor(Color.white);
    }

    public void update(GameContainer container) {
        //elapsed += 5;

        //update all lights to have them smoothly scale
        for (int zi = 0; zi < lights.size(); zi++) {
            lights.get(zi).update(elapsed / 1000f);
        }

        //the last-added light will be the one under the mouse
        if (lights.size() > 0) {
            Light l = lights.get(0);
            l.x = container.getInput().getMouseX();
            l.y = container.getInput().getMouseY();
        }
    }

    public Light addLight(float x, float y, float scale, Color color) {
        Light l = new Light(x, y, scale, color);
        lights.add(l);
        return l;
    }
}