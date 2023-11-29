package brickGame.GameComponents;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BlockTest {

    @Test
    void checkInitializeBlock(){
        int level = 1;
        BlockWithoutUI block = new BlockWithoutUI(level);

        assertEquals(block.destroyedBlockCount, 0);
        assertEquals(block.blocks.size(), (level+1) * 5);
    }

    @Test
    void checkClearLevel() {
        int level = 1;
        BallWithoutUI.BallEntry ballEntry = new BallWithoutUI.BallEntry();
        BreakWithoutUI rect = new BreakWithoutUI();
        BlockWithoutUI block = new BlockWithoutUI(level);

        block.destroyedBlockCount = block.blocks.size();
        assertTrue(block.checkClearLevel());

        block.destroyedBlockCount = 1;
        assertFalse(block.checkClearLevel());
    }
}