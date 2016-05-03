package com.github.vchimishuk.newground.model;

import java.util.Objects;

import org.springframework.core.style.ToStringCreator;

public class Range {
    private final int start;

    private final int end;

    public Range(int start, int end) {
        this.start = start;
        this.end = end;
    }

    public int getStart() {
        return start;
    }

    public int getEnd() {
        return end;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Range range = (Range) o;

        return Objects.equals(start, range.start)
                && Objects.equals(end, range.end);
    }

    @Override
    public int hashCode() {
        return Objects.hash(start, end);
    }

    @Override
    public String toString() {
        return new ToStringCreator(this)
                .append("start", start)
                .append("end", end)
                .toString();
    }
}
