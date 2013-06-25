/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package myjogl.gameobjects;

import com.sun.opengl.util.texture.Texture;
import javax.media.opengl.GL;
import myjogl.GameEngine;
import myjogl.Global;
import myjogl.particles.Debris;
import myjogl.particles.Explo;
import myjogl.particles.Explo1;
import myjogl.particles.ParticalManager;
import myjogl.particles.RoundSparks;
import myjogl.utils.GLModel;
import myjogl.utils.ID;
import myjogl.utils.Md2;
import myjogl.utils.ModelLoaderOBJ;
import myjogl.utils.TankMap;
import myjogl.utils.Vector3;
import myjogl.utils.ResourceManager;

public class Tank {

    public final static float TANK_FIRE_TIME = 400; //millisecond between 2 fire time
    public final static float TANK_VELOCITY = 0.25f; //do not change it
    public final static float TANK_VELOCITY_SLOW = 0.15f; //do not change it
    public final static float TANK_WIDTH = 2.75f;
    public final static float TANK_HEIGHT = 2.75f;
    public final static int TANK_NUMBER_BULLETS = 10;
    //
    public boolean isAlive;
    private Vector3 position;
    private int direction;
    private Vector3 lastPosition;
    private float velocity = TANK_VELOCITY;
    //
    public TankBullet bullets[];
    protected Texture texture;
    protected long fireTime = 0;
    GLModel model = null;

    //
    //init
    //
    /**
     * Init tank at default position and direction
     */
    public Tank() {
        isAlive = true;
        position = new Vector3();
        direction = CDirections.UP;
        //
        lastPosition = position.Clone();
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
        direction = dir;
        //
        lastPosition = position.Clone();
    }

    public void load() {
        bullets = new TankBullet[TANK_NUMBER_BULLETS];
        for (int i = 0; i < TANK_NUMBER_BULLETS; i++) {
            bullets[i] = new TankBullet();
            bullets[i].load();
            bullets[i].isAlive = false; //start by false
        }

        //
        texture = ResourceManager.getInst().getTexture("data/game/tank.png");
        //

        model = ModelLoaderOBJ.LoadModel("data/model/HK-Tank.obj",
                "data/model/HK-Tank.mtl", "data/model/t0026_0.png", Global.drawable);

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

    //
    //public method
    //
    public void rollBack() {
        this.position = this.lastPosition.Clone();
    }

    /**
     * Change direction If have same direction, let's move tank
     */
    public boolean move(int dir) {

        this.lastPosition = this.position.Clone();
        //
        Vector3 tempLastPos = new Vector3(position);

        if (direction != dir) {
            direction = dir;
        } else {
            switch (direction) {
                case CDirections.UP:
                    position.z -= velocity;
                    if (position.z <= 0) {
                        position.z = 0;
                        return false;
                    }
                    break;

                case CDirections.DOWN:
                    position.z += velocity;
                    if (position.z > TankMap.getInst().height - TANK_HEIGHT) {
                        position.z = TankMap.getInst().height - TANK_HEIGHT;
                        return false;
                    }
                    break;

                case CDirections.LEFT:
                    position.x -= velocity;
                    if (position.x <= 0) {
                        position.x = 0;
                        return false;
                    }
                    break;

                case CDirections.RIGHT:
                    position.x += velocity;
                    if (position.x > TankMap.getInst().width - TANK_WIDTH) {
                        position.x = TankMap.getInst().width - TANK_WIDTH;
                        return false;
                    }
                    break;
            }

            //collide in map
            if (TankMap.getInst().isIntersect(this.getBound())) {
                position = tempLastPos;
                return false;
            }

            if (TankMap.getInst().isIntersectItem(this.getBound(), ID.WATER)) {
                velocity = TANK_VELOCITY_SLOW;
            } else {
                velocity = TANK_VELOCITY;
            }

        }

        return true;
    }

    /**
     * Fire when a key is press Reset bullet position and isAlive
     */
    public boolean fire() {
        if (System.currentTimeMillis() - fireTime >= TANK_FIRE_TIME) {
            fireTime = System.currentTimeMillis();

            Vector3 bpos = new Vector3(position);
            bpos.x += TANK_WIDTH / 2 - TankBullet.BULLET_WIDTH / 2;
            bpos.y = 2;
            bpos.z += TANK_HEIGHT / 2 - TankBullet.BULLET_HEIGHT / 2;

            for (int i = 0; i < TANK_NUMBER_BULLETS; i++) {
                if (bullets[i].isAlive == false) {
                    bullets[i].reset(bpos, getDirection());
                    break;
                }
            }

            return true;
        }

        return false;
    }

    public void explode() {
        GameEngine.sExplode.play(false);

        Vector3 a = getPosition().Clone();
        float scale = 0.1f;
        float time = 0.4f;
        Explo shootParticle = new Explo(a, time, scale);
        shootParticle.LoadingTexture();
        ParticalManager.getInstance().Add(shootParticle);

        Explo1 shootParticle2 = new Explo1(a, time, scale);
        shootParticle2.LoadingTexture();
        ParticalManager.getInstance().Add(shootParticle2);

        RoundSparks shootParticle3 = new RoundSparks(a, time, scale);
        shootParticle3.LoadingTexture();
        ParticalManager.getInstance().Add(shootParticle3);

        Debris shootParticle4 = new Debris(a, time, scale);
        shootParticle4.LoadingTexture();
        ParticalManager.getInstance().Add(shootParticle4);
    }

    /**
     * If tank is dead, it will be reset at start position
     */
    public void reset(Vector3 pos, int dir) {
        isAlive = true;
        position.Copy(pos);
        direction = dir;
        for (int i = 0; i < TANK_NUMBER_BULLETS; i++) {
            bullets[i].isAlive = false;
        }
    }

    //
    //update and draw
    //
    /**
     * Use singleton TankMap to update tank and bullet
     */
    public void update(long dt) {
        //bullet
        for (int i = 0; i < TANK_NUMBER_BULLETS; i++) {
            if (bullets[i].isAlive) {
                bullets[i].update(dt);
            }
        }

        //tank
        if (this.isAlive) {
        }
    }

    /**
     * D?a v�o: direction + position => v? model t?i ?�ng v? tr�
     */
    public void draw() {
        //bullet
        for (int i = 0; i < TANK_NUMBER_BULLETS; i++) {
            if (bullets[i].isAlive) {
                bullets[i].draw();
            }
        }

        //draw tank
        if (this.isAlive) {
            //tank
            GL gl = Global.drawable.getGL();
            gl.glPushMatrix();
            {
                gl.glTranslatef(getPosition().x, getPosition().y, getPosition().z);
                //Global.drawCube(texture, 0, 0, 0, Tank.TANK_WIDTH, 2, Tank.TANK_HEIGHT);

                float scale = 0.003f;
                gl.glTranslatef(TANK_WIDTH / 2, 0, TANK_WIDTH / 2);
                gl.glScalef(scale, scale, scale);
                float angle = 0;
                if (direction == CDirections.UP) {
                    angle = 180;
                } else if (direction == CDirections.LEFT) {
                    angle = -90;
                } else if (direction == CDirections.RIGHT) {
                    angle = 90;
                }

                gl.glRotatef(angle, 0, 1, 0);
                model.opengldraw(Global.drawable);
            }
            gl.glPopMatrix();
        }
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