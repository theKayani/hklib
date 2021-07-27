package com.hk.json;

import com.hk.util.Requirements;

/**
 * <p>JsonString class.</p>
 *
 * @author theKayani
 */
public class JsonString extends JsonValue
{
	private String value;
	
	/**
	 * <p>Constructor for JsonString.</p>
	 *
	 * @param value a {@link java.lang.String} object
	 */
	public JsonString(String value)
	{
		this.value = Requirements.requireNotNull(value);
	}
	
	/**
	 * <p>getType.</p>
	 *
	 * @return a {@link com.hk.json.JsonType} object
	 */
	public JsonType getType()
	{
		return JsonType.STRING;
	}

	/**
	 * <p>isString.</p>
	 *
	 * @return a boolean
	 */
	public boolean isString()
	{
		return true;
	}
	
	/**
	 * <p>getString.</p>
	 *
	 * @return a {@link java.lang.String} object
	 */
	public String getString()
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
		return o instanceof JsonString && value.equals(((JsonString) o).value);
	}

	/** {@inheritDoc} */
	@Override
	public String toString()
	{
		return Json.write(this);
	}
}