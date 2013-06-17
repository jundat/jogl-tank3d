/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package myjogl.gameobjects;

import myjogl.particles.ParticalManager;
import javax.media.opengl.GL;
import myjogl.Global;
import myjogl.utils.TankMap;
import myjogl.utils.Vector3;

/**
 *
 * @author Jundat
 */
public class TankBullet {

    public static final float BULLET_VELOCITY = 1.0f; //do not change it
    public static final float BULLET_WIDTH = 1.0f;
    public static final float BULLET_HEIGHT = 1.0f;
    private boolean isAlive;
    private Vector3 position;
    private int direction;

    public TankBullet() {
        position = new Vector3(0, 0, 0);
        direction = CDirections.DOWN;
        isAlive = true;
    }

    public TankBullet(Vector3 pos, int dir) {
        position = new Vector3(pos.x, pos.y, pos.z);
        direction = dir;
        isAlive = true;
    }

    /**
     * Reset when Tanh fire
     *
     * @param pos new position
     * @param dir new direction
     */
    public void reset(Vector3 pos, int dir) {
        position = new Vector3(pos.x, pos.y, pos.z);
        direction = dir;
        isAlive = true;
    }

    /**
     * Update position use: velocity + direction
     */
    public void update(long dt) {
        Vector3 lastPos = new Vector3(position);
        
        if (isAlive) {
            switch (direction) {
                case CDirections.UP:
                    position.z -= BULLET_VELOCITY;
                    if (position.z <= 0) {
                        position.z = 0;
                        isAlive = false;
                    }
                    break;

                case CDirections.DOWN:
                    position.z += BULLET_VELOCITY;
                    if (position.z > TankMap.getInst().height - BULLET_HEIGHT) {
                        position.z = TankMap.getInst().height - BULLET_HEIGHT;
                        isAlive = false;
                    }
                    break;

                case CDirections.LEFT:
                    position.x -= BULLET_VELOCITY;
                    if (position.x <= 0) {
                        position.x = 0;
                        isAlive = false;
                    }
                    break;

                case CDirections.RIGHT:
                    position.x += BULLET_VELOCITY;
                    if (position.x > TankMap.getInst().width - BULLET_WIDTH) {
                        position.x = TankMap.getInst().width - BULLET_WIDTH;
                        isAlive = false;
                    }
                    break;
            }

            //collide in map
            if (TankMap.getInst().isIntersect(this.getBound())) {
                position = lastPos;
                for (int i = -1; i <= 1; i++) {
                    for (int j = -1; j <= 1; j++) {
                        TankMap.getInst().delete((int) position.z + i, (int) position.x + j);
                    }
                }

                isAlive = false;
            }
        }
    }

    /**
     * Draw model use: position, direction
     */
    public void draw() {
        if (isAlive == true) {
            GL gl = Global.drawable.getGL();
            gl.glColor3f(1, 1, 1);
            gl.glBegin(GL.GL_QUADS);
            {
                gl.glVertex3f(getPosition().x, getPosition().y, getPosition().z);
                gl.glVertex3f(getPosition().x + BULLET_WIDTH, getPosition().y, getPosition().z);
                gl.glVertex3f(getPosition().x + BULLET_WIDTH, getPosition().y, getPosition().z + BULLET_HEIGHT);
                gl.glVertex3f(getPosition().x, getPosition().y, getPosition().z + BULLET_HEIGHT);
            }
            gl.glEnd();
        }
    }

    //get and set
    public CRectangle getBound() {
        CRectangle rect = new CRectangle();
        rect.x = getPosition().x;
        rect.y = getPosition().z;
        rect.w = BULLET_WIDTH;
        rect.h = BULLET_HEIGHT;

        return rect;
    }

    /**
     * @return the isAlive
     */
    public boolean isAlive() {
        return isAlive;
    }

    /**
     * @param isAlive the isAlive to set
     */
    public void setAlive(boolean isAlive) {
        this.isAlive = isAlive;
    }

    /**
     * @return the position
     */
    public Vector3 getPosition() {
        return position;
    }

    /**
     * @param position the position to set
     */
    public void setPosition(Vector3 position) {
        this.position = position;
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
