package com.mygis.model.geom;

import com.mygis.model.common.Envelop;
import com.mygis.model.common.GeometryType;

public abstract class AbstractGeometry implements Geometry {

    protected GeometryType geometryType;
    protected Envelop envelop;

    public AbstractGeometry(GeometryType geometryType) {
        this.geometryType = geometryType;
    }

    public GeometryType getGeometryType() {
        return this.geometryType;
    }

    public Envelop getEnvelop() {
        if (envelop == null) {
            calculateEnvelop();
        }
        return envelop;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(geometryType.toString());
        sb.append(toDataString());
        return sb.toString();
    }

    abstract protected void calculateEnvelop();

    abstract protected String toDataString();
}
