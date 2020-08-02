package com.spaceinvaders.actors;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

public class Explosion {
    private Animation<TextureRegion> animation;
    private float explosionTimer;

    private Rectangle hitBox;

    public Explosion(Rectangle hitBox, float totalAnimationTime){
        Texture texture = new Texture("Dead_animation.png");
        this.hitBox = hitBox;

        TextureRegion[][] textureRegion2D = TextureRegion.split(texture, texture.getWidth()/2, texture.getHeight()/2);

        TextureRegion[] textureRegion1D = new TextureRegion[16];
        int index = 0;
        for(int i = 0; i < 2; i++){
            for(int j = 0; j < 2; j++){
                textureRegion1D[index] = textureRegion2D[i][j];
                index++;
            }
        }

        animation = new Animation<>(totalAnimationTime/4, textureRegion1D);
        explosionTimer = 0;
    }

    public void update(float delta){
        explosionTimer += delta;
    }

    public void draw(SpriteBatch batch){
        batch.begin();

        if(animation.getKeyFrame(explosionTimer) != null){
            batch.draw(animation.getKeyFrame(explosionTimer),
                    hitBox.getX(),
                    hitBox.getY(),
                    hitBox.getWidth(),
                    hitBox.getHeight());
        }

        batch.end();
    }

    public boolean isFinished(){
        return animation.isAnimationFinished(explosionTimer);
    }
}
