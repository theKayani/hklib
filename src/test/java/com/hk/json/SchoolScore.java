package com.hk.json;

public class SchoolScore
{
	public final String dbn, schoolName;
	private int satTestTakers, satReadingAvg, satWritingAvg, satMathAvg;

	public SchoolScore(String dbn, String schoolName)
	{
		this.dbn = dbn;
		this.schoolName = schoolName;
	}

	public int getSatTestTakers()
	{
		return satTestTakers;
	}

	public void setSatTestTakers(int satTestTakers)
	{
		this.satTestTakers = satTestTakers;
	}

	public int getSatReadingAvg()
	{
		return satReadingAvg;
	}

	public void setSatReadingAvg(int satReadingAvg)
	{
		this.satReadingAvg = satReadingAvg;
	}

	public int getSatWritingAvg()
	{
		return satWritingAvg;
	}

	public void setSatWritingAvg(int satWritingAvg)
	{
		this.satWritingAvg = satWritingAvg;
	}

	public int getSatMathAvg()
	{
		return satMathAvg;
	}

	public void setSatMathAvg(int satMathAvg)
	{
		this.satMathAvg = satMathAvg;
	}

	@Override
	public String toString() {
		return "SchoolScore [dbn=" + dbn + ", schoolName=" + schoolName + ", satTestTakers=" + satTestTakers
				+ ", satReadingAvg=" + satReadingAvg + ", satWritingAvg=" + satWritingAvg + ", satMathAvg=" + satMathAvg
				+ "]";
	}
}