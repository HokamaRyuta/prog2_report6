package jp.ac.uryukyu.ie.e245703;

import jp.ac.uryukyu.ie.e245703.game.Tetrimino;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.awt.Point;

public class TetriminoTest {
    /*
     * 4回同じ方向に回転させることで、テトリミノが元の形状に戻ることを検証
     * 検証手順
     * （1）テトリミノを生成
     * （2）テトリミノを回転する前にshapeを保存
     * （3）テトリミノを4回同じ方向に回転させる
     * （4）回転後のshapeが回転する前に保存したshapeと一致するかを確認
     */
    @Test
    void testClockwiseRotationFourTimesReturnsToOriginalShape(){ // 時計回りを4回
        for(int shapeNumber = 1; shapeNumber <= 7; shapeNumber++){
            Tetrimino mino = new Tetrimino(shapeNumber);

            Point[] beforeShape = new Point[mino.getShape().length];
            for(int i=0; i<mino.getShape().length; i++){
                beforeShape[i] = new Point(mino.getShape()[i].x, mino.getShape()[i].y);
            }

            int count = 0;
            while(count != 4){
                mino.clockwiseRotation();
                count++;
            }

            for(int i=0; i<beforeShape.length; i++){
                assertEquals(beforeShape[i], mino.getShape()[i]);
            }
        }
    }

    @Test
    void testcounterclockwiseRotationFourTimesReturnsToOriginalShape(){ // 反時計回りを4回
        for(int shapeNumber = 1; shapeNumber <= 7; shapeNumber++){
            Tetrimino mino = new Tetrimino(shapeNumber);

            Point[] beforeShape = new Point[mino.getShape().length];
            for(int i=0; i<mino.getShape().length; i++){
                beforeShape[i] = new Point(mino.getShape()[i].x, mino.getShape()[i].y);
            }

            int count = 0;
            while(count != 4){
                mino.counterclockwiseRotation();
                count++;
            }

            for(int i=0; i<beforeShape.length; i++){
                assertEquals(beforeShape[i], mino.getShape()[i]);
            }
        }
    }

    /*
     * 時計回りと反時計回りの回転を同じ回数行うことで、テトリミノの形状が元に戻ることを検証
     * 検証手順
     * （1）テトリミノを生成
     * （2）テトリミノを回転する前にshapeを保存
     * （3）テトリミノを時計回りと反時計回りにそれぞれ1回ずつ回転
     * （4）回転後のshapeが回転する前に保存したshapeと一致するかを確認
     * （5）(3)の回転の順序を入れ替えて(4)を再度実行
     */
    @Test
    void testClockwiseAndCounterclockwiseRotationCancelEachOther(){
        for(int shapeNumber = 1; shapeNumber <= 7; shapeNumber++){
            Tetrimino mino = new Tetrimino(shapeNumber);

            Point[] beforeShape = new Point[mino.getShape().length];
            for(int i=0; i<mino.getShape().length; i++){
                beforeShape[i] = new Point(mino.getShape()[i].x, mino.getShape()[i].y);
            }

            mino.clockwiseRotation();
            mino.counterclockwiseRotation();
            for(int i=0; i<beforeShape.length; i++){
                assertEquals(beforeShape[i], mino.getShape()[i]);
            }

            mino.counterclockwiseRotation();
            mino.clockwiseRotation();
            for(int i=0; i<beforeShape.length; i++){
                assertEquals(beforeShape[i], mino.getShape()[i]);
            }
        }
    }
}
