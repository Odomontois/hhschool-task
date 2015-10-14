/**
 * Author: Oleg Nizhnik
 * Date  : 14.10.2015
 * Time  : 8:55
 */
package ru.hh.school.runnable;

import java.util.*;
import java.util.stream.Stream;

public interface RunFlag {
    String[] getParams();

    static <F extends RunFlag> Optional<F> parseOne(F[] flags, String arg) {
        for (F flag : flags)
            for (String param : flag.getParams())
                if (Objects.equals(arg, param)) return Optional.of(flag);

        return Optional.empty();
    }

    static <F extends RunFlag> Set<F> parse(F[] flags, List<String> args) {
        final Set<F> result = new HashSet<>();

        args.stream()
                .map(arg -> RunFlag.parseOne(flags, arg))
                .flatMap(f -> f.map(Stream::of).orElse(Stream.empty()))
                .forEach(result::add);

        return result;
    }
}
