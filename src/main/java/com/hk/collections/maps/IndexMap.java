package com.hk.collections.maps;

import java.io.Serializable;
import java.util.AbstractMap;
import java.util.AbstractSet;
import java.util.Arrays;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import com.hk.array.ArrayUtil;

/**
 * <p>IndexMap class.</p>
 *
 * @author theKayani
 */
@Deprecated
public class IndexMap<T> extends AbstractMap<Integer, T> implements Cloneable, Serializable
{
	private Map.Entry<Integer, T>[] ents;
	private int size, modCount;

	@SuppressWarnings("unchecked")
	/**
	 * <p>Constructor for IndexMap.</p>
	 */
	public IndexMap()
	{
		ents = new Map.Entry[0];
		size = 0;
	}

	/** {@inheritDoc} */
	@Override
	public int size()
	{
		return size;
	}

	/** {@inheritDoc} */
	@Override
	public boolean containsKey(Object key)
	{
		return key instanceof Integer ? search((Integer) key) >= 0 : false;
	}

	/** {@inheritDoc} */
	@Override
	public boolean containsValue(Object value)
	{
		for (Entry<Integer, T> ent : ents)
		{
			T obj = ent.getValue();
			if (value == null)
			{
				if (obj == null) return true;
			}
			else
			{
				if (Objects.deepEquals(obj, value)) return true;
			}
		}
		return false;
	}

	/** {@inheritDoc} */
	@Override
	@Deprecated
	public T get(Object key)
	{
		return key instanceof Integer ? get(((Integer) key).intValue()) : null;
	}

	/**
	 * <p>get.</p>
	 *
	 * @param index a int
	 * @return a T object
	 */
	public T get(int index)
	{
		int i = search(index);
		return i >= 0 ? ents[i].getValue() : null;
	}

	/** {@inheritDoc} */
	@Override
	public T put(Integer key, T value)
	{
		Entry<Integer, T> ent;
		int index = search(key);
		if (index < 0)
		{
			ent = new IndexEntry(key);
			ents = ArrayUtil.growArray(ents);
			ents[ents.length - 1] = ent;
			Arrays.sort(ents);
			size++;
			modCount++;
		}
		else
		{
			ent = ents[index];
		}
		return ent.setValue(value);
	}

	/** {@inheritDoc} */
	@Override
	public T remove(Object key)
	{
		return key instanceof Integer ? remove(((Integer) key).intValue()) : null;
	}

	/**
	 * <p>remove.</p>
	 *
	 * @param key a int
	 * @return a T object
	 */
	@SuppressWarnings("unchecked")
	public T remove(int key)
	{
		int index = search(key);
		if (index >= 0)
		{
			ents[index] = null;
			Entry<Integer, T>[] entsC = new Entry[size - 1];
			boolean done = false;
			for (int i = 0; i < size; i++)
			{
				Entry<Integer, T> ent = ents[i];
				if (ent == null)
				{
					done = true;
				}
				else
				{
					entsC[i + (done ? -1 : 0)] = ent;
				}
			}
			ents = entsC;
			size--;
			modCount++;
		}
		return null;
	}

	/** {@inheritDoc} */
	@SuppressWarnings("unchecked")
	@Override
	public void clear()
	{
		size = 0;
		ents = new Entry[0];
		modCount++;
	}

	transient volatile Set<Entry<Integer, T>> set = null;

	/** {@inheritDoc} */
	@Override
	public Set<Entry<Integer, T>> entrySet()
	{
		if (set == null)
		{
			set = new EntrySet();
		}
		return set;
	}

	private int search(int key)
	{
		int i = 0;
		int j = ents.length - 1;
		while (i <= j)
		{
			int mid = (i + j) / 2;
			int v = Integer.compare(ents[mid].getKey(), key);

			if (v == 0)
			{
				return mid;
			}
			else
			{
				if (v < 0)
				{
					i = mid + 1;
				}
				else
				{
					j = mid - 1;
				}
			}
		}
		return -1;
	}

	private class EntrySet extends AbstractSet<Entry<Integer, T>>
	{
		public EntrySet()
		{
			super();
		}

		@Override
		public Iterator<Entry<Integer, T>> iterator()
		{
			return new Iterator<Entry<Integer, T>>()
			{
				private int index, expectedModCount = modCount;
				private Entry<Integer, T> last;

				@Override
				public boolean hasNext()
				{
					return index < size;
				}

				@Override
				public Entry<Integer, T> next()
				{
					if (expectedModCount != modCount) throw new ConcurrentModificationException();
					return last = ents[index++];
				}

				@Override
				public void remove()
				{
					IndexMap.this.remove(last.getKey());
					expectedModCount = modCount;
				}
			};
		}

		@Override
		public int size()
		{
			return size;
		}
	}

	private class IndexEntry implements Map.Entry<Integer, T>, Comparable<IndexEntry>
	{
		private final int key;
		private T value;

		private IndexEntry(int key)
		{
			this.key = key;
		}

		@Override
		public Integer getKey()
		{
			return key;
		}

		@Override
		public T getValue()
		{
			return value;
		}

		@Override
		public T setValue(T value)
		{
			T tee = this.value;
			this.value = value;
			return tee;
		}

		@Override
		public int compareTo(IndexEntry o)
		{
			return o == null ? 1 : Integer.compare(getKey(), o.getKey());
		}

		@Override
		public String toString()
		{
			return key + "=" + value;
		}

		@SuppressWarnings("unchecked")
		@Override
		public boolean equals(Object obj)
		{
			if (obj instanceof IndexMap)
			{
				final IndexEntry other = (IndexEntry) obj;
				return this.key == other.key && Objects.equals(this.value, other.value);
			}
			return false;
		}

		@Override
		public int hashCode()
		{
			int hash = 7;
			hash = 97 * hash + this.key;
			hash = 97 * hash + Objects.hashCode(this.value);
			return hash;
		}
	}

	private static final long serialVersionUID = 5627989684153090506L;
}
