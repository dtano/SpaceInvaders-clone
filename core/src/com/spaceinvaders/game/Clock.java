package com.spaceinvaders.game;

import com.badlogic.gdx.Gdx;

public class Clock {
    private static float timer = 0;

    public static void tick(){
        timer+=Gdx.graphics.getDeltaTime();
    }

    public static float getTime(){
        return timer;
    }

    public static void reset(){
        timer = 0;
    }

}
