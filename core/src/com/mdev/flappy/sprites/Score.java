package com.mdev.flappy.sprites;

import com.badlogic.gdx.graphics.Texture;

public class Score {
    public Texture cent, dec, unit;
    int value = 0;
    public Score() {
        cent = new Texture("0.png");
        dec = new Texture("0.png");
        unit = new Texture("0.png");
    }

    public void increaseValue() {
        ++value;
        int number = value;
        unit = new Texture(number % 10 + ".png");
        number = number / 10;
        dec = new Texture(number % 10 + ".png");
        number = number / 10;
        cent = new Texture(number % 10 + ".png");

    }
}
