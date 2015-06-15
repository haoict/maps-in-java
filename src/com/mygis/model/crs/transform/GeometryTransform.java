package com.mygis.model.crs.transform;

import com.mygis.model.geom.Point;
import com.mygis.model.geom.RequestPoint;

public interface GeometryTransform {

    public Point convert(Point point);
}
