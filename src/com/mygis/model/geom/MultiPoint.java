package com.mygis.model.geom;

import java.util.ArrayList;
import java.util.List;

import com.mygis.model.common.Envelop;
import com.mygis.model.common.GeometryType;
import com.mygis.model.crs.transform.GeometryTransform;

public class MultiPoint extends AbstractGeometry implements GeometryCollection {

    private List<Point> pointList;

    public MultiPoint() {
        this(new ArrayList<Point>());
    }

    public MultiPoint(List<Point> pointList) {
        super(GeometryType.MultiPoint);

        if (pointList == null) {
            throw new IllegalArgumentException("Point list can not be null");
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
        return new MultiPoint(newList);
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

    @Override
    public Geometry getGeometry(int index) {
        if (index < 0 || index >= pointList.size()) {
            throw new IllegalArgumentException("Invalid index " + index);
        }
        return pointList.get(index);
    }

    @Override
    public int size() {
        return pointList.size();
    }

}
