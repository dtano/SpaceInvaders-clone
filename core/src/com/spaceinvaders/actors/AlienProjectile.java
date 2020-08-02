package com.spaceinvaders.actors;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;

public class AlienProjectile extends Projectile {

    public AlienProjectile(float x, float y){
        img = new Texture("projectile_tight (1).png");
        this.x = x;
        this.y = y;
        hitBox = new Rectangle(x,y,img.getWidth(),img.getHeight());
    }
    @Override
    public void update(float deltaTime) {
        y -= SPEED * deltaTime;
        if(y < 0){
            remove = true;
        }
        hitBox.setPosition(x,y);
}
}
