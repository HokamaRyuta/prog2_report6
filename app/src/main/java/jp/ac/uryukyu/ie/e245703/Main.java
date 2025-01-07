package jp.ac.uryukyu.ie.e245703;

import jp.ac.uryukyu.ie.e245703.ui.FieldPanel;
import javax.swing.JFrame;

public class Main {
    public static void main(String[] args) {
        // サンプルのフィールドを準備
        int[][] testField = new int[25][10];
        // Oミノ設置
        testField[23][8] = 1;
        testField[24][8] = 1;
        testField[23][9] = 1;
        testField[24][9] = 1;
        // Tミノ設置
        testField[23][1] = 2;
        testField[23][2] = 2;
        testField[23][3] = 2;
        testField[24][2] = 2;
        // Sミノ設置
        testField[22][6] = 3;
        testField[23][6] = 3;
        testField[23][7] = 3;
        testField[24][7] = 3;
        // Zミノ設置
        testField[22][3] = 4;
        testField[22][4] = 4;
        testField[23][4] = 4;
        testField[23][5] = 4;
        // Lミノ設置
        testField[22][0] = 5;
        testField[23][0] = 5;
        testField[24][0] = 5;
        testField[24][1] = 5;
        // Jミノ設置
        testField[21][3] = 6;
        testField[21][4] = 6;
        testField[21][5] = 6;
        testField[22][5] = 6;
        // Iミノ設置
        testField[24][3] = 7;
        testField[24][4] = 7;
        testField[24][5] = 7;
        testField[24][6] = 7;

        // ウィンドウの作成
        JFrame frame = new JFrame("Tetris");
        FieldPanel fieldPanel = new FieldPanel();

        fieldPanel.getGameManage().setField(testField);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(fieldPanel);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}