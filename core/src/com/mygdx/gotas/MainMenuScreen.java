package com.mygdx.gotas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.ScreenUtils;

public class MainMenuScreen implements Screen {

    final Gotas game;
    OrthographicCamera camera;

    Texture fondo;
    public MainMenuScreen(final Gotas game) {
        this.game = game;


        // Inicializa el fondo actual
        fondo = new Texture(Gdx.files.internal("totoro.jpg"));

        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0.2f, 1);

        camera.update();
        game.batch.setProjectionMatrix(camera.combined);

        game.batch.begin();
        game.batch.draw(fondo, 0, 0, 800, 480);
        game.font.draw(game.batch, "Bienvenido al juego de las gotas", 170, 470);
        game.font.draw(game.batch, "Pulsa cualquier boton para empezar!", 140, 80);
        game.batch.end();

        if (Gdx.input.isTouched()) {
            game.setScreen(new GameScreen(game));
            dispose();

        }
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

    }
}
