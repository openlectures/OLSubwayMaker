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
public abstract class Element {

    /**
     * Returns a copy of the
     * <code>Point2D</code> that has been scaled accordingly.
     * <p/>
     * @param pt    The original point.
     * @param scale The amount to scale the point by.
     * @return \
     */
    public static Point2D pointScale(Point2D pt, double scale) {
        return new Point2D.Double(pt.getX() * scale, pt.getY() * scale);
    }

    /**
     * Returns the nearest integer coordinate from the reference.
     * <p/>
     * Useful for snapping points to a grid.
     * <p/>
     * @param pt The reference point.
     * @return A new point that's snapped to the nearest whole number
     *         coordinate.
     */
    public static Point2D nearPoint(Point2D pt) {
        // TODO use this function to settle snap to grid
        return new Point2D.Double(Math.round(pt.getX()), Math.round(pt.getY()));
    }

    /**
     * Adds a new point to this
     * <code>Element</code>.
     * <p/>
     * This allows this
     * <code>Element</code> to be appended with new points. An modifier can be
     * specified if the new point is to be added in another fashion.
     * <p/>
     * @param pt  The point to be added.
     * @param mod <code>true</code> if the way the point to be added should be
     *            modified, <code>false</code> otherwise.
     */
    public abstract void add(Point2D pt, boolean mod);
    
    /**
     * Removes the last point added to this element.
     */
    public abstract void remove();

    /**
     * Paints the
     * <code>Element</code> using the graphics supplied.
     * <p/>
     * This
     * <code>Element</code> will be scaled by
     * <code>blocksize</code> before being painted.
     * <p/>
     * @param g         The graphics object to paint the element with.
     * @param blocksize The block size to scale the element to.
     */
    public abstract void paint(Graphics2D g, int blocksize);

    /**
     * Called when this
     * <code>Element</code> has been completed and no further changes are
     * necessary.
     * <p/>
     * Completed
     * <code>Element</code> may be printed in a different manner as compared to
     * incomplete ones.
     */
    public abstract void complete();
}
