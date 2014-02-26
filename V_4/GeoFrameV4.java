package V_4;

import Frames.FrameMethods;
import Frames.MyFrame;
import Shapes.Vector;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

//Класс главного окна приложения
public class GeoFrameV4 extends MyFrame {
    GeoFrameV4() {
        super();
    }

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
                jarviz();
                for (int i = 1; i < bestPoints.size(); ++i) {
                    g.drawLine(bestPoints.get(i).getX(), bestPoints.get(i).getY(),
                            bestPoints.get(i - 1).getX(), bestPoints.get(i - 1).getY());
                }
                jLabel1.update(jLabel1.getGraphics());
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

    public static int closestPoint() {
        Shapes.Point min = new Shapes.Point(Integer.MAX_VALUE, Integer.MAX_VALUE);
        int index = 0;
        for (Shapes.Point point : points) {
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

    public static int myCompare(Shapes.Point p, Shapes.Point p1, Shapes.Point p2) {
        Vector v1 = new Vector(p, p1);
        Vector v2 = new Vector(p, p2);
        return (byte) Math.signum(Vector.mulVectors(v2, v1));
    }

    public static void jarviz() {
        jButton1.setEnabled(false);
        jButton2.setEnabled(false);


        for (Shapes.Point point : points) {
            copyArray.add(point);
        }

        bestPoints = new ArrayList<Shapes.Point>();
        Shapes.Point cP = points.get(closestPoint());
        bestPoints.add(cP);
        points.remove(cP);
        points.add(cP);

        clearImage();
        showPoints();
        g.setColor(Color.GREEN);

        while (true) {
            int lastIndex = 0;
            for (int i = 0; i < copyArray.size(); i++) {
                if (myCompare(bestPoints.get(bestPoints.size() - 1), copyArray.get(lastIndex), copyArray.get(i)) == -1) {
                    g.setColor(Color.BLACK);
                    drawP(lastIndex);
                    lastIndex = i;
                    g.setColor(Color.BLUE);
                } else {
                    g.setColor(Color.RED);
                }
                drawP(i);
                jLabel1.update(jLabel1.getGraphics());
            }
            if (copyArray.get(lastIndex) == bestPoints.get(0)) {
                break;
            } else {
                bestPoints.add(copyArray.get(lastIndex));
                g.setColor(Color.GREEN);
                drawB();
                copyArray.remove(lastIndex);
                jLabel1.update(jLabel1.getGraphics());
            }
        }
        for (int i = 0; i < copyArray.size(); ++i) {
            g.setColor(Color.RED);
            if (!bestPoints.contains(copyArray.get(i))) {
                g.fillOval(copyArray.get(i).getX() - 5, copyArray.get(i).getY() - 5, 10, 10);
            }
        }
        for (int i = 0; i < bestPoints.size(); ++i) {
            g.setColor(Color.BLUE);
            g.fillOval(bestPoints.get(i).getX() - 5, bestPoints.get(i).getY() - 5, 10, 10);
        }
        g.setColor(Color.GREEN);
        g.drawLine(bestPoints.get(bestPoints.size() - 1).getX(), bestPoints.get(bestPoints.size() - 1).getY(),
                bestPoints.get(0).getX(), bestPoints.get(0).getY());

        copyArray = new ArrayList<Shapes.Point>();
        jButton1.setEnabled(true);
    }

    public static synchronized void drawB() {
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        g.drawLine(bestPoints.get(bestPoints.size() - 1).getX(), bestPoints.get(bestPoints.size() - 1).getY(),
                bestPoints.get(bestPoints.size() - 2).getX(), bestPoints.get(bestPoints.size() - 2).getY());
    }

    public static synchronized void drawP(final int i) {
        g.fillOval(copyArray.get(i).getX() - 5, copyArray.get(i).getY() - 5, 10, 10);
    }
}
