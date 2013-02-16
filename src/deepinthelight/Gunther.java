/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package deepinthelight;

/**
 *
 * @author peniblec
 */
public class Gunther extends Element {

    public enum Direction{
        NONE, UP, DOWN, LEFT, RIGHT
    }

    private int energyLeft;
    private Direction currentDirection;
    
    
    @Override
    public void update() {

    }

    @Override
    public void render() {

    }

    @Override
    public void collide() {

    }

    public void move(Direction dir) {
        currentDirection = dir;
        
    }
}
