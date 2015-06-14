package com.mygis.model.crs.transform;

import com.mygis.model.geom.Point;

public class SphericalPolar implements GeometryTransform {

    private double f0;
    private double l0;

    public SphericalPolar(double f0, double l0) {
        this.f0 = f0;
        this.l0 = l0;
    }

    @Override
    public Point convert(Point p) {
        double f = Math.toRadians(p.getX());
        double l = Math.toRadians(p.getY());

        double z = Math.acos(cosz(f, l));
        double tana = sinzsina(f, l) / sinzcosa(f, l);
        double a = Math.atan(tana);

        return new Point(Math.toDegrees(z), Math.toDegrees(a));
    }

    protected double cosz(double f, double l) {
        double cosz = Math.sin(f) * Math.sin(f0) + Math.cos(f) * Math.cos(f0) * Math.cos(l - l0);
        return cosz;
    }

    protected double sinzcosa(double f, double l) {
        double r = Math.sin(f) * Math.cos(f0) - Math.cos(f) * Math.sin(f0) * Math.cos(l - l0);
        return r;
    }

    protected double sinzsina(double f, double l) {
        return Math.cos(f) * Math.sin(l - l0);
    }

}
