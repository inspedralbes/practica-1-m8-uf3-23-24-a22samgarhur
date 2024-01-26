package com.mygdx.gotas.objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.mygdx.gotas.Gotas;
import com.mygdx.gotas.Settings;

public class Bucket extends Actor {
    final Gotas game;

    // Diferents posicions de l'Spacecraft: recta, pujant i baixant
    public static final int BUCKET_RECTO = 0;
    public static final int BUCKET_DERECHO = 1;
    public static final int BUCKET_IZQUIERDO = 2;

    public static final float VELOCIDAD_MOVIMIENTO = 50;

    // Paràmetres del bucket
    private Vector2 position;
    private int width, height;
    private int direction;
    private Rectangle collisionRect;

    public Bucket(Gotas game, float x, float y, int width, int height) {
        this.game = game;

        // Inicialitzem els arguments segons la crida del constructor
        this.width = width;
        this.height = height;
        position = new Vector2(x, y);

        // Inicialitzem el BUCKET a l'estat normal
        direction = BUCKET_RECTO;

        // Creem el rectangle de col·lisions
        collisionRect = new Rectangle();

    }

    public void act(float delta) {

        // Movem el bucket depenent de la direcció controlant que no surti de la pantalla
        switch (direction) {
            case BUCKET_IZQUIERDO:
                if (this.position.x - Settings.BUCKET_VELOCIDAD * delta >= 0) {
                    this.position.x -= Settings.BUCKET_VELOCIDAD * delta;
                }
                break;
            case BUCKET_DERECHO:
                if (this.position.x + width + Settings.BUCKET_VELOCIDAD * delta <= Settings.JUEGO_ANCHO) {
                    this.position.x += Settings.BUCKET_VELOCIDAD * delta;
                }
                break;
            case BUCKET_RECTO:
                break;
        }

        collisionRect.set(position.x, position.y + 3, width, 10);
        setBounds(position.x, position.y ,width,height);


    }

    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        batch.draw(game.assetManager.get("bucket.png", Texture.class), position.x, position.y, width, height);
    }

    // Getters dels atributs principals
    public float getX() {
        return position.x;
    }

    public float getY() {
        return position.y;
    }

    public void setX(float x) {
        this.position.x = x;
    }


    public void setY(float y) {
        this.position.y = y;
    }

    public Rectangle getCollisionRect() {
        return collisionRect;
    }
    ;


    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }

}
