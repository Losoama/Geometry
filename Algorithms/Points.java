package Algorithms;

import Shapes.Line;
import Shapes.Point;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Created by Игорь on 27.02.14.
 */
public class Points {
    public static void sortByX(ArrayList<Shapes.Point> xArray) {
        Collections.sort(xArray, new Comparator<Point>() {
            @Override
            public int compare(Shapes.Point p1, Shapes.Point p2) {
                if (p1.getX() > p2.getX()) {
                    return 1;
                } else if (p1.getX() < p2.getX()) {
                    return -1;
                }
                return 0;
            }
        });
    }

    public static void sortByY(ArrayList<Shapes.Point> yArray) {
        Collections.sort(yArray, new Comparator<Shapes.Point>() {
            @Override
            public int compare(Shapes.Point p1, Shapes.Point p2) {
                if (p1.getY() > p2.getY()) {
                    return 1;
                } else if (p1.getY() < p2.getY()) {
                    return -1;
                }
                return 0;
            }
        });
    }

    public static Line closestPair(ArrayList<Point> xPoints, ArrayList<Shapes.Point> yPoints, ArrayList<Shapes.Point> aux,
                            int left, int right, Line s0) {
        if (left >= right) {
            return new Line(0, 0, Integer.MAX_VALUE, Integer.MAX_VALUE);
        }

        int middle = ((right + left) >> 1) + ((right + left) % 2);
        Shapes.Point mPoint = xPoints.get(middle);

        Line lS;
        lS = closestPair(xPoints, yPoints, aux, left, middle - 1, s0);
        Line rS;
        rS = closestPair(xPoints, yPoints, aux, middle + 1, right, s0);
        Line s = lS.compare(rS).compare(s0);

        int index = 0;

        for (int i = left; i <= right; i++) {
            if ((Math.abs((new Line(yPoints.get(i), mPoint)).getSize())) < (s.getSize())) {
                aux.add(index++, yPoints.get(i));
            }
        }

        for (int i = 0; i < index; i++) {
            for (int j = i + 1; (j < index) && (aux.get(i).getY() - aux.get(j).getY()) <
                    (s.getSize()); j++) {
                if (((new Line(aux.get(i), aux.get(j))).getSize()) < (s.getSize())) {
                    s = new Line(aux.get(i), aux.get(j));
                }
            }
        }

        return s;
    }
}
