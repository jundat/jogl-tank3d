/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package myjogl.utils;

import com.sun.opengl.util.texture.Texture;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import myjogl.Global;

/**
 *
 * @author Jundat
 */
public class Map {

    public byte[][] board;
    public int width;
    public int height;
    private static Texture ttGachTuong = null;

    private static Map instance = null;
    
    public static Map getInst() {
        if(instance == null) {
            instance = new Map();
        }
        
        return instance;
    }
    
    private Map() {
        ttGachTuong = ResourceManager.getInst().getTexture("data/game/ttGachTuong.png");
    }

    //only use png file
    public void LoadMap(String fileName) {
        if (!fileName.endsWith("png")) {
            System.err.println("Map.LoadMap(): only use PNG map! - Close");
            return;
        }

        try {
            InputStream is = getClass().getResourceAsStream(fileName);
            BufferedImage image = ImageIO.read(is); //file

            this.width = image.getWidth();
            this.height = image.getHeight();

            this.board = new byte[height][width];
            int color;
            byte red, green, blue, alpha;

            for (int i = 0; i < height; ++i) //y
            {
                for (int j = 0; j < width; ++j) //x
                {
                    color = image.getRGB(j, i); //x,y

                    alpha = (byte) ((color & 0xff000000) >> 24);
                    red = (byte) ((color & 0x00ff0000) >> 16);
                    green = (byte) ((color & 0x0000ff00) >> 8);
                    blue = (byte) ((color & 0x000000ff));

                    board[i][j] = blue;
                }
            }

        } catch (IOException ex) {
            System.out.println("Map.LoadMap: can not load map!");
            Logger.getLogger(Map.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * 
     * @param drawable
     * @param h height of cube
     * @param w width of cube
     */ 
    public void Render(int w, int h) {
        int blue = 0;
        for (int i = 0; i < height; ++i) //y
        {
            for (int j = 0; j < width; ++j) //x
            {
                blue = board[i][j];
                if (blue != 0) {
                    this.DrawCube(j * w, h / 2, i * w, w, h, w);
                }
            }
        }
    }

    private void DrawCube(float x, float y, float z,
            float sx, float sy, float sz) {
        GL gl = Global.drawable.getGL();

        float x2 = sx / 2;
        float y2 = sy / 2;
        float z2 = sz / 2;

        gl.glPushMatrix();
        gl.glTranslatef(x, y, z);

        ttGachTuong.enable();
        ttGachTuong.bind();

        gl.glBegin(GL.GL_QUADS);        // Draw The Cube Using quads
            gl.glNormal3f(0, 1, 0);
            //glColor3f(0.0f,1.0f,0.0f);    // Color Blue
            gl.glTexCoord2f(sx, sz);
            gl.glVertex3f(x2, y2, -z2);    // Top Right Of The Quad (Top)
            gl.glTexCoord2f(0.0f, sz);
            gl.glVertex3f(-x2, y2, -z2);    // Top Left Of The Quad (Top)
            gl.glTexCoord2f(0.0f, 0.0f);
            gl.glVertex3f(-x2, y2, z2);    // Bottom Left Of The Quad (Top)
            gl.glTexCoord2f(sx, 0.0f);
            gl.glVertex3f(x2, y2, z2);    // Bottom Right Of The Quad (Top)

            gl.glNormal3f(0, -1, 0);
            //glColor3f(1.0f,0.5f,0.0f);    // Color Orange
            gl.glTexCoord2f(sx, sz);
            gl.glVertex3f(x2, -y2, z2);    // Top Right Of The Quad (Bottom)
            gl.glTexCoord2f(0.0f, sz);
            gl.glVertex3f(-x2, -y2, z2);    // Top Left Of The Quad (Bottom)
            gl.glTexCoord2f(0.0f, 0.0f);
            gl.glVertex3f(-x2, -y2, -z2);    // Bottom Left Of The Quad (Bottom)
            gl.glTexCoord2f(sx, 0.0f);
            gl.glVertex3f(x2, -y2, -z2);    // Bottom Right Of The Quad (Bottom)

            gl.glNormal3f(0, 0, 1);
            //glColor3f(1.0f,0.0f,0.0f);    // Color Red    
            gl.glTexCoord2f(sx, sy);
            gl.glVertex3f(x2, y2, z2);    // Top Right Of The Quad (Front)
            gl.glTexCoord2f(0.0f, sy);
            gl.glVertex3f(-x2, y2, z2);    // Top Left Of The Quad (Front)
            gl.glTexCoord2f(0.0f, 0.0f);
            gl.glVertex3f(-x2, -y2, z2);    // Bottom Left Of The Quad (Front)
            gl.glTexCoord2f(sx, 0.0f);
            gl.glVertex3f(x2, -y2, z2);    // Bottom Right Of The Quad (Front)

            gl.glNormal3f(0, 0, -1);
            //glColor3f(1.0f,1.0f,0.0f);    // Color Yellow
            gl.glTexCoord2f(sx, sy);
            gl.glVertex3f(x2, -y2, -z2);    // Top Right Of The Quad (Back)
            gl.glTexCoord2f(0.0f, sy);
            gl.glVertex3f(-x2, -y2, -z2);    // Top Left Of The Quad (Back)
            gl.glTexCoord2f(0.0f, 0.0f);
            gl.glVertex3f(-x2, y2, -z2);    // Bottom Left Of The Quad (Back)
            gl.glTexCoord2f(sx, 0.0f);
            gl.glVertex3f(x2, y2, -z2);    // Bottom Right Of The Quad (Back)

            gl.glNormal3f(-1, 0, 0);
            //glColor3f(0.0f,0.0f,1.0f);    // Color Blue
            gl.glTexCoord2f(sz, sy);
            gl.glVertex3f(-x2, y2, z2);    // Top Right Of The Quad (Left)
            gl.glTexCoord2f(0.0f, sy);
            gl.glVertex3f(-x2, y2, -z2);    // Top Left Of The Quad (Left)
            gl.glTexCoord2f(0.0f, 0.0f);
            gl.glVertex3f(-x2, -y2, -z2);    // Bottom Left Of The Quad (Left)
            gl.glTexCoord2f(sz, 0.0f);
            gl.glVertex3f(-x2, -y2, z2);    // Bottom Right Of The Quad (Left)

            gl.glNormal3f(1, 0, 0);
            //glColor3f(1.0f,0.0f,1.0f);    // Color Violet
            gl.glTexCoord2f(sz, sy);
            gl.glVertex3f(x2, y2, -z2);    // Top Right Of The Quad (Right)
            gl.glTexCoord2f(0.0f, sy);
            gl.glVertex3f(x2, y2, z2);    // Top Left Of The Quad (Right)
            gl.glTexCoord2f(0.0f, 0.0f);
            gl.glVertex3f(x2, -y2, z2);    // Bottom Left Of The Quad (Right)
            gl.glTexCoord2f(sz, 0.0f);
            gl.glVertex3f(x2, -y2, -z2);    // Bottom Right Of The Quad (Right)

            gl.glEnd();            // End Drawing The Cube
        gl.glPopMatrix();

        //reset color
        gl.glColor4f(1, 1, 1, 1);

        ttGachTuong.disable();
    }
}
