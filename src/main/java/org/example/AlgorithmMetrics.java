package org.example;

public class AlgorithmMetrics {
    public long timeNs;
    public int maxRecursionDepth;
    public int comparisons;
    public int allocations;
    public int recursiveCalls;

    public void reset() {
        timeNs = 0;
        maxRecursionDepth = 0;
        comparisons = 0;
        allocations = 0;
        recursiveCalls = 0;
    }
}