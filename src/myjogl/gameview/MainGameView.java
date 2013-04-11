/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package myjogl.gameview;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import javax.media.opengl.GL;
import javax.media.opengl.glu.GLU;
import myjogl.GameEngine;
import myjogl.Global;
import myjogl.utils.*;

/**
 *
 * @author Jundat
 */
public class MainGameView implements GameView {

    private SkyBox m_skybox;
    private Camera objCamera;
    private static Md2 md2Tank;
    private static Md2 model;
    
    //tieunun
    //public static Tank myTank;

    public MainGameView() {
    }

    public void keyPressed(KeyEvent e) {

        float CAMERASPEED = 0.03f;
        /////////////////////
        //forward

        if (e.getKeyCode()
                == KeyEvent.VK_UP) {
            objCamera.Move_Camera(CAMERASPEED);
        }

        //backward
        if (e.getKeyCode()
                == KeyEvent.VK_DOWN) {
            objCamera.Move_Camera(-CAMERASPEED);
        }

        //left
        if (e.getKeyCode()
                == KeyEvent.VK_LEFT) {
            objCamera.Move_Left_Right(-CAMERASPEED);
        }

        //right
        if (e.getKeyCode()
                == KeyEvent.VK_RIGHT) {
            objCamera.Move_Left_Right(CAMERASPEED);
        }

        //up
        if (e.getKeyCode()
                == KeyEvent.VK_N) {
            objCamera.Move_Up_Down(CAMERASPEED / 5);
        }

        //down
        if (e.getKeyCode()
                == KeyEvent.VK_M) {
            objCamera.Move_Up_Down(-CAMERASPEED / 5);
        }
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

        if (objCamera != null) {
            objCamera.Mouse_Move(x, y, Global.wndWidth, Global.wndHeight);
        }
    }

    public void pointerReleased(MouseEvent e) {
    }

    public void load() {
        ResourceManager.getInst().LoadInGame();
        
        //set hide cursor
        Toolkit t = Toolkit.getDefaultToolkit();
        Image i = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
        Cursor noCursor = t.createCustomCursor(i, new Point(0, 0), "none");
        GameEngine.getInst().tank3d.frame.setCursor(noCursor);
        //end - set hide cursor
        
        //init variable
        objCamera = new Camera();
        objCamera.Position_Camera(-19.760378f, 3.8099978f, -8.027661f, -15.02627f, 3.6239977f, -6.2564106f, 0.0f, 1.0f, 0.0f);
        //(0, 1.5f, 4.0f, 0, 1.5f, 0, 0, 1.0f, 0);

        //skybox
        m_skybox = new SkyBox();
        m_skybox.Initialize(5.0f);

        m_skybox.LoadTextures(
                ResourceManager.ttSkyUp, ResourceManager.ttSkyDown,
                ResourceManager.ttSkyFront, ResourceManager.ttSkyBack,
                ResourceManager.ttSkyLeft, ResourceManager.ttSkyRight);
        //init map
        Map.getInst().LoadMap("data/map/MAP0.png");

        //model
        md2Tank = new Md2();
        md2Tank.LoadModel("data/model/triax_wheels.md2");
        md2Tank.LoadSkin(ResourceManager.ttTank);

        model = new Md2();
        model.LoadModel("data/model/knight.md2");
        model.LoadSkin(ResourceManager.ttKnight);
    }

    public void unload() {
        ResourceManager.getInst().UnLoadInGame();
        //set show cursor
        GameEngine.getInst().tank3d.frame.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
        //end - set show cursor
    }

    void DrawPlane() {
        GL gl = Global.drawable.getGL();

        ResourceManager.ttGachMen.enable();
        ResourceManager.ttGachMen.bind();

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
    }

    public void display() {
        Global.drawable.getGL().glLoadIdentity();
        if (ResourceManager.isLoadInGame) {
            // use this function for opengl target camera
            (new GLU()).gluLookAt(
                    objCamera.mPos.x, objCamera.mPos.y, objCamera.mPos.z,
                    objCamera.mView.x, objCamera.mView.y, objCamera.mView.z,
                    objCamera.mUp.x, objCamera.mUp.y, objCamera.mUp.z);

            // skybox origin should be same as camera position
            m_skybox.Render(objCamera.mPos.x, objCamera.mPos.y, objCamera.mPos.z);

            this.DrawPlane();

            Map.getInst().Render(2, 4);

            GL gl = Global.drawable.getGL();

            ////////////////////////////////////////////////////////
            //camera
            gl.glPushMatrix();
                // Always keep the character in the view
                gl.glTranslatef(objCamera.mView.x, 0.0f, objCamera.mView.z);
                float dx = objCamera.mView.x - objCamera.mPos.x;
                float dz = objCamera.mView.z - objCamera.mPos.z;
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
                md2Tank.SetScale(0.03f);
                md2Tank.DrawModel(gl, 0);
            gl.glPopMatrix();
            ////////////////////////////////////////////////////////

            float h = 20;
            float scale = h / 256.0f;
            float transY = h * 9.5f / 100;
            gl.glPushMatrix();
                gl.glTranslatef(5.0f, transY, -7);
                gl.glRotatef(-90, 0, 0, 1);
                gl.glRotatef(-90, 0, 1, 0);
                model.SetScale(scale);
                model.DrawAnimate(gl, 0, 100, 0.05f);
            gl.glPopMatrix();

            Writer.Render("MAIN GAME VIEW - Escape key back to menu", "Constantia", Font.BOLD, 120, 400, 400, Color.YELLOW);
        }
    }
}
