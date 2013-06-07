/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package myjogl.gameview;

import com.sun.opengl.util.texture.Texture;
import java.awt.Rectangle;
import javax.media.opengl.GL;
import javax.swing.JOptionPane;
import myjogl.utils.Renderer;
import myjogl.utils.ResourceManager;

/**
 *
 * @author Jundat
 */
public class MenuItem {
    
    private boolean isClicked = false;
    public Rectangle rect = new Rectangle();
    private Texture ttNormal = null;
    private Texture ttClick = null;
        
    public MenuItem(Texture ttNormal, Texture ttClick){
        this.ttNormal = ttNormal;
        this.ttClick = ttClick;
        
        if(ttNormal == null && ttClick == null)
            JOptionPane.showMessageDialog(null, "Can not create MenuItem");
    }
    
    public void SetPosition(int x, int y){
        rect.x = x;
        rect.y = y;
        rect.width = this.ttNormal.getWidth();
        rect.height = this.ttNormal.getHeight();
    }            
    
    public void Render(){
        if(isClicked == true && ttClick != null){
            Renderer.Render(this.ttClick, rect.x, rect.y);
        } else {
            Renderer.Render(this.ttNormal, rect.x, rect.y);
        }            
    }

    public void setIsClick(boolean isClicked){
        this.isClicked = isClicked;
    }
    
    //
    public boolean contains(int x, int y) {
        if(isClicked == true && ttClick != null){
        } else {
            rect.width = this.ttClick.getWidth();
            rect.height = this.ttClick.getHeight();
        }
        
        return rect.contains(x, y);
    }
}
