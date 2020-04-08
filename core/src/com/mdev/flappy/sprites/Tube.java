package com.mdev.flappy.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import java.util.Random;

/**
 * Created by Marco Rodriguez on 22/11/2016.
 */

public class Tube
{
    private static final int FLUCTUATION = 130;
    private static final int TUBE_GAP = 100;
    private static final int LOWEST_OPENING = 120;
    public static final int TUBE_WIDTH = 52;

    private Texture topTube, bottomTube;
    private Vector2 posTopTube, posBottomTube;
    public Rectangle boundsTop, boundsBot, boundsPass;
    private Random random;
    public boolean passing = false;

    public Tube(float x) {
        topTube = new Texture("toptube.png");
        bottomTube = new Texture("bottomtube.png");
        random = new Random();
        posTopTube = new Vector2(x, random.nextInt(FLUCTUATION) + TUBE_GAP + LOWEST_OPENING);
        posBottomTube = new Vector2(x, posTopTube.y - TUBE_GAP - bottomTube.getHeight());
        boundsTop = new Rectangle(x, posTopTube.y, topTube.getWidth(), topTube.getHeight());
        boundsBot = new Rectangle(x, posBottomTube.y, bottomTube.getWidth(), bottomTube.getHeight());
        boundsPass = new Rectangle(x, posBottomTube.y + bottomTube.getHeight(), topTube.getWidth(), TUBE_GAP);
    }

    public Texture getTopTube() {
        return topTube;
    }

    public Texture getBottomTube() {
        return bottomTube;
    }

    public Vector2 getPosTopTube() {
        return posTopTube;
    }

    public Vector2 getPosBottomTube() {
        return posBottomTube;
    }

    public void reposition(float x) {
        posTopTube.set(x, random.nextInt(FLUCTUATION) + TUBE_GAP + LOWEST_OPENING);
        posBottomTube.set(x, posTopTube.y - TUBE_GAP - bottomTube.getHeight());
        boundsTop.setPosition(x, posTopTube.y);
        boundsBot.setPosition(x, posBottomTube.y);
        boundsPass.setPosition(x, posBottomTube.y + bottomTube.getHeight());
    }

    public boolean collides (Rectangle player) {
        return player.overlaps(boundsTop)|| player.overlaps(boundsBot);
    }

    public boolean isPassing (Rectangle player) {
        if(player.overlaps(boundsPass)) {
            passing = true;
        }
        return passing;
    }

    public void dispose() {
        topTube.dispose();
        bottomTube.dispose();
    }
}
