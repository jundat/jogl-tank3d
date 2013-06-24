/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package myjogl.gameview;

import com.sun.opengl.util.texture.Texture;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import myjogl.GameEngine;
import myjogl.utils.Renderer;
import myjogl.utils.ResourceManager;
import myjogl.utils.Sound;

/**
 *
 * @author Jundat
 */
public class AboutView implements GameView {

    Point p = new Point(10, 350);
    private MenuItem itBack;
    private Texture ttBgAbout;
    private Texture ttText;

    public AboutView() {
        System.out.println("Go to about view------------------------------------");
    }

    public void keyPressed(KeyEvent e) {
    }

    public void keyReleased(KeyEvent e) {
    }

    public void pointerPressed(MouseEvent e) {
        if (itBack.contains(e.getX(), e.getY())) {
            itBack.setIsClick(true);
        }
    }

    public void pointerMoved(MouseEvent e) {
        if (itBack.contains(e.getX(), e.getY())) {
            if (itBack.isOver == false) {
                itBack.setIsOver(true);
                GameEngine.sMouseMove.play(false);
            }
        } else {
            itBack.setIsOver(false);
        }
    }

    public void pointerReleased(MouseEvent e) {
        if (itBack.contains(e.getX(), e.getY())) {
            itBack.setIsClick(false);

            GameEngine.getInst().attach(new MenuView());
            GameEngine.getInst().detach(this);

            //sound
            GameEngine.sClick.play();
        }
    }

    public void load() {
        itBack = new MenuItem(ResourceManager.getInst().getTexture("data/menu/btn.png"),
                ResourceManager.getInst().getTexture("data/menu/btn_press.png"));

        ttBgAbout = ResourceManager.getInst().getTexture("data/menu/bg_menu.png");
        ttText = ResourceManager.getInst().getTexture("data/about/bg_about.png");

        itBack.SetPosition(20, 20);
    }

    public void unload() {
        ResourceManager.getInst().getTexture("data/about/bg_about.png");
    }

    public void update(long elapsedTime) {
    }

    public void display() {
        Renderer.Render(ttBgAbout, 0, 0);
        itBack.Render();

        Renderer.Render(ttText, p.x, p.y);
        GameEngine.writer.Render("BACK", 25, 25, 0.85f, 0.85f);
    }
}
