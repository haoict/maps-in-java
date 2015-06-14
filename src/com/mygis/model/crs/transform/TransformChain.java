package com.mygis.model.crs.transform;

import java.util.ArrayList;
import java.util.List;

import com.mygis.model.geom.Point;

public class TransformChain implements GeometryTransform {

    private List<GeometryTransform> transforms = new ArrayList<GeometryTransform>();

    @Override
    public Point convert(Point p) {
        Point tp = p;
        for (GeometryTransform trans : transforms) {
            if (trans == null) {
                continue;
            }
            tp = trans.convert(tp);
        }
        return tp;
    }

    public void add(GeometryTransform trans) {
        transforms.add(trans);
    }
}
