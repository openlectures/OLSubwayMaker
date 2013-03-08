/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package SubwayMaker.Elements;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.GeneralPath;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

/**
 *
 * @author Yichen
 */
public class Island extends Element {

    public static final Color INI_COLOR = new Color(30, 150, 200);
    public static final float DIAMETER_SCALE = (float) 0.5;
    private List<Point2D> edgeList;
    private Color fill;
    private boolean complete;

    public Island() {
        complete = false;
        edgeList = new ArrayList<>();
        fill = new Color(50, 50, 50, 50);
    }

    @Override
    public void add(Point2D pt, boolean mod) {
        edgeList.add(pt);
    }

    @Override
    public void complete() {
        complete = true;
    }

    @Override
    public void paint(Graphics2D g, int blocksize) {
        int diameter = Math.round(blocksize * DIAMETER_SCALE);

        ListIterator<Point2D> it = edgeList.listIterator();
        GeneralPath polygon = new GeneralPath();

        if (it.hasNext()) {
            Point2D pt = pointScale(it.next(), blocksize);
            polygon.moveTo(pt.getX(), pt.getY());
            while (it.hasNext()) {
                pt = pointScale(it.next(), blocksize);
                polygon.lineTo(pt.getX(), pt.getY());
            }
        }

        g.setStroke(new BasicStroke(1));

        if (complete) {
            g.setColor(fill);
            polygon.closePath();
            g.fill(polygon);
        } else {
            g.setColor(INI_COLOR);
            for (it = edgeList.listIterator(); it.hasNext();) {
                Point2D pt = pointScale(it.next(), blocksize);
                g.fillOval((int) pt.getX() - diameter / 2, (int) pt.getY() - diameter / 2, diameter, diameter);
            }
            g.draw(polygon);
        }

    }
}
