/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import com.sun.opengl.util.texture.Texture;
import com.sun.opengl.util.texture.TextureIO;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import javax.imageio.ImageIO;
import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import javax.swing.JOptionPane;

/**
 * A helper to load texture, and flip it up-side down if you want
 * @author Jundat
 */
public class TextureLoader {
    
    /**
     * 
     * @param drawable
     * @param filename
     * @param wantFlip
     * @param GL_TEXT_PARA : GL.GL_REPEAT, GL.GL_CLAMP, ...
     * @return 
     */
    public static Texture Load(GLAutoDrawable drawable, String filename, boolean wantFlip, int GL_TEXT_PARA)
    {
        GL gl = drawable.getGL();
        Texture tt = null;
        
        //Load resource
        try {
            InputStream is;
            BufferedImage tBufferedImage;
            
            //gach tuong
            is = (new TextureLoader()).getClass().getResourceAsStream(filename);
            tBufferedImage = ImageIO.read(new BufferedInputStream(is)); 
            
            if(wantFlip)
                tBufferedImage = flipBufferedImageVertical(tBufferedImage);
            
            tt = TextureIO.newTexture(tBufferedImage, true);
            gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_WRAP_T, GL_TEXT_PARA);
            gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_WRAP_S, GL_TEXT_PARA);
            
        } catch (IOException exc) {
            System.out.println("TextureLoader: Can not load resource: " + exc.getMessage());
            JOptionPane.showMessageDialog(null, "TextureLoader: Can not load resource: " + filename + "\n" + exc.getMessage());
        }
        
        return tt;
    }
    
    /**
     * Flip a texture Up-side down
     * @param inBufferedImage
     * @return 
     */
    private static BufferedImage flipBufferedImageVertical(BufferedImage inBufferedImage) { 
        int tWidth = inBufferedImage.getWidth(); 
        int tHeight = inBufferedImage.getHeight(); 
        BufferedImage tFlippedBufferedImage = new BufferedImage(tWidth, tHeight, inBufferedImage.getType()); 
        Graphics2D tG2D = tFlippedBufferedImage.createGraphics(); 
        tG2D.drawImage(inBufferedImage, 0, 0, tWidth, tHeight, 0, tHeight, tWidth, 0, null); 
        tG2D.dispose(); 
        return tFlippedBufferedImage; 
    }
}
