import java.util.Random;

public class QuickSort {

    private static final Random rand = new Random();

    public static void sort(int[] arr, Metrics metrics) {
        quickSort(arr, 0, arr.length - 1, 0, metrics);
    }

    private static void quickSort(int[] arr, int left, int right, int depth, Metrics metrics) {
        if (left >= right) return;

        metrics.recursionDepth = Math.max(metrics.recursionDepth, depth);

        // Randomized pivot
        int pivotIndex = left + rand.nextInt(right - left + 1);
        Utils.swap(arr, pivotIndex, right, metrics);

        // Partition using Utils
        int partitionIndex = Utils.partition(arr, left, right, right, metrics);

        // Recurse on smaller partition first
        if (partitionIndex - 1 - left < right - (partitionIndex + 1)) {
            quickSort(arr, left, partitionIndex - 1, depth + 1, metrics);
            quickSort(arr, partitionIndex + 1, right, depth, metrics); // tail recursion on larger
        } else {
            quickSort(arr, partitionIndex + 1, right, depth + 1, metrics);
            quickSort(arr, left, partitionIndex - 1, depth, metrics);
        }
    }
}
