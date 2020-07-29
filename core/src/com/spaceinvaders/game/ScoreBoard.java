package com.spaceinvaders.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class ScoreBoard {
    private Texture img;
    private Sprite sprite;
    private BitmapFont font;
    private int shipLives;
    private int gameScore = 0;

    public ScoreBoard(int shipLives){
        font = new BitmapFont();
        this.shipLives = shipLives;
    }

    public void updateScore(int score){
        gameScore += score;
        //updateGraphic(score);
    }

    public void updateLives(){
        shipLives--;
    }

    public void updateGraphic(int score){

    }

     public void render(SpriteBatch batch){
        font.draw(batch, "Lives: " + shipLives, 10, 15);
        font.draw(batch, "Score: " + gameScore,340,720);
    }

    public int getGameScore(){
        return gameScore;
    }

}
