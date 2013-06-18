/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package myjogl.utils;

import com.sun.opengl.util.texture.Texture;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import myjogl.Global;
import myjogl.gameobjects.CRectangle;

class ID {

    public static final byte BOSS = 1;
    public static final byte BOSS_AI = 2;
    public static final byte TANK = 3;
    public static final byte TANK_AI = 4;
    //
    public static final byte BRICK = 11;
    public static final byte ROCK = 12;
    public static final byte TREE = 13;
    public static final byte WATER = 14;
    //
}

public class TankMap {

    public static final float TILE_WIDTH = 1;
    public static final float TILE_HEIGHT = 2;
    public static final float TILE_BORDER = 2;
    //
    public byte[][] board;
    public int width;
    public int height;
    //
    public ArrayList listTankAiPosition;
    public ArrayList listTankPosition;
    public Vector3 bossPosition;
    public Vector3 bossAiPosition;
    //
    private static Texture ttGachTuong = null;
    private static Texture ttGachTuong2 = null;
    private static Texture ttGachMen = null;
    //
    final float[] redLightColor = {1.0f, 1.0f, 1.0f, 1.0f}; //red
    final float[] redLightPos = {0.0f, 0.0f, 0.0f, 1.0f};
    //
    private static TankMap instance = null;
    //

    public static TankMap getInst() {
        if (instance == null) {
            instance = new TankMap();
        }

        return instance;
    }

    private TankMap() {
        ttGachTuong = ResourceManager.getInst().getTexture("data/game/gach_tuong.png", true, GL.GL_REPEAT, GL.GL_REPEAT, GL.GL_LINEAR_MIPMAP_LINEAR, GL.GL_LINEAR_MIPMAP_LINEAR);
        ttGachTuong2 = ResourceManager.getInst().getTexture("data/game/gach_tuong2.png", true, GL.GL_REPEAT, GL.GL_REPEAT, GL.GL_LINEAR_MIPMAP_LINEAR, GL.GL_LINEAR_MIPMAP_LINEAR);
        ttGachMen = ResourceManager.getInst().getTexture("data/game/gach_men.png", true, GL.GL_REPEAT, GL.GL_REPEAT, GL.GL_LINEAR_MIPMAP_LINEAR, GL.GL_LINEAR_MIPMAP_LINEAR);
        //
        listTankPosition = new ArrayList();
        listTankAiPosition = new ArrayList();
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

            for (int i = 0; i < height; ++i) //z
            {
                for (int j = 0; j < width; ++j) //x
                {
                    color = image.getRGB(j, i); //x,z

                    alpha = (byte) ((color & 0xff000000) >> 24);
                    red = (byte) ((color & 0x00ff0000) >> 16);
                    green = (byte) ((color & 0x0000ff00) >> 8);
                    blue = (byte) ((color & 0x000000ff));

                    board[i][j] = blue;
                    //
                    if (blue == ID.TANK) {
                        listTankPosition.add(new Vector3(j, 0, i));
                    } else if (blue == ID.TANK_AI) {
                        listTankAiPosition.add(new Vector3(j, 0, i));
                    } else if (blue == ID.BOSS) {
                        bossPosition = new Vector3(j, 0, i);
                    } else if (blue == ID.BOSS_AI) {
                        bossAiPosition = new Vector3(j, 0, i);
                    }
                }
            }

        } catch (IOException ex) {
            System.out.println("Map.LoadMap: can not load map!");
            Logger.getLogger(TankMap.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     *
     * @param drawable
     * @param h height of cube
     * @param w width of cube
     */
    public void Render() { /*float w, float h*/
        GL gl = Global.drawable.getGL();

        this.drawPlane(-(int) TILE_BORDER, -(int) TILE_BORDER, width + (int) TILE_BORDER, height + (int) TILE_BORDER);
        this.drawAsix();
        this.drawBorder();

        int blue = 0;
        for (int i = 0; i < height; ++i) //z
        {
            for (int j = 0; j < width; ++j) //x
            {
                blue = board[i][j];
                //
                if (blue == ID.BRICK) {
                    this.drawCube(j * TILE_WIDTH, 0, i * TILE_WIDTH,
                            TILE_WIDTH, TILE_HEIGHT, TILE_WIDTH);
                }
            }
        }
    }

    public boolean isIntersect(CRectangle rect) {
        int x = (int) rect.x;
        int y = (int) rect.y;
        int r = Global.getUpper(rect.x + rect.w);
        int b = Global.getUpper(rect.y + rect.h);

        for (int i = y; i < b; i++) {
            for (int j = x; j < r; j++) {
                if (j >= 0 && j < width && i >= 0 && i < height) {
                    if (board[i][j] == ID.BRICK) {
                        return true;
                    }
                }

            }
        }

        return false;
    }

    /**
     * @param x
     * @param y meant z
     */
    public void delete(int z, int x) {
        if (x >= 0 && x < width && z >= 0 && z < height) {
            board[z][x] = 0;
        }
    }

    private void drawBorder() {
        int border = (int) TILE_BORDER;
        for (int i = -border; i < height + border; ++i) //z
        {
            this.drawCube(-border, 0, i * TILE_WIDTH,
                    TILE_WIDTH, TILE_HEIGHT, TILE_WIDTH);
            this.drawCube(-border + 1, 0, i * TILE_WIDTH,
                    TILE_WIDTH, TILE_HEIGHT, TILE_WIDTH);
            this.drawCube(width + border - 1, 0, i * TILE_WIDTH,
                    TILE_WIDTH, TILE_HEIGHT, TILE_WIDTH);
            this.drawCube(width + border - 2, 0, i * TILE_WIDTH,
                    TILE_WIDTH, TILE_HEIGHT, TILE_WIDTH);
        }

        for (int i = -border; i < width + border; i++) {
            this.drawCube(i * TILE_WIDTH, 0, -border,
                    TILE_WIDTH, TILE_HEIGHT, TILE_WIDTH);
            this.drawCube(i * TILE_WIDTH, 0, -border + 1,
                    TILE_WIDTH, TILE_HEIGHT, TILE_WIDTH);
            this.drawCube(i * TILE_WIDTH, 0, height + border - 1,
                    TILE_WIDTH, TILE_HEIGHT, TILE_WIDTH);
            this.drawCube(i * TILE_WIDTH, 0, height + border - 2,
                    TILE_WIDTH, TILE_HEIGHT, TILE_WIDTH);
        }
    }

    private void drawPlane(int x, int z, int w, int h) {
        GL gl = Global.drawable.getGL();

        ttGachMen.enable();
        ttGachMen.bind();

        gl.glBegin(GL.GL_QUADS);
        {
            gl.glNormal3f(0.0f, 1.0f, 0.0f);

            gl.glTexCoord2f(w, 0);
            gl.glVertex3f(w + x, 0, h + z);

            gl.glTexCoord2f(w, h);
            gl.glVertex3f(w + x, 0, z);

            gl.glTexCoord2f(0, h);
            gl.glVertex3f(x, 0, z);

            gl.glTexCoord2f(0, 0);
            gl.glVertex3f(x, 0, h + z);
        }
        gl.glEnd();

        //reset color
        gl.glColor4f(1, 1, 1, 1);
    }

    private void drawAsix() {
        GL gl = Global.drawable.getGL();
        gl.glPushMatrix();
        {
            gl.glLoadIdentity();
            gl.glBegin(GL.GL_LINES);
            {
                gl.glColor3f(1, 0, 0); //red: Ox
                gl.glVertex3f(1, 1, 1);
                gl.glVertex3f(100, 1, 1);

                gl.glColor3f(0, 1, 0); //green: Oy
                gl.glVertex3f(1, 1, 1);
                gl.glVertex3f(1, 100, 1);

                gl.glColor3f(0, 0, 1); //blue: Oz
                gl.glVertex3f(1, 1, 1);
                gl.glVertex3f(1, 1, 100);
            }
            gl.glEnd();
        }
        gl.glPopMatrix();
    }

    public void drawCube(float x, float y, float z,
            float sx, float sy, float sz) {
        GL gl = Global.drawable.getGL();

        gl.glPushMatrix();
        gl.glTranslatef(x, y, z);

        ttGachTuong2.enable();
        ttGachTuong2.bind();
        gl.glBegin(GL.GL_QUADS);        // Draw The Cube Using quads
        {
            gl.glNormal3f(0, 1, 0);
            //glColor3f(0.0f,1.0f,0.0f);    // Color Blue
            gl.glTexCoord2f(sx, sz);
            gl.glVertex3f(sx, sy, 0);    // Top Right Of The Quad (Top)
            gl.glTexCoord2f(0.0f, sz);
            gl.glVertex3f(0, sy, 0);    // Top Left Of The Quad (Top)
            gl.glTexCoord2f(0.0f, 0.0f);
            gl.glVertex3f(0, sy, sz);    // Bottom Left Of The Quad (Top)
            gl.glTexCoord2f(sx, 0.0f);
            gl.glVertex3f(sx, sy, sz);    // Bottom Right Of The Quad (Top)
        }
        gl.glEnd();
        ttGachTuong2.disable();

        ttGachTuong.enable();
        ttGachTuong.bind();
        gl.glBegin(GL.GL_QUADS);        // Draw The Cube Using quads
        {
            //top is in upper

            gl.glNormal3f(0, -1, 0);
            //glColor3f(1.0f,0.5f,0.0f);    // Color Orange
            gl.glTexCoord2f(sx, sz);
            gl.glVertex3f(sx, 0, sz);    // Top Right Of The Quad (Bottom)
            gl.glTexCoord2f(0.0f, sz);
            gl.glVertex3f(0, 0, sz);    // Top Left Of The Quad (Bottom)
            gl.glTexCoord2f(0.0f, 0.0f);
            gl.glVertex3f(0, 0, 0);    // Bottom Left Of The Quad (Bottom)
            gl.glTexCoord2f(sx, 0.0f);
            gl.glVertex3f(sx, 0, 0);    // Bottom Right Of The Quad (Bottom)

            gl.glNormal3f(0, 0, 1);
            //glColor3f(1.0f,0.0f,0.0f);    // Color Red    
            gl.glTexCoord2f(sx, sy);
            gl.glVertex3f(sx, sy, sz);    // Top Right Of The Quad (Front)
            gl.glTexCoord2f(0.0f, sy);
            gl.glVertex3f(0, sy, sz);    // Top Left Of The Quad (Front)
            gl.glTexCoord2f(0.0f, 0.0f);
            gl.glVertex3f(0, 0, sz);    // Bottom Left Of The Quad (Front)
            gl.glTexCoord2f(sx, 0.0f);
            gl.glVertex3f(sx, 0, sz);    // Bottom Right Of The Quad (Front)

            gl.glNormal3f(0, 0, -1);
            //glColor3f(1.0f,1.0f,0.0f);    // Color Yellow
            gl.glTexCoord2f(sx, sy);
            gl.glVertex3f(sx, 0, 0);    // Top Right Of The Quad (Back)
            gl.glTexCoord2f(0.0f, sy);
            gl.glVertex3f(0, 0, 0);    // Top Left Of The Quad (Back)
            gl.glTexCoord2f(0.0f, 0.0f);
            gl.glVertex3f(0, sy, 0);    // Bottom Left Of The Quad (Back)
            gl.glTexCoord2f(sx, 0.0f);
            gl.glVertex3f(sx, sy, 0);    // Bottom Right Of The Quad (Back)

            gl.glNormal3f(-1, 0, 0);
            //glColor3f(0.0f,0.0f,1.0f);    // Color Blue
            gl.glTexCoord2f(sz, sy);
            gl.glVertex3f(0, sy, sz);    // Top Right Of The Quad (Left)
            gl.glTexCoord2f(0.0f, sy);
            gl.glVertex3f(0, sy, 0);    // Top Left Of The Quad (Left)
            gl.glTexCoord2f(0.0f, 0.0f);
            gl.glVertex3f(0, 0, 0);    // Bottom Left Of The Quad (Left)
            gl.glTexCoord2f(sz, 0.0f);
            gl.glVertex3f(0, 0, sz);    // Bottom Right Of The Quad (Left)

            gl.glNormal3f(1, 0, 0);
            //glColor3f(1.0f,0.0f,1.0f);    // Color Violet
            gl.glTexCoord2f(sz, sy);
            gl.glVertex3f(sx, sy, 0);    // Top Right Of The Quad (Right)
            gl.glTexCoord2f(0.0f, sy);
            gl.glVertex3f(sx, sy, sz);    // Top Left Of The Quad (Right)
            gl.glTexCoord2f(0.0f, 0.0f);
            gl.glVertex3f(sx, 0, sz);    // Bottom Left Of The Quad (Right)
            gl.glTexCoord2f(sz, 0.0f);
            gl.glVertex3f(sx, 0, 0);    // Bottom Right Of The Quad (Right)
        }
        gl.glEnd();            // End Drawing The Cube

        ttGachTuong.disable();

        gl.glPopMatrix();

        //reset color
        gl.glColor4f(1, 1, 1, 1);
    }
    
    //
}
