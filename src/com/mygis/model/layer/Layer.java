package com.mygis.model.layer;

import java.util.ArrayList;
import java.util.List;

import com.mygis.model.common.Envelop;
import com.mygis.model.crs.transform.GeometryTransform;
import com.mygis.model.geom.Geometry;

public class Layer {

    private List<Geometry> geometryList = new ArrayList<Geometry>();

    public Layer() {
    }

    public void addGeometry(Geometry geo) {
        geometryList.add(geo);
    }

    public int size() {
        return geometryList.size();
    }

    public Geometry getGeometry(int index) {
        if (index >= geometryList.size()) {
            throw new IllegalArgumentException("Error index");
        }

        return geometryList.get(index);
    }

    public Envelop getEnvelop() {
        Envelop envelop = new Envelop();
        for (Geometry geom : geometryList) {
            envelop.extendEnvelop(geom.getEnvelop());
        }
        return envelop;
    }

    public Layer transform(GeometryTransform trans) {
        Layer newLayer = new Layer();
        for (Geometry geom : geometryList) {
            newLayer.addGeometry(geom.transform(trans));
        }
        return newLayer;
    }
}
