package com.spaceinvaders.actors;


import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.spaceinvaders.game.Clock;
import com.spaceinvaders.strategies.IBlaster;
import com.spaceinvaders.game.SpaceInvaders;

import java.util.ArrayList;

/**
 *  Represents the player-controlled ship
 */
public class Ship extends MovingObject implements IBlaster {

    // whether or not the ship has been killed
    private boolean reset = false;

    private static final int SPEED = 100;

    private int lives = 3;

    // How long the invincibility and flickering are in effect
    private int deathDelay = 3;

    // Time of last death
    private float lastDeath = 0;


    // For the flickering effect
    private float alpha = .0f;
    private float alphaModulation;

    public Ship(){
        setImg(new Texture("spaceInvaders_ship.png"));
        setSprite(new Sprite(getImg()));
        getSprite().setPosition(SpaceInvaders.GAME_WIDTH/2 - getSprite().getWidth()/2,40);

        setPosition(getSprite().getX(), getSprite().getY());

        hitBox = new Rectangle(getX(), getY(), getImg().getWidth(), getImg().getHeight()/2);
    }

    /**
     * Draws the ship on screen
     * @param batch the spritebatch
     */
    public void draw(SpriteBatch batch){

        if(reset == true){
            getSprite().draw(batch, alphaModulation);
            //getSprite().draw(batch, (float)Math.abs(Math.sin(alpha)));
        }else{
            getSprite().draw(batch);
        }

        /*
        ////// Render the blinking stuff here when the ship gets attacked
         */

    }





    public void update(float delta){
        alpha+=delta;

        //System.out.println(isInvincible());

        // Stops the invincibility and flickering effects once the time is more than the deathDelay
        if(reset && Clock.getTime() - lastDeath > deathDelay){
            System.out.println("Invincibility over");
            reset = false;
            lastDeath = 0;
        }

        // This is for the flickering calculation
        if(reset == true){
            alphaModulation = +5f*(float)Math.sin(alpha) + .5f;
        }

        hitBox.setPosition(getX(), getY());

    }

    public void moveLeft(float delta){
        setX(getX() - SPEED * delta);
    }

    public void moveRight(float delta){
        setX(getX() + SPEED * delta);
    }


    public void shoot(ArrayList<Projectile> blasts){
       blasts.add(new ShipProjectile(getX() + getSprite().getWidth()/2, getY() + getSprite().getHeight()/2));
    }

    public float getWidth(){
        return getSprite().getWidth();
    }

    /**
     *  This method is called when the ship is killed
     */
    public void kill(){
        if(reset == false){
            lives--;
            reset = true;

            setX(SpaceInvaders.GAME_WIDTH/2 - getSprite().getWidth()/2);
            setY(20);
            lastDeath = Clock.getTime();

        }


    }

    public boolean actuallyDead(){
        return lives == 0;
    }

    public int getLives(){
        return lives;
    }

    public boolean isInvincible(){
        return reset;
    }






}
