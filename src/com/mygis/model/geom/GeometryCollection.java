package com.mygis.model.geom;

public interface GeometryCollection {

    public Geometry getGeometry(int index);

    public int size();
}
