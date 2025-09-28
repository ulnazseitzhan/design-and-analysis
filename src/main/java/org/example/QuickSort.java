package org.example;

import java.util.Random;

public class QuickSort {
    private AlgorithmMetrics metrics;
    private Random random;

    public QuickSort() {
        this.metrics = new AlgorithmMetrics();
        this.random = new Random();
    }

    public void sort(int[] arr) {
        long startTime = System.nanoTime();
        metrics.reset();
        ArrayUtils.shuffle(arr);
        sort(arr, 0, arr.length - 1, 0);
        metrics.timeNs = System.nanoTime() - startTime;
    }

    private void sort(int[] arr, int left, int right, int depth) {
        metrics.recursiveCalls++;
        metrics.maxRecursionDepth = Math.max(metrics.maxRecursionDepth, depth);

        while (left < right) {
            int pivotIndex = partition(arr, left, right);

            if (pivotIndex - left < right - pivotIndex) {
                sort(arr, left, pivotIndex - 1, depth + 1);
                left = pivotIndex + 1;
            } else {
                sort(arr, pivotIndex + 1, right, depth + 1);
                right = pivotIndex - 1;
            }
        }
    }

    private int partition(int[] arr, int left, int right) {
        int pivot = arr[right];
        int i = left - 1;

        for (int j = left; j < right; j++) {
            metrics.comparisons++;
            if (arr[j] <= pivot) {
                i++;
                ArrayUtils.swap(arr, i, j);
            }
        }
        ArrayUtils.swap(arr, i + 1, right);
        return i + 1;
    }

    public AlgorithmMetrics getMetrics() { return metrics; }
}