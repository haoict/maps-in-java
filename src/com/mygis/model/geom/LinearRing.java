package com.mygis.model.geom;

import java.util.ArrayList;
import java.util.List;

import com.mygis.model.crs.transform.GeometryTransform;

public class LinearRing extends LineString {

    public LinearRing(List<Point> pointList) {
        super(pointList);

        if (pointList.size() < 3) {
            throw new IllegalArgumentException("LinerRing point size must great two!");
        }

        if (!pointList.get(0).equals(pointList.get(pointList.size() - 1))) {
            throw new IllegalArgumentException("LinerRing first point and end point must be equal!");
        }
    }

    @Override
    public Geometry transform(GeometryTransform geometryTransform) {
        List<Point> newList = new ArrayList<Point>(pointList.size());
        for (Point p : pointList) {
            newList.add(geometryTransform.convert(p));
        }
        return new LinearRing(newList);
    }
}
