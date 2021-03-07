package animationtesting.Buildup;

import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

import java.io.FileInputStream;
import java.io.FileNotFoundException;


public class PacMain extends Application {
    MenuPane menuPane;
    private static final long THRESHOLD = 400_000_000L; // 500 ms
    private long lastMoveNanos;
    PacView game;
    Scene scene;
    Text scorelbl;
    Text liveslbl;
    @Override
    public void start(Stage primaryStage){
        BorderPane window = new BorderPane();
        game = new PacView();
        window.setCenter(game);
        scene = new Scene(window,575, 625, Color.BLACK);
        window.setOnKeyPressed(e-> handleKeyPressed(e));

        primaryStage.setScene(scene);
        primaryStage.show();
        window.requestFocus();
    }
/*
    public Scene makeScene(){
        BorderPane window = new BorderPane();
        PacView game = new PacView();
        window.setCenter(game);
        //window.setTop(scorePanel());
        Scene scene = new Scene(window, Color.BLACK);
        game.setOnKeyPressed(this::handleKeyPressed);
        game.requestFocus();
        return scene;
    }


 */



    public static void main(String[] args) {
        launch(args);
    }

    private void handleKeyPressed(KeyEvent event) {
        Point2D potloc = null;
        Point2D logicloc = null;
        if (event.getCode().isArrowKey()) {
            event.consume();
            final TranslateTransition transition = new TranslateTransition(Duration.millis(300), game.player);
            long now = System.nanoTime();
            if (lastMoveNanos <= 0L || now - lastMoveNanos >= THRESHOLD) {
                switch (event.getCode()) {
                    case LEFT:
                            if (game.logic.cellGrid[(int)game.logic.playerloc.getY()][(int)game.logic.playerloc.getX() -1].type != '1'){
                                logicloc = new Point2D(-1, 0);
                                game.logic.movePlayer(logicloc);
                                game.player.setRotate(180);
                                potloc = new Point2D(-game.tileSize,0);
                            }
                        break;
                    case RIGHT:
                        if (game.logic.cellGrid[(int)game.logic.playerloc.getY()][(int)game.logic.playerloc.getX() + 1].type != '1'){
                            logicloc = new Point2D(1, 0);
                            game.logic.movePlayer(logicloc);
                            game.player.setRotate(0);
                            potloc = new Point2D(game.tileSize,0);
                        }
                        break;
                    case DOWN:
                        if (game.logic.cellGrid[(int)game.logic.playerloc.getY() +1][(int)game.logic.playerloc.getX()].type != '1') {
                            logicloc = new Point2D(0, 1);
                            game.logic.movePlayer(logicloc);
                            potloc = new Point2D(0, game.tileSize);
                            game.player.setRotate(90);
                        }
                        break;
                    case UP:
                        if (game.logic.cellGrid[(int)game.logic.playerloc.getY() -1][(int)game.logic.playerloc.getX()].type != '1'){
                            logicloc = new Point2D(0, -1);
                            game.logic.movePlayer(logicloc);
                            game.player.setRotate(270);
                            potloc = new Point2D(0,-game.tileSize);
                        }
                        break;
                    case A:
                        if (game.logic.cellGrid[(int)game.logic.playerloc.getY()][(int)game.logic.playerloc.getX() -1].type != '1'){
                            logicloc = new Point2D(-1, 0);
                            game.logic.movePlayer(logicloc);
                            potloc = new Point2D(-game.tileSize,0);
                        }
                    case D:
                        if (game.logic.cellGrid[(int)game.logic.playerloc.getY()][(int)game.logic.playerloc.getX() + 1].type != '1'){
                            logicloc = new Point2D(1, 0);
                            game.logic.movePlayer(logicloc);
                            potloc = new Point2D(game.tileSize,0);
                        }
                        break;
                    case W:
                        if (game.logic.cellGrid[(int)game.logic.playerloc.getY() -1][(int)game.logic.playerloc.getX()].type != '1'){
                            logicloc = new Point2D(0, -1);
                            game.logic.movePlayer(logicloc);
                            potloc = new Point2D(0,-game.tileSize);
                        }
                        break;
                    case S:
                        if (game.logic.cellGrid[(int)game.logic.playerloc.getY() +1][(int)game.logic.playerloc.getX()].type != '1'){
                            logicloc = new Point2D(0, 1);
                            game.logic.movePlayer(logicloc);
                            potloc = new Point2D(0,game.tileSize);
                        }
                        break;
                    case J:
                        break;
                    default:
                        throw new AssertionError();
                }
                if (potloc != null){
                    scorelbl.setText(game.logic.score + "");
                    liveslbl.setText(game.logic.lives + "");
                    Point2D animloc = new Point2D(game.player.getTranslateX(), game.player.getTranslateY());
                    potloc = animloc.add(potloc);
                    transition.setFromX(game.player.getTranslateX());
                    transition.setFromY(game.player.getTranslateY());
                    transition.setToX(potloc.getX());
                    transition.setToY(potloc.getY());
                    transition.playFromStart();
                    lastMoveNanos = now;
                }
            }
        }
    }
}
