package com.mygis.model.crs;

public class CoordinateReferenceSystem {

    public static final CoordinateReferenceSystem Wgs84 = new CoordinateReferenceSystem("WGS84", 4326);

    private String name;
    private int srid;
    private Datum datum;
    private Spheroid spheroid;
    private int unit;

    public CoordinateReferenceSystem() {

    }

    public CoordinateReferenceSystem(String name, int srid) {
        this.name = name;
        this.srid = srid;
    }

    public String getName() {
        return name;
    }

    public int getSrid() {
        return srid;
    }

    public Datum getDatum() {
        return datum;
    }

    public void setDatum(Datum datum) {
        this.datum = datum;
    }

    public Spheroid getSpheroid() {
        return spheroid;
    }

    public void setSpheroid(Spheroid spheroid) {
        this.spheroid = spheroid;
    }

    public void setUnit(int unit) {
        this.unit = unit;
    }

    public int getUnit() {
        return unit;
    }
}
