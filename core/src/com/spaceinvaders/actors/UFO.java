package com.spaceinvaders.actors;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.spaceinvaders.game.SpaceInvaders;

public class UFO extends Enemy {
    private final int SPEED = 40;

    public UFO(){
        setImg(new Texture("UFO_64.png"));
        setSprite(new Sprite(getImg()));
        getSprite().setPosition(SpaceInvaders.GAME_WIDTH,650);

        setScore(200);

        setX(getSprite().getX());
        setY(getSprite().getY());

        hitBox = new Rectangle(getX(), getY(), getSprite().getWidth(), getSprite().getHeight());
    }

    int velocity = SPEED;
    int direction = -1;

    @Override
    public void draw(SpriteBatch batch) {
        getSprite().draw(batch);
    }

    @Override
    public void update(float deltaTime) {
        if(getX() + getSprite().getWidth() < 0){
            kill();
            return;
        }
        setX(getX() + velocity * direction * deltaTime);
        hitBox.setPosition(getX(), getY());
    }

    @Override
    public void reverseDirection(float delta) {

    }
}
