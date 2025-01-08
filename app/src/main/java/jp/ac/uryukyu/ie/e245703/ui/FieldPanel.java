package jp.ac.uryukyu.ie.e245703.ui;

import static jp.ac.uryukyu.ie.e245703.game.GameManage.*;
import jp.ac.uryukyu.ie.e245703.game.*;
import jp.ac.uryukyu.ie.e245703.game.Controller.MinoKeyListener;

import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;

public class FieldPanel extends JPanel{
    private static final long serialVersionUID = 1L;
    // レイアウト
    BorderLayout layout = new BorderLayout();
    // GameManageの参照
    GameManage gm;
    // キーリスナー
    MinoKeyListener minoKeyListener;

    // コンストラクタ
    public FieldPanel(){
        gm = new GameManage();
        this.setLayout(layout); // レイアウト設定
        this.setBackground(Color.black); // 背景の色
        setPreferredSize(new Dimension(COLUMNS * TILE_SIZE, ROWS * TILE_SIZE)); // ウィンドウサイズ
        minoKeyListener = new MinoKeyListener(this);
        this.addKeyListener(minoKeyListener);
        this.setFocusable(true);
        this.requestFocusInWindow();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // フィールドを描画
        for(int y = 0; y < ROWS; y++){
            for(int x = 0; x < COLUMNS; x++){
                if(gm.getBlock(y + SPACE, x) > 0){
                    // ブロックの描画
                    g.setColor(getBlockColor(gm.getBlock(y + SPACE, x)));
                    g.fillRect(x * TILE_SIZE, y * TILE_SIZE, TILE_SIZE, TILE_SIZE);
                    // 枠線の描画
                    g.setColor(Color.gray);
                    g.drawRect(x * TILE_SIZE, y * TILE_SIZE, TILE_SIZE, TILE_SIZE);
                }
            }
        }

        // 現在操作しているテトリミノを描画
        if(gm.getActiveMino() != null){
            for (Point block : gm.getActiveMino().getShape()) {
                int x = gm.getActiveMino().getPosition().x + block.x;
                int y = gm.getActiveMino().getPosition().y + block.y;
                if(x > 0 || x <= COLUMNS || y > SPACE || y <= ROWS + SPACE){
                    g.setColor(getBlockColor(gm.getActiveMino().getShapeNumber()));
                    g.fillRect(x * TILE_SIZE, (y - SPACE) * TILE_SIZE, TILE_SIZE, TILE_SIZE);
                    g.setColor(Color.GRAY);
                    g.drawRect(x * TILE_SIZE, (y - SPACE) * TILE_SIZE, TILE_SIZE, TILE_SIZE);
                }
            }
        }
    }

    // テトリミノの種類ごとに色を取得するメソッド
    public Color getBlockColor(int shapeNumber){
        switch (shapeNumber) {
            case 1: return Color.yellow; // Oミノ
            case 2: return Color.magenta; // Tミノ
            case 3: return Color.green; // Sミノ
            case 4: return Color.red; // Zミノ
            case 5: return Color.orange; // Lミノ
            case 6: return Color.blue; // Jミノ
            case 7: return Color.cyan; // Iミノ
            default: return Color.black; // 空
        }
    }

    public GameManage getGameManage(){
        return gm;
    }
}