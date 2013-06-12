/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package myjogl.gameview;

import GameObjects.EnemyTank;
import GameObjects.Tank;
import GamePartical.Debris;
import GamePartical.Explo;
import GamePartical.Explo1;
import GamePartical.ParticalManager;
import GamePartical.RoundSparks;
import com.sun.opengl.util.texture.Texture;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.nio.FloatBuffer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.media.opengl.GL;
import javax.media.opengl.glu.GLU;
import myjogl.GameEngine;
import myjogl.Global;
import myjogl.KeyboardState;
import myjogl.utils.*;

/**
 *
 * @author Jundat
 */
public class MainGameView implements GameView {

    private SkyBox m_skybox;
    private Camera2 camera;
    public Tank myTank;
    public static EnemyTank otherTank;
    private static Texture ttGachMen;
    

    public MainGameView() {
        System.out.println("Go to main game!------------------------------------");
    }

    public void handleInput() {
        KeyboardState state = KeyboardState.getState();

        // this is my
        if (state.isDown(KeyEvent.VK_A)) {
            myTank.TurnLeft();
        }
        if (state.isDown(KeyEvent.VK_D)) {
            myTank.TurnRight();
        }
        if (state.isDown(KeyEvent.VK_W)) {
            myTank.SetTankVel(0.5f);
        } else {
            myTank.SetTankVel(0.0f);
        }
    }

    public void keyPressed(KeyEvent e) {
    }

    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {

            GameEngine.getInst().attach(new MenuView());
            GameEngine.getInst().detach(this);
        }
    }

    public void pointerPressed(MouseEvent e) {
        System.err.println("Shoot");
        Line line = myTank.GetLineBullet(); // PT duong thang vien dan
        Vector3 a = line.IsCollisionWithGameObject(otherTank);
        if (a != null) {
            System.err.println("BAN TRUNG!!!");

            Explo shootParticle = new Explo(a, 0.1f, 0.5f);
            shootParticle.LoadingTexture();
            ParticalManager.getInstance().Add(shootParticle);

            Explo1 shootParticle2 = new Explo1(a, 0.1f, 0.5f);
            shootParticle2.LoadingTexture();
            ParticalManager.getInstance().Add(shootParticle2);

            RoundSparks shootParticle3 = new RoundSparks(a, 0.1f, 0.3f);
            shootParticle3.LoadingTexture();
            ParticalManager.getInstance().Add(shootParticle3);

            Debris shootParticle4 = new Debris(a, 0.1f, 0.5f);
            shootParticle4.LoadingTexture();
            ParticalManager.getInstance().Add(shootParticle4);
        } else {
            System.err.println("---BAN HUT---");
        }

        myTank.StartShootParticle();
    }

    public void pointerMoved(MouseEvent e) {
        int x = e.getXOnScreen();
        int y = e.getYOnScreen();
        //System.err.print("\n" + x + " " + y);
        int mid_x = Global.wndWidth / 2;
        int mid_y = Global.wndHeight / 2;
        if ((x == mid_x) && (y == mid_y)) {
            return;
        }

        Robot r;
        try {
            r = new Robot();
            r.mouseMove(mid_x, mid_y);
        } catch (AWTException ex) {
            Logger.getLogger(Camera.class.getName()).log(Level.SEVERE, null, ex);
        }

        // Get the direction from the mouse cursor, set a resonable maneuvering speed
        float angle_x = (float) ((mid_x - x)) / 1000;
        float angle_y = (float) ((mid_y - y)) / 1000;
        myTank.TankCamera.beta += angle_x;
        myTank.TankCamera.alpha += angle_y;
        //System.err.print("\n" + angle_x);
    }

    public void pointerReleased(MouseEvent e) {
    }

    public void mouseMoved(MouseEvent e) {
    }

    public void load() {
        //set hide cursor
        Toolkit t = Toolkit.getDefaultToolkit();
        Image i = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
        Cursor noCursor = t.createCustomCursor(i, new Point(0, 0), "none");
        GameEngine.getInst().tank3d.frame.setCursor(noCursor);
        //end - set hide cursor

        ttGachMen = ResourceManager.getInst().getTexture("data/game/gach_men.png");

        //skybox
        m_skybox = new SkyBox();
        m_skybox.Initialize(5.0f);

        m_skybox.LoadTextures(
                "data/skybox/up.jpg", "data/skybox/down.jpg",
                "data/skybox/front.jpg", "data/skybox/back.jpg",
                "data/skybox/left.jpg", "data/skybox/right.jpg");
        //init map
        TankMap.getInst().LoadMap("data/map/MAP0.png");

        myTank = new Tank(new Vector3(-5.0f, 2, -7), new Vector3(0, 0, 1), 0.00f, 1.0f); // 0.05
        myTank.Init(Global.drawable);
        camera = myTank.TankCamera;
        otherTank = new EnemyTank(new Vector3(2, 2, -10), new Vector3(0, 1, 1), 0.0f, 1.0f);
        otherTank.Init(Global.drawable);
    }

    public void unload() {
        GameEngine.getInst().tank3d.frame.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));

        //pre-load main game
        ResourceManager.getInst().deleteTexture("data/game/gach_men.png");
        ResourceManager.getInst().deleteTexture("data/model/triax_wheels.png");
        ResourceManager.getInst().deleteTexture("data/model/knight.png");

        //skybox
        ResourceManager.getInst().deleteTexture("data/skybox/up.jpg");
        ResourceManager.getInst().deleteTexture("data/skybox/down.jpg");
        ResourceManager.getInst().deleteTexture("data/skybox/left.jpg");
        ResourceManager.getInst().deleteTexture("data/skybox/right.jpg");
        ResourceManager.getInst().deleteTexture("data/skybox/front.jpg");
        ResourceManager.getInst().deleteTexture("data/skybox/back.jpg");

    }

    private void DrawPlane() {
        GL gl = Global.drawable.getGL();

        ttGachMen.enable();
        ttGachMen.bind();

        int repeat = 250;
        gl.glBegin(GL.GL_QUADS);
        gl.glTexCoord2f(repeat, 0);
        gl.glVertex3f(500, 0, 500);

        gl.glTexCoord2f(repeat, repeat);
        gl.glVertex3f(500, 0, -500);

        gl.glTexCoord2f(0, repeat);
        gl.glVertex3f(-500, 0, -500);

        gl.glTexCoord2f(0, 0);
        gl.glVertex3f(-500, 0, 500);
        gl.glEnd();

        //reset color
        gl.glColor4f(1, 1, 1, 1);
    }

    public void update(long elapsedTime) {
        handleInput();

        myTank.Update(0);
        otherTank.Update(0);
    }

    public void display() {
        GL gl = Global.drawable.getGL();
        GLU glu = new GLU();
        // use this function for opengl target camera
        gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);
        gl.glLoadIdentity();

        glu.gluLookAt(camera.x, camera.y, camera.z, camera.lookAtX, camera.lookAtY, camera.lookAtZ, 0, 1, 0);
        // skybox origin should be same as camera position
        m_skybox.Render((float) camera.x, (float) camera.y, (float) camera.z);

        this.DrawPlane();

        TankMap.getInst().Render(2, 6);

        // Draw player
        gl.glPushMatrix();
        myTank.Draw(Global.drawable);
        gl.glPopMatrix();

        gl.glPushMatrix();
        otherTank.Draw(Global.drawable);
        gl.glPopMatrix();

        // Partical draw
        ParticalManager particle = ParticalManager.getInstance();
        particle.Update();
        particle.Draw(gl, camera.GetAngleY());
        
        Renderer.Render(ttGachMen, 0, 0);

        // Draw alis
        gl.glPushMatrix();
        gl.glBegin(GL.GL_LINES);
        gl.glColor3f(1, 0, 0);
        gl.glVertex3f(0.0f, 0.0f, 0.0f);
        gl.glVertex3f(10, 0, 0);
        gl.glColor3f(0, 1, 0);
        gl.glVertex3f(0.0f, 0.0f, 0.0f);
        gl.glVertex3f(0, 10, 0);
        gl.glColor3f(0, 0, 1);
        gl.glVertex3f(0.0f, 0.0f, 0.0f);
        gl.glVertex3f(0, 0, 10);
        gl.glEnd();
        gl.glPopMatrix();
        // End
    }
}
