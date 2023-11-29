package brickGame;

import brickGame.GameComponents.*;
/**
 * The GameVariables class manages the game variables and states, such as the ball,
 * break, blocks, bonus, heart, and various game-related parameters.
 */
public class GameVariables {
    public Ball ballClass;
    public Break breakClass;
    public Block blockClass;
    public Bonus bonusClass;
    public Heart heartClass;
    public boolean isLoad;

    public int page = 0; //0 home, 1 inGame, 2 after game
    public int level = 1;
    public int heart = 3;
    public int  score = 0;
    public long time = 0;
    /**
     * Initializes the game variables and instances.
     */
    public void onInit(){
        //init ball
        ballClass = new Ball();
        //init break
        breakClass = new Break();
        //init blocks
        blockClass = new Block(level);
        //init bonus
        bonusClass = new Bonus();
        //init heartItem
        heartClass = new Heart();
    }

}
