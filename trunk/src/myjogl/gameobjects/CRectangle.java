/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package myjogl.gameobjects;

/**
 * My rectangle
 * @author Jundat
 */
public class CRectangle {

    public float x, y, w, h;

    public CRectangle() {
        x = y = w = h = 0;
    }

    public CRectangle(float x, float y, float w, float h) {
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
    }

    public boolean isIntersect(CRectangle rect) {
        float area = (Math.max(this.x, rect.x) - Math.min(this.x, rect.x))
                * (Math.max(this.y, rect.y) - Math.min(this.y, rect.y));

        if (area == 0) {
            return false;
        } else {
            return true;
        }
    }
}
