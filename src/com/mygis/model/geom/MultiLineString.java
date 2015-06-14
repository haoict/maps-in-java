package com.mygis.model.geom;

import java.util.ArrayList;
import java.util.List;

import com.mygis.model.common.Envelop;
import com.mygis.model.common.GeometryType;
import com.mygis.model.crs.transform.GeometryTransform;

public class MultiLineString extends AbstractGeometry implements GeometryCollection {

    List<LineString> lines;

    public MultiLineString(List<LineString> lines) {
        super(GeometryType.MultiLineString);
        if (lines == null || lines.size() == 0) {
            throw new IllegalArgumentException("Illegal list!");
        }
        this.lines = lines;
    }

    @Override
    protected void calculateEnvelop() {
        envelop = new Envelop();

        for (LineString lineString : lines) {
            envelop.extendEnvelop(lineString.getEnvelop());
        }
    }

    @Override
    public Geometry transform(GeometryTransform geometryTransform) {
        List<LineString> newLines = new ArrayList<LineString>(lines.size());
        for (LineString line : lines) {
            newLines.add((LineString) line.transform(geometryTransform));
        }
        return new MultiLineString(newLines);
    }

    @Override
    public Geometry getGeometry(int index) {
        if (index < 0 || index >= lines.size()) {
            throw new IllegalArgumentException("Invalid index " + index);
        }
        return lines.get(index);
    }

    @Override
    public int size() {
        return lines.size();
    }

    public String toDataString() {
        StringBuilder sb = new StringBuilder();
        sb.append("(");

        boolean firstLine = true;
        for (LineString line : lines) {
            if (firstLine) {
                firstLine = false;
            } else {
                sb.append(",");
            }
            sb.append(line.toDataString());

        }
        sb.append(")");

        return sb.toString();
    }
}
