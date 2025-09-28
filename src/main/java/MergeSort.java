public class MergeSort {

    private static final int CUTOFF = 16; // small-n cutoff

    public static void sort(int[] arr, Metrics metrics) {
        int[] buffer = new int[arr.length];
        mergeSort(arr, buffer, 0, arr.length - 1, 0, metrics);
    }

    private static void mergeSort(int[] arr, int[] buffer, int left, int right, int depth, Metrics metrics) {
        metrics.recursionDepth = Math.max(metrics.recursionDepth, depth);

        if (right - left + 1 <= CUTOFF) {
            insertionSort(arr, left, right, metrics);
            return;
        }

        int mid = left + (right - left) / 2;
        mergeSort(arr, buffer, left, mid, depth + 1, metrics);
        mergeSort(arr, buffer, mid + 1, right, depth + 1, metrics);

        merge(arr, buffer, left, mid, right, metrics);
    }

    private static void insertionSort(int[] arr, int left, int right, Metrics metrics) {
        for (int i = left + 1; i <= right; i++) {
            int key = arr[i];
            metrics.allocations++;
            int j = i - 1;
            // Shift elements using Utils.swap
            while (j >= left && (++metrics.comparisons > 0) && arr[j] > key) {
                Utils.swap(arr, j + 1, j, metrics);
                j--;
            }
            arr[j + 1] = key;
        }
    }

    private static void merge(int[] arr, int[] buffer, int left, int mid, int right, Metrics metrics) {
        System.arraycopy(arr, left, buffer, left, right - left + 1);
        metrics.allocations += (right - left + 1);

        int i = left;
        int j = mid + 1;
        for (int k = left; k <= right; k++) {
            if (i > mid) {
                arr[k] = buffer[j++];
            } else if (j > right) {
                arr[k] = buffer[i++];
            } else if (++metrics.comparisons > 0 && buffer[i] <= buffer[j]) {
                arr[k] = buffer[i++];
            } else {
                arr[k] = buffer[j++];
            }
        }
    }
}
