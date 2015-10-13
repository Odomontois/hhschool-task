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
    enum Flag {
        LABELLED("-l", "--labelled");

        private String[] params;

        Flag(String... params) {
            this.params = params;
        }

        public static Optional<Flag> parseOne(String arg) {
            for (Flag flag : values())
                for (String param : flag.params)
                    if (arg == param) return Optional.of(flag);

            return Optional.empty();
        }

        public static Set<Flag> parse(List<String> args) {
            final Set<Flag> result = new HashSet<>();

            args.stream()
                    .map(Flag::parseOne)
                    .flatMap(f -> f.map(Stream::of).orElse(Stream.empty()))
                    .forEach(result::add);

            return result;
        }
    }


    static Point parsePoint(String s) {
        final StringTokenizer tok = new StringTokenizer(s);
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

    public static void main(String[] args) {
        try {
            final List<String> lines = Files.readAllLines(Paths.get(args[0]));
            final List<Point> points = new ArrayList<>(lines.size());
            final Set<Flag> flags = Flag.parse(Arrays.asList(args).subList(1, args.length));
            final Function<String, Point> parse;

            if (flags.contains(Flag.LABELLED))
                parse = MinimalDistance::parseLabelled;
            else parse = MinimalDistance::parsePoint;

            lines.stream().map(parse).forEach(points::add);
            System.out.println(MinDistSearcher.find(points));

        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
            ex.printStackTrace(System.err);
        }

    }
}
