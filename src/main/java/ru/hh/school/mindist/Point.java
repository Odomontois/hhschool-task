/**
 * Author: Oleg Nizhnik
 * Date  : 07.10.2015
 * Time  : 16:47
 */
package ru.hh.school.mindist;

/**
 * Точка на двумерной плоскости
 */
public class Point {
    double x,y;

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
}
