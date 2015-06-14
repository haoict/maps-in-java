package com.mygis.math;

public class Matrix {

    private int m;
    private int n;
    private double[][] data;

    public Matrix() {
        m = n = 3;
        data = new double[m][n];
        for (int i = 0; i < m; i++) {
            data[i][i] = 1;
        }

    }

    public Matrix(int m, int n) {
        if (m < 1 || n < 1) {
            throw new IllegalArgumentException("Matrix rows or columns must greater than zero!");
        }
        this.m = m;
        this.n = n;
        data = new double[m][n];
    }

    public int getRowCount() {
        return m;
    }

    public int getColumnCount() {
        return n;
    }

    public double getValue(int rowIndex, int colIndex) {
        if (rowIndex >= m || colIndex >= n) {
            throw new IllegalArgumentException("row or column index out of bundery exception!");
        }
        return data[rowIndex][colIndex];
    }

    public double setValue(int rowIndex, int colIndex, double newValue) {
        if (rowIndex >= m || colIndex >= n) {
            throw new IllegalArgumentException("row or column index out of bundery exception!");
        }
        return data[rowIndex][colIndex] = newValue;
    }

    /**
     * Two matrix add operation
     *
     * @param other
     * @return
     */
    public Matrix add(Matrix other) {
        if (m != other.getRowCount()) {
            throw new IllegalArgumentException("Matrix must have same row count when do add operation!");
        }
        if (n != other.getColumnCount()) {
            throw new IllegalArgumentException("Matrix must have same column count when do add operation!");
        }

        Matrix result = new Matrix(m, n);
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                result.setValue(i, j, data[i][j] + other.getValue(i, j));
            }
        }
        return result;
    }

    /**
     * Two Matrix multiply operation
     *
     * @param other
     * @return
     */
    public Matrix multiply(Matrix other) {
        if (n != other.getRowCount()) {
            throw new IllegalArgumentException("First matrix column count must equals to second matrix row count when do multiply operation!");
        }
        Matrix result = new Matrix(m, other.getColumnCount());
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < other.getColumnCount(); j++) {
                double tmp = 0;
                for (int k = 0; k < n; k++) {
                    tmp += data[i][k] * other.getValue(k, j);
                }
                result.setValue(i, j, tmp);
            }
        }
        return result;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Matrix:");
        sb.append("\n");

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                sb.append("   ");
                sb.append(data[i][j]);
            }
            sb.append("\n");
        }
        return sb.toString();
    }

}
