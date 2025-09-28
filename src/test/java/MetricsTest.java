import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class MetricsTest {
    @Test
    void testReset() {
        Metrics m = new Metrics();
        m.comparisons = 5;
        m.recursionDepth = 3;
        m.allocations = 2;
        m.reset();
        assertEquals(0, m.comparisons);
        assertEquals(0, m.recursionDepth);
        assertEquals(0, m.allocations);
    }
}
