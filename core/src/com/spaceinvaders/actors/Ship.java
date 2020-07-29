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

public class Ship extends MovingObject implements IBlaster {
    private boolean reset = false;
    private static final int SPEED = 150;

    private int lives = 3;
    private int deathDelay = 3;
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
        if(reset && Clock.getTime() - lastDeath > deathDelay){
            System.out.println("Invincibility over");
            reset = false;
            lastDeath = 0;
        }

        if(reset == true){
            alphaModulation = +5f*(float)Math.sin(alpha) + .5f;
        }

        // This is for damage flicker
        /**
         * import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
         * import com.badlogic.gdx.scenes.scene2d.actions.Actions;
         *
         * if (playerDamaged) {
         *     SequenceAction flicker = new SequenceAction(Actions.fadeOut(0.25f), Actions.fadeIn(0.25f));
         *     player.addAction(Actions.repeat(6, flicker));
         * }
         */



        hitBox.setPosition(getX(), getY());



    }

    @Override
    public void action() {

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

    public void kill(){
        if(reset == false){
            lives--;
            reset = true;

            setX(SpaceInvaders.GAME_WIDTH/2 - getSprite().getWidth()/2);
            setY(20);
            lastDeath = Clock.getTime();

            //SequenceAction flicker = new SequenceAction(Actions.fadeOut(0.25f), Actions.fadeIn(0.25f));
            //this.addAction(Actions.repeat(6, flicker));
        }

        //lives--;

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
