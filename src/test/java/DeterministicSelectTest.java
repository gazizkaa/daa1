import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.Arrays;
import java.util.Random;

class DeterministicSelectTest {

    @Test
    void testRandomArray() {
        Random rand = new Random();
        int[] arr = rand.ints(1000, 0, 10000).toArray();
        int k = 500;
        int[] copy = Arrays.copyOf(arr, arr.length);
        Arrays.sort(copy);

        Metrics metrics = new Metrics();
        int result = DeterministicSelect.select(arr, k, metrics);

        assertEquals(copy[k], result);
        System.out.println("Recursion depth: " + metrics.recursionDepth);
        System.out.println("Comparisons: " + metrics.comparisons);
        System.out.println("Allocations: " + metrics.allocations);
    }
}
