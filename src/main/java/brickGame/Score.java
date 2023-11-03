package brickGame;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
//import sun.plugin2.message.Message;

public class Score {

    public void showScore(final double x, final double y, int score, Main main) {
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
            main.root.getChildren().add(label);
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

    public void showMessage(String message, final Main main) {
        final Label label = new Label(message);
        label.setTranslateX(220);
        label.setTranslateY(340);

        Platform.runLater(() -> {
            main.root.getChildren().add(label);
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

    public void showGameOver(final Main main) {
            Label label = new Label("Game Over :(");
            label.setTranslateX(200);
            label.setTranslateY(250);
            label.setScaleX(2);
            label.setScaleY(2);

            Button restart = new Button("Restart");
            restart.setTranslateX(220);
            restart.setTranslateY(300);

            Button ranking = new Button("Ranking");
            ranking.setTranslateX(220);
            ranking.setTranslateY(340);
            ranking.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {

                }
            });
            restart.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    //main.restartGame();
                }
            });

            Platform.runLater(() -> {
                main.root.getChildren().addAll(label, restart, ranking);
            });
    }

    public void showWin(final Main main) {
        Label label = new Label("You Win :)");
        label.setTranslateX(200);
        label.setTranslateY(250);
        label.setScaleX(2);
        label.setScaleY(2);
        Platform.runLater(()-> {
            main.root.getChildren().addAll(label);
        });
    }
}
