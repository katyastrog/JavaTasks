package ru.spbstu.appmath.strogalshchikova;

import java.util.Comparator;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;
import java.util.Random;


@RunWith(Parameterized.class)
public class SelectionSortTest<T> {

    private static final Comparator<Human> HUMAN_NAME_COMPARATOR = new HumanNameComparator();
    private static final Comparator<Human> HUMAN_AGE_COMPARATOR = new HumanAgeComparator();
    private static final Comparator<Double> DOUBLE_DECREASE_COMPARATOR = new Comparator<Double>() {
        public int compare(final Double o1, final Double o2) {
            return (-1) * o1.compareTo(o2);
        }
    };
    private static final Comparator<Double> DOUBLE_INCREASE_COMPARATOR = new Comparator<Double>() {
        public int compare(final Double o1, final Double o2) {
            return o1.compareTo(o2);
        }
    };
    private static final SelectionSort SELECTION_SORT = new SelectionSort();

    private static Object[][] TEST_DATA = {
            {SELECTION_SORT, HUMAN_NAME_COMPARATOR, new Human[]{}},
            {SELECTION_SORT, HUMAN_AGE_COMPARATOR, new Human[]{}},
            {SELECTION_SORT, HUMAN_NAME_COMPARATOR, new Human[]{new Human(12, "Kate"), new Human(52, "Chris"), new Human(3, "Elly")}},
            {SELECTION_SORT, HUMAN_AGE_COMPARATOR, new Human[]{new Human(1, "Alex"), new Human(2, "Gleb"), new Human(3, "Serg")}},
            {SELECTION_SORT, HUMAN_NAME_COMPARATOR, new Human[]{new Human(2, "Ann"), new Human(78, "Ark"), new Human(75, "Roman"), new Human(78, "Vlad")}},
            {SELECTION_SORT, HUMAN_AGE_COMPARATOR, new Human[]{new Human(45, "Leon"), new Human(32, "Jack"), new Human(8, "Tati")}},
            {SELECTION_SORT, DOUBLE_DECREASE_COMPARATOR, getArrayDouble(5)},
            {SELECTION_SORT, DOUBLE_DECREASE_COMPARATOR, getArrayDouble(10)},
            {SELECTION_SORT, DOUBLE_DECREASE_COMPARATOR, getArrayDouble(0)},
            {SELECTION_SORT, DOUBLE_INCREASE_COMPARATOR, getArrayDouble(8)},
            {SELECTION_SORT, DOUBLE_INCREASE_COMPARATOR, getArrayDouble(2)},
            {SELECTION_SORT, DOUBLE_INCREASE_COMPARATOR, getArrayDouble(0)}
    };


    private static Double[] getArrayDouble(final int size) {

        Random rand = new Random();
        Double[] array = new Double[size];

        for (int i = 0; i < array.length; i++) {
            array[i] = Math.floor(rand.nextDouble() * 100.0) / 10.0 - 5.0;
        }

        return array;
    }


    @Parameterized.Parameters
    public static Collection<Object[]> testData() {
        return Arrays.asList(TEST_DATA);
    }


    private Sort<T> sort;
    private T[] input;
    private Comparator<T> comparator;

    public SelectionSortTest(Sort<T> sort, Comparator<T> comparator, T[] input) {
        this.sort = sort;
        this.comparator = comparator;
        this.input = input;
    }

    @Test
    public void testArraySorting() {

        T[] result = sort.sort(input, comparator);
        Assert.assertTrue("The array is not sorted", testAscendingOrder(result, comparator));
        Assert.assertEquals("Result array length should be equal to original", input.length, result.length);
        Assert.assertTrue("Arrays contain different number of the same elements", hasSameNumberOfIdenticalElements(input, result, comparator));

    }

    private boolean testAscendingOrder(T[] array, Comparator<T> comparator) {
        for (int i = 0; i < array.length - 1; i++) {
            if (comparator.compare(array[i], array[i + 1]) > 0)
                return false;
        }
        return true;
    }

    private boolean hasSameNumberOfIdenticalElements(T[] input, T[] result, Comparator<T> comparator) {
        T[] copyOfInput = input.clone();

        for (int i = 0; i < result.length; i++) {
            for (int j = 0; j < copyOfInput.length; j++) {
                if (copyOfInput[j] != null && comparator.compare(copyOfInput[j], result[i]) == 0) {
                    copyOfInput[j] = null;
                    break;
                }
            }
        }
        for (int j = 0; j < copyOfInput.length; j++) {
            if (copyOfInput[j] != null)
                return false;
        }
        return true;
    }
}


