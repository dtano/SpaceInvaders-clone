package com.spaceinvaders.actors;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;

public class CrabAlien extends Alien {
    public CrabAlien(float x, float y){
        setImg(new Texture("spaceInvaders_alien1.png"));
        setSprite(new Sprite(getImg()));
        getSprite().setPosition(x,y);

        setScore(50);
        //blaster = new AlienBlaster();

        setX(getSprite().getX());
        setY(getSprite().getY());

        hitBox = new Rectangle(getX(), getY(), getSprite().getWidth(), getSprite().getHeight());
    }


}
