
/**
 * Created by Екатерина on 03.10.2015.
 */

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;


@RunWith(Parameterized.class)
public class SelectionSortTestDouble {

    private static final SelectionSort SELECTION_SORT = new SelectionSort();
    private static final Object[][] TEST_DATA = {
            {SELECTION_SORT, new Double[]{}},
            {SELECTION_SORT, new Double[]{1.0, 2.0, 3.0}},
            {SELECTION_SORT, new Double[]{3.0, 2.0, 1.0}},
            {SELECTION_SORT, new Double[]{1., 1., 1.}},
            {SELECTION_SORT, new Double[]{-131., 2524., 23., -352.5}},
    };


    private static final Comparator<Double> DOUBLE_COMPARATOR = new Comparator<Double>() {
        public int compare(final Double o1, final Double o2) {
            return o1.compareTo(o2);
        }
    };

    @Parameterized.Parameters
    public static Collection<Object[]> testData() {
        return Arrays.asList(TEST_DATA);
    }

    private Sort<Double> sort;
    private Double[] input;

    public SelectionSortTestDouble(Sort<Double> sort, Double[] input) {
        this.sort = sort;
        this.input = input;
    }

    @Test
    public void testDoubleArraySorting() {
        Double[] result = sort.sort(input, DOUBLE_COMPARATOR);
        Assert.assertTrue(testAscendingOrder(result));
        Assert.assertEquals("Result array length should be equal to original", input.length, result.length);
        Assert.assertTrue(hasEachElementOf(input, result));
    }

    private static boolean testAscendingOrder(Double[] array) {
        for (int i = 0; i < array.length - 1; i++) {
            if (array[i] > array[i + 1])
                return false;
        }
        return true;
    }

    private static boolean hasEachElementOf(Double[] input, Double[] result) {
        for (double element : input) {
            for (int j = 0; j < result.length; j++) {
                if (result[j] == element)
                    break;
                if (j == result.length - 1)
                    return false;
            }
        }
        return true;
    }
}


