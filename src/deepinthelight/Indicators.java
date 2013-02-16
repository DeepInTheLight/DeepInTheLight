/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package deepinthelight;

import java.awt.Font;
import java.awt.FontFormatException;
import java.io.IOException;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.font.effects.ColorEffect;
import org.newdawn.slick.font.effects.OutlineEffect;
import org.newdawn.slick.util.ResourceLoader;

/**
 *
 * @author jonas
 */
public class Indicators {
    private final static int X_OFFSET = 20;
    private final static int Y_OFFSET = 10;

    private UnicodeFont unicodeFont;

    public Indicators() throws SlickException {        
        Font javaFont = null;
        try {
            javaFont = Font.createFont(Font.TRUETYPE_FONT,
         ResourceLoader.getResourceAsStream("fonts/LCD_Mono_Normal.ttf"));
        } catch (FontFormatException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        unicodeFont = new UnicodeFont(javaFont, 40, false, false);
        unicodeFont.getEffects().add(new ColorEffect(java.awt.Color.white));
        unicodeFont.getEffects().add(new OutlineEffect(4, new java.awt.Color(0, 255, 100, 64)));
        unicodeFont.addGlyphs("0123456789 ptshealt:");
        unicodeFont.loadGlyphs();
    }

    public void render(Graphics gr) {
        String scoreText = "pts:" + GamePlay.getGamePlay().score;
        int textWidth = unicodeFont.getWidth(scoreText);
        
        String lifeText = "health:" + GamePlay.getGamePlay().gunther.getHealth();

        gr.setColor(Color.white);
        gr.setFont(unicodeFont);

        gr.drawString(scoreText, Main.width - X_OFFSET - textWidth, Y_OFFSET);
        gr.drawString(lifeText, X_OFFSET, Y_OFFSET);
    }
}
