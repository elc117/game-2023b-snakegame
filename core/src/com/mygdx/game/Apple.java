package com.mygdx.game;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;


public class Apple {
    private Rectangle bounds;
    private Texture appleTexture;
    private Sprite appleSprite;

    public Apple(float x, float y, float width, float height) {
        appleTexture = new Texture("apple.png");
        appleSprite = new Sprite(appleTexture);
        appleSprite.setSize(30, 30);
        bounds = new Rectangle(x, y, width, height);
    }

    public Rectangle getBounds() {
        return bounds;
    }

    public void render(ShapeRenderer shapeRenderer, SpriteBatch batch) {
        batch.begin();
        appleSprite.setPosition(bounds.x, bounds.y);
        appleSprite.draw(batch);
        batch.end();
    }
}

