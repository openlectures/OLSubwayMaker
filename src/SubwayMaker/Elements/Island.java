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
import java.util.Random;

/**
 *
 * @author Yichen
 */
public class Island extends Element {

    public static final Color INI_COLOR = new Color(30, 150, 200);
    public static final float DIAMETER_SCALE = 0.5f;
    private List<Point2D> edgeList;
    private Point2D previewPt;
    private Color fill;
    private boolean complete;

    public Island() {
        complete = false;
        edgeList = new ArrayList<>();
        previewPt = null;

        Random random = new Random();

        int rgb = Color.HSBtoRGB(random.nextFloat(), (random.nextInt(1000) + 2500) / 10000f, (random.nextInt(1000) + 8000) / 10000f);
        fill = new Color((rgb >> 16) & 0xFF, (rgb >> 8) & 0xFF, rgb & 0xFF, 0x88);
    }

    @Override
    public void add(Point2D pt, boolean mod) {
        edgeList.add(pt);
        previewPt = null;
    }

    @Override
    public void setPreview(Point2D pt, boolean mod) {
        previewPt = pt;
    }

    @Override
    public void remove() {
        if (!edgeList.isEmpty()) {
            edgeList.remove(edgeList.size() - 1);
        }
    }

    @Override
    public void complete() {
        complete = true;
    }

    @Override
    public boolean isEmpty() {
        return edgeList.isEmpty();
    }

    @Override
    public void paint(Graphics2D g, int blocksize) {
        int diameter = Math.round(blocksize * DIAMETER_SCALE);

        boolean preview = !(complete || previewPt == null);
        if (preview) {
            edgeList.add(previewPt);
        }

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

        if (complete && !edgeList.isEmpty()) {
            g.setColor(fill);
            polygon.closePath();
            g.fill(polygon);
        } else {
            g.setColor(INI_COLOR);
            for (it = edgeList.listIterator(); it.hasNext();) {
                Point2D pt = pointScale(it.next(), blocksize);
                g.fillOval((int) Math.round(pt.getX() - diameter / 2.0), (int) Math.round(pt.getY() - diameter / 2.0), diameter, diameter);
            }
            g.draw(polygon);
        }

        if (preview) {
            remove();
        }
    }
}
