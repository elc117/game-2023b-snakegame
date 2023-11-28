package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class GameScreen extends ScreenAdapter {
    private final MyGame game;

    private Snake snake;
    private OrthographicCamera camera;
    private SpriteBatch batch;
    private ShapeRenderer shapeRenderer;
    private Texture background;

    private float timeBetweenClicks = 0.2f;
    private float timer = 0;


    public GameScreen(final MyGame game) {
        this.game = game;
        camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch = new SpriteBatch();
        shapeRenderer = new ShapeRenderer();
        background = new Texture("bgGame.jpg");
        snake = new Snake(this);
    }

    public GameScreen(final MyGame game, int size) {
        this.game = game;
        camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch = new SpriteBatch();
        shapeRenderer = new ShapeRenderer();
        background = new Texture("bgGame.jpg");
        snake = new Snake(this, size);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // desenha fundo
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        batch.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch.end();

        // desenha a cobra
        shapeRenderer.setProjectionMatrix(camera.combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        snake.render(shapeRenderer);
        shapeRenderer.end();

        timer += delta;

        handleInput();
        snake.move();
    }

    private void handleInput() {
        if (Gdx.input.isKeyPressed(Input.Keys.UP) && timer >= timeBetweenClicks) {
            snake.setDirection(Snake.Direction.UP);
            resetTimer();
        } else if (Gdx.input.isKeyPressed(Input.Keys.DOWN) && timer >= timeBetweenClicks) {
            snake.setDirection(Snake.Direction.DOWN);
            resetTimer();
        } else if (Gdx.input.isKeyPressed(Input.Keys.LEFT) && timer >= timeBetweenClicks) {
            snake.setDirection(Snake.Direction.LEFT);
            resetTimer();
        } else if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) && timer >= timeBetweenClicks) {
            snake.setDirection(Snake.Direction.RIGHT);
            resetTimer();
        }
    }

    private void resetTimer() {
        timer = 0;
    }

    @Override
    public void resize(int width, int height) {
        camera.setToOrtho(false, width, height);
    }

    @Override
    public void dispose() {
        batch.dispose();
        background.dispose();
        shapeRenderer.dispose();
    }

    public void showGameOverScreen() {
        game.setScreen(new GameOverScreen(game, this.snake.getSnakeSize()));
    }
}

