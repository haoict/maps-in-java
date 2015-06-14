package com.mygis.model.common;

import com.mygis.model.geom.Point;

public class Envelop {

    private double minX;
    private double minY;
    private double maxX;
    private double maxY;

    public Envelop() {
        this.minX = Double.POSITIVE_INFINITY;
        this.minY = Double.POSITIVE_INFINITY;
        this.maxX = Double.NEGATIVE_INFINITY;
        this.maxY = Double.NEGATIVE_INFINITY;
    }

    public Envelop(double x1, double y1, double x2, double y2) {
        if (x1 < x2) {
            minX = x1;
            maxX = x2;
        } else {
            minX = x2;
            maxX = x1;
        }

        if (y1 < y2) {
            minY = y1;
            maxY = y2;
        } else {
            minY = y2;
            maxY = y1;
        }
    }

    public double getMinX() {
        return minX;
    }

    public double getMinY() {
        return minY;
    }

    public double getMaxX() {
        return maxX;
    }

    public double getMaxY() {
        return maxY;
    }

    public Point getCenterPoint() {
        return new Point((maxX + minX) / 2d, (maxY + minY) / 2d);
    }

    public double getWidth() {
        return maxX - minX;
    }

    public double getHeight() {
        return maxY - minY;
    }

    public void extendEnvelop(Point point) {
        if (point.getX() < minX) {
            minX = point.getX();
        }

        if (point.getX() > maxX) {
            maxX = point.getX();
        }

        if (point.getY() < minY) {
            minY = point.getY();
        }

        if (point.getY() > maxY) {
            maxY = point.getY();
        }
    }

    public void extendEnvelop(Envelop otherEnvelop) {
        if (otherEnvelop.getMinX() < minX) {
            minX = otherEnvelop.getMinX();
        }

        if (otherEnvelop.getMaxX() > maxX) {
            maxX = otherEnvelop.getMaxX();
        }

        if (otherEnvelop.getMinY() < minY) {
            minY = otherEnvelop.getMinY();
        }

        if (otherEnvelop.getMaxY() > maxY) {
            maxY = otherEnvelop.getMaxY();
        }
    }

    public String toString() {
        return "Envelop { (" + minX + ", " + minY + ") (" + maxX + ", " + maxY + ") }";
    }

}
