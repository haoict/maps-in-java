package com.mygis.model.common;

public enum GeometryType {

    Point {
                public int getDimension() {
                    return 2;
                }

                public String toString() {
                    return "Point";
                }
            },
    LineString {
                public int getDimension() {
                    return 2;
                }

                public String toString() {
                    return "LineString";
                }
            },
    Polygon {
                public int getDimension() {
                    return 2;
                }

                public String toString() {
                    return "Polygon";
                }
            },
    MultiPoint {
                public int getDimension() {
                    return 2;
                }

                public String toString() {
                    return "MultiPoint";
                }
            },
    MultiLineString {
                public int getDimension() {
                    return 2;
                }

                public String toString() {
                    return "MultiLineString";
                }
            },
    MultiPolygon {
                public int getDimension() {
                    return 2;
                }

                public String toString() {
                    return "MultiPolygon";
                }
            };

    public abstract int getDimension();
}
