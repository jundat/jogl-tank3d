/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package myjogl.gameobjects;

import java.util.Random;
import myjogl.utils.*;

/**
 *
 * @author Jundat
 */
public class TankAI extends Tank {
    //

    public static Random TANK_RANDOM = new Random(System.currentTimeMillis());
    public static int TANK_TIME_TO_FIRE = 2000; //5 seconds
    //
    protected int counterFire; //millisecond to fire
    //

    public TankAI() {
        super();
        counterFire = 0;
    }

    public TankAI(Vector3 pos, int dir) {
        super(pos, dir);
        counterFire = 0;
    }
    
    @Override
    public void load() {
        super.load();
        texture = ResourceManager.getInst().getTexture("data/game/tankAi.png");
    }

    @Override
    public void update(long dt) {
        super.update(dt);
        //
        //fire
        counterFire += dt;
        if (counterFire >= TANK_TIME_TO_FIRE) {
            counterFire = 0;
            super.fire();
        }

        //change direction
        boolean canMove = super.move(getDirection());
        if (canMove != true) {
            this.setDirection(TANK_RANDOM.nextInt(CDirections.NUMBER_DIRECTION));
        }
    }

    @Override
    public void draw() {
        super.draw();
        
    }
}
