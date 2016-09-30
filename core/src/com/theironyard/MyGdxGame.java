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

    static final float MAX_VELOCITY = 100;
    static final float FRICTION = 0.9f;
    static final int WIDTH = 16;
    static final int HEIGHT = 16;
    static final int DRAW_WIDTH = WIDTH *3;
    static final int DRAW_HEIGHT = HEIGHT *3;
    static final int GRAVITY = 0;

    TextureRegion down, up, right, left, stand;


    boolean faceDown = true;
    Animation walk;
    float x, y, xv, yv, totalTime;

	
	@Override
	public void create () {
		batch = new SpriteBatch();
		img = new Texture("tiles.png");
        Texture tiles = new Texture("tiles.png");
        TextureRegion[][] grid = TextureRegion.split(tiles,WIDTH,HEIGHT);
        down = grid[6][0];
        up = grid[6][1];
        right = grid[6][3];
        left = new TextureRegion(right);
        left.flip(true,false);
	}

	@Override
	public void render () {
        totalTime += Gdx.graphics.getDeltaTime();

        TextureRegion miniCraftDude;
        if (xv > 0) {
            miniCraftDude = right;
        }
        else if (xv < 0) {
            miniCraftDude = left;
        }
        else if (yv > 0) {
            miniCraftDude = up;
        }
        else {
            miniCraftDude = down;
        }

        move();


		Gdx.gl.glClearColor(0.5f, 0.8f, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
        if (faceDown) {
            batch.draw(miniCraftDude, x, y, DRAW_WIDTH, DRAW_HEIGHT);
        }
        else {
            batch.draw(miniCraftDude, x, y + DRAW_HEIGHT, DRAW_WIDTH, DRAW_HEIGHT * -1);
        }

		batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		img.dispose();
	}

    public void move () {
        if (Gdx.input.isKeyPressed(Input.Keys.W)  || Gdx.input.isKeyPressed(Input.Keys.UP)) {
            yv = MAX_VELOCITY;
        }
        else if (Gdx.input.isKeyPressed(Input.Keys.S) || Gdx.input.isKeyPressed(Input.Keys.DOWN)){
            yv = MAX_VELOCITY * -1;
        }
        else if (Gdx.input.isKeyPressed(Input.Keys.D) || Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            xv = MAX_VELOCITY;
        }
        else if (Gdx.input.isKeyPressed(Input.Keys.A) || Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            xv = MAX_VELOCITY * -1;
        }
        if ((Gdx.input.isKeyPressed(Input.Keys.SPACE) && Gdx.input.isKeyPressed(Input.Keys.W)) ||
                (Gdx.input.isKeyPressed(Input.Keys.SPACE) && Gdx.input.isKeyPressed(Input.Keys.UP))) {
            yv = MAX_VELOCITY * 4;
        }
        else if ((Gdx.input.isKeyPressed(Input.Keys.SPACE) && Gdx.input.isKeyPressed(Input.Keys.S)) ||
                Gdx.input.isKeyPressed(Input.Keys.SPACE) && Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            yv = MAX_VELOCITY * -4;
        }
        else if ((Gdx.input.isKeyPressed(Input.Keys.SPACE) && Gdx.input.isKeyPressed(Input.Keys.A)) ||
                Gdx.input.isKeyPressed(Input.Keys.SPACE) && Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            xv = MAX_VELOCITY * - 4;
        }
        else if ((Gdx.input.isKeyPressed(Input.Keys.SPACE) && Gdx.input.isKeyPressed(Input.Keys.D)) ||
                Gdx.input.isKeyPressed(Input.Keys.SPACE) && Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            xv = MAX_VELOCITY * 4;
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
