package org.example;

public class MergeSort {
    private AlgorithmMetrics metrics;
    private static final int INSERTION_CUTOFF = 15;

    public MergeSort() {
        this.metrics = new AlgorithmMetrics();
    }

    public void sort(int[] arr) {
        long startTime = System.nanoTime();
        metrics.reset();
        int[] buffer = new int[arr.length];
        sort(arr, 0, arr.length - 1, buffer, 0);
        metrics.timeNs = System.nanoTime() - startTime;
        metrics.allocations = 1; // buffer allocation
    }

    private void sort(int[] arr, int left, int right, int[] buffer, int depth) {
        metrics.recursiveCalls++;
        metrics.maxRecursionDepth = Math.max(metrics.maxRecursionDepth, depth);

        if (right - left <= INSERTION_CUTOFF) {
            insertionSort(arr, left, right);
            return;
        }

        int mid = left + (right - left) / 2;
        sort(arr, left, mid, buffer, depth + 1);
        sort(arr, mid + 1, right, buffer, depth + 1);
        merge(arr, left, mid, right, buffer);
    }

    private void merge(int[] arr, int left, int mid, int right, int[] buffer) {
        System.arraycopy(arr, left, buffer, left, right - left + 1);

        int i = left, j = mid + 1, k = left;
        while (i <= mid && j <= right) {
            metrics.comparisons++;
            if (buffer[i] <= buffer[j]) {
                arr[k++] = buffer[i++];
            } else {
                arr[k++] = buffer[j++];
            }
        }

        while (i <= mid) arr[k++] = buffer[i++];
        while (j <= right) arr[k++] = buffer[j++];
    }

    private void insertionSort(int[] arr, int left, int right) {
        for (int i = left + 1; i <= right; i++) {
            int key = arr[i];
            int j = i - 1;
            while (j >= left && arr[j] > key) {
                metrics.comparisons++;
                arr[j + 1] = arr[j];
                j--;
            }
            arr[j + 1] = key;
        }
    }

    public AlgorithmMetrics getMetrics() { return metrics; }
}