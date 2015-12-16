package ru.spbstu.appmath.strogalshchikova;

public enum Constant {
    PI (Math.PI),
    E (Math.E);

    private final double value;

    Constant(final double value) {
        this.value = value;
    }
}
