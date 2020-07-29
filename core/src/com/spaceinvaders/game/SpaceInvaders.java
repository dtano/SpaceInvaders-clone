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
	}
	private State currentState;

	
	@Override
	public void create () {
		batch = new SpriteBatch();
		ship = new Ship();
		scoreBoard = new ScoreBoard(ship.getLives());

		try {
			round = new Round(ship, batch, camera, scoreBoard);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}


		this.setScreen(new MenuScreen(this));
		currentState = State.MENU;
		camera.setToOrtho(false,GAME_WIDTH,GAME_HEIGHT);
	}

	@Override
	public void render () {
		float delta = Gdx.graphics.getDeltaTime();
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		//super.render();
		switch(currentState){
			case MENU:
				getScreen().render(delta);
				break;
			case GAME:

				try {
					setScreen(new GameScreen(this));
					getScreen().render(delta);
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
				//round.playRound(delta);
				break;
			case OVER:
			    //Screen over = new GameOverScreen(this);
			    setScreen(new GameOverScreen(this));
			    getScreen().render(delta);
                //over.render(delta);
		}
		/*
		if(currentState == State.MENU){
			getScreen().render(delta);
		}else if(currentState == State.GAME){
			round.playRound(delta);
		}

		 */
		//getScreen().render(delta);


		//round.playRound(delta);
	}
	
	@Override
	public void dispose () {
		round.dispose();

	}

	public void changeState(State state){
		currentState = state;
	}

	public Round getRound(){
		return round;
	}

	public void initRound(){
        try {
            round = new Round(ship, batch, camera, scoreBoard);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

}
