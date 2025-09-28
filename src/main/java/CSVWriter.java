import java.io.FileWriter;
import java.io.IOException;

public class CSVWriter {
    private FileWriter writer;

    public CSVWriter(String filename) throws IOException {
        writer = new FileWriter(filename);
        writer.write("n,time_ms,depth,comparisons,allocations\n");
    }

    public void writeLine(int n, long time, int depth, int comparisons, int allocations) throws IOException {
        writer.write(n + "," + time + "," + depth + "," + comparisons + "," + allocations + "\n");
    }

    public void close() throws IOException {
        writer.close();
    }
}

