package com.mygis.model.crs.proj;

import com.mygis.model.crs.CoordinateReferenceSystem;
import com.mygis.model.crs.Spheroid;
import com.mygis.model.crs.transform.GeometryTransform;
import com.mygis.model.geom.Point;

public class Mercator extends Projection {

    public Mercator() {
        CoordinateReferenceSystem.Wgs84.setSpheroid(new Spheroid("circle", 6378137, 6378137));
    }

    @Override
    public GeometryTransform getReverseTransform() {
        return null;
    }

    @Override
    public GeometryTransform getTransform() {
        Spheroid s = getCs().getSpheroid();
        return new MercatorTransform(s.getA(), s.getB(), s.getE(), s.getE2(), 0, 0);

    }

    class MercatorTransform implements GeometryTransform {

        private double k;
        private double l0;
        private double e;

        public MercatorTransform(double a, double b, double e, double e2, double b0, double l0) {
            super();
            this.k = computeK(a, b, e2, b0);
            this.l0 = l0;
            this.e = e;
        }

        @Override
        public Point convert(Point p) {
            double b = Math.toRadians(p.getY());
            double l = Math.toRadians(p.getX());

            double y = k * lntan(b, e);
            double x = k * (l - l0);

            return new Point(x, y);
        }

        private double lntan(double b, double e) {
            double d = Math.tan(Math.PI / 4 + b / 2)
                    * Math.pow((1 - e * Math.sin(b)) / (1 + e * Math.sin(b)), e / 2);

            return Math.log(d);
        }

    }

    class MercatorRTransform implements GeometryTransform {

        @Override
        public Point convert(Point p) {
            return p;
        }

    }

    private static double computeK(double a, double b, double e2, double b0) {
        double fz = Math.pow(a, 2) / b;
        double fm = Math.sqrt(1 + Math.pow(e2, 2) * Math.pow(Math.cos(b0), 2));
        double r = (fz / fm) * Math.cos(b0);
        return r;
    }

}
