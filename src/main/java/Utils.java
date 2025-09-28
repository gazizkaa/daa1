import java.util.Random;

public class Utils {

    private static final Random rand = new Random();

    /** Swap elements in an array and count allocation for metrics */
    public static void swap(int[] arr, int i, int j, Metrics metrics) {
        if (i != j) {
            int temp = arr[i];
            arr[i] = arr[j];
            arr[j] = temp;
            metrics.allocations++;
        }
    }

    /** Partition for QuickSort / Select */
    public static int partition(int[] arr, int left, int right, int pivotIndex, Metrics metrics) {
        int pivotValue = arr[pivotIndex];
        swap(arr, pivotIndex, right, metrics);
        int storeIndex = left;
        for (int i = left; i < right; i++) {
            metrics.comparisons++;
            if (arr[i] <= pivotValue) {
                swap(arr, storeIndex, i, metrics);
                storeIndex++;
            }
        }
        swap(arr, storeIndex, right, metrics);
        return storeIndex;
    }

    /** Fisher-Yates shuffle an array */
    public static void shuffle(int[] arr, Metrics metrics) {
        for (int i = arr.length - 1; i > 0; i--) {
            int j = rand.nextInt(i + 1);
            swap(arr, i, j, metrics);
        }
    }

    /** Guard: check array bounds */
    public static void checkBounds(int[] arr, int index) {
        if (index < 0 || index >= arr.length) {
            throw new IndexOutOfBoundsException("Index " + index + " out of bounds for array of length " + arr.length);
        }
    }
}
