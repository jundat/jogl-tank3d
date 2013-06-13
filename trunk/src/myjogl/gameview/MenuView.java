/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package myjogl.gameview;

import com.sun.opengl.util.texture.Texture;
import java.awt.Color;
import java.awt.Font;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import myjogl.GameEngine;
import myjogl.GameEngine;
import myjogl.utils.Renderer;
import myjogl.utils.ResourceManager;
import myjogl.utils.Writer;

/**
 *
 * @author Jundat
 */
public class MenuView implements GameView {

    Point pExit = new Point(1033, 20);
    Point pAbout = new Point(1033, 120);
    Point pPlay = new Point(1033, 220);
    //int yb = 
    private MenuItem itPlay;
    private MenuItem itAbout;
    private MenuItem itExit;
    Texture ttBgMenu;

    public MenuView() {
        System.out.println("Go to menu view-------------------------------------");
    }

    public void keyPressed(KeyEvent e) {
    }

    public void keyReleased(KeyEvent e) {
    }

    public void pointerPressed(MouseEvent e) {
        if (itPlay.contains(e.getX(), e.getY())) {
            itPlay.setIsClick(true);
        }

        if (itAbout.contains(e.getX(), e.getY())) {
            itAbout.setIsClick(true);
        }

        if (itExit.contains(e.getX(), e.getY())) {
            itExit.setIsClick(true);
        }
    }

    public void pointerMoved(MouseEvent e) {
    }

    public void pointerReleased(MouseEvent e) {
        if (itPlay.contains(e.getX(), e.getY())) {
            itPlay.setIsClick(false);

            //pre-load main game
            ResourceManager.getInst().PreLoadTexture("data/game/gach_tuong.png", true, GL.GL_REPEAT, GL.GL_REPEAT, GL.GL_LINEAR, GL.GL_LINEAR);
            ResourceManager.getInst().PreLoadTexture("data/game/gach_men.png", true, GL.GL_REPEAT, GL.GL_REPEAT, GL.GL_LINEAR, GL.GL_LINEAR);
            ResourceManager.getInst().PreLoadTexture("data/model/triax_wheels.png", false, GL.GL_REPEAT, GL.GL_REPEAT, GL.GL_LINEAR, GL.GL_LINEAR);

            //skybox
            ResourceManager.getInst().PreLoadTexture("data/skybox/top.jpg", false, GL.GL_REPEAT, GL.GL_REPEAT, GL.GL_CLAMP_TO_EDGE, GL.GL_CLAMP_TO_EDGE);
            ResourceManager.getInst().PreLoadTexture("data/skybox/bottom.jpg", false, GL.GL_REPEAT, GL.GL_REPEAT, GL.GL_CLAMP_TO_EDGE, GL.GL_CLAMP_TO_EDGE);
            ResourceManager.getInst().PreLoadTexture("data/skybox/left.jpg", false, GL.GL_REPEAT, GL.GL_REPEAT, GL.GL_CLAMP_TO_EDGE, GL.GL_CLAMP_TO_EDGE);
            ResourceManager.getInst().PreLoadTexture("data/skybox/right.jpg", false, GL.GL_REPEAT, GL.GL_REPEAT, GL.GL_CLAMP_TO_EDGE, GL.GL_CLAMP_TO_EDGE);
            ResourceManager.getInst().PreLoadTexture("data/skybox/front.jpg", false, GL.GL_REPEAT, GL.GL_REPEAT, GL.GL_CLAMP_TO_EDGE, GL.GL_CLAMP_TO_EDGE);
            ResourceManager.getInst().PreLoadTexture("data/skybox/back.jpg", false, GL.GL_REPEAT, GL.GL_REPEAT, GL.GL_CLAMP_TO_EDGE, GL.GL_CLAMP_TO_EDGE);

            GameEngine.getInst().attach(new LoadingView(new MainGameView()));
            GameEngine.getInst().detach(this);
        }

        if (itAbout.contains(e.getX(), e.getY())) {
            itAbout.setIsClick(false);

            GameEngine.getInst().attach(new AboutView());
            GameEngine.getInst().detach(this);
        }

        if (itExit.contains(e.getX(), e.getY())) {
            itExit.setIsClick(false);

            GameEngine.getInst().exit();
        }
    }

    public void load() {
        ttBgMenu = ResourceManager.getInst().getTexture("data/menu/bg_menu.png");

        itPlay = new MenuItem(ResourceManager.getInst().getTexture("data/menu/btn_play.png"),
                ResourceManager.getInst().getTexture("data/menu/btn_play_press.png"));
        itAbout = new MenuItem(ResourceManager.getInst().getTexture("data/menu/btn_about.png"),
                ResourceManager.getInst().getTexture("data/menu/btn_about_press.png"));
        itExit = new MenuItem(ResourceManager.getInst().getTexture("data/menu/btn_exit.png"),
                ResourceManager.getInst().getTexture("data/menu/btn_exit_press.png"));

        itPlay.SetPosition(pPlay);
        itAbout.SetPosition(pAbout);
        itExit.SetPosition(pExit);
    }

    public void unload() {
    }

    public void update(long elapsedTime) {
    }

    public void display() {
        Renderer.Render(ttBgMenu, 0, 0);

        itPlay.Render();
        itAbout.Render();
        itExit.Render();
    }
}
