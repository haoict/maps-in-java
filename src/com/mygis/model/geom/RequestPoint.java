package com.mygis.model.geom;

import com.mygis.model.common.Envelop;
import com.mygis.model.common.GeometryType;
import com.mygis.model.crs.transform.GeometryTransform;


//Tien
public class RequestPoint extends Point {

    private int id;
    private double x;
    private double y;

    public RequestPoint(int id) {
        super(id);
    }

    public RequestPoint(double x, double y) {
        super(x,y);
    }

    public RequestPoint(int id, double x, double y) {
        super(id,x,y);
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

        if (Double.doubleToLongBits(x) != Double.doubleToLongBits(p.getX())) {
            return false;
        }

        if (Double.doubleToLongBits(y) != Double.doubleToLongBits(p.getY())) {
            return false;
        }

        return true;
    }

    public String toDataString() {
        return "(" + x + " " + y + ")";
    }
}
