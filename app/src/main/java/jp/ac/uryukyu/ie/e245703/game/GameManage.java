package jp.ac.uryukyu.ie.e245703.game;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GameManage {
    // 定数
    public static final int TILE_SIZE = 30; // 1マスのサイズ（ピクセル）
    public static final int ROWS = 20; // フィールドの行数
    public static final int COLUMNS = 10; // フィールドの列数
    public static final int SPACE = 5; // フィールドの上の空間(描画はしない)
    // フィールド
    private int[][] field;
    private Tetrimino activeMino; // 現在操作可能なテトリミノを保持
    private boolean isControllable; // activeMinoが操作できる状態かを表す
    private int[] currentShuffleList; // 生成するテトリミノの順番を保存する配列
    private int[] nextShuffleList; // 生成するテトリミノの順番を保存する配列(currentShuffleListの全要素を参照し終えた後に使う)
    private int index; // currentShuffleListの要素にアクセスするためのインデックスを保存

    public GameManage(){
        this.field = new int[ROWS + SPACE][COLUMNS];  // 空白のフィールドを生成
        currentShuffleList = generateRandomArray();
        do{
            nextShuffleList = generateRandomArray();
        } while(currentShuffleList[6] == nextShuffleList[0]);
        generateMino();
    }

    public int getBlock(int y, int x){
        return field[y][x];
    }

    public Tetrimino getActiveMino(){
        return activeMino;
    }

    public boolean isControllable(){
        return isControllable;
    }

    public void enableControl(){
        isControllable = true;
    }

    public void disableControl(){
        isControllable = false;
    }

    public boolean canMove(){ // 現在操作しているテトリミノがフィールド外または他のブロックと重なっているかを判定するメソッド
        for(Point block : activeMino.getShape()){
            int x = activeMino.getPosition().x + block.x;
            int y = activeMino.getPosition().y + block.y;
            
            if(x < 0 || x >= COLUMNS || y < 0 || y >= ROWS + SPACE || field[y][x] != 0){
                return false; // 衝突
            }
        }
        return true;
    }

    // このメソッドを使用する場合は、必ずTetrimino.moveDown()を使用した直後にしてください。
    public boolean canFix(){ // 現在操作しているテトリミノがフィールド上に固定可能かを判定するメソッド
        for(Point block : activeMino.getShape()){
            int x = activeMino.getPosition().x + block.x;
            int y = activeMino.getPosition().y + block.y;
            
            if(y >= ROWS + SPACE || field[y][x] != 0){
                return true; // 固定可能
            }
        }
        return false; // 固定不可
    }

    public void fixMinoInField(){
        disableControl();
        for(Point block: activeMino.getShape()){
            field[block.y + activeMino.getPosition().y][block.x + activeMino.getPosition().x] = activeMino.getShapeNumber();
        }
    }

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

    public void generateMino(){
        if(!isControllable){
            activeMino = new Tetrimino(currentShuffleList[index]);
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
}
