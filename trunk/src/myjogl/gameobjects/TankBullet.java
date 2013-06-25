/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package myjogl.gameobjects;

import com.sun.opengl.util.texture.Texture;
import myjogl.particles.ParticalManager;
import javax.media.opengl.GL;
import myjogl.Global;
import myjogl.particles.Debris;
import myjogl.particles.Explo;
import myjogl.particles.Explo1;
import myjogl.particles.RoundSparks;
import myjogl.utils.GLModel;
import myjogl.utils.ModelLoaderOBJ;
import myjogl.utils.TankMap;
import myjogl.utils.Vector3;
import myjogl.utils.ResourceManager;

/**
 *
 * @author Jundat
 */
public class TankBullet {

    public static final float BULLET_VELOCITY = 0.5f; //do not change it
    public static final float BULLET_WIDTH = 1.0f;
    public static final float BULLET_HEIGHT = 1.0f;
    public boolean isAlive;
    private Vector3 position;
    private int direction;
    private Texture tt;

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

    public void load() {
        tt = ResourceManager.getInst().getTexture("data/game/bullet.png");

        //
        Vector3 a = getPosition().Clone();
        float scale = 0.1f;
        Explo shootParticle = new Explo(a, 0.1f, scale);
        shootParticle.LoadingTexture();

        Explo1 shootParticle2 = new Explo1(a, 0.1f, scale);
        shootParticle2.LoadingTexture();

        RoundSparks shootParticle3 = new RoundSparks(a, 0.1f, scale);
        shootParticle3.LoadingTexture();

        Debris shootParticle4 = new Debris(a, 0.1f, scale);
        shootParticle4.LoadingTexture();
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

    public void explode() {
        Vector3 a = getPosition().Clone();
        float scale = 0.03f;
        float time = 0.3f;
        Explo shootParticle = new Explo(a, time, scale);
        shootParticle.LoadingTexture();
        ParticalManager.getInstance().Add(shootParticle);

        Explo1 shootParticle2 = new Explo1(a, time, scale);
        shootParticle2.LoadingTexture();
        ParticalManager.getInstance().Add(shootParticle2);
//
//        RoundSparks shootParticle3 = new RoundSparks(a, time, 0.1f);
//        shootParticle3.LoadingTexture();
//        ParticalManager.getInstance().Add(shootParticle3);
//
        Debris shootParticle4 = new Debris(a, time, scale);
        shootParticle4.LoadingTexture();
        ParticalManager.getInstance().Add(shootParticle4);
    }

    /**
     * Update position use: velocity + direction
     */
    public void update(long dt) {
        if (this.isAlive) {
            Vector3 lastPos = position.Clone();
            switch (direction) {
                case CDirections.UP:
                    position.z -= BULLET_VELOCITY;
                    if (position.z <= 0) {
                        position.z = 0;
                        this.explode();
                        isAlive = false;
                    }
                    break;

                case CDirections.DOWN:
                    position.z += BULLET_VELOCITY;
                    if (position.z > TankMap.getInst().height - BULLET_HEIGHT) {
                        position.z = TankMap.getInst().height - BULLET_HEIGHT;
                        this.explode();
                        isAlive = false;
                    }
                    break;

                case CDirections.LEFT:
                    position.x -= BULLET_VELOCITY;
                    if (position.x <= 0) {
                        position.x = 0;
                        this.explode();
                        isAlive = false;
                    }
                    break;

                case CDirections.RIGHT:
                    position.x += BULLET_VELOCITY;
                    if (position.x > TankMap.getInst().width - BULLET_WIDTH) {
                        position.x = TankMap.getInst().width - BULLET_WIDTH;
                        this.explode();
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

                this.explode();
                isAlive = false;
            }
        }
    }

    /**
     * Draw model use: position, direction
     */
    public void draw() {
        if (this.isAlive) {
            GL gl = Global.drawable.getGL();
            
            tt.enable();
            tt.bind();
            gl.glBegin(GL.GL_QUADS);
            {
                gl.glTexCoord2f(0, 0);
                gl.glVertex3f(position.x, Tank.TANK_WIDTH / 2, position.z);

                gl.glTexCoord2f(1, 0);
                gl.glVertex3f(position.x + BULLET_WIDTH, Tank.TANK_WIDTH / 2, position.z);

                gl.glTexCoord2f(1, 1);
                gl.glVertex3f(position.x + BULLET_WIDTH, Tank.TANK_WIDTH / 2, position.z + BULLET_HEIGHT);

                gl.glTexCoord2f(0, 1);
                gl.glVertex3f(position.x, Tank.TANK_WIDTH / 2, position.z + BULLET_HEIGHT);
            }
            gl.glEnd();
            tt.disable();
        }
    }

    //
    //get and set
    //
    public CRectangle getBound() {
        CRectangle rect = new CRectangle();
        rect.x = position.x;
        rect.y = position.z;
        rect.w = BULLET_WIDTH;
        rect.h = BULLET_HEIGHT;

        return rect;
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
