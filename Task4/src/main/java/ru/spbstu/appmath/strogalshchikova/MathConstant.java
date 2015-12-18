package ru.spbstu.appmath.strogalshchikova;

public enum MathConstant {
    PI(Math.PI),
    E(Math.E);

    private final double value;

    MathConstant(final double value) {
        this.value = value;
    }

    public double getValue() {
        return value;
    }
}
