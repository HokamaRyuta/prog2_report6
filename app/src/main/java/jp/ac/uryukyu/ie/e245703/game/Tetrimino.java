package jp.ac.uryukyu.ie.e245703.game;

import java.awt.Point;

public class Tetrimino {
    private Point[] shape; // ミノの形状
    private Point position; // ミノの現在位置
    private int shapeNumber; // ミノの形状を表す番号
    private int minoDir; // ミノの回転状態を表す番号

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
        this.minoDir = 1; // 初期化時の回転状態
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

    public void moveUP(){
        position.y -= 1;
    }

    public void setRotateImino(){ // minoDirの値によってIミノの形状を変更するメソッド
        if(shapeNumber == 7){
            switch (minoDir) {
                case 1: // 初期化時の回転状態
                    shape = new Point[]{new Point(-1, 0), new Point(0, 0), new Point(1, 0), new Point(2, 0)};
                    break;

                case 2:
                    shape = new Point[]{new Point(1, -1), new Point(1, 0), new Point(1, 1), new Point(1, 2)};
                    break;

                case 3:
                    shape = new Point[]{new Point(-1, 1), new Point(0, 1), new Point(1, 1), new Point(2, 1)};
                    break;

                case 4:
                    shape = new Point[]{new Point(0, -1), new Point(0, 0), new Point(0, 1), new Point(0, 2)};
                    break;
            }
        }
    }

    public void clockwiseRotation(){
        // ミノの回転状態の更新
        if(minoDir == 4){
            minoDir = 1;
        }
        else{
            minoDir += 1;
        }

        if(shapeNumber == 7){ // Iミノの場合
            setRotateImino();
        }
        else if(shapeNumber != 1){ // Oミノを除く他の5種類の場合
            for (int i = 0; i < shape.length; i++) {
                int x = shape[i].x;
                shape[i].x = -shape[i].y;
                shape[i].y = x;
            }
        }
    }

    public void counterclockwiseRotation(){
        // ミノの回転状態の更新
        if(minoDir == 1){
            minoDir = 4;
        }
        else{
            minoDir -= 1;
        }

        if(shapeNumber == 7){ // Iミノの場合
            setRotateImino();
        }
        else if(shapeNumber != 1){ // Oミノを除く他の5種類の場合
            for (int i = 0; i < shape.length; i++) {
                int y = shape[i].y;
                shape[i].y = -shape[i].x;
                shape[i].x = y;
            }
        }
    }
}
