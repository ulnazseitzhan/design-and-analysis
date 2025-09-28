package org.example;

import java.util.*;

public class ClosestPair {
    private AlgorithmMetrics metrics;

    public static class Point {
        public double x, y;
        public Point(double x, double y) {
            this.x = x; this.y = y;
        }
        public double distance(Point other) {
            double dx = x - other.x;
            double dy = y - other.y;
            return Math.sqrt(dx * dx + dy * dy);
        }
    }

    public ClosestPair() {
        this.metrics = new AlgorithmMetrics();
    }

    public double findClosestPair(Point[] points) {
        long startTime = System.nanoTime();
        metrics.reset();

        Point[] pointsByX = points.clone();
        Arrays.sort(pointsByX, (a, b) -> Double.compare(a.x, b.x));
        metrics.allocations++;

        double result = closestPair(pointsByX, 0, pointsByX.length - 1, 0);
        metrics.timeNs = System.nanoTime() - startTime;
        return result;
    }

    private double closestPair(Point[] pointsByX, int left, int right, int depth) {
        metrics.recursiveCalls++;
        metrics.maxRecursionDepth = Math.max(metrics.maxRecursionDepth, depth);

        int n = right - left + 1;
        if (n <= 3) {
            return bruteForce(pointsByX, left, right);
        }

        int mid = left + (right - left) / 2;
        double midX = pointsByX[mid].x;

        double d1 = closestPair(pointsByX, left, mid, depth + 1);
        double d2 = closestPair(pointsByX, mid + 1, right, depth + 1);
        double d = Math.min(d1, d2);

        List<Point> strip = new ArrayList<>();
        for (int i = left; i <= right; i++) {
            if (Math.abs(pointsByX[i].x - midX) < d) {
                strip.add(pointsByX[i]);
            }
        }

        strip.sort((a, b) -> Double.compare(a.y, b.y));

        double stripMin = d;
        for (int i = 0; i < strip.size(); i++) {
            for (int j = i + 1; j < strip.size() && (strip.get(j).y - strip.get(i).y) < stripMin; j++) {
                metrics.comparisons++;
                double dist = strip.get(i).distance(strip.get(j));
                if (dist < stripMin) {
                    stripMin = dist;
                }
            }
        }

        return Math.min(d, stripMin);
    }

    private double bruteForce(Point[] points, int left, int right) {
        double min = Double.MAX_VALUE;
        for (int i = left; i <= right; i++) {
            for (int j = i + 1; j <= right; j++) {
                metrics.comparisons++;
                double dist = points[i].distance(points[j]);
                if (dist < min) {
                    min = dist;
                }
            }
        }
        return min;
    }

    public AlgorithmMetrics getMetrics() { return metrics; }
}