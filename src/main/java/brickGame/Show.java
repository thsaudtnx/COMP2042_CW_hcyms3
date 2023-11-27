package brickGame;

import javafx.application.Platform;
import javafx.scene.control.Label;

public class Show {
    public void showCountDown(Main main, GameEngine engine){
        Label label = new Label();
        label.setTranslateX(250);
        label.setTranslateY(300);
        label.setVisible(true);
        Platform.runLater(() -> {
            main.root.getChildren().add(label);
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

}
