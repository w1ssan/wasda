package animationtesting.Buildup;

import javafx.animation.TranslateTransition;
import javafx.geometry.Insets;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.io.FileInputStream;
import java.io.FileNotFoundException;


public class MenuPane extends BorderPane {
    //top menu TODO
    HBox topMenu;
    VBox startMenu;
    private static final long THRESHOLD = 400_000_000L; // 500 ms
    private long lastMoveNanos;
    Button backBtn;
    Text score;
    ImageView live;
    Button startGame;
    Button highScores;
    boolean gameStarted = false;
    PacView game;


    public MenuPane(){
        setTop(makeTopMenu());
        setCenter(makeMenu());
    }
    //Center start menu
    public VBox makeMenu(){
        startMenu = new VBox();
        try {
            ImageView paclogo = new ImageView(new Image(new FileInputStream("C:\\Users\\Bjorn\\Desktop\\pacmanImages\\pacLogo.png")));
            paclogo.setFitHeight(200);
            paclogo.setFitWidth(500);
            startMenu.setPadding(new Insets(10,10,10,10));
            startGame = new Button("Start Game");
            startGame.setOnAction(e-> startGameAction());
            highScores = new Button("HighScores");
            startMenu.getChildren().addAll(paclogo, startGame, highScores);
            startMenu.setAlignment(Pos.CENTER);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        startMenu.setBackground(new Background(new BackgroundFill(Color.BLACK, null, null)));
        return startMenu;
    }
    private HBox makeTopMenu(){
        topMenu = new HBox();
        score = new Text("Score: ");
        backBtn = new Button("Back");
        topMenu.setBackground(new Background(new BackgroundFill(Color.BLACK, null, null)));
        topMenu.getChildren().addAll(score, backBtn);
        return topMenu;
    }
    private void startGameAction(){
        game = new PacView();
        setCenter(new PacView());
        gameStarted = true;
    }
    //center Game TODO


}
