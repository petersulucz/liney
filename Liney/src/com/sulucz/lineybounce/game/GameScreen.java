package com.sulucz.lineybounce.game;

import com.sulucz.lineybounce.Globals;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public abstract class GameScreen {
	protected SpriteBatch batch;
	protected Camera camera;
	protected boolean visible = false;
	protected float aspect;
	public GameScreen(SpriteBatch batch)
	{
		this.batch = batch;
		camera = new OrthographicCamera();
		float w = Gdx.graphics.getWidth();
		float h = Gdx.graphics.getHeight();
		aspect = w/h;
		camera = new OrthographicCamera(Globals.viewHeight * aspect, Globals.viewHeight);
	}

	public Camera getCamera()
	{
	    return this.camera;
	}

	public SpriteBatch getBatch()
	{
		return batch;
	}

	public float getAspect()
	{
		return aspect;
	}

	public abstract void render();
	public abstract void start();
	public abstract void stop();
	public abstract void pause();
	public abstract void reset();
}
