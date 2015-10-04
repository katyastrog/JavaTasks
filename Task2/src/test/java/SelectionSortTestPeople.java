
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
public class SelectionSortTestPeople {
    private static final SelectionSort SELECTION_SORT = new SelectionSort();
    private static final Object[][] TEST_DATA = {
            {SELECTION_SORT, new People[]{}},
            {SELECTION_SORT, new People[]{new People(12, "Kate"), new People(52, "Chris"), new People(3, "Elly")}},
            {SELECTION_SORT, new People[]{new People(1, "Alex"), new People(2, "Gleb"), new People(3, "Serg")}},
            {SELECTION_SORT, new People[]{new People(2, "Ann"), new People(78, "Ark"), new People(75, "Roman"), new People(78, "Vlad")}},
            {SELECTION_SORT, new People[]{new People(45, "Leon"), new People(32, "Jack"), new People(8, "Tati")}},
    };


    private static final Comparator<People> PEOPLE_COMPARATOR = new PeopleComparator();

    @Parameterized.Parameters
    public static Collection<Object[]> testData() {
        return Arrays.asList(TEST_DATA);
    }

    private Sort<People> sort;
    private People[] input;

    public SelectionSortTestPeople(Sort<People> sort, People[] input) {
        this.sort = sort;
        this.input = input;
    }

    @Test
    public void testDoubleArraySorting() {
        People[] result = sort.sort(input, PEOPLE_COMPARATOR);
        Assert.assertTrue(testAscendingOrder(result, PEOPLE_COMPARATOR));
        Assert.assertEquals("Result array length should be equal to original", input.length, result.length);
        Assert.assertTrue(hasEachElementOf(input, result, PEOPLE_COMPARATOR));
    }

    private static boolean testAscendingOrder(People[] array, Comparator<People> comparator) {
        for (int i = 0; i < array.length - 1; i++) {
            if (comparator.compare(array[i], array[i + 1]) > 0)
                return false;
        }
        return true;
    }

    private static boolean hasEachElementOf(People[] input, People[] result, Comparator<People> comparator) {
        for (People element : input) {
            for (int j = 0; j < result.length; j++) {
                if (comparator.compare(result[j], element) == 0)
                    break;
                if (j == result.length - 1)
                    return false;
            }
        }
        return true;
    }

}