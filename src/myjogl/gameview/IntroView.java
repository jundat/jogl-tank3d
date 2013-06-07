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
    
    Texture ttLogo;

    public IntroView() {
    }

    public void keyPressed(KeyEvent e) {
    }

    public void keyReleased(KeyEvent e) {
        if (drawText && e.getKeyCode() == KeyEvent.VK_ENTER) {
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
        ttLogo = ResourceManager.getInst().getTexture("data/ttLogo.png");

        w = Global.wndWidth;
        h = ttLogo.getHeight() * w / ttLogo.getWidth();
        x = 0;
        y = (Global.wndHeight - h) / 2;

        s = ResourceManager.getInst().getSound("sound/intro.wav", false);
        s.play();
    }

    public void unload() {
        s.close();
    }

    public void update(long elapsedTime) {
        if (w > ttLogo.getWidth() && h > ttLogo.getHeight()) {
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
        Renderer.Render(ttLogo, x, y, w, h);

        if (drawText) {
            Writer.Render("...press enter to continue...",
                    "Constantia", Font.BOLD, 24, 1050, 32, Color.GRAY);
        }
    }
}
