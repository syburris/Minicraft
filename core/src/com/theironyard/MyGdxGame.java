package com.theironyard;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class MyGdxGame extends ApplicationAdapter {
	SpriteBatch batch;
	Texture img;

    static final float MAX_VELOCITY = 500;
    static final float FRICTION = 0.9f;
    static final int WIDTH = 16;
    static final int HEIGHT = 16;
    static final int DRAW_WIDTH = WIDTH *3;
    static final int DRAW_HEIGHT = HEIGHT *3;
    static final int GRAVITY = 0;

    Texture tiles = new Texture("tiles.png");
    TextureRegion[][] grid = TextureRegion.split(tiles,WIDTH,HEIGHT);
    TextureRegion down = grid[6][0];
    TextureRegion up = grid[6][1];
    TextureRegion right = grid[6][3];
    TextureRegion left = new TextureRegion(right);
    boolean faceRight = true;
    Animation walk;
    float x, y, xv, yv, totalTime;

	
	@Override
	public void create () {
		batch = new SpriteBatch();
		img = new Texture("badlogic.jpg");
        left.flip(true,false);
	}

	@Override
	public void render () {
        totalTime += Gdx.graphics.getDeltaTime();

        move();


		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		batch.draw(img, 0, 0);
		batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		img.dispose();
	}

    public void move () {
        if (Gdx.input.isKeyPressed(Input.Keys.W)  || Gdx.input.isKeyPressed(Input.Keys.UP)) {
            yv = MAX_VELOCITY * 4;
        }
        else if (Gdx.input.isKeyPressed(Input.Keys.S) || Gdx.input.isKeyPressed(Input.Keys.DOWN)){
            yv = MAX_VELOCITY * -1;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.D) || Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            xv = MAX_VELOCITY;
            faceRight = true;
        }
        else if (Gdx.input.isKeyPressed(Input.Keys.A) || Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            xv = MAX_VELOCITY * -1;
            faceRight = false;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
            yv = MAX_VELOCITY * 7;
        }

        yv += GRAVITY;

        x += xv * Gdx.graphics.getDeltaTime();
        y += yv * Gdx.graphics.getDeltaTime();

        xv = decelerate(xv);
        yv = decelerate(yv);

        if (y < 0) {
            y = 0;
        }

        System.out.println(xv + " " + yv);
    }

    public float decelerate(float velocity) {
        velocity *= FRICTION;
        if (Math.abs(velocity) < 40) {
            velocity = 0;
        }
        return velocity;
    }
}
