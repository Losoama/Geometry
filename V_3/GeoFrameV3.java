package V_3;

import Frames.FrameMethods;
import Frames.MyFrame;
import Shapes.Line;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

//Класс главного окна приложения
public class GeoFrameV3 extends MyFrame {

    GeoFrameV3() {
        super();
    }

    static ArrayList<Shapes.Point> xpoints = new ArrayList<Shapes.Point>(points);
    static ArrayList<Shapes.Point> ypoints = new ArrayList<Shapes.Point>(points);

    public static void main(String[] args) {

        jLabel1.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                FrameMethods.mousePointPressed(e);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);
                FrameMethods.mousePointReleased(e);
            }
        }
        );

        jLabel1.addMouseMotionListener(new MouseMotionListener() {
            @Override
            public void mouseDragged(MouseEvent e) {
                FrameMethods.mousePointDragged(e);
            }

            @Override
            public void mouseMoved(MouseEvent e) {
            }
        });


        jButton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                FrameMethods.buttonClearClicked();
            }
        }
        );

        jButton2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                xpoints = new ArrayList<Shapes.Point>(points);
                ypoints = new ArrayList<Shapes.Point>(points);
                sortByX(xpoints);
                sortByY(ypoints);

                System.out.println();

                System.out.println();
                ArrayList<Shapes.Point> aux = new ArrayList<Shapes.Point>();
                Line l;
                l = closestPair(xpoints, ypoints, aux, 0, xpoints.size() - 1, new Line(0, 0, 500, 500));
                g.setColor(Color.GREEN);
                clearImage();
                showPoints();
                g.drawLine(l.getPoint0().getX(),
                        l.getPoint0().getY(),
                        l.getPoint1().getX(),
                        l.getPoint1().getY());
                jLabel1.update(jLabel1.getGraphics());
                g.setColor(Color.RED);
            }
        }
        );

        jButton3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                FrameMethods.buttonRandomClicked();
            }
        }
        );
    }

    public static void sortByX(ArrayList<Shapes.Point> xArray) {
        Collections.sort(xArray, new Comparator<Shapes.Point>() {
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

    static Line closestPair(ArrayList<Shapes.Point> xPoints, ArrayList<Shapes.Point> yPoints, ArrayList<Shapes.Point> aux,
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


        g.setColor(Color.YELLOW);
        if (lS.getPoint1().getX() < 400) {
            drawB(lS);
        }
        g.setColor(Color.BLUE);
        if (rS.getPoint1().getX() < 400) {
            drawB(rS);
        }
        g.setColor(Color.BLACK);
        drawB(s);
        jLabel1.update(jLabel1.getGraphics());
        g.setColor(Color.RED);

        return s;
    }

    public static synchronized void drawB(Line s) {
        try {
            Thread.sleep(10);
            g.drawLine(s.getPoint0().getX(),
                    s.getPoint0().getY(),
                    s.getPoint1().getX(),
                    s.getPoint1().getY());
        } catch (InterruptedException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }
}
