package com.spaceinvaders.screens;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.spaceinvaders.actors.Enemy;
import com.spaceinvaders.actors.Projectile;
import com.spaceinvaders.actors.Ship;
import com.spaceinvaders.game.Clock;
import com.spaceinvaders.game.Round;
import com.spaceinvaders.game.SpaceInvaders;

import java.io.FileNotFoundException;
import java.util.ArrayList;

public class GameScreen implements Screen {
    private SpaceInvaders game;
    private Texture bg;

    private SpriteBatch batch;
    private OrthographicCamera camera = new OrthographicCamera();;
    private Round round;

    private boolean over = false;

    // This could either be a GameOver or Won screen
    // topScreen means that it will be rendered on top of the gamescreen
    private Screen topScreen;
    private int currentLevel;


    public GameScreen(SpaceInvaders game) throws FileNotFoundException {
        this.game = game;
        bg = new Texture("spaceBackgroundBig.png");

        batch = game.batch;
        camera.setToOrtho(false,game.GAME_WIDTH,game.GAME_HEIGHT);
        round = new Round(batch, camera, game.getScoreBoard(), 1);
        currentLevel = 1;

    }

    @Override
    public void show() {

    }

    @Override
    public void render(float v) {

        batch.begin();
        batch.draw(bg, 0, 0, SpaceInvaders.GAME_WIDTH, SpaceInvaders.GAME_HEIGHT);
        game.getScoreBoard().render(batch);
        batch.end();

        round.playRound(v);
        round.render();

        if(!over){
            if(round.won()){
                Clock.reset();
                /**
                 *  1. Show level cleared screen
                 *  2. Advance to the next level once the player presses continue
                 *      - The lives is reset back to the number of lives given
                 *      - If player presses quit, then the player is brought back to the menu
                 *  3. Load new round with new alienPositions and maybe new alien speed
                 *  4. If the player loses the round, the game over screen is shown and the player is given the option
                 *     to either retry or quit
                 *  5. Case retry:
                 *          reload the first level (GameScreen might need an array of levels)
                 *          score is reset, lives reset as well
                 *  6. Case quit:
                 *          The game quits to the desktop
                 */

                /**
                 *  1st option: Set a new gameover screen, thus the gamescreen can just be discarded after use
                 */
                //round.freeze();
                game.changeState(SpaceInvaders.State.OVER);

                /**
                 *    // Probably won't work lol
                 *    round.transition(); // A method that makes the ship move upwards out of the screen into the next level
                 *    render "level cleared" text for a few seconds maybe?
                 *
                 *
                 *    if(currentLevel < SpaceInvaders.numLevels){
                 *        currentLevel++;
                 *    }
                 *
                 *    round = new Round(batch, camera, game.getScoreBoard(), currentLevel);
                 */


                /**
                 *  2nd option: Create a game over screen that will be rendered alongside the game screen
                 *  , but the game screen will be paused and it will continue to run its playRound() method.
                 */
                topScreen = new GameOverScreen(game);
                //System.out.println("GameOver screen created");
                over = true;


            }else if(round.gameOver()){
                //round.freeze();
                game.changeState(SpaceInvaders.State.OVER);
                topScreen = new GameOverScreen(game);
                over = true;

            }
        }else{
            //System.out.println("Game over screen being rendered");
            topScreen.render(v);
        }


    }

    @Override
    public void resize(int i, int i1) {

    }

    @Override
    public void pause() {
        round.freeze();
    }

    @Override
    public void resume() {
        round.resume();
        game.changeState(SpaceInvaders.State.GAME);
    }

    @Override
    public void hide() {
        dispose();
    }

    @Override
    public void dispose() {
        bg.dispose();
        round.dispose();
        if(topScreen != null){
            topScreen.dispose();
        }
    }
}
