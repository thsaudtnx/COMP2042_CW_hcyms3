package brickGame.utils;

import brickGame.GameEngine;
import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;

/**
 * The Show class provides methods to display countdown, score, and messages on a JavaFX Pane.
 *
 */
public class Show {
    /**
     * Displays a countdown on the specified Pane and starts the game when the countdown is complete.
     *
     * @param pane   The JavaFX Pane to display the countdown on.
     * @param engine The GameEngine associated with the game.
     *
     */
    public void showCountDown(Pane pane, GameEngine engine){
        Label label = new Label();
        label.setTranslateX(250);
        label.setTranslateY(300);
        label.setVisible(true);
        Platform.runLater(() -> {
            pane.getChildren().add(label);
        });

        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i=3;i>0;i--){
                    for (int j= 0; j < 21; j++) {
                        final int scaleValue = j;
                        final double opacityValue = (20 - j) / 20.0;
                        try {
                            int finalI = i;
                            Platform.runLater(() -> {
                                label.setText(finalI + "");
                                label.setScaleX(scaleValue);
                                label.setScaleY(scaleValue);
                                label.setOpacity(opacityValue);
                            });
                            Thread.sleep(15);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e){
                        e.printStackTrace();
                    }
                }
                engine.start();
            }
        }).start();
    }

    /**
     * Displays the score on the specified coordinates of the Pane.
     *
     * @param x     The x-coordinate for displaying the score.
     * @param y     The y-coordinate for displaying the score.
     * @param score The score to be displayed.
     * @param pane  The JavaFX Pane to display the score on.
     *
     */
    public void showScore(final double x, final double y, int score, Pane pane) {
        String sign;
        if (score >= 0) {
            sign = "+";
        } else {
            sign = "";
        }
        final Label label = new Label(sign + score);
        label.setTranslateX(x);
        label.setTranslateY(y);
        label.setVisible(true);
        Platform.runLater(() -> {
            pane.getChildren().add(label);
        });

        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 21; i++) {
                    final int scaleValue = i;
                    final double opacityValue = (20 - i) / 20.0;
                    try {
                        Platform.runLater(() -> {
                            label.setScaleX(scaleValue);
                            label.setScaleY(scaleValue);
                            label.setOpacity(opacityValue);
                        });
                        Thread.sleep(15);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    /**
     * Displays a message on the specified Pane.
     *
     * @param message The message to be displayed.
     * @param pane    The JavaFX Pane to display the message on.
     */
    public void showMessage(String message, final Pane pane) {
        final Label label = new Label(message);
        label.setTranslateX(220);
        label.setTranslateY(340);

        Platform.runLater(() -> {
            pane.getChildren().add(label);
        });

        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 21; i++) {
                    final int scaleValue = Math.abs(i-10);
                    final double opacityValue = (20 - i) / 20.0;
                    try {
                        label.setScaleX(scaleValue);
                        label.setScaleY(scaleValue);
                        label.setOpacity(opacityValue);
                        Thread.sleep(15);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }
}
