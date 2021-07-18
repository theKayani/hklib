package com.hk.math.shape;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import com.hk.math.vector.Vector2F;

public class Polygon extends Shape
{
	public final List<Vector2F> vertices;

	public Polygon()
	{
		vertices = new ArrayList<>();
	}

	public Polygon(Iterable<Vector2F> vertices)
	{
		this();
		for (Vector2F vertex : vertices)
		{
			this.vertices.add(vertex);
		}
	}

	public Polygon addVertex(Vector2F vertex)
	{
		vertices.add(vertex.clone());
		return this;
	}

	public Polygon addVertices(Collection<Vector2F> vertices)
	{
		this.vertices.addAll(vertices);
		return this;
	}

	public Polygon addVertex(float x, float y)
	{
		vertices.add(new Vector2F(x, y));
		return this;
	}

	public Vector2F calcMiddle()
	{
		Vector2F v = new Vector2F();
		for (int i = 0; i < vertices.size(); i++)
		{
			v.addLocal(vertices.get(i));
		}
		return v.divideLocal(vertices.size());
	}

	@Override
	public boolean contains(float x, float y)
	{
		boolean in = false;
		for (int i = 0; i < vertices.size(); i++)
		{
			Vector2F a = vertices.get(i);
			Vector2F b = i == vertices.size() - 1 ? vertices.get(0) : vertices.get(i + 1);
			if (a.y > y != b.y > y && x < (b.x - a.x) * (y - a.y) / (b.y - a.y) + a.x)
			{
				in = !in;
			}
		}
		return in;
	}

	@Override
	public boolean contains(float x, float y, float w, float h)
	{
		return false;
	}

	@Override
	public Polygon clone()
	{
		return new Polygon(vertices);
	}

	@Override
	public boolean equals(Object o)
	{
		if (o instanceof Polygon)
		{
			Polygon p = (Polygon) o;
			if (p.vertices.size() == vertices.size())
			{
				for (int i = 0; i < vertices.size(); i++)
				{
					if (!vertices.get(i).equals(p.vertices.get(i)))
					{
						return false;
					}
				}
				return true;
			}
			else return false;
		}
		return false;
	}

	@Override
	public int hashCode()
	{
		int hash = 11;
		for (int i = 0; i < vertices.size(); i++)
		{
			hash = hash * 19 + vertices.get(i).hashCode();
		}
		return hash;
	}

	@Override
	public String toString()
	{
		StringBuilder bd = new StringBuilder("[");
		for (int i = 0; i < vertices.size(); i++)
		{
			bd.append(vertices.get(i) + (i == vertices.size() - 1 ? "" : ", "));
		}
		return bd.append("]").toString();
	}

	private static final long serialVersionUID = -1739201507573654013L;
}
