package ru.spbstu.appmath.strogalshchikova;

import java.util.Arrays;
public final class ArrayStack<T> extends ru.spbstu.appmath.strogalshchikova.AbstractStack<T> {

    private static final int DEFAULT_CAPACITY = 5;

    private T[] elements;

    public ArrayStack() {
        this(DEFAULT_CAPACITY);
    }

    public ArrayStack(final int capacity) {
        // noinspection unchecked
        elements = (T[]) new Object[capacity];
    }

    protected void pushInternal(final T element) {
        checkCapacity(size() + 1);
        elements[size()] = element;
    }

    private void checkCapacity(final int requiredCapacity) {
        if (requiredCapacity > elements.length) {
            elements = Arrays.copyOf(elements, requiredCapacity * 2);
        }
    }

    protected T popInternal() {
        T result = elements[size()];
        elements[size()] = null;
        return result;
    }

    protected T topInternal() {
        return elements[size() - 1];
    }


}
