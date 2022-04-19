package com.hk.lua;

/**
 * <p>LuaErrors class.</p>
 *
 * @author theKayani
 */
public enum LuaErrors
{
	DIVIDE_BY_ZERO                  ("attempt to divide by zero"),
	INVALID_ARITHMETIC              ("attempt to perform arithmetic on a %s value"),
	INVALID_CONCATENATE             ("attempt to concatenate a %s value"),
	INVALID_LENGTH                  ("attempt to get length of a %s value"),
	INVALID_COMPARISON              ("attempt to compare %s with %s"),
	INVALID_DUAL_COMPARISON         ("attempt to compare two %s values"),	
	INVALID_INDEX                   ("attempt to index a %s value"),
	INVALID_CALL                    ("attempt to call a %s value");

	private final String message;

	LuaErrors(String message)
	{
		this.message = message;
	}

	LuaException create(Object... args)
	{
		return new LuaException(String.format(message, args));
	}
}