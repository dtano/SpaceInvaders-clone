package com.spaceinvaders.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.spaceinvaders.game.SpaceInvaders;

import java.io.FileNotFoundException;

public class MenuScreen implements Screen {
    private static final int PLAY_BUTTON_WIDTH = 220;
    private static final int PLAY_BUTTON_HEIGHT = 100;
    private static final int PLAY_BUTTON_Y = 300;
    SpaceInvaders game;

    Texture playButton;
    Texture exitButton;

    Texture title;
    Texture start;
    Texture exit;
    Texture bg;

    private BitmapFont font;

    public MenuScreen(SpaceInvaders game){

        this.game = game;
        //playButton = new Texture("ui/PNG/blue_button01.png");
        //exitButton = new Texture("ui/PNG/red_button01.png");

        title = new Texture("Space-Invaders.png");
        start =  new Texture("Start.png");
        exit = new Texture("Exit.png");
        bg = new Texture("spaceBackgroundBig.png");
        font = new BitmapFont();
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float v) {
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        //int x = SpaceInvaders.GAME_WIDTH/2 - PLAY_BUTTON_WIDTH/2;
        int startX = SpaceInvaders.GAME_WIDTH/2 - start.getWidth()/2;
        int exitX = SpaceInvaders.GAME_WIDTH/2 - exit.getWidth()/2;

        game.batch.begin();

        game.batch.draw(bg, 0, 0, SpaceInvaders.GAME_WIDTH, SpaceInvaders.GAME_HEIGHT);
        game.batch.draw(title, SpaceInvaders.GAME_WIDTH/2 - title.getWidth()/2, 500);
        game.batch.draw(start, SpaceInvaders.GAME_WIDTH/2 - start.getWidth()/2, 300);
        game.batch.draw(exit, SpaceInvaders.GAME_WIDTH/2 - exit.getWidth()/2, 200);


        if(Gdx.input.getX() < startX + start.getWidth() && Gdx.input.getX() > startX && SpaceInvaders.GAME_HEIGHT - Gdx.input.getY() <
                300 + start.getHeight() && SpaceInvaders.GAME_HEIGHT - Gdx.input.getY() > 300 ){
            if(Gdx.input.isTouched()){
                game.changeState(SpaceInvaders.State.GAME);

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
        dispose();
    }

    @Override
    public void dispose() {
        title.dispose();
        bg.dispose();
        exit.dispose();
        start.dispose();
        font.dispose();
    }
}
