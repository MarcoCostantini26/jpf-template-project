/*
 *   V2d.java
 *
 * Copyright 2000-2001-2002  aliCE team at deis.unibo.it
 *
 * This software is the proprietary information of deis.unibo.it
 * Use is subject to license terms.
 *
 */
package pcd.ass01.common;

import java.util.Objects;

/**
 * 2-dimensional vector
 * objects are completely state-less
 */
public final class V2d {
    private final double x;
    private final double y;

    /**
     *
     */
    public V2d(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public V2d sum(V2d v) {
        return new V2d(x + v.x, y + v.y);
    }

    public double abs() {
        return (double) Math.sqrt(x * x + y * y);
    }

    public V2d getNormalized() {
        double module = (double) Math.sqrt(x * x + y * y);
        return new V2d(x / module, y / module);
    }

    public V2d mul(double fact) {
        return new V2d(x * fact, y * fact);
    }

    public String toString() {
        return "V2d(" + x + "," + y + ")";
    }

    public double x() {
        return x;
    }

    public double y() {
        return y;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (V2d) obj;
        return Double.doubleToLongBits(this.x) == Double.doubleToLongBits(that.x) &&
                Double.doubleToLongBits(this.y) == Double.doubleToLongBits(that.y);
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }


}
