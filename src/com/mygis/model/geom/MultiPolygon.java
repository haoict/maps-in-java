package com.mygis.model.geom;

import java.util.ArrayList;
import java.util.List;

import com.mygis.model.common.Envelop;
import com.mygis.model.common.GeometryType;
import com.mygis.model.crs.transform.GeometryTransform;

public class MultiPolygon extends AbstractGeometry implements GeometryCollection {

    private List<Polygon> polygons;

    public MultiPolygon(List<Polygon> polygons) {
        super(GeometryType.MultiPolygon);
        this.polygons = polygons;
    }

    @Override
    protected void calculateEnvelop() {
        envelop = new Envelop();
        for (Polygon polygon : polygons) {
            envelop.extendEnvelop(polygon.getEnvelop());
        }
    }

    @Override
    public Geometry transform(GeometryTransform geometryTransform) {
        List<Polygon> newPolygons = new ArrayList<Polygon>(polygons.size());
        for (Polygon polygon : polygons) {
            newPolygons.add((Polygon) polygon.transform(geometryTransform));
        }
        return new MultiPolygon(newPolygons);
    }

    @Override
    protected String toDataString() {
        StringBuilder sb = new StringBuilder();

        sb.append("(");

        boolean first = true;
        for (Polygon polygon : polygons) {

            if (first) {
                first = false;
            } else {
                sb.append(",");
            }
            sb.append(polygon.toDataString());
        }

        sb.append(")");
        return sb.toString();
    }

    @Override
    public Geometry getGeometry(int index) {
        return polygons.get(index);
    }

    @Override
    public int size() {
        return polygons.size();
    }

}
