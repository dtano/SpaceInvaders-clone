package com.spaceinvaders.strategies;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.spaceinvaders.actors.MovingObject;
import com.spaceinvaders.actors.Projectile;

import java.util.ArrayList;

public interface IBlaster {
    public void shoot(ArrayList<Projectile> blasts);


}
