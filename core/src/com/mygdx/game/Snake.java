package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.audio.Sound;

public class Snake {
    private GameScreen gameScreen;
    private static final int SNAKE_SIZE = 30;
    private static final int SCREEN_WIDTH = 720;
    private static final int SCREEN_HEIGHT = 720;
    private static final float SPEED = 5.0f;

    private Array<Rectangle> segments;
    private Direction direction;

    private Apple apple;
    private Texture headTexture;
    private Sprite headSprite;
    private Texture skinTexture;
    private Sprite skinSprite;

    private Sound eatAppleSound;


    public enum Direction {
        UP, DOWN, LEFT, RIGHT;

        public boolean isOpposite(Direction other) {
            return this == UP && other == DOWN ||
                    this == DOWN && other == UP ||
                    this == LEFT && other == RIGHT ||
                    this == RIGHT && other == LEFT;
        }
    }

    public Snake(GameScreen gameScreen) {
        this.gameScreen = gameScreen;
        spawnApple();
        segments = new Array<>();
        direction = Direction.RIGHT;
        headTexture = new Texture("snake_head_right.png");
        headSprite = new Sprite(headTexture);
        headSprite.setSize(SNAKE_SIZE, SNAKE_SIZE);
        skinTexture = new Texture("skin.png");
        skinSprite = new Sprite(skinTexture);
        skinSprite.setSize(SNAKE_SIZE, SNAKE_SIZE);
        eatAppleSound = Gdx.audio.newSound(Gdx.files.internal("appleSound.mp3"));
        for (int i = 0; i < 6; i++) {
            addSegment(100 + i * SNAKE_SIZE, 100);
        }
    }

    public Snake(GameScreen gameScreen, int size) {
        this.gameScreen = gameScreen;
        spawnApple();
        segments = new Array<>();
        direction = Direction.UP;
        headTexture = new Texture("snake_head_up.png");
        headSprite = new Sprite(headTexture);
        headSprite.setSize(SNAKE_SIZE, SNAKE_SIZE);
        skinTexture = new Texture("skin.png");
        skinSprite = new Sprite(skinTexture);
        skinSprite.setSize(SNAKE_SIZE, SNAKE_SIZE);
        eatAppleSound = Gdx.audio.newSound(Gdx.files.internal("appleSound.mp3"));
        for (int i = 0; i < size; i++) {
            addSegment(300 + i * SNAKE_SIZE, 800);
        }

    }


    private void addSegment(float x, float y) {
        Rectangle newSegment = new Rectangle(x, y, SNAKE_SIZE, SNAKE_SIZE);
        segments.add(newSegment);
    }

    public void move() {
        // Move a cobra
        for (int i = segments.size - 1; i > 0; i--) {
            Rectangle currentSegment = segments.get(i);
            Rectangle previousSegment = segments.get(i - 1);
            currentSegment.x = previousSegment.x;
            currentSegment.y = previousSegment.y;
        }

        Rectangle head = getHead();
        switch (direction) {
            case UP:
                head.y += SPEED;
                break;
            case DOWN:
                head.y -= SPEED;
                break;
            case LEFT:
                head.x -= SPEED;
                break;
            case RIGHT:
                head.x += SPEED;
                break;
        }

        // colisao no proprio corpo


        for (int i = 1; i < segments.size; i++) {
            Rectangle currentSegment = segments.get(i);
            if (i > 12 && head.overlaps(currentSegment)) {
                gameOver();
                return;
            }
        }

        // pegou maça
        if (head.overlaps(apple.getBounds())) {
            eatAppleSound.play();
            grow();

            boolean spawned = true;
            while(spawned){
                spawned = false;
                spawnApple();

                Rectangle spawnedApple = apple.getBounds();
                for (int i = 1; i < segments.size; i++) {
                    Rectangle currentSegment = segments.get(i);
                    if (spawnedApple.overlaps(currentSegment)) {
                        spawned = true;
                    }
                }
            }

        }


        fixHeadPosition();

    }

    private void spawnApple() {
        float x = MathUtils.random(0, SCREEN_WIDTH - SNAKE_SIZE);
        float y = MathUtils.random(0, SCREEN_HEIGHT - SNAKE_SIZE);
        apple = new Apple(x, y, SNAKE_SIZE, SNAKE_SIZE);
    }

    private void fixHeadPosition() {
        Rectangle head = segments.first();
        if (head.x < 0) head.x = SCREEN_WIDTH - SNAKE_SIZE;
        if (head.x >= SCREEN_WIDTH) head.x = 0;
        if (head.y < 0) head.y = SCREEN_HEIGHT - SNAKE_SIZE;
        if (head.y >= SCREEN_HEIGHT) head.y = 0;
    }



    public void grow() {
        Rectangle tail = segments.peek();

        float newSegmentX = tail.x;
        float newSegmentY = tail.y;


        for(int i=0;i<3;i++){
            addSegment(newSegmentX, newSegmentY);
        }

    }

    public void render(ShapeRenderer shapeRenderer, SpriteBatch batch) {
        for (int i = segments.size - 1; i >= 0; i--) {
            Rectangle segment = segments.get(i);
            if (i == 0) {
                switch(direction){
                    case UP:
                        headTexture = new Texture("snake_head_up.png");
                        headSprite = new Sprite(headTexture);
                        headSprite.setSize(SNAKE_SIZE, SNAKE_SIZE);
                        batch.begin();
                        headSprite.setPosition(segment.x, segment.y + SNAKE_SIZE - 10);
                        headSprite.draw(batch);
                        batch.end();
                        break;
                    case DOWN:
                        headTexture = new Texture("snake_head_down.png");
                        headSprite = new Sprite(headTexture);
                        headSprite.setSize(SNAKE_SIZE, SNAKE_SIZE);
                        batch.begin();
                        headSprite.setPosition(segment.x, segment.y - SNAKE_SIZE + 10);
                        headSprite.draw(batch);
                        batch.end();
                        break;
                    case LEFT:
                        headTexture = new Texture("snake_head_left.png");
                        headSprite = new Sprite(headTexture);
                        headSprite.setSize(SNAKE_SIZE, SNAKE_SIZE);
                        batch.begin();
                        headSprite.setPosition(segment.x - SNAKE_SIZE + 10, segment.y);
                        headSprite.draw(batch);
                        batch.end();
                        break;
                    case RIGHT:
                        headTexture = new Texture("snake_head_right.png");
                        headSprite = new Sprite(headTexture);
                        headSprite.setSize(SNAKE_SIZE, SNAKE_SIZE);
                        batch.begin();
                        headSprite.setPosition(segment.x + SNAKE_SIZE - 10, segment.y);
                        headSprite.draw(batch);
                        batch.end();
                        break;
                }

            } else {
                batch.begin();
                skinSprite.setPosition(segment.x, segment.y);
                skinSprite.draw(batch);
                batch.end();
            }
        }

        apple.render(shapeRenderer, batch);
    }

    public void setDirection(Direction newDirection) {
        if (!direction.isOpposite(newDirection)) {
            direction = newDirection;
        }
    }

    public Rectangle getHead() {
        return segments.first();
    }

    public Array<Rectangle> getSegments() {
        return segments;
    }

    public int getSnakeSize() {
        return this.segments.size;
    }

    private void gameOver() {

        gameScreen.showGameOverScreen();
    }
}
