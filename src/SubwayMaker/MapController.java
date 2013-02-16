/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package SubwayMaker;

import SubwayMaker.Elements.Element;
import SubwayMaker.Elements.Island;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Yichen
 */
public class MapController {
    
    //Colour of grid
    private Color gridColor;
    //The number of pixels each grid takes up.
    private int blocksize;
    //The size of the canvas (in blocks)
    private int width, height;
    
    //Elements to print
    private List<List<Element> > elemList;
    private boolean editing;
    private Element editElem;
    
    public MapController(){
        blocksize = 20;
        width = 50;
        height = 30;
        gridColor = Color.LIGHT_GRAY;
        elemList = new ArrayList<>(3);
        editing = false;
        for (int i = 0; i < 3; i++) {
            elemList.add(new ArrayList<Element>());   
        }
    }
    
    public void addPoint(float x, float y){
        Point2D.Float pt = new Point2D.Float(x, y);
        if(editing){
            editElem.add(pt);
        }
        else{
            editElem = new Island();
            editElem.add(pt);
            elemList.get(0).add(editElem);
            editing = true;
        }
    }
    
    public void complete(){
        editing = false;
    }
    
    public Dimension canvasSize(){
        return new Dimension(width*blocksize+1, height*blocksize+1);
    }
    
    public void paint(Graphics2D g){
        paintGrid(g);
        for (List<Element> list : elemList) {
            for (Element element : list) {
                element.paint(g, blocksize);
            }
        }
    }
    
    private void paintGrid(Graphics2D g) {
        g.setColor(gridColor);
        for (int i = 0; i <= width; i++) {
            g.drawLine(i*blocksize, 0, i*blocksize, height*blocksize);
        }
        for (int i = 0; i <= height; i++) {
            g.drawLine(0,i*blocksize, width*blocksize, i*blocksize);
        }
    }

}
