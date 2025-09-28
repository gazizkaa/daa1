import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.Arrays;
import java.util.Random;

class MergeSortTest {

    @Test
    void testRandomArray() {
        Random rand = new Random();
        int[] arr = rand.ints(1000, 0, 10000).toArray();
        int[] copy = Arrays.copyOf(arr, arr.length);
        Metrics metrics = new Metrics();

        MergeSort.sort(arr, metrics);
        Arrays.sort(copy);

        assertArrayEquals(copy, arr);
        System.out.println("Recursion depth: " + metrics.recursionDepth);
        System.out.println("Comparisons: " + metrics.comparisons);
        System.out.println("Allocations: " + metrics.allocations);
    }
}

