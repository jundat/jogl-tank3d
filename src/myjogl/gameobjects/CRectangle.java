/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package myjogl.gameobjects;

/**
 * My rectangle
 *
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
        return (this.x <= rect.x + rect.w
                && rect.x <= this.x + rect.w
                && this.y <= rect.y + rect.h
                && rect.y <= this.y + rect.h);
    }

    public int getCollisionDirection(CRectangle _rect2) {
        if (this.isIntersect(_rect2)) {
            float top = Math.abs(this.y - _rect2.y - _rect2.h);
            float botom = Math.abs(this.y + this.h - _rect2.y);
            float left = Math.abs(this.x - _rect2.x - _rect2.w);
            float right = Math.abs(this.x + this.w - _rect2.x);
            float rs = Math.min(Math.min(right, left), Math.min(top, botom));

            if (rs == top) {
                return CDirections.UP;
            }
            if (rs == botom) {
                return CDirections.DOWN;
            }
            if (rs == left) {
                return CDirections.LEFT;
            }
            if (rs == right) {
                return CDirections.RIGHT;
            }
        }

        return CDirections.NONE;
    }
}
