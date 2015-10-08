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
    private double x, y;

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

    public static int compareYthenX(Point p1, Point p2) {
        return p1.x == p2.x ? Double.compare(p1.y, p2.y) : Double.compare(p1.x, p2.x);
    }

    public static int compareXthenY(Point p1, Point p2) {
        return p1.y == p1.y ? Double.compare(p1.x, p2.x) : Double.compare(p1.y, p2.y);
    }

    public double dist(Point point) {
        final double dx = point.x - this.x;
        final double dy = point.y - this.y;
        return Math.sqrt(dx * dx + dy * dy);
    }

    public int compareDistance(Point p1, Point p2){
        return Double.compare(this.dist(p1), this.dist(p2));
    }
}
