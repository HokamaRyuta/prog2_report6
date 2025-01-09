package jp.ac.uryukyu.ie.e245703.game;

import java.awt.Point;

public class GameManage {
    // 定数
    public static final int TILE_SIZE = 30; // 1マスのサイズ（ピクセル）
    public static final int ROWS = 20; // フィールドの行数
    public static final int COLUMNS = 10; // フィールドの列数
    public static final int SPACE = 5; // フィールドの上の空間(描画はしない)
    // フィールド
    private int[][] field;
    private Tetrimino activeMino; // 現在操作可能なテトリミノを保持

    public GameManage(){
        this.field = new int[ROWS + SPACE][COLUMNS];  // 空白のフィールドを生成
    }

    public int getBlock(int y, int x){
        return field[y][x];
    }

    public Tetrimino getActiveMino(){
        return activeMino;
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

    public void setActiveMino(Tetrimino mino){ // テスト用のsetterメソッド(後々消去予定)
        this.activeMino = mino;
    }

    public void setField(int[][] testField){ // テスト用のsetterメソッド(後々消去予定)
        this.field = testField;
    }

}
