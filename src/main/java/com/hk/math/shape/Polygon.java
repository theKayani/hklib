package com.hk.math.shape;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import com.hk.math.vector.Vector2F;

/**
 * <p>Polygon class.</p>
 *
 * @author theKayani
 */
public class Polygon extends Shape
{
	public final List<Vector2F> vertices;

	/**
	 * <p>Constructor for Polygon.</p>
	 */
	public Polygon()
	{
		vertices = new ArrayList<>();
	}

	/**
	 * <p>Constructor for Polygon.</p>
	 *
	 * @param vertices a {@link java.lang.Iterable} object
	 */
	public Polygon(Iterable<Vector2F> vertices)
	{
		this();
		for (Vector2F vertex : vertices)
		{
			this.vertices.add(vertex);
		}
	}

	/**
	 * <p>addVertex.</p>
	 *
	 * @param vertex a {@link com.hk.math.vector.Vector2F} object
	 * @return a {@link com.hk.math.shape.Polygon} object
	 */
	public Polygon addVertex(Vector2F vertex)
	{
		vertices.add(vertex.clone());
		return this;
	}

	/**
	 * <p>addVertices.</p>
	 *
	 * @param vertices a {@link java.util.Collection} object
	 * @return a {@link com.hk.math.shape.Polygon} object
	 */
	public Polygon addVertices(Collection<Vector2F> vertices)
	{
		this.vertices.addAll(vertices);
		return this;
	}

	/**
	 * <p>addVertex.</p>
	 *
	 * @param x a float
	 * @param y a float
	 * @return a {@link com.hk.math.shape.Polygon} object
	 */
	public Polygon addVertex(float x, float y)
	{
		vertices.add(new Vector2F(x, y));
		return this;
	}

	/**
	 * <p>calcMiddle.</p>
	 *
	 * @return a {@link com.hk.math.vector.Vector2F} object
	 */
	public Vector2F calcMiddle()
	{
		Vector2F v = new Vector2F();
		for (Vector2F vertex : vertices)
			v.addLocal(vertex);

		return v.divideLocal(vertices.size());
	}

	/** {@inheritDoc} */
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

	/** {@inheritDoc} */
	@Override
	public boolean contains(float x, float y, float w, float h)
	{
		return false;
	}

	/** {@inheritDoc} */
	@Override
	public Polygon clone()
	{
		return new Polygon(vertices);
	}

	/** {@inheritDoc} */
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

	/** {@inheritDoc} */
	@Override
	public int hashCode()
	{
		int hash = 11;
		for (Vector2F vertex : vertices)
			hash = hash * 19 + vertex.hashCode();

		return hash;
	}

	/** {@inheritDoc} */
	@Override
	public String toString()
	{
		StringBuilder bd = new StringBuilder("[");
		for (int i = 0; i < vertices.size(); i++)
		{
			bd.append(vertices.get(i));

			if(i <= vertices.size() - 1)
				bd.append(", ");
		}
		return bd.append("]").toString();
	}

	private static final long serialVersionUID = -1739201507573654013L;
}
