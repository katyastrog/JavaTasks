/**
 * Ekaterina Strog.
 * Oracle Labs. 2015
 * Senior Developer
 */

public class Sort {
    static void selectionSort(double[] arr) {

        for (int i = 0; i < arr.length - 1; ++i) {
            int indexOfMinElem = i;
            for (int j = i + 1; j < arr.length; ++j) {
                if (arr[j] < arr[indexOfMinElem]) {
                    indexOfMinElem = j;
                }
            }
            if (indexOfMinElem != i) {
                double tmp = arr[i];
                arr[i] = arr[indexOfMinElem];
                arr[indexOfMinElem] = tmp;
            }
        }
    }

    public static void main(String[] args) {
        double[] arr = new double[args.length];

        for (int i = 0; i < args.length; ++i) {
            arr[i] = Double.parseDouble(args[i]);
        }

        selectionSort(arr);

        for (int i = 0; i < args.length; ++i) {
            System.out.print(arr[i] + " ");
        }
    }
}
