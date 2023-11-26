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
2. Open the folder in the IDE (Intellij Recommended)
3. Set the jdk for the java project (I have used openjdk-21)
4. Run the Main Class 

## 📌 Implementations
### 1. Implemented and Working Properly
List the features that have been successfully implemented and are functioning as expected. Provide a brief description of each. 
- Background music
  - Different background music for home page and game page
  - Can stop and start the background music
  - Located in Background Music Class
  - Used Javafx Media library
- Ball
  - Located in Ball Class
  - Used Javafx Media library for ball hit sound
  - Have ballEntry type array for multiple balls
  - Set and checks gold time of the ball
  - Have ballEntry inner class for set physics to each ball
  - BallEntry class implements serializable for file system
  - setPhysics method controls the movement of the ball, checks the hit to the break/block/wall
- Block
  - Located in Block Class
  - Used Random Library for initializing the blocks
  - Have blockEntry type array for storing the blocks
  - Counts destroyed blocks to check if the level is completed
  - Have blockEntry inner class for each block
- Bonus
  - Located in Bonus Class
  - Used Random Library for initializing the bonuses
  - Have bonusEntry type array for storing multiple bonuses
  - Have bonusEntry inner class for each bonus
- Break
  - Located in Break Class
  - Used keycode library to checks the keyCode that user press
  - Move method to move the break left or right / cannot move if hits the wall
- Colour
  - Located in Colour Class
  - Stores the variable for different colors
- Game Engine
  - Located in GameEngine Class
  - Deleted Initialize method because it is more efficient that the game variables are only initialized in the Main
  - Deleted PhysicsCalculation method since the whole game could be controlled by single thread. Otherwise, it is vulnerable to control the race conditions of the thread. Also for the better efficiency of the game
  - Created stop/go method for time variable. This is for the pause function for the middle of the game
- Heart
  - Located in Heart Class
  - Modified that life is added if the ball hits the heart block and the break hits the falling heart item instead of just hitting the heart block
  - Have heartEntry type array for storing multiple hearts
  - HeartEntry inner class is implementing serializable for the file system
- Load/Save
  - Located in LoadSave Class
  - Game is saved when the user press the keyboard 's' during the game
  - Game is loaded when the user clicks the load game button in the home page
  - Data inner class implements the serializable for the file system
  - All the settings for the game is saved and loaded using the Data class
  - Setting contain level, score, time, life, blockClass, heartClass, bonusClass, ballClass and breakClass
  - Game setting is saved in Desktop/breakGameData directory, savedData.obj file
  - It updates the saved data when new version is saved
- Ranking
  - Located in Ranking Class
  - Every end of the level, the information is saved in the ranking list
  - The user can check rankings by clicking ranking button on home page
  - It is saved with Username, Score, Time, DateTime
  - Ranking is saved in Desktop/breakGameData directory, ranking.obj file
  - It is sorted first with score and then time
- Score
  - Located in Score Class
  - Displays the countdown before starting the game
  - Displays the points when the ball hits the block or the ground
  - Displays the message when the break hits the heart item

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
>  - Different background music for home page and game page
>  - Can stop and start the background music
>  - Located in Background Music Class
>  - Used Javafx Media library
<br><br>
> - Ball
>  - Located in Ball Class
>  - Used Javafx Media library for ball hit sound
>  - Have ballEntry type array for multiple balls
>  - Set and checks gold time of the ball
>  - Have ballEntry inner class for set physics to each ball
>  - BallEntry class implements serializable for file system
>  - setPhysics method controls the movement of the ball, checks the hit to the break/block/wall
<br><br>
> - Break
>  - Located in Break Class
>  - Used keycode library to checks the keyCode that user press
>  - Move method to move the break left or right / cannot move if hits the wall
<br><br>
> - Colour
>  - Located in Colour Class
>  - Stores the variable for different colors
<br><br>
> - Heart
>  - Located in Heart Class
>  - Modified that life is added if the ball hits the heart block and the break hits the falling heart item instead of just hitting the heart block
>  - Have heartEntry type array for storing multiple hearts
>  - HeartEntry inner class is implementing serializable for the file system
<br><br>
> - Ranking
>  - Located in Ranking Class
>  - Every end of the level, the information is saved in the ranking list
>  - The user can check rankings by clicking ranking button on home page
>  - It is saved with Username, Score, Time, DateTime
>  - Ranking is saved in Desktop/breakGameData directory, ranking.obj file
>  - It is sorted first with score and then time

### 5. Modified Java Classes
List the Java classes you modified from the provided code base. Describe the changes you made and explain why these modifications were necessary.
> - Game Engine
>  - Located in GameEngine Class
>  - Deleted Initialize method because it is more efficient that the game variables are only initialized in the Main
>  - Deleted PhysicsCalculation method since the whole game could be controlled by single thread. Otherwise, it is vulnerable to control the race conditions of the thread. Also for the better efficiency of the game
>  - Created stop/go method for time variable. This is for the pause function for the middle of the game

### 6. Unexpected Problems
Communicate any unexpected challenges or issues you encountered during the assignment. Describe how you addressed or attempted to resolve them.
> - Application UI : The UI of the game did not reflect the change correctly. This mostly occurs when promoting to the next level, ball hitting the block and so forth.
I thought the usage of the platform.runLater function can solve it. I found out that
implementing UI operations, such as creating or modifying UI elements, should be performed on the JavaFX Application Thread. 
The code should be written to avoid time-consuming tasks on the application thread. Tasks that take a significant amount of time should not be executed on the JavaFX Application Thread to prevent UI freezing.
That is when a background thread needs to update the UI, use Platform.runLater() to schedule the UI update on the JavaFX Application Thread.
<br><br>
> - GameEngine Thread : The main problem occurs when the game stops at unexpected moment. It was assumed that the problem was originated from the update and physic thread in the GameEngine class.
I have deleted physics thread to control the thread correctly and added stop/go method for the thread.
Therefore, the thread won't be terminated but will stop before the user wants to continue.
