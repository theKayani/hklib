package com.hk.util;

import java.util.Enumeration;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * <p>Node class.</p>
 *
 * @author theKayani
 */
public class Node<T> implements Iterator<Node<T>>
{
	public Node<T> next;
	public T value;

	/**
	 * <p>Constructor for Node.</p>
	 */
	public Node()
	{
	}

	/**
	 * <p>Constructor for Node.</p>
	 *
	 * @param parent a {@link com.hk.util.Node} object
	 */
	@SuppressWarnings("CopyConstructorMissesField")
	public Node(Node<T> parent)
	{
		parent.next = this;
	}

	/**
	 * <p>Constructor for Node.</p>
	 *
	 * @param value a T object
	 */
	public Node(T value)
	{
		this.value = value;
	}

	/**
	 * <p>Constructor for Node.</p>
	 *
	 * @param parent a {@link com.hk.util.Node} object
	 * @param value a T object
	 */
	public Node(Node<T> parent, T value)
	{
		parent.next = this;
		this.value = value;
	}

	/**
	 * <p>Constructor for Node.</p>
	 *
	 * @param value a T object
	 * @param next a {@link com.hk.util.Node} object
	 */
	public Node(T value, Node<T> next)
	{
		this.value = value;
		this.next = next;
	}

	/**
	 * <p>Getter for the field <code>next</code>.</p>
	 *
	 * @return a {@link com.hk.util.Node} object
	 */
	public Node<T> getNext()
	{
		return next;
	}

	/**
	 * <p>Setter for the field <code>next</code>.</p>
	 *
	 * @param next a {@link com.hk.util.Node} object
	 * @return a {@link com.hk.util.Node} object
	 */
	public Node<T> setNext(Node<T> next)
	{
		this.next = next;
		return this;
	}

	/**
	 * <p>Getter for the field <code>value</code>.</p>
	 *
	 * @return a T object
	 */
	public T getValue()
	{
		return value;
	}

	/**
	 * <p>Setter for the field <code>value</code>.</p>
	 *
	 * @param value a T object
	 * @return a {@link com.hk.util.Node} object
	 */
	public Node<T> setValue(T value)
	{
		this.value = value;
		return this;
	}

	/** {@inheritDoc} */
	@Override
	public boolean hasNext()
	{
		return next != null;
	}

	/** {@inheritDoc} */
	@Override
	public Node<T> next()
	{
		return next;
	}

	/** {@inheritDoc} */
	@Override
	public void remove()
	{
		throw new UnsupportedOperationException("remove");
	}

	/**
	 * <p>last.</p>
	 *
	 * @return a {@link com.hk.util.Node} object
	 */
	public Node<T> last()
	{
		Node<T> curr = this;
		while(curr.next != null)
		{
			curr = curr.next;
		}
		return curr;
	}

	/**
	 * <p>add.</p>
	 *
	 * @param value a T object
	 * @return a {@link com.hk.util.Node} object
	 */
	public Node<T> add(T value)
	{
		return new Node<>(this, value);
	}

	/**
	 * <p>addLast.</p>
	 *
	 * @param value a T object
	 * @return a {@link com.hk.util.Node} object
	 */
	public Node<T> addLast(T value)
	{
		return last().add(value);
	}

	/**
	 * <p>insert.</p>
	 *
	 * @param value a T object
	 * @return a {@link com.hk.util.Node} object
	 */
	public Node<T> insert(T value)
	{
		if(next == null)
			return new Node<>(this, value);
		else
			return next = new Node<>(value, next);
	}

	/**
	 * <p>append.</p>
	 *
	 * @param node a {@link com.hk.util.Node} object
	 * @return a {@link com.hk.util.Node} object
	 */
	public Node<T> append(Node<T> node)
	{
		Requirements.requireNotNull(node);

		if(next != null)
			node.last().next = next;

		next = node;
		return this;
	}

	/**
	 * <p>appendLast.</p>
	 *
	 * @param node a {@link com.hk.util.Node} object
	 * @return a {@link com.hk.util.Node} object
	 */
	public Node<T> appendLast(Node<T> node)
	{
		return last().setNext(node);
	}

	/**
	 * <p>get.</p>
	 *
	 * @param index a int
	 * @return a T object
	 */
	public T get(int index)
	{
		Node<T> curr = this;
		while(index > 0)
		{
			curr = curr.next;
			index--;
		}
		return curr.value;
	}

	/**
	 * <p>size.</p>
	 *
	 * @return a int
	 */
	public int size()
	{
		int amt = 1;
		Node<T> curr = this;
		while(curr.next != null)
		{
			amt++;
			curr = curr.next;
		}
		return amt;
	}

	/**
	 * <p>enumeration.</p>
	 *
	 * @return a {@link java.util.Enumeration} object
	 */
	public Enumeration<T> enumeration()
	{
		return new Enum();
	}

	/**
	 * <p>list.</p>
	 *
	 * @return a {@link java.util.List} object
	 */
	public List<T> list()
	{
		List<T> lst = new LinkedList<>();
		Node<T> curr = this;
		do
		{
			lst.add(curr.value);
			curr = curr.next;
		} while(curr != null);

		return lst;
	}

	/**
	 * <p>toString.</p>
	 *
	 * @return a {@link java.lang.String} object
	 */
	public String toString()
	{
		StringBuilder sb = new StringBuilder();
		sb.append("[");
		Node<T> curr = this;
		do
		{
			sb.append(curr.value);
			curr = curr.next;
			if(curr != null)
				sb.append(", ");
		} while(curr != null);
		sb.append("]");
		return sb.toString();
	}

	private class Enum implements Enumeration<T>
	{
		private Node<T> curr = Node.this;

		@Override

		public boolean hasMoreElements()
		{
			return curr != null;
		}

		@Override

		public T nextElement()
		{
			T value = curr.value;
			curr = curr.next;
			return value;
		}
	}
}