package com.hk.collections.lists;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import com.hk.abs.Lockable;
import com.hk.util.Requirements;

/**
 * <p>LockableList class.</p>
 *
 * @author theKayani
 */
public class LockableList<E> implements List<E>, Lockable
{
	private List<E> parent;
	private boolean isLocked;
	
	/**
	 * <p>Constructor for LockableList.</p>
	 */
	public LockableList()
	{
		this.parent = new ArrayList<E>();
	}
	
	/**
	 * <p>Constructor for LockableList.</p>
	 *
	 * @param parent a {@link java.util.List} object
	 */
	public LockableList(List<E> parent)
	{
		this.parent = Requirements.requireNotNull(parent);
	}
	
	/**
	 * <p>Constructor for LockableList.</p>
	 *
	 * @param es an array of E[] objects
	 */
	public LockableList(E[] es)
	{
		this.parent = ListUtil.newArrayListWith(es);
	}

	/** {@inheritDoc} */
	@Override
	public boolean isLocked()
	{
		return isLocked;
	}

	/** {@inheritDoc} */
	@Override
	public void lock()
	{
		checkLocked();
		isLocked = true;
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
	public boolean addAll(Collection<? extends E> c)
	{
		checkLocked();
		return parent.addAll(c);
	}

	/** {@inheritDoc} */
	@Override
	public boolean addAll(int pos, Collection<? extends E> c)
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
	public boolean containsAll(Collection<?> c)
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
		checkLocked();
		return parent.iterator();
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
		checkLocked();
		return parent.listIterator();
	}

	/** {@inheritDoc} */
	@Override
	public ListIterator<E> listIterator(int i)
	{
		checkLocked();
		return parent.listIterator(i);
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
	public boolean removeAll(Collection<?> c)
	{
		checkLocked();
		return parent.removeAll(c);
	}

	/** {@inheritDoc} */
	@Override
	public boolean retainAll(Collection<?> c)
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
	public List<E> subList(int i1, int i2)
	{
		return parent.subList(i1, i2);
	}

	/** {@inheritDoc} */
	@Override
	public Object[] toArray()
	{
		return parent.toArray();
	}

	/** {@inheritDoc} */
	@Override
	public <T> T[] toArray(T[] arr)
	{
		return parent.toArray(arr);
	}
	
	private void checkLocked()
	{
		if(isLocked)
			throw new IllegalStateException("Access to Locked List");
	}
}
