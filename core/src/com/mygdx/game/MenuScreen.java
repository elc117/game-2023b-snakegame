package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class MenuScreen implements Screen {
    private final MyGame game;
    private OrthographicCamera camera;
    private SpriteBatch batch;
    private BitmapFont font;
    private float buttonX, buttonY, buttonWidth, buttonHeight;
    private Texture buttonTexture;
    private Texture bgTexture;
    private Texture menuLogoTexture;  // Adiciona uma textura para o MenuLogo
    private float logoScale = 2.3f;  // Fator de escala para aumentar o MenuLogo

    public MenuScreen(final MyGame game) {
        this.game = game;
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 720, 720);
        batch = new SpriteBatch();
        font = new BitmapFont();

        buttonWidth = 200;
        buttonHeight = 70;
        buttonX = (Gdx.graphics.getWidth() - buttonWidth) / 2f;
        buttonY = Gdx.graphics.getHeight() / 2f - buttonHeight / 2f;
        buttonY -= 220;

        buttonTexture = new Texture(Gdx.files.internal("button.png"));
        bgTexture = new Texture(Gdx.files.internal("bgMenuv2.jpeg"));
        menuLogoTexture = new Texture(Gdx.files.internal("MenuLogov2.png"));  // Adiciona a textura para o MenuLogo
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

        // Desenha o MenuLogo no topo centralizado com escala
        float logoX = (Gdx.graphics.getWidth() - menuLogoTexture.getWidth() * logoScale) / 2f;
        float logoY = Gdx.graphics.getHeight() - menuLogoTexture.getHeight() * logoScale - 20;
        batch.draw(menuLogoTexture, logoX, logoY, menuLogoTexture.getWidth() * logoScale, menuLogoTexture.getHeight() * logoScale);

        drawCenteredTexture(batch, buttonTexture, buttonX, buttonY, buttonWidth, buttonHeight);

        batch.end();

        if (Gdx.input.isTouched()) {
            float touchX = Gdx.input.getX();
            float touchY = Gdx.graphics.getHeight() - Gdx.input.getY();

            if (touchX >= buttonX && touchX <= buttonX + buttonWidth &&
                    touchY >= buttonY && touchY <= buttonY + buttonHeight) {
                game.setScreen(new GameScreen(game));
                dispose();
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
        menuLogoTexture.dispose();
    }
}
