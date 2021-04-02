package com.hk.collections.lists;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import com.hk.abs.Lockable;
import com.hk.util.Requirements;

public class LockableList<E> implements List<E>, Lockable
{
	private List<E> parent;
	private boolean isLocked;
	
	public LockableList()
	{
		this.parent = new ArrayList<E>();
	}
	
	public LockableList(List<E> parent)
	{
		this.parent = Requirements.requireNotNull(parent);
	}
	
	public LockableList(E[] es)
	{
		this.parent = ListUtil.newArrayListWith(es);
	}

	@Override
	public boolean isLocked()
	{
		return isLocked;
	}

	@Override
	public void lock()
	{
		checkLocked();
		isLocked = true;
	}

	@Override
	public boolean add(E e)
	{
		checkLocked();
		return parent.add(e);
	}

	@Override
	public void add(int pos, E e)
	{
		checkLocked();
		parent.add(pos, e);
	}

	@Override
	public boolean addAll(Collection<? extends E> c)
	{
		checkLocked();
		return parent.addAll(c);
	}

	@Override
	public boolean addAll(int pos, Collection<? extends E> c)
	{
		checkLocked();
		return parent.addAll(pos, c);
	}

	@Override
	public void clear()
	{
		checkLocked();
		parent.clear();
	}

	@Override
	public boolean contains(Object e)
	{
		return parent.contains(e);
	}

	@Override
	public boolean containsAll(Collection<?> c)
	{
		return parent.containsAll(c);
	}

	@Override
	public E get(int i)
	{
		return parent.get(i);
	}

	@Override
	public int indexOf(Object e)
	{
		return parent.indexOf(e);
	}

	@Override
	public boolean isEmpty()
	{
		return parent.isEmpty();
	}

	@Override
	public Iterator<E> iterator()
	{
		checkLocked();
		return parent.iterator();
	}

	@Override
	public int lastIndexOf(Object e)
	{
		return parent.lastIndexOf(e);
	}

	@Override
	public ListIterator<E> listIterator()
	{
		checkLocked();
		return parent.listIterator();
	}

	@Override
	public ListIterator<E> listIterator(int i)
	{
		checkLocked();
		return parent.listIterator(i);
	}

	@Override
	public boolean remove(Object e)
	{
		checkLocked();
		return parent.remove(e);
	}

	@Override
	public E remove(int i)
	{
		checkLocked();
		return parent.remove(i);
	}

	@Override
	public boolean removeAll(Collection<?> c)
	{
		checkLocked();
		return parent.removeAll(c);
	}

	@Override
	public boolean retainAll(Collection<?> c)
	{
		checkLocked();
		return parent.retainAll(c);
	}

	@Override
	public E set(int i, E c)
	{
		checkLocked();
		return parent.set(i, c);
	}

	@Override
	public int size()
	{
		return parent.size();
	}

	@Override
	public List<E> subList(int i1, int i2)
	{
		return parent.subList(i1, i2);
	}

	@Override
	public Object[] toArray()
	{
		return parent.toArray();
	}

	@Override
	public <T> T[] toArray(T[] arr)
	{
		return parent.toArray(arr);
	}
	
	private void checkLocked()
	{
		Requirements.requireCondition(null, !isLocked, "Access to Locked List");
	}
}
