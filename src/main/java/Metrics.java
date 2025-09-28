public class Metrics {
    public int comparisons = 0;
    public int allocations = 0;
    public int recursionDepth = 0;

    public void reset() {
        comparisons = 0;
        allocations = 0;
        recursionDepth = 0;
    }
}
