/**
 * Author: Oleg Nizhnik
 * Date  : 07.10.2015
 * Time  : 16:58
 */
package ru.hh.school.mindist;

import java.util.List;
import java.util.stream.Stream;

/**
 * ѕромежуточный шаш алгоритма "раздел€й и властвуй" - полоса точек между
 */
public class PointStrip {

    private static class MinDist {

        private double minimalDist; //минимальна€ найденна€ дистанци€

        private Stream<Point> pointsByX; //список точек, упор€доченных по координате X
    }

    /**
     *  оллекци€ точек в наборе - всегда отсортирована по _x_
     */
    private List<Point> points;

    public PointStrip[] split() {
        final int median = (points.size() + 1) / 2;
        final List<Point> left = points.subList(0, median);
        final List<Point> right = points.subList(median, points.size());
        return new PointStrip[]{new PointStrip(left), new PointStrip(right)};
    }

    public List<Point> getPoints() {
        return points;
    }

    private PointStrip(List<Point> points) {
        this.points = points;
    }

    public MinDist getMinDist() {
        return new MinDist();
    }


}
