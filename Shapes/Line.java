package Shapes;

/*
 * Автор - Габайдулин Игорь
 * Не все права защищены
 *
 * Класс, реализующий линию.
 */
public class Line {
    private Point point0;
    private Point point1;

    public Line() {
        this.point0 = new Point(0, 0);
        this.point1 = new Point(0, 0);
        point0.setLine(this); //При создании линии, точки автоматически присваиваются этой линии
        point1.setLine(this);
    }

    public Line(int x0, int y0, int x1, int y1) {
        this.point0 = new Point(x0, y0);
        this.point1 = new Point(x1, y1);
        point0.setLine(this);
        point1.setLine(this);
    }

    public Line(Point p0, Point p1) {
        this.point0 = new Point(p0.getX(), p0.getY());
        this.point1 = new Point(p1.getX(), p1.getY());
        point0.setLine(this);
        point1.setLine(this);
    }

    public void setXY(int x0, int y0, int x1, int y1) {
        this.point0.setXY(x0, y0);
        this.point1.setXY(x1, y1);
    }

    public Point getPoint0() {
        return this.point0;
    }

    public Point getPoint1() {
        return this.point1;
    }

    /**
     * Создает новую линию из точек указанной линии.
     *
     * @return Line line, где line - новый объект
     */
    public Line copyLine() {
        return new Line(
                this.getPoint0().getX(),
                this.getPoint0().getY(),
                this.getPoint1().getX(),
                this.getPoint1().getY());
    }

    /**
     * Пересекается ли линия с указанной
     *
     * @param line проверяемая линия
     * @return /result, где /result - true, если линии пересекаются
     */
    public Boolean isCrossed(Line line) {
        int f1 = Integer.signum(Vector.mulVectors(new Vector(this), new Vector(this.point1, line.point0)));
        int f2 = Integer.signum(Vector.mulVectors(new Vector(this), new Vector(this.point1, line.point1)));
        int f3 = Integer.signum(Vector.mulVectors(new Vector(line), new Vector(line.point1, this.point0)));
        int f4 = Integer.signum(Vector.mulVectors(new Vector(line), new Vector(line.point1, this.point1)));
        if (f1 * f2 < 0 && f3 * f4 < 0) {
            return true;
        } else if (((f1 * f2 == 0 && f3 * f4 < 0) || (f3 * f4 == 0 && f1 * f2 < 0) ||
                (f1 * f2 == 0 && f3 * f4 == 0)) && (f1 != f2)) {
            return true;
        } else if (f1 == 0 && f2 == 0 && f3 == 0 && f4 == 0) {
            if (maxAbs(
                    Math.max(this.point0.getX(), this.point1.getX()) -
                            Math.min(line.point0.getX(), line.point1.getX()),
                    Math.min(this.point0.getX(), this.point1.getX()) -
                            Math.max(line.point0.getX(), line.point1.getX()))
                    <=
                    Math.abs(this.point0.getX() - this.point1.getX()) +
                            Math.abs(line.point0.getX() - line.point1.getX())) {
                return false;
            } else {
                return false;
            }
        }

        return false;
    }

    /**
     * Дополнительный метод, возвращает максимальное число по модулю из двух
     *
     * @param a первое число
     * @param b второе число
     * @return /result, где /result - max(abs(a), abs(b))
     */
    private static int maxAbs(int a, int b) {
        if (Math.abs(a) > Math.abs(b)) {
            return Math.abs(a);
        }
        return Math.abs(b);
    }

    @Override
    public String toString() {
        return "(" + this.getPoint0().getX() + ", " + this.getPoint0().getY() + "); " +
                "(" + this.getPoint1().getX() + ", " + this.getPoint1().getY() + ")";
    }

    /**
     * Получить длину линии
     */
    public double getSize() {
        return (new Vector(this)).getSize();
    }

    /**
     * Сравнить линии по длине
     *
     * @param o сравниваемая с исходной линия
     * @return /result, где result - линия с большей длиной
     */
    public Line compare(Object o) {
        if (this.getSize() > ((Line) o).getSize()) {
            return this;
        } else if (this.getSize() < ((Line) o).getSize()) {
            return (Line) o;
        }
        return this;
    }

    public Point getLeftPoint() {
        return point0.isLeftPoint() ? point0 : point1;
    }

    public Point getRightPoint() {
        return point0.isLeftPoint() ? point1 : point0;
    }
}
