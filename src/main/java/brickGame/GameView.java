package brickGame;

import brickGame.gameEffects.BackgroundMusic;
import brickGame.gameUtils.Ranking;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * The {@code GameView} class implements the {@code Page} interface and represents
 * the view of the game, including the home page, game page, and menu pages.
 */
public class GameView implements GamePage {
    final public int sceneWidth = 500;
    final public int sceneHeight = 700;
    BackgroundMusic backgroundMusic;
    public Pane root;
    public Scene scene;
    public Label titleLabel;
    public Button newGameButton;
    public Button loadGameButton;
    public Button instructionButton;
    public Button rankingButton;
    public Button quitButton;
    public Label scoreLabel;
    public Label heartLabel;
    public Label levelLabel;
    public Label timeLabel;
    public Button submitButton;
    public TextField usernameField;
    public Image titleImage;
    public Button homeButton;
    public Button nextGameButton;
    /**
     * Draws the home page of the game.
     *
     * @param primaryStage The primary stage of the JavaFX application.
     */
    @Override
    public void drawHomePage(Stage primaryStage){
        //Set Label
        titleLabel = new Label();
        Image titleImage = new Image("brickGameTitle.png");
        ImageView titleImageView = new ImageView(titleImage);
        titleImageView.setFitWidth(titleImage.getWidth() * 0.5);
        titleImageView.setFitHeight(titleImage.getHeight() * 0.5);
        titleLabel.setGraphic(titleImageView);
        titleLabel.setTranslateX(120);
        titleLabel.setTranslateY(120);
        titleLabel.setVisible(true);

        //Set Buttons
        newGameButton = new Button();
        loadGameButton = new Button();
        rankingButton = new Button();
        quitButton = new Button();
        instructionButton = new Button();
        Image newGameImage = new Image("newGameButton.png");
        Image loadGameImage = new Image("loadGameButton.png");
        Image rankingImage = new Image("rankingButton.png");
        Image quitImage = new Image("quitButton.png");
        Image instructionImage = new Image("instructionButton.png");
        ImageView newGameImageView = new ImageView(newGameImage);
        ImageView loadGameImageView = new ImageView(loadGameImage);
        ImageView rankingImageView = new ImageView(rankingImage);
        ImageView quitImageView = new ImageView(quitImage);
        ImageView instructionImageView = new ImageView(instructionImage);
        newGameImageView.setFitWidth(newGameImage.getWidth() * 0.3);
        newGameImageView.setFitHeight(newGameImage.getHeight() * 0.3);
        loadGameImageView.setFitWidth(loadGameImage.getWidth() * 0.3);
        loadGameImageView.setFitHeight(loadGameImage.getHeight() * 0.3);
        rankingImageView.setFitWidth(rankingImage.getWidth() * 0.3);
        rankingImageView.setFitHeight(rankingImage.getHeight() * 0.3);
        quitImageView.setFitWidth(quitImage.getWidth() * 0.3);
        quitImageView.setFitHeight(quitImage.getHeight() * 0.3);
        instructionImageView.setFitWidth(instructionImage.getWidth() * 0.3);
        instructionImageView.setFitHeight(instructionImage.getHeight() * 0.3);
        newGameButton.setGraphic(newGameImageView);
        loadGameButton.setGraphic(loadGameImageView);
        rankingButton.setGraphic(rankingImageView);
        instructionButton.setGraphic(instructionImageView);
        quitButton.setGraphic(quitImageView);
        newGameButton.setTranslateX(170);
        newGameButton.setTranslateY(300);
        loadGameButton.setTranslateX(170);
        loadGameButton.setTranslateY(360);
        rankingButton.setTranslateX(170);
        rankingButton.setTranslateY(420);
        instructionButton.setTranslateX(170);
        instructionButton.setTranslateY(480);
        quitButton.setTranslateX(185);
        quitButton.setTranslateY(540);

        rankingButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Ranking ranking = new Ranking();
                System.out.println(ranking.getFormattedRanking());
                //Set popup stage
                Stage popupStage = new Stage();
                popupStage.initModality(Modality.APPLICATION_MODAL);
                popupStage.setResizable(false);
                popupStage.setTitle("Ranking");
                popupStage.setX(600);  // Set X coordinate
                popupStage.setY(200);  // Set Y coordinate

                Platform.runLater(() -> {
                    // Design the content of the popup (a simple example with a label)
                    VBox popupLayout = new VBox();
                    popupLayout.setSpacing(10);
                    Label headLabel = new Label("Ranking\tUsername\tScore\tRecord\tDateTime");
                    Label rankingLabel = new Label(ranking.getFormattedRanking());
                    popupLayout.getChildren().addAll(headLabel, rankingLabel);

                    //Set a popup scene
                    Scene popupScene = new Scene(popupLayout, 300, 200);
                    popupStage.setScene(popupScene);

                    // Show the popup
                    popupStage.show();
                });
            }
        });
        /**
        loadGameButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (LoadSave.isExistSavedFile()){
                    try {
                        backgroundMusic.stop();
                        gameVariables.page = 1;
                        gameVariables.isLoad = true;
                        start(primaryStage);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    //There is no saved file
                }
            }
        });
        newGameButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    backgroundMusic.stop();
                    gameVariables.page = 1;
                    gameVariables.level=1;
                    start(primaryStage);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
         **/
        instructionButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                System.out.println("instruction");
                //Set the pop up stage
                Stage popupStage = new Stage();
                popupStage.initModality(Modality.APPLICATION_MODAL);
                popupStage.setResizable(false);
                popupStage.setTitle("Instruction");
                popupStage.setX(630);  // Set X coordinate
                popupStage.setY(300);  // Set Y coordinate

                Platform.runLater(() -> {
                    // Design the content of the popup (a simple example with a label)
                    Pane popupLayout = new Pane();
                    BackgroundImage background = new BackgroundImage(
                            new Image("instructionPage.png"),
                            BackgroundRepeat.NO_REPEAT,
                            BackgroundRepeat.NO_REPEAT,
                            BackgroundPosition.CENTER,
                            new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, true, false)
                    );
                    popupLayout.setBackground(new Background(background));

                    //Set a popup scene
                    Scene popupScene = new Scene(popupLayout, 300, 200);
                    popupStage.setScene(popupScene);

                    // Show the popup
                    popupStage.show();
                });

            }
        });
        quitButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                primaryStage.close();
            }
        });

        //Set the root pane
        root = new Pane();
        BackgroundImage background = new BackgroundImage(
                new Image("spaceBg.jpg"),
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT,
                new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, true, false)
        );
        root.setBackground(new Background(background));

        //Set background music
        backgroundMusic = new BackgroundMusic(0);
        backgroundMusic.start();

        //Draw UI Components
        Platform.runLater(() -> {
            //Add components into root
            root.getChildren().addAll(
                    titleLabel,
                    newGameButton,
                    loadGameButton,
                    rankingButton,
                    instructionButton,
                    quitButton
            );
            //Set the main scene
            scene = new Scene(root, sceneWidth, sceneHeight);
            //scene.getStylesheets().add("style.css");

            //Set the primary stage
            primaryStage.setTitle("Brick Game");
            primaryStage.setScene(scene);
            primaryStage.setResizable(false);
            primaryStage.show();
        });
    }
    /**
     * Draws the game page with the game elements.
     *
     * @param primaryStage The primary stage of the JavaFX application.
     */
    @Override
    public void drawGamePage(Stage primaryStage){
        //Set Labels
        scoreLabel = new Label("Score: ");
        levelLabel = new Label("Level ");
        heartLabel = new Label("Heart : ");
        timeLabel = new Label("Time : ");
        levelLabel.setTranslateX(5);
        scoreLabel.setTranslateX(sceneWidth / 4);
        timeLabel.setTranslateX(sceneWidth / 2);
        heartLabel.setTranslateX(sceneWidth*3 / 4);

        //Set the root pane
        root = new Pane();
        BackgroundImage background = new BackgroundImage(
                new Image("bg.jpg"),
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT,
                new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, true, false)
        );
        root.setBackground(new Background(background));

        //Set background music
        backgroundMusic = new BackgroundMusic(1);
        backgroundMusic.start();

        Platform.runLater(() -> {
            root.getChildren().addAll(
                    scoreLabel,
                    heartLabel,
                    levelLabel,
                    timeLabel
            );

            //Set the main scene
            scene = new Scene(root, sceneWidth, sceneHeight);
            scene.getStylesheets().add("style.css");

            //Set the primary stage
            primaryStage.setTitle("Brick Game");
            primaryStage.setScene(scene);
            primaryStage.setResizable(false);
            primaryStage.show();
        });
    }
    /**
     * Draws the end menu page after completing a level.
     *
     * @param primaryStage The primary stage of the JavaFX application.
     */
    @Override
    public void drawMenuPageEnd(Stage primaryStage){
        //Set Label
        titleLabel = new Label();
        scoreLabel = new Label("Score : ");
        timeLabel = new Label("Time : ");
        scoreLabel.setTextFill(Color.WHITE);
        timeLabel.setTextFill(Color.WHITE);
        scoreLabel.setStyle("-fx-font-size: 24px;");
        timeLabel.setStyle("-fx-font-size: 24px;");
        titleImage = new Image("gameOver.png");
        ImageView titleImageView = new ImageView(titleImage);
        titleImageView.setFitWidth(titleImage.getWidth() * 0.5);
        titleImageView.setFitHeight(titleImage.getHeight() * 0.5);
        titleLabel.setGraphic(titleImageView);
        titleLabel.setTranslateX(120);
        titleLabel.setTranslateY(120);
        scoreLabel.setTranslateX(200);
        scoreLabel.setTranslateY(300);
        timeLabel.setTranslateX(200);
        timeLabel.setTranslateY(350);

        // Create a TextField for username input
        usernameField = new TextField();
        usernameField.setPromptText("Enter your username"); // Set a prompt text

        // Create a Button to submit the username
        submitButton = new Button("Submit");

        HBox inputLayout = new HBox(usernameField, submitButton);
        inputLayout.setSpacing(10);
        inputLayout.setTranslateX(170);
        inputLayout.setTranslateY(400);

        //set buttons
        homeButton = new Button();
        Image homeImage = new Image("homeButton.png");
        ImageView homeImageView = new ImageView(homeImage);
        homeImageView.setFitWidth(homeImage.getWidth() * 0.3);
        homeImageView.setFitHeight(homeImage.getHeight() * 0.3);
        homeButton.setGraphic(homeImageView);
        homeButton.setTranslateX(170);
        homeButton.setTranslateY(450);
        homeButton.setVisible(true);

        //Set the root pane
        root = new Pane();
        root.setStyle("-fx-background-color: black;");
        Platform.runLater(() -> {
            root.getChildren().addAll(
                    titleLabel,
                    scoreLabel,
                    timeLabel,
                    inputLayout,
                    homeButton
            );

            //Set the main scene
            scene = new Scene(root, sceneWidth, sceneHeight);
            //scene.getStylesheets().add("style.css");

            //Set the primary stage
            primaryStage.setTitle("Brick Game");
            primaryStage.setScene(scene);
            primaryStage.setResizable(false);
            primaryStage.show();
        });

        //Turn off background music
        backgroundMusic.stop();
    }
    /**
     * Draws the menu page after completing a level and proceeding to the next level.
     *
     * @param primaryStage The primary stage of the JavaFX application.
     */
    @Override
    public void drawMenuPageNextLevel(Stage primaryStage){
        //Set Label
        Label titleLabel = new Label();
        Image titleImage = new Image("levelUp.png");
        ImageView titleImageView = new ImageView(titleImage);
        titleImageView.setFitWidth(titleImage.getWidth() * 0.5);
        titleImageView.setFitHeight(titleImage.getHeight() * 0.5);
        titleLabel.setGraphic(titleImageView);
        titleLabel.setTranslateX(120);
        titleLabel.setTranslateY(120);
        titleLabel.setVisible(true);

        //set buttons
        nextGameButton = new Button();
        homeButton = new Button();
        Image nextGameImage = new Image("nextLevelButton.png");
        Image homeImage = new Image("homeButton.png");
        ImageView nextGameImageView = new ImageView(nextGameImage);
        ImageView homeImageView = new ImageView(homeImage);
        nextGameImageView.setFitWidth(nextGameImage.getWidth() * 0.3);
        nextGameImageView.setFitHeight(nextGameImage.getHeight() * 0.3);
        homeImageView.setFitWidth(homeImage.getWidth() * 0.3);
        homeImageView.setFitHeight(homeImage.getHeight() * 0.3);
        nextGameButton.setGraphic(nextGameImageView);
        homeButton.setGraphic(homeImageView);
        nextGameButton.setTranslateX(170);
        nextGameButton.setTranslateY(300);
        homeButton.setTranslateX(170);
        homeButton.setTranslateY(360);

        //Set the root pane
        root = new Pane();
        root.setStyle("-fx-background-color: black;");
        Platform.runLater(() -> {
            root.getChildren().addAll(
                    titleLabel,
                    nextGameButton,
                    homeButton
            );

            //Set the main scene
            scene = new Scene(root, sceneWidth, sceneHeight);
            //scene.getStylesheets().add("style.css");

            //Set the primary stage
            primaryStage.setTitle("Brick Game");
            primaryStage.setScene(scene);
            primaryStage.setResizable(false);
            primaryStage.show();
        });

        //Turn off background music
        backgroundMusic.stop();
    }
}
