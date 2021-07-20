package com.hk.json;

/**
 * <p>JsonBoolean class.</p>
 *
 * @author theKayani
 */
public class JsonBoolean extends JsonValue
{
	private final boolean value;

	private JsonBoolean(boolean value)
	{
		this.value = value;
	}
	
	/**
	 * <p>getType.</p>
	 *
	 * @return a {@link com.hk.json.JsonType} object
	 */
	public JsonType getType()
	{
		return JsonType.BOOLEAN;
	}

	/**
	 * <p>isBoolean.</p>
	 *
	 * @return a boolean
	 */
	public boolean isBoolean()
	{
		return true;
	}
	
	/**
	 * <p>getBoolean.</p>
	 *
	 * @return a boolean
	 */
	public boolean getBoolean()
	{
		return value;
	}

	/** {@inheritDoc} */
	@Override
	public int hashCode()
	{
		return value ? 707 : ~707;
	}

	/** {@inheritDoc} */
	@Override
	public boolean equals(Object o)
	{
		return o instanceof JsonBoolean && value == ((JsonBoolean) o).value;
	}
	
	/**
	 * <p>toString.</p>
	 *
	 * @return a {@link java.lang.String} object
	 */
	public String toString()
	{
		return Boolean.toString(value);
	}
	
	/**
	 * <p>valueOf.</p>
	 *
	 * @param b a boolean
	 * @return a {@link com.hk.json.JsonBoolean} object
	 */
	public static JsonBoolean valueOf(boolean b)
	{
		return b ? TRUE : FALSE;
	}

	/** Constant <code>TRUE</code> */
	public static final JsonBoolean TRUE = new JsonBoolean(true);
	/** Constant <code>FALSE</code> */
	public static final JsonBoolean FALSE = new JsonBoolean(false);
}
