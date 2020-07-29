package com.spaceinvaders.actors;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.spaceinvaders.game.ScoreBoard;
import com.spaceinvaders.game.SpaceInvaders;

import java.util.ArrayList;
import java.util.Random;

public class AlienGroup extends Alien {
    private ArrayList<Alien> aliens;
    private Random random = new Random();
    protected Enemy left;
    protected Enemy right;
    private boolean reachEnd = false;

    public AlienGroup(){
        aliens = new ArrayList<>();
    }

    public AlienGroup(AlienGroup another){
        this.aliens = another.aliens;
    }
    public void addAlien(Alien alien){
        aliens.add(alien);
        if(aliens.size() == 1){
            left = alien;
        }
        right = alien;
    }

    public void addAliens(ArrayList<Alien> aliens){
        this.aliens = aliens;
    }

    public void drawBox(){
        left = aliens.get(0);
        float startingX = left.getX();
        float startingY = left.getY();
        float height = left.getImg().getHeight();
        //float width = (gapBetweenAliens * (aliens.size()-1)) + left.getImg().getWidth();
        float width = right.getX() - left.getX() + right.getImg().getWidth();

        hitBox = new Rectangle(startingX, startingY, width, height);
    }

    @Override
    public void draw(SpriteBatch batch){
        //drawBox();
        for(Enemy alien : aliens){
            alien.draw(batch);
        }
    }

    @Override
    public void update(float deltaTime){
        //drawBox();
        // Need to find a way to do this

        /*
        if(hitBox.getX() + hitBox.getWidth() >= SpaceInvaders.GAME_WIDTH - WALL_BOUNDARY ||
                hitBox.getX() <= WALL_BOUNDARY){
            for(Alien alien : aliens){
                alien.reverseDirection();
            }
        }





        if(hitBox.getY() <= 100){
            //aliens.removeAll(aliens);

            for(Alien alien : aliens){
                alien.dispose();
            }
            aliens.removeAll(aliens);

            reachEnd = true;
        }
        */





        for(Enemy alien : aliens){
            alien.update(deltaTime);
        }

        //hitBox.setPosition(left.getX(), left.getY());

        /*
        if(aliens.size() > 0){
            hitBox.setWidth(right.getX() - left.getX() + right.getImg().getWidth());
        }

         */




    }

    @Override
    public void action() {
    }

    // Change the shot mechanics
    // Shots might have to be determined by the alien
    @Override
    public void shoot(ArrayList<Projectile> blasts){
        if(aliens.size() != 0){
            // Get the aliens that are cleared to shoot
            ArrayList<Alien> aliensToShoot = new ArrayList<>();
            for(Alien alien : aliens){
                if(alien.getShotClearance()){
                    aliensToShoot.add(alien);
                }
            }

            //aliensToShoot.forEach((alien) -> alien.shoot(blasts));
            /*
            for(Alien alien : aliensToShoot){
                alien.shoot(blasts);
            }

             */

            // The original algorithm

            if(aliensToShoot.size() != 0){
                /*
                if(random.nextFloat() < 0.50f){
                    int alienIndex = random.nextInt(aliensToShoot.size());
                    aliensToShoot.get(alienIndex).shoot(blasts);
                }

                 */
                // Select a random alien from the cleared to shoot aliens
                int alienIndex = random.nextInt(aliensToShoot.size());
                aliensToShoot.get(alienIndex).shoot(blasts);
            }
        }

    }

    public void removeEnemies(ArrayList<Alien> aliensToRemove){
        if(aliensToRemove != null){
            aliens.removeAll(aliensToRemove);
        }

    }

    /*
    public void removeAliens(Alien alien){
        if(alien instanceof AlienGroup){
            ArrayList<Alien> aliensToRemove = ((AlienGroup) alien).getAliens();
            if(aliensToRemove != null){
                aliens.removeAll(aliensToRemove);
            }
        }else{
            aliens.remove(alien);
        }
    }

     */

    /**
     *
     * @param projectile the projectile that could hit an alien
     * @param scoreBoard updates the score based on the score of the alien that is hit
     * @return enemies that have been killed
     */
    public ArrayList<Alien> checkCollisions(Projectile projectile, ScoreBoard scoreBoard){
        ArrayList<Alien> deadAliens = new ArrayList<>();
        for(Alien alien : aliens){
            if(projectile.collideWith(alien.getHitBox())){
                if(alien.equals(left) && aliens.size() > 1){
                    left = aliens.get(aliens.indexOf(alien) + 1);
                }else if(alien.equals(right) && aliens.size() > 1){
                    right = aliens.get(aliens.indexOf(alien) - 1);
                }
                //System.out.println("Alien killed: " + alien.getX() + ", " + alien.getY() + " Column num: " + alien.getColumn());

                alien.kill();


                deadAliens.add(alien);
                System.out.println(alien.dead());
                System.out.println(deadAliens.size());

                scoreBoard.updateScore(alien.getScore());
                System.out.println("updated score");


            }
        }
        //System.out.println(deadAliens.size());
        return deadAliens;
    }

    public Enemy getLeft(){
        return left;
    }

    public Enemy getRightAlien(){
        return right;
    }

    /**
     *  Reverses the direction of a row of aliens
     */
    @Override
    public void reverseDirection(float delta){

        for(Enemy alien : aliens){
            alien.reverseDirection(delta);
        }
    }

    @Override
    public void dispose(){
        for(Enemy alien : aliens){
            alien.dispose();
        }
    }

    public Alien getAlien(int index){
        return aliens.get(index);
    }

    public ArrayList<Alien> getAliens(){
        return aliens;
    }

    public int getSize(){
        return aliens.size();
    }

    public boolean dead(){
        if(aliens.size() == 0){
            killed = true;
        }
        return killed;
    }
}
