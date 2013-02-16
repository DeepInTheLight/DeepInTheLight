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
    private final int BASE_ENERGY = 42;
    private final int ENERGY_DEC = 5;

    private Direction currentDirection = Direction.NONE;
    private final int SPEED = 5;

    @Override
    public void update() {
        energyLeft-= ENERGY_DEC;
    }

    @Override
    public void render() {

    }

    @Override
    public void collide() {

    }

    public void move(Direction dir) {
        currentDirection = dir;
        //TODO: actually move
    }
}
