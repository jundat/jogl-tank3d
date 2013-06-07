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
public class MenuView implements GameView {

    private MenuItem itPlay;
    private MenuItem itAbout;
    private MenuItem itExit;
    Texture ttBgMenu;

    public MenuView() {}

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
            
            GameEngine.getInst().attach(new MainGameView());
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
        ttBgMenu = ResourceManager.getInst().getTexture("data/ttBgMenu.png");

        itPlay = new MenuItem( ResourceManager.getInst().getTexture("data/ttButtonNormal.png"),
                ResourceManager.getInst().getTexture("data/ttButtonClick.png") );
        itAbout = new MenuItem( ResourceManager.getInst().getTexture("data/ttButtonNormal.png"),
                ResourceManager.getInst().getTexture("data/ttButtonClick.png") );
        itExit = new MenuItem( ResourceManager.getInst().getTexture("data/ttButtonNormal.png"),
                ResourceManager.getInst().getTexture("data/ttButtonClick.png") );

        itPlay.SetPosition(84, 0);
        itAbout.SetPosition(384, 0);
        itExit.SetPosition(684, 0);
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

            Writer.Render("PLAY", "Constantia", Font.BOLD, 60, itPlay.rect.x + 30, itPlay.rect.y + 30, Color.YELLOW);
            Writer.Render("ABOUT", "Constantia", Font.BOLD, 60, itAbout.rect.x + 30, itAbout.rect.y + 30, Color.YELLOW);
            Writer.Render("EXIT", "Constantia", Font.BOLD, 60, itExit.rect.x + 30, itExit.rect.y + 30, Color.YELLOW);
    }
}
