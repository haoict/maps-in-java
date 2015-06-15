package com.mygis.view.util;

import com.mygis.model.common.GeometryType;
import java.awt.Graphics2D;
import java.awt.geom.Area;

import com.mygis.model.geom.Geometry;
import com.mygis.model.geom.LineString;
import com.mygis.model.geom.LinearRing;
import com.mygis.model.geom.MultiLineString;
import com.mygis.model.geom.MultiPoint;
import com.mygis.model.geom.MultiPolygon;
import com.mygis.model.geom.Point;
import com.mygis.model.geom.Polygon;
import com.mygis.model.geom.SolutionLineString;
import com.mygis.view.style.Style;

public class DrawHelper {

    public static void drawGeometry(Graphics2D g, Style style, Geometry geometry) {
        switch (geometry.getGeometryType()) {
                        
            case Point: {
                drawPoint(g, style, (Point) geometry);
                break;
            }
            
            case SolutionPoint: {
                drawSolutionPoint(g, style, (Point) geometry);
                break;
            }
            
            case LineString: {
                drawLineString(g, style, (LineString) geometry);
                break;
            }
            
            case SolutionLineString: {
                drawSolutionLineString(g, style, (SolutionLineString) geometry);
                break;
            }

            case Polygon: {
                drawPolygon(g, style, (Polygon) geometry);
                break;
            }

            case MultiPoint: {
                drawMultiPoint(g, style, (MultiPoint) geometry);
                break;
            }

            case MultiLineString: {
                drawMultiLineString(g, style, (MultiLineString) geometry);
                break;
            }

            case MultiPolygon: {
                drawMultiPolygon(g, style, (MultiPolygon) geometry);
                break;
            }

            default: {
                throw new RuntimeException("Wrong geometry type " + geometry.getGeometryType());
            }
        }
    }
   

    private static void drawPoint(Graphics2D g, Style style, Point p) {
        double width = style.pointWidth / 2d;
        //System.out.println("("+p.getX()+","+p.getY()+")");
        g.setColor(style.pointColor);
        g.setStroke(style.stroke);

        g.fillOval((int) (p.getX() - width), (int) (p.getY() - width), style.pointWidth, style.pointWidth);
    }
    
    //Tien
    public static void drawRequestPoint(Graphics2D g, Style style, Point p) {
        double width = style.pointWidth / 2d;
        //System.out.println("("+p.getX()+","+(p.getY())+")");
        g.setColor(style.requestColor);
        g.setStroke(style.stroke);

        g.fillOval((int) (p.getX() - width), (int) (p.getY() - width), 5, 5);
    }
    
    public static void drawSolutionPoint(Graphics2D g, Style style, Point p) {
        double width = style.pointWidth / 2d;
        //System.out.println("("+p.getX()+","+(p.getY())+")");
        g.setColor(style.solutionPointColor);
        g.setStroke(style.stroke);

        g.fillOval((int) (p.getX() - width), (int) (p.getY() - width), style.solutionPointWidth, style.solutionPointWidth);
    }
    
    private static void drawSolutionLineString(Graphics2D g, Style style, SolutionLineString ls) {
        g.setPaint(style.solutionLineColor);
        g.setStroke(style.stroke);

        g.drawPolyline(ls.getX(), ls.getY(), ls.size());
    }

    private static void drawLineString(Graphics2D g, Style style, LineString ls) {
        g.setPaint(style.lineColor);
        g.setStroke(style.stroke);

        g.drawPolyline(ls.getX(), ls.getY(), ls.size());
    }

    private static void drawPolygon(Graphics2D g, Style style, Polygon polygon) {
        java.awt.Polygon awtPolygon = new java.awt.Polygon(polygon.getShell().getX(), polygon.getShell().getY(), polygon.getShell().size());
        Area area = new Area(awtPolygon);

        for (LinearRing r : polygon.getHoles()) {
            java.awt.Polygon holePath = new java.awt.Polygon(r.getX(), r.getY(), r.size());
            Area holeArea = new Area(holePath);
            area.subtract(holeArea);
        }

        g.setPaint(style.fillColor);
        g.fill(area);

        g.setPaint(style.lineColor);
        g.draw(area);
    }

    private static void drawMultiPoint(Graphics2D g, Style style, MultiPoint points) {
        for (int i = 0; i < points.size(); i++) {
            drawPoint(g, style, (Point) points.getGeometry(i));
        }
    }

    private static void drawMultiLineString(Graphics2D g, Style style, MultiLineString ls) {
        for (int i = 0; i < ls.size(); i++) {
            drawLineString(g, style, (LineString) ls.getGeometry(i));
        }
    }

    private static void drawMultiPolygon(Graphics2D g, Style style, MultiPolygon polygons) {
        for (int i = 0; i < polygons.size(); i++) {
            drawPolygon(g, style, (Polygon) polygons.getGeometry(i));
        }
    }
}
