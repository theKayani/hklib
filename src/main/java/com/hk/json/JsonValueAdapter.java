package com.hk.json;

/**
 * <p>JsonValueAdapter class.</p>
 *
 * @author theKayani
 */
public class JsonValueAdapter<T extends JsonValue> extends JsonAdapter<T>
{
	private final JsonType type;

	/**
	 * <p>Constructor for JsonValueAdapter.</p>
	 *
	 * @param cls a {@link java.lang.Class} object
	 */
	public JsonValueAdapter(Class<T> cls)
	{
		super(cls);
		this.type = JsonType.by(cls);
	}

	/**
	 * <p>Constructor for JsonValueAdapter.</p>
	 *
	 * @param type a {@link com.hk.json.JsonType} object
	 */
	@SuppressWarnings("unchecked")
	public JsonValueAdapter(JsonType type)
	{
		super((Class<? extends T>) type.cls);
		this.type = type;
	}

	/** {@inheritDoc} */
	public JsonValueAdapter<T> setPriority(int priority)
	{
		super.setPriority(priority);
		return this;
	}

	/** {@inheritDoc} */
	@SuppressWarnings("unchecked")
	@Override
	public T fromJson(JsonValue val) throws JsonAdaptationException
	{
		if(val.getType() != type)
			throw new JsonAdaptationException("expected a " + type.name().toLowerCase() + ", not a " + val.getType().name().toLowerCase());
		return (T) val;
	}

	/** {@inheritDoc} */
	@Override
	public JsonValue toJson(T val) throws JsonAdaptationException
	{
		return val;
	}
}