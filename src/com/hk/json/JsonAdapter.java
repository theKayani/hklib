package com.hk.json;

public abstract class JsonAdapter<T> implements Comparable<JsonAdapter<?>>
{
	private int priority = 0;
	protected Class<? extends T> cls;
	
	/**
	 * Method one is to manually set the {@code cls} variable
	 */
	public JsonAdapter()
	{}

	/**
	 * Method two is to pass a {@code Class} to the constructor
	 */
	public JsonAdapter(Class<? extends T> cls)
	{
		this.cls = cls;
	}
	
	public abstract T fromJson(JsonValue val) throws JsonAdaptationException;
	
	public abstract JsonValue toJson(T val) throws JsonAdaptationException;

	JsonValue tryTo(Object obj) throws JsonAdaptationException
	{
		return toJson(cls.cast(obj));
	}

	/**
	 * Method three is to override this method and
	 * 
	 * @return your own class
	 */
	public Class<? extends T> getObjClass()
	{
		return cls;
	}
	
	/**
	 * Set the priority to check this Object Class with future
	 * attempts at adaptation
	 * 
	 * @param priority the priority to check this adapter with
	 * 
	 * @return this
	 */
	protected JsonAdapter<T> setPriority(int priority)
	{
		this.priority = priority;
		return this;
	}
	
	/**
	 * Get the priority to check whether this adapter applies
	 * to a certain class.
	 * 
	 * Can be overridden by subclasses
	 * 
	 * @return the priority as an integer value to be sorted
	 *         descending order.
	 */
	public int getPriority()
	{
		return priority;
	}
	
	@Override
	public int compareTo(JsonAdapter<?> o)
	{
		return Integer.compare(o.getPriority(), getPriority());
	}
}
