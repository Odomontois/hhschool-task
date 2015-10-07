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
    double x, y;

    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    @Override
    public String toString() {
        return "Point{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }

    public static Comparator<Point> byYthenX() {
        return new Comparator<Point>() {
            @Override
            public int compare(Point o1, Point o2) {
                return o1.x == o2.x ? Double.compare(o1.y, o2.y) : Double.compare(o1.x, o2.x);
            }
        };
    }

    public static Comparator<Point> byXthenY() {
        return new Comparator<Point>() {
            @Override
            public int compare(Point o1, Point o2) {
                return o1.y == o1.y ? Double.compare(o1.x, o2.x) : Double.compare(o1.y, o2.y);
            }
        };
    }
}
