package com.hk.collections.lists;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.concurrent.atomic.AtomicBoolean;

import com.hk.abs.Unlockable;
import com.hk.util.Requirements;
import org.jetbrains.annotations.NotNull;

/**
 * <p>LockableList class.</p>
 *
 * @author theKayani
 */
public class LockableList<E> implements List<E>, Unlockable
{
	private final List<E> parent;
	private final AtomicBoolean isLocked;

	/**
	 * <p>Constructor for LockableList.</p>
	 */
	public LockableList()
	{
		this.parent = new ArrayList<>();
		isLocked = new AtomicBoolean(false);
	}

	/**
	 * <p>Constructor for LockableList.</p>
	 *
	 * @param parent a {@link java.util.List} object
	 */
	public LockableList(@NotNull List<E> parent)
	{
		this.parent = Requirements.requireNotNull(parent);
		isLocked = new AtomicBoolean(false);
	}

	private LockableList(@NotNull List<E> parent, @NotNull AtomicBoolean locket)
	{
		this.parent = Requirements.requireNotNull(parent);
		isLocked = locket;
	}

	/**
	 * <p>Constructor for LockableList.</p>
	 *
	 * @param es an array of E[] objects
	 */
	public LockableList(E[] es)
	{
		this.parent = ListUtil.newArrayListWith(es);
		isLocked = new AtomicBoolean(false);
	}

	/** {@inheritDoc} */
	@Override
	public boolean isLocked()
	{
		return isLocked.get();
	}

	/** {@inheritDoc} */
	@Override
	public void lock()
	{
		if(!isLocked.compareAndSet(false, true))
			throw new IllegalStateException("List is already in a locked state");
	}

	/** {@inheritDoc} */
	@Override
	public void unlock()
	{
		if(!isLocked.compareAndSet(true, false))
			throw new IllegalStateException("List is already in an unlocked state");
	}

	/** {@inheritDoc} */
	@Override
	public boolean add(E e)
	{
		checkLocked();
		return parent.add(e);
	}

	/** {@inheritDoc} */
	@Override
	public void add(int pos, E e)
	{
		checkLocked();
		parent.add(pos, e);
	}

	/** {@inheritDoc} */
	@Override
	public boolean addAll(@NotNull Collection<? extends E> c)
	{
		checkLocked();
		return parent.addAll(c);
	}

	/** {@inheritDoc} */
	@Override
	public boolean addAll(int pos, @NotNull Collection<? extends E> c)
	{
		checkLocked();
		return parent.addAll(pos, c);
	}

	/** {@inheritDoc} */
	@Override
	public void clear()
	{
		checkLocked();
		parent.clear();
	}

	/** {@inheritDoc} */
	@Override
	public boolean contains(Object e)
	{
		return parent.contains(e);
	}

	/** {@inheritDoc} */
	@Override
	public boolean containsAll(@NotNull Collection<?> c)
	{
		return parent.containsAll(c);
	}

	/** {@inheritDoc} */
	@Override
	public E get(int i)
	{
		return parent.get(i);
	}

	/** {@inheritDoc} */
	@Override
	public int indexOf(Object e)
	{
		return parent.indexOf(e);
	}

	/** {@inheritDoc} */
	@Override
	public boolean isEmpty()
	{
		return parent.isEmpty();
	}

	/** {@inheritDoc} */
	@Override
	public Iterator<E> iterator()
	{
		return new Itr(parent.iterator());
	}

	/** {@inheritDoc} */
	@Override
	public int lastIndexOf(Object e)
	{
		return parent.lastIndexOf(e);
	}

	/** {@inheritDoc} */
	@Override
	public ListIterator<E> listIterator()
	{
		return new ListItr(parent.listIterator());
	}

	/** {@inheritDoc} */
	@Override
	public ListIterator<E> listIterator(int i)
	{
		checkLocked();
		return new ListItr(parent.listIterator(i));
	}

	/** {@inheritDoc} */
	@Override
	public boolean remove(Object e)
	{
		checkLocked();
		return parent.remove(e);
	}

	/** {@inheritDoc} */
	@Override
	public E remove(int i)
	{
		checkLocked();
		return parent.remove(i);
	}

	/** {@inheritDoc} */
	@Override
	public boolean removeAll(@NotNull Collection<?> c)
	{
		checkLocked();
		return parent.removeAll(c);
	}

	/** {@inheritDoc} */
	@Override
	public boolean retainAll(@NotNull Collection<?> c)
	{
		checkLocked();
		return parent.retainAll(c);
	}

	/** {@inheritDoc} */
	@Override
	public E set(int i, E c)
	{
		checkLocked();
		return parent.set(i, c);
	}

	/** {@inheritDoc} */
	@Override
	public int size()
	{
		return parent.size();
	}

	/** {@inheritDoc} */
	@Override
	public LockableList<E> subList(int i1, int i2)
	{
		return new LockableList<>(parent.subList(i1, i2), isLocked);
	}

	/** {@inheritDoc} */
	@Override
	public Object[] toArray()
	{
		return parent.toArray();
	}

	/** {@inheritDoc} */
	@SuppressWarnings("SuspiciousToArrayCall")
	@Override
	public <T> T[] toArray(T @NotNull [] arr)
	{
		return parent.toArray(arr);
	}

	@SuppressWarnings("EqualsWhichDoesntCheckParameterClass")
	@Override
	public boolean equals(Object o)
	{
		return parent.equals(o);
	}

	@Override
	public int hashCode()
	{
		return parent.hashCode();
	}

	@Override
	public String toString()
	{
		return parent.toString();
	}

	private void checkLocked()
	{
		if(isLocked.get())
			throw new IllegalStateException("Access to Locked List");
	}

	private class Itr implements Iterator<E>
	{
		private final Iterator<E> iterator;

		public Itr(Iterator<E> iterator)
		{
			this.iterator = iterator;
		}

		@Override
		public boolean hasNext()
		{
			return iterator.hasNext();
		}

		@Override
		public E next()
		{
			return iterator.next();
		}

		@Override
		public void remove()
		{
			checkLocked();
			iterator.remove();
		}
	}

	private class ListItr implements ListIterator<E>
	{
		private final ListIterator<E> listIterator;

		private ListItr(ListIterator<E> listIterator)
		{
			this.listIterator = listIterator;
		}

		@Override
		public boolean hasNext()
		{
			return listIterator.hasNext();
		}

		@Override
		public E next()
		{
			return listIterator.next();
		}

		@Override
		public boolean hasPrevious()
		{
			return listIterator.hasPrevious();
		}

		@Override
		public E previous()
		{
			return listIterator.previous();
		}

		@Override
		public int nextIndex()
		{
			return listIterator.nextIndex();
		}

		@Override
		public int previousIndex()
		{
			return listIterator.previousIndex();
		}

		@Override
		public void remove()
		{
			checkLocked();
			listIterator.remove();
		}

		@Override
		public void set(E e)
		{
			checkLocked();
			listIterator.set(e);
		}

		@Override
		public void add(E e)
		{
			checkLocked();
			listIterator.add(e);
		}
	}
}