package com.mygdx.gotas;

import java.util.Random;

public class Methods {

    // Mètode que torna un float aleatori entre un mínim i un màxim
    public static float randomFloat(float min, float max) {
        Random r = new Random();
        return r.nextFloat() * (max - min) + min;

    }

}
