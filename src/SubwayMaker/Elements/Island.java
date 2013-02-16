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
public class Island implements Element{

    private List<Point2D.Float> edgeList;
    private Color fill;

    public Island() {
        edgeList = new ArrayList<>();
        fill = new Color(50, 50, 50, 50);
    }
            
    @Override
    public void add(Point2D.Float p){
        edgeList.add(p);
    }

    @Override
    public void paint(Graphics2D g, int blocksize) {
        g.setColor(fill);
        g.setStroke(new BasicStroke(1));
        
        GeneralPath polygon = new GeneralPath();
        
        ListIterator<Point2D.Float> it = edgeList.listIterator();
        
        if(it.hasNext()){
            Point2D.Float pt = it.next();
            polygon.moveTo(pt.x, pt.y);
            while(it.hasNext()){
                pt = it.next();
                polygon.lineTo(pt.x, pt.y);
            }
        }
        
        polygon.closePath();
        g.fill(polygon);
    }
    
}
