/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package SubwayMaker.Elements;

import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Float;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Yichen
 */
public class Track implements Element {

    private List<TrackPoint> trackPoints;
    private boolean complete;

    public Track() {
        complete = false;
        trackPoints = new ArrayList<>();
    }

    @Override
    public void add(Point2D.Float p, boolean mod) {
        if(trackPoints.isEmpty()){
            trackPoints.add(new TrackPoint(p));
        }
        else{
            trackPoints.add(new TrackPoint(p, trackPoints.get(trackPoints.size()-1), mod));
        }
    }

    @Override
    public void paint(Graphics2D g, int blocksize) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void complete() {
        complete = true;
    }

    private class TrackPoint extends Point2D.Float {

        private static final double DELTA = 0.000001;
        
        private boolean hort = true;
        private boolean turningPoint = false;
        
        private Point2D.Float refPoint;

        /**
         * Create a new TrackPoint without direction or curve. <p>
         * Usually for the first point.
         *
         * @param endPoint The point for this track point.
         */
        TrackPoint(Point2D.Float endPoint) {
            super(endPoint.x, endPoint.y);
        }

        /**
         * Creates a new TrackPoint, automatically determining direction/curve unless otherwise stated. <p>
         * To determine if there should be a curve, the function looks at the previous point and evaluates if the new point is linear.
         * @param endPoint The point of this TrackPoint.
         * @param prevPoint The previous point to use as a reference.
         * @param mod If the automatic direction/curve should be inverted.
         */
        TrackPoint(Point2D.Float endPoint, TrackPoint prevPoint, boolean mod) {
            this(endPoint);
            
            turningPoint = !((Math.abs(prevPoint.x - x) < DELTA) || (Math.abs(prevPoint.y - y) < DELTA));
            
            if(turningPoint){
                hort = !prevPoint.goingHorizontal();
                
                if(hort ^ mod){
                    refPoint = new Point2D.Float(prevPoint.x,endPoint.y);
                }
                else{
                    refPoint = new Point2D.Float(endPoint.x, prevPoint.y);
                }
            }
            else{
                hort = prevPoint.goingHorizontal();
                if((hort && (Math.abs(prevPoint.y-y)>DELTA)) || (!hort && (Math.abs(prevPoint.x-x)>DELTA))){
                    hort = !hort;
                }
            }
        }
        
        Point2D.Float curveReference(){
            return refPoint;
        }
        
        boolean goingHorizontal(){
            return hort;
        }

        boolean isTurningPoint(){
            return turningPoint;
        }
    }
}
