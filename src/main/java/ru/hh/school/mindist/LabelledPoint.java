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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof LabelledPoint)) return false;
        if (!super.equals(o)) return false;

        LabelledPoint<?> that = (LabelledPoint<?>) o;

        return !(label != null ? !label.equals(that.label) : that.label != null);

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (label != null ? label.hashCode() : 0);
        return result;
    }
}
