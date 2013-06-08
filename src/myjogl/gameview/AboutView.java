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
import myjogl.GameEngine;
import myjogl.utils.Renderer;
import myjogl.utils.ResourceManager;
import myjogl.utils.Writer;

/**
 *
 * @author Jundat
 */
public class AboutView implements GameView {

    private MenuItem itBack;
    private Texture ttBgAbout;
    //Texture ttBgAbout;

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
    }

    public void pointerReleased(MouseEvent e) {
        if (itBack.contains(e.getX(), e.getY())) {
            itBack.setIsClick(false);

            GameEngine.getInst().attach(new MenuView());
            GameEngine.getInst().detach(this);
        }
    }

    public void load() {
        itBack = new MenuItem(ResourceManager.getInst().getTexture("data/ttButtonNormal.png"),
                ResourceManager.getInst().getTexture("data/ttButtonClick.png"));
        
        ttBgAbout = ResourceManager.getInst().getTexture("data/ttBgAbout.png");
        itBack.SetPosition(0, 0);
    }

    public void unload() {
        ResourceManager.getInst().deleteTexture("data/ttBgAbout.png");
        ResourceManager.getInst().deleteTexture("data/ttButtonNormal.png");
        ResourceManager.getInst().deleteTexture("data/ttButtonClick.png");
    }

    public void update(long elapsedTime) {
    }

    public void display() {
        Renderer.Render(ttBgAbout, 0, 0);

        itBack.Render();

        Writer.Render("BACK", "Constantia", Font.BOLD, 60, itBack.rect.x + 30, itBack.rect.y + 30, Color.YELLOW);
    }
}
