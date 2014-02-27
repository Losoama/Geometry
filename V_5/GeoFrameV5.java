package V_5;

import Algorithms.BentleyOttman;
import Frames.FrameMethods;
import Frames.MyFrame;

import java.awt.event.*;

//Класс главного окна приложения
public class GeoFrameV5 extends MyFrame {

    GeoFrameV5() {
        super();
    }

    public static void main(String[] args) {
        jLabel1.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                FrameMethods.mouseLinePressed(e);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);
                FrameMethods.mouseLineReleased(e);
            }
        }
        );

        jLabel1.addMouseMotionListener(new MouseMotionListener() {
            @Override
            public void mouseDragged(MouseEvent e) {
                FrameMethods.mouseLineDragged(e);
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
                showLines();
                if (BentleyOttman.isCrossed2(lines)) {
                    jText1.setText("Есть пересечение, капитан!");
                } else {
                    jText1.setText("Увы, но пересечений нет!");
                }
            }
        }
        );

        jButton3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                FrameMethods.buttonRandomLineClicked();
            }
        }
        );
    }
}
