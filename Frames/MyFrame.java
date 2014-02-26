package Frames;

import Shapes.Line;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

//Класс главного окна приложения
public class MyFrame extends JFrame {
    public static MyFrame gFrame = new MyFrame();

    public final int DEFAULT_POSITION_X; //координата X верхнего левого угла окна
    public final int DEFAULT_POSITION_Y; //координата Y верхнего левого угла окна
    public final int DEFAULT_WIDTH;      //ширина окна
    public final int DEFAULT_HEIGHT;     //высота окна

    //Компоненты
    public static JPanel gPanel1;

    public static JLabel jLabel1;
    public static JTextArea jText1;
    public static JPanel gPanel2;

    public static JButton jButton1;
    public static JButton jButton2;
    public static JButton jButton3;

    //Дополнительные компоненты
    public static BufferedImage image1;
    public static ImageIcon iIcon1;
    public static Graphics g;

    public static final String rules = "Правила:" +
            "\nЧтобы начать, щелкните на \nсвободную область." +
            "\nНачало координат слева вверху." +
            "\nВо время выполнения алгоритма запрещены" +
            "\nлюбые изменения.";

    //Коструктор окна
    public MyFrame() {
        Toolkit t = Toolkit.getDefaultToolkit();
        DEFAULT_POSITION_X = t.getScreenSize().width / 4;
        DEFAULT_POSITION_Y = t.getScreenSize().height / 4;
        DEFAULT_WIDTH = t.getScreenSize().width / 2;
        DEFAULT_HEIGHT = t.getScreenSize().height / 2;

        JFrame.setDefaultLookAndFeelDecorated(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        setBounds(DEFAULT_POSITION_X, DEFAULT_POSITION_Y, DEFAULT_WIDTH, DEFAULT_HEIGHT);
        setIconImage(Toolkit.getDefaultToolkit().createImage("C:\\Users\\Игорь\\Desktop\\Документы\\X7l5SlpT1Uc.jpg"));

        gPanel1 = new JPanel(new BorderLayout());
        jLabel1 = new JLabel();
        jLabel1.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        jText1 = new JTextArea(rules);
        jText1.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        jText1.setEditable(false);
        gPanel2 = new JPanel(new GridLayout(12, 1));
        gPanel1.add(jLabel1, BorderLayout.EAST);
        gPanel1.add(gPanel2, BorderLayout.WEST);
        gPanel1.add(jText1, BorderLayout.CENTER);

        jButton1 = new JButton();
        jButton1.setText("Clear");
        jButton3 = new JButton();
        jButton3.setText("Random");
        jButton2 = new JButton();
        jButton2.setEnabled(false);
        jButton2.setText("Go!");
        gPanel2.add(jButton1);
        gPanel2.add(jButton2);
        gPanel2.add(jButton3);

        setContentPane(gPanel1);
        setVisible(true);

        image1 = new BufferedImage(getWidth() / 2, jLabel1.getHeight(), BufferedImage.TYPE_INT_RGB);
        image1.getGraphics().fillRect(0, 0, 500, 700);
        image1.getGraphics().setColor(Color.BLACK);

        iIcon1 = new ImageIcon();
        iIcon1.setImage(image1);
        jLabel1.setIcon(iIcon1);
        g = image1.getGraphics();
    }

    public static MyFrame getGeoFrame() {
        return gFrame;
    }

    //Глобальные переменные
    public static ArrayList<Shapes.Point> points = new ArrayList<Shapes.Point>();
    public static ArrayList<Shapes.Point> bestPoints = new ArrayList<Shapes.Point>();
    public static ArrayList<Shapes.Point> copyArray = new ArrayList<Shapes.Point>();
    //Линии
    public static ArrayList<Line> lines = new ArrayList<Line>();
    public static Line currentLine = new Line();
    //Нажата ли кнопка
    public static boolean isClicked = false;
    /*
     * Если isDragged > 0, значит, было нажатие на точку
     * -1 и 0 - отсутствие нажатия
     * -2 - до события было нажатие на точку
     */
    public static int isDragged = -1;
    //Координаты точки нажатия
    public static int X0, Y0;

    public static void clearImage() {
        image1.getGraphics().setColor(Color.WHITE);
        image1.getGraphics().fillRect(0, 0, jLabel1.getWidth(), jLabel1.getHeight());
        image1.getGraphics().setColor(Color.RED);
    }

    public static void showPoints() {
        for (Shapes.Point point : points) {
            g.setColor(Color.RED);
            g.setPaintMode();
            g.drawOval(point.getX() - 3, point.getY() - 3, 3, 3);
            jLabel1.repaint();
        }
    }

    public static void showLines() {
        for (Line line : lines) {
            g.setColor(Color.BLACK);
            g.setPaintMode();
            g.drawLine(line.getPoint0().getX(), line.getPoint0().getY(),
                    line.getPoint1().getX(), line.getPoint1().getY());
            jLabel1.repaint();
        }
    }
}
