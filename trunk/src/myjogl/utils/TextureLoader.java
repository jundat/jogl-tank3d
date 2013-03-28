/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package myjogl.utils;

import com.sun.opengl.util.texture.Texture;
import com.sun.opengl.util.texture.TextureIO;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import javax.imageio.ImageIO;
import javax.media.opengl.GL;
import javax.swing.JOptionPane;
import myjogl.Global;

/**
 * A helper to load texture, and flip it up-side down if you want
 *
 * @author Jundat
 */
public class TextureLoader {

    /**
     *
     * @param drawable
     * @param filename
     * @param wantFlip
     * @param GL_REPEAT : GL.GL_REPEAT, GL.GL_CLAMP, ...
     * @return
     */
    public static Texture Load(String filename, boolean wantFlip, int GL_REPEAT) {
        GL gl = Global.drawable.getGL();
        Texture tt = null;

        InputStream is = null;
        BufferedImage tBufferedImage = null;

        //Load resource
        try {
            is = (new TextureLoader()).getClass().getResourceAsStream(filename);
            tBufferedImage = ImageIO.read(new BufferedInputStream(is));

            if (wantFlip) {
                tBufferedImage = flipBufferedImageVertical(tBufferedImage);
            }

            tt = TextureIO.newTexture(tBufferedImage, true);
            gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_WRAP_T, GL_REPEAT);
            gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_WRAP_S, GL_REPEAT);

        } catch (Exception exc) {
            System.out.println("TextureLoader: Can not load resource: " + exc.getMessage());
            JOptionPane.showMessageDialog(null, "TextureLoader: Can not load resource: " + filename + "\n" + exc.getMessage());
        } finally {
            try {
                tBufferedImage.flush();
                is.close();
                
            } catch (IOException ex) {
                System.out.println("Can not close inputstream " + ex.getMessage());
                JOptionPane.showMessageDialog(null, "TextureLoader: Can not close inputstream resource: " + filename + "\n" + ex.getMessage());
            }
        }

        return tt;
    }

    /**
     * Flip a texture Up-side down
     *
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
