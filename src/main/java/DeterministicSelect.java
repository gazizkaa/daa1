import java.util.Arrays;

public class DeterministicSelect {

    // Public API: select kth smallest (0-based) value from arr, recording metrics
    public static int select(int[] arr, int k, Metrics metrics) {
        return select(arr, 0, arr.length - 1, k, metrics, 1);
    }

    // Internal select that tracks recursion depth
    private static int select(int[] arr, int left, int right, int k, Metrics metrics, int depth) {
        metrics.recursionDepth = Math.max(metrics.recursionDepth, depth);

        while (true) {
            if (left == right) return arr[left];

            // pivotIndex
            int pivotIndex = medianOfMedians(arr, left, right, metrics, depth + 1);
            pivotIndex = partition(arr, left, right, pivotIndex, metrics);

            if (k == pivotIndex) return arr[k];
            else if (k < pivotIndex) right = pivotIndex - 1;
            else left = pivotIndex + 1;

            depth++;
            metrics.recursionDepth = Math.max(metrics.recursionDepth, depth);
        }
    }

    //  returns final storeIndex
    private static int partition(int[] arr, int left, int right, int pivotIndex, Metrics metrics) {
        int pivotValue = arr[pivotIndex];
        swap(arr, pivotIndex, right, metrics); // move pivot to end
        int storeIndex = left;
        for (int i = left; i < right; i++) {
            metrics.comparisons++;
            if (arr[i] < pivotValue) {
                swap(arr, storeIndex, i, metrics);
                storeIndex++;
            }
        }
        swap(arr, storeIndex, right, metrics); // move pivot to final place
        return storeIndex;
    }


    private static void swap(int[] arr, int i, int j, Metrics metrics) {
        if (i != j) {
            int tmp = arr[i];
            arr[i] = arr[j];
            arr[j] = tmp;
            metrics.allocations++;
        }
    }


    private static int medianOfMedians(int[] arr, int left, int right, Metrics metrics, int depth) {
        int n = right - left + 1;
        if (n <= 5) {
            Arrays.sort(arr, left, right + 1);
            metrics.allocations += n;
            return left + n / 2; // return index of median in this small block
        }

        int numMedians = 0;

        for (int i = left; i <= right; i += 5) {
            int subRight = Math.min(i + 4, right);
            Arrays.sort(arr, i, subRight + 1);
            metrics.allocations += (subRight - i + 1);
            int medianIndex = i + (subRight - i) / 2; // median index of this group
            swap(arr, left + numMedians, medianIndex, metrics); // move median to front
            numMedians++;
        }


        int medianOfMediansValue = select(arr, left, left + numMedians - 1, left + numMedians / 2, metrics, depth + 1);

        // Find the index of that median value within the medians subarray and return it
        for (int i = left; i <= left + numMedians - 1; i++) {
            if (arr[i] == medianOfMediansValue) {
                return i;
            }
        }


        return left;
    }
}

