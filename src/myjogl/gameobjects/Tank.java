/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package myjogl.gameobjects;

import com.sun.opengl.util.texture.Texture;
import javax.media.opengl.GL;
import myjogl.Global;
import myjogl.utils.Md2;
import myjogl.utils.TankMap;
import myjogl.utils.Vector3;
import myjogl.utils.ResourceManager;

public class Tank {

    public final static float TANK_VELOCITY = 0.1f; //do not change it
    public final static float TANK_WIDTH = 3;
    public final static float TANK_HEIGHT = 3;
    public final static int TANK_BULLETS = 10;
    //
    public boolean isAlive;
    private Vector3 position;
    private Vector3 lastPosition;
    private int direction;
    protected TankBullet bullets[];
    protected Md2 model;
    protected Texture texture;

    //
    //init
    //
    /**
     * Init tank at default position and direction
     */
    public Tank() {
        isAlive = true;
        position = new Vector3();
        lastPosition = position.Clone();
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
        position = new Vector3(pos);
        lastPosition = position.Clone();
        direction = dir;
    }

    public void load() {
        bullets = new TankBullet[TANK_BULLETS];
        for (int i = 0; i < TANK_BULLETS; i++) {
            bullets[i] = new TankBullet();
            bullets[i].setAlive(false); //start by false
        }

        //
        texture = ResourceManager.getInst().getTexture("data/game/tank.png");
        //
        model = new Md2();
        model.LoadModel("data/model/triax_wheels.md2");
        model.LoadSkin(ResourceManager.getInst().getTexture("data/model/triax_wheels.png", false, GL.GL_REPEAT));
    }

    //
    //public method
    //
    /**
     * Change direction If have same direction, let's move tank
     */
    public boolean move(int dir) {
        Vector3 tempLastPos = new Vector3(position);

        if (getDirection() != dir) {
            direction = dir;
        }
        //else 
        {
            switch (direction) {
                case CDirections.UP:
                    position.z -= TANK_VELOCITY;
                    if (position.z <= 0) {
                        position.z = 0;
                        return false;
                    }
                    break;

                case CDirections.DOWN:
                    position.z += TANK_VELOCITY;
                    if (position.z > TankMap.getInst().height - TANK_HEIGHT) {
                        position.z = TankMap.getInst().height - TANK_HEIGHT;
                        return false;
                    }
                    break;

                case CDirections.LEFT:
                    position.x -= TANK_VELOCITY;
                    if (position.x <= 0) {
                        position.x = 0;
                        return false;
                    }
                    break;

                case CDirections.RIGHT:
                    position.x += TANK_VELOCITY;
                    if (position.x > TankMap.getInst().width - TANK_WIDTH) {
                        position.x = TankMap.getInst().width - TANK_WIDTH;
                        return false;
                    }
                    break;
            }
        }

        //collide in map
        if (TankMap.getInst().isIntersect(this.getBound())) {
            position = tempLastPos;
            return false;
        } else {
            lastPosition.Copy(tempLastPos);
        }

        return true;
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
                bullets[i].reset(bpos, getDirection());
                break;
            }
        }
    }

    /**
     * If tank is dead, it will be reset at start position
     */
    public void reset(Vector3 pos, int dir) {
        isAlive = true;
        position.Copy(pos);
        lastPosition.Copy(pos);
        direction = dir;
        for (int i = 0; i < TANK_BULLETS; i++) {
            bullets[i].setAlive(false);
        }
    }

    public void rollBack() {
        this.position.Copy(lastPosition);
    }
    
    //
    //update and draw
    //
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
            gl.glTranslatef(getPosition().x, getPosition().y, getPosition().z);
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
            Global.drawCube(texture, 0, 0, 0, 3, 2, 3);
        }
        gl.glPopMatrix();
    }

    //
    //get and set
    //
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
     * @return the position
     */
    public Vector3 getPosition() {
        return position.Clone();
    }

    /**
     * @param position the position to set
     */
    public void setPosition(Vector3 pos) {
        this.position.Copy(pos);
    }

    /**
     * @return the direction
     */
    public int getDirection() {
        return direction;
    }

    /**
     * @param direction the direction to set
     */
    public void setDirection(int direction) {
        this.direction = direction;
    }
}