package jp.ac.uryukyu.ie.e245703.game;

import java.awt.Point;

public class Tetrimino {
    private Point[] shape; // ミノの形状
    private Point position; // ミノの現在位置
    private int shapeNumber; // ミノの形状を表す番号

    public Tetrimino(int shapeNumber){
        switch (shapeNumber) { // 回転軸となる点がPoint(0, 0) // OミノとIミノは例外
            case 1: // Oミノ
            this.shape = new Point[]{new Point(0, 0), new Point(1, 0), new Point(0, -1), new Point(1, -1)};
            break;

            case 2: // Tミノ
                this.shape = new Point[]{new Point(0, 0), new Point(-1, 0), new Point(1, 0), new Point(0, -1)};
                break;

            case 3: // Sミノ
                this.shape = new Point[]{new Point(0, 0), new Point(-1, 0), new Point(0, -1), new Point(1, -1)};
                break;

            case 4: // Zミノ
                this.shape = new Point[]{new Point(0, 0), new Point(1, 0), new Point(-1, -1), new Point(0, -1)};
                break;

            case 5: // Lミノ
                this.shape = new Point[]{new Point(0, 0), new Point(-1, 0), new Point(1, 0), new Point(1, -1)};
                break;

            case 6: // Jミノ
                this.shape = new Point[]{new Point(0, 0), new Point(-1, 0), new Point(1, 0), new Point(-1, -1)};
                break;

            case 7: // Iミノ
                this.shape = new Point[]{new Point(-1, 0), new Point(0, 0), new Point(1, 0), new Point(2, 0)};
                break;
        }
        this.position = new Point(4, 4); // ミノの初期位置が下から21行目の真ん中くらいになる
        this.shapeNumber = shapeNumber;
    }

    public Point[] getShape(){
        return shape;
    }

    public Point getPosition(){
        return position;
    }

    public int getShapeNumber(){
        return shapeNumber;
    }

    public void moveRight(){
        position.x += 1;
    }

    public void moveLeft(){
        position.x -= 1;
    }

    public void moveDown(){
        position.y += 1;
    }
}
