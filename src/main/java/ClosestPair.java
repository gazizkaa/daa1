import java.util.Arrays;

public class ClosestPair {

    public static class Point {
        public final double x, y;
        public Point(double x, double y) { this.x = x; this.y = y; }
    }

    public static double closestPair(Point[] points, Metrics metrics) {
        Point[] Px = Arrays.copyOf(points, points.length);
        Point[] Py = Arrays.copyOf(points, points.length);

        Arrays.sort(Px, (a, b) -> Double.compare(a.x, b.x));
        Arrays.sort(Py, (a, b) -> Double.compare(a.y, b.y));

        return closestPairRec(Px, Py, 0, points.length - 1, metrics);
    }

    private static double closestPairRec(Point[] Px, Point[] Py, int left, int right, Metrics metrics) {
        metrics.recursionDepth = Math.max(metrics.recursionDepth, metrics.recursionDepth + 1);

        int n = right - left + 1;
        if (n <= 3) return bruteForce(Px, left, right, metrics);

        int mid = left + (right - left) / 2;
        Point midPoint = Px[mid];

        // Divide Py into two halves
        Point[] PyLeft = Arrays.stream(Py).filter(p -> p.x <= midPoint.x).toArray(Point[]::new);
        Point[] PyRight = Arrays.stream(Py).filter(p -> p.x > midPoint.x).toArray(Point[]::new);

        double dl = closestPairRec(Px, PyLeft, left, mid, metrics);
        double dr = closestPairRec(Px, PyRight, mid + 1, right, metrics);

        double d = Math.min(dl, dr);

        // Check the strip around midPoint
        Point[] strip = Arrays.stream(Py).filter(p -> Math.abs(p.x - midPoint.x) < d).toArray(Point[]::new);

        double stripDist = stripClosest(strip, d, metrics);
        return Math.min(d, stripDist);
    }

    private static double bruteForce(Point[] points, int left, int right, Metrics metrics) {
        double min = Double.POSITIVE_INFINITY;
        for (int i = left; i <= right; i++) {
            for (int j = i + 1; j <= right; j++) {
                metrics.comparisons++;
                min = Math.min(min, distance(points[i], points[j]));
            }
        }
        return min;
    }

    private static double stripClosest(Point[] strip, double d, Metrics metrics) {
        double min = d;
        for (int i = 0; i < strip.length; i++) {
            for (int j = i + 1; j < strip.length && (strip[j].y - strip[i].y) < min; j++) {
                metrics.comparisons++;
                min = Math.min(min, distance(strip[i], strip[j]));
            }
        }
        return min;
    }

    private static double distance(Point p1, Point p2) {
        double dx = p1.x - p2.x;
        double dy = p1.y - p2.y;
        return Math.sqrt(dx*dx + dy*dy);
    }
}
