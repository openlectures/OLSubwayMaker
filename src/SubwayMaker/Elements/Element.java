/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package SubwayMaker.Elements;

import java.awt.Graphics2D;
import java.awt.geom.Point2D;

/**
 *
 * @author Yichen
 */
public interface Element {
    public abstract void add(Point2D p);
    public abstract void paint(Graphics2D g);
}
