import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class BenchmarkRunner {

    public static void main(String[] args) {
        int[] sizes = {100, 500, 1000, 5000, 10000, 20000};

        runBenchmark("mergesort", sizes);
        runBenchmark("quicksort", sizes);
        runBenchmark("select", sizes);
        runBenchmark("closest", new int[]{100, 500, 1000, 2000, 5000}); // smaller sizes
    }

    private static void runBenchmark(String algorithm, int[] sizes) {
        System.out.println("Running benchmarks for: " + algorithm);

        try (FileWriter fw = new FileWriter(algorithm + "_bench.csv")) {
            fw.write("n,timeMs,comparisons,allocations,depth\n");

            for (int n : sizes) {
                Metrics metrics = new Metrics();
                Random rand = new Random();

                int[] arr = new int[n];
                for (int i = 0; i < n; i++) arr[i] = rand.nextInt(n * 10);

                long start = System.nanoTime();
                double closest = -1;

                switch (algorithm) {
                    case "mergesort":
                        MergeSort.sort(arr, metrics);
                        break;
                    case "quicksort":
                        QuickSort.sort(arr, metrics);
                        break;
                    case "select":
                        int k = n / 2;
                        DeterministicSelect.select(arr, k, metrics);
                        break;
                    case "closest":
                        ClosestPair.Point[] points = new ClosestPair.Point[n];
                        for (int i = 0; i < n; i++) {
                            points[i] = new ClosestPair.Point(rand.nextDouble() * 1000, rand.nextDouble() * 1000);
                        }
                        closest = ClosestPair.closestPair(points, metrics);
                        break;
                }

                long end = System.nanoTime();
                double timeMs = (end - start) / 1_000_000.0;

                fw.write(n + "," + timeMs + "," + metrics.comparisons + "," + metrics.allocations + "," + metrics.recursionDepth + "\n");

                System.out.println("n=" + n + " done (" + timeMs + " ms)");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
