package com.mygdx.gotas;

public class Settings {
    // Mida del joc, s'escalarà segons la necessitat
    public static final int JUEGO_ALTO = 480;
    public static final int JUEGO_ANCHO= 800;

    // Propietats de la nau
    public static final float BUCKET_VELOCIDAD = 50;
    public static final int BUCKET_ANCHO = 36;
    public static final int BUCKET_ALTO = 15;
    public static final float BUCKET_EMPIEZAX = 20;
    public static final float BUCKET_EMPIEZAY = JUEGO_ALTO/2 - BUCKET_ALTO/2;

    // Rang de valors per canviar la mida de l'asteroide
    public static final float MAX_ASTEROID = 1.5f;
    public static final float MIN_ASTEROID = 0.5f;

    // Configuració scrollable
    public static final int ASTEROID_SPEED = -150;
    public static final int ASTEROID_GAP = 75;
    public static final int BG_SPEED = -100;
}
