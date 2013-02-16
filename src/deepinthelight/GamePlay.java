package deepinthelight;

import deepinthelight.Gunther.Direction;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;


public class GamePlay extends BasicGameState {

    private static GamePlay gp = null;
    public static GamePlay getGamePlay() {
        return GamePlay.gp;
    }


    int stateID = -1;

    public Gunther gunther;
    public World world;

    public float screenX;
    public float screenY;


    GamePlay(int stateID) {
        this.stateID = stateID;
    }

    @Override
    public int getID() {
        return stateID;
    }

    public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
        GamePlay.gp = this;

        this.gunther = new Gunther();
        this.world = new World();

        this.screenX = 0;
        this.screenY = 0;

    }

    public void render(GameContainer gc, StateBasedGame sbg, Graphics grphcs) throws SlickException {
        for(Element e : this.world.getElements()) {
            e.render(this.screenX, this.screenY);
        }

        this.gunther.render(this.screenX, this.screenY);
    }

    public void update(GameContainer gc, StateBasedGame sbg, int i) throws SlickException {
        manageInput(gc, sbg, i);

        manageCollisions();

    }

    private void manageCollisions() {
        for(Element e : this.world.getElements()) {
            if(e.getBox().intersects(gunther.getBox())) {
                e.collide();
            }
        }
    }

    private void manageInput(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
        Input input = gc.getInput();

        if (input.isKeyDown(Input.KEY_UP) && input.isKeyDown(Input.KEY_RIGHT)) {
            gunther.move(Direction.RIGHTUP);
        } else if (input.isKeyDown(Input.KEY_UP) && input.isKeyDown(Input.KEY_LEFT)) {
            gunther.move(Direction.LEFTUP);
        } else if (input.isKeyDown(Input.KEY_DOWN) && input.isKeyDown(Input.KEY_RIGHT)) {
            gunther.move(Direction.RIGHTDOWN);
        } else if (input.isKeyDown(Input.KEY_DOWN) && input.isKeyDown(Input.KEY_LEFT)) {
            gunther.move(Direction.LEFTDOWN);
        } else if (input.isKeyDown(Input.KEY_UP)) {
            gunther.move(Direction.UP);
        } else if (input.isKeyDown(Input.KEY_DOWN)) {
            gunther.move(Direction.DOWN);
        } else if (input.isKeyDown(Input.KEY_LEFT)) {
            gunther.move(Direction.LEFT);
        } else if (input.isKeyDown(Input.KEY_RIGHT)) {
            gunther.move(Direction.RIGHT);
        } else {
            gunther.move(Direction.NONE);
        }



    }

}
