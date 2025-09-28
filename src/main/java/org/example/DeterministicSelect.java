package org.example;

import java.util.Random;

public class DeterministicSelect {
    private AlgorithmMetrics metrics;
    private Random random;

    public DeterministicSelect() {
        this.metrics = new AlgorithmMetrics();
        this.random = new Random();
    }

    public int select(int[] arr, int k) {
        if (k < 0 || k >= arr.length) {
            throw new IllegalArgumentException("k must be between 0 and " + (arr.length - 1));
        }

        long startTime = System.nanoTime();
        metrics.reset();
        int[] arrCopy = arr.clone();
        int result = quickSelect(arrCopy, 0, arrCopy.length - 1, k, 0);
        metrics.timeNs = System.nanoTime() - startTime;
        return result;
    }

    private int quickSelect(int[] arr, int left, int right, int k, int depth) {
        metrics.recursiveCalls++;
        metrics.maxRecursionDepth = Math.max(metrics.maxRecursionDepth, depth);

        if (left == right) {
            return arr[left];
        }

        // Choose random pivot for simplicity and reliability
        int pivotIndex = left + random.nextInt(right - left + 1);
        pivotIndex = partition(arr, left, right, pivotIndex);

        if (k == pivotIndex) {
            return arr[k];
        } else if (k < pivotIndex) {
            return quickSelect(arr, left, pivotIndex - 1, k, depth + 1);
        } else {
            return quickSelect(arr, pivotIndex + 1, right, k, depth + 1);
        }
    }

    private int partition(int[] arr, int left, int right, int pivotIndex) {
        int pivotValue = arr[pivotIndex];
        ArrayUtils.swap(arr, pivotIndex, right);

        int storeIndex = left;
        for (int i = left; i < right; i++) {
            metrics.comparisons++;
            if (arr[i] < pivotValue) {
                ArrayUtils.swap(arr, storeIndex, i);
                storeIndex++;
            }
        }
        ArrayUtils.swap(arr, right, storeIndex);
        return storeIndex;
    }

    public AlgorithmMetrics getMetrics() { return metrics; }
}