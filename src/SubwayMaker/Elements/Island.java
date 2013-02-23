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
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/**
 *
 * @author Yichen
 */
public class Island implements Element {
    
    public static final Color INI_COLOR = new Color(30, 150, 200);
    public static final int DIAMETER = 10;

    private List<Point2D.Float> edgeList;
    private Color fill;
    private boolean complete;

    public Island() {
        complete = false;
        edgeList = new ArrayList<>();
        fill = new Color(50, 50, 50, 50);
    }

    @Override
    public void add(Point2D.Float p) {
        edgeList.add(p);
    }

    @Override
    public void complete() {
        complete = true;
    }

    @Override
    public void paint(Graphics2D g, int blocksize) {
        ListIterator<Point2D.Float> it = edgeList.listIterator();
        GeneralPath polygon = new GeneralPath();

        if (it.hasNext()) {
            Point2D.Float pt = it.next();
            polygon.moveTo(pt.x, pt.y);
            while (it.hasNext()) {
                pt = it.next();
                polygon.lineTo(pt.x, pt.y);
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
                Point2D.Float pt = it.next();
                g.fillOval((int)pt.x-DIAMETER/2,(int)pt.y-DIAMETER/2,DIAMETER,DIAMETER);
            }
            g.draw(polygon);
        }

    }
}
