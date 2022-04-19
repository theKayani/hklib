package com.hk.json;

/**
 * <p>JsonNumber class.</p>
 *
 * @author theKayani
 */
public class JsonNumber extends JsonValue
{
	private final Number value;

	/**
	 * <p>Constructor for JsonNumber.</p>
	 *
	 * @param value a long
	 */
	public JsonNumber(long value)
	{
		this.value = value;
	}

	/**
	 * <p>Constructor for JsonNumber.</p>
	 *
	 * @param value a double
	 */
	public JsonNumber(double value)
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
		return JsonType.NUMBER;
	}

	/**
	 * <p>isNumber.</p>
	 *
	 * @return a boolean
	 */
	public boolean isNumber()
	{
		return true;
	}

	/**
	 * <p>getNumber.</p>
	 *
	 * @return a {@link java.lang.Number} object
	 */
	public Number getNumber()
	{
		return value;
	}

	/** {@inheritDoc} */
	@Override
	public int hashCode()
	{
		return value.hashCode();
	}

	/** {@inheritDoc} */
	@Override
	public boolean equals(Object o)
	{
		return o instanceof JsonNumber && value.equals(((JsonNumber) o).value);
	}

	/** {@inheritDoc} */
	@Override
	public String toString()
	{
		return value.toString();
	}
}