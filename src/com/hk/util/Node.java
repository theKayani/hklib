package com.hk.util;

import java.util.Enumeration;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class Node<T> implements Iterator<Node<T>>
{
	public Node<T> next;
	public T value;
	
	public Node()
	{
	}
	
	public Node(Node<T> parent)
	{
		parent.next = this;
	}
	
	public Node(T value)
	{
		this.value = value;
	}
	
	public Node(Node<T> parent, T value)
	{
		parent.next = this;
		this.value = value;
	}
	
	public Node(T value, Node<T> next)
	{
		this.value = value;
		this.next = next;
	}
	
	public Node<T> getNext()
	{
		return next;
	}
	
	public Node<T> setNext(Node<T> next)
	{
		this.next = next;
		return this;
	}
	
	public T getValue()
	{
		return value;
	}
	
	public Node<T> setValue(T value)
	{
		this.value = value;
		return this;
	}

	@Override
	public boolean hasNext()
	{
		return next != null;
	}

	@Override
	public Node<T> next()
	{
		return next;
	}

	public Node<T> last()
	{
		Node<T> curr = this;
		while(curr.next != null)
		{
			curr = curr.next;
		}
		return curr;
	}
	
	public Node<T> add(T value)
	{
		return new Node<>(this, value);
	}
	
	public Node<T> addLast(T value)
	{
		return last().add(value);
	}
	
	public Node<T> insert(T value)
	{
		if(next == null)
			return new Node<>(this, value);
		else
			return next = new Node<>(value, next);
	}
	
	public Node<T> append(Node<T> node)
	{
		Requirements.requireNotNull(node);
		
		if(next == null)
		{
			next = node;
		}
		else
		{
			node.last().next = next;
			next = node;
		}
		return this;
	}
	
	public Node<T> appendLast(Node<T> node)
	{
		return last().setNext(node);
	}
	
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

	public Enumeration<T> enumeration()
	{
		return new Enum();
	}

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
