package com.mygis.model.crs;

public class Datum {

    private String name;
    private double x;
    private double y;
    private double r;
    private double c;

    public Datum(String name, double x, double y, double r, double c) {
        this.name = name;
        this.x = x;
        this.y = y;
        this.r = r;
        this.c = c;
    }

    public String getName() {
        return name;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getR() {
        return r;
    }

    public double getC() {
        return c;
    }
}
