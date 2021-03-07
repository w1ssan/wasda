package animationtesting.Buildup;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.io.FileInputStream;
import java.io.FileNotFoundException;


public class PacView extends Pane {
    protected PacLogic logic;
    protected GridPane tiles;
    protected final int tileSize = 30;

    //textures
    protected Image wallStraight;
    protected Image bigfood;
    protected Image floor;
    protected Image food;

    //module animations
    protected AnimatedAsset bluey;
    protected AnimatedAsset blinky;
    protected AnimatedAsset pokey;
    protected AnimatedAsset pinky;
    protected AnimatedAsset player;
    public void initAnimations(){
        try {
            bluey = new AnimatedAsset(logic.bluey.location.getX() * tileSize, logic.bluey.location.getY() * tileSize, tileSize, new Image(
                    new FileInputStream("C:\\Users\\Bjorn\\Desktop\\pacmanImages\\bluey1.png")),
                    new Image(new FileInputStream("C:\\Users\\Bjorn\\Desktop\\pacmanImages\\bluey2.png")));
            player = new AnimatedAsset(logic.playerloc.getX() * tileSize, logic.playerloc.getY() * tileSize, tileSize,
                    new Image(new FileInputStream("C:\\Users\\Bjorn\\Desktop\\pacmanImages\\pacmanOpen.png")),
                    new Image(new FileInputStream("C:\\Users\\Bjorn\\Desktop\\pacmanImages\\pacmanHalvopen.png")),
                    new Image(new FileInputStream("C:\\Users\\Bjorn\\Desktop\\pacmanImages\\pacmanClosed.png")));
            pinky = new AnimatedAsset(logic.pinky.location.getX() * tileSize, logic.pinky.location.getY() * tileSize, tileSize, new Image(
                    new FileInputStream("C:\\Users\\Bjorn\\Desktop\\pacmanImages\\pinky1.png")),
                    new Image(new FileInputStream("C:\\Users\\Bjorn\\Desktop\\pacmanImages\\pinky2.png")));
            blinky = new AnimatedAsset(logic.blinky.location.getX() * tileSize, logic.blinky.location.getY() * tileSize, tileSize, new Image(
                    new FileInputStream("C:\\Users\\Bjorn\\Desktop\\pacmanImages\\Blinky.png")),
                    new Image(new FileInputStream("C:\\Users\\Bjorn\\Desktop\\pacmanImages\\blinky2.png")));
            pokey = new AnimatedAsset(logic.pokey.location.getX() * tileSize, logic.pokey.location.getY() * tileSize, tileSize, new Image(
                    new FileInputStream("C:\\Users\\Bjorn\\Desktop\\pacmanImages\\pokey1.png")),
                    new Image(new FileInputStream("C:\\Users\\Bjorn\\Desktop\\pacmanImages\\pokey2.png")));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
    public void update(){
        if (logic.gameOver){
            Text gameoverTxt = new Text("GAME OVER");
            gameoverTxt.setFont(new Font(50));
            gameoverTxt.setFill(Color.WHITE);
            gameoverTxt.setX(getWidth()/2);
            gameoverTxt.setY(getHeight()/2);
            getChildren().add(gameoverTxt);

        }else {
            logic.updateAi();
            updateView();
            bluey.runAnimation();
            ghostMoveGraphic(bluey, logic.bluey.location.getX() * tileSize, logic.bluey.location.getY() * tileSize);
            player.runAnimation();
            pinky.runAnimation();
            ghostMoveGraphic(pinky, logic.pinky.location.getX() * tileSize, logic.pinky.location.getY() * tileSize);
            blinky.runAnimation();
            ghostMoveGraphic(blinky, logic.blinky.location.getX() * tileSize, logic.blinky.location.getY() * tileSize);
            pokey.runAnimation();
            ghostMoveGraphic(pokey, logic.pokey.location.getX() * tileSize, logic.pokey.location.getY() * tileSize);
        }
    }

    public PacView(){
        //setBackground(new Background(new BackgroundFill(Color.BLACK, null, null)));
        this.logic = new PacLogic(1);
        initiateTextures();
        initiateView();
        initAnimations();
        this.getChildren().add(tiles);
        Timeline timeline = new Timeline();
        timeline.getKeyFrames().add(new KeyFrame(Duration.millis(500), e-> update()));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
        getChildren().addAll(bluey, player, blinky, pokey, pinky);
    }


    private void initiateView(){
        tiles = new GridPane();
        PacLogic.Tile[][] grid = logic.cellGrid;
        for (int i = 0; i < logic.rows-1; i++){
            for (int j = 0; j < logic.columns-1; j++){
                if (grid[i][j].texture == 0 ){
                    if (grid[i][j].hasBigFood){
                        ImageView iv = new ImageView(bigfood);
                        iv.setFitHeight(tileSize);
                        iv.setFitWidth(tileSize);
                        tiles.add(iv, j, i);
                    }else if (grid[i][j].hasfood){
                        ImageView iv = new ImageView(food);
                        iv.setFitHeight(tileSize);
                        iv.setFitWidth(tileSize);
                        tiles.add(iv, j, i);
                    }else{
                        ImageView iv = new ImageView(floor);
                        iv.setFitHeight(tileSize);
                        iv.setFitWidth(tileSize);
                        tiles.add(iv, j, i);
                    }

                }else if (grid[i][j].texture == 1){
                    ImageView iv = new ImageView(wallStraight);
                    iv.setFitHeight(tileSize);
                    iv.setFitWidth(tileSize);
                    tiles.add(iv, j, i);
                }
            }
        }
    }
    protected void updateView(){
        PacLogic.Tile[][] grid = logic.cellGrid;
        for (int i = 0; i < logic.rows-1; i++){
            for (int j = 0; j < logic.columns-1; j++){
                if (grid[i][j].texture == 0 ){
                    if (grid[i][j].hasBigFood){
                        ImageView iv = new ImageView(bigfood);
                        iv.setFitHeight(tileSize);
                        iv.setFitWidth(tileSize);
                        tiles.add(iv, j, i);
                    }else if (grid[i][j].hasfood){
                        ImageView iv = new ImageView(food);
                        iv.setFitHeight(tileSize);
                        iv.setFitWidth(tileSize);
                        tiles.add(iv, j, i);
                    }else{
                        ImageView iv = new ImageView(floor);
                        iv.setFitHeight(tileSize);
                        iv.setFitWidth(tileSize);
                        tiles.add(iv, j, i);
                    }

                }else if (grid[i][j].texture == 1){
                    ImageView iv = new ImageView(wallStraight);
                    iv.setFitHeight(tileSize);
                    iv.setFitWidth(tileSize);
                    tiles.add(iv, j, i);
                }
            }
        }
    }
    private void initiateTextures(){
        try{
            wallStraight = new Image(new FileInputStream("C:\\Users\\Bjorn\\Desktop\\pacmanImages\\tile.png"));
            food = new Image(new FileInputStream("C:\\Users\\Bjorn\\Desktop\\pacmanImages\\smallfood.png"));
            bigfood = new Image(new FileInputStream("C:\\Users\\Bjorn\\Desktop\\pacmanImages\\bigfood.png"));
            floor = new Image(new FileInputStream("D:\\IdeaProjects\\Animationfx\\src\\animationtesting\\Buildup\\PacAssets\\floor.png"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
    private void ghostMoveGraphic(AnimatedAsset target, double destX, double destY){
        TranslateTransition transition = new TranslateTransition(Duration.millis(500), target);
        transition.setFromX(target.getTranslateX());
        transition.setFromY(target.getTranslateY());
        transition.setToX(destX);
        transition.setToY(destY);
        transition.playFromStart();
    }


}
