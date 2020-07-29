package com.spaceinvaders.actors;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.spaceinvaders.strategies.IBlaster;

import java.util.ArrayList;
import java.util.Random;

public abstract class Alien extends Enemy implements IBlaster {
    private final int SPEED = 10;
    private final double VELOCITY_INCREASE = 1.2;
    protected final int WALL_BOUNDARY = 60;
    protected boolean canShoot = false;


    protected float shotChance = 0.33f;

    @Override
    public void draw(SpriteBatch batch, float delta) {
        getSprite().draw(batch);
    }

    int velocity = SPEED;
    int direction = 1;
    @Override
    public void update(float deltaTime) {
        /*
        if(getY() <= 100){
            dispose();
        }

        if(getX() <= 0 + getSprite().getWidth() || getX() + getSprite().getWidth() >= SpaceInvaders.GAME_WIDTH - getSprite().getWidth() ){
            reverseDirection();
        }

         */
        setX(getX() + velocity * direction * deltaTime);
        hitBox.setPosition(getX(), getY());
    }

    public void reverseDirection(float delta){
        direction *= -1;
        velocity *= VELOCITY_INCREASE;
        setY(getY() - 40);
        setX(getX() + velocity * direction * delta);

    }

    public void setColumn(int column){
        this.column = column;
    }

    public void shoot(ArrayList<Projectile> blasts){
        Random rand = new Random();
        /*
        if(rand.nextFloat() <= shotChance){
            blasts.add(new AlienProjectile(getX() + getSprite().getWidth()/2, getY()));
        }

         */
        blasts.add(new AlienProjectile(getX() + getSprite().getWidth()/2, getY()));
    }

    public void clearedToShoot(){
        canShoot = true;
    }

    public boolean getShotClearance(){
        return canShoot;
    }



}
