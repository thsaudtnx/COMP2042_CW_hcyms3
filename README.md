# COMP2042_CW_hcyms3
A Readme.md file, documenting the work you've done for the
coursework, including key changes made for maintenance and extension, 
along with their locations and reasons. 
WARNING: If you do not mention it here, do not blame us later if we miss it

## 🖥️ Project Description
This coursework is about maintaining and extending a re-implementation of a classic retro
game (Brick Breaker) using JavaFX
<br>

UI Features
- Home Page
  - Title Label
  - Background Music and Wall Paper
  - Buttons like New Game, Load Game, Ranking, Instruction and Quit
- In-Game Page
  - Background Music
  - Top Bar with level, score, heart and time label
  - Components including the break, ball and block
  - Items like bonus, heart and additional balls
  - New Stage with Continue, Reset and Go Home buttons when the game is paused
- After-Game Page
  - Go Home Button
  - User input for ranking
<br>

<div style="display:flex; margin-right:10px;">
  <img alt="Alt text" src="/src/main/resources/breakGame_homePage.png" title="Optional title" width="200"/>
  <img alt="Alt text" src="/src/main/resources/breakGame_gamePage.png" title="Optional title" width="200"/>
  <img alt="Alt text" src="/src/main/resources/breakGame_menuPage.png" title="Optional title" width="200"/>
</div>
<br>

## 🕰️ Duration
* 23/10/2023 - 11/12/2023

### ⚙️ Environment
- `Java 8`
- `openjdk-21`
- **IDE** : Intellij
- **Library** : JavaFx

### 🧑‍🤝‍🧑 Compilation Instructions
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
  - Set and checks gold time of every ball
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


### 3. Features Not Implemented
Identify any features that you were unable to implement and provide a clear explanation for why they were left out.

### 4. New Java Classes
Enumerate any new Java classes that you introduced for the assignment. Include a brief description of each class's purpose and its location in the code.


### 5. Modified Java Classes
List the Java classes you modified from the provided code base. Describe the changes you made and explain why these modifications were necessary.


### 6. Unexpected Problems
Communicate any unexpected challenges or issues you encountered during the assignment. Describe how you addressed or attempted to resolve them.
