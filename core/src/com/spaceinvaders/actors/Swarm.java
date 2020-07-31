package com.spaceinvaders.actors;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.spaceinvaders.game.ScoreBoard;
import com.spaceinvaders.game.SpaceInvaders;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Random;

public class Swarm extends AlienGroup {
    private boolean reachEnd = false;
    private ArrayList<AlienGroup> alienRows;
    private ArrayList<LinkedList<Alien>> alienColumns;
    //private HashMap<Integer, ArrayList<Alien>> enemiesToRemove;

    // An invisible row of aliens with the same positions as the first row aliens
    // The aliens' x and y values will be used to alter the size of the hitbox
    private AlienGroup rectanglePoints;

    // The column that is being used as the left and right boundaries of the hitbox
    private int leftColumn;
    private int rightColumn;
    private int numAliens;



    public Swarm(){
        alienRows = new ArrayList<>();
        alienColumns = new ArrayList<>();
        //initRemovalMap();
    }

    public void addAlienGroup(AlienGroup line){
        alienRows.add(line);

        // Creation of the boundary points that will be used to constantly update the hitbox
        if(alienRows.size() == 1){
            rectanglePoints = new AlienGroup();
            for(int i = 0; i < line.getSize(); i++){
              float x = line.getAlien(i).getX();
              float y = line.getAlien(i).getY();

              rectanglePoints.addAlien(new CrabAlien(x,y));
            }
            int index = 0;
            for(Alien alien : rectanglePoints.getAliens()){
                System.out.println("left bound " + index + ": " + alien.getX() + ", " + alien.getY());
                index++;
            }
        }

        numAliens = getSize();

    }

    /**
     * Creates a rectangle that encapsulates the entire swarm of aliens
     */
    public void drawBox(){
        System.out.println("box drawn");
        connectAliens();

        // Measurements for the swarm hitbox
        Enemy left = rectanglePoints.getAlien(0);
        Enemy topLeft = alienRows.get(alienRows.size()-1).getLeft();
        Enemy right = rectanglePoints.getAlien(rectanglePoints.getSize() - 1);

        float startingX = left.getX();
        float startingY = left.getY();
        float height = (topLeft.getImg().getHeight() - left.getImg().getHeight()) + topLeft.getImg().getHeight();
        float width = right.getX() - left.getX() + right.getImg().getWidth();

        hitBox = new Rectangle(startingX, startingY, width, height);
    }

    @Override
    public void draw(SpriteBatch batch){
        for(AlienGroup line : alienRows){
            line.draw(batch);
        }
    }

    int num = 1;

    @Override
    public void update(float delta){

        Enemy left = rectanglePoints.getAlien(leftColumn);
        Enemy right = rectanglePoints.getAlien(rightColumn);

        // Checks whether or not the hitbox has reached an invisible wall

        // This line of code is a bit iffy
        // Sometimes it gets called multiple times, maybe because even after reversing its x position is still in
        // the GAME_WIDTH - WALL_BOUNDARY range
        // This is the only problem left
        if(hitBox.getX() + hitBox.getWidth() > SpaceInvaders.GAME_WIDTH - WALL_BOUNDARY ||
                hitBox.getX() < WALL_BOUNDARY){
            // The aliens are then made to reverse their directions
            for(AlienGroup line : alienRows){
                line.reverseDirection(delta);
            }
            rectanglePoints.reverseDirection(delta);

            //hitBox.setPosition(SpaceInvaders.GAME_WIDTH - WALL_BOUNDARY,left.getY());
            System.out.println(num + ". " + (hitBox.getX() + hitBox.getWidth()));
            System.out.println(num + ". " + "swarm reversed");
            num++;


        }

        // Signals the game that the player has lost since the aliens have reached their goal
        if(hitBox.getY() <= 50){

            reachEnd = true;

            return;
        }

        for(AlienGroup line : alienRows){
            line.update(delta);
        }

        rectanglePoints.update(delta);

        hitBox.setPosition(left.getX(), left.getY());

        if(alienColumns.size() > 0){
            hitBox.setWidth(right.getX() - left.getX() + right.getImg().getWidth());
        }



        if(numAliens - getSize() == 4){
            //System.out.println(numAliens);
            System.out.println("Enough aliens have been killed for a speed update");
            //setSpeed((float) (getSpeed() * 1.5));
            updateVelocity();
            rectanglePoints.updateVelocity();
            numAliens = getSize();
        }






    }

    public void shoot(ArrayList<Projectile> blasts){
        // Check every alien column
        Random rand = new Random();
        giveShotClearance();

        alienRows.get(rand.nextInt(alienRows.size())).shoot(blasts);

        /*
        for(int i = 0; i<rand.nextInt(alienRows.size()); i++){
            alienRows.get(i).shoot(blasts);
        }

         */


        /*
        for(AlienGroup row : alienRows){
            row.shoot(blasts);
        }

         */
        // Only aliens in the head of their list can shoot
        // Activate the aliens clearToShoot


    }

    public ArrayList<Alien> checkCollisions(Projectile projectile, ScoreBoard scoreBoard){

        // A nested arraylist of dead aliens
        // The outer list represents the rows of the swarm and the inner list (in the form of an AlienGroup)
        // represents the dead aliens in that particular row
        ArrayList<Alien> deadArr = new ArrayList<>();

        int rowNum = 1;
        for(AlienGroup row : alienRows){
            AlienGroup deadRow = new AlienGroup();
            ArrayList<Alien> deadAliens = row.checkCollisions(projectile, scoreBoard);

            row.removeEnemies(deadAliens);
            deadRow.addAliens(deadAliens);
            deadArr.add(deadRow);

            //System.out.println("row " + rowNum + ": " + deadRow.getSize());
            rowNum++;

            // Puts the dead aliens in the map based on its row number
            //enemiesToRemove.put(index, deadAliens);
            //index++;
        }


        return deadArr;
    }

    /**
     * Links aliens in the same column together
     */
    private void connectAliens(){
        int numAliensInARow = alienRows.get(0).getSize();
        int columnNum = 0;
        leftColumn = columnNum;
        for(int i = 0; i < numAliensInARow; i++){
            LinkedList<Alien> column = new LinkedList<>();
            for(AlienGroup row : alienRows){
                row.getAlien(i).setColumn(columnNum);
                column.add(row.getAlien(i));
            }
            // Let the front most alien in every column shoot
            column.getFirst().clearedToShoot();
            alienColumns.add(column);
            columnNum++;
        }
        rightColumn = columnNum - 1 ;
    }



    /**
     * Creates a map that contains the aliens that are to be removed
     * @return A map with row number as a key and a list of aliens as the value
     */
    private HashMap<Integer, ArrayList<Alien>> createRemovalMap(){
        HashMap<Integer, ArrayList<Alien>>enemiesToRemove = new HashMap<>();
        for(int i = 0; i < alienRows.size(); i++){
            ArrayList<Alien> deadEnemies = new ArrayList<>();
            enemiesToRemove.put(i, deadEnemies);
        }
        return enemiesToRemove;
    }



    @Override
    public void reverseDirection(float delta){
        for(AlienGroup row : alienRows){
            row.reverseDirection(delta);
        }
    }

    /**
     * Removes the enemies from the columns
     * @param aliensToRemove The list of aliens that were removed in the checkCollisions method
     *                       This list is used to update the alien columns
     */
    public void removeEnemies(ArrayList<Alien> aliensToRemove){
        // A map that maps the dead enemies to their row number
        // Will be used to remove the dead alien from its column
        HashMap<Integer, ArrayList<Alien>> enemiesToRemove = createRemovalMap();
        //ArrayList<ArrayList<Alien>> deadAliensByRow = new ArrayList<>();

        // Removes rows that do not have aliens anymore
        removeRows();

        int index = 0;
        for(Alien alien : aliensToRemove){
            if(alien instanceof AlienGroup){
                //deadAliensByRow.add(((AlienGroup) alien).getAliens());
                enemiesToRemove.put(index, ((AlienGroup) alien).getAliens());
                //System.out.println("row number " + index + " " + enemiesToRemove.get(index).size());
            }else{
                System.out.println("Somehow it isn't an alienGroup");
            }
            index++;
        }

        //index = 0;

        for(int i = 0 ; i < alienRows.size(); i++){
            if(enemiesToRemove.get(i) != null){
                for(Alien alien : enemiesToRemove.get(i)){
                    int columnNum = alien.getColumn();
                    alienColumns.get(columnNum).remove(alien);
                    System.out.println("Column " + columnNum + ": " + alien.getX() + ", " + alien.getY() + " removed");

                }
            }else{

            }


        }

        aliensToRemove.clear();
        changeBounds();


    }

    private void removeRows(){
        ArrayList<AlienGroup> rows = new ArrayList<>();

        for(AlienGroup row : alienRows){
            if(row.dead()){
                row.dispose();
                rows.add(row);
            }
        }

        alienRows.removeAll(rows);
    }


    /**
     *  Changes the left and right boundaries of the swarm hitbox
     */
    private void changeBounds(){
        // Checks whether or not the left and right bounds of the hitbox must be changed
        if(alienColumns.size() > 1){
            // If the left and/or right columns are empty, then the next available column will be used

            if(alienColumns.get(leftColumn).isEmpty() && leftColumn < alienColumns.size() - 1){
                System.out.println("change left bound");
                //int index = leftColumn;
                //System.out.println("Old left column: " + leftColumn);

                /*
                while(alienColumns.get(index).isEmpty() && index < alienColumns.size() - 1){
                    index++;
                }

                 */
                int index = 0;
                if(index < alienColumns.size()){
                    System.out.println(index);
                    System.out.println("Number of columns: " + alienColumns.size());
                    while(alienColumns.get(index).isEmpty() && index < alienColumns.size() - 1){
                        index++;
                    }
                }



                leftColumn = index;
                System.out.println("New left column: " + leftColumn);
                //leftColumn++;
                //System.out.println("New left column: " + leftColumn);
            }else if(!alienColumns.get(leftColumn).isEmpty()){

            }
            if(alienColumns.get(rightColumn).isEmpty() && rightColumn >= 0){
                System.out.println("change right bound");
                rightColumn--;
                System.out.println("New right column: " + rightColumn);
            }
        }
    }

    private void giveShotClearance(){
        for(LinkedList<Alien> column : alienColumns){
            if(!column.isEmpty()){
                column.getFirst().clearedToShoot();

            }
        }
    }

    public boolean dead(){
        if(alienRows.size() == 0){
            killed = true;
        }
        return killed;
    }



    public void dispose(){
        for(AlienGroup row : alienRows){
            row.dispose();
        }
        alienRows.removeAll(alienRows);
        rectanglePoints.dispose();
    }

    public boolean reachedGoal(){
        return reachEnd;
    }

    public int getSize(){
        int numAliens = 0;

        for(AlienGroup row : alienRows){
            numAliens+=row.getSize();
        }

        return numAliens;
    }


    public void updateVelocity(){
        for(AlienGroup row : alienRows){
            row.updateVelocity();
        }
    }



    public void setSpeed(float speed){
        for(AlienGroup row : alienRows){
            row.setSpeed(speed);
        }
    }


}
