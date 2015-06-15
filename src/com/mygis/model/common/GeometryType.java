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
    SolutionPoint {
                public int getDimension() {
                    return 2;
                }

                public String toString() {
                    return "SolutionPoint";
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
    SolutionLineString {
                public int getDimension() {
                    return 2;
                }

                public String toString() {
                    return "SolutionLineString";
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
            },
    RequestPoint {
                public int getDimension() {
                    return 2;
                }

                public String toString() {
                    return "Point";
                }
            };

    public abstract int getDimension();
}
