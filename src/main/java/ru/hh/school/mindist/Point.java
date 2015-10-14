/**
 * Author: Oleg Nizhnik
 * Date  : 07.10.2015
 * Time  : 16:47
 */
package ru.hh.school.mindist;

import java.util.Comparator;

/**
 * Точка на двумерной плоскости
 */
public class Point {
    protected double x, y;

    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    @Override
    public String toString() {
        return "Point{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }

    public static int compareXthenY(Point p1, Point p2) {
        return p1.x == p2.x ? Double.compare(p1.y, p2.y) : Double.compare(p1.x, p2.x);
    }

    public static int compareYthenX(Point p1, Point p2) {
        return p1.y == p2.y ? Double.compare(p1.x, p2.x) : Double.compare(p1.y, p2.y);
    }

    public double dist(Point point) {
        final double dx = point.x - this.x;
        final double dy = point.y - this.y;
        return Math.sqrt(dx * dx + dy * dy);
    }

    public int compareDistance(Point p1, Point p2){
        return Double.compare(this.dist(p1), this.dist(p2));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Point)) return false;

        Point point = (Point) o;

        return Double.compare(point.x, x) == 0 && Double.compare(point.y, y) == 0;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        temp = Double.doubleToLongBits(x);
        result = (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(y);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }
}
