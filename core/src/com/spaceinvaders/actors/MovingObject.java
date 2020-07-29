package com.spaceinvaders.actors;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;

public abstract class MovingObject extends Actor {
    private Sprite sprite;
    private Texture img;
    protected Rectangle hitBox;

    private float x;
    private float y;
    protected boolean killed = false;

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
        sprite.setX(x);
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
        sprite.setY(y);
    }

    public abstract void draw(SpriteBatch batch);

    public abstract void update(float deltaTime);

    public abstract void action();

    public abstract void kill();

    public boolean dead(){
        return killed;
    }

    public Texture getImg() {
        return img;
    }

    public void setImg(Texture img) {
        this.img = img;
    }

    public Sprite getSprite() {
        return sprite;
    }

    public void setSprite(Sprite sprite) {
        this.sprite = sprite;
    }

    public void dispose(){

        img.dispose();

    }

    public boolean collideWith(Rectangle rect){
        return hitBox.overlaps(rect);
    }

    public Rectangle getHitBox(){
        return hitBox;
    }

    public void setPosition(float x, float y){
        this.x = x;
        this.y = y;
    }
}
