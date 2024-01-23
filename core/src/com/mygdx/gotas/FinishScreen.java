package com.mygdx.gotas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.ScreenUtils;


public class FinishScreen implements Screen{
    final Gotas game;
    OrthographicCamera camera;
    int gotasCogidas;
    int gotasFalladas;

    Texture fondo;

    public FinishScreen(Gotas game, int gotasCogidas, int gotasFalladas) {
        this.game = game;
        this.gotasCogidas = gotasCogidas;
        this.gotasFalladas = gotasFalladas;

        fondo = new Texture(Gdx.files.internal("the_end.jpg"));

        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);

    }

    public FinishScreen(Gotas game) {
        this.game = game;


        fondo = new Texture(Gdx.files.internal("the_end.jpg"));

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
        game.font3.draw(game.batch, "Has perdido", 170, 450);
        game.font.draw(game.batch, "Gotas recogidas: "+gotasCogidas, 170, 100);
        game.font.draw(game.batch, "Gotas sin recoger: "+gotasFalladas, 170, 60);
        game.batch.end();

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
