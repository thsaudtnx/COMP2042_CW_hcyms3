package brickGame.gameComponents;

import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.*;


public class BallTest {

    @Test
    void checkInitializationOfBall(){
        BallWithoutUI tempBall = new BallWithoutUI();

        assertFalse(tempBall.isGoldStatus);
        assertEquals(tempBall.goldTime, 0);
        assertEquals(tempBall.balls.size(), 1);
    }

    @Test
    void checkGoldTimeOverWhenTimeOver(){
        BallWithoutUI tempBall = new BallWithoutUI();

        tempBall.setGoldTime(10);
        tempBall.checkGoldTimeOver(16);

        assertFalse(tempBall.isGoldStatus);
    }

    @Test
    void checkGoldTimeOverWhenTimeNotOver(){
        BallWithoutUI tempBall = new BallWithoutUI();

        tempBall.setGoldTime(10);
        tempBall.checkGoldTimeOver(15);

        assertTrue(tempBall.isGoldStatus);
    }

    @Test
    void checkInitializationOfBallEntry(){
        BallWithoutUI.BallEntry ballEntry = new BallWithoutUI.BallEntry();
        ballEntry.resetCollideFlags();

        assertTrue(ballEntry.goDownBall);
        assertTrue(ballEntry.goRightBall);
        assertFalse(ballEntry.collideToBottomWall);
        assertFalse(ballEntry.collideToBlock);
        assertEquals(ballEntry.collideBlock, null);
        assertEquals(ballEntry.xBall, (500 - 5) / 2);
        assertEquals(ballEntry.yBall, 700 - 50);
    }

    @Test
    void checkBallMovement(){
        BallWithoutUI.BallEntry ballEntry = new BallWithoutUI.BallEntry();
        BreakWithoutUI rect = new BreakWithoutUI();
        int level = 1;
        ArrayList<BlockWithoutUI.BlockEntry> blocks = new ArrayList<>();

        ballEntry.setPhysicsToBall(rect, level, blocks);

        assertEquals(ballEntry.xBall, 248);
        assertEquals(ballEntry.yBall, 651);

    }
    @Test
    void checkBallHitsBottomWall(){
        BallWithoutUI.BallEntry ballEntry = new BallWithoutUI.BallEntry();
        BreakWithoutUI rect = new BreakWithoutUI();
        rect.xBreak = 0;
        int level = 1;
        ArrayList<BlockWithoutUI.BlockEntry> blocks = new ArrayList<>();

        while (true){
            ballEntry.setPhysicsToBall(rect, level, blocks);

            if (ballEntry.collideToBottomWall){
                assertTrue(ballEntry.collideToBottomWall);
                break;
            } else {
                assertFalse(ballEntry.collideToBottomWall);
            }
        }
    }
    @Test
    void checkBallHitsBlock(){
        int level = 1;
        BallWithoutUI.BallEntry ballEntry = new BallWithoutUI.BallEntry();
        BreakWithoutUI rect = new BreakWithoutUI();
        BlockWithoutUI block = new BlockWithoutUI(1);

        while (true){
            ballEntry.setPhysicsToBall(rect, level, block.blocks);
            if (ballEntry.collideToBlock){
                assertTrue(ballEntry.collideToBlock);
                break;
            } else {
                assertFalse(ballEntry.collideToBlock);
            }
        }

    }

    @Test
    void checkIsMinusHeart(){
        BallWithoutUI.BallEntry ballEntry = new BallWithoutUI.BallEntry();
        BreakWithoutUI rect = new BreakWithoutUI();
        rect.xBreak = 0;
        int level = 1;
        ArrayList<BlockWithoutUI.BlockEntry> blocks = new ArrayList<>();

        while (true){
            ballEntry.setPhysicsToBall(rect, level, blocks);

            if (ballEntry.collideToBottomWall){
                assertTrue(ballEntry.isMinusHeart());
                break;
            }
        }
    }

}