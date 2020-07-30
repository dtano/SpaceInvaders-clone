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
    //private OrthographicCamera camera = new OrthographicCamera();;
    //private ScoreBoard scoreBoard;


    public GameScreen(SpaceInvaders game) throws FileNotFoundException {
        this.game = game;
        bg = new Texture("spaceBackgroundBig.png");

        batch = game.batch;
        //scoreBoard = new ScoreBoard(ship.getLives());
        camera.setToOrtho(false,game.GAME_WIDTH,game.GAME_HEIGHT);
        //round = game.getRound();
        round = new Round(batch, camera, game.getScoreBoard());



    }

    @Override
    public void show() {

    }

    @Override
    public void render(float v) {
        //round.render()

        batch.begin();
        //batch.draw(bg, 60, 0, SpaceInvaders.GAME_WIDTH - 120, SpaceInvaders.GAME_HEIGHT);
        batch.draw(bg, 0, 0, SpaceInvaders.GAME_WIDTH, SpaceInvaders.GAME_HEIGHT);
        game.getScoreBoard().render(batch);
        batch.end();

        round.playRound(v);
        //round.render();

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
            //round.freeze();
            game.changeState(SpaceInvaders.State.OVER);


        }else if(round.gameOver()){
            game.changeState(SpaceInvaders.State.OVER);

        }
        //batch.end();
    }

    @Override
    public void resize(int i, int i1) {

    }

    @Override
    public void pause() {
        batch.begin();
        //batch.draw(bg, 60, 0, SpaceInvaders.GAME_WIDTH - 120, SpaceInvaders.GAME_HEIGHT);
        batch.draw(bg, 0, 0, SpaceInvaders.GAME_WIDTH, SpaceInvaders.GAME_HEIGHT);
        game.getScoreBoard().render(batch);
        batch.end();

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
    }
}
