package com.mygis.view;

import com.mygis.data.io.WKTParser;
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;

import com.mygis.model.common.Envelop;
import com.mygis.model.crs.transform.AffineTransform;
import com.mygis.model.geom.Geometry;
import com.mygis.model.geom.Point;
import com.mygis.model.layer.Layer;
import com.mygis.view.style.Style;
import com.mygis.view.util.DrawHelper;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Map {

    private int width;
    private int height;

    private Layer layer;
    private Layer solutionLayer;
    private AffineTransform geometryTransform;
    private List<Point> listP;

    private BufferedImage bufferImage;
    private Graphics2D graphic;
    private Style style;

    private Component component;

    private File requestReader;
    private File solutionReader;

    public Map(int width, int height, Layer layer, Component component) {
        this.width = width;
        this.height = height;
        this.layer = layer;
        this.component = component;
        geometryTransform = new AffineTransform();
        bufferImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        graphic = (Graphics2D) bufferImage.getGraphics();
        style = new Style();
    }

    public Layer getLayer() {
        return layer;
    }

    public void setLayer(Layer layer) {
        this.layer = layer;
    }

    public Layer getSolutionLayer() {
        return solutionLayer;
    }

    public void setSolutionLayer(Layer solutionLayer) {
        this.solutionLayer = solutionLayer;
    }
    
    public void setRequestReader(File requestReader) {
        this.requestReader = requestReader;
    }
    
    public void setSolutionReader(File solutionReader) {
        this.solutionReader = solutionReader;
    }
    
    public List<Point> getListP() {
        return listP;
    }

    public void setListP(List<Point> listP) {
        this.listP = listP;
    }

    public void fullView() {
        geometryTransform = new AffineTransform();
        Envelop envelop = getEnvelop();
        Point center = envelop.getCenterPoint();

        double scale = computeScale(envelop);

        geometryTransform = geometryTransform.accumulate(AffineTransform.scale(center.getX(), center.getY(), scale, scale));

        double dx = width / 2 - center.getX();
        double dy = height / 2 - center.getY();

        geometryTransform = geometryTransform.accumulate(AffineTransform.pan(dx, dy));

        drawMap();
    }

    public void drawMap() {
        graphic.clearRect(0, 0, width, height);
        graphic.setPaint(Color.white);
        graphic.fillRect(0, 0, width, height);

        for (int i = 0; i < layer.size(); i++) {
            Geometry geom = layer.getGeometry(i).transform(geometryTransform);
            DrawHelper.drawGeometry(graphic, style, geom);
        }

        bufferImage.flush();
    }

    //Tien
    public void drawRequest() {
        String text;
        try {
            //requestReader.mark(0);
            BufferedReader br = new BufferedReader(new FileReader(requestReader));

            text = br.readLine();

            while (text != null) {
                System.out.println(text);
                String[] parts = text.split(" ");
                double epsilon = 0.000001;

                double u = Double.parseDouble(parts[1]);
                double v = Double.parseDouble(parts[2]);
                double x = (u - epsilon) / 80000 + 139;
                double y = ((-v - epsilon) / 80000) * 2 / 3 - (35.3333333330);
                System.out.println("(" + x + "," + y + ")");
                Point point = new Point(x, y);
                
                DrawHelper.drawRequestPoint(graphic, style, (Point) point.transform(geometryTransform));
                text = br.readLine();
            }
            component.repaint();
        } catch (IOException ex) {
            Logger.getLogger(Map.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void drawSolution() throws Exception {
        String text;
        try {
            BufferedReader br = new BufferedReader(new FileReader(solutionReader));
            WKTParser wkt = new WKTParser();
            solutionLayer = new Layer();
            text = br.readLine();

            while (text != null) {
                System.out.println(text);
                String[] parts = text.split(" ");
                
                for (int i = 0; i < parts.length-1; i++) {
                    int pID = Integer.parseInt(parts[i]);
                    int nextpID = Integer.parseInt(parts[i+1]);
                    Point p = listP.get(Collections.binarySearch(listP, new Point(pID), Point.getComparator()));
                    DrawHelper.drawSolutionPoint(graphic, style, (Point)p.transform(geometryTransform));
                    
                    Geometry geoEdge = wkt.read("SolutionLineString("
                            + listP.get(Collections.binarySearch(listP, new Point(pID), Point.getComparator())).getX()
                            + " "
                            + listP.get(Collections.binarySearch(listP, new Point(pID), Point.getComparator())).getY()
                            + ","
                            + listP.get(Collections.binarySearch(listP, new Point(nextpID), Point.getComparator())).getX()
                            + " "
                            + listP.get(Collections.binarySearch(listP, new Point(nextpID), Point.getComparator())).getY()
                            + ")");

                    solutionLayer.addGeometry(geoEdge);
                }
                
                int pID0 = Integer.parseInt(parts[parts.length-1]);
                Point p0 = listP.get(Collections.binarySearch(listP, new Point(pID0), Point.getComparator()));
                DrawHelper.drawSolutionPoint(graphic, style, (Point)p0.transform(geometryTransform));
                
                text = br.readLine();
            }
            
            for (int i = 0; i < solutionLayer.size(); i++) {
                Geometry geom = solutionLayer.getGeometry(i).transform(geometryTransform);
                DrawHelper.drawGeometry(graphic, style, geom);
            }
            
            component.repaint();
        } catch (IOException ex) {
            Logger.getLogger(Map.class.getName()).log(Level.SEVERE, null, ex);
            throw ex;
        }

    }

    
    public void refreshMap() {
        drawMap();
        if (requestReader != null) {
            drawRequest();
        }
        if (solutionReader != null) {
            try {
                drawSolution();
            } catch (Exception ex) {
                Logger.getLogger(Map.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        component.repaint();
    }

    public Image getImage() {
        return bufferImage;
    }

    public void transform(AffineTransform otherTransform) {
        geometryTransform = geometryTransform.accumulate(otherTransform);
    }

    public Envelop getEnvelop() {
        return layer.getEnvelop();
    }

    private double computeScale(Envelop envelop) {
        double wScale = (width - 10) / envelop.getWidth();
        double hScale = (height - 10) / envelop.getHeight();
        return Math.min(wScale, hScale);
    }

}
