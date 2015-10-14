/**
 * Author: Oleg Nizhnik
 * Date  : 13.10.2015
 * Time  : 17:22
 */
package ru.hh.school.runnable;

import ru.hh.school.mindist.LabelledPoint;
import ru.hh.school.mindist.MinDistSearcher;
import ru.hh.school.mindist.Point;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Stream;

public class MinimalDistance {
    enum Flag implements RunFlag {
        LABELLED("-l", "--labelled"),
        VERBOSE("-l", "--verbose");

        private String[] params;

        Flag(String... params) {
            this.params = params;
        }

        @Override
        public String[] getParams() {
            return params;
        }
    }

    static Point parsePoint(String line) {
        final StringTokenizer tok = new StringTokenizer(line);
        final double x = Double.parseDouble(tok.nextToken());
        final double y = Double.parseDouble(tok.nextToken());
        return new Point(x, y);
    }

    static LabelledPoint<String> parseLabelled(String s) {
        final StringTokenizer tok = new StringTokenizer(s);
        final String label = tok.nextToken();
        final double x = Double.parseDouble(tok.nextToken());
        final double y = Double.parseDouble(tok.nextToken());
        return new LabelledPoint<>(x, y, label);
    }

    static List<Point> parsePoints(String filename, boolean labelled) throws IOException {
        final List<String> lines = Files.readAllLines(Paths.get(filename));
        final List<Point> points = new ArrayList<>(lines.size());
        final Function<String, Point> parse;

        if (labelled) parse = MinimalDistance::parseLabelled;
        else parse = MinimalDistance::parsePoint;
        lines.stream().map(parse).forEach(points::add);

        return points;
    }

    static void report(MinDistSearcher.Result<Point> result, boolean verbose) {
        System.out.println(verbose ? result : result.getDistance());
    }

    public static void main(String[] args) {
        try {
            final Set<Flag> flags = RunFlag.parse(Flag.values(), Arrays.asList(args).subList(1, args.length));

            List<Point> points = parsePoints(args[0], flags.contains(Flag.LABELLED));
            report(MinDistSearcher.find(points), flags.contains(Flag.VERBOSE));
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
            ex.printStackTrace(System.err);
        }
    }
}
