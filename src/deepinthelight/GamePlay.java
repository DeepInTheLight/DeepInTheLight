package deepinthelight;

import deepinthelight.Element.Direction;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
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
    private LightBackground lbackground;

    private static GamePlay gp = null;

    public static GamePlay getGamePlay() {
        return GamePlay.gp;
    }
    private final boolean BOX_VISIBLE = false;
    
    int stateID = -1;

    public Gunther gunther;
    public World world;

    public Indicators uiIndicators;
    StateBasedGame sbg;
    public float screenX;
    public float screenY;
    public final float MIN_X_FROM_BORDER = Main.width/4;
    public final float MIN_Y_FROM_BORDER = Main.height/4;

    public int score;

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

        this.screenX = 0;
        this.screenY = 0;

        this.score = 0;

    }

    @Override
    public void render(GameContainer gc, StateBasedGame sbg, Graphics grphcs) throws SlickException {
        
//        for (Element e : this.world.getElements()) {
//            e.render(this.screenX, this.screenY);
//        }

//        this.gunther.render(this.screenX, this.screenY);
 
        lbackground.render(gc, sbg, grphcs);
        renderBoxes(gc);

        renderLigths();
        
        uiIndicators.render(grphcs);
    }

    @Override
    public void update(GameContainer gc, StateBasedGame sbg, int i) throws SlickException {
        this.sbg = sbg;
        manageInput(gc, sbg, i);

        this.gunther.update();

        world.update();

        for (Element e : this.world.getElements()) {
            e.update();
        }

        boolean needToStop = manageCollisions();

        if(needToStop) {
            this.gunther.moveBack();
        }

        checkScreenBorders();

    }

    private boolean manageCollisions() {
        boolean needToStop = false;
        for (Element e : this.world.getElements()) {
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
            gunther.move(Direction.RIGHTUP);
        } else if (isUP(input) && isLEFT(input)) {
            gunther.move(Direction.LEFTUP);
        } else if (isDOWN(input) && isRIGHT(input)) {
            gunther.move(Direction.RIGHTDOWN);
        } else if (isDOWN(input) && isLEFT(input)) {
            gunther.move(Direction.LEFTDOWN);
        } else if (isUP(input)) {
            gunther.move(Direction.UP);
        } else if (isDOWN(input)) {
            gunther.move(Direction.DOWN);
        } else if (isLEFT(input)) {
            gunther.move(Direction.LEFT);
        } else if (isRIGHT(input)) {
            gunther.move(Direction.RIGHT);
        } else {
            //gunther.move(Direction.NONE);
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
