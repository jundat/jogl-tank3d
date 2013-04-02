/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package myjogl.gameview;

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
    //Texture ttBgAbout;

    public AboutView() {        
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

    public void load() {ResourceManager.getInst().LoadOutGame();
        //ttBgAbout = ResourceManager.ttBgAbout;

        itBack = new MenuItem(ResourceManager.ttButtonNormal, ResourceManager.ttButtonClick);

        itBack.SetPosition(0, 0);        
    }

    public void unload() {
        ResourceManager.getInst().UnLoadOutGame();
    }

    public void update(long elapsedTime) {
    }

    public void display() {
        if (ResourceManager.isLoadOutGame) {
            Renderer.Render(ResourceManager.ttBgAbout, 0, 0);

            itBack.Render();

            Writer.Render("BACK", "Constantia", Font.BOLD, 60, itBack.rect.x + 30, itBack.rect.y + 30, Color.YELLOW);
        }
    }
}
