package com.spaceinvaders.actors;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;

public class IceAlien extends Alien {
    public IceAlien(float x, float y){
        setImg(new Texture("iceAlien.png"));
        setSprite(new Sprite(getImg()));
        getSprite().setPosition(x,y);

        setScore(150);
        shotChance = 0.95f;

        setX(getSprite().getX());
        setY(getSprite().getY());

        hitBox = new Rectangle(getX(), getY(), getSprite().getWidth(), getSprite().getHeight());
    }
}
