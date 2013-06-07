/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package myjogl.gameview;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import myjogl.*;
import myjogl.utils.Renderer;
import myjogl.utils.ResourceManager;
import myjogl.utils.Sound;
import myjogl.utils.Writer;

/**
 *
 * @author Jundat
 */
public class IntroView implements GameView {

    boolean drawText = false;
    float scale = 0.993f;
    Sound s;
    float x = 0, y = 0;
    float w, h;

    public IntroView() {
    }

    public void keyPressed(KeyEvent e) {
    }

    public void keyReleased(KeyEvent e) {
        if ( drawText && e.getKeyCode() == KeyEvent.VK_ENTER) {
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
        w = Global.wndWidth;
        h = ResourceManager.ttLogo.getHeight() * w / ResourceManager.ttLogo.getWidth();
        x = 0;
        y = (Global.wndHeight - h) / 2;

        s = new Sound("sound/intro.wav", false);
        s.play();
    }

    public void unload() {
        s.close();
    }

    public void update(long elapsedTime) {
        if (w > ResourceManager.ttLogo.getWidth() && h > ResourceManager.ttLogo.getHeight()) {
            float tempw = w;
            float temph = h;
            w *= scale;
            h *= scale;

            x += (tempw - w) / 2;
            y += (temph - h) / 2;
        } else {
            drawText = true;
        }
    }

    public void display() {
        if (ResourceManager.isLoadOutGame) {
            Renderer.Render(ResourceManager.ttLogo, x, y, w, h);
            
            if(drawText) {
            Writer.Render("...press enter to continue...",
                    "Constantia", Font.BOLD, 24, 1050, 32, Color.GRAY);
            }
        }
    }
}
