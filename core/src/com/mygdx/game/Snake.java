package com.mygdx.game;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;

public class Snake {
    private GameScreen gameScreen;
    private static final int SNAKE_SIZE = 30;
    private static final int SCREEN_WIDTH = 720;
    private static final int SCREEN_HEIGHT = 720;
    private static final float SPEED = 5.0f;

    private Array<Rectangle> segments;
    private Direction direction;

    private Apple apple;



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
        for (int i = 0; i < 6; i++) {
            addSegment(100 + i * SNAKE_SIZE, 100);
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

        Rectangle head = segments.first();
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
            if (i >= 12 && head.overlaps(currentSegment)) {
                gameOver();
            }
        }

        // pegou maça
        if (head.overlaps(apple.getBounds())) {
            grow();
            spawnApple();
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
        if (head.x < 0) head.x = SCREEN_WIDTH - SNAKE_SIZE;
        if (head.x < 0) head.x = SCREEN_WIDTH - SNAKE_SIZE;
        if (head.x >= SCREEN_WIDTH) head.x = 0;
        if (head.y < 0) head.y = SCREEN_HEIGHT - SNAKE_SIZE;
        if (head.y >= SCREEN_HEIGHT) head.y = 0;
    }


    public void grow() {
        Rectangle tail = segments.peek();

        float newSegmentX = tail.x;
        float newSegmentY = tail.y;

        switch (direction) {
            case UP:
                newSegmentY += SNAKE_SIZE;
                break;
            case DOWN:
                newSegmentY -= SNAKE_SIZE;
                break;
            case LEFT:
                newSegmentX -= SNAKE_SIZE;
                break;
            case RIGHT:
                newSegmentX += SNAKE_SIZE;
                break;
        }
        for(int i=0;i<3;i++){
            addSegment(newSegmentX, newSegmentY);
        }

    }

    public void render(ShapeRenderer shapeRenderer) {
        for (int i = segments.size - 1; i >= 0; i--) {
            Rectangle segment = segments.get(i);
            if (i == 0) {
                shapeRenderer.setColor(Color.YELLOW);
            } else {
                shapeRenderer.setColor(Color.GREEN);
            }

            shapeRenderer.rect(segment.x, segment.y, segment.width, segment.height);
        }

        apple.render(shapeRenderer);
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

    private void gameOver() {

        gameScreen.showGameOverScreen();
    }
}
