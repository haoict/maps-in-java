package com.mygis.model.geom;

import java.util.ArrayList;
import java.util.List;

import com.mygis.model.common.GeometryType;
import com.mygis.model.crs.transform.GeometryTransform;

public class Polygon extends AbstractGeometry {

    private LinearRing shell;
    private List<LinearRing> holes;

    public Polygon(LinearRing shell) {
        this(shell, new ArrayList<LinearRing>(0));
    }

    public Polygon(LinearRing shell, List<LinearRing> holes) {
        super(GeometryType.Polygon);

        this.shell = shell;
        this.holes = holes;
    }

    @Override
    protected void calculateEnvelop() {
        envelop = shell.getEnvelop();
    }

    public LinearRing getShell() {
        return shell;
    }

    public List<LinearRing> getHoles() {
        return holes;
    }

    @Override
    public Geometry transform(GeometryTransform geometryTransform) {
        LinearRing newShell = (LinearRing) shell.transform(geometryTransform);

        List<LinearRing> newHoles = new ArrayList<LinearRing>(holes.size());
        for (LinearRing hole : holes) {
            newHoles.add((LinearRing) hole.transform(geometryTransform));
        }
        return new Polygon(newShell, newHoles);
    }

    public String toDataString() {
        StringBuilder sb = new StringBuilder();

        sb.append("(");
        sb.append(shell.toDataString());

        for (LinearRing hole : holes) {

            sb.append(",");
            sb.append(hole.toDataString());

        }
        sb.append(")");

        return sb.toString();
    }

}
