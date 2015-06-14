package com.mygis.model.crs.transform;

import com.mygis.model.geom.Point;

public class PlanTransform extends SphericalPolar {

    public PlanTransform(double f0, double l0) {
        super(f0, l0);
    }

    @Override
    public Point convert(Point p) {
        double f = Math.toRadians(p.getY());
        double l = Math.toRadians(p.getX());

        double cosz = cosz(f, l);
        double cota = sinzcosa(f, l) / sinzsina(f, l);
        double x = Math.atan(cota / cosz);
        double cosx = Math.cos(x);
        double y = Math.atan(cosx / cosz);

        return new Point(y, x);
    }

}
