package com.spaceinvaders.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.spaceinvaders.actors.*;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

/**
 * Represents a level in the game
 * All of the actors that make the game are controlled here
 */
public class Round {
    private Ship ship;
    private ArrayList<Enemy> enemies = new ArrayList<>();
    private SpriteBatch batch;
    private OrthographicCamera camera;
    private ScoreBoard scoreBoard;

    private Texture bg;

    private ArrayList<Projectile> blasts = new ArrayList<>();

    //private ArrayList<Projectile> friendlyBlasts = new ArrayList<>();
    private float shotDelay = 0;
    private int timedShotDelay = 2;

    private float lastShot = 0;
    private float shipDelay = 0.5f;

    private int specialDelay = 8;
    private float lastRequest = 0;
    private boolean createdUFO = false;



    private boolean gameOver = false;
    private boolean won = false;
    private float ufo = 0.50f;


    // Might need to change what round instantiates and creates
    // Round for sure needs to create the enemies
    // Scoreboard and ship might be created by the game itself or gamescreen (Maybe even create a new ship every round)
    public Round(Ship ship, SpriteBatch batch, OrthographicCamera camera, ScoreBoard scoreBoard) throws FileNotFoundException {
        this.ship = ship;
        this.batch = batch;
        this.camera = camera;
        this.scoreBoard = scoreBoard;

        bg = new Texture("spaceBackgroundBig.png");

        enemies.add(EnemyFactory.getInstance().getSwarm("core/enemyPositions.csv"));
        //enemies.add(new UFO());

    }

    public void playRound(float delta){

        // Lets the clock run
        Clock.tick();

        if(enemies.size() == 0 && gameOver == false){
            System.out.println("WINNER!");
            won = true;
            // Switch the state to WIN
        }

        if(gameOver == true){
            System.out.println("LOSER!");
            // Switch the state to OVER
        }

        // Holds the dead enemies per frame
        ArrayList<Enemy> enemiesToRemove = new ArrayList<>();

        // Holds the removed projectiles per frame
        ArrayList<Projectile> blastDump = new ArrayList<>();

        // User control
        handleInput(delta);
        System.out.println("Wang");

        // Update ship's movements
        ship.update(delta);


        shotDelay += delta;


        // This is a bit wonky
        // Might need to change the algorithm behind the alien's shot behaviour

        // Lets eligible aliens shoot every few seconds
        if(shotDelay > timedShotDelay ) {
            for(Enemy enemy : enemies){
                if(enemy instanceof Alien){
                    ((Alien) enemy).shoot(blasts);
                    shotDelay = 0;
                }
            }
        }



        // Updates the enemies' movements
        updateEnemies(delta);

        // Updates the movements of the projectiles
        updateProjectile(delta, blastDump);

        // Checks for collisions between the projectiles, ship and aliens
        checkCollisions();

        // Checks whether the player has lost all their lives
        if(ship.actuallyDead()){
            gameOver = true;
            System.out.println("Game Over");
        }

        // Removal of enemies
        removeEnemies(enemiesToRemove);

        // Removal of all projectiles
        blasts.removeAll(blastDump);

        // Renders everything on screen
        render(delta);

        // UFO is added if certain conditions are met
        generateUFO();




    }

    public void dispose(){
        batch.dispose();
        ship.dispose();
    }

    private void handleInput(float delta){
        // User control
        if(Gdx.input.isKeyPressed(Input.Keys.LEFT) && ship.getX() > 0){
            ship.moveLeft(delta);
        }else if(Gdx.input.isKeyPressed(Input.Keys.RIGHT) && ship.getX() < SpaceInvaders.GAME_WIDTH - ship.getWidth()){
            ship.moveRight(delta);
        }

        // Sets a limit on how many shots a ship can make at a time
        if(Gdx.input.isKeyJustPressed(Input.Keys.SPACE) && (Clock.getTime() - lastShot > shipDelay || lastShot == 0)){
            ship.shoot(blasts);
            lastShot = Clock.getTime();
        }
    }

    public void render(float delta){
        // Drawing on screen
        batch.setProjectionMatrix(camera.combined);
        batch.begin();

        //batch.draw(bg, 60, 0, SpaceInvaders.GAME_WIDTH - 120, SpaceInvaders.GAME_HEIGHT);
        batch.draw(bg, 0, 0, SpaceInvaders.GAME_WIDTH, SpaceInvaders.GAME_HEIGHT);

        scoreBoard.render(batch);
        ship.draw(batch, delta);
        for(Projectile blast : blasts){
            blast.render(batch);
        }

        for(Enemy enemy : enemies){
            enemy.draw(batch,delta);
        }

        batch.end();
    }

    /**
     * Updates the movements of the enemies
     * @param delta delta time
     */
    private void updateEnemies(float delta){
        for(Enemy enemy : enemies){
            enemy.update(delta);
            if(enemy.reachedGoal()){
                System.out.println("Reached goal");
                gameOver = true;
            }
        }
    }

    /**
     * Updates the movement of the projectile and checks for collisions between objects and projectiles
     * @param delta delta time
     * @param blastDump the arraylist for projectiles that are going to be removed
     * @return A list of aliens that have been killed
     */
    private void updateProjectile(float delta, ArrayList<Projectile> blastDump){
        for(Projectile blast : blasts){
            if(blast.remove) {
                blastDump.add(blast);
            }
            blast.update(delta);


        }
    }

    /**
     *  Checks for blast collisions with aliens or the ship
     */
    private void checkCollisions(){
        for(Projectile blast : blasts){
            if(blast instanceof ShipProjectile){
                for(Enemy enemy : enemies){

                    // AlienGroups handle the removal differently
                    if(enemy instanceof AlienGroup){
                        // Every alien in the AlienGroup that has died is saved to aliensToRemove
                        ArrayList<Alien> aliensToRemove = ((AlienGroup) enemy).checkCollisions(blast, scoreBoard);

                        // The dead aliens are then passed back to the AlienGroup, so that they can be removed by the AlienGroup
                        ((AlienGroup) enemy).removeEnemies(aliensToRemove);
                    }else{
                        if(blast.collideWith(enemy.getHitBox())) {
                            enemy.kill();
                            scoreBoard.updateScore(enemy.getScore());
                        }
                    }
                }
            }else if(blast instanceof AlienProjectile){
                if(blast.collideWith(ship.getHitBox()) && !ship.isInvincible()){
                    System.out.println("Ah shit");
                    ship.kill();
                    scoreBoard.updateLives();

                }
            }
        }
    }

    public boolean won(){
        return won;
    }

    public boolean gameOver(){
        return gameOver;
    }

    /**
     * Removes dead enemies from the round
     * @param enemiesToRemove enemies that have been killed
     */
    public void removeEnemies(ArrayList<Enemy> enemiesToRemove){
        for(Enemy enemy : enemies){
            if(enemy.dead()){

                // Resets the boolean value so that a new UFO can be created
                if(enemy instanceof UFO){
                    createdUFO = false;
                }
                enemiesToRemove.add(enemy);
                enemy.dispose();
            }
        }

        enemies.removeAll(enemiesToRemove);
    }

    /**
     * Adds a UFO to the round when certain conditions are met
     */
    private void generateUFO(){

        // Every "specialDelay" seconds, there is a chance a UFO will appear
        if(Clock.getTime() - lastRequest > specialDelay && enemies.size() > 0){
            Random random = new Random();

            // UFOs are then created by chance
            if(createdUFO == false && random.nextFloat() < ufo){
                enemies.add(new UFO());
                createdUFO = true;
            }else{
                System.out.println("Either there's still a ufo or it randomly didn't appear");
            }

            // Regardless of the outcome, the lastRequest will always be set to the current time
            lastRequest = Clock.getTime();
        }
    }

}
