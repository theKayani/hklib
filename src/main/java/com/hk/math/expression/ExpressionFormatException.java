package com.hk.math.expression;

/**
 * This class is thrown by classes in this package when an expression
 * wasn't properly parsed. The format of the expression must be
 * changed.
 *
 * @author theKayani
 */
public class ExpressionFormatException extends RuntimeException
{
	ExpressionFormatException(String message, Throwable throwable)
	{
		super(message, throwable);
	}

	ExpressionFormatException(String message)
	{
		super(message);
	}

	private static final long serialVersionUID = -3221130785673901354L;
}
