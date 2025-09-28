package org.example;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.*;

public class AlgorithmTest {

    // Existing tests...
    @Test
    public void testMergeSort() {
        // ... existing code
    }

    @Test
    public void testQuickSort() {
        // ... existing code
    }

    @Test
    public void testDeterministicSelect() {
        // ... existing code
    }

    @Test
    public void testClosestPair() {
        // ... existing code
    }

    // ===== MISSING TESTS TO ADD =====

    @Test
    public void testSortingEdgeCases() {
        System.out.println("\n=== Testing Sorting Edge Cases ===");

        // Test 1: Already sorted array
        int[] sortedArray = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        MergeSort ms = new MergeSort();
        QuickSort qs = new QuickSort();

        int[] msSorted = sortedArray.clone();
        ms.sort(msSorted);
        assertTrue(ArrayUtils.isSorted(msSorted));
        System.out.println("MergeSort with sorted array: PASS");

        int[] qsSorted = sortedArray.clone();
        qs.sort(qsSorted);
        assertTrue(ArrayUtils.isSorted(qsSorted));
        System.out.println("QuickSort with sorted array: PASS");

        // Test 2: Reverse sorted array
        int[] reverseArray = {10, 9, 8, 7, 6, 5, 4, 3, 2, 1};

        int[] msReverse = reverseArray.clone();
        ms.sort(msReverse);
        assertTrue(ArrayUtils.isSorted(msReverse));
        System.out.println("MergeSort with reverse array: PASS");

        int[] qsReverse = reverseArray.clone();
        qs.sort(qsReverse);
        assertTrue(ArrayUtils.isSorted(qsReverse));
        System.out.println("QuickSort with reverse array: PASS");

        // Test 3: Array with duplicates
        int[] duplicatesArray = {5, 2, 5, 1, 2, 3, 5, 2, 1, 3};

        int[] msDup = duplicatesArray.clone();
        ms.sort(msDup);
        assertTrue(ArrayUtils.isSorted(msDup));
        System.out.println("MergeSort with duplicates: PASS");

        int[] qsDup = duplicatesArray.clone();
        qs.sort(qsDup);
        assertTrue(ArrayUtils.isSorted(qsDup));
        System.out.println("QuickSort with duplicates: PASS");

        // Test 4: Very small arrays
        int[] singleElement = {42};
        int[] twoElements = {2, 1};
        int[] emptyArray = {};

        ms.sort(singleElement);
        assertArrayEquals(new int[]{42}, singleElement);
        ms.sort(twoElements);
        assertTrue(ArrayUtils.isSorted(twoElements));
        System.out.println("MergeSort with small arrays: PASS");

        qs.sort(singleElement.clone());
        qs.sort(twoElements.clone());
        System.out.println("QuickSort with small arrays: PASS");
    }

    @Test
    public void testSelectComprehensive() {
        System.out.println("\n=== Comprehensive Select Tests ===");

        DeterministicSelect ds = new DeterministicSelect();
        Random random = new Random();

        // Test with different array types
        int passed = 0;
        int totalTests = 0;

        // Random arrays
        for (int i = 0; i < 5; i++) {
            int size = 50 + random.nextInt(100);
            int[] arr = ArrayUtils.generateRandomArray(size);
            int k = random.nextInt(size);

            int result = ds.select(arr.clone(), k);
            int[] sorted = arr.clone();
            Arrays.sort(sorted);

            if (result == sorted[k]) passed++;
            totalTests++;
        }

        // Sorted array
        int[] sortedArr = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        for (int k = 0; k < sortedArr.length; k++) {
            int result = ds.select(sortedArr.clone(), k);
            if (result == sortedArr[k]) passed++;
            totalTests++;
        }

        // Reverse sorted array
        int[] reverseArr = {10, 9, 8, 7, 6, 5, 4, 3, 2, 1};
        for (int k = 0; k < reverseArr.length; k++) {
            int result = ds.select(reverseArr.clone(), k);
            int[] sorted = reverseArr.clone();
            Arrays.sort(sorted);
            if (result == sorted[k]) passed++;
            totalTests++;
        }

        // Array with duplicates
        int[] dupArr = {3, 1, 4, 1, 5, 9, 2, 6, 5, 3};
        for (int k = 0; k < dupArr.length; k++) {
            int result = ds.select(dupArr.clone(), k);
            int[] sorted = dupArr.clone();
            Arrays.sort(sorted);
            if (result == sorted[k]) passed++;
            totalTests++;
        }

        System.out.println("Select comprehensive tests: " + passed + "/" + totalTests + " passed");
        assertTrue(passed == totalTests, "All select tests should pass");
    }

    @Test
    public void testClosestPairComprehensive() {
        System.out.println("\n=== Comprehensive Closest Pair Tests ===");

        ClosestPair cp = new ClosestPair();

        // Test 1: Known simple case
        ClosestPair.Point[] points1 = {
                new ClosestPair.Point(0, 0),
                new ClosestPair.Point(3, 4), // distance 5
                new ClosestPair.Point(1, 1)  // distance √2 ≈ 1.414
        };
        double result1 = cp.findClosestPair(points1);
        double expected1 = Math.sqrt(2);
        assertEquals(expected1, result1, 1e-9, "Closest pair simple case");
        System.out.println("Simple case: PASS");

        // Test 2: Points with same x-coordinate
        ClosestPair.Point[] points2 = {
                new ClosestPair.Point(5, 0),
                new ClosestPair.Point(5, 2),
                new ClosestPair.Point(5, 5),
                new ClosestPair.Point(5, 3)
        };
        double result2 = cp.findClosestPair(points2);
        double expected2 = 1.0; // Distance between (5,2) and (5,3)
        assertEquals(expected2, result2, 1e-9, "Points with same x-coordinate");
        System.out.println("Same x-coordinate: PASS");

        // Test 3: Large random set validation (small n for brute force)
        ClosestPair.Point[] points3 = generateRandomPoints(15);
        double result3 = cp.findClosestPair(points3);
        double bruteForce3 = bruteForceClosestPair(points3);
        assertEquals(bruteForce3, result3, 1e-9, "Large random set validation");
        System.out.println("Large random validation: PASS");

        // Test 4: Performance test with larger n
        ClosestPair.Point[] points4 = generateRandomPoints(1000);
        long startTime = System.nanoTime();
        double result4 = cp.findClosestPair(points4);
        long endTime = System.nanoTime();
        double timeMs = (endTime - startTime) / 1e6;
        System.out.println("1000 points processed in: " + timeMs + "ms");
        assertTrue(timeMs < 1000, "Should handle 1000 points reasonably fast"); // 1 second limit
        System.out.println("Performance test: PASS");
    }

    // Helper methods
    private ClosestPair.Point[] generateRandomPoints(int n) {
        Random random = new Random();
        ClosestPair.Point[] points = new ClosestPair.Point[n];
        for (int i = 0; i < n; i++) {
            points[i] = new ClosestPair.Point(random.nextDouble() * 100, random.nextDouble() * 100);
        }
        return points;
    }

    private double bruteForceClosestPair(ClosestPair.Point[] points) {
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

    @Test
    public void testRecursionDepthBounded() {
        System.out.println("\n=== Testing Recursion Depth Bounds ===");

        // Test QuickSort depth (should be O(log n))
        int[] largeArray = ArrayUtils.generateRandomArray(10000);
        QuickSort qs = new QuickSort();
        qs.sort(largeArray);

        int depth = qs.getMetrics().maxRecursionDepth;
        int expectedMaxDepth = (int) (2 * (Math.log(largeArray.length) / Math.log(2)) + 10); // ~2*log2(n) + buffer

        System.out.println("QuickSort depth: " + depth + ", expected <= " + expectedMaxDepth);
        assertTrue(depth <= expectedMaxDepth, "QuickSort recursion depth should be O(log n)");

        // Test MergeSort depth
        MergeSort ms = new MergeSort();
        ms.sort(largeArray.clone());
        int msDepth = ms.getMetrics().maxRecursionDepth;
        int expectedMsDepth = (int) (Math.log(largeArray.length) / Math.log(2)) + 5;

        System.out.println("MergeSort depth: " + msDepth + ", expected <= " + expectedMsDepth);
        assertTrue(msDepth <= expectedMsDepth, "MergeSort recursion depth should be O(log n)");
    }
}