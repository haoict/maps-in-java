package com.mygis.model.geom;

import com.mygis.model.common.Envelop;
import com.mygis.model.common.GeometryType;
import com.mygis.model.crs.transform.GeometryTransform;

public class Point extends AbstractGeometry {

    private int id;
    private double x;
    private double y;

    public Point(int id) {
        super(GeometryType.Point);
        this.id = id;
    }

    public Point(double x, double y) {
        super(GeometryType.Point);
        this.x = x;
        this.y = y;
    }

    public Point(int id, double x, double y) {
        super(GeometryType.Point);
        this.id = id;
        this.x = x;
        this.y = y;
    }

    public int getId() {
        return id;
    }

    public Integer getID() {
        return Integer.valueOf(id);
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public void calculateEnvelop() {
        this.envelop = new Envelop(x, y, x, y);
    }

    public Geometry transform(GeometryTransform geometryTransform) {
        return geometryTransform.convert(this);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj == null) {
            return false;
        }

        if (this.getClass() != obj.getClass()) {
            return false;
        }

        Point p = (Point) obj;

        if (Double.doubleToLongBits(x) != Double.doubleToLongBits(p.x)) {
            return false;
        }

        if (Double.doubleToLongBits(y) != Double.doubleToLongBits(p.y)) {
            return false;
        }

        return true;
    }

    public String toDataString() {
        return "(" + x + " " + y + ")";
    }
}
