/**
 * Author: Oleg Nizhnik
 * Date  : 07.10.2015
 * Time  : 17:36
 */
package ru.hh.school.mindist;

import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * Утилиты для обработки списов
 */
public class Utils {
    /**
     * Слияние заранее сортированных коллекций в новую сортированную коллекцию
     */
    public static <E> Stream<E> merge(Stream<E> first, Stream<E> second, Comparator<E> comp) {
        Stream.Builder<E> builder = Stream.builder();
        Optional<E> h1 = first.findFirst();
        Optional<E> h2 = second.findFirst();

        while (h1.isPresent() && h2.isPresent()) {
            if (comp.compare(h1.get(), h2.get()) >= 0) {
                builder.accept(h1.get());
                first = first.skip(1);
                h1 = first.findFirst();
            } else {
                builder.accept(h2.get());
                second = second.skip(1);
                h2 = second.findFirst();
            }
        }
        first.forEach(builder::add);
        second.forEach(builder::add);
        return builder.build();
    }
}
