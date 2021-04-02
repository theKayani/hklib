package com.hk.collections.lists;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public final class ImmutableList<E> extends ArrayList<E> implements Serializable, Cloneable
{
	public ImmutableList(List<? extends E> c)
	{
		super(new ArrayList<>(c));
	}

	@Override
	public void add(int index, E element)
	{
		throw new UnsupportedOperationException("This list in immutable");
	}

	@Override
	public boolean add(E e)
	{
		throw new UnsupportedOperationException("This list in immutable");
	}

	@Override
	public boolean addAll(int index, Collection<? extends E> c)
	{
		throw new UnsupportedOperationException("This list in immutable");
	}

	@Override
	public boolean addAll(Collection<? extends E> c)
	{
		throw new UnsupportedOperationException("This list in immutable");
	}

	@Override
	public void clear()
	{
		throw new UnsupportedOperationException("This list in immutable");
	}

	@Override
	public boolean remove(Object o)
	{
		throw new UnsupportedOperationException("This list in immutable");
	}

	@Override
	public E remove(int index)
	{
		throw new UnsupportedOperationException("This list in immutable");
	}

	@Override
	public boolean removeAll(Collection<?> c)
	{
		throw new UnsupportedOperationException("This list in immutable");
	}

	@Override
	public boolean retainAll(Collection<?> c)
	{
		throw new UnsupportedOperationException("This list in immutable");
	}

	@Override
	public E set(int index, E element)
	{
		throw new UnsupportedOperationException("This list in immutable");
	}

	@Override
	public Iterator<E> iterator()
	{
		return new Itr(super.iterator());
	}

	@Override
	public List<E> subList(int fromIndex, int toIndex)
	{
		return new ImmutableList<>(super.subList(fromIndex, toIndex));
	}

	@Override
	protected void removeRange(int fromIndex, int toIndex)
	{
		throw new UnsupportedOperationException("This list in immutable");
	}

	@SuppressWarnings({
			"rawtypes", "unchecked"
	})
	@Override
	public ListIterator<E> listIterator(int index)
	{
		return new ImmutableList.ListItr(super.listIterator(index));
	}

	@SuppressWarnings({
			"rawtypes", "unchecked"
	})
	@Override
	public ListIterator<E> listIterator()
	{
		return new ImmutableList.ListItr(super.listIterator());
	}

	@SuppressWarnings("unchecked")
	@Override
	public Object clone()
	{
		return new ImmutableList<>((ArrayList<E>) super.clone());
	}

	private class Itr implements Iterator<E>
	{
		private final Iterator<E> par;

		private Itr(Iterator<E> par)
		{
			this.par = par;
		}

		@Override
		public boolean hasNext()
		{
			return par.hasNext();
		}

		@Override
		public E next()
		{
			return par.next();
		}

		@Override
		public void remove()
		{
			throw new UnsupportedOperationException("This list in immutable");
		}
	}

	private class ListItr implements ListIterator<E>
	{
		private final ListIterator<E> par;

		private ListItr(ListIterator<E> par)
		{
			this.par = par;
		}

		@Override
		public boolean hasNext()
		{
			return par.hasNext();
		}

		@Override
		public E next()
		{
			return par.next();
		}

		@Override
		public boolean hasPrevious()
		{
			return par.hasPrevious();
		}

		@Override
		public E previous()
		{
			return par.previous();
		}

		@Override
		public int nextIndex()
		{
			return par.nextIndex();
		}

		@Override
		public int previousIndex()
		{
			return par.previousIndex();
		}

		@Override
		public void remove()
		{
			throw new UnsupportedOperationException("This list in immutable");
		}

		@Override
		public void set(E e)
		{
			throw new UnsupportedOperationException("This list in immutable");
		}

		@Override
		public void add(E e)
		{
			throw new UnsupportedOperationException("This list in immutable");
		}
	}

	public static final class Builder<E>
	{
		private final List<E> list = new ArrayList<>();

		public boolean add(E obj)
		{
			return list.add(obj);
		}

		@SuppressWarnings("unchecked")
		public boolean add(E... obj)
		{
			return list.addAll(Arrays.asList(obj));
		}

		public boolean addAll(Collection<? extends E> obj)
		{
			return list.addAll(obj);
		}

		public ImmutableList<E> build()
		{
			return new ImmutableList<E>(list);
		}
	}

	private static final long serialVersionUID = -2023176864373832742L;
}
