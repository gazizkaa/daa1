import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class MainCLI {

    public static void main(String[] args) {
        if (args.length < 2) {
            System.out.println("Usage: java MainCLI <algorithm> <size>");
            System.out.println("Algorithms: mergesort, quicksort, select, closest");
            return;
        }

        String algorithm = args[0].toLowerCase();
        int n = Integer.parseInt(args[1]);

        Metrics metrics = new Metrics();
        Random rand = new Random();
        int[] arr = new int[n];
        for (int i = 0; i < n; i++) arr[i] = rand.nextInt(n*10);

        double closest = -1; // for closest pair

        long start = System.nanoTime();

        switch (algorithm) {
            case "mergesort":
                MergeSort.sort(arr, metrics);
                break;
            case "quicksort":
                QuickSort.sort(arr, metrics);
                break;
            case "select":
                int k = n / 2; // median
                DeterministicSelect.select(arr, k, metrics);
                break;
            case "closest":
                ClosestPair.Point[] points = new ClosestPair.Point[n];
                for (int i = 0; i < n; i++) points[i] = new ClosestPair.Point(rand.nextDouble()*1000, rand.nextDouble()*1000);
                closest = ClosestPair.closestPair(points, metrics);
                break;
            default:
                System.out.println("Unknown algorithm: " + algorithm);
                return;
        }

        long end = System.nanoTime();
        double timeMs = (end - start) / 1_000_000.0;

        System.out.println("Algorithm: " + algorithm);
        System.out.println("N: " + n);
        System.out.println("Time (ms): " + timeMs);
        System.out.println("Comparisons: " + metrics.comparisons);
        System.out.println("Allocations/Swaps: " + metrics.allocations);
        System.out.println("Recursion Depth: " + metrics.recursionDepth);
        if (algorithm.equals("closest")) System.out.println("Closest Distance: " + closest);

        // Write CSV
        try (FileWriter fw = new FileWriter(algorithm + "_metrics.csv", true)) {
            fw.write(n + "," + timeMs + "," + metrics.comparisons + "," + metrics.allocations + "," + metrics.recursionDepth + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
