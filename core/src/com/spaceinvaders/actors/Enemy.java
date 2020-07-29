package com.spaceinvaders.actors;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.spaceinvaders.strategies.IBlaster;

import java.util.ArrayList;

public abstract class Enemy extends MovingObject{
    protected int score;
    protected boolean killed = false;
    protected int velocity;
    protected int direction = 1;
    protected int column;
    protected boolean reachEnd = false;

    public abstract void draw(SpriteBatch batch, float delta);

    public abstract void update(float deltaTime);

    public abstract void action();

    public void updateScore(int scoreBoard){
        scoreBoard += score;

    }

    public abstract void reverseDirection(float delta);

    public boolean reachedGoal(){
        return reachEnd;
    }



    public int getScore(){
        return score;
    }

    public void setScore(int score){
        this.score = score;
    }

    public void kill(){
        killed = true;
    }

    public boolean dead(){
        return killed;
    }

    public void setColumn(int column){
        this.column = column;
    }

    public int getColumn(){
        return column;
    }


}
