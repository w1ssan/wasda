package animationtesting.Buildup;

import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class PacLogic {
    protected Tile[][] cellGrid;
    protected int rows;
    protected int columns;
    protected final int level;
    protected File map;
    protected int foodamount;
    protected boolean eatingMode = false;
    protected int eatingDuration = 15;
    protected int eatingTimer = 0;
    protected int score = 0;

    //player
    protected Point2D playerloc;
    protected Point2D playerLastloc;
    protected int lives = 3;
    protected boolean gameOver = false;
    //ghosts
    protected Point2D spawnLoc;
    protected PacGhost blinky;
    protected PacGhost pinky;
    protected PacGhost bluey;
    protected PacGhost pokey;


    public PacLogic(int level){
        this.level = level;
        populateGrid();
    }

    private void getMap(){
        switch (level){
            case 1:
                map = new File("D:\\IdeaProjects\\Animationfx\\src\\animationtesting\\assets\\map1.txt");
                break;
            case 2:
                map = new File("D:\\IdeaProjects\\Animationfx\\src\\animationtesting\\assets\\map2.txt");
                break;
            case 3:
                map = new File("D:\\IdeaProjects\\Animationfx\\src\\animationtesting\\assets\\map3.txt");
                break;
        }
    }
    private void findSize(){
        try {
            getMap();
            Scanner mapReader = new Scanner(map);
            rows = 1;
            char[] line;
            while (mapReader.hasNextLine()) {
                line = mapReader.nextLine().toCharArray();
                rows++;
                columns = line.length+1;
            }
            //for testing purpose
            //System.out.println("rows: " + rows + ", Columns: " + columns);
            mapReader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
    private void populateGrid(){
        try {
            findSize();
            initiateGhosts();
            cellGrid = new Tile[rows][columns];
            Scanner mapReader = new Scanner(map);
            int row = 0;
            while (mapReader.hasNextLine()){
                char[] line = mapReader.nextLine().toCharArray();
                for (int i= 0; i < line.length; i++){
                    switch (line[i]){
                        case '1':
                            cellGrid[row][i] = new Tile(line[i],1, false, false);
                            break;
                        case '2':
                            cellGrid[row][i] = new Tile('0',0, false, true);
                            break;
                        case '0':
                            cellGrid[row][i] = new Tile(line[i],0, true,false);
                            foodamount++;
                            break;
                        case 'P':
                            cellGrid[row][i] = new Tile('0',0, false,false);
                            playerloc = new Point2D(i, row);
                            break;
                        case 'R':
                            cellGrid[row][i] = new Tile('0',0, true,false);
                            blinky.setHome(new Point2D(i, row));
                            foodamount++;
                            break;
                        case 'L':
                            cellGrid[row][i] = new Tile('0',0, true,false);
                            //ghostHome = new Point2D(row, i);
                            pinky.setHome(new Point2D(i, row));
                            foodamount++;
                            break;
                        case 'B':
                            cellGrid[row][i] = new Tile('0',0, true,false);
                            //ghostHome = new Point2D(row, i);
                            bluey.setHome(new Point2D(i, row));
                            foodamount++;
                            break;
                        case 'Y':
                            cellGrid[row][i] = new Tile('0',0, true,false);
                            //ghostHome = new Point2D(row, i);
                            pokey.setHome(new Point2D(i, row));
                            foodamount++;
                            break;
                        case 'M':
                            cellGrid[row][i] = new Tile('0',0, false,false);
                            spawnLoc = new Point2D(i, row);
                            pinky.setSpawnLoc(spawnLoc);
                            bluey.setSpawnLoc(spawnLoc);
                            blinky.setSpawnLoc(spawnLoc);
                            pokey.setSpawnLoc(spawnLoc);
                            blinky.setLocation(new Point2D(i, row));
                            blinky.setLastLocation(new Point2D(row, i - 1));
                            break;
                        case 'X':
                            cellGrid[row][i] = new Tile('0',0, false,false);
                            pinky.dead = new Point2D(i, row);
                            blinky.dead = new Point2D(i, row);
                            pinky.setLocation(new Point2D(i, row));
                            pinky.setLastLocation(new Point2D(row, i - 1));
                            break;
                        case 'S':
                            cellGrid[row][i] = new Tile('0',0, false,false);
                            bluey.setLocation(new Point2D(i, row));
                            bluey.dead = new Point2D(i, row);
                            bluey.setLastLocation(new Point2D(row, i - 1));
                            break;
                        case 'V':
                            cellGrid[row][i] = new Tile('0',0, false,false);
                            pokey.setLocation(new Point2D(i, row));
                            pokey.dead = new Point2D(i, row);
                            pokey.setLastLocation(new Point2D(row, i - 1));
                            break;
                    }
                }
                row++;
            }
            //System.out.println(Arrays.deepToString(cellGrid));
            mapReader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
    public void initiateGhosts(){
        blinky = new PacGhost(7, 14, 15, 1);
        blinky.ghost.setFill(Color.RED);
        pinky = new PacGhost(5,10,40, 10);
        pinky.ghost.setFill(Color.PINK);
        bluey = new PacGhost(9,20,15, 20);
        bluey.ghost.setFill(Color.BLUE);
        pokey = new PacGhost(2,4,10, 30);
        pokey.ghost.setFill(Color.PURPLE);

    }
    static class Tile{
        char type;
        int texture;
        boolean hasfood;
        boolean hasBigFood;
        private Tile(char type, int texture, boolean hasfood, boolean hasBigFood){
            this.type = type;
            this.texture = texture;
            this.hasfood = hasfood;
            this.hasBigFood = hasBigFood;
        }

        @Override
        public String toString() {
            return "Tile{" +
                    "type=" + type +
                    ", hasfood=" + hasfood +
                    '}';
        }
    }
    public void updateAi(){
        checkCollision(blinky);
        checkCollision(pinky);
        checkCollision(bluey);
        checkCollision(pokey);
        blinky.moveGhost(cellGrid, playerloc, eatingMode);
        pinky.moveGhost(cellGrid, playerloc, eatingMode);
        bluey.moveGhost(cellGrid, playerloc, eatingMode);
        pokey.moveGhost(cellGrid, playerloc, eatingMode);
        if (eatingMode){
            if (eatingDuration < eatingTimer){
                eatingTimer = 0;
                eatingMode = false;
                System.out.println("eating mode off");
            }else{
                eatingTimer++;
            }
        }
    }
    public void movePlayer(Point2D direction){
        Point2D poss = direction;
        poss = playerloc.add(direction);
        if (cellGrid[(int)poss.getY()][(int)poss.getX()].type != '1'){
            if (cellGrid[(int)poss.getY()][(int)poss.getX()].hasfood){
                score += 100;
                cellGrid[(int)poss.getY()][(int)poss.getX()].hasfood = false;
            }
            if (cellGrid[(int)poss.getY()][(int)poss.getX()].hasBigFood){
                score += 200;
                eatingMode = true;
                System.out.println("eating mode on");
                cellGrid[(int)poss.getY()][(int)poss.getX()].hasBigFood = false;
            }
            playerloc = poss;
        }
    }

    public Tile[][] getCellGrid() {
        return cellGrid;
    }
    protected void checkCollision(PacGhost ghost) {
        if (ghost.goalReached(playerloc)) {
            if (eatingMode) {
                ghost.location = ghost.dead;
                ghost.deadTimer = 0;
            } else {
                if (lives > 0){
                    lives--;
                }else{
                    gameOver = true;
                }
            }
        }
    }
}
