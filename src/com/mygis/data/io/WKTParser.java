package com.mygis.data.io;

import java.util.ArrayList;
import java.util.List;

import com.mygis.model.geom.Geometry;
import com.mygis.model.geom.LineString;
import com.mygis.model.geom.LinearRing;
import com.mygis.model.geom.MultiLineString;
import com.mygis.model.geom.MultiPoint;
import com.mygis.model.geom.MultiPolygon;
import com.mygis.model.geom.Point;
import com.mygis.model.geom.Polygon;
import com.mygis.model.geom.SolutionLineString;

public class WKTParser {

    public Geometry read(String text) {
        int tIndex = text.indexOf('(');

        String type = text.substring(0, tIndex).trim();
        text = text.substring(tIndex);

        if (type.equalsIgnoreCase("Point")) {
            return getPoint(text);
        } else if (type.equalsIgnoreCase("LineString")) {
            return getLineString(text);
        } else if (type.equalsIgnoreCase("SolutionLineString")) {
            return getSolutionLineString(text);
        } else if (type.equalsIgnoreCase("Polygon")) {
            return getPolygon(text);
        }
        if (type.equalsIgnoreCase("MultiPoint")) {
            return getMultiPoint(text);
        }
        if (type.equalsIgnoreCase("MultiLineString")) {
            return getMultiLineString(text);
        }
        if (type.equalsIgnoreCase("MultiPolygon")) {
            return getMultiPolygon(text);
        } else {
            throw new IllegalArgumentException("Unknow geometry");
        }

    }

    private Point getPoint(String text) {
        text = text.trim();
        text = removeBrackets(text);
        return readPoint(text);
    }

    private Point readPoint(String text) {
        text = text.trim();
        int xIndex = text.indexOf(' ');

        double x = Double.parseDouble(text.substring(0, xIndex));
        double y = Double.parseDouble(text.substring(xIndex));

        Point p = new Point(x, y);
        return p;
    }

    private LineString getLineString(String text) {
        text = text.trim();
        List<Point> points = readPointList(text);
        return new LineString(points);
    }
    
    private SolutionLineString getSolutionLineString(String text) {
        text = text.trim();
        List<Point> points = readPointList(text);
        return new SolutionLineString(points);
    }
    

    private List<Point> readPointList(String text) {
        text = text.trim();

        String[] str = text.split(",");

        List<Point> points = new ArrayList<Point>(str.length);

        for (String s : str) {
            Point p = getPoint(s);
            points.add(p);
        }
        return points;
    }

    private Polygon getPolygon(String text) {
        text = text.trim();

        String[] linearRingArray = text.split("\\)\\s*,\\s*\\(");

        List<LinearRing> linearRingList = new ArrayList<LinearRing>(linearRingArray.length);

        for (String linearRing : linearRingArray) {
            List<Point> points = readPointList(linearRing);
            linearRingList.add(new LinearRing(points));
        }

        return new Polygon(linearRingList.get(0), linearRingList.subList(1, linearRingList.size()));
    }

    private MultiPoint getMultiPoint(String text) {
        text = text.trim();
        List<Point> points = readPointList(text);

        return new MultiPoint(points);
    }

    private MultiLineString getMultiLineString(String text) {
        text = text.trim();
        String[] lineStrings = text.split("\\)\\s*,\\s*\\(");

        List<LineString> lineStringList = new ArrayList<LineString>(lineStrings.length);

        for (String oneLineString : lineStrings) {
            lineStringList.add(getLineString(oneLineString));
        }

        return new MultiLineString(lineStringList);

    }

    private MultiPolygon getMultiPolygon(String text) {
        text = text.trim();

        String[] polygons = text.split("\\)\\s*\\)\\s*,\\s*\\(\\s*\\(");

        List<Polygon> polygonList = new ArrayList<Polygon>(polygons.length);

        for (String polygon : polygons) {
            polygonList.add(getPolygon(polygon));
        }

        return new MultiPolygon(polygonList);
    }

    private String removeBrackets(String text) {
        text = text.replace('(', ' ');
        text = text.replace(')', ' ');
        return text.trim();
    }

}
