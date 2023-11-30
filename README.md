# COMP2042_CW_hcyms3

## 🖥️ Project Description
This coursework is about maintaining and extending a re-implementation of a classic retro
game (Brick Breaker)
<br>

<div>
  <div style="margin-bottom:10px">1. Home Page</div>
  <div style="margin:10px;display:flex;">
    <img alt="Alt text" src="/src/main/resources/breakGame_homePage.png" title="Optional title" width="200"/>
    <img alt="Alt text" src="/src/main/resources/breakGame_homePage_ranking.png" title="Optional title" width="200"/>
    <img alt="Alt text" src="/src/main/resources/breakGame_homePage_instruction.png" title="Optional title" width="200"/>
  </div>

  <div style="margin-bottom:10px">2. Game Page</div>
  <div style="margin:10px;display:flex;">
    <img alt="Alt text" src="/src/main/resources/breakGame_gamePage.png" title="Optional title" width="200"/>
    <img alt="Alt text" src="/src/main/resources/breakGame_gamePage_pause.png" title="Optional title" width="200"/>
  </div>

  <div style="margin-bottom:10px">3. Menu Page</div>
  <div style="margin:10px;display:flex;">
    <img alt="Alt text" src="/src/main/resources/breakGame_menuPage_lost.png" title="Optional title" width="200"/>
    <img alt="Alt text" src="/src/main/resources/breakGame_menuPage_nextLevel.png" title="Optional title" width="200"/>
    <img alt="Alt text" src="/src/main/resources/breakGame_menuPage_win.png" title="Optional title" width="200"/>
  </div>
</div>
<br>

## 🕰️ Duration
* 23/10/2023 - 11/12/2023

## ⚙️ Environment
- **Language** : `Java 21`
- **JDK** : `openjdk-21`
- **IDE** : Intellij
- **Library** : JavaFx

## 🌐 Compilation Instructions
Provide a clear, step-by-step guide on how to compile the code to produce the application. Include any dependencies or special settings required.

1. Download the zip file or clone the code into your local directory
``` bash
$ git clone https://github.com/thsaudtnx/COMP2042_CW_hcyms3.git
```
- For Github contribution
```bash
$ git init
$ git add .
$ git commit -m "commit message"
$ git push origin main
```
2. Open the folder in the IntelliJ IDE
3. In the IntelliJ > File > Project Structure > Libraries 
   add a library in Java > javafx-sdk-21.0.1 > lib
5. Run the Main Class

## 📌 Implementations
### 1. Implemented and Working Properly
List the features that have been successfully implemented and are functioning as expected. Provide a brief description of each. 
>> Game View and Controller
> 1. Home Page : the page shown first when the user runs the game 
>   - New Game Button : new game starts 
>   - Load Game Button : load game if there is a saved game
>   - Instruction Button : popup modal with instruction is shown
>   - Ranking Button : pop up modal with rankings of all the users appears. Each rankingEntry includes username, score, time and dateTime
>   - Quit Button : close the game
> 2. Game Page
>   - Top bar : level, score, heart and time are shown on the top of the game page
>   - Components : ball, block and break shown
>   - Items : bonus, heart or new ball is added when the ball hits the relevant block
>   - Pause : popup modal with continue, reset and go home buttons appears when the user press space bar
>   - Continue Button: Resume the game
>   - Reset Button: initialize the game to level 1, score 0, heart 3 and time 0
>   - Go Home Button: Go back to home page
> 3. Menu Page
>   - Complete and Game Over Page: the user can enter in username in input bar with submit button to record the ranking. Also, user can go to home page with clicking the Home Button. 
>   - Next Level Page : Next Game Button and Go Home Button are shown

>> Game Effects
> 1. Background music
>   - Different background music for home and game page
>   - Can stop and start the background music when the user pauses or continues the game
> 2. Showing
>   - Displays the countdown before starting the game
>   - Displays the points when the ball hits the block or the ground
>   - Displays the message when the break hits the heart item

>> Game Utils
> 1. Load/Save
>   - Game is saved when the user press the keyboard 's' during the game
>   - Game is loaded when the user clicks the load game button in the home page
>   - Game setting is saved in Desktop/breakGameData directory, savedData.obj file
>   - It updates the saved data when new version is saved
> 2. Ranking
>   - the ranking is recorded when the user enters in information after the game
>   - The user can check rankings by clicking ranking button on home page
>   - It is saved with Username, Score, Time and DateTime
>   - Ranking is saved in Desktop/breakGameData directory, ranking.obj file
>   - It is sorted first with score and second time

>> Game Components
> 1. Ball
>   - Sound effect when the ball hit an object
>   - Add a new ball when the ball hits the ball block
>   - Set and checks gold time of the ball
>   - Controls the movement of the ball, checks the hit to the break/block/wall
> 2. Block
>   - Initializing the blocks in every level without the blank
>   - There are 4 blocks in each column and (level + 1) rows
>   - Total 5 different blocks (normal, bonus, star, heart and ball blocks)
>   - Fill normal block with random color
>   - Special blocks (Ball, Bonus, Heart and Star blocks) can appear once in each level
>   - Check if level is completed by counting destroyed blocks
> 3. Break
>   - Move method to move the break left or right
>   - It can move til end of the wall

>> Game Items
> 1. Bonus
>   - Bonus item falls when the ball hits the choco block
>   - Add 3 points when the break hits the bonus
>   - Gold Status continues for 5 seconds when the ball hits the star block
> 2. Heart
>   - Heart item falls when the ball hits the heart block
>   - Add a life when the break hits the heart
> 3. Gold Status
>   - Gold Status continues for 5 seconds when the ball hits the star block
>   - the life is not decreased if its gold status


### 2. Implemented but Not Working Properly
List any features that have been implemented but are not working correctly. Explain the issues you encountered, and if possible, the steps you took to address them.
> - Load/Save : It works correctly when the user save the game and then load it. 
However, when the load game button is clicked without saving, the error occurs with the message
``` this.gameVariables.blockClass" is null ```.
The problem is related to initializing the Data class in the LoadSave class when it is loaded from the savedData.obj.
I tried to re-initialize the Data Class before it takes the saved data. Also, the load game button is not active if there is no saved game.
But, it could not be fixed.
<br><br>
> - Ball Speed : The speed of the ball got faster when the game is reset or when it is played many times.
It is assumed that the problem is related to initialize, start and stop method in GameEngine Class.
It appears that during repeated game executions, instances of the game engine might occasionally be initiated redundantly instead of being consistently initialized.
I tried to initialize the gameEngine before every games so that the gameEngine would not be running at the same time. But I still could not fix it. 


### 3. Features Not Implemented
Identify any features that you were unable to implement and provide a clear explanation for why they were left out.
> - Multiplayer Mode : Building a multiplayer mode fot the brick game requires one more break. Then the second user can move the second break by pressing 'wasd' keycodes.
It was planned to add 'wasd' keycodes to move the second break. However, there were new errors when the ball hits the both break at the same time.
However, I was unable to implement because it was not user-friendly controllers and also the efficiency of the game got lower.
<br><br>
> - Block formation and customization: Making a difference initialized block formation requires expandable Memory or Customization to store. 
That is, the features needs additional memory or customization such as different styles of map or the ball.  Although this could develop the user experience of the game, it was left out.


### 4. New Java Classes
Enumerate any new Java classes that you introduced for the assignment. Include a brief description of each class's purpose and its location in the code.
> - BackgroundMusic
>   - Different background music for home page and game page
>   - Located in brickGame.gameEffects.BackgroundMusic Class
<br><br>
> - Ball
>   - Controls multiple balls and the gold status
>   - Located in brickGame.gameComponents.Ball Class
<br><br>
> - Break
>   - Controls the break left or right 
>   - Located in brickGame.gameComponents.Break Class
<br><br>
> - Colour
>   - Stores the variable name for different colors
>   - Located in brickGame.gameStyle.Colour Class
<br><br>
> - Heart
>   - Represents the hearts in the game that the player can collect for extra lives.
>   - Located in brickGame.gameItems.Heart Class
<br><br>
> - Ranking
>   - Manages the ranking of game entries, retrieve the ranking, and save/load entries from/to a file.
>   - Located in brickGame.gameUtils.Ranking Class
<br><br>
> - GameController
>   - The GameController interface defines methods for controlling different pages or sections of a game.
>   - Located in brickGame.GameController Interface
<br><br>
> - GamePage
>   - The interface defines methods for drawing various pages on a JavaFX
>   - Located in brickGame.GamePage Interface
<br><br>
> - GameVariables
>   - Manages the game variables and states, such as the ball, break, blocks, bonus, heart, and various game-related parameters.
>   - Located in brickGame.GameVariables Class
<br><br>
> - GameView
>   - Implements the Page interface and represents the view of the game, including the home page, game page, and menu pages.
>   - Located in brickGame.GameView Class


### 5. Modified Java Classes
List the Java classes you modified from the provided code base. Describe the changes you made and explain why these modifications were necessary.
> - Game Engine
>   - Deleted Initialize method because it could be made with initializing game variables in the game logic side.
>   - Deleted PhysicsCalculation method since the whole game could be easily controlled by onUpdate method and for the efficiency matter. 
>   - Created stop/go method for time variable as for the pause function for the middle of the game
<br><br>
> - Ball
>   - After creating Ball Class, the codes related to ball in Main Class were moved into the Ball Class
>   - The codes include the properties of the ball, isGoldStatus, initBall method, all the resetCollideFlags and setPhysicsToBall method
>   - Created BallEntry list so that it can store multiple balls.
>   - Maintained goldTime / isGoldStatus static variable to check the ball's status
>   - Created ballSound and its path to add sound when the ball hits an object using MediaPlayer library
>   - Created checkGoldTimeOver and setGoldTime method and moved the related codes inside for checking gold time purpose.
>   - Created addBall method to add new Ball in the ballEntry list
>   - Created BallEntry inner class for controlling each ball
>   - Moved properties of the ball like ballRadius, xBall, yBall, vX, vY, goDownBall and goRightBall is in BallEntry Class
>   - Deleted all the resetCollideFlags except collideToBlock, collideToBottomWall and collideBlock because those deleted variables have no need to be used outside the class
>   - Moved setPhysicsToBall in ballEntry class for controlling each different ball
>   - Modified setPhysicsToBall to interact with its movement, collisions with wall, block and break and collideBlock field is added to tell which block is hit
>   - Created isMinusHeart method in ballEntry class to check the life decrease
<br><br>
> - Block
>   - Maintained the original Block Class but, I changed the usage of it not for the single block 
>   - Deleted BlockSerializable Class because it is defined in BlockEntry class
>   - Moved fields and method related to Block from the Main to Block class like block array and initBoard
>   - Created BlockEntry inner class to define single Block
>   - Moved fields representing single block into BlockEntry Class like row, column, x, y, color, type and isDestroyed.
>   - Deleted fields like NO_HIT, HIT_RIGHT, HIT_BOTTOM, HIT_LEFT and HIT_TOP because it could be handled in Ball Class for the movement afterward
>   - Maintained BlockType fields like BLOCK_NORMAL, BLOCK_CHOCO, BLOCK_STAR, BLOCK_HEART and BLOCK_BALL
>   - Moved draw method in the BlockEntry class for drawing single block
>   - Deleted checkHitToBlock method since it can be handled in Ball class side - collideBlock field
>   - Deleted getter method because changed paddingTop, paddingWidth, height and width field to static for sharing anywhere
<br><br>
> - Break
>   - After creating Break Class and moved related code from the Main class to Break class
>   - Moved fields like breakX, breakY, centerBreakX and halfBreak and methods like move to Break class for single break
>   - Deleted fields like oldBreakX, LEFT and RIGHT since it is useless
<br><br>
> - Show
>   - Modified the naming of the class from Score to Show as the purpose of it was showing things like score and messages.
>   - Deleted showWin and showGameOver method because it would be displayed in the Menu Page.
>   - Modified the naming from show to showScore as it is showing score
>   - Create showCountDown method for counting 3 seconds before the game instances move
<br><br>
> - Bonus
>   - Maintained the original Bonus class, but its usage is not for single bonus
>   - Created BonusEntry inner class for single bonus
>   - Moved related codes from original Bonus class into BonusEntry class for representing each bonus
>   - Created BonusEntry list in the Bonus class to store multiple bonuses
>   - Created addBonus method to add new BonusEntry
<br><br>
> - LoadSave
>   - Deleted all the fields in the original LoadSave class
>   - Created Data(Serializable) inner class to store game variables with class units for the convenience of the saving and loading data
>   - Moved Load method from the Main class to LoadSave class
>   - Created isExistSavedFile method to check whether there is existing file
<br><br>
> - Main
>   - Moved all the game logics, view, engine and variables from Main class to Game Class
>   - Main class just make Game instance and start to optimize the Main class


### 6. Unexpected Problems
Communicate any unexpected challenges or issues you encountered during the assignment. Describe how you addressed or attempted to resolve them.
> - Changing Application UI : The UI of the game did not reflect the change correctly. 
This mostly occurred when promoting to the next level, ball hitting the block and changing the stages in the short time.
I thought the usage of the code "platform.runLater()" can handle this problem. I found out that
implementing UI operations, such as creating or modifying UI elements, should be performed on the JavaFX Application Thread. 
Tasks that take a significant amount of time should not be executed on the JavaFX Application Thread to prevent UI freezing.
That is, when a background thread needs to update the UI, use Platform.runLater() to schedule the UI update on the JavaFX Application Thread.
<br><br>
> - Dealing with threads : The main problem occurred when the game stops at unexpected moment. It was assumed that the update and physic thread in the GameEngine class was not working properly.
First, I moved the codes in physics method in Main class to update method. Then I deleted the physics method with physics thread in GameEngine class. It was mainly because multiple threads can lower the efficiency in this case. 
Also, it was intuitive to control the thread correctly and by adding stop/go method for the thread was better.
Therefore, the thread won't be terminated but will stop before the user wants to continue.

## 📃 MARKING CRITERIA/RUBRIC
Assessment Criteria and Marking Overview Tasks
> 1. Git
>   - Project exists : https://github.com/thsaudtnx/COMP2042_CW_hcyms3
>   - Commits : More than 40 commits
>   - Regularity : Check by clicking "commits" in the repo
>   - Commit messages : Check the messages by clicking "commits" in the repo
>   - Branching/merging : Have used main branch 
>   - Meaningful .gitignore : Added ranking.obj for preventing pushing the ranking file
> 2. Refactoring
>   - Meaningful package naming/organisation : Check the source code
>   - Basic maintenance (e.g. renaming classes; encapsulation; deleting unused resources)
>   - Supporting single responsibility by splitting up classes
>   - MVC : Created GameView, GameController and Game Class for logic
>   - Other patterns : Observer Pattern (setOnAction), Strategy Pattern (Game class) and Factory patterns(Ball, Block, Bonus, Heart and Ranking class) 
>   - Meaningful JUnit tests :src/test/java/brickGame.gameComponent
>   - Correct use of build tools (Maven or Gradle) : Automatically built in IntelliJ
>   - Complete translation from Swing to JavaFX : No need
>   - module-info.java file (compulsory) : Automatically built in IntelliJ
> 3. Additions
>   - Additional (playable) levels : Decreased level from 18 to 10, different block formation and speed of the ball on each level
>   - Anything else exciting (reward) or bad (penalty) : Add items like heart(+1 life), bonus(+3 points) and new ball(reward - finish faster and penalty - hit bottom wall then -1 life)
> 4. Documentation
>   - Readme.md: highlighting the key changes (maintenance + extensions) + where + why : This Readme file
>   - Javadocs: Created (and deposited in the correct location) : Created in Javadoc directory
>   - Javadocs: New ones added (half marks if only comments added)
>   - Javadocs: Complete (half marks for substantial amount) : Fully created
>   - Javadocs: Informative and concise : Added sufficient javadoc comments with the code
>   - Class diagram: Something meaningful present : Can understand the structure of the game easily with it
>   - Class diagram: High level : Put only the classes or interfaces to be the high level of class diagram
>   - Class diagram: Conforms with code (of the final program) : Everything conforms with code
> 5. Demonstration video
>   - Showing software running : Shown in the video
>   - Explaining refactoring activities and extensions : Shown in the video
>   - Highlighted two achievements most proud of : Ranking system and pause & continue function
>   - Timing (approx. 3 minutes) : Below 3 minutes

