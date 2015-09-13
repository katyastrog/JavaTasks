/**
 * Ekaterina Strog.
 * Oracle Labs. 2015
 * Senior Developer
 */

public class Sort {
    static void SelectionSort (double [] arr) {
        int i, j, indexOfMinElem;
        double tmp;

        for (i = 0; i < arr.length - 1; ++i) {
            indexOfMinElem = i;
            for (j = i + 1; j < arr.length; ++j) {
                if (arr[j] < arr[indexOfMinElem]) {
                    indexOfMinElem = j;
                }
            }
            if (indexOfMinElem != i) {
                tmp = arr[i];
                arr[i] = arr[indexOfMinElem];
                arr[indexOfMinElem] = tmp;
            }
        }
    }
    public static void main(String[] args) {
        int i;
        double [] arr = new double [args.length];

        for (i = 0; i < args.length; ++i) {
            arr[i] = Double.parseDouble(args[i]);
        }

        SelectionSort(arr);

        for (i = 0; i < args.length; ++i) {
            System.out.print(arr[i] + " ");
        }
    }
}
