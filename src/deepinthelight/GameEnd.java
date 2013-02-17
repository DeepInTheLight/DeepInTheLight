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
import java.awt.Font;
import java.awt.FontFormatException;
import java.io.IOException;
import org.newdawn.slick.*;
import org.newdawn.slick.font.effects.ColorEffect;
import org.newdawn.slick.font.effects.OutlineEffect;
import org.newdawn.slick.gui.AbstractComponent;
import org.newdawn.slick.gui.ComponentListener;
import org.newdawn.slick.gui.MouseOverArea;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.util.ResourceLoader;

public class GameEnd extends BasicGameState implements ComponentListener {

    int stateID = -1;
    boolean newEnding;
    MouseOverArea restartButton;
    StateBasedGame sbg;
    Image img;
    private UnicodeFont unicodeFont;
    float restartX =  Main.width/2 - 50;
    float restartY = Main.height/2;

    GameEnd(int stateID) {
        this.stateID = stateID;
    }

    @Override
    public int getID() {
        newEnding = true;
        return stateID;

    }

    public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
        img = new Image("images/Black.png");
        restartButton = new MouseOverArea(gc, img, (int)restartX, (int)restartY, 100, 40, this);
        restartButton.setMouseOverColor(new Color(90, 223, 255));
        restartButton.setNormalColor(Color.white);
        
        Font javaFont = null;
        try {
            javaFont = Font.createFont(Font.TRUETYPE_FONT,
         //ResourceLoader.getResourceAsStream("fonts/LCD_Mono_Normal.ttf"));
         ResourceLoader.getResourceAsStream("fonts/Quicksand_Book.otf"));
        } catch (FontFormatException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        unicodeFont = new UnicodeFont(javaFont, 30, false, false);
        unicodeFont.getEffects().add(new ColorEffect(java.awt.Color.white));
        unicodeFont.getEffects().add(new OutlineEffect(2, new java.awt.Color(0, 255, 100, 64)));
        unicodeFont.addGlyphs("0123456789 RESTARScore:");
        unicodeFont.loadGlyphs();
    }

    public void render(GameContainer gc, StateBasedGame sbg, Graphics gr) throws SlickException {
        restartButton.render(gc, gr);
        img.draw(0,0);
        String restartStr = "RESTART";
        int textWidth = unicodeFont.getWidth(restartStr);

        gr.setColor(Color.white);
        gr.setFont(unicodeFont);

        gr.drawString(restartStr,restartX, restartY);
        
        showInformation(gr);
       
    }

    public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
        this.sbg = sbg;
         GamePlay.getGamePlay().init(gc, sbg);
    }

    private void showInformation(Graphics gr) {
        int p = GamePlay.getGamePlay().score;
        gr.setColor(Color.white);
        gr.drawString("Score " + p, restartX +10, restartY - 100);
    }

    public void componentActivated(AbstractComponent ac) {
        if (ac == restartButton) {
            sbg.enterState(2);
        }
    }
}
