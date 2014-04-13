package com.sulucz.lineybounce.game.components;

import com.sulucz.lineybounce.Globals;
import com.sulucz.lineybounce.data.XMLPoint;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.EdgeShape;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

public class BoundsController {

	float xinc = 2f;
	Vector2 lastP;
	private World world;
	private Queue<LevelBound> bounds;
	private ShapeRenderer renderer;
	public static Texture platformTex;

	public BoundsController(World world)
	{
		lastP = new Vector2(-100f, 0f);
		this.world = world;
		this.bounds = new LinkedList<LevelBound>();
		platformTex = new Texture(Gdx.files.internal("data/platform.png"));
	}

	public void reset()
	{
		lastP = new Vector2(-100f, 0f);
//		for(LevelBound b : this.bounds)
//		{
//			b.destroy();
//		}
//		this.bounds.clear();
	}

	public void removeFirstBound()
	{
	    LevelBound b = this.bounds.remove();
	    b.destroy();
	}

	public void Update()
	{
		if(Globals.xmlLineController.hasNext())
		{
			XMLPoint pnt = Globals.xmlLineController.getNext(Globals.player.getPosition().x, 25f);
			if(pnt != null)
			{
				this.bounds.add(new LevelBound(lastP, pnt.point, this.world, pnt.color));
				this.lastP = pnt.point.cpy();
			}
		}
		else if(Globals.player.getPosition().x >= lastP.x - 14f)
		{
			Vector2 np = new Vector2(lastP.x + xinc, (float) (Math.cos((lastP.x + xinc+10) * Math.PI / 18) * 4f));
			//Color c = new Color((float)Math.random(), (float)Math.random(), (float) Math.random(), 1f);
			this.bounds.add(new LevelBound(lastP, np, this.world, Color.GREEN));
			this.lastP = np;

		}
		Iterator<LevelBound> itr = this.bounds.iterator();
		while(itr.hasNext())
		{
			LevelBound b = itr.next();
			if(b.isDestroyed())
				itr.remove();
		}
	}

	public void Draw(Camera camera)
	{
		for(LevelBound b : this.bounds)
		{
			b.Draw();
		}
	}
}
