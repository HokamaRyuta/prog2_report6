package jp.ac.uryukyu.ie.e245703.game;

import jp.ac.uryukyu.ie.e245703.ui.FieldPanel;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

/**
 * テトリスゲームのユーザー操作を処理するためのコントローラークラスです。
 * 内部クラスとして、キーボード操作を処理する{@link MinoKeyListener}と、
 * 時間経過によるテトリミノの自動落下を処理する{@link MinoActionListener}を提供します。
 */
public class Controller {
    /**
     * キーボード操作を処理するリスナークラスです。
     * ユーザーのキーボード入力に基づいて、テトリミノの移動や回転を行います。
     */
    public static class MinoKeyListener implements KeyListener{
        private FieldPanel panel;
        private GameManage gm;

        // コンストラクタ
        /**
         * {@link MinoKeyListener}のコンストラクタです。
         * @param p 貼り付け先の{@link FieldPanel}オブジェクト
         */
        public MinoKeyListener(FieldPanel p){
            panel = p;
            gm = panel.getGameManage();
        }

        /**
         * キーがタイプされた際の処理を定義します。(未使用)
         * @param e キーイベント
         */
        @Override
        public void keyTyped(KeyEvent e){
            // do nothing
        }

        /**
         * キーがリリースされた際の処理を定義します。(未使用)
         * @param e キーイベント
         */
        @Override
        public void keyReleased(KeyEvent e){
            // do nothing
        }

        /**
         * キーが押下された際の処理を定義します。
         * テトリミノがフィールド上の適切な位置で移動や回転をするように制御します。
         * @param e キーイベント
         */
        @Override
        public void keyPressed(KeyEvent e){
            if(gm.isControllable()){
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_RIGHT:
                        gm.getActiveMino().moveRight();
                        if(!gm.canMove()){
                            gm.getActiveMino().moveLeft();
                        }
                        break;

                    case KeyEvent.VK_LEFT:
                        gm.getActiveMino().moveLeft();
                        if(!gm.canMove()){
                            gm.getActiveMino().moveRight();
                        }
                        break;

                    case KeyEvent.VK_DOWN:
                        panel.stopTimer();
                        gm.getActiveMino().moveDown();
                        if(!gm.canMove()){
                            if(gm.canFix()){
                                gm.getActiveMino().moveUP();
                                gm.fixMinoInField();
                                gm.clearAndCountLines();
                                panel.updateTimerDelay();
                                gm.generateMino();
                            }
                            else{
                                gm.getActiveMino().moveUP();
                            }
                        }
                        panel.startTimer();
                        break;

                    case KeyEvent.VK_E:
                        gm.getActiveMino().clockwiseRotation();
                        if(!gm.canMove()){
                            gm.getActiveMino().counterclockwiseRotation();
                        }
                        break;

                    case KeyEvent.VK_W:
                        gm.getActiveMino().counterclockwiseRotation();
                        if(!gm.canMove()){
                            gm.getActiveMino().clockwiseRotation();
                        }
                        break;
                }
            }
            panel.repaint();
        }
    }

    /**
     * 時間経過によるテトリミノの自動落下を処理するリスナークラスです。
     * 一定時間ごとに呼び出され、テトリミノを1マス下に移動します。
     */
    public static class MinoActionListener implements ActionListener{
        private FieldPanel panel;
        private GameManage gm;

        // コンストラクタ
        /**
         * {@link MinoActionListener}のコンストラクタです。
         * @param p 貼り付け先の{@link FieldPanel}オブジェクト
         */
        public MinoActionListener(FieldPanel p){
            panel = p;
            gm = panel.getGameManage();
        }

        /**
         * 一定時間ごとに呼び出され、テトリミノの自動落下を処理します。
         * @param e アクションイベント
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            if(gm.isControllable()){
                gm.getActiveMino().moveDown();
                if(!gm.canMove()){
                    if(gm.canFix()){
                        gm.getActiveMino().moveUP();
                        gm.fixMinoInField();
                        gm.clearAndCountLines();
                        panel.updateTimerDelay();
                        gm.generateMino();
                    }
                    else{
                        gm.getActiveMino().moveUP();
                    }
                }
            }
            panel.repaint();
        }
    }
}
