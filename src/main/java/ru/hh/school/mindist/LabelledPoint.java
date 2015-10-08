/**
 * Author: Oleg Nizhnik
 * Date  : 08.10.2015
 * Time  : 13:37
 */
package ru.hh.school.mindist;

public class LabelledPoint<L> extends Point {
    final L label;

    public LabelledPoint(double x, double y, L label) {
        super(x, y);
        this.label = label;
    }

    @Override
    public String toString() {
        return String.format("[%s](%s,%s)", label, x, y);
    }
}
