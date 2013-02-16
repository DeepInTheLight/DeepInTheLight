/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package deepinthelight;

import org.newdawn.slick.Color;


/** Describes a single point light. */
public class Light {
    /** The position of the light */
    float x, y;
    /** The RGB tint of the light, alpha is ignored */
    Color tint; 
    /** The alpha value of the light, default 1.0 (100%) */
    float alpha;
    /** The amount to scale the light (use 1.0 for default size). */
    private float scale;
    //original scale
    private float scaleOrig;

    public Light(float x, float y, float scale, Color tint) {
        this.x = x;
        this.y = y;
        this.scale = scaleOrig = scale;
        this.alpha = 1f;
        this.tint = tint;
    }

    public Light(float x, float y, float scale) {
            this(x, y, scale, Color.blue);
    }

    public void update(float time) {
        //effect: scale the light slowly using a sin func
        //scale = scaleOrig + 1f + .5f*(float)Math.sin(time);
    }

    public void setOriginScale(float scale) {
        scaleOrig = scale;
    }
    
    public float getOriginScale(float scale) {
        return scaleOrig;
    }

    public Color getTint() {
        return tint;
    }

    public void setPositiont(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public float getScale() {
        return scale;
    }

    public void setScale(float scale) {
        this.scale = scale;
    }
    
    
        
}
