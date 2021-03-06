
/**
 * Created by ��������� on 26.09.2015.
 */
package ru.spbstu.appmath.strogalshchikova;

import java.util.Arrays;
import java.util.Comparator;


public class SelectionSort<T> implements Sort<T> {
    public T[] sort(T[] array, Comparator<T> comparator) {
        T[] result = Arrays.copyOf(array, array.length);
        selectionSort(result, comparator);
        return result;
    }

    private static <T> void selectionSort(T[] array, Comparator<T> comparator) {
        int i, j, indexOfMinElem;
        T tmp;

        for (i = 0; i < array.length - 1; i++) {
            indexOfMinElem = i;
            for (j = i + 1; j < array.length; j++) {
                if (comparator.compare(array[j], array[indexOfMinElem]) < 0) {
                    indexOfMinElem = j;
                }
            }
            if (indexOfMinElem != i) {
                tmp = array[i];
                array[i] = array[indexOfMinElem];
                array[indexOfMinElem] = tmp;
            }
        }
    }
}

