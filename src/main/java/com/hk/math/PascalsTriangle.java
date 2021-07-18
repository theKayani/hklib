package com.hk.math;

public class PascalsTriangle
{
    public static int factorial(int a)
    {
        return (int) factorial((long) a);
    }

    public static long factorial(long a)
    {
        long product = 1;

        for (long i = 2; i <= a; i++)
        {
            product *= i;
        }

        return product;
    }

    public static int nCr(int n, int r)
    {
        return (int) nCr((long) n, (long) r);
    }

    public static int nPr(int n, int r)
    {
        return (int) nPr((long) n, (long) r);
    }

    public static long nCr(long n, long r)
    {
        return factorial(n) / (factorial(r) * factorial(n - r));
    }

    public static long nPr(long n, long r)
    {
        return factorial(n) / factorial(n - r);
    }

    public static int[] pascalsTriangle(int exp)
    {
        int[] coeff = new int[exp + 1];
        for (int i = 0; i < exp; i++)
        {
            coeff[i] = nCr(exp, i);
        }
        coeff[coeff.length - 1] = 1;
        return coeff;
    }

    public static String getTerm(int exp, int term)
    {
        int a = exp - term;
        int i = pascalsTriangle(exp)[term];
        return (i == 1 ? "" : i) + (a == 0 ? "" : "a" + getExponent(a)) + (term == 0 ? "" : "b" + getExponent(term));
    }

    public static String getFullExpression(int exp)
    {
        if (exp == 0)
            return "1";
        String s = "";
        for (int i = 0; i < exp + 1; i++)
        {
            s += getTerm(exp, i) + (i == exp ? "" : " + ");
        }
        return s;
    }

    public static String getExponent(int num)
    {
        if (num == 1)
            return "";
        String s = String.valueOf(num);
        return "^" + (s.length() == 1 ? s : "(" + s + ")");
    }

    private PascalsTriangle()
    {}
}
