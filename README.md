# COMP2042_CW_hcyms3
A Readme.md file, documenting the work you've done for the
coursework, including key changes made for maintenance and extension, 
along with their locations and reasons. 
WARNING: If you do not mention it here, do not blame us later if we miss it

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
- `Java 8`
- `openjdk-21`
- **IDE** : Intellij
- **Library** : JavaFx

## 🌐 Compilation Instructions
Provide a clear, step-by-step guide on how to compile the code to produce the application. Include any dependencies or special settings required.

1. Download the zip file or clone the code into your local directory
``` bash
$ git clone https://github.com/thsaudtnx/COMP2042_CW_hcyms3.git
```
2. Open the folder in the IntelliJ IDE
3. In the IntelliJ > File > Project Structure > Libraries
4. Add a library > Java > javafx-sdk-21.0.1 > lib, then apply
5. Run the Main Class 

## 📌 Implementations
### 1. Implemented and Working Properly
List the features that have been successfully implemented and are functioning as expected. Provide a brief description of each. 
> - Background music
>   - Different background music for home and game page
>   - Can stop and start the background music when the user pause or continue the game
> - Ball
>   - Sound effect when the ball hit an object
>   - Add a new ball when the ball hits the ball block
>   - Set and checks gold time of the ball
>   - Controls the movement of the ball, checks the hit to the break/block/wall
> - Block
>   - Initializing the blocks in every level without the blank
>   - Counts destroyed blocks to check if the level is completed
>   - Have different types (normal, choco, star, heart and ball block)
>   - Fill normal block with random color
> - Bonus / Heart
>   - Bonus falls when the ball hits the choco block
>   - heart falls when the ball hits the heart block
>   - Add 3 points when the break hits the bonus
>   - Add a life when the break hits the heart
>   - Both can have multiples
> - Break
>   - Move method to move the break left or right
>   - It cannot move if it hits the wall
> - Load/Save
>   - Game is saved when the user press the keyboard 's' during the game
>   - Game is loaded when the user clicks the load game button in the home page
>   - Game setting is saved in Desktop/breakGameData directory, savedData.obj file
>   - It updates the saved data when new version is saved
> - Ranking
>   - the information is saved in the ranking list when the user enters in information after the game
>   - The user can check rankings by clicking ranking button on home page
>   - It is saved with Username, Score, Time, DateTime
>   - Ranking is saved in Desktop/breakGameData directory, ranking.obj file
>   - It is sorted first with score and second time
> - Score
>   - Displays the countdown before starting the game
>   - Displays the points when the ball hits the block or the ground
>   - Displays the message when the break hits the heart item
> - Pause / Reset / Go Home
>   - The game is paused when the user press the space bar
>   - When the game is paused, the modal appears with 3 buttons(continue, reset and go home)
> - Instruction modal
>   - The user can see the instruction when they press the instruction button on home
> - Level / Score / Heart / Time
>   - These 4 variables appear on the top in the game


### 2. Implemented but Not Working Properly
List any features that have been implemented but are not working correctly. Explain the issues you encountered, and if possible, the steps you took to address them.
> - Load/Save : It works correctly when the user save the game and then load it. 
However, when the user first clicks the load game button without saving the game, the error occurs.
It is assumed that the problem is related to initializing the Data class in the LoadSave class.
<br><br>
> - Ball Speed : The speed of the ball is uncontrollable. Sometimes it becomes faster and sometimes vise-versa.
It is assumed that the problem is also related to initializing the ball class and well as the place where the break hits the ball 

### 3. Features Not Implemented
Identify any features that you were unable to implement and provide a clear explanation for why they were left out.
> - Multiplayer Mode : The reason for making the separate break class was to add one more break.
It was planned to add 'wasd' keycodes to move the second break. However, there were new errors when the ball hits the both break at the same time.
Also, the pace of the game got slow due to the efficiency problem
<br><br>
> - Expandable Memory or Customization: Brick games are often standalone devices with fixed software. 
Implementing features that require expandable memory or customization such as different styles of map or the ball requires more memory and files.
Therefore, due to the lack of such capabilities, it was left out.

### 4. New Java Classes
Enumerate any new Java classes that you introduced for the assignment. Include a brief description of each class's purpose and its location in the code.
> - BackgroundMusic
>   - Different background music for home page and game page
>   - Can stop and start the background music
>   - Located in Background Music Class
>   - Used Javafx Media library
<br><br>
> - Ball
>   - Located in Ball Class
>   - Used Javafx Media library for ball hit sound
>   - Have ballEntry type array for multiple balls
>   - Set and checks gold time of the ball
>   - Have ballEntry inner class for set physics to each ball
>   - BallEntry class implements serializable for file system
>   - setPhysics method controls the movement of the ball, checks the hit to the break/block/wall
<br><br>
> - Break
>   - Located in Break Class
>   - Used keycode library to checks the keyCode that user press
>   - Move method to move the break left or right / cannot move if hits the wall
<br><br>
> - Colour
>   - Located in Colour Class
>   - Stores the variable for different colors
<br><br>
> - Heart
>   - Located in Heart Class
>   - Modified that life is added if the ball hits the heart block and the break hits the falling heart item instead of just hitting the heart block
>   - Have heartEntry type array for storing multiple hearts
>   - HeartEntry inner class is implementing serializable for the file system
<br><br>
> - Ranking
>   - Located in Ranking Class
>   - Every end of the level, the information is saved in the ranking list
>   - The user can check rankings by clicking ranking button on home page
>   - It is saved with Username, Score, Time, DateTime
>   - Ranking is saved in Desktop/breakGameData directory, ranking.obj file
>   - It is sorted first with score and then time

### 5. Modified Java Classes
List the Java classes you modified from the provided code base. Describe the changes you made and explain why these modifications were necessary.
> - Game Engine
>   - Located in GameEngine Class
>   - Deleted Initialize method because it is more efficient that the game variables are only initialized in the Main
>   - Deleted PhysicsCalculation method since the whole game could be controlled by single thread. Otherwise, it is vulnerable to control the race conditions of the thread. Also for the better efficiency of the game
>   - Created stop/go method for time variable. This is for the pause function for the middle of the game

### 6. Unexpected Problems
Communicate any unexpected challenges or issues you encountered during the assignment. Describe how you addressed or attempted to resolve them.
> - Application UI : The UI of the game did not reflect the change correctly. This mostly occurred when promoting to the next level, ball hitting the block and so on.
I thought the usage of the code "platform.runLater()" can handle this problem. I found out that
implementing UI operations, such as creating or modifying UI elements, should be performed on the JavaFX Application Thread. 
Tasks that take a significant amount of time should not be executed on the JavaFX Application Thread to prevent UI freezing.
That is, when a background thread needs to update the UI, use Platform.runLater() to schedule the UI update on the JavaFX Application Thread.
<br><br>
> - GameEngine Thread : The main problem occurred when the game stops at unexpected moment. It was assumed that the update and physic thread in the GameEngine class was not working properly.
First, I moved the codes in physics method in Main class to update method. Then I deleted the physics method with physics thread in GameEngine class. It was mainly because multiple threads can lower the efficiency in this case. 
Also, it was intuitive to control the thread correctly and by adding stop/go method for the thread was better.
Therefore, the thread won't be terminated but will stop before the user wants to continue.
