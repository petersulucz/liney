package com.sulucz.lineybounce.game.components;

import com.sulucz.lineybounce.Globals;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.EdgeShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;

public class LevelBound {
	private Body body;
	private World world;
	private FixtureDef fixture;
	private Vector2 start;
	private Vector2 end;
	private Vector2 startpos;
	private float st;
	private Color color;
	private Sprite sprite;
	private float width;
	static Vector2 downDist = new Vector2(0f, -0.4f);
	float lifeLength;
	float currentLife = 0f;
	public LevelBound(Vector2 start, Vector2 end, World world, Color color)
	{

		this.lifeLength = (float) (Math.random() + Math.max(5+ start.x*-0.003f, 1));
		sprite = new Sprite(BoundsController.platformTex);
		st = 1f;
		startpos = new Vector2((float) (Math.random()*5f)+5f, (float) (Math.random()*20)-10);
		width = start.dst(end);
		//this.sprite.setOrigin(width/2, 0.2f);
		this.sprite.setOrigin(0, 0);
		this.sprite.setRotation((end.cpy().sub(start)).angle());
		this.sprite.setSize(width, 0.4f);
		this.sprite.setColor(color);
		this.world = world;
		BodyDef lnDef = new BodyDef();

		fixture = new FixtureDef();
		fixture.friction = (1f);
		this.body = world.createBody(lnDef);
		EdgeShape eshape = new EdgeShape();
		this.body.setUserData(this.sprite);

		this.start = start;
		this.end = end;
		eshape.set(start, end);
		fixture.shape = eshape;
		body.createFixture(fixture);
		eshape.dispose();
		this.color = color;
	}
	public void destroy()
	{

		this.world.destroyBody(this.body);

	}

	boolean update = true;
	boolean destroy = false;
	public void Draw()
	{
		this.currentLife += Gdx.graphics.getDeltaTime();
		if(this.st > 0.01 && !destroy)
		{
			this.sprite.setPosition(start.x + this.startpos.x*st, start.y+ this.startpos.y*st + downDist.y);
			st *= 0.88f;
		}
		else if(destroy)
		{
			this.sprite.setPosition(start.x + this.startpos.x*st, start.y+ this.startpos.y*st + downDist.y);
			st *= 1.12f;
		}
		else if(update)
		{
			this.sprite.setPosition(start.x, start.y+downDist.y);
			update = false;
		}
		else if(this.lifeLength < this.currentLife)
		{
			this.destroy();
			this.destroy = true;
		}
		this.sprite.draw(Globals.mainGame.getBatch());
	}

	public boolean isDestroyed()
	{
		return destroy && st > 1;
	}
}
