package com.hk.math.expression;

import com.hk.func.Supplier;
import com.hk.math.Rand;
import junit.framework.TestCase;

import java.util.HashMap;
import java.util.Map;

public class AlgebraicExpressionTest extends TestCase
{
	public void testValidFormats()
	{
		String[] strs = {
				"3.14",
				"1 + 2 + 3",
				"2 + 2 / 2",
				"4 ^ 3 ^ 2",
				"(x + 2)(x + 4^2)(x - 8)",
				"5 + 3(8 + 2)",
				"a + b * c",
				"5x + 2",
				"x^2 + 2x + 1",
				"7x(5y + 10z)",
				"grade1 * 0.2 + grade2 * 0.2 + grade3 * 0.2 + final * 0.4",
		};

		for(String str : strs)
			new AlgebraicExpression(str);
	}
	public void testInvalidFormats()
	{
/*
		TODO: fix this, amirite
		String[] strs = {
				"--",
		};

		for(String str : strs)
		{
			try
			{
				new AlgebraicExpression(str);

				fail(str + " should not be a valid expression");
			}
			catch(ExpressionFormatException ignored)
			{}
		}
*/
	}

	public void testNonVariableExpressions()
	{
		Object[][] arrs = {
				{ "2 + 2 / 2", 3D },
				{ "123454321 + 543212345", 666666666D },
				{ "2^(5 + 2 * 2)", 512D },
				{ "2^2 * 2^2", 16D },
				{ "5 * (7 - 2) + (8 + 1)", 34D },
				{ "2 * 6 / (8 - 2) - 2^3 + 3 * 4", 6D },
				{ "4+3(8-2(6-3))/2", 7D },
				{ "18 / (8-2*3)", 9D },
				{ "(4*3 / 6 + 1) * 3^2", 27D },
				{ "(2 + 2)/(2 * 2)", 1D },
				// Must be evaluated as 4^(3^2) because
				// exponents are right associative
				{ "4 ^ 3 ^ 2", 262144D },
				{ "9^0.5", 3D },
				{ "2^0.5", Math.sqrt(2D) },
		};

		String str;
		double result;
		for(Object[] arr : arrs)
		{
			str = (String) arr[0];
			result = new AlgebraicExpression(str).get();

			assertEquals(str, arr[1], result);
		}
	}

	public void testNegNonVariableExpressions()
	{
		String[] strs = {
				"-10",
				"-(10)",
				"10 * -1",
				"0 + -10",
				"0 + - - -10",
				"-5 + -5",
				"-5 - 5",
				"-20 + 10",
				"-5 * 2",
				"-(5 * 2)",
				"-(-10^5 / -10^4)",
				"(5)(-(2))",
				"-(-5)(-(2))",
		};

		double result;
		for(String str : strs)
		{
			result = new AlgebraicExpression(str).getResult();

			assertEquals(str, -10D, result);
		}

		assertEquals(0.5D, new AlgebraicExpression("2^-1").getResult());
	}

	public void testVariableExpressions()
	{
		double r = Rand.nextDouble();
		Object[][] arrs = {
				{ "a", 1D, "a", 1D },
				{ "x", 1D, "x", 1D },
				{ "a", r, "a", r },
				{ "x", r, "x", r },
				{ "a + b * c", 7D, "a", 1D, "b", 2D, "c", 3D },
				{ "5x + 2", 62D, "x", 12D },
				{ "x^2 + 2x + 1", 36D, "x", 5D },
				{ "7x(5y + 10z)", 0D, "x", 2D, "y", 2D, "z", -1D },
				{ "grade1 * 0.2 + grade2 * 0.2 + grade3 * 0.2 + final * 0.4", 85.2D,
						"grade1", 74D, "grade2", 88D, "grade3", 82D, "final", 91D },
		};

		String str;
		double result;
		for(Object[] arr : arrs)
		{
			str = (String) arr[0];
			AlgebraicExpression expression = new AlgebraicExpression(str);

			for(int i = 2; i < arr.length; i += 2)
				assertSame(expression, expression.set((String) arr[i], (double) arr[i + 1]));

			result = expression.getResult();
			assertEquals(str, (double) arr[1], result, 0.00000000000002D);

			expression = new AlgebraicExpression(str);

			Map<String, Double> variableMap = new HashMap<>();

			for(int i = 2; i < arr.length; i += 2)
				variableMap.put((String) arr[i], (double) arr[i + 1]);

			result = expression.getResult(variableMap);
			assertEquals(str, (double) arr[1], result, 0.00000000000002D);
		}
	}

	public void testSetSupplier()
	{
		String text = "a + a * a";
		AlgebraicExpression expression = new AlgebraicExpression(text);

		Supplier<Double> supp = new Supplier<Double>()
		{
			int i = 1;
			@Override
			public Double get()
			{
				return (double) i++;
			}
		};

		// This variable, using this supplier turns the expression
		// into "3 + 1 * 2" given PEMDAS
		assertSame(expression, expression.set("a", supp));

		assertEquals(5D, expression.getResult());
	}
}