/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package myjogl.gameobjects;

import java.util.ArrayList;
import javax.media.opengl.GL;
import myjogl.Global;
import myjogl.utils.Md2;
import myjogl.utils.TankMap;
import myjogl.utils.Vector3;
import myjogl.utils.ResourceManager;

public class Tank {
    //

    final static float TANK_VELOCITY = 0.5f; //do not change it
    final static float TANK_WIDTH = 3;
    final static float TANK_HEIGHT = 3;
    final static Vector3 TANK_START_LEFT = new Vector3(12, 0, 35);
    final static Vector3 TANK_START_RIGHT = new Vector3(23, 0, 35);
    final static int TANK_BULLETS = 10;
    //
    public boolean isAlive;
    private Vector3 position;
    private int direction;
    //private TankBullet bullet;
    private TankBullet bullets[];
    private Md2 model;
    //

    /**
     * Init tank at default position and direction
     */
    public Tank() {
        isAlive = true;
        position = (System.currentTimeMillis() / 2 == 0) ? TANK_START_LEFT : TANK_START_RIGHT;
        direction = CDirections.UP;
    }

    /**
     * Init Tank with position and direction
     *
     * @param pos position
     * @param dir direction in Directions.java class
     */
    public Tank(Vector3 pos, int dir) {
        isAlive = true;
        position = pos;
        direction = dir;
    }

    public void load() {
        //bullet = new TankBullet();
        //bullet.setAlive(false); //start by false

        bullets = new TankBullet[TANK_BULLETS];
        for (int i = 0; i < TANK_BULLETS; i++) {
            bullets[i] = new TankBullet();
            bullets[i].setAlive(false); //start by false
        }

        model = new Md2();
        model.LoadModel("data/model/triax_wheels.md2");
        model.LoadSkin(ResourceManager.getInst().getTexture("data/model/triax_wheels.png", false, GL.GL_REPEAT));
    }

    /**
     * Change direction If have same direction, let's move tank
     */
    public void move(int dir) {
        Vector3 lastPos = new Vector3(position.x, position.y, position.z);

        if (direction != dir) {
            direction = dir;
        }
        //else 
        {
            switch (direction) {
                case CDirections.UP:
                    position.z -= TANK_VELOCITY;
                    if (position.z <= 0) {
                        position.z = 0;
                    }
                    break;

                case CDirections.DOWN:
                    position.z += TANK_VELOCITY;
                    if (position.z > TankMap.getInst().height - TANK_HEIGHT) {
                        position.z = TankMap.getInst().height - TANK_HEIGHT;
                    }
                    break;

                case CDirections.LEFT:
                    position.x -= TANK_VELOCITY;
                    if (position.x <= 0) {
                        position.x = 0;
                    }
                    break;

                case CDirections.RIGHT:
                    position.x += TANK_VELOCITY;
                    if (position.x > TankMap.getInst().width - TANK_WIDTH) {
                        position.x = TankMap.getInst().width - TANK_WIDTH;
                    }
                    break;
            }
        }

        //collide in map
        if (TankMap.getInst().isIntersect(this.getBound())) {
            position = lastPos;
        }
    }

    /**
     * Fire when a key is press Reset bullet position and isAlive
     */
    public void fire() {
        Vector3 bpos = new Vector3(position);
        bpos.x += TANK_WIDTH / 2 - TankBullet.BULLET_WIDTH / 2;
        bpos.y = 2;
        bpos.z += TANK_HEIGHT / 2 - TankBullet.BULLET_HEIGHT / 2;

        for (int i = 0; i < TANK_BULLETS; i++) {
            if (bullets[i].isAlive() == false) {
                bullets[i].reset(bpos, direction);
                break;
            }
        }
    }

    /**
     * Return bound rect to check collision
     *
     * @return
     */
    public CRectangle getBound() {
        CRectangle rect = new CRectangle();
        rect.x = position.x;
        rect.y = position.z;
        rect.w = TANK_WIDTH;
        rect.h = TANK_HEIGHT;

        return rect;
    }

    /**
     * If tank is dead, it will be reset at start position
     */
    public void reset() {
        isAlive = true;
        position = (System.currentTimeMillis() / 2 == 0) ? TANK_START_LEFT : TANK_START_RIGHT;
        direction = CDirections.UP;
        for (int i = 0; i < TANK_BULLETS; i++) {
            bullets[i].setAlive(false);
        }
    }

    //update and draw
    /**
     * Use singleton TankMap to update tank and bullet
     */
    public void update(long dt) {
        //bullet
        for (int i = 0; i < TANK_BULLETS; i++) {
            bullets[i].update(dt);
        }

        //tank
    }

    /**
     * D?a vào: direction + position => v? model t?i ?úng v? trí
     */
    public void draw() {
        //bullet
        for (int i = 0; i < TANK_BULLETS; i++) {
            bullets[i].draw();
        }

        //tank
        GL gl = Global.drawable.getGL();
        gl.glPushMatrix();
        {
            gl.glTranslatef(position.x, position.y, position.z);
//            
//            switch(direction)
//            {
//                case CDirections.UP:
//                    //gl.glRotatef(90, 0, 1, 0);
//                    break;
//                case CDirections.DOWN:
//                    gl.glRotatef(180, 0, 1, 0);
//                    break;
//                case CDirections.LEFT:
//                    gl.glRotatef(90, 0, 1, 0);
//                    break;
//                case CDirections.RIGHT:
//                    gl.glRotatef(-90, 0, 1, 0);
//                    break;
//            }

            TankMap.getInst().drawCube(0, 0, 0, 3, 2, 3);
        }
        gl.glPopMatrix();
    }
}