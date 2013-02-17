package deepinthelight;

import deepinthelight.Element.Direction;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.particles.ParticleIO;
import org.newdawn.slick.particles.ParticleSystem;
import org.newdawn.slick.particles.ConfigurableEmitter;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import java.io.File;
import java.util.ArrayList;
import org.newdawn.slick.Image;
/**
 * davedes' Tutorials
 * Alpha Map Lighting
 * http://slick.cokeandcode.com/wiki/doku.php?id=alpha_maps
 * 
 * @author davedes
 */
public class GamePlay extends BasicGameState {
    private LightBackground lbackground;

    private static GamePlay gp = null;

    public static GamePlay getGamePlay() {
        return GamePlay.gp;
    }
    private final boolean BOX_VISIBLE = false;
    
    int stateID = -1;

    public Gunther gunther;
    public ArrayList<Element> bonus = new ArrayList<Element>();
    public World world;

    public Image tuto;
    public Image tutoFish1;
    public Image tutoFish2;
    public Image tutoFish3;
    public Image tutoFish4;
    public Image tutoFish5;
    public Image tutoFish6;

    public Indicators uiIndicators;
    StateBasedGame sbg;
    public float screenX;
    public float screenY;
    public final float MIN_X_FROM_BORDER = Main.width/4;
    public final float MIN_Y_FROM_BORDER = Main.height/4;
    public boolean activated = true;
    public int score;

    public ParticleSystem psystem;
    private ParticleIO particleIO;
    private final int emitterNb = 18;
    private ConfigurableEmitter[] emitters;

    GamePlay(int stateID) {
        this.stateID = stateID;
        lbackground = new LightBackground();
    }

    @Override
    public int getID() {
        return this.stateID;
    }

    @Override
    public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
        lbackground.init(gc);
        GamePlay.gp = this;

        this.gunther = new Gunther();
        this.world = new World();
        this.uiIndicators = new Indicators();

        this.tuto = new Image("images/tuto.png");
        this.tutoFish1 = new Image("images/bonus/foodfish1.png");
        this.tutoFish2 = new Image("images/bonus/foodfish2.png");
        this.tutoFish3 = new Image("images/bonus/foodfish3.png");
        this.tutoFish4 = new Image("images/malus/danger1.png");
        this.tutoFish5 = new Image("images/malus/danger2.png");
        this.tutoFish6 = new Image("images/malus/danger3.png");
  

        this.screenX = 0;
        this.screenY = 0;

        this.score = 0;
        psystem = new ParticleSystem("images/particles/bubble15x15.png");
        emitters = new ConfigurableEmitter[emitterNb];
        try {
            File xmlFile = new File("effects/bubble.xml");
            for (int i = 0; i < emitterNb; i++)  {
                emitters[i] = ParticleIO.loadEmitter(xmlFile);
                emitters[i].setPosition(2*Main.width - (int)Math.round(2*i*Main.width/emitterNb), 720);
                psystem.addEmitter(emitters[i]);
            }
        } catch (Exception e) {
            System.out.println("Exception: " +e.getMessage());
            e.printStackTrace();
            System.exit(0);
        }

        psystem.setBlendingMode(ParticleSystem.BLEND_ADDITIVE);
        psystem.setUsePoints(false);
    }

    @Override
    public void render(GameContainer gc, StateBasedGame sbg, Graphics grphcs) throws SlickException {
        
//        for (Element e : this.world.getElements()) {
//            e.render(this.screenX, this.screenY);
//        }

//        this.gunther.render(this.screenX, this.screenY);

 
        lbackground.render(gc, sbg, grphcs);

        renderLigths();

        renderBoxes(gc);
        
        uiIndicators.render(grphcs);
        //psystem.render();
    }

    @Override
    public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
        this.sbg = sbg;

        manageInput(gc, sbg, delta);
        this.gunther.update(delta);
        world.update(delta);

        for (Element e : this.world.getElements()) {
            e.update(delta);
        }

        boolean needToStop = manageCollisions();

        if(needToStop) {
            this.gunther.moveBack();
        }

        updatePSystem(delta);
//        float screenDiffX = screenX;
//        float screenDiffY = screenY;
//        checkScreenBorders();
//        screenDiffX -= screenX;
//        screenDiffY -= screenY;
//        
//        for (int j = 0; j < emitterNb; j++) {
//            psystem.moveAll(emitters[j], screenDiffX, screenDiffY);
//        }
//
//        psystem.update(i);

    }
    
    public void updatePSystem(int delta){
        float screenDiffX = screenX;
        float screenDiffY = screenY;
        checkScreenBorders();
        screenDiffX -= screenX;
        screenDiffY -= screenY;
        
        for (int j = 0; j < emitterNb; j++) {
            psystem.moveAll(emitters[j], screenDiffX, screenDiffY);
        }

        psystem.update(delta);
    }

    private boolean manageCollisions() {
        boolean needToStop = false;
        for (Element e : this.world.getElements()) {
            if (e.getScreen() != this.world.getCurrentScreen() && e.getSize() == 1) {
                continue;
            }
            if (e.getBox().intersects(gunther.getBox())) {
                boolean b = e.collide();
                needToStop = b ? true : needToStop;
            }
        }

        return needToStop;
    }

    private void checkScreenBorders() {
        float guntherX = gunther.getBox().getCenterX();
        float guntherY = gunther.getBox().getCenterY();
        
        if ( guntherX - MIN_X_FROM_BORDER < screenX ) {
            screenX = guntherX - MIN_X_FROM_BORDER;
        }
        else if ( guntherX + MIN_X_FROM_BORDER > screenX + Main.width ) {
            screenX = guntherX + MIN_X_FROM_BORDER - Main.width;
        }

        if ( guntherY - MIN_Y_FROM_BORDER < screenY ) {
            screenY = guntherY - MIN_Y_FROM_BORDER;
        }
        else if ( guntherY + MIN_Y_FROM_BORDER > screenY + Main.height ) {
            screenY = guntherY + MIN_Y_FROM_BORDER - Main.height;
        }
    }

    private void manageInput(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
        Input input = gc.getInput();

        if (isUP(input) && isRIGHT(input)) {
            gunther.move(Direction.RIGHTUP, delta);
        } else if (isUP(input) && isLEFT(input)) {
            gunther.move(Direction.LEFTUP, delta);
        } else if (isDOWN(input) && isRIGHT(input)) {
            gunther.move(Direction.RIGHTDOWN, delta);
        } else if (isDOWN(input) && isLEFT(input)) {
            gunther.move(Direction.LEFTDOWN, delta);
        } else if (isUP(input)) {
            gunther.move(Direction.UP, delta);
        } else if (isDOWN(input)) {
            gunther.move(Direction.DOWN, delta);
        } else if (isLEFT(input)) {
            gunther.move(Direction.LEFT, delta);
        } else if (isRIGHT(input)) {
            gunther.move(Direction.RIGHT, delta);
        } else if (isSUICIDE(input)) {
            gunther.die();
        }
            //gunther.move(Direction.NONE);
     }

    private boolean isSUICIDE(Input input) {
        if (input.isKeyDown(Input.KEY_K)) {
            return true;
        } else {
            return false;
        }
    }
    
    private boolean isUP(Input input){
        if(input.isKeyDown(Input.KEY_UP) || input.isKeyDown(Input.KEY_Z) || input.isKeyDown(Input.KEY_W)) {
            return true;
        } else {
            return false;
        }
    }

    private boolean isDOWN(Input input){
        if(input.isKeyDown(Input.KEY_DOWN) || input.isKeyDown(Input.KEY_S)) {
            return true;
        } else {
            return false;
        }
    }

    private boolean isRIGHT(Input input){
        if(input.isKeyDown(Input.KEY_RIGHT) || input.isKeyDown(Input.KEY_D)) {
            return true;
        } else {
            return false;
        }
    }

    private boolean isLEFT(Input input){
        if(input.isKeyDown(Input.KEY_LEFT) || input.isKeyDown(Input.KEY_Q) || input.isKeyDown(Input.KEY_A)) {
            return true;
        } else {
            return false;
        }
    }

    private void renderBoxes(GameContainer gc) {
        if (BOX_VISIBLE) {
            Color c = gc.getGraphics().getColor();
            gc.getGraphics().setColor(new Color(255, 0, 0, 100));

            gc.getGraphics().translate(-this.screenX, -this.screenY);

            gc.getGraphics().fill(this.gunther.getBox());

            for (Element e : this.world.getElements()) {
                gc.getGraphics().fill(e.getBox());
            }

            gc.getGraphics().translate(this.screenX, this.screenY);
            gc.getGraphics().setColor(c);
        }
    }

    private void renderLigths() {
        for (Element e : this.world.getElements()) {
            if(e instanceof BonusFish) {
                ((BonusFish) e).drawLight(screenX, screenY);
            }
        }
    }
}
