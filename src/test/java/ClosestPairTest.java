import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ClosestPairTest {

    @Test
    void testSmallSet() {
        ClosestPair.Point[] points = {
                new ClosestPair.Point(0,0),
                new ClosestPair.Point(1,1),
                new ClosestPair.Point(2,2)
        };
        Metrics metrics = new Metrics();
        double result = ClosestPair.closestPair(points, metrics);
        assertEquals(Math.sqrt(2), result, 1e-6);
    }
}
