package com.sulucz.lineybounce.data;

import com.sulucz.lineybounce.Globals;
import java.awt.List;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;

public class XMLLineController {
	private Queue<XMLPoint> points;
	public XMLLineController()
	{
		points = new LinkedList<XMLPoint>();
	}
	public void reset()
	{
		this.points.clear();
		//Globals.loader.loadXMLURL(0);
		Globals.loader.loadBlock(0);
	}

	public Queue<XMLPoint> getPoints()
	{
		return points;
	}

	public boolean hasNext()
	{
		return !points.isEmpty();
	}

	public XMLPoint getNext(float x, float lookAhead)
	{
		if(points.isEmpty())
			return null;
		if(x+lookAhead >= points.peek().point.x)
		{
			return points.poll();
		}
		return null;
	}

	public void addPoint(String x1, String y1, String color)
	{
		XMLPoint start = new XMLPoint(new Vector2(Float.parseFloat(x1), Float.parseFloat(y1)), Color.valueOf(color));
		this.points.add(start);
	}
}
