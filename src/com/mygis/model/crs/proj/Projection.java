package com.mygis.model.crs.proj;

import com.mygis.model.crs.CoordinateReferenceSystem;
import com.mygis.model.crs.transform.GeometryTransform;

public abstract class Projection {

    private String name;
    private CoordinateReferenceSystem cs;

    public String getName() {
        return name;
    }

    public CoordinateReferenceSystem getCs() {
        return cs;
    }

    public abstract GeometryTransform getTransform();

    public abstract GeometryTransform getReverseTransform();
}
