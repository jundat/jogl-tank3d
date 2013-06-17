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
import myjogl.GameEngine;
import myjogl.utils.Renderer;
import myjogl.utils.ResourceManager;

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
    }

    public void pointerReleased(MouseEvent e) {
        if (itBack.contains(e.getX(), e.getY())) {
            itBack.setIsClick(false);

            GameEngine.getInst().attach(new MenuView());
            GameEngine.getInst().detach(this);
        }
    }

    public void load() {
        itBack = new MenuItem(ResourceManager.getInst().getTexture("data/menu/btn_back.png"),
                ResourceManager.getInst().getTexture("data/menu/btn_back_press.png"));
        
        ttBgAbout = ResourceManager.getInst().getTexture("data/menu/bg_menu.png");
        ttText = ResourceManager.getInst().getTexture("data/about/bg_about.png");
        
        itBack.SetPosition(20, 20);
    }

    public void unload() {
        ResourceManager.getInst().deleteTexture("data/menu/bg_menu.png");
        ResourceManager.getInst().getTexture("data/about/bg_about.png");
        
        ResourceManager.getInst().deleteTexture("data/menu/btn_back.png");
        ResourceManager.getInst().deleteTexture("data/menu/btn_back_press.png");
    }

    public void update(long elapsedTime) {
    }

    public void display() {
        Renderer.Render(ttBgAbout, 0, 0);
        itBack.Render();
        
        Renderer.Render(ttText, p.x, p.y);
        //Writer.Render("Exception Team", "Nyala", 76, pExceptionTeam.x, pExceptionTeam.y, Color.ORANGE);
        //Writer.Render("GameUIT", "Nyala", 76, pGameUIT.x, pGameUIT.y, Color.ORANGE);
        //Writer.Render("2013", "Nyala", 76, p2013.x, p2013.y, Color.ORANGE);
    }
}
