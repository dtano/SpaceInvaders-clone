package com.spaceinvaders.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.spaceinvaders.game.SpaceInvaders;

import java.io.FileNotFoundException;

public class GameOverScreen implements Screen {
    private SpaceInvaders game;
    private SpriteBatch batch;

    Texture gameOver;
    Texture retry;
    Texture exit;

    public GameOverScreen(SpaceInvaders game){
        this.game = game;

        gameOver = new Texture("Game-Over.png");
        exit = new Texture("Exit.png");
        retry = new Texture("retry.png");

    }

    @Override
    public void show() {

    }

    @Override
    public void render(float v) {

        int retryX = 150;
        int exitX = 450;
        game.batch.begin();
        game.batch.draw(gameOver, SpaceInvaders.GAME_WIDTH/2 - gameOver.getWidth()/2, 400);
        game.batch.draw(exit, 450, 200, 96, 39);
        game.batch.draw(retry, 150, 200,121,39);


        if(Gdx.input.getX() < retryX + retry.getWidth() && Gdx.input.getX() > retryX && SpaceInvaders.GAME_HEIGHT - Gdx.input.getY() <
                200 + retry.getHeight() && SpaceInvaders.GAME_HEIGHT - Gdx.input.getY() > 200 ){
            if(Gdx.input.isTouched()){
                game.changeState(SpaceInvaders.State.GAME);
                game.getScoreBoard().reset();
                try {
                    game.setScreen(new GameScreen(game));
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }

        if(Gdx.input.getX() < exitX + exit.getWidth() && Gdx.input.getX() > exitX && SpaceInvaders.GAME_HEIGHT - Gdx.input.getY() <
                200 + exit.getHeight() && SpaceInvaders.GAME_HEIGHT - Gdx.input.getY() > 200 ){
            if(Gdx.input.isTouched()){
                //game.changeState(SpaceInvaders.State.GAME);
                Gdx.app.exit();
            }
        }

        game.batch.end();
    }

    @Override
    public void resize(int i, int i1) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
