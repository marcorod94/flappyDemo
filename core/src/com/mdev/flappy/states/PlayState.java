package com.mdev.flappy.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.mdev.flappy.FlappyDemo;
import com.mdev.flappy.sprites.Bird;
import com.mdev.flappy.sprites.Score;
import com.mdev.flappy.sprites.Tube;

/**
 * Created by Marco Rodriguez on 16/11/2016.
 */

public class PlayState extends State
{
    private static final int TUBE_SPACING = 125;
    private static final int TUBE_COUNT = 4;
    private static final int GROUND_Y_OFFSET = -50;
    private static final float SOUND_EFFECT_VOL = 0.2f;
    private Bird bird;
    private Texture bg;
    private Texture ground;
    private Score score;
    private Vector2 groundPos1, groundPos2;
    private Array<Tube> tubes;
    private Sound hit;
    private Sound die;
    private Sound point;

    public PlayState(GameStateManager gsm) {
        super(gsm);
        score = new Score();
        bird = new Bird(50,300);
        cam.setToOrtho(false, FlappyDemo.WIDTH/2, FlappyDemo.HEIGHT/2);
        bg = new Texture("bg.png");
        ground = new Texture("ground.png");
        groundPos1 = new Vector2(cam.position.x - cam.viewportWidth / 2, GROUND_Y_OFFSET);
        groundPos2 = new Vector2((cam.position.x - cam.viewportWidth / 2) + ground.getWidth(), GROUND_Y_OFFSET);
        tubes = new Array<Tube>();
        for(int i = 1; i <= TUBE_COUNT; i++) {
            tubes.add(new Tube(i * (TUBE_SPACING + Tube.TUBE_WIDTH)));
        }

        hit = Gdx.audio.newSound(Gdx.files.internal("hit.wav"));
        die = Gdx.audio.newSound(Gdx.files.internal("die.wav"));
        point = Gdx.audio.newSound(Gdx.files.internal("point.wav"));
    }

    @Override
    public void handleInput() {
        if(Gdx.input.justTouched()) {
            bird.jump();
        }
    }

    @Override
    public void update(float dt) {
        handleInput();
        updateGround();
        bird.update(dt);
        cam.position.x = bird.getPosition().x + 80;
        for(Tube tube:tubes) {
            if((cam.position.x - (cam.viewportWidth / 2)) > (tube.getPosTopTube().x + tube.getTopTube().getWidth())) {
                tube.reposition(tube.getPosTopTube().x + ((Tube.TUBE_WIDTH+TUBE_SPACING) * TUBE_COUNT));
            }

            if(tube.collides(bird.getBounds())) {
                hit.play();
                die.play();
                gsm.set(new com.mdev.flappy.states.GameOverState(gsm));
            }

            if(tube.isPassing(bird.getBounds()) && bird.getPosition().x >= tube.getPosTopTube().x + tube.getTopTube().getWidth()) {
                point.play();
                score.increaseValue();
                tube.passing = false;
            }
        }

        if(bird.getPosition().y <= ground.getHeight() + GROUND_Y_OFFSET) {
            die.play();
            gsm.set(new GameOverState(gsm));
        }
        cam.update();
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.setProjectionMatrix(cam.combined);
        sb.begin();
        sb.draw(bg,cam.position.x - (cam.viewportWidth / 2),0);
        sb.draw(bird.getBird(), bird.getPosition().x, bird.getPosition().y);

        for(Tube tube:tubes) {
            sb.draw(tube.getTopTube(), tube.getPosTopTube().x, tube.getPosTopTube().y);
            sb.draw(tube.getBottomTube(), tube.getPosBottomTube().x, tube.getPosBottomTube().y);
        }
        sb.draw(score.cent, cam.position.x - score.cent.getWidth() * 1.5f, cam.viewportHeight - score.cent.getHeight() );
        sb.draw(score.dec, cam.position.x - score.dec.getWidth() * 0.5f, cam.viewportHeight - score.dec.getHeight());
        sb.draw(score.unit, cam.position.x + score.unit.getWidth() * 0.5f, cam.viewportHeight - score.unit.getHeight());
        sb.draw(ground, groundPos1.x, groundPos1.y);
        sb.draw(ground, groundPos2.x, groundPos2.y);
        sb.end();
    }

    @Override
    public void dispose() {
        bg.dispose();
        bird.dispose();
        for(Tube tube: tubes) {
            tube.dispose();
        }
    }

    private void updateGround() {
        if((cam.position.x - cam.viewportWidth / 2) > (groundPos1.x + ground.getWidth()))
        {
            groundPos1.add(ground.getWidth() * 2, 0);
        }
        if((cam.position.x - cam.viewportWidth / 2) > (groundPos2.x + ground.getWidth()))
        {
            groundPos2.add(ground.getWidth() * 2, 0);
        }
    }
}
