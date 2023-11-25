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

    public MenuScreen(final MyGame game) {
        this.game = game;
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 720, 720);
        batch = new SpriteBatch();
        font = new BitmapFont();

        // Configuração do botão de Jogar
        buttonWidth = 200;
        buttonHeight = 70;
        buttonX = (Gdx.graphics.getWidth() - buttonWidth) / 2f;
        buttonY = Gdx.graphics.getHeight() / 2f - buttonHeight / 2f;
        buttonY -= 220;

        // Carrega a textura do botão
        buttonTexture = new Texture(Gdx.files.internal("button.png"));

        // Carrega a textura do fundo
        bgTexture = new Texture(Gdx.files.internal("bgMenu.jpeg"));
    }

    @Override
    public void show() {
        // Configurações de inicialização da tela do menu (se necessário)
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();
        batch.setProjectionMatrix(camera.combined);

        batch.begin();

        // Desenha o fundo
        batch.draw(bgTexture, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        // Desenha o botão
        drawCenteredTexture(batch, buttonTexture, buttonX, buttonY, buttonWidth, buttonHeight);

        batch.end();

        // Verifica se o botão foi tocado
        if (Gdx.input.isTouched()) {
            float touchX = Gdx.input.getX();
            float touchY = Gdx.graphics.getHeight() - Gdx.input.getY();

            // Verifica se o toque está dentro do botão
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
        // Configurações de redimensionamento da tela (se necessário)
    }

    @Override
    public void pause() {
        // Lógica de pausa da tela (se necessário)
    }

    @Override
    public void resume() {
        // Lógica de retomada da tela (se necessário)
    }

    @Override
    public void hide() {
        // Lógica de ocultação da tela (se necessário)
    }

    @Override
    public void dispose() {
        // Descarta os recursos da tela do menu (texturas, fontes, etc.)
        batch.dispose();
        font.dispose();
        buttonTexture.dispose();
        bgTexture.dispose();
    }
}
