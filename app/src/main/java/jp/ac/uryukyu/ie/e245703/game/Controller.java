package jp.ac.uryukyu.ie.e245703.game;

import jp.ac.uryukyu.ie.e245703.ui.FieldPanel;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;

public class Controller {
    public static class MinoKeyListener implements KeyListener{
        private FieldPanel panel;
        private GameManage gm;

        // コンストラクタ
        public MinoKeyListener(FieldPanel p){
            panel = p;
            gm = panel.getGameManage();
        }

        @Override
        public void keyTyped(KeyEvent e){
            // do nothing
        }

        @Override
        public void keyReleased(KeyEvent e){
            // do nothing
        }

        @Override
        public void keyPressed(KeyEvent e){
            if(gm.getActiveMino() != null){
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
                        gm.getActiveMino().moveDown();
                        if(!gm.canMove()){
                            gm.getActiveMino().moveUP();
                        }
                        break;
                }
            }
            panel.repaint();
        }
    }
}
