/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package myjogl.gameobjects;

import com.sun.opengl.util.texture.Texture;
import javax.media.opengl.GL;
import myjogl.Global;
import myjogl.particles.Debris;
import myjogl.particles.Explo;
import myjogl.particles.Explo1;
import myjogl.particles.ParticalManager;
import myjogl.particles.RoundSparks;
import myjogl.utils.Md2;
import myjogl.utils.TankMap;
import myjogl.utils.Vector3;
import myjogl.utils.ResourceManager;

public class Tank {

    public final static float TANK_VELOCITY = 0.25f; //do not change it
    public final static float TANK_WIDTH = 2;
    public final static float TANK_HEIGHT = 2;
    public final static int TANK_NUMBER_BULLETS = 10;
    //
    public boolean isAlive;
    private Vector3 position;
    private int direction;
    private Vector3 lastPosition;
    //
    public TankBullet bullets[];
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
        model = new Md2();
        model.LoadModel("data/model/triax_wheels.md2");
        model.LoadSkin(ResourceManager.getInst().getTexture("data/model/triax_wheels.png", false, GL.GL_REPEAT));
        
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

        for (int i = 0; i < TANK_NUMBER_BULLETS; i++) {
            if (bullets[i].isAlive == false) {
                bullets[i].reset(bpos, getDirection());
                break;
            }
        }
    }

    public void explode() {
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
     * D?a vào: direction + position => v? model t?i ?úng v? trí
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
                Global.drawCube(texture, 0, 0, 0, Tank.TANK_WIDTH, 2, Tank.TANK_HEIGHT);
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