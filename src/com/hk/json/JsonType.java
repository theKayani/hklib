package com.hk.json;

public enum JsonType
{
	OBJECT,
	ARRAY,
	STRING,
	NUMBER,
	BOOLEAN,
	NULL;
	
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
				throw new IllegalArgumentException("Only 0 <= index <= " + (size() - 1));
		}
	}
}
