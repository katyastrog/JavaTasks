package ru.spbstu.appmath.strogalshchikova;

import java.util.Comparator;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import ru.spbstu.appmath.strogalshchikova.Human;
import ru.spbstu.appmath.strogalshchikova.HumanNameComparator;
import ru.spbstu.appmath.strogalshchikova.SelectionSort;
import ru.spbstu.appmath.strogalshchikova.Sort;

import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.Random;


@RunWith(Parameterized.class)
public class SelectionSortTest<T> {

    private static final Comparator<Human> HUMAN_NAME_COMPARATOR = new HumanNameComparator();
    private static final Comparator<Human> HUMAN_AGE_COMPARATOR = new HumanAgeComparator();
    private static final Comparator<Double> DOUBLE_DECREASE_COMPARATOR = new Comparator<Double>() {
        public int compare(final Double o1, final Double o2) {
            return (-1)*o1.compareTo(o2);
        }
    };
    private static final Comparator<Double> DOUBLE_INCREASE_COMPARATOR = new Comparator<Double>() {
        public int compare(final Double o1, final Double o2) {
            return o1.compareTo(o2);
        }
    };
    private static final SelectionSort SELECTION_SORT = new SelectionSort();

    private static Object[][] TEST_DATA_DOUBLE = initHumanDataTest();
    private static final Object[][] TEST_DATA_PEOPLE = {
            {SELECTION_SORT, HUMAN_NAME_COMPARATOR, new Human[]{}},
            {SELECTION_SORT, HUMAN_AGE_COMPARATOR, new Human[]{}},
            {SELECTION_SORT, HUMAN_NAME_COMPARATOR, new Human[]{new Human(12, "Kate"), new Human(52, "Chris"), new Human(3, "Elly")}},
            {SELECTION_SORT, HUMAN_AGE_COMPARATOR, new Human[]{new Human(1, "Alex"), new Human(2, "Gleb"), new Human(3, "Serg")}},
            {SELECTION_SORT, HUMAN_NAME_COMPARATOR, new Human[]{new Human(2, "Ann"), new Human(78, "Ark"), new Human(75, "Roman"), new Human(78, "Vlad")}},
            {SELECTION_SORT, HUMAN_AGE_COMPARATOR, new Human[]{new Human(45, "Leon"), new Human(32, "Jack"), new Human(8, "Tati")}},
    };

    private static Object[][] initHumanDataTest() {
        int arraySize = 5;
        Object[][] data = new Object[arraySize][3];

        Random rand = new Random();
        int numOfElem;
        Double[] array;
        for (int i = 0; i < arraySize; i++) {
            numOfElem = rand.nextInt(20);
            array = new Double[numOfElem];

            for (int j = 0; j < array.length; j++) {
                array[j] = Math.floor(rand.nextDouble() * 100.0) / 10.0 - 5.0;
            }

            data[i][0] = SELECTION_SORT;
            if(i % 2 == 0)
                data[i][1] = DOUBLE_DECREASE_COMPARATOR;
            else
                data[i][1] = DOUBLE_INCREASE_COMPARATOR;
            data[i][2] = array;
        }

        return data;
    }


    @Parameterized.Parameters
    public static Collection<Object[]> testData() {
       // return Arrays.asList(TEST_DATA_PEOPLE);
      return  Arrays.asList(TEST_DATA_DOUBLE);
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


