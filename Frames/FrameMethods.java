package Frames;

import Shapes.Line;
import Shapes.Point;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.Random;

import static Frames.MyFrame.*;

public class FrameMethods {
    public static void mousePointPressed(MouseEvent e) {
        isClicked = true;
        //Нажатие по точке или нет.
        for (Shapes.Point point : points) {
            if (point.isContained(new Shapes.Point(e.getX(), e.getY()))) {
                isDragged = points.indexOf(point);
                X0 = e.getX();
                Y0 = e.getY();
                break;
            }
        }
    }

    public static void mousePointDragged(MouseEvent e) {
        boolean isSoClose = false;
        if (isClicked) {
            if (isDragged > -1) {
                g.drawOval(points.get(isDragged).getX() - 3,
                        points.get(isDragged).getY() - 3, 3, 3);
                points.remove(isDragged);
                isDragged = -2;
            } else {
                if (isDragged == -2) {
                    for (Shapes.Point point : points) {
                        isSoClose = point.equals(new Shapes.Point(e.getX(), e.getY()), 40);
                        if (isSoClose) break;
                    }
                    if (!isSoClose && e.getX() > 3 && e.getX() < jLabel1.getWidth() - 3
                            && e.getY() > 3 && e.getY() < jLabel1.getHeight() - 3) {
                        clearImage();
                        g.drawOval(e.getX() - 3,
                                e.getY() - 3, 3, 3);
                        X0 = e.getX();
                        Y0 = e.getY();

                    }
                }
            }
            showPoints();
        }
    }

    public static void mousePointReleased(MouseEvent e) {
        boolean isDelete = false;
        if (isClicked) {
            boolean isContained = false;
            //Проверка, отпускаем ли над областью точки
            for (Shapes.Point point : points) {
                isContained = point.equals(new Shapes.Point(e.getX(), e.getY()), 40);
                isDelete = isContained &&
                        point.equals(new Shapes.Point(e.getX(), e.getY()), 30) && isDragged != -2;
                if (isContained) break;
            }
            if (isDragged != -2) {
                if (!isContained) {
                    points.add(new Shapes.Point(e.getX(), e.getY()));
                    g.drawOval(e.getX() - 3, e.getY() - 3, 3, 3);
                } else if (isDragged > -1 && isDelete) {
                    points.remove(isDragged);
                }
            } else {
                points.add(new Shapes.Point(X0, Y0));
                g.drawOval(X0 - 3, Y0 - 3, 3, 3);
            }

            isDragged = -1;
            isClicked = false;
            clearImage();
            showPoints();
            jLabel1.repaint();
        }
        jButton2.setEnabled(points.size() > 2);
    }

    public static void buttonClearClicked() {
        image1.getGraphics().setColor(Color.WHITE);
        image1.getGraphics().fillRect(0, 0, 780, 640);
        jLabel1.repaint();
        points.clear();
        lines.clear();
        jText1.setText(rules);
        jButton2.setEnabled(false);
    }

    public static void buttonRandomClicked() {
        Random r = new Random();
        for (int i = 0; i < 10; i++) {
            boolean isCons = false;
            int X = 10 + r.nextInt(jLabel1.getWidth() - 20);
            int Y = 10 + r.nextInt(jLabel1.getWidth() - 20);
            for (Shapes.Point point : points) {
                isCons = point.isContained(new Shapes.Point(X, Y));
                if (isCons) break;
            }
            if (!isCons) {
                points.add(new Shapes.Point(X, Y));
                clearImage();
                showPoints();
            }
            jLabel1.repaint();
        }
        jButton2.setEnabled(points.size() > 2);
    }

    public static void buttonRandomLineClicked() {
        Random r = new Random();
        for (int i = 0; i < 3; i++) {
            int X0 = 10 + r.nextInt(jLabel1.getWidth() - 20);
            int Y0 = 10 + r.nextInt(jLabel1.getWidth() - 20);
            int X1 = 10 + r.nextInt(jLabel1.getWidth() - 20);
            int Y1 = 10 + r.nextInt(jLabel1.getWidth() - 20);

            g.setColor(Color.BLACK);
            g.drawLine(X0, Y0, X1, Y1);

            Line rLine = new Line(X0, Y0, X1, Y1);
            lines.add(rLine);
            points.add(rLine.getPoint0());
            points.add(rLine.getPoint1());

            jLabel1.repaint();
        }
        jButton2.setEnabled(points.size() > 2);
    }

    public static void mouseLinePressed(MouseEvent e) {
        g.setColor(Color.BLACK);
        isClicked = true;
        currentLine.setXY(e.getX(), e.getY(), e.getX(), e.getY());
    }

    public static void mouseLineReleased(MouseEvent e) {
        if (isClicked) {
            currentLine.setXY(currentLine.getPoint0().getX(), currentLine.getPoint0().getY(), e.getX(), e.getY());
            g.setPaintMode();
            jLabel1.repaint();
            lines.add(currentLine.copyLine());
            points.add(lines.get(lines.size() - 1).getPoint0());
            points.add(lines.get(lines.size() - 1).getPoint1());

            image1.getGraphics().setColor(Color.WHITE);
            image1.getGraphics().fillRect(0, 0, 480, 640);
            image1.getGraphics().setColor(Color.BLACK);
            for (Line line : lines) {
                g.drawLine(line.getPoint0().getX(), line.getPoint0().getY(),
                        line.getPoint1().getX(), line.getPoint1().getY());
            }

            isClicked = false;
            currentLine = new Line(0, 0, 0, 0);
            if (lines.size() > 1) {
                jButton2.setEnabled(true);
            }
        }
    }

    public static void mouseLineDragged(MouseEvent e) {
        if (isClicked) {
            Point p0 = currentLine.getPoint0();
            Point p1 = currentLine.getPoint1();
            g.drawLine(p0.getX(),
                    p0.getY(),
                    p1.getX(),
                    p1.getY());
            g.setXORMode(Color.white);
            g.drawLine(p0.getX(),
                    p0.getY(),
                    e.getX(), e.getY());
            currentLine.setXY(currentLine.getPoint0().getX(), currentLine.getPoint0().getY(), e.getX(), e.getY());
            jLabel1.repaint();
        }
    }
}