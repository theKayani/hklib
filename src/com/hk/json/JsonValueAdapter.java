package com.hk.json;

public class JsonValueAdapter<T extends JsonValue> extends JsonAdapter<T>
{
	private final JsonType type;
	
	public JsonValueAdapter(Class<T> cls)
	{
		super(cls);
		this.type = JsonType.by(cls);
	}

	@SuppressWarnings("unchecked")
	public JsonValueAdapter(JsonType type)
	{
		super((Class<? extends T>) type.cls);
		this.type = type;
	}

	public JsonValueAdapter<T> setPriority(int priority)
	{
		super.setPriority(priority);
		return this;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public T fromJson(JsonValue val) throws JsonAdaptationException
	{
		if(val.getType() != type)
			throw new JsonAdaptationException("expected a " + type.name().toLowerCase() + ", not a " + val.getType().name().toLowerCase());
		return (T) val;
	}

	@Override
	public JsonValue toJson(T val) throws JsonAdaptationException
	{
		return val;
	}
}
