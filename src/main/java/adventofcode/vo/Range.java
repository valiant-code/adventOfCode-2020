package adventofcode.vo;

public class Range {
    private int min;
    private int max;

    public Range() {
    }

    public Range(int min, int max) {
        this.min = min;
        this.max = max;
    }

    public boolean isInRange(int num) {
        return num >= min && num <= max;
    }

    public int getMin() {
        return min;
    }

    public void setMin(int min) {
        this.min = min;
    }

    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Range that = (Range) o;

        if (min != that.min) return false;
        return max == that.max;
    }

    @Override
    public int hashCode() {
        int result = min;
        result = 31 * result + max;
        return result;
    }
}
