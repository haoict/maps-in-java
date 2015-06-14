package com.mygis.model.geom;

import com.mygis.model.common.Envelop;
import com.mygis.model.common.GeometryType;
import com.mygis.model.crs.transform.GeometryTransform;

public interface Geometry {

    /**
     * Get geometry type
     *
     * @return
     */
    public GeometryType getGeometryType();

    /**
     * Get Envelop
     *
     * @return
     */
    public Envelop getEnvelop();

    public Geometry transform(GeometryTransform geometryTransform);
}
