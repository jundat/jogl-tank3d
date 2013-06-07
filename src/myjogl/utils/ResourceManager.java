/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package myjogl.utils;

import com.sun.opengl.util.texture.Texture;
import javax.media.opengl.GL;

/**
 *All game resource must be loaded into before run game.
 * @author Jundat
 */
public class ResourceManager {

    //public static boolean isLoadIntro = false;
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
    public static Texture ttGachTuong;
    public static Texture ttGachMen;
    
    //model texture
    public static Texture ttTank;
    public static Texture ttKnight;
    
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
     * Load menu, intro, about, view resource
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

            
            //must be in the end of function
            ResourceManager.isLoadOutGame = true;
        }
    }

    /**
     * Load menu, intro, about, view resource
     */
    public void UnLoadOutGame() {
    }

    //GameView
    /**
     * Load maingameview resource
     *
     * @param drawable
     */
    public void LoadInGame() {
        if (isLoadInGame == false) {
            
            ttBgGame = TextureLoader.Load("data/ttBgGame.png", true, GL.GL_REPEAT);
            ttGachTuong = TextureLoader.Load("data/game/gach_tuong.png", true, GL.GL_REPEAT);
            ttGachMen = TextureLoader.Load("data/game/gach_men.png", true, GL.GL_REPEAT);
            
            //model texture
            ttTank = TextureLoader.Load("data/model/triax_wheels.png", false, GL.GL_REPEAT);
            ttKnight = TextureLoader.Load("data/model/knight.png", false, GL.GL_REPEAT);
            
            //skybox
            ttSkyUp = TextureLoader.Load("data/skybox/up.jpg", false, GL.GL_CLAMP_TO_EDGE);
            ttSkyDown = TextureLoader.Load("data/skybox/down.jpg", false, GL.GL_CLAMP_TO_EDGE);
            ttSkyFront = TextureLoader.Load("data/skybox/front.jpg", false, GL.GL_CLAMP_TO_EDGE);
            ttSkyBack = TextureLoader.Load("data/skybox/back.jpg", false, GL.GL_CLAMP_TO_EDGE);
            ttSkyLeft = TextureLoader.Load("data/skybox/left.jpg", false, GL.GL_CLAMP_TO_EDGE);
            ttSkyRight = TextureLoader.Load("data/skybox/right.jpg", false, GL.GL_CLAMP_TO_EDGE);

            //load your resource here
            
            
            //must be in the end of function
            ResourceManager.isLoadInGame = true;
        }
    }

    /**
     * Load maingameview resource
     */
    public void UnLoadInGame() {
    }
}
