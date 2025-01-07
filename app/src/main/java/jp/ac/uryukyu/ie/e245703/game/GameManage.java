package jp.ac.uryukyu.ie.e245703.game;

public class GameManage {
    // 定数
    public static final int TILE_SIZE = 30; // 1マスのサイズ（ピクセル）
    public static final int ROWS = 20; // フィールドの行数
    public static final int COLUMNS = 10; // フィールドの列数
    public static final int SPACE = 5; // フィールドの上の空間(描画はしない)
    // フィールド
    private int[][] field;

    public GameManage(){
        this.field = new int[ROWS + SPACE][COLUMNS];  // 空白のフィールドを生成
    }

    public int getBlock(int y, int x){
        return field[y][x];
    }

    public void setField(int[][] testField){ // テスト用のsetterメソッド(後々消去予定)
        this.field = testField;
    }

}
