/**
 * Author: Oleg Nizhnik
 * Date  : 07.10.2015
 * Time  : 16:58
 */
package ru.hh.school.mindist;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Поиск минимального расстояния методом "разделяй и властвуй"
 */
public abstract class MinDistSearcher<P extends Point> {
    protected MinDistSearcher() {
    }

    /**
     * Промежуточное решение алгоритма "разделяй и властвуй"
     */
    public static class Result<P extends Point> {
        double distance;

        P point1, point2;

        Result(double distance, P point1, P point2) {
            this.distance = distance;
            this.point1 = point1;
            this.point2 = point2;
        }


        public Result<P> bestOf(Result<P> other) {
            if (Double.compare(this.distance, other.distance) < 0) return this;
            else return other;
        }

        @Override
        public String toString() {
            return "distance "  + distance +
                    " between " + point1 +
                    " and "     + point2;
        }
    }

    /**
     * Промежуточное решение вместе со списком точек, упорядоченных по Y
     */
    private static class SearchResult<P extends Point> {
        /**
         * список точек, упорядоченных по координате Y
         */
        Result<P> result;
        List<P> pointsByY;

        public SearchResult(Result<P> result, List<P> pointsByY) {
            this.result = result;
            this.pointsByY = pointsByY;
        }
    }

    abstract SearchResult<P> search();

    /**
     * Промежуточная коллекция точек алгоритма в вертикальной полосе
     */
    static class PointStrip<P extends Point> extends MinDistSearcher<P> {
        /**
         * Коллекция точек в наборе - всегда отсортирована по _x_
         */
        private List<P> points;

        @Override
        SearchResult<P> search() {
            final int median = (points.size() + 1) / 2;
            final MinDistSearcher<P> leftSearch = create(points.subList(0, median));
            final MinDistSearcher<P> rightSearch = create(points.subList(median, points.size()));

            final SearchResult<P> left = leftSearch.search();
            final SearchResult<P> right = rightSearch.search();

            final Result<P> currentResult = left.result.bestOf(right.result);
            final List <P> pointsByY = Utils.merge(left.pointsByY, right.pointsByY, Point::compareYthenX);

            //noinspection SuspiciousNameCombination
            final Result<P> optimal = searchAlongDivLine(left.pointsByY, right.pointsByY, currentResult);

            return new SearchResult<>(optimal, pointsByY);
        }

        private Result<P> searchAlongDivLine(List<P> left, List<P> right, final Result<P> current) {
            final double borderX = Collections.max(left, Point::compareXthenY).getX();

            final LinkedList<P> leftBorder = left.stream().filter(p -> p.getX() >= borderX - current.distance)
                    .collect(Collectors.toCollection(LinkedList::new));
            final LinkedList<P> rightBorder = right.stream().filter(p -> p.getX() <= borderX + current.distance)
                    .collect(Collectors.toCollection(LinkedList::new));

            final LinkedList<P> comparing = new LinkedList<>();

            Result<P> optimal = current;

            for (P p : leftBorder) {
                //заполняем коллекцию только теми элементами, что лежат в диапазане [p.y - mindDist, p.y + minDist]
                while (!comparing.isEmpty() && comparing.getFirst().getY() < p.getY() - optimal.distance) {
                    comparing.removeFirst();
                }
                while (!rightBorder.isEmpty() && rightBorder.getFirst().getY() <= p.getY() + optimal.distance) {
                    comparing.addLast(rightBorder.removeFirst());
                }

                optimal = checkMinimumDist(p, comparing, optimal);
            }
            return optimal;
        }

        /**
         * Находит минимум среди отобранных точек, сравнивает с имеющимся
         */
        final Result<P> checkMinimumDist(P point, Collection<P> comparing, final Result<P> current) {
            return comparing.stream()
                    .min(point::compareDistance)
                    .filter(that -> point.dist(that) < current.distance)
                    .map(that -> new Result<>(point.dist(that), point, that))
                    .orElse(current);
        }


        PointStrip(List<P> points) {
            this.points = points;
        }
    }

    static class SinglePoint<P extends Point> extends MinDistSearcher<P> {
        private P point;

        @Override
        SearchResult<P> search() {
            return new SearchResult<>(
                    new Result<>(Double.POSITIVE_INFINITY, point, point),
                    Collections.singletonList(point));
        }

        SinglePoint(P point) {
            this.point = point;
        }
    }

    protected static <P extends Point> MinDistSearcher<P> create(List<P> points) {
        if (points.size() == 1) return new SinglePoint<>(points.get(0));
        else return new PointStrip<>(points);
    }

    public static <P extends Point> Result<P> find(Collection<P> points) {
        final List<P> sorted = new ArrayList<>(points);
        sorted.sort(Point::compareXthenY);
        return MinDistSearcher.create(sorted).search().result;
    }
}
