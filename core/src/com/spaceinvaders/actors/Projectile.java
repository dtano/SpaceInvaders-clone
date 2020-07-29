package com.spaceinvaders.actors;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public abstract class Projectile {
    public static final int SPEED = 500;
    protected Texture img;
    protected float x;
    protected float y;
    protected Rectangle hitBox;

    public boolean remove = false;



    public abstract void update(float deltaTime);


    public void render(SpriteBatch sb){
        sb.draw(img,x,y);
    }

    public boolean collideWith(Rectangle rect){

        if(hitBox.overlaps(rect)){
            remove = true;
        }
        return hitBox.overlaps(rect);
    }

    public float getX() {
        return x;
    }

    public float getY(){ return y;}

    public void setX(float x) {
        this.x = x;
    }


    public Texture getImg() {
        return img;
    }

    public void setImg(Texture img) {
        this.img = img;
    }

    public Rectangle getHitBox(){
        return hitBox;
    }
}
