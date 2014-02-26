package Algorithms;

import Frames.MyFrame;
import Shapes.Line;
import Shapes.Point;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class V5 {
    /**
     * Сортирует точки по координате x слева-направо
     *
     * @param xArray сортируемый ArrayList
     */
    private static void sortByX(ArrayList<Point> xArray) {
        Collections.sort(xArray, new Comparator<Point>() {
            @Override
            public int compare(Point p1, Point p2) {
                if (p1.getX() > p2.getX()) {
                    return 1;
                } else if (p1.getX() < p2.getX()) {
                    return -1;
                } else if (p1.getY() > p2.getY()) {
                    return -1;
                }
                return 1;
            }
        });
    }

    private static void sortLines(ArrayList<Line> lines, final double X) {
        Collections.sort(lines, new Comparator<Line>() {
            @Override
            public int compare(Line p1, Line p2) {
                double y1 = (double) ((p1.getPoint1().getY() - p1.getPoint0().getY()) * X + p1.getPoint1().getX() * p1.getPoint0().getY() -
                        p1.getPoint0().getX() * p1.getPoint1().getY()) / (p1.getPoint1().getX() - p1.getPoint0().getX());
                double y2 = (double) ((p2.getPoint1().getY() - p2.getPoint0().getY()) * X + p2.getPoint1().getX() * p2.getPoint0().getY() -
                        p2.getPoint0().getX() * p2.getPoint1().getY()) / (p2.getPoint1().getX() - p2.getPoint0().getX());
                if (y1 > y2) {
                    return 1;
                } else {
                    return -1;
                }
            }
        });
    }

    //Добавить линию
    private static void insert(ArrayList<Line> tLines, Line l) {
        tLines.add(l);
    }

    //Получить верхнюю линию
    private static Line above(ArrayList<Line> tLines, Line l) {
        int index = -1;
        for (Line l0 : tLines) {
            if (l0 == l) {
                index = tLines.indexOf(l);
                break;
            }
        }
        if (index > 0) {
            return tLines.get(index - 1);
        } else if (index == 0) {
            return null;
        }
        return null;
    }

    //Получить нижнюю линию
    private static Line below(ArrayList<Line> tLines, Line l) {
        int index = -1;
        for (Line l0 : tLines) {
            if (l0 == l) {
                index = tLines.indexOf(l);
                break;
            }
        }
        if (index < tLines.size() - 1) {
            return tLines.get(index + 1);
        } else if (index == tLines.size() - 1) {
            return null;
        }
        return null;
    }

    //Удалить линию
    private static void delete(ArrayList<Line> tLines, Line l) {
        for (int i = 0; i < tLines.size(); i++) {
            if (l == tLines.get(i)) {
                tLines.remove(i);
                break;
            }
        }
    }

    //Пересекаются ли хотя бы два отрезка
    public static boolean isCrossed(ArrayList<Point> xArray) {
        ArrayList<Line> tLines = new ArrayList<Line>();

        V5.sortByX(xArray);

        for (Point p : xArray) {
            if (p.isLeftPoint()) {
                insert(tLines, p.getLine());
                if ((above(tLines, p.getLine()) != null && above(tLines, p.getLine()).isCrossed(p.getLine())) ||
                        (below(tLines, p.getLine()) != null && below(tLines, p.getLine()).isCrossed(p.getLine()))) {
                    return true;
                }
            }
            if (!p.isLeftPoint()) {
                if (above(tLines, p.getLine()) != null &&
                        below(tLines, p.getLine()) != null &&
                        above(tLines, p.getLine()).isCrossed(below(tLines, p.getLine()))) {
                    return true;
                }
                delete(tLines, p.getLine());
            }
        }
        return false;
    }

    //Пересекаются ли хотя бы два отрезка с отрисовкой
    public static boolean isCrossed2(ArrayList<Point> xArray) {
        ArrayList<Line> tLines = new ArrayList<Line>();

        V5.sortByX(xArray);

        for (Point p : xArray) {
            /*****/
            drawB(new Line(p.getX(),0,p.getX(),1000), Color.RED);
            MyFrame.jLabel1.update(MyFrame.jLabel1.getGraphics());
            /*****/
            if (p.isLeftPoint()) {
                insert(tLines, p.getLine());
                sortLines(tLines, p.getX());
                /*****/
                drawB(p.getLine(), Color.GREEN);
                MyFrame.jLabel1.update(MyFrame.jLabel1.getGraphics());
                /*****/

                if ((above(tLines, p.getLine()) != null && above(tLines, p.getLine()).isCrossed(p.getLine())) ||
                        (below(tLines, p.getLine()) != null && below(tLines, p.getLine()).isCrossed(p.getLine()))) {

                    /*****/
                    if (above(tLines, p.getLine()) != null && above(tLines, p.getLine()).isCrossed(p.getLine())) {
                        drawB(above(tLines, p.getLine()), Color.BLUE);
                        drawB(p.getLine(), Color.BLUE);
                        MyFrame.jLabel1.update(MyFrame.jLabel1.getGraphics());
                    } else {
                        drawB(below(tLines, p.getLine()), Color.BLUE);
                        drawB(p.getLine(), Color.BLUE);
                        MyFrame.jLabel1.update(MyFrame.jLabel1.getGraphics());
                    }
                    /*****/
                    return true;
                }
            }

            if (p.isLeftPoint() == false) {
                if (above(tLines, p.getLine()) != null &&
                        below(tLines, p.getLine()) != null &&
                        above(tLines, p.getLine()).isCrossed(below(tLines, p.getLine()))) {
                    /*****/
                    drawB(above(tLines, p.getLine()), Color.BLUE);
                    drawB(below(tLines, p.getLine()), Color.BLUE);
                    MyFrame.jLabel1.update(MyFrame.jLabel1.getGraphics());
                    /*****/
                    return true;
                }
                /*****/
                drawB(p.getLine(), Color.YELLOW);
                MyFrame.jLabel1.update(MyFrame.jLabel1.getGraphics());
                /*****/

                delete(tLines, p.getLine());
            }
        }
        return false;
    }

    public static synchronized void drawB(Line l, Color c) {
        try {
            Thread.sleep(100);

        } catch (InterruptedException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        MyFrame.g.setColor(c);
        MyFrame.g.drawLine(l.getPoint0().getX(), l.getPoint0().getY(),
                l.getPoint1().getX(), l.getPoint1().getY());
    }
}
