package jp.ac.uryukyu.ie.e245703.game;

import jp.ac.uryukyu.ie.e245703.ui.FieldPanel;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;

public class Controller {
    public static class MinoKeyListener implements KeyListener{
        private FieldPanel panel;

        // コンストラクタ
        public MinoKeyListener(FieldPanel p){
            panel = p;
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
            switch (e.getKeyCode()) {
                case KeyEvent.VK_RIGHT:
                    panel.getGameManage().getActiveMino().moveRight();
                    break;

                case KeyEvent.VK_LEFT:
                    panel.getGameManage().getActiveMino().moveLeft();
                    break;

                case KeyEvent.VK_DOWN:
                    panel.getGameManage().getActiveMino().moveDown();
                    break;
            }
            panel.repaint();
        }
    }
}
