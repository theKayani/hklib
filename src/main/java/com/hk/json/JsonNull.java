package com.hk.json;


/**
 * <p>JsonNull class.</p>
 *
 * @author theKayani
 */
public class JsonNull extends JsonValue
{
	/** Constant <code>NULL</code> */
	public static final JsonNull NULL = new JsonNull();

	private JsonNull()
	{}
	
	/**
	 * <p>getType.</p>
	 *
	 * @return a {@link com.hk.json.JsonType} object
	 */
	public JsonType getType()
	{
		return JsonType.NULL;
	}

	/**
	 * <p>isNull.</p>
	 *
	 * @return a boolean
	 */
	public boolean isNull()
	{
		return true;
	}

	/** {@inheritDoc} */
	@Override
	public int hashCode()
	{
		return 6532;
	}

	/** {@inheritDoc} */
	@Override
	public boolean equals(Object o)
	{
		return o instanceof JsonNull;
	}
	
	/**
	 * <p>toString.</p>
	 *
	 * @return a {@link java.lang.String} object
	 */
	public String toString()
	{
		return "null";
	}
}
