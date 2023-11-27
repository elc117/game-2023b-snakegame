package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.Screen;

import java.util.Random;

public class GameOverScreen implements Screen {
    private final MyGame game;
    private OrthographicCamera camera;
    private SpriteBatch batch;
    private BitmapFont font;
    private float buttonX, buttonY, buttonWidth, buttonHeight;
    private Texture buttonTexture;
    private Texture bgTexture;
    private float buttonPadding = 20;
    private float buttonRows = 2;
    private float buttonCols = 2;

    private Question[] questions;

    private Question currentQuestion;

    private int size;

    public GameOverScreen(final MyGame game, int size) {
        this.size = size;
        this.game = game;
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 720, 720);
        batch = new SpriteBatch();
        font = new BitmapFont();

        buttonWidth = 150;
        buttonHeight = 50;

        float totalButtonWidth = buttonCols * buttonWidth + (buttonCols - 1) * buttonPadding;
        float totalButtonHeight = buttonRows * buttonHeight + (buttonRows - 1) * buttonPadding;

        float startX = (Gdx.graphics.getWidth() - totalButtonWidth) / 2f;
        float startY = Gdx.graphics.getHeight() / 2f + totalButtonHeight / 2f - buttonHeight;

        buttonX = startX;
        buttonY = startY;

        buttonTexture = new Texture(Gdx.files.internal("button.png"));
        bgTexture = new Texture(Gdx.files.internal("bgMenu.jpeg"));

        Question[] questions = {
                new Question("Quando o Jardim Botâncio da UFSM foi fundado?", "1981", new String[]{"1981", "2005", "1975", "1995"}),
                new Question("Quantas espécies estão catalogadas no acervo do Jardim Botânico da UFSM?", "370", new String[]{"70", "523", "180", "370"}),
                new Question("Quantos hectares de área o Jardim Botânico da UFSM possui?", "13ha", new String[]{"13ha", "15ha", "10ha", "8ha"}),
                new Question("A que centro o Jardim Botânico da UFSM é suplementar?", "CCNE", new String[]{"CAL", "CT", "CCNE", "CCR"}),
                new Question("O Jardim Botânico da UFSM está localizado em qual cidade?", "Santa Maria", new String[]{"Porto Alegre", "Santa Maria", "Uruguaiana", "Pelotas"}),
        };

        Random random = new Random();
        currentQuestion = questions[random.nextInt(questions.length)];
    }

    @Override
    public void show() {
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();
        batch.setProjectionMatrix(camera.combined);

        batch.begin();

        batch.draw(bgTexture, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        font.draw(batch, currentQuestion.getText(), buttonX, buttonY + 2 * buttonHeight);

        for (int row = 0; row < buttonRows; row++) {
            for (int col = 0; col < buttonCols; col++) {
                drawCenteredTexture(batch, buttonTexture, buttonX + col * (buttonWidth + buttonPadding),
                        buttonY - row * (buttonHeight + buttonPadding), buttonWidth, buttonHeight);
            }
        }

        font.draw(batch, currentQuestion.getAnswers()[0], buttonX, buttonY + buttonHeight);
        font.draw(batch, currentQuestion.getAnswers()[1], buttonX + buttonWidth + buttonPadding, buttonY + buttonHeight);
        font.draw(batch, currentQuestion.getAnswers()[2], buttonX, buttonY - buttonPadding);
        font.draw(batch, currentQuestion.getAnswers()[3], buttonX + buttonWidth + buttonPadding, buttonY - buttonPadding);

        batch.end();

        if (Gdx.input.isTouched()) {
            float touchX = Gdx.input.getX();
            float touchY = Gdx.graphics.getHeight() - Gdx.input.getY();

            for (int row = 0; row < buttonRows; row++) {
                for (int col = 0; col < buttonCols; col++) {
                    float currentButtonX = buttonX + col * (buttonWidth + buttonPadding);
                    float currentButtonY = buttonY - row * (buttonHeight + buttonPadding);

                    if (touchX >= currentButtonX && touchX <= currentButtonX + buttonWidth &&
                            touchY >= currentButtonY && touchY <= currentButtonY + buttonHeight) {
                        if (currentQuestion.isAnswerCorrect(currentQuestion.getAnswers()[col + row * (int) buttonCols])) {
                            game.setScreen(new GameScreen(game, this.size));
                        } else {
                            game.setScreen(new MenuScreen(game));
                        }
                    }
                }
            }
        }
    }


    private void drawCenteredTexture(SpriteBatch batch, Texture texture, float x, float y, float width, float height) {
        batch.draw(texture, x, y, width, height);
    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void hide() {
    }

    @Override
    public void dispose() {
        batch.dispose();
        font.dispose();
        buttonTexture.dispose();
        bgTexture.dispose();
    }
}
