package com.mygis.model.crs.transform;

import com.mygis.model.geom.Point;

public interface GeometryTransform {

    public Point convert(Point point);
}
