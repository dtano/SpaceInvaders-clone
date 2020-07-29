package com.spaceinvaders.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.spaceinvaders.actors.Ship;
import com.spaceinvaders.screens.GameOverScreen;
import com.spaceinvaders.screens.GameScreen;
import com.spaceinvaders.screens.MenuScreen;

import java.io.FileNotFoundException;

public class SpaceInvaders extends Game {
	public static final int GAME_WIDTH = 720;
	public static final int GAME_HEIGHT = 750;
	public static final int WALL_BOUNDARY = 80;


	public SpriteBatch batch;

	private Ship ship;
	private ScoreBoard scoreBoard;
	private Round round;
	private OrthographicCamera camera = new OrthographicCamera();
	public enum State{
		MENU,
		GAME,
		OVER,
		WIN
	}
	private State currentState;

	
	@Override
	public void create () {
		batch = new SpriteBatch();
		ship = new Ship();
		scoreBoard = new ScoreBoard(ship.getLives());

		/*
		try {
			round = new Round(batch, camera, scoreBoard);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		 */


		this.setScreen(new MenuScreen(this));
		currentState = State.MENU;
		camera.setToOrtho(false,GAME_WIDTH,GAME_HEIGHT);
	}

	@Override
	public void render () {
		float delta = Gdx.graphics.getDeltaTime();
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		//getScreen().render(delta);



		// This automatically calls the render() method of the current set screen which is pretty nifty
		super.render();

		/*
		switch(currentState){
			case MENU:
				getScreen().render(delta);
				break;
			case GAME:

				try {
					//getScreen().hide();
					setScreen(new GameScreen(this));
					getScreen().render(delta);
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
				//round.playRound(delta);
				break;
			case OVER:
				getScreen().hide();
			    Screen over = new GameOverScreen(this);
			    setScreen(over);
			    //getScreen().render(delta);
			    //getScreen().pause();
                over.render(delta);
                break;
			case WIN:
				// 1. Make a screen that displays WINNER and the options to return to menu or to exit
				// 2.
		}

		 */






	}
	
	@Override
	public void dispose () {
		round.dispose();
		getScreen().dispose();

	}

	public void changeState(State state){
		currentState = state;
	}

	public Round getRound(){
		return round;
	}

	public void initRound(){
        try {
            round = new Round(batch, camera, scoreBoard);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public ScoreBoard getScoreBoard(){
		return scoreBoard;
	}

}
