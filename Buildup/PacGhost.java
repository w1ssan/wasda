package animationtesting.Buildup;

import javafx.geometry.Point2D;
import javafx.scene.shape.Circle;

import java.util.ArrayList;


public class PacGhost {
    protected Point2D location;
    protected Point2D home;
    protected Point2D lastLocation;
    protected Point2D spawnLoc;
    protected Point2D dead;
    protected int deadPeriod = 10;
    protected int deadTimer = 10;
    protected int chase;
    protected int scatter;
    protected int move = 0;
    protected int idle;
    protected int spawn;
    protected boolean spawned = false;
    protected int idleperiod = 0;
    Circle ghost;

    public PacGhost(){}
    public PacGhost(int chase, int scatter, int idle, int spawn){
        setChase(chase);
        setScatter(scatter);
        setIdle(idle);
        this.spawn = spawn;
        ghost = new Circle(5);
    }
    public void setLocation(Point2D location) {
        this.location = location;
    }
    public void setLastLocation(Point2D lastLocation) {
        this.lastLocation = lastLocation;
    }
    public void setHome(Point2D home) {
        this.home = home;
    }
    public void setChase(int chase) {
        this.chase = chase;
    }
    public void setScatter(int scatter) {
        this.scatter = scatter;
    }

    public void setSpawnLoc(Point2D spawnLoc) {
        this.spawnLoc = spawnLoc;
    }

    public void setIdle(int idle) {
        this.idle = idle;
    }

    public void tick(){
        if (move > scatter){
            move = 0;
        }else{
            this.move++;
        }
    }

    public void moveGhost(PacLogic.Tile[][] cellGrid, Point2D playerloc, boolean ghostEatingMode){
        if (idleperiod == spawn){
            spawn();
            idleperiod++;
        }else if(deadPeriod == deadTimer) {
            spawn();
            deadTimer++;
        }else if (idleperiod < spawn){
            idleperiod++;
        }else if (deadTimer < deadPeriod){
            deadTimer++;
        }else {
            Point2D possible = null;
            //gets possible moves
            ArrayList<Point2D> optionsList = possibleOptions(cellGrid);
            lastLocation = location;
            double playervalue = Math.abs(playerloc.getX() + playerloc.getY());
            double ghostHomeValue = Math.abs(home.getX() + home.getY());
            double lowestValue = 99;
            double movevalue;
            optionsList.remove(lastLocation);
            /*
            for (Point2D option:optionsList) {
                System.out.println(option.getX() + ", " + option.getY());
            }

 */

            for (Point2D possibleOption : optionsList) {
                if (playervalue < 15 || ghostEatingMode || move > chase) {
                    movevalue = Math.abs(possibleOption.getX() + possibleOption.getY() - ghostHomeValue);
                    if (movevalue < lowestValue) {
                        lowestValue = movevalue;
                        possible = possibleOption;
                    }
                } else {
                    movevalue = Math.abs(possibleOption.getX() + possibleOption.getY() - playervalue);
                    if (movevalue < lowestValue) {
                        lowestValue = movevalue;
                        possible = possibleOption;
                    }
                }
                location = possible;
                tick();
            }
        }

    }
    public ArrayList<Point2D> possibleOptions(PacLogic.Tile[][] cellGrid){
        //System.out.println(location.getX() + ", " + location.getY());
        ArrayList<Point2D> locationslist = new ArrayList<>();
        Point2D newPoint;
        if (cellGrid[(int)location.getY()][(int)location.getX() + 1].type == '0'){
            newPoint = new Point2D(location.getX() + 1, location.getY());
            if (!matchPoint2D(newPoint, lastLocation)){
                locationslist.add(newPoint);
            }
        }
        if (cellGrid[(int)location.getY()][(int)location.getX() - 1].type == '0'){
            newPoint = new Point2D(location.getX() - 1, location.getY());
            if (!matchPoint2D(newPoint,lastLocation)){
                locationslist.add(newPoint);
            }
        }
        if (cellGrid[(int)location.getY() + 1][(int)location.getX()].type == '0'){
            newPoint = new Point2D(location.getX(), location.getY() + 1);
            if (!matchPoint2D(newPoint, lastLocation)){
                locationslist.add(newPoint);
            }
        }
        if (cellGrid[(int)location.getY() - 1][(int)location.getX()].type == '0'){
            newPoint = new Point2D(location.getX(), location.getY()- 1);
            if (!matchPoint2D(newPoint, lastLocation)){
                locationslist.add(newPoint);
            }
        }
        return locationslist;
    }
    public boolean goalReached(Point2D target){
        if (matchPoint2D(new Point2D(location.getX() +1, location.getY()), target)){
            return true;
        }else if (matchPoint2D(new Point2D(location.getX() -1, location.getY()), target)){
            return true;
        }else if (matchPoint2D(new Point2D(location.getX() , location.getY() +1), target)){
            return true;
        }else if (matchPoint2D(new Point2D(location.getX() , location.getY() -1), target)){
            return true;
        }else return false;
    }
    public boolean matchPoint2D(Point2D a, Point2D b){
        return a.getX() == b.getX() && a.getY() == b.getY();
    }

    private void spawn(){
        location = spawnLoc;
        lastLocation = new Point2D(spawnLoc.getX()-1, spawnLoc.getY());
    }
}
