package jp.ac.uryukyu.ie.e245703;

import jp.ac.uryukyu.ie.e245703.ui.FieldPanel;
import javax.swing.JFrame;

public class Main {
    public static void main(String[] args) {
        // ウィンドウの作成
        JFrame frame = new JFrame("Tetris");
        FieldPanel fieldPanel = new FieldPanel();

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(fieldPanel);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}