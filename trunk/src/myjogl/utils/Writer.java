/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package myjogl.utils;

import com.sun.opengl.util.j2d.TextRenderer;
import com.sun.opengl.util.texture.Texture;
import java.awt.Color;
import java.awt.Font;
import myjogl.Global;

/**
 *
 * @author Jundat
 */
public class Writer {

    /*
     * ??c file Fnt v?i ??nh d?ng c?a HgeEngine
     */
    public void Writer(String fileFnt, Texture fntTexture) {
        
    }
    
    
    //static 
    
    private static Font font = new Font("Times New Roman", Font.BOLD, 40);
    private static TextRenderer tr = new TextRenderer(font);

    public static void Render(String content, String fontName, int size, int x, int y, Color color) {
        //render
        tr.setColor(color);
        tr.setSmoothing(false);

        tr.beginRendering(Global.wndWidth, Global.wndHeight);
        tr.draw(content, x, y);
        tr.endRendering();

        tr.setColor(Color.WHITE);
    }
}
