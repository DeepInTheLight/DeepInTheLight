/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package deepinthelight;

import java.awt.Font;
import java.awt.FontFormatException;
import java.io.IOException;
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.font.effects.ColorEffect;
import org.newdawn.slick.font.effects.OutlineEffect;
import org.newdawn.slick.gui.AbstractComponent;
import org.newdawn.slick.gui.ComponentListener;
import org.newdawn.slick.gui.MouseOverArea;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.util.ResourceLoader;

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
    private LightBackground lbackground;
    //List<String> playersSelected = new ArrayList<String>();
    Image play;
    private UnicodeFont unicodeFont;
    int startX = Main.width/2 - 60;
    int startY = Main.height/2 + 70;

    @Override
    public int getID() {
        return stateID;
    }

    MainMenu(int stateID) {
        this.stateID = stateID;//stateID;
        lbackground = new LightBackground();
        //GamePlay.getGamePlay();
    }
    
    @Override
    public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
        lbackground.init(gc);

        Image start = new Image("images/menu/play.png");

        startButton = new MouseOverArea(gc, start, startX, startY, 140, 40, this);
        startButton.setMouseOverColor(Color.blue);
        startButton.setNormalColor(Color.white);
        
        Font javaFont = null;
        try {
            javaFont = Font.createFont(Font.TRUETYPE_FONT,
             //ResourceLoader.getResourceAsStream("fonts/LCD_Mono_Normal.ttf"));
             ResourceLoader.getResourceAsStream("fonts/Quicksand_Bold.otf"));
        } catch (FontFormatException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        
        unicodeFont = new UnicodeFont(javaFont, 40, false, false);
        unicodeFont.getEffects().add(new ColorEffect(java.awt.Color.white));
        unicodeFont.getEffects().add(new OutlineEffect(2, new java.awt.Color(0, 255, 100, 64)));
        unicodeFont.addGlyphs("START");
        unicodeFont.loadGlyphs();
        
    }

    public void render(GameContainer gc, StateBasedGame sbg, Graphics gr) throws SlickException {
        startButton.render(gc, gr);
        lbackground.render(gc, sbg, gr);  
        gr.setColor(Color.white);
       
        
        String startStr = "START";
        int textWidth = unicodeFont.getWidth(startStr);
        

        gr.setColor(Color.white);
        gr.setFont(unicodeFont);

        gr.drawString(startStr, Main.width/2 - 60, Main.height/2 + 70);
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
    
    @Override
    public void mouseMoved(int oldx, int oldy, int newx, int newy) {
      super.mouseMoved(oldx, oldy, newx, newy);
      GamePlay.getGamePlay().gunther.eat();  } 
}
