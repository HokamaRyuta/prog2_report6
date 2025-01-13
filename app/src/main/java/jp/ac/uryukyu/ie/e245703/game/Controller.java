package jp.ac.uryukyu.ie.e245703.game;

import jp.ac.uryukyu.ie.e245703.ui.FieldPanel;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

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
                        gm.getActiveMino().moveDown();
                        if(!gm.canMove()){
                            if(gm.canFix()){
                                gm.getActiveMino().moveUP();
                                gm.fixMinoInField();
                                gm.generateMino();
                            }
                            else{
                                gm.getActiveMino().moveUP();
                            }
                        }
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

    public static class MinoActionListener implements ActionListener{
        private FieldPanel panel;
        private GameManage gm;

        // コンストラクタ
        public MinoActionListener(FieldPanel p){
            panel = p;
            gm = panel.getGameManage();
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if(gm.isControllable()){
                gm.getActiveMino().moveDown();
                if(!gm.canMove()){
                    if(gm.canFix()){
                        gm.getActiveMino().moveUP();
                        gm.fixMinoInField();
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
