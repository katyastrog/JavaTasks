package ru.spbstu.appmath.strogalshchikova;

public abstract class AbstractStack<T> implements Stack<T> {
    private int size;

    @Override
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

    @Override
    public T top() {
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
