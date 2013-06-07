/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package myjogl.utils;

import com.sun.opengl.util.texture.Texture;
import java.util.HashMap;
import javax.media.opengl.GL;

/**
 * All game resource must be loaded into before run game.
 *
 * @author Jundat
 */
public class ResourceManager {

    private HashMap textures;
    private HashMap sounds;

    private ResourceManager() {
        textures = new HashMap();
        sounds = new HashMap();
    }
    
    private static ResourceManager instance = null;

    public static ResourceManager getInst() {
        if (instance == null) {
            instance = new ResourceManager();
        }

        return instance;
    }

    public Texture getTexture(String fileName, boolean wantFlip, int wrap_s, int wrap_t, int minFilter, int magFilter) {
        if (textures.containsKey(fileName)) {
            return (Texture) textures.get(fileName);
        } else {
            Texture tt = TextureLoader.Load(fileName, wantFlip, wrap_s, wrap_t, minFilter, magFilter);
            textures.put(fileName, tt);

            return tt;
        }
    }

    public Texture getTexture(String fileName, boolean wantFlip, int wrap) {
        if (textures.containsKey(fileName)) {
            return (Texture) textures.get(fileName);
        } else {
            Texture tt = TextureLoader.Load(fileName, wantFlip, wrap, GL.GL_NEAREST);
            textures.put(fileName, tt);
            
            return tt;
        }
    }

    public Texture getTexture(String fileName) {
        if (textures.containsKey(fileName)) {
            return (Texture) textures.get(fileName);
        } else {
            Texture tt = TextureLoader.Load(fileName);
            textures.put(fileName, tt);

            return tt;
        }
    }

    public Sound getSound(String fileName, boolean isLoop) {
        if (sounds.containsKey(fileName)) {
            return (Sound) sounds.get(fileName);
        } else {
            Sound s = new Sound(fileName, isLoop);
            sounds.put(fileName, s);

            return s;
        }
    }

    public void deleteTexture(String fileName) {
        textures.remove(fileName);
    }

    public void deleteSound(String fileName) {
        sounds.remove(fileName);
    }

    public void deleteAllTextures() {
        textures.clear();
    }

    public void deleteAllSounds() {
        sounds.clear();
    }
}
