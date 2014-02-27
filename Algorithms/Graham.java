package Algorithms;

import Shapes.Point;
import Shapes.Vector;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Graham {
    public static int closestPoint(ArrayList<Point> points) {
        Point min = new Point(Integer.MAX_VALUE, Integer.MAX_VALUE);
        int index = 0;
        for (Point point : points) {
            if (point.getY() < min.getY()) {
                min = point;
                index = points.indexOf(point);
            } else if (point.getY() == min.getY()) {
                if (point.getX() < min.getX()) {
                    min = point;
                    index = points.indexOf(point);
                }
            }
        }
        return index;
    }

    public static int myCompare(Point p, Point p1, Point p2) {
        Vector v1 = new Vector(p, p1);
        Vector v2 = new Vector(p, p2);
        return (byte) Math.signum(Vector.mulVectors(v2, v1));
    }

    public static void sort(ArrayList<Point> points, final Point p) {
        Collections.sort(points, new Comparator<Point>() {
            @Override
            public int compare(Point p1, Point p2) {
                return myCompare(p, p1, p2);
            }
        });
    }

    public static ArrayList<Point> graham(ArrayList<Point> points) {
        ArrayList<Point> bestPoints = new ArrayList<Point>();
        Point tP = points.get(closestPoint(points));
        points.remove(closestPoint(points));
        sort(points, tP);
        points.add(0, tP);

        ArrayList<Point> uniqArray = new ArrayList<Point>();
        for (Point p : points) {
            uniqArray.add(p);
        }
        int index = 2;
        while (index < uniqArray.size()) {
            if (myCompare(uniqArray.get(0), uniqArray.get(index), uniqArray.get(index - 1)) == 0) {
                if ((new Vector(uniqArray.get(0), uniqArray.get(index))).getSize() >
                        (new Vector(uniqArray.get(0), uniqArray.get(index - 1))).getSize()) {
                    uniqArray.remove(index - 1);
                } else {
                    uniqArray.remove(index);
                }
            } else {
                index++;
            }
        }
        bestPoints.add(uniqArray.get(0));
        bestPoints.add(uniqArray.get(1));

        for (int i = 2; i < uniqArray.size(); i++) {
            while (Vector.mulVectors(new Vector(bestPoints.get(bestPoints.size() - 1), bestPoints.get(bestPoints.size() - 2)),
                    new Vector(bestPoints.get(bestPoints.size() - 1), uniqArray.get(i))) > 0) {
                bestPoints.remove(bestPoints.size() - 1);
            }
            bestPoints.add(uniqArray.get(i));
        }

        return bestPoints;
    }

    public static ArrayList<Point> jarviz(ArrayList<Point> points) {
        ArrayList<Point> copyArray = new ArrayList<Point>();
        for (Shapes.Point point : points) {
            copyArray.add(point);
        }

        ArrayList<Point> bestPoints = new ArrayList<Shapes.Point>();
        Shapes.Point cP = points.get(closestPoint(points));
        bestPoints.add(cP);
        points.remove(cP);
        points.add(cP);

        while (true) {
            int lastIndex = 0;
            for (int i = 0; i < copyArray.size(); i++) {
                if (myCompare(bestPoints.get(bestPoints.size() - 1), copyArray.get(lastIndex), copyArray.get(i)) == -1) {
                    lastIndex = i;
                }
            }
            if (copyArray.get(lastIndex) == bestPoints.get(0)) {
                break;
            } else {
                bestPoints.add(copyArray.get(lastIndex));
                copyArray.remove(lastIndex);
            }
        }

        return bestPoints;
    }
}
