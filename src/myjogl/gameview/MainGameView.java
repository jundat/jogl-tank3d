/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package myjogl.gameview;

import com.sun.opengl.util.texture.Texture;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import javax.media.opengl.glu.GLU;
import myjogl.GameEngine;
import myjogl.Global;
import myjogl.utils.*;

/**
 *
 * @author Jundat
 */
public class MainGameView implements GameView {

    private MenuItem itBack;
    Texture ttBgGame;
    private static SkyBox m_skybox;
    private static Camera objCamera;

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
        if (itBack.contains(e.getX(), e.getY())) {
            itBack.setIsClick(true);
        }
    }

    public void pointerMoved(MouseEvent e) {
        int x = e.getXOnScreen();
        int y = e.getYOnScreen();

        if (objCamera != null) {
            objCamera.Mouse_Move(x, y, Global.wndWidth, Global.wndHeight);
        }
    }

    public void pointerReleased(MouseEvent e) {
        if (itBack.contains(e.getX(), e.getY())) {
            itBack.setIsClick(false);

            GameEngine.getInst().attach(new MenuView());
            GameEngine.getInst().detach(this);
        }
    }

    public void load() {
        ResourceManager.getInst().LoadInGame();
        ttBgGame = ResourceManager.ttBgGame;

        itBack = new MenuItem(ResourceManager.ttButtonNormal, ResourceManager.ttButtonClick);
        itBack.SetPosition(0, 0);

        //init variable
        objCamera = new Camera();
        objCamera.Position_Camera(-19.760378f, 3.8099978f, -8.027661f, -15.02627f, 3.6239977f, -6.2564106f, 0.0f, 1.0f, 0.0f);
        //(0, 1.5f, 4.0f, 0, 1.5f, 0, 0, 1.0f, 0);

        //skybox
        m_skybox = new SkyBox();
        m_skybox.Initialize(20.0f);

        m_skybox.LoadTextures(
                ResourceManager.ttSkyUp, ResourceManager.ttSkyDown,
                ResourceManager.ttSkyFront, ResourceManager.ttSkyBack,
                ResourceManager.ttSkyLeft, ResourceManager.ttSkyRight);
    }

    public void unload() {
        ResourceManager.getInst().UnLoadInGame();
    }

    public void update(long elapsedTime) {
    }

    public void display() {
        Global.drawable.getGL().glLoadIdentity();
        if (ResourceManager.isLoadInGame) {
            //Renderer.Render(ttBgGame, 0, 0);

            // use this function for opengl target camera
            (new GLU()).gluLookAt(
                    objCamera.mPos.x, objCamera.mPos.y, objCamera.mPos.z,
                    objCamera.mView.x, objCamera.mView.y, objCamera.mView.z,
                    objCamera.mUp.x, objCamera.mUp.y, objCamera.mUp.z);

            // skybox origin should be same as camera position
            // skybox origin should be same as camera position
            m_skybox.Render(objCamera.mPos.x, objCamera.mPos.y, objCamera.mPos.z);

            itBack.Render();

            Writer.Render("MAIN GAME VIEW", "Constantia", Font.BOLD, 120, 400, 400, Color.YELLOW);
            Writer.Render("BACK", "Constantia", Font.BOLD, 60, itBack.rect.x + 30, itBack.rect.y + 30, Color.YELLOW);
        }
    }
}
