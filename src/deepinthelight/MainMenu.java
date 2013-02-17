/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package deepinthelight;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.gui.AbstractComponent;
import org.newdawn.slick.gui.ComponentListener;
import org.newdawn.slick.gui.MouseOverArea;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

/**
 *
 * @author jonas
 */
public class MainMenu extends BasicGameState implements ComponentListener {
    final int nbBouton = 1;
    int stateID = -1;
    Image img;
//    CustomMouseOverArea button;
    MouseOverArea startButton;
    StateBasedGame sbg;
    //List<String> playersSelected = new ArrayList<String>();
    Image play;

    @Override
    public int getID() {
        return stateID;
    }

    MainMenu(int stateID) {
        this.stateID = stateID;//stateID;
    }
    
    @Override
    public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
        img = new Image("images/menu/menuBackground.png");

        Image start = new Image("images/menu/play.png");

        startButton = new MouseOverArea(gc, start, Main.width/2 - start.getWidth()/2, 450, this);
        startButton.setMouseOverColor(Color.red);
        startButton.setNormalColor(Color.white);
    }

    public void render(GameContainer gc, StateBasedGame sbg, Graphics gr) throws SlickException {
        //init(gc, sbg);
        img.draw(0, 0,Main.width,Main.height); 

        gr.setColor(Color.white);
        startButton.render(gc, gr);
    }

    public void update(GameContainer gc, StateBasedGame sbg, int i) throws SlickException {
        this.sbg = sbg;
    }

    @Override
    public void componentActivated(AbstractComponent source) { //methode de l'interface ComponentListener

        if (source == startButton) {
            sbg.enterState(Main.GAMEPLAY);
        }
    }
    
//    class CustomMouseOverArea extends MouseOverArea {
//
//        private String perso;
//        private boolean selected = false;
//
//        public CustomMouseOverArea(GUIContext container, Image image, int x, int y, ComponentListener listener) {
//            super(container, image, x, y, listener);
//
//            perso = image.getResourceReference();
//        }
//
//        public String getRessource() {
//            return perso;
//        }
//
//        public boolean isSelected() {
//            return selected;
//        }
//
//        public void setSelected(boolean selected) {
//            this.selected = selected;
//        }
//    }
}
