/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package deepinthelight;

/**
 *
 * @author mica
 */
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
import org.newdawn.slick.*;
import org.newdawn.slick.gui.AbstractComponent;
import org.newdawn.slick.gui.ComponentListener;
import org.newdawn.slick.gui.MouseOverArea;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class GameEnd extends BasicGameState implements ComponentListener {

    int stateID = -1;
    boolean newEnding;
    MouseOverArea restartButton;
    StateBasedGame sbg;
    Image img;

    GameEnd(int stateID) {
        this.stateID = stateID;
    }

    @Override
    public int getID() {
        newEnding = true;
        return stateID;

    }

    public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
        restartButton = new MouseOverArea(gc, new Image("images/menuEnd/restart.png"), 875, 450, this);
        restartButton.setMouseOverColor(new Color(90, 223, 255));
        restartButton.setNormalColor(Color.white);

        img = new Image("images/Black.png");
    }

    public void render(GameContainer gc, StateBasedGame sbg, Graphics gr) throws SlickException {
        img.draw(0,0);
        GamePlay.getGamePlay().init(gc, sbg);
        showInformation(gr);
        restartButton.render(gc, gr);
       
    }

    public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
        this.sbg = sbg;
    }

    private void showInformation(Graphics gr) {
        int p = GamePlay.getGamePlay().score;
        gr.setColor(Color.white);
        gr.drawString("Score " + p, Main.width/2-50, Main.height/2);
    }

    public void componentActivated(AbstractComponent ac) {
        if (ac == restartButton) {
            sbg.enterState(2);
        }
    }
}
