/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package myjogl.gameview;

import com.sun.opengl.util.texture.Texture;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import myjogl.GameEngine;
import myjogl.Global;
import myjogl.utils.Renderer;
import myjogl.utils.ResourceManager;

/**
 *
 * @author Jundat
 */
public class IntroView implements GameView {

    public Texture ttBgIntro;
    public Texture ttLogo;
    boolean isDead = false;
    int x = 0;
    int y = 300;

    public IntroView() {
    }

    public void keyPressed(KeyEvent e) {
    }

    public void keyReleased(KeyEvent e) {
    }

    public void pointerPressed(MouseEvent e) {
    }

    public void pointerMoved(MouseEvent e) {
    }

    public void pointerReleased(MouseEvent e) {
    }
    
    public void load() {
        ResourceManager.getInst().LoadOutGame();

        ttBgIntro = ResourceManager.ttBgIntro;
        ttLogo = ResourceManager.ttLogo;
        x = - ttLogo.getWidth();
    }

    public void unload() {
        ResourceManager.getInst().LoadOutGame();
    }

    public void update(long elapsedTime) {
        x += (elapsedTime / 2);
        if (x >= Global.wndWidth && isDead == false) {
            isDead = true;

            GameEngine.getInst().attach(new MenuView());
            GameEngine.getInst().detach(this);
        }
    }

    public void display() {
        if (ResourceManager.isLoadOutGame) {
            Renderer.Render(ttBgIntro, 0, 0);

            float alpha = (float) x / (float) Global.wndWidth;
            Renderer.Render(ttLogo, x, y, alpha);
        }
    }

}
