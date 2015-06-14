package com.mygis.model.geom;

import java.util.ArrayList;
import java.util.List;

import com.mygis.model.common.Envelop;
import com.mygis.model.common.GeometryType;
import com.mygis.model.crs.transform.GeometryTransform;

public class LineString extends AbstractGeometry {

    protected List<Point> pointList;

    public LineString(List<Point> pointList) {
        super(GeometryType.LineString);
        if (pointList == null || pointList.size() < 2) {
            throw new IllegalArgumentException("LineString must contains more than one point");
        }
        this.pointList = pointList;
    }

    @Override
    protected void calculateEnvelop() {
        envelop = new Envelop();
        for (Point p : pointList) {
            envelop.extendEnvelop(p);
        }

    }

    @Override
    public Geometry transform(GeometryTransform geometryTransform) {
        List<Point> newList = new ArrayList<Point>(pointList.size());
        for (Point p : pointList) {
            newList.add(geometryTransform.convert(p));
        }
        return new LineString(newList);
    }

    public int[] getX() {
        int[] res = new int[pointList.size()];

        int index = 0;
        for (Point p : pointList) {
            res[index] = (int) p.getX();
            index++;
        }
        return res;
    }

    public int[] getY() {
        int[] res = new int[pointList.size()];

        int index = 0;
        for (Point p : pointList) {
            res[index] = (int) p.getY();
            index++;
        }
        return res;
    }

    public int size() {
        return pointList.size();
    }

    public String toDataString() {
        StringBuilder sb = new StringBuilder();
        sb.append("(");
        boolean first = true;
        for (Point p : pointList) {
            if (!first) {
                sb.append(",");
            }
            sb.append(p.getX());
            sb.append(" ");
            sb.append(p.getY());
            first = false;
        }
        sb.append(")");

        return sb.toString();
    }
}
