/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import com.sun.opengl.util.texture.Texture;
import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;

/**
 *
 * @author Jundat
 */
public class ResourceManager {

    private static boolean isLoadIntro = false;
    private static boolean isLoadMenu = false;
    private static boolean isLoadAbout = false;
    private static boolean isLoadGame = false;
    
    //Resource
    //IntroView
    public static Texture ttBgIntro;
    
    
    //MenuView
    public static Texture ttBgMenu;
    
    
    //AboutView
    public static Texture ttBgAbout;
    
    
    //GameView
    
    
    

    private ResourceManager() {
    }
    
    public static ResourceManager instance = null;

    public static ResourceManager getInst() {
        if (instance == null) {
            instance = new ResourceManager();
        }

        return instance;
    }

    //IntroView
    /**
     * Load introview resource
     * @param drawable 
     */
    public void LoadIntro(GLAutoDrawable drawable) {
        if (isLoadIntro == false) {
            ttBgIntro = TextureLoader.Load(drawable, "../data/ttBgIntro.png", true, GL.GL_REPEAT);

            ResourceManager.isLoadIntro = true;
        }
    }

    /**
     * Load introview resource
     */
    public void UnLoadIntro() {
        if (isLoadIntro == true) {
            ttBgIntro.dispose();
            
            ResourceManager.isLoadIntro = false;
        }
    }
    
    //MenuView
    /**
     * Load menuview resource
     * @param drawable 
     */
    public void LoadMenu(GLAutoDrawable drawable) {
        if (isLoadMenu == false) {


            ResourceManager.isLoadMenu = true;
        }
    }

    /**
     * Load menuview resource
     */
    public void UnLoadMenu() {
        if (isLoadMenu == true) {
            
            
            ResourceManager.isLoadMenu = false;
        }
    }
    
    //AboutView
    /**
     * Load aboutview resource
     * @param drawable 
     */
    public void LoadAbout(GLAutoDrawable drawable) {
        if (isLoadAbout == false) {


            ResourceManager.isLoadAbout = true;
        }
    }

    /**
     * Load aboutview resource
     */
    public void UnLoadAbout() {
        if (isLoadAbout == true) {
            
            
            ResourceManager.isLoadAbout = false;
        }
    }
    
    //GameView
    /**
     * Load gameview resource
     * @param drawable 
     */
    public void LoadGame(GLAutoDrawable drawable) {
        if (isLoadGame == false) {


            ResourceManager.isLoadGame = true;
        }
    }

    /**
     * Load gameview resource
     */
    public void UnLoadGame() {
        if (isLoadGame == true) {
            
            
            ResourceManager.isLoadGame = false;
        }
    }
}
