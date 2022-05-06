package com.hk.json;

import org.jetbrains.annotations.NotNull;

/**
 * <p>JsonType class.</p>
 *
 * @author theKayani
 */
public enum JsonType
{
	OBJECT(JsonObject.class),
	ARRAY(JsonArray.class),
	STRING(JsonString.class),
	NUMBER(JsonNumber.class),
	BOOLEAN(JsonBoolean.class),
	NULL(JsonNull.class);

	public final Class<? extends JsonValue> cls;

	JsonType(Class<? extends JsonValue> cls)
	{
		this.cls = cls;
	}

	/**
	 * <p>get.</p>
	 *
	 * @param index a int
	 * @return a {@link com.hk.json.JsonType} object
	 */
	@NotNull
	public static JsonType get(int index)
	{
		switch(index)
		{
			case 0:
				return OBJECT;
			case 1:
				return ARRAY;
			case 2:
				return STRING;
			case 3:
				return NUMBER;
			case 4:
				return BOOLEAN;
			case 5:
				return NULL;
			default:
				throw new IllegalArgumentException("Only 0 <= index <= 5");
		}
	}

	/**
	 * <p>by.</p>
	 *
	 * @param cls a {@link java.lang.Class} object
	 * @return a {@link com.hk.json.JsonType} object
	 */
	public static JsonType by(@NotNull Class<?> cls)
	{
		for(JsonType type : values())
		{
			if(type.cls == cls)
				return type;
		}
		return null;
	}
}