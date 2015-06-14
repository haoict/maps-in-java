package com.mygis.model.crs;

public class Spheroid {

    private String name;
    private double a;
    private double b;
    private double e;
    private double e2;

    public Spheroid(String name, double a, double b) {
        super();

        if (a < b) {
            throw new IllegalArgumentException("a must lagger then b");
        }

        this.name = name;
        this.a = a;
        this.b = b;

        e = computeE();
        e2 = computeE2();
    }

    public String getName() {
        return name;
    }

    public double getA() {
        return a;
    }

    public double getB() {
        return b;
    }

    public double getE() {
        return e;
    }

    public double getE2() {
        return e2;
    }

    private double computeE() {
        return Math.sqrt(1 - Math.pow((b / a), 2));
    }

    private double computeE2() {
        return Math.sqrt(Math.pow((a / b), 2) - 1);
    }
}
