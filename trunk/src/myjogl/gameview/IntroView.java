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
import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
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

    float scaleWind = Global.wndWidth / 800.0f;
    float scaleLogo = 0.9951f;
    Sound s;
    float x = 0, y = 0;
    float w, h;
    float xl, yl, wl, hl;
    long startLight = 3810000;
    long endLight1 = 7059000;
    long endLight2 = 7800000;
    Texture ttLogo;
    Texture ttLight;

    public IntroView() {
        System.out.println("Go to intro view------------------------------------");
    }

    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            //System.out.println("CounterLight: " + counterLight);
            System.out.println("Sound Frame: " + s.clip.getFramePosition());
            System.out.println("Sound Microsecond: " + s.clip.getMicrosecondPosition());
        }
    }

    public void keyReleased(KeyEvent e) {
        if (s.clip.getMicrosecondPosition() >= endLight1) {
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
        ttLogo = ResourceManager.getInst().getTexture("data/ttLogo.png", true, GL.GL_REPEAT, GL.GL_REPEAT, GL.GL_LINEAR, GL.GL_LINEAR);
        ttLight = ResourceManager.getInst().getTexture("data/ttLight.png", true, GL.GL_REPEAT, GL.GL_REPEAT, GL.GL_LINEAR, GL.GL_LINEAR);

        wl = ttLight.getWidth();
        hl = 2 * ttLight.getHeight();
        xl = (Global.wndWidth - wl) / 2;
        yl = (Global.wndHeight - hl) / 2;

        w = 1.5f * Global.wndWidth;
        h = ttLogo.getHeight() * w / ttLogo.getWidth();
        x = (Global.wndWidth - w) / 2;
        y = (Global.wndHeight - h) / 2;

        s = ResourceManager.getInst().getSound("sound/intro.wav", false);
        s.play();

        //preload menu
//        ResourceManager.getInst().PreLoadTexture("data/ttBgMenu.png", true, GL.GL_REPEAT, GL.GL_REPEAT, GL.GL_LINEAR, GL.GL_LINEAR);
//        ResourceManager.getInst().PreLoadTexture("data/ttButtonNormal.png", true, GL.GL_REPEAT, GL.GL_REPEAT, GL.GL_LINEAR, GL.GL_LINEAR);
//        ResourceManager.getInst().PreLoadTexture("data/ttButtonClick.png", true, GL.GL_REPEAT, GL.GL_REPEAT, GL.GL_LINEAR, GL.GL_LINEAR);
    }

    public void unload() {
        ResourceManager.getInst().deleteTexture("data/ttLight.png");
        ResourceManager.getInst().deleteTexture("data/ttLogo.png");
        ResourceManager.getInst().deleteSound(s);
    }

    public void update(long elapsedTime) {
        //light
        if (s.clip.getMicrosecondPosition() >= startLight && s.clip.getMicrosecondPosition() <= endLight1) {
            wl += 30.0f * scaleWind;
            hl += 2.0f * scaleWind;
            xl = (Global.wndWidth - wl) / 2;
            yl = (Global.wndHeight - hl) / 2;
        } else if (s.clip.getMicrosecondPosition() > endLight1 && s.clip.getMicrosecondPosition() <= endLight2) {
            wl -= 330.0f * scaleWind;
            hl -= 20.0f * scaleWind;
            xl = (Global.wndWidth - wl) / 2;
            yl = (Global.wndHeight - hl) / 2;
        }

        //logo
        if (w > ttLogo.getWidth() * scaleWind && h > ttLogo.getHeight() * scaleWind) {
            float tempw = w;
            float temph = h;
            w *= scaleLogo;
            h *= scaleLogo;

            x += (tempw - w) / 2;
            y += (temph - h) / 2;
        }
    }

    public void display() {
        //&& s.clip.getMicrosecondPosition() <= endLight
        if (s.clip.getMicrosecondPosition() >= startLight && s.clip.getMicrosecondPosition() <= endLight2) {
            Renderer.Render(ttLight, xl, yl, wl, hl);
        }

        Renderer.Render(ttLogo, x, y, w, h);

        if (s.clip.getMicrosecondPosition() >= endLight1) {
            Writer.Render("...press anykey to continue...",
                    "Constantia", Font.BOLD, 24, 1010, 32, Color.GRAY);
        }
    }
}
