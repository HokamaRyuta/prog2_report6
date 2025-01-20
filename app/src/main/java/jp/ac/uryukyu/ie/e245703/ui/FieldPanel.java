package jp.ac.uryukyu.ie.e245703.ui;

import static jp.ac.uryukyu.ie.e245703.game.GameManage.*;
import jp.ac.uryukyu.ie.e245703.game.*;
import jp.ac.uryukyu.ie.e245703.game.Controller.MinoActionListener;
import jp.ac.uryukyu.ie.e245703.game.Controller.MinoKeyListener;

import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import javax.swing.Timer;
/**
 * フィールドや操作しているテトリミノを描画するクラスです。
 * {@link Timer}を用いて、一定時間ごとのテトリミノの落下を制御します。
 *  また、キーリスナーとアクションリスナーを保持し、ユーザー操作の受付を行います。
 */
public class FieldPanel extends JPanel{
    /**
     * シリアライズ時のバージョン管理用IDです。
     */
    private static final long serialVersionUID = 1L;
    /**
     * パネルのレイアウトマネージャーです。BorderLayoutを使用しています。
     */
    BorderLayout layout = new BorderLayout();
    /**
     * ゲームの状態を管理する{@link GameManage}オブジェクトへの参照です。
     */
    GameManage gm;
    /**
     * キーボード操作を受け付けるための{@link MinoKeyListener}です。
     */
    MinoKeyListener minoKeyListener;
    /**
     * 一定時間ごとにテトリミノを落下させるための{@link MinoActionListener}です。
     */
    MinoActionListener minoActionListener;
    /**
     * テトリミノの自動落下を制御するための{@link Timer}です。
     */
    Timer timer = null;

    // コンストラクタ
    /**
     * FieldPanelクラスのコンストラクタです。
     * ゲームの初期化、レイアウトや背景色の設定、キーリスナーやアクションリスナーの初期化を行います。
     * また、{@link Timer}を起動して、テトリミノの自動落下を開始します。
     */
    public FieldPanel(){
        gm = new GameManage();
        this.setLayout(layout); // レイアウト設定
        this.setBackground(Color.black); // 背景の色
        setPreferredSize(new Dimension(COLUMNS * TILE_SIZE, ROWS * TILE_SIZE)); // ウィンドウサイズ
        minoKeyListener = new MinoKeyListener(this);
        this.addKeyListener(minoKeyListener);
        this.setFocusable(true);
        this.requestFocusInWindow();
        minoActionListener = new MinoActionListener(this);
        timer = new Timer(800, minoActionListener);
        timer.start();
    }

    /**
     * フィールド全体および現在操作中のテトリミノ、ゲームオーバー画面を描画します。
     * @param g 描画用のGraphicsオブジェクト
     */
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
        if(gm.isControllable()){
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

        // ゲームオーバーの画面を描画
        if(gm.isGameOver()){
            stopTimer();
            // 画面を半透明の黒で塗りつぶし
            g.setColor(new Color(0, 0, 0, 200));
            g.fillRect(0, 0, getWidth(), getHeight());
            // ゲームオーバーの文字を表示
            g.setColor(Color.white);
            g.setFont(g.getFont().deriveFont(30f));
            g.drawString("GAME OVER", getWidth() / 2 - 90, getHeight() / 2 - 20);
            // 消去したライン数を表示
            g.setFont(g.getFont().deriveFont(20f));
            g.drawString("Lines Cleared: " + gm.getCountClearLines(), getWidth() / 2 - 90, getHeight() / 2 + 20);
        }
    }

    /**
     * テトリミノの形状に応じて描画する色を取得します。
     * @param shapeNumber テトリミノの形状を表す番号
     * @return 対応する色
     * <p>
     * 各番号がどのテトリミノに対応するかについては、
     * {@link Tetrimino#getShapeNumber()} のドキュメントを参照してください。
     * </p>
    */
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

    /**
     * ゲーム管理オブジェクトを取得します。
     * @return 現在の{@link GameManage}オブジェクト
     */
    public GameManage getGameManage(){
        return gm;
    }

    /**
     * タイマーを開始し、テトリミノの自動落下を開始します。
     */
    public void startTimer() {
        timer.start();
    }

    /**
     * タイマーを停止し、テトリミノの自動落下を停止します。
     */
    public void stopTimer() {
        timer.stop();
    }

    /**
     * 消去した累計ライン数に応じて、タイマーの遅延時間を更新します。
     * 初期状態は0.8秒あたりに1マス落下し、10ライン消すごとに0.05秒短縮していきます。
     * 最大速度は1フレーム(0.016秒)あたり1マスの落下速度に制限されます。
     */
    public void updateTimerDelay() {
        if(timer.getDelay() == 16){
            return;
        }
        int newDelay = 800 - (gm.getCountClearLines() / 10) * 50;

        if(newDelay == timer.getDelay()){
            return;
        }
        else if(newDelay > 16){
            timer.setDelay(newDelay);
            System.out.printf("レベルUP!!\n現在のレベル:%d\n", gm.getCountClearLines() / 10);
        }
        else{
            timer.setDelay(16);
        }
    }
}