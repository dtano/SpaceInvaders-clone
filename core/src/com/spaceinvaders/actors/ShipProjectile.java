package com.spaceinvaders.actors;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.spaceinvaders.actors.Projectile;
import com.spaceinvaders.game.SpaceInvaders;

public class ShipProjectile extends Projectile {
    public ShipProjectile(float x, float y){
        img = new Texture("projectile_tight.png");
        this.x = x;
        this.y = y;
        hitBox = new Rectangle(x,y,img.getWidth(),img.getHeight());
    }

    public void update(float deltaTime){
        y += SPEED * deltaTime;
        if(y > SpaceInvaders.GAME_HEIGHT){
            remove = true;
        }
        hitBox.setPosition(x,y);
    }


}
