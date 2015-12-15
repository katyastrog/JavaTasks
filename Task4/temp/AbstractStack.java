package ru.spbstu.appmath.strogalshchikova;

public abstract class AbstractStack<T> {
    private int size;

    public void push(T element) {
        pushInternal(element);
        size++;
    }

    protected abstract void pushInternal(T element);

    public T pop() {
        if (size == 0) {
            return null;
        }
        size--;
        return popInternal();
    }

    protected abstract T popInternal();

    public T peek() {
        if (size == 0) {
            return null;
        }
        return topInternal();
    }

    protected abstract T topInternal();

    public final int size() {
        return size;
    }

    public final boolean isEmpty() {
        return size() == 0;
    }
}
