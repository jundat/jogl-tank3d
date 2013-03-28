/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package myjogl.utils;

import com.sun.opengl.util.texture.Texture;
import javax.media.opengl.GL;

/**
 *
 * @author Jundat
 */
public class ResourceManager {

    public static boolean isLoadOutGame = false;
    public static boolean isLoadInGame = false;
    //Resource
    //IntroView
    public static Texture ttBgIntro;
    public static Texture ttLogo;
    //MenuView
    public static Texture ttBgMenu;
    public static Texture ttButtonNormal;
    public static Texture ttButtonClick;
    //AboutView
    public static Texture ttBgAbout;

    //GameView
    public static Texture ttBgGame;
    //skybox
    public static Texture ttSkyUp;
    public static Texture ttSkyDown;
    public static Texture ttSkyFront;
    public static Texture ttSkyBack;
    public static Texture ttSkyLeft;
    public static Texture ttSkyRight;
    
    private ResourceManager() {
    }
    public static ResourceManager instance = null;

    public static ResourceManager getInst() {
        if (instance == null) {
            instance = new ResourceManager();
        }

        return instance;
    }

    //MenuView
    /**
     * Load menu view resource
     *
     * @param drawable
     */
    public void LoadOutGame() {
        if (isLoadOutGame == false) {
            //intro
            ttBgIntro = TextureLoader.Load("data/ttBgIntro.png", true, GL.GL_REPEAT);
            ttLogo = TextureLoader.Load("data/ttLogo.png", true, GL.GL_REPEAT);

            //menu
            ttBgMenu = TextureLoader.Load("data/ttBgMenu.png", true, GL.GL_REPEAT);
            ttButtonNormal = TextureLoader.Load("data/ttButton.png", true, GL.GL_REPEAT);
            ttButtonClick = TextureLoader.Load("data/ttButtonClick.png", true, GL.GL_REPEAT);

            //about
            ttBgAbout = TextureLoader.Load("data/ttBgAbout.png", true, GL.GL_REPEAT);

            ResourceManager.isLoadOutGame = true;
        }
    }

    /**
     * Load menuview resource
     */
    public void UnLoadOutGame() {
//        if (isLoadOutGame == true) {
//            ResourceManager.isLoadOutGame = false;
//        }
    }

    //GameView
    /**
     * Load gameview resource
     *
     * @param drawable
     */
    public void LoadInGame() {
        if (isLoadInGame == false) {
            ttBgGame = TextureLoader.Load("data/ttBgGame.png", true, GL.GL_REPEAT);
            //skybox
            ttSkyUp = TextureLoader.Load("data/skybox/up.bmp", true, GL.GL_CLAMP_TO_EDGE);
            ttSkyDown = TextureLoader.Load("data/skybox/down.bmp", true, GL.GL_CLAMP_TO_EDGE);
            ttSkyFront = TextureLoader.Load("data/skybox/front.bmp", true, GL.GL_CLAMP_TO_EDGE);
            ttSkyBack = TextureLoader.Load("data/skybox/back.bmp", true, GL.GL_CLAMP_TO_EDGE);
            ttSkyLeft = TextureLoader.Load("data/skybox/left.bmp", true, GL.GL_CLAMP_TO_EDGE);
            ttSkyRight = TextureLoader.Load("data/skybox/right.bmp", true, GL.GL_CLAMP_TO_EDGE);

            ResourceManager.isLoadInGame = true;
        }
    }

    /**
     * Load gameview resource
     */
    public void UnLoadInGame() {
//        if (isLoadInGame == true) {
//            ResourceManager.isLoadInGame = false;
//        }
    }
}
