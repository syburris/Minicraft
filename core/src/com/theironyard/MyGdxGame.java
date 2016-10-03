package com.theironyard;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
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

    TextureRegion down, up, right, left;

    String direction = "faceDown";
    Animation walkRight, walkLeft, walkUp, walkDown;
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
        TextureRegion walkLeft1 = new TextureRegion(grid[6][2]);
        TextureRegion walkLeft2 = new TextureRegion(grid[6][3]);
        walkLeft1.flip(true,false);
        walkLeft2.flip(true,false);
        walkRight = new Animation(0.08f, grid[6][2], grid[6][3]);
        walkLeft = new Animation(0.08f,walkLeft1,walkLeft2);
        TextureRegion walkUp1 = new TextureRegion(grid[6][1]);
        TextureRegion walkUp2 = new TextureRegion(grid[6][1]);
        walkUp2.flip(true,false);
        walkUp = new Animation(0.08f,walkUp1,walkUp2);
        TextureRegion walkDown1 = new TextureRegion(grid[6][0]);
        TextureRegion walkDown2 = new TextureRegion(grid[6][0]);
        walkDown2.flip(true,false);
        walkDown = new Animation(0.08f, walkDown1,walkDown2);
	}

	@Override
	public void render () {
        totalTime += Gdx.graphics.getDeltaTime();

        move();

        TextureRegion miniCraftDude;
        if (direction.equals("faceRight")) {
            if (xv != 0){
                miniCraftDude = walkRight.getKeyFrame(totalTime,true);
            }
            else {
                miniCraftDude = right;
            }
        }
        else if (direction.equals("faceLeft")) {
            if (xv != 0) {
                miniCraftDude = walkLeft.getKeyFrame(totalTime,true);
            }
            else {
                miniCraftDude = left;
            }

        }
        else if (direction.equals("faceUp")) {
            if (yv != 0) {
                miniCraftDude = walkUp.getKeyFrame(totalTime,true);
            }
            else {
                miniCraftDude = up;
            }
        }
        else if (direction.equals("faceDown")) {
            if (yv != 0) {
                miniCraftDude = walkDown.getKeyFrame(totalTime,true);
            }
            else {
                miniCraftDude = down;
            }
        }
        else {
            miniCraftDude = down;
        }



		Gdx.gl.glClearColor(0.5f, 0.8f, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
        batch.draw(miniCraftDude, x, y, DRAW_WIDTH, DRAW_HEIGHT);
		batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		img.dispose();
	}

    public void move () {
        int multiplier = 1;

        if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
            multiplier = 4;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.W)  || Gdx.input.isKeyPressed(Input.Keys.UP)) {
            yv = MAX_VELOCITY * multiplier;
            direction = "faceUp";
        }
        else if (Gdx.input.isKeyPressed(Input.Keys.S) || Gdx.input.isKeyPressed(Input.Keys.DOWN)){
            yv = MAX_VELOCITY * -multiplier;
            direction = "faceDown";
        }
        else if (Gdx.input.isKeyPressed(Input.Keys.D) || Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            xv = MAX_VELOCITY * multiplier;
            direction = "faceRight";
        }
        else if (Gdx.input.isKeyPressed(Input.Keys.A) || Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            xv = MAX_VELOCITY * -multiplier;
            direction = "faceLeft";
        }

        x += xv * Gdx.graphics.getDeltaTime();
        y += yv * Gdx.graphics.getDeltaTime();

        xv = decelerate(xv);
        yv = decelerate(yv);

        if (y < 0 - DRAW_HEIGHT) {
            y = Gdx.graphics.getHeight();
        }
        if (y > Gdx.graphics.getHeight()) {
            y = 0 - DRAW_HEIGHT;
        }

        if (x > Gdx.graphics.getWidth()) {
            x = 0 - DRAW_WIDTH;
        }
        if (x < 0-DRAW_WIDTH) {
            x = Gdx.graphics.getWidth();
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
