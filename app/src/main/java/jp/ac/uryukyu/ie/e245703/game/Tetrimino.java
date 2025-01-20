package jp.ac.uryukyu.ie.e245703.game;

import java.awt.Point;

/**
 * テトリミノの形状や現在の位置を管理するクラスです。
 * テトリミノの移動や回転の処理を担当します。
 */
public class Tetrimino {
    /**
     * テトリミノの形状を表します。
     */
    private Point[] shape;
    /**
     * テトリミノの現在位置を表します。
     */
    private Point position;
    /**
     * テトリミノの形状を表す番号です。
     *  <p>
     * この番号は以下のように各テトリミノの種類に対応しています:
     * <ul>
     *     <li>1: Oミノ (正方形)</li>
     *     <li>2: Tミノ (T字型)</li>
     *     <li>3: Sミノ (S字型)</li>
     *     <li>4: Zミノ (Z字型)</li>
     *     <li>5: Lミノ (L字型)</li>
     *     <li>6: Jミノ (逆L字型)</li>
     *     <li>7: Iミノ (棒型)</li>
     * </ul>
     * </p>
     * <p>
     * この番号は、テトリミノの形状を初期化する際や、フィールド上に描画する際に使用されます。
     * </p>
     */
    private int shapeNumber;
    /**
     * テトリミノの回転状態を表す番号です。
     * <p>
     * この番号は主にIミノの回転を処理する際に使用されます。
     * </p>
     * <p>
     * 番号と対応する回転状態は以下の通りです:
     * <ul>
     *     <li>1: 初期状態</li>
     *     <li>2: 時計回りに90度回転</li>
     *     <li>3: 時計回りに180度回転</li>
     *     <li>4: 時計回りに270度回転</li>
     * </ul>
     * </p>
     */
    private int minoDir;

    /**
     * 指定された形状番号に基づいて、テトリミノのインスタンスを生成します。
     * <p>
     * このコンストラクタは、以下の操作を行います:
     * <ul>
     *     <li>引数の{@code shapeNumber}に対応するテトリミノの形状を{@link #shape}配列に設定します。</li>
     *     <li>テトリミノの初期位置をフィールド中央（21行目の中央付近）に設定します。</li>
     *     <li>テトリミノの回転状態を初期状態の1に設定します。</li>
     * </ul>
     * <p>
     * 各形状番号がどのテトリミノに対応するかについては、
     * {@link #shapeNumber} のドキュメントを参照してください。
     *
     * @param shapeNumber 生成したいテトリミノの形状の番号。
     */
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

    /**
     * テトリミノの形状を取得します。
     * 
     * @return {@link Point}の配列で表現されたテトリミノの形状。
     */
    public Point[] getShape(){
        return shape;
    }

    /**
     * テトリミノの現在位置を取得します。
     * 
     * @return テトリミノの現在位置を表す{@link Point}オブジェクト。
     */
    public Point getPosition(){
        return position;
    }

    /**
     * テトリミノの形状を表す番号を取得します。
     * <p>
     * 各形状番号がどのテトリミノに対応するかについては、
     * {@link #shapeNumber} のドキュメントを参照してください。
     * </p>
     * @return テトリミノの形状を表す番号。
     */
    public int getShapeNumber(){
        return shapeNumber;
    }

    /**
     * テトリミノを右に1マス動かします。
     */
    public void moveRight(){
        position.x += 1;
    }

    /**
     * テトリミノを左に1マス動かします。
     */
    public void moveLeft(){
        position.x -= 1;
    }

    /**
     * テトリミノを下に1マス動かします。
     */
    public void moveDown(){
        position.y += 1;
    }

    /**
     * テトリミノを上に1マス動かします。
     */
    public void moveUP(){
        position.y -= 1;
    }

    /**
     * Iミノを回転状態に応じて形状を変更します。
     * <p>
     * 現在の回転状態（{@code minoDir}）に基づいて、Iミノの形状を更新します。
     * 回転状態は1から4の番号で表され、それぞれ以下の状態を示します:
     * <ul>
     *     <li>1: 初期状態</li>
     *     <li>2: 時計回りに90度回転</li>
     *     <li>3: 時計回りに180度回転</li>
     *     <li>4: 時計回りに270度回転</li>
     * </ul>
     * <p>
     * なお、回転処理は{@code shapeNumber}が7(Iミノの場合)のみ実行されます。
     */
    public void setRotateImino(){
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

    /**
     * テトリミノを時計回りに90度回転します。
     * <p>
     * ただし、Oミノの場合は回転しません。
     * </p>
     */
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

    /**
     * テトリミノを反時計回りに90度回転します。
     * <p>
     * ただし、Oミノの場合は回転しません。
     * </p>
     */
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
