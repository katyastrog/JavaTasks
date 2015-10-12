
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.awt.event.ComponentAdapter;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.Random;


@RunWith(Parameterized.class)
public class SelectionSortTest<T> {

    private static final SelectionSort SELECTION_SORT = new SelectionSort();
    private static Object[][] TEST_DATA_DOUBLE = initHumanDataTest();
    private static final Object[][] TEST_DATA_PEOPLE = {
            {SELECTION_SORT, new Human[]{}},
            {SELECTION_SORT, new Human[]{new Human(12, "Kate"), new Human(52, "Chris"), new Human(3, "Elly")}},
            {SELECTION_SORT, new Human[]{new Human(1, "Alex"), new Human(2, "Gleb"), new Human(3, "Serg")}},
            {SELECTION_SORT, new Human[]{new Human(2, "Ann"), new Human(78, "Ark"), new Human(75, "Roman"), new Human(78, "Vlad")}},
            {SELECTION_SORT, new Human[]{new Human(45, "Leon"), new Human(32, "Jack"), new Human(8, "Tati")}},
    };

    private static final Comparator<Double> DOUBLE_COMPARATOR = new Comparator<Double>() {
        public int compare(final Double o1, final Double o2) {
            return o1.compareTo(o2);
        }
    };
    private static final Comparator<Human> HUMAN_COMPARATOR = new HumanComparator();

    private static Object[][] initHumanDataTest() {
        int arraySize = 5;
        Object[][] data = new Object[arraySize][2];

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
            data[i][1] = array;
        }

        return data;
    }


    @Parameterized.Parameters
    public static Collection<Object[]> testData() {
        return Arrays.asList(TEST_DATA_PEOPLE);
//        return  Arrays.asList(TEST_DATA_DOUBLE);
    }


    private Sort<T> sort;
    private T[] input;

    public SelectionSortTest(Sort<T> sort, T[] input) {
        this.sort = sort;
        this.input = input;
    }

    @Test
    public void testArraySorting() {

        Object comparator = null;

        if (input instanceof Human[]) {
            comparator = HUMAN_COMPARATOR;
        } else if (input instanceof Double[]) {
            comparator = DOUBLE_COMPARATOR;
        }

        T[] result = sort.sort(input, (Comparator<T>) comparator);

        Assert.assertTrue("The array is not sorted", testAscendingOrder(result, (Comparator<T>) comparator));
        Assert.assertEquals("Result array length should be equal to original", input.length, result.length);
        Assert.assertTrue("Arrays consist of different elements", hasEachElementOf(input, result));
        Assert.assertTrue("Arrays contain different number of the same elements", hasSameNumberOfIdenticalElements(input, result, (Comparator<T>) comparator));
    }

    private boolean testAscendingOrder(T[] array, Comparator<T> comparator) {
        for (int i = 0; i < array.length - 1; i++) {
            if (comparator.compare(array[i], array[i + 1]) > 0)
                return false;
        }
        return true;
    }

    private boolean hasEachElementOf(T[] input, T[] result) {
        for (T element : input) {
            for (int j = 0; j < result.length; j++) {
                if (result[j] == element)
                    break;
                if (j == result.length - 1)
                    return false;
            }
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


