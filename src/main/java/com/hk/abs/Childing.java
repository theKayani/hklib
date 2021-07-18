package com.hk.abs;

public interface Childing<T extends Childing<T>>
{
	public T getParent();

	public T[] getChildren();
}
