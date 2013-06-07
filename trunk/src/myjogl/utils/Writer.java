/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package myjogl.utils;

import com.sun.opengl.util.j2d.TextRenderer;
import java.awt.Color;
import java.awt.Font;
import myjogl.Global;

/**
 *
 * @author Jundat
 */
public class Writer {
    
    private static Font font = new Font("Consolas", Font.BOLD, 20);
    private static TextRenderer tr = new TextRenderer(font);
    
    public static void Render(String content, String fontName, int style, int size, int x, int y, Color color){
        //render
        tr.setColor(color);
        tr.setSmoothing(false);

        tr.beginRendering(Global.wndWidth, Global.wndHeight);
        tr.draw(content, x, y);
        tr.endRendering();
        
        tr.setColor(Color.WHITE);
    }
}
