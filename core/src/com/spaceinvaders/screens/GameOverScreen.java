package com.spaceinvaders.screens;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.spaceinvaders.game.SpaceInvaders;

public class GameOverScreen implements Screen {
    private SpaceInvaders game;
    private SpriteBatch batch;

    Texture gameOver;
    Texture exit;

    public GameOverScreen(SpaceInvaders game){
        this.game = game;

        gameOver = new Texture("Game-Over.png");
        exit = new Texture("Exit.png");
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float v) {
        game.batch.begin();
        game.batch.draw(gameOver, SpaceInvaders.GAME_WIDTH/2 - gameOver.getWidth()/2, 400);
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
