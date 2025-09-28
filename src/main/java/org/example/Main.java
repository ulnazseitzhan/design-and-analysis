package org.example;

import java.io.*;
import java.util.*;


public class Main {
    public static void main(String[] args) {
        if (args.length == 0) {
            runDemo();
            return;
        }

        if (args[0].equals("benchmark")) {
            runBenchmark();
        } else if (args[0].equals("test")) {
            runTests();
        } else if (args[0].equals("csv")) {
            generateCSV();
        } else {
            System.out.println("Usage: java Main [benchmark|test|csv]");
        }
    }

    public static void runDemo() {
        System.out.println("=== Divide & Conquer Algorithms Demo ===\n");

        // Test with small arrays for demo
        testMergeSort();
        testQuickSort();
        testDeterministicSelect();
        testClosestPairDemo();
        System.out.println("Generating CSV automatically...");
        generateCSV();
    }

    public static void testMergeSort() {
        System.out.println("1. MergeSort:");
        int[] arr = ArrayUtils.generateRandomArray(20);
        System.out.println("Original: " + Arrays.toString(arr));
        MergeSort ms = new MergeSort();
        ms.sort(arr);
        System.out.println("Sorted:   " + Arrays.toString(arr));
        System.out.println("Metrics: " + formatMetrics(ms.getMetrics()));
        System.out.println();
    }

    public static void testQuickSort() {
        System.out.println("2. QuickSort:");
        int[] arr = ArrayUtils.generateRandomArray(20);
        System.out.println("Original: " + Arrays.toString(arr));
        QuickSort qs = new QuickSort();
        qs.sort(arr);
        System.out.println("Sorted:   " + Arrays.toString(arr));
        System.out.println("Metrics: " + formatMetrics(qs.getMetrics()));
        System.out.println();
    }

    public static void testDeterministicSelect() {
        System.out.println("3. Deterministic Select:");
        int[] arr = ArrayUtils.generateRandomArray(20);
        int k = 10;
        System.out.println("Array: " + Arrays.toString(arr));
        DeterministicSelect ds = new DeterministicSelect();
        int result = ds.select(arr.clone(), k);

        // Verify with sorting
        int[] sorted = arr.clone();
        Arrays.sort(sorted);
        System.out.println(k + "-th smallest element: " + result);
        System.out.println("Verified with Arrays.sort: " + (result == sorted[k] ? "PASS" : "FAIL"));
        System.out.println("Metrics: " + formatMetrics(ds.getMetrics()));
        System.out.println();
    }

    public static void testClosestPairDemo() {
        System.out.println("4. Closest Pair:");
        ClosestPair cp = new ClosestPair();
        ClosestPair.Point[] points = generateRandomPoints(10);
        System.out.println("Points sample: (" + points[0].x + "," + points[0].y + ") ... (" + points[points.length-1].x + "," + points[points.length-1].y + ")");
        double distance = cp.findClosestPair(points);
        System.out.println("Closest pair distance: " + distance);
        System.out.println("Metrics: " + formatMetrics(cp.getMetrics()));
        System.out.println();
    }

    public static void runBenchmark() {
        System.out.println("Running comprehensive benchmarks...");

        int[] sizes = {100, 1000, 5000, 10000};

        System.out.println("\nSize\tMergeSort(ms)\tQuickSort(ms)\tSelect(ms)\tClosestPair(ms)");
        System.out.println("----\t-------------\t------------\t----------\t-------------");

        for (int size : sizes) {
            if (size > 5000) {
                benchmarkSortingAlgorithms(size);
            } else {
                benchmarkAllAlgorithms(size);
            }
        }
    }

    public static void benchmarkAllAlgorithms(int size) {
        // MergeSort
        MergeSort ms = new MergeSort();
        int[] arr1 = ArrayUtils.generateRandomArray(size);
        ms.sort(arr1);

        // QuickSort
        QuickSort qs = new QuickSort();
        int[] arr2 = ArrayUtils.generateRandomArray(size);
        qs.sort(arr2);

        // Deterministic Select
        DeterministicSelect ds = new DeterministicSelect();
        int[] arr3 = ArrayUtils.generateRandomArray(size);
        ds.select(arr3, size / 2);

        // Closest Pair
        ClosestPair cp = new ClosestPair();
        ClosestPair.Point[] points = generateRandomPoints(size);
        cp.findClosestPair(points);

        System.out.printf("%d\t%.3f\t\t%.3f\t\t%.3f\t\t%.3f\n",
                size,
                ms.getMetrics().timeNs / 1e6,
                qs.getMetrics().timeNs / 1e6,
                ds.getMetrics().timeNs / 1e6,
                cp.getMetrics().timeNs / 1e6);
    }

    public static void benchmarkSortingAlgorithms(int size) {
        // MergeSort
        MergeSort ms = new MergeSort();
        int[] arr1 = ArrayUtils.generateRandomArray(size);
        ms.sort(arr1);

        // QuickSort
        QuickSort qs = new QuickSort();
        int[] arr2 = ArrayUtils.generateRandomArray(size);
        qs.sort(arr2);

        // Deterministic Select
        DeterministicSelect ds = new DeterministicSelect();
        int[] arr3 = ArrayUtils.generateRandomArray(size);
        ds.select(arr3, size / 2);

        System.out.printf("%d\t%.3f\t\t%.3f\t\t%.3f\t\tN/A\n",
                size,
                ms.getMetrics().timeNs / 1e6,
                qs.getMetrics().timeNs / 1e6,
                ds.getMetrics().timeNs / 1e6);
    }

    public static void generateCSV() {
        try (PrintWriter writer = new PrintWriter("metrics.csv")) {
            writer.println("Algorithm,Size,Time(ns),RecursionDepth,Comparisons,Allocations");

            int[] sizes = {100, 500, 1000, 5000};

            for (int size : sizes) {
                // MergeSort
                MergeSort ms = new MergeSort();
                int[] arr1 = ArrayUtils.generateRandomArray(size);
                ms.sort(arr1);
                writer.printf("MergeSort,%d,%d,%d,%d,%d\n",
                        size, ms.getMetrics().timeNs, ms.getMetrics().maxRecursionDepth,
                        ms.getMetrics().comparisons, ms.getMetrics().allocations);

                // QuickSort
                QuickSort qs = new QuickSort();
                int[] arr2 = ArrayUtils.generateRandomArray(size);
                qs.sort(arr2);
                writer.printf("QuickSort,%d,%d,%d,%d,%d\n",
                        size, qs.getMetrics().timeNs, qs.getMetrics().maxRecursionDepth,
                        qs.getMetrics().comparisons, qs.getMetrics().allocations);

                // Deterministic Select
                if (size <= 5000) {
                    DeterministicSelect ds = new DeterministicSelect();
                    int[] arr3 = ArrayUtils.generateRandomArray(size);
                    ds.select(arr3, size / 2);
                    writer.printf("Select,%d,%d,%d,%d,%d\n",
                            size, ds.getMetrics().timeNs, ds.getMetrics().maxRecursionDepth,
                            ds.getMetrics().comparisons, ds.getMetrics().allocations);
                }

                // Closest Pair
                if (size <= 1000) {
                    ClosestPair cp = new ClosestPair();
                    ClosestPair.Point[] points = generateRandomPoints(size);
                    cp.findClosestPair(points);
                    writer.printf("ClosestPair,%d,%d,%d,%d,%d\n",
                            size, cp.getMetrics().timeNs, cp.getMetrics().maxRecursionDepth,
                            cp.getMetrics().comparisons, cp.getMetrics().allocations);
                }
            }

            System.out.println("CSV file generated: metrics.csv");
        } catch (FileNotFoundException e) {
            System.err.println("Error creating CSV file: " + e.getMessage());
        }
    }

    public static void runTests() {
        System.out.println("Running comprehensive tests...");

        // Test sorting algorithms
        testSortingAlgorithms();

        // Test select algorithm
        testSelectAlgorithm();

        // Test closest pair algorithm
        testClosestPairAlgorithm();

        System.out.println("All tests completed!");
    }

    public static void testSortingAlgorithms() {
        System.out.println("\n=== Testing Sorting Algorithms ===");

        int[] sizes = {10, 100, 1000};
        for (int size : sizes) {
            int[] arr = ArrayUtils.generateRandomArray(size);

            // Test MergeSort
            MergeSort ms = new MergeSort();
            int[] arr1 = arr.clone();
            ms.sort(arr1);
            if (!ArrayUtils.isSorted(arr1)) {
                System.err.println("MergeSort failed for size " + size);
            } else {
                System.out.println("MergeSort passed for size " + size +
                        " (depth: " + ms.getMetrics().maxRecursionDepth + ")");
            }

            // Test QuickSort
            QuickSort qs = new QuickSort();
            int[] arr2 = arr.clone();
            qs.sort(arr2);
            if (!ArrayUtils.isSorted(arr2)) {
                System.err.println("QuickSort failed for size " + size);
            } else {
                System.out.println("QuickSort passed for size " + size +
                        " (depth: " + qs.getMetrics().maxRecursionDepth + ")");
            }
        }
    }

    public static void testSelectAlgorithm() {
        System.out.println("\n=== Testing Deterministic Select ===");

        Random random = new Random();
        int passed = 0;
        for (int i = 0; i < 10; i++) {
            int size = 100 + random.nextInt(900);
            int[] arr = ArrayUtils.generateRandomArray(size);
            int k = random.nextInt(size);

            DeterministicSelect ds = new DeterministicSelect();
            int result = ds.select(arr.clone(), k);

            int[] sorted = arr.clone();
            Arrays.sort(sorted);
            int expected = sorted[k];

            if (result != expected) {
                System.err.println("Select failed: expected " + expected + ", got " + result);
            } else {
                passed++;
            }
        }
        System.out.println("Select tests passed: " + passed + "/10");
    }

    public static void testClosestPairAlgorithm() {
        System.out.println("\n=== Testing Closest Pair Algorithm ===");

        // Test case 1: Known points
        ClosestPair cp = new ClosestPair();
        ClosestPair.Point[] points1 = {
                new ClosestPair.Point(0, 0),
                new ClosestPair.Point(1, 1),
                new ClosestPair.Point(1, 2),
                new ClosestPair.Point(3, 3)
        };
        double result1 = cp.findClosestPair(points1);
        double expected1 = Math.sqrt(2);
        if (Math.abs(result1 - expected1) > 1e-9) {
            System.err.println("ClosestPair test 1 failed: expected " + expected1 + ", got " + result1);
        } else {
            System.out.println("ClosestPair test 1 passed");
        }

        // Test case 2: Random points small n for brute force validation
        ClosestPair.Point[] points2 = generateRandomPoints(10);
        double result2 = cp.findClosestPair(points2);
        double bruteForce2 = bruteForceClosestPair(points2);
        if (Math.abs(result2 - bruteForce2) > 1e-9) {
            System.err.println("ClosestPair test 2 failed: expected " + bruteForce2 + ", got " + result2);
        } else {
            System.out.println("ClosestPair test 2 passed");
        }
    }

    // Helper method to generate random points
    private static ClosestPair.Point[] generateRandomPoints(int n) {
        Random random = new Random();
        ClosestPair.Point[] points = new ClosestPair.Point[n];
        for (int i = 0; i < n; i++) {
            points[i] = new ClosestPair.Point(random.nextDouble() * 100, random.nextDouble() * 100);
        }
        return points;
    }

    // Helper method for brute force closest pair validation
    private static double bruteForceClosestPair(ClosestPair.Point[] points) {
        double min = Double.MAX_VALUE;
        for (int i = 0; i < points.length; i++) {
            for (int j = i + 1; j < points.length; j++) {
                double dist = points[i].distance(points[j]);
                if (dist < min) {
                    min = dist;
                }
            }
        }
        return min;
    }

    private static String formatMetrics(AlgorithmMetrics metrics) {
        return String.format(
                "Time: %.3fms, Depth: %d, Comparisons: %d, Allocations: %d",
                metrics.timeNs / 1e6, metrics.maxRecursionDepth,
                metrics.comparisons, metrics.allocations
        );
    }
}