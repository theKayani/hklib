package com.hk.json;

public enum JsonType
{
	OBJECT(JsonObject.class),
	ARRAY(JsonArray.class),
	STRING(JsonString.class),
	NUMBER(JsonNumber.class),
	BOOLEAN(JsonBoolean.class),
	NULL(JsonNull.class);
	
	public final Class<? extends JsonValue> cls;
	
	private JsonType(Class<? extends JsonValue> cls)
	{
		this.cls = cls;
	}
	
	public static int size()
	{
		return 6;
	}
	
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

	public static JsonType by(Class<?> cls)
	{
		for(JsonType type : values())
		{
			if(type.cls == cls)
				return type;
		}
		return null;
	}
}
