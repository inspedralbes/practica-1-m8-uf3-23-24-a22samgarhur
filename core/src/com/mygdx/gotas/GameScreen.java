package com.mygdx.gotas;

import java.util.Iterator;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.TimeUtils;

public class GameScreen implements Screen {

    final Gotas game;
    Texture dropImage;
    Texture piedrasImage;
    Texture bucketImage;
    Texture fondoActual;
    Sound dropSound;
    Sound piedraSound;
    Music rainMusic;
    OrthographicCamera camera;
    Rectangle bucket;
    Array<Rectangle> raindrops;
    long lastDropTime;
    Array<Rectangle> caidapiedras;
    long tiempoPiedras;
    int gotasCogidas;
    int Vidas=3;
    int gotasPerdidas=0;
    int velocidadCaidaGotas = 200;
    int cantidadgotas = 800000000;

    int velocidadCaidaPiedras = 50;
    long cantidadPiedras = 3100000000L;


    public GameScreen(final Gotas game) {
        this.game = game;

        // Cargamos las imagenes cubo,las gotas y piedras de la clase AssetsManager que viene con GDX , 64x64 pixels each
        dropImage = game.assetManager.get("droplet.png", Texture.class);
        piedrasImage = game.assetManager.get("piedra.png", Texture.class);
        bucketImage = game.assetManager.get("bucket.png", Texture.class);

        // Cargamos los sonidos iniciales de la clase AssetsManager que viene con GDX

        dropSound=game.assetManager.get("drop.wav", Sound.class);
        piedraSound=game.assetManager.get("hit.wav", Sound.class);
        rainMusic=game.assetManager.get("rain.mp3", Music.class);
        rainMusic.setLooping(true);

        // Inicializa el fondo actual de la clase AssetsManager que viene con GDX
        fondoActual = game.assetManager.get("lluvia.jpg", Texture.class);

        // create the camera and the SpriteBatch
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);

        // create a Rectangle to logically represent the bucket
        bucket = new Rectangle();
        bucket.x = 800 / 2 - 64 / 2; // center the bucket horizontally
        bucket.y = 20; // bottom left corner of the bucket is 20 pixels above
        // the bottom screen edge
        bucket.width = 64;
        bucket.height = 64;

        // create the raindrops array and spawn the first raindrop
        raindrops = new Array<Rectangle>();
        spawnRaindrop();

        // Creas un array de caida de piedras y lo enseña
        caidapiedras = new Array<Rectangle>();
        mostrarLluviaPiedras();

    }

    private void spawnRaindrop() {
        Rectangle raindrop = new Rectangle();
        raindrop.x = MathUtils.random(0, 800 - 64);
        raindrop.y = 480;
        raindrop.width = 64;
        raindrop.height = 64;
        raindrops.add(raindrop);
        lastDropTime = TimeUtils.nanoTime();
    }

    private void mostrarLluviaPiedras() {
        Rectangle caidaPiedra = new Rectangle();
        caidaPiedra.x = MathUtils.random(0, 800 - 64);
        caidaPiedra.y = 480;
        caidaPiedra.width = 64;
        caidaPiedra.height = 64;
        caidapiedras.add(caidaPiedra);
        tiempoPiedras = TimeUtils.nanoTime();
    }

    @Override
    public void render(float delta) {

        ScreenUtils.clear(0, 0, 0.2f, 1);

        // tell the camera to update its matrices.
        camera.update();

        // tell the SpriteBatch to render in the
        // coordinate system specified by the camera.
        game.batch.setProjectionMatrix(camera.combined);

        // begin a new batch and draw the bucket and
        // all drops
        game.batch.begin();

        // Dibuja el fondo
        game.batch.draw(fondoActual, 0, 0, 800, 480);
        game.font2.draw(game.batch, "Gotas recolectadas: " + gotasCogidas, 0, 480);
        game.font2.draw(game.batch, "Gotas falladas: " + gotasPerdidas, 0, 450);
        game.font2.draw(game.batch, "Vidas: " + Vidas, 0, 420);
        game.batch.draw(bucketImage, bucket.x, bucket.y, bucket.width, bucket.height);
        for (Rectangle raindrop : raindrops) {
            game.batch.draw(dropImage, raindrop.x, raindrop.y);
        }

        for (Rectangle caidaprieda : caidapiedras) {
            game.batch.draw(piedrasImage, caidaprieda.x, caidaprieda.y,64,64);
        }
        game.batch.end();

        // process user input
        if (Gdx.input.isTouched()) {
            Vector3 touchPos = new Vector3();
            touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            camera.unproject(touchPos);
            bucket.x = touchPos.x - 64 / 2;
            bucket.y = touchPos.y - 64 / 2;
        }

        //Determinamos que hace cuando pulsamos la tecla derecha,izquierda, arriba o abajo
        if (Gdx.input.isKeyPressed(Keys.LEFT))
            bucket.x -= 500 * Gdx.graphics.getDeltaTime();
        if (Gdx.input.isKeyPressed(Keys.RIGHT))
            bucket.x += 500 * Gdx.graphics.getDeltaTime();
        if (Gdx.input.isKeyPressed(Input.Keys.UP))
            bucket.y += 500 * Gdx.graphics.getDeltaTime();
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN))
            bucket.y -= 500 * Gdx.graphics.getDeltaTime();

        // make sure the bucket stays within the screen bounds
        if (bucket.x < 0)
            bucket.x = 0;
        if (bucket.x > 800 - 64)
            bucket.x = 800 - 64;

        // check if we need to create a new raindrop
        if (TimeUtils.nanoTime() - lastDropTime > cantidadgotas)
            spawnRaindrop();

        // move the raindrops, remove any that are beneath the bottom edge of
        // the screen or that hit the bucket. In the later case we increase the
        // value our drops counter and add a sound effect.
        Iterator<Rectangle> iter = raindrops.iterator();
        while (iter.hasNext()) {
            Rectangle raindrop = iter.next();
            raindrop.y -= velocidadCaidaGotas * Gdx.graphics.getDeltaTime();
            if (raindrop.y + 64 < 0) {
                iter.remove();
                gotasPerdidas++;//suma al contador las gotas que fallas y pasan de la pantalla
            }

            if (raindrop.overlaps(bucket)) {
                gotasCogidas++;
                dropSound.play();
                iter.remove();

                if (gotasCogidas <= 10) {

                }
                else if (gotasCogidas ==11) {
                    cambiarMusica("Peace sells.mp3");
                    cambiarDificultad(400,400000000,"tormenta.jpg");
                    spawnRaindrop();


                } else if (gotasCogidas ==41){
                    cambiarMusica("Points.mp3");
                    cambiarDificultad(600,200000000,"calavera.png");
                    spawnRaindrop();

                }
            }

        }

        // Va creando piedras
        if (TimeUtils.nanoTime() - tiempoPiedras > cantidadPiedras)
            mostrarLluviaPiedras();


        // Va iterando para borrar o crear piedras segun si tocan abajo o si chocan con el cubo
        Iterator<Rectangle> iter2 = caidapiedras.iterator();
        while (iter2.hasNext()) {
            Rectangle caidapiedra = iter2.next();
            caidapiedra.y -= velocidadCaidaPiedras * Gdx.graphics.getDeltaTime();
            if (caidapiedra.y + 64 < 0) {
                iter2.remove();
            }

            if (caidapiedra.overlaps(bucket)) {
                Vidas--;
                piedraSound.play();
                iter2.remove();

            }
        }
        if (Vidas==0) {
            game.setScreen(new FinishScreen(game,gotasCogidas,gotasPerdidas));
            dispose();
        }
    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void show() {
        // start the playback of the background music
        // when the screen is shown
        rainMusic.play();
    }

    @Override
    public void hide() {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void dispose() {
        dropImage.dispose();
        bucketImage.dispose();
        dropSound.dispose();
        rainMusic.dispose();
        game.assetManager.dispose();

    }

    private void cambiarMusica(String cancion) {
        if (rainMusic.isPlaying()) {
            rainMusic.stop();
            rainMusic.dispose();
            rainMusic = game.assetManager.get(cancion, Music.class);;
            rainMusic.setLooping(true);
            rainMusic.play();
        }
    }

    private void cambiarDificultad(int caida,int cantidad,String fondo) {
        fondoActual.dispose();
        fondoActual = game.assetManager.get(fondo, Texture.class);;
        velocidadCaidaGotas =caida;
        cantidadgotas=cantidad;

    }


}
