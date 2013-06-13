/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package myjogl.gameview;

import com.sun.opengl.util.texture.Texture;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import javax.media.opengl.GL;
import javax.media.opengl.glu.GLU;
import myjogl.GameEngine;
import myjogl.GameEngine;
import myjogl.Global;
import myjogl.Global;
import myjogl.KeyboardState;
import myjogl.KeyboardState;
import myjogl.utils.*;

/**
 *
 * @author Jundat
 */
public class MainGameView implements GameView {

    private SkyBox m_skybox;
    private Camera camera;
    private static Md2 md2Tank;
    Texture ttGachMen;
    final float[] redLightColorAmbient = {0.0f, 0.0f, 0.0f, 0.0f}; //red
    final float[] redLightColorDisfuse = {2.0f, 2.0f, 2.0f, 1.0f}; //red
    final float[] redLightColorSpecular = {6.0f, 6.0f, 6.0f, 1.0f}; //red
    final float[] redLightPos = {32.0f, 20.0f, 32.0f, 1.0f};
    
    public MainGameView() {
        super();
        System.out.println("Go to main game!------------------------------------");
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
    }

    public void pointerMoved(MouseEvent e) {
        int x = e.getXOnScreen();
        int y = e.getYOnScreen();

        if (camera != null) {
            camera.Mouse_Move(x, y, Global.wndWidth, Global.wndHeight);
        }
    }

    public void pointerReleased(MouseEvent e)    {
    }
    
    private void setLight() {
        GL gl = Global.drawable.getGL();

        gl.glEnable(GL.GL_LIGHTING);
        // set up static red light
        gl.glEnable(GL.GL_LIGHT1);
        gl.glLightfv(GL.GL_LIGHT1, GL.GL_AMBIENT, redLightColorAmbient, 0);
        gl.glLightfv(GL.GL_LIGHT1, GL.GL_DIFFUSE, redLightColorDisfuse, 0);
        gl.glLightfv(GL.GL_LIGHT1, GL.GL_SPECULAR, redLightColorSpecular, 0);
        gl.glLightfv(GL.GL_LIGHT1, GL.GL_POSITION, redLightPos, 0);
    }

    public void load() {
        //set hide cursor
        Toolkit t = Toolkit.getDefaultToolkit();
        Image i = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
        Cursor noCursor = t.createCustomCursor(i, new Point(0, 0), "none");
        GameEngine.getInst().tank3d.frame.setCursor(noCursor);
        //end - set hide cursor

        this.setLight();

        //init variable
        camera = new Camera();
        camera.Position_Camera(32.443157f, 38.76995f, 56.465584f, 32.421318f, 37.233974f, 55.778576f, 0.0f, 1.0f, 0.0f);

        ttGachMen = ResourceManager.getInst().getTexture("data/game/gach_men.png");

        //skybox
        m_skybox = new SkyBox();
        m_skybox.Initialize(5.0f);

        m_skybox.LoadTextures(
                "data/skybox/top.jpg", "data/skybox/bottom.jpg",
                "data/skybox/front.jpg", "data/skybox/back.jpg",
                "data/skybox/left.jpg", "data/skybox/right.jpg");

        //init map
        TankMap.getInst().LoadMap("data/map/MAP0.png");

        //model
        md2Tank = new Md2();
        md2Tank.LoadModel("data/model/triax_wheels.md2");
        md2Tank.LoadSkin(ResourceManager.getInst().getTexture("data/model/triax_wheels.png", false, GL.GL_REPEAT));
    }

    public void unload() {
        GameEngine.getInst().tank3d.frame.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));

        //pre-load main game
        ResourceManager.getInst().deleteTexture("data/game/gach_men.png");
        ResourceManager.getInst().deleteTexture("data/model/triax_wheels.png");

        //skybox
        ResourceManager.getInst().deleteTexture("data/skybox/top.jpg");
        ResourceManager.getInst().deleteTexture("data/skybox/bottom.jpg");
        ResourceManager.getInst().deleteTexture("data/skybox/left.jpg");
        ResourceManager.getInst().deleteTexture("data/skybox/right.jpg");
        ResourceManager.getInst().deleteTexture("data/skybox/front.jpg");
        ResourceManager.getInst().deleteTexture("data/skybox/back.jpg");
    }

    public void handleInput() {
        KeyboardState state = KeyboardState.getState();

        //camera controller
        {
            float CAMERASPEED = 0.3f;

            //near-far

            //near
            if (state.isDown(KeyEvent.VK_B)) {
                camera.Move_Character_Farther(CAMERASPEED / 5);
            }


            //far
            if (state.isDown(KeyEvent.VK_V)) {
                camera.Move_Character_Farther(-CAMERASPEED / 5);
            }


            //forward
            if (state.isDown(KeyEvent.VK_UP)) {
                camera.Move_Camera(CAMERASPEED / 4);
            }

            //backward
            if (state.isDown(KeyEvent.VK_DOWN)) {
                camera.Move_Camera(-CAMERASPEED / 4);
            }

            //left
            if (state.isDown(KeyEvent.VK_LEFT)) {
                camera.Move_Left_Right(-CAMERASPEED / 4);
            }

            //right
            if (state.isDown(KeyEvent.VK_RIGHT)) {
                camera.Move_Left_Right(CAMERASPEED / 4);
            }

            //up
            if (state.isDown(KeyEvent.VK_N)) {
                camera.Move_Up_Down(CAMERASPEED / 5);

                System.out.println(camera.mPos.x + "f, " + camera.mPos.y + "f, " + camera.mPos.z + "f, "
                        + camera.mView.x + "f, " + camera.mView.y + "f, " + camera.mView.z + "f, "
                        + camera.mUp.x + "f, " + camera.mUp.y + "f, " + camera.mUp.z);
            }

            //down
            if (state.isDown(KeyEvent.VK_M)) {
                camera.Move_Up_Down(-CAMERASPEED / 5);

                System.out.println(camera.mPos.x + "f, " + camera.mPos.y + "f, " + camera.mPos.z + "f, "
                        + camera.mView.x + "f, " + camera.mView.y + "f, " + camera.mView.z + "f, "
                        + camera.mUp.x + "f, " + camera.mUp.y + "f, " + camera.mUp.z);
            }
        }
    }

    public void update(long elapsedTime) {
        handleInput();
    }

    public void display() {
        GL gl = Global.drawable.getGL();
        GLU glu = new GLU();
        gl.glLoadIdentity();
        gl.glEnable(GL.GL_LIGHTING);

        gl.glEnable(GL.GL_LINE_SMOOTH);
        gl.glEnable(GL.GL_POLYGON_SMOOTH);
        gl.glHint(GL.GL_LINE_SMOOTH_HINT, GL.GL_NICEST);
        gl.glHint(GL.GL_POLYGON_SMOOTH_HINT, GL.GL_NICEST);
        
        gl.glEnable(GL.GL_BLEND); 
        gl.glBlendFunc(GL.GL_SRC_ALPHA, GL.GL_ONE_MINUS_SRC_ALPHA);
        
        gl.glEnable(GL.GL_MULTISAMPLE);

        glu.gluLookAt(
                camera.mPos.x, camera.mPos.y, camera.mPos.z,
                camera.mView.x, camera.mView.y, camera.mView.z,
                camera.mUp.x, camera.mUp.y, camera.mUp.z);

        // skybox origin should be same as camera position
        m_skybox.Render(camera.mPos.x, camera.mPos.y, camera.mPos.z);

        //map
        TankMap.getInst().Render(1, 2);

        //camera
        gl.glPushMatrix();
        {
            // Always keep the character in the view
            gl.glTranslatef(camera.mView.x, 0.0f, camera.mView.z);
            float dx = camera.mView.x - camera.mPos.x;
            float dz = camera.mView.z - camera.mPos.z;
            float angle = (float) Math.atan(dz / dx);
            angle = 180 * angle / 3.141592654f;
            int angle2 = (int) angle;
            angle2 %= 360;
            if (dx < 0) {
                angle2 = (int) (angle - 180);
            }
            gl.glRotatef(-angle2, 0, 1, 0);

            //draw tank
            gl.glRotatef(-90, 0, 0, 1);
            gl.glRotatef(-90, 0, 1, 0);
            //md2Tank.SetScale(0.005f);
            //md2Tank.DrawModel(gl, 0);
        }
        
        gl.glPopMatrix();

        gl.glDisable(GL.GL_LIGHTING);
    }
}
