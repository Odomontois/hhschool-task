/**
 * Author: Oleg Nizhnik
 * Date  : 07.10.2015
 * Time  : 16:58
 */
package ru.hh.school.mindist;

import java.util.List;

/**
 * Промежуточный шаш алгоритма "разделяй и властвуй" - полоса точек между
 */
public class PointStrip {
    /**
     * Коллекция точек в наборе - всегда отсортирована по _x_
     */
    List<Point> points;

    public PointStrip[] split() {
        final int median = (points.size() + 1) / 2;
        final List<Point> left = points.subList(0, median);
        final List<Point> right = points.subList(median, points.size());
        return new PointStrip[]{ new PointStrip(left), new PointStrip(right) };
    }



    private PointStrip(List<Point> points) {
        this.points = points;
    }
}
