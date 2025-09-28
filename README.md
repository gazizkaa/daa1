# DAA1: Algorithms and Data Analysis Project

## Implemented Algorithms
- **MergeSort** – classic divide-and-conquer with reusable buffer and small-n cut-off
- **QuickSort** – randomized pivot, recurse on smaller partition first
- **Deterministic Select (Median of Medians)** – O(n) selection algorithm, in-place
- **Closest Pair of Points** – 2D divide-and-conquer, strip-check by y-order

## Project Structure
- `src/main/java/org/example` – main implementation
- `src/test/java/org/example` – JUnit tests
- `pom.xml` – Maven configuration
- `*.csv` – benchmark data
- `*.png` – performance plots

## Architecture Notes
- **MergeSort**: linear merge with reusable buffer; small-n cutoff via insertion sort reduces recursion depth.
- **QuickSort**: always recurses on the smaller partition first; bounds stack depth to ~O(log n).
- **Deterministic Select**: median-of-medians groups by 5; recurses only on the needed side to control depth.
- **Closest Pair**: divides points recursively by x-coordinate; uses strip neighbors by y-order; depth grows O(log n).

## Recurrence Analysis
- **MergeSort**: T(n) = 2T(n/2) + Θ(n) → Master Theorem Case 2 → Θ(n log n)
- **QuickSort**: Average T(n) = 2T(n/2) + Θ(n) → Θ(n log n), worst-case Θ(n²). Smaller-first recursion bounds stack depth.
- **Deterministic Select**: T(n) = T(n/5) + T(7n/10) + Θ(n) → Θ(n) deterministic. Only recurses on needed partition.
- **Closest Pair**: T(n) = 2T(n/2) + Θ(n) → Θ(n log n); strip scan adds minor constant factor; recursion depth ≈ log2 n.

## Benchmark Results

### Time vs Array Size
![Time vs n](time_vs_n.png)

### Recursion Depth vs Array Size
![Depth vs n](depth_vs_n.png)

## Discussion
- **MergeSort** and **QuickSort** match theoretical complexity: MergeSort is stable, QuickSort is faster on small arrays.
- **Deterministic Select** has predictable recursion depth but slightly higher time overhead for small n.
- **Closest Pair** recursion depth grows quickly due to full recursive splitting.
- Overall, measured times align with theory, with small differences due to memory allocations and caching effects.

## Summary
- Measured times mostly align with theoretical predictions (Θ(n log n) or Θ(n))
- Recursion depth is controlled as designed for each algorithm
- Constant factors (cache, memory allocation, GC) explain minor differences between theory and observed timings
