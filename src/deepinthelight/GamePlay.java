package deepinthelight;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

/**
 * davedes' Tutorials
 * Alpha Map Lighting
 * http://slick.cokeandcode.com/wiki/doku.php?id=alpha_maps
 * 
 * @author davedes
 */
public class GamePlay extends BasicGameState {

//	//number of tiles in our simple horizontal sprite sheet
//	public static final int TILE_COUNT = 5;
//	
//	//width/height of tile in pixels
//	public static final int TILE_SIZE = 40;
//	
//	//size of alpha map (for use with sprite sheet)
//	public static final int ALPHA_MAP_SIZE = 256;
//	
//	//space after tile, before next tile
//	public static final int TILE_SPACING = 2;
//	
//	//the "sprite sheet" or texture atlas image
//	private Image spriteSheet;
//	
//	//the sub-images of our sprite sheet
//	private Image[] tileSprites;
//	
//	//our 2D map array
//	private Image[][] tileMap;
//
//	//map size in tiles
//	private int mapWidth, mapHeight;
//	
//	//our alpha map image; just a feathered black circle on a transparent background
//	private Image alphaMap;
//	
//	private Random random = new Random();
//	
//	//our lights
//	private List<Light> lights = new ArrayList<Light>();
//	
//	//a timer used for simple light scaling effect
//	private long elapsed;
//	
//	//a shared instance of Color so we don't need to create a new one each frame
//	private Color sharedColor = new Color(1f, 1f, 1f, 1f);
    private LightBackground lbackground;

    @Override
    public int getID() {
        return 0;
    }

    public GamePlay(int a) {
        lbackground = new LightBackground();
    }

    public void init(GameContainer container, StateBasedGame sbg) throws SlickException {
        lbackground.init(container);
    }

    public void render(GameContainer container, StateBasedGame sbg, Graphics g) throws SlickException {
        lbackground.render(container, sbg, g);
    }

    public void update(GameContainer container, StateBasedGame sbg, int i) throws SlickException {
        lbackground.update(container);
    }
    
}