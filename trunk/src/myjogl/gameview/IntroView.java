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
public class IntroView implements GameView {

    public IntroView() {
    }

    public void keyPressed(KeyEvent e) {
    }

    public void keyReleased(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_ENTER) {
            GameEngine.getInst().attach(new MenuView());
            GameEngine.getInst().detach(this);
        }
    }

    public void pointerPressed(MouseEvent e) {
    }

    public void pointerMoved(MouseEvent e) {
    }

    public void pointerReleased(MouseEvent e) {
    }
    
    public void load() {
        ResourceManager.getInst().LoadOutGame();
    }

    public void unload() {
        ResourceManager.getInst().UnLoadOutGame();
    }

    public void update(long elapsedTime) {
    }

    public void display() {
        if (ResourceManager.isLoadOutGame) {
            Renderer.Render(ResourceManager.ttBgIntro, 0, 0);
            Renderer.Render(ResourceManager.ttLogo, 400, 300);
            Writer.Render("press Enter to skip!", "Constantia", Font.BOLD, 20, 200, 200, Color.RED);
        }
    }

}
