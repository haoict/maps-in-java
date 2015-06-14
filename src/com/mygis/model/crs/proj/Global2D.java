package com.mygis.model.crs.proj;

import com.mygis.model.crs.transform.GeometryTransform;
import com.mygis.model.crs.transform.PlanTransform;

public class Global2D extends Projection {

    PlanTransform trans;

    public Global2D(double f, double l) {
        this.trans = new PlanTransform(f, l);
    }

    @Override
    public GeometryTransform getReverseTransform() {
        throw new UnsupportedOperationException();
    }

    @Override
    public GeometryTransform getTransform() {
        return trans;
    }

}
