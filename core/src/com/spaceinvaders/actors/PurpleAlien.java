package com.spaceinvaders.actors;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;

public class PurpleAlien extends Alien {

    public PurpleAlien(float x, float y){
        setImg(new Texture("purpleAlien.png"));
        setSprite(new Sprite(getImg()));
        getSprite().setPosition(x,y);

        setScore(100);
        //blaster = new AlienBlaster();

        setX(getSprite().getX());
        setY(getSprite().getY());

        hitBox = new Rectangle(getX(), getY(), getSprite().getWidth(), getSprite().getHeight());
    }
}
