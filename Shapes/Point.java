package Shapes;

/*
 * Автор - Габайдулин Игорь
 * Не все права защищены
 *
 * Класс, реализующий точку.
 */
public class Point implements Comparable {
    private int x;
    private int y;

    //true, если точка лежит на конце отрезка
    private boolean haveLine = false;
    //Отрезок, на конце которого лежит точка
    private Line line;
    //Является ли точка крайне левой
    private boolean isLeft;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    //Присвоить линии
    public void setLine(Line line) {
        if (line.getPoint0() == this || line.getPoint1() == this) {
            haveLine = true;
            this.line = line;
            if (this == line.getPoint0()) {
                isLeft = line.getPoint0().getX() < line.getPoint1().getX();
            } else {
                isLeft = line.getPoint0().getX() > line.getPoint1().getX();
            }

        } else {
            throw new IllegalArgumentException("Точка не лежит на концах отрезка");
        }
    }

    //Получить линию, на конце которой лежит точка
    public Line getLine() {
        if (haveLine) {
            return line;
        } else {
            throw new IllegalStateException("Точка не принадлежит ни одной линии");
        }
    }

    //Если точка лежит на отрезке, мы можем узнать, является ли она крайней левой
    public boolean isLeftPoint() {
        if (haveLine) {
            return isLeft;
        } else {
            throw new IllegalStateException("Точка не принадлежит ни одной линии");
        }
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public void setXY(int x, int y) {
        this.x = x;
        this.y = y;
    }

    //Лежит ли точка в окрестностях другой точки
    public boolean isContained(Point p) {
        return (Math.pow(p.getX() - this.getX(), 2) + Math.pow(p.getY() - this.getY(), 2) < 1000);
    }

    //Являются ли точки одинаковыми с учетом окрестности
    public boolean equals(Object point, int S) {
        return point.getClass().getName().equals("Point") &&
                ((Math.pow(((Point) point).getX() - this.getX(), 2)) +
                        Math.pow(((Point) point).getY() - this.getY(), 2) < S);
    }

    @Override
    public String toString() {
        return "(" + this.getX() + ", " + this.getY() + ")";
    }

    @Override
    public int compareTo(Object o) {
        if (this.getY() > ((Point) o).getY()) {
            return 1;
        } else if (this.getY() < ((Point) o).getY()) {
            return -1;
        }
        return 0;
    }
}
