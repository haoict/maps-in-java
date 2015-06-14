package com.mygis.model.crs.transform;

import com.mygis.math.Matrix;
import com.mygis.model.geom.Point;

public class AffineTransform implements GeometryTransform {

    private Matrix matrix;

    public AffineTransform() {
        matrix = new Matrix();
    }

    public AffineTransform(Matrix matrix) {
        this.matrix = matrix;
    }

    @Override
    public Point convert(Point point) {
        Matrix sourceMatrix = new Matrix(3, 3);

        sourceMatrix.setValue(0, 0, point.getX());
        sourceMatrix.setValue(0, 1, point.getY());
        sourceMatrix.setValue(0, 2, 1);

        Matrix result = sourceMatrix.multiply(matrix);

        return new Point(result.getValue(0, 0), result.getValue(0, 1));
    }

    public Matrix getMatrix() {
        return matrix;
    }

    protected void setValue(int rowIndex, int colIndex, double newValue) {
        matrix.setValue(rowIndex, colIndex, newValue);
    }

    public AffineTransform accumulate(AffineTransform other) {
        return new AffineTransform(matrix.multiply(other.getMatrix()));
    }

    public static AffineTransform pan(double dx, double dy) {
        AffineTransform transf = new AffineTransform();
        transf.setValue(2, 0, dx);
        transf.setValue(2, 1, dy);
        transf.setValue(2, 2, 1);
        return transf;
    }

    public static AffineTransform rotate(double x, double y, double thta) {
        AffineTransform transf = new AffineTransform();
        transf.setValue(0, 0, Math.cos(thta));
        transf.setValue(1, 1, Math.cos(thta));
        transf.setValue(2, 2, 1);
        transf.setValue(0, 1, Math.sin(thta));
        transf.setValue(1, 0, -Math.sin(thta));
        transf.setValue(2, 0, (1 - Math.cos(thta)) * x + (y * Math.sin(thta)));
        transf.setValue(2, 1, (1 + Math.cos(thta)) * y - (x * Math.sin(thta)));
        return transf;
    }

    public static AffineTransform scale(double x, double y, double sx, double sy) {
        AffineTransform transf = new AffineTransform();
        transf.setValue(0, 0, sx);
        transf.setValue(1, 1, sy);
        transf.setValue(2, 2, 1);
        transf.setValue(2, 0, (1 - sx) * x);
        transf.setValue(2, 1, (1 - sy) * y);
        return transf;
    }

    public static AffineTransform shear(double b, double d) {
        AffineTransform transf = new AffineTransform();
        transf.setValue(0, 0, 1);
        transf.setValue(1, 1, 1);
        transf.setValue(2, 2, 1);
        transf.setValue(0, 1, d);
        transf.setValue(1, 0, b);
        return transf;
    }

    public static AffineTransform symmetry(double a, double b, double d, double e) {
        AffineTransform transf = new AffineTransform();
        transf.setValue(0, 0, a);
        transf.setValue(1, 1, e);
        transf.setValue(2, 2, 1);
        transf.setValue(0, 1, d);
        transf.setValue(1, 0, b);
        return transf;
    }
}
