package com.hk.util;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

/**
 * Contains two paired values
 *
 * @param <A>
 * @param <B>
 */
public class Pair<A, B>
{
	public A a;
	public B b;

	public Pair()
	{
		a = null;
		b = null;
	}

	public Pair(@Nullable A a, @Nullable B b)
	{
		this.a = a;
		this.b = b;
	}

	public boolean hasA()
	{
		return a != null;
	}

	public A getA()
	{
		return a;
	}

	@NotNull
	public Pair<A, B> setA(@Nullable A a)
	{
		this.a = a;
		return this;
	}

	public boolean hasB()
	{
		return b != null;
	}

	public B getB()
	{
		return b;
	}

	@NotNull
	public Pair<A, B> setB(@Nullable B b)
	{
		this.b = b;
		return this;
	}

	@NotNull
	public Pair<A, B> copy()
	{
		return new Pair<>(a, b);
	}

	@NotNull
	public Pair<B, A> flipped()
	{
		return new Pair<>(b, a);
	}

	@Override
	public boolean equals(Object o)
	{
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Pair<?, ?> pair = (Pair<?, ?>) o;
		return Objects.equals(a, pair.a) && Objects.equals(b, pair.b);
	}

	@Override
	public int hashCode()
	{
		return Objects.hash(a, b);
	}

	@Override
	public String toString()
	{
		return "(" + a + ", " + b + ")";
	}

	public static <T, U> List<Pair<U, T>> flip(List<Pair<T, U>> pairs)
	{
		ArrayList<Pair<U, T>> flippedPairs = new ArrayList<>(pairs.size());

		for (Pair<T, U> pair : pairs)
			flippedPairs.add(pair.flipped());

		return flippedPairs;
	}

	@NotNull
	public static <T, U> List<Pair<T, U>> join(@NotNull Collection<T> aPairs, @NotNull Collection<U> bPairs)
	{
		return join(aPairs.iterator(), bPairs.iterator());
	}

	@NotNull
	public static <T, U> List<Pair<T, U>> join(@NotNull T[] aArr, @NotNull U[] bArr)
	{
		ArrayList<Pair<T, U>> pairs = new ArrayList<>();
		int max = Math.max(aArr.length, bArr.length);
		pairs.ensureCapacity(max);

		boolean aNext, bNext;
		for(int i = 0; i < max; i++)
		{
			T a = i < aArr.length ? aArr[i] : null;
			U b = i < bArr.length ? bArr[i] : null;
			pairs.add(new Pair<>(a, b));
		}

		return pairs;
	}

	@NotNull
	public static <T, U> List<Pair<T, U>> join(Iterator<T> aItr, Iterator<U> bItr)
	{
		List<Pair<T, U>> pairs = new ArrayList<>();
		boolean aNext, bNext;
		while(true)
		{
			aNext = aItr.hasNext();
			bNext = bItr.hasNext();

			if(!aNext && !bNext)
				break;

			T a = aNext ? aItr.next() : null;
			U b = bNext ? bItr.next() : null;
			pairs.add(new Pair<>(a, b));
		}

		return pairs;
	}
}
