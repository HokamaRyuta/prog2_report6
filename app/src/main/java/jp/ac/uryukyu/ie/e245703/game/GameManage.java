package jp.ac.uryukyu.ie.e245703.game;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * ゲームルールなどのロジックを管理するクラスです。
 * テトリミノの生成やフィールドの状態の更新、ライン消去の処理を担当します。
 */
public class GameManage {
    // 定数
    /**
     * ブロックを描画する際の1マスのサイズ(ピクセル)です。
     */
    public static final int TILE_SIZE = 30;
    /**
     * フィールドの行数です。
     */
    public static final int ROWS = 20;
    /**
     * フィールドの列数です。
     */
    public static final int COLUMNS = 10;
    /**
     * フィールドの上の空間です。
     * この空間にブロックを置くことはできますが、描画はしません。
     */
    public static final int SPACE = 5;
    // フィールド
    /**
     * フィールドの状態を保持する2次元配列です。
     * 配列の要素はブロックの種類を表す数値 (0は空) です。
     */
    private int[][] field;
    /**
     * 現在操作中のテトリミノを保持します。
     */
    private Tetrimino activeMino;
    /**
     * activeMinoが操作できる状態かを表します。
     */
    private boolean isControllable;
    /**
     * 生成するテトリミノの順番を保持する配列です。
     */
    private int[] currentShuffleList;
    /**
     * 生成するテトリミノの順番を保持する配列です。
     * currentShuffleListの全要素を参照し終えた後に使用します。
     */
    private int[] nextShuffleList;
    /**
     * currentShuffleListの要素にアクセスするためのインデックスを保存します。
     */
    private int index;
    /**
     * 消去したラインの累計数を記録します。
     * この値によって、テトリミノの落下速度が上昇します。
     */
    private int countClearLines;

    /**
     * 空白のフィールドと、生成するテトリミノの順番を保持する配列の値を生成し、ゲームをスタートします。
     * <p>
     * コンストラクタ内で{@link #generateRandomArray()}を用いて、currentShuffleListを作成します。
     * 2回連続で同じ種類のミノが出てこないように、最初の要素がcurrentShuffleListの最後の要素と別になるまでnextShuffleListを作成します。
     * </p>
     */
    public GameManage(){
        this.field = new int[ROWS + SPACE][COLUMNS];  // 空白のフィールドを生成
        currentShuffleList = generateRandomArray();
        do{
            nextShuffleList = generateRandomArray();
        } while(currentShuffleList[6] == nextShuffleList[0]);
        generateMino();
        System.out.printf("ゲームスタート!!\n現在のレベル:%d\n", countClearLines);
    }

    /**
     * フィールド上の指定されたマスの状態を取得します。
     * 
     * @param y 行番号
     * @param x 列番号
     * @return 指定されたマスの状態。値は以下のいずれかです。
     * <ul>
     *   <li>0 - 空きマス</li>
     *   <li>1～7 - テトリミノの種類を表す値</li>
     * </ul>
     */
    public int getBlock(int y, int x){
        return field[y][x];
    }

    /**
     * 現在操作中のテトリミノを取得します。
     * 
     * @return 現在操作中の {@link Tetrimino} オブジェクト。
     */
    public Tetrimino getActiveMino(){
        return activeMino;
    }

    /**
     * 消去したラインの累計数を取得します。
     * 
     * @return 現在の消去したラインの累計数。
     */
    public int getCountClearLines(){
        return countClearLines;
    }

    /**
     * 現在操作中のテトリミノが操作可能な状態かどうかを返します。
     *
     * @return {@code true} の場合、操作可能。{@code false} の場合、操作不可。
     */
    public boolean isControllable(){
        return isControllable;
    }

    /**
     * テトリミノを操作可能な状態に設定します。
     */
    public void enableControl(){
        isControllable = true;
    }

    /**
     * テトリミノを操作不能な状態に設定します。
     */
    public void disableControl(){
        isControllable = false;
    }

    /**
     * 現在操作しているテトリミノが、フィールド外または他のブロックと重なっているかを判定します。
     * 
     * @return {@code true} の場合、フィールド範囲内に適切に位置している。{@code false} の場合、衝突判定。
     */
    public boolean canMove(){
        for(Point block : activeMino.getShape()){
            int x = activeMino.getPosition().x + block.x;
            int y = activeMino.getPosition().y + block.y;
            
            if(x < 0 || x >= COLUMNS || y < 0 || y >= ROWS + SPACE || field[y][x] != 0){
                return false; // 衝突
            }
        }
        return true;
    }

    /**
     * 現在操作しているテトリミノが、フィールド上に固定可能かを判定します。
     * <b>注意:</b> このメソッドは、必ず{@link Tetrimino#moveDown()}を使用した直後に使用してください。
     * 
     * @return {@code true} の場合、固定可能。{@code false} の場合、固定不可。
     */
    public boolean canFix(){
        for(Point block : activeMino.getShape()){
            int x = activeMino.getPosition().x + block.x;
            int y = activeMino.getPosition().y + block.y;
            
            if(y >= ROWS + SPACE || field[y][x] != 0){
                return true; // 固定可能
            }
        }
        return false; // 固定不可
    }

    /**
     * 現在操作しているテトリミノをフィールド上に固定します。
     */
    public void fixMinoInField(){
        disableControl();
        for(Point block: activeMino.getShape()){
            field[block.y + activeMino.getPosition().y][block.x + activeMino.getPosition().x] = activeMino.getShapeNumber();
        }
    }

    /**
     * 1〜7の値をランダムにシャッフルした配列を取得します。
     * 
     * @return 1〜7の順番がランダムにシャッフルされた配列。
     */
    public int[] generateRandomArray(){
        // 1～7をリストに追加
        List<Integer> set = new ArrayList<>();
        for (int i = 1; i <= 7; i++) {
            set.add(i);
        }
        // リスト内の要素をシャッフル
        Collections.shuffle(set);
        // リストをストリーム化し、Integerをintに変換した後、配列にして返す
        return set.stream().mapToInt(Integer::intValue).toArray();
    }

    /**
     * テトリミノを新たにフィールド上に配置します。
     * 
     * <p>
     * このメソッドは、現在のテトリミノが操作可能でない場合にのみ、新しいテトリミノを生成します。
     * テトリミノの生成は「七種一巡の法則」に基づいて行われ、同じ種類のテトリミノが二巡続けて
     * 出現しないように制御されています。
     * </p>
     * 
     * <p>
     * ただし、ゲームオーバーの条件を満たしている場合、このメソッドは処理を中断します。
     * </p>
     */
    public void generateMino(){
        if(!isControllable){
            activeMino = new Tetrimino(currentShuffleList[index]);
            // ゲームオーバーの場合は処理を中断
            if(isGameOver()){
                return;
            }
            // 次に生成するテトリミノの順番を管理
            if(index == 6){
                currentShuffleList = nextShuffleList.clone();
                do{
                    nextShuffleList = generateRandomArray();
                } while(currentShuffleList[6] == nextShuffleList[0]);
                index = 0;
            }
            else{
                index++;
            }
            enableControl();
        }
    }

    /**
     * 指定した行番号のフィールドを消去して、上から詰めます。
     * 一番上の行は空白になります。
     * 
     * @param row 行番号
     */
    public void clearLine(int row){
        // 引数で指定した行を消去して、上から詰める
        for(int y = row; y > 0; y--){
            for(int x = 0; x < COLUMNS; x++){
                field[y][x] = field[y-1][x];
            }
        }
        // 一番上の行を空白にする
        for(int x = 0; x < COLUMNS; x++){
            field[0][x] = 0;
        }
    }

    /**
     * フィールドの各ラインが消去可能かを判定し、可能な場合は{@link #clearLine(int)}を実行します。
     * <p>
     * 消去されたラインがあった場合は、詰められた行を再度チェックするために
     * インデックスを調整しています。
     * また、消去された累計ライン数は{@link #countClearLines}によって記録されます。
     * </p>
     */
    public void clearAndCountLines(){
        for(int y = 0; y < ROWS + SPACE; y++){
            boolean isRowFilled = true;
            for(int x = 0; x < COLUMNS; x++){
                if(field[y][x] == 0){
                    isRowFilled = false;
                    break;
                }
            }
            if(isRowFilled){
                clearLine(y); // ラインを消去
                y--; // 詰められた行を再チェック
                countClearLines++; // 消去したライン数をカウント
            }
        }
    }

    /**
     * ゲームオーバーの条件を満たしているかを判定します。
     * <p>
     * テトリミノが生成される21段目以上で、他のブロックとの衝突判定があったとき、ゲームオーバーとなります。
     * </p>
     * @return {@code true}の場合、ゲームオーバー。{@code false}の場合、ゲーム継続。
     */
    public boolean isGameOver(){ // テトリミノの生成位置にブロックがあるかを判定
        for(Point block: activeMino.getShape()){
            if(block.y + activeMino.getPosition().y < SPACE || block.y + activeMino.getPosition().y >= 0){ // テトリミノの位置が21段目以上にあることを確認
                if(field[block.y + activeMino.getPosition().y][block.x + activeMino.getPosition().x] != 0){
                    return true; // ゲームオーバー
                }
            }
        }
        return false;
    }
}
