/**
 * Author: Oleg Nizhnik
 * Date  : 14.10.2015
 * Time  : 9:23
 */
package ru.hh.school.runnable;

import ru.hh.school.typeclasses.BigIntegerWhole;
import ru.hh.school.fraction.Fraction;
import ru.hh.school.typeclasses.LongWhole;
import ru.hh.school.typeclasses.Whole;
import ru.hh.school.utils.CyclicSequence;
import ru.hh.school.utils.Pair;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class Fractions {
    enum Flag implements RunFlag {
        BIG_INTEGER("-b", "--bigint"),
        LONG("-l", "--long"),
        CONSTANT_MEMORY("-cm", "--O1memory"),
        MEMOIZE("-s", "--memoize");

        private String[] params;

        Flag(String... params) {
            this.params = params;
        }

        @Override
        public String[] getParams() {
            return params;
        }
    }

    static <N> Pair<Fraction<N>, N> parseFraction(String line, Whole<N> whole) {
        final StringTokenizer tok = new StringTokenizer(line);
        final N numerator = whole.parse(tok.nextToken());
        final N denominator = whole.parse(tok.nextToken());
        final N base = whole.parse(tok.nextToken());
        final Fraction<N> fraction = Fraction.create(numerator, denominator, whole);
        return Pair.of(fraction, base);
    }


    static <N> List<Pair<Fraction<N>, N>> parseFractions(String filename, Whole<N> whole) throws IOException {
        final List<String> lines = Files.readAllLines(Paths.get(filename));
        final List<Pair<Fraction<N>, N>> fractions = new ArrayList<>(lines.size());

        lines.stream().map(line -> parseFraction(line, whole)).forEach(fractions::add);

        return fractions;
    }

    static <N> List<String> createReprs(String filename, Whole<N> whole, CyclicSequence.Method method) throws IOException {
        final List<Pair<Fraction<N>, N>> fractions = parseFractions(filename, whole);
        final List<String> reprs = new ArrayList<>(fractions.size());

        fractions.stream()
                .map(fraction -> fraction.getFirst().toDigitalString(fraction.getSecond(), method))
                .forEach(reprs::add);

        return reprs;
    }

    public static void main(String[] args) {
        try {
            final Set<Flag> flags = RunFlag.parse(Flag.values(), Arrays.asList(args).subList(1, args.length));
            final Whole<?> whole;
            final CyclicSequence.Method method;

            //BigInteger приоритетней и по умолчанию
            if (flags.contains(Flag.BIG_INTEGER))
                whole = BigIntegerWhole.INSTANCE;
            else if (flags.contains(Flag.LONG))
                whole = LongWhole.INSTANCE;
            else whole = BigIntegerWhole.INSTANCE;

            //Constant-memory приоритетней и по умолчанию
            if (flags.contains(Flag.CONSTANT_MEMORY))
                method = CyclicSequence.Method.CONSTANT_MEMORY;
            else if (flags.contains(Flag.MEMOIZE))
                method = CyclicSequence.Method.MEMOIZE;
            else method = CyclicSequence.Method.MEMOIZE;

            final List<String> reprs = createReprs(args[0], whole, method);

            reprs.forEach(System.out::println);
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        } catch (Throwable ex) {
            ex.printStackTrace(System.err);
        }
    }
}
