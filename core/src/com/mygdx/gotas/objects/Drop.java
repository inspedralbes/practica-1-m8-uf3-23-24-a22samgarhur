package com.mygdx.gotas.objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.mygdx.gotas.Gotas;
import com.mygdx.gotas.Methods;
import com.mygdx.gotas.Settings;


import java.util.Random;

public class Drop extends Actor {

    final Gotas game;

    private Vector2 position;
    private int velocity;
    private int width, height;

    private float runTime;
    private Circle collisionCircle;

    protected boolean leftOfScreen;

    public Drop(Gotas juego,float x, float y, int width, int height,int velocity) {

        this.game = juego;
        this.velocity = velocity;
        // Inicialitzem els arguments segons la crida del constructor
        this.width = width;
        this.height = height;
        position = new Vector2(x, y);

        // Creem el cercle
        collisionCircle = new Circle();
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        position.x += velocity*delta;

        if(position.x + width<0){
            leftOfScreen = true;

        }

        // Actualitzem el cercle de col·lisions (punt central de l'asteroide i del radi).
        collisionCircle.set(position.x + width / 2.0f, position.y + width / 2.0f, width / 2.0f);
    }




    public void reset(float newX) {
        position.x = newX;
        leftOfScreen = false;
        // Obtenim un número al·leatori entre MIN i MAX
        float newSize = Methods.randomFloat(Settings.MIN_ASTEROID, Settings.MAX_ASTEROID);

        // La posició serà un valor aleatòri entre 0 i l'alçada de l'aplicació menys l'alçada
        position.y = new Random().nextInt(Settings.JUEGO_ALTO - (int) height);

    }

    // Getter pel runTime
    public float getRunTime() {

        return runTime;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        batch.draw(game.assetManager.get("bucket.png", Texture.class), position.x, position.y, width, height);
    }

    // Retorna true si hi ha col·lisió
    public boolean collides(Bucket bucket) {

        if (position.x <= bucket.getX() + bucket.getWidth()) {
            // Comprovem si han col·lisionat sempre que la gota es trobi a la mateixa alçada que el bucket
            return (Intersector.overlaps(collisionCircle, bucket.getCollisionRect()));
        }
        return false;
    }
}

