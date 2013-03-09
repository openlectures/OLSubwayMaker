/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package SubwayMaker;

import SubwayMaker.Elements.Element;
import SubwayMaker.Elements.Island;
import SubwayMaker.Elements.Station;
import SubwayMaker.Elements.Track;
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

    public static enum editMode {

        NONE, ISLAND, TRACK, STATION
    }
    //Colour of grid
    private Color gridColor;
    //The number of pixels each grid takes up.
    private int blocksize;
    //The size of the canvas (in blocks)
    private int width, height;
    //Elements to print
    private List<List<Element>> elemList;
    private boolean editing;
    private editMode mode;
    private Element editElem;

    public MapController() {
        blocksize = 20;
        width = 50;
        height = 30;
        gridColor = Color.LIGHT_GRAY;
        elemList = new ArrayList<>(3);
        editing = false;
        mode = editMode.NONE;
        for (int i = 0; i < 3; i++) {
            elemList.add(new ArrayList<Element>());
        }
    }

    /**
     * Adds a new point to the
     * <code>Element</code> in editing.
     * <p/>
     * Specify the coordinates in absolute coordinates of the canvas. The
     * modifier can be used to alter the addition process.
     * <p/>
     * @param x   The X coordinate.
     * @param y   The Y coordinate.
     * @param mod The modifier.
     */
    public void addPoint(double x, double y, boolean mod) {
        if (editing) {
            Point2D pt = new Point2D.Double(x / blocksize, y / blocksize);

            editElem.add(pt, mod);
        }
    }

    /**
     * Removes the last point added to the
     * <code>Element</code> being edited.
     */
    public void undo() {
        if (editing) {
            editElem.remove();
        }
    }

    /**
     * Sets the preview point to the
     * <code>Element</code> being edited.
     * <p/>
     * Preview point are shown only when the
     * <code>Element</code> is being edited, and will not be included when it is
     * complete. Only one preview point may be assigned to an
     * <code>Element</code> and any existing point will be overwritten when this
     * method is called.
     * <p/>
     * Graphically, the new point will look just as if it has been added to the
     * <code>Element</code> via
     * <code>addPoint()</code>
     * <p/>
     * @see addPoint()
     * @param x   The X coordinate.
     * @param y   The Y coordinate.
     * @param mod The modifier.
     */
    public void previewPos(double x, double y, boolean mod) {
        if (editing) {
            editElem.setPreview(new Point2D.Double(x / blocksize, y / blocksize), mod);
        }
    }

    /**
     * Creates a new
     * <code>Element</code>.
     * <p/>
     * Type of
     * <code>Element</code> created is based on the mode of the
     * <code>MapController</code>. If the state is
     * <code>NONE</code> this function does nothing. Editing mode will be
     * turned.
     */
    public void createNewElement() {
        List<Element> curList;
        switch (mode) {
            case ISLAND:
                editElem = new Island();
                curList = elemList.get(0);
                break;
            case TRACK:
                editElem = new Track();
                curList = elemList.get(1);
                break;
            case STATION:
                editElem = new Station();
                curList = elemList.get(2);
                break;
            default:
                return;
        }
        curList.add(editElem);
        editing = true;
    }

    /**
     * Completes the current
     * <code>Element</code>.
     * <p/>
     * If the
     * <code>Element</code> does not have any points, it'll be removed. Editing
     * mode will be turned off.
     */
    public void complete() {
        if (editing) {
            editElem.complete();

            List<Element> editList;
            switch (mode) {
                case ISLAND:
                    editList = elemList.get(0);
                    break;
                case TRACK:
                    editList = elemList.get(1);
                    break;
                case STATION:
                    editList = elemList.get(2);
                    break;
                default:
                    editList = null;
            }

            if (editList != null && editElem.isEmpty()) {
                editList.remove(editElem);
            }
            editing = false;
        }
    }

    /**
     * Sets the mode of the
     * <code>MapController</code>.
     * <p/>
     * Completes any
     * <code>Element</code> in editing, sets the mode and attempts to create a
     * new
     * <code>Element</code> based on the new controller mode.
     * <p/>
     * @param m The new mode of the controller.
     */
    public void setMode(editMode m) {
        complete();
        mode = m;
        createNewElement();
    }

    /**
     * Returns the canvas size.
     * <p/>
     * This is the size of the canvas required to show the subway map.
     * <p/>
     * @return The dimension object representative of the size.
     */
    public Dimension canvasSize() {
        return new Dimension(width * blocksize + 1, height * blocksize + 1);
    }

    /**
     * Paints the subway map created thus far.
     * <p/>
     * @param g The <code>Graphics2D</code> object the map is to be painted on.
     */
    public void paint(Graphics2D g) {
        paintGrid(g);
        for (List<Element> list : elemList) {
            for (Element element : list) {
                element.paint(g, blocksize);
            }
        }
        if (editing) {
            editElem.paint(g, blocksize);
        }
    }

    /**
     * Paints a grid for referencing on the canvas.
     * <p/>
     * @param g The <code>Graphics2D</code> object to paint with.
     */
    private void paintGrid(Graphics2D g) {
        g.setColor(gridColor);
        for (int i = 0; i <= width; i++) {
            g.drawLine(i * blocksize, 0, i * blocksize, height * blocksize);
        }
        for (int i = 0; i <= height; i++) {
            g.drawLine(0, i * blocksize, width * blocksize, i * blocksize);
        }
    }
}
