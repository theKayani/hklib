package com.hk.json;

import org.jetbrains.annotations.NotNull;

public class SchoolScoreAdapter extends JsonAdapter<SchoolScore>
{
	@Override
	public SchoolScore fromJson(@NotNull JsonValue val) throws JsonAdaptationException
	{
		if(val.isObject())
		{
			JsonObject obj = val.getObject();
			String dbn = obj.getString("dbn");
			String schoolName = obj.getString("school_name");

			SchoolScore score = new SchoolScore(dbn, schoolName);

			int satTestTakers = -1;
			try
			{
				satTestTakers = Integer.parseInt(obj.getString("num_of_sat_test_takers"));
			}
			catch (NumberFormatException ignored)
			{}
			score.setSatTestTakers(satTestTakers);

			int satReadingAvg = -1;
			try
			{
				satReadingAvg = Integer.parseInt(obj.getString("sat_critical_reading_avg_score"));
			}
			catch (NumberFormatException ignored)
			{}
			score.setSatReadingAvg(satReadingAvg);

			int satMathAvg = -1;
			try
			{
				satMathAvg = Integer.parseInt(obj.getString("sat_math_avg_score"));
			}
			catch (NumberFormatException ignored)
			{}
			score.setSatMathAvg(satMathAvg);

			int satWritingAvg = -1;
			try
			{
				satWritingAvg = Integer.parseInt(obj.getString("sat_writing_avg_score"));
			}
			catch (NumberFormatException ignored)
			{}
			score.setSatWritingAvg(satWritingAvg);

			return score;
		}
		else
			throw new JsonAdaptationException("can only convert objects to school scores");
	}

	@NotNull
	@Override
	public JsonValue toJson(SchoolScore val) throws JsonAdaptationException
	{
		JsonObject obj = new JsonObject();

		obj.put("dbn", val.dbn);
		obj.put("school_name", val.schoolName);

		if(val.getSatTestTakers() == -1)
			obj.put("num_of_sat_test_takers", "s");
		else
			obj.put("num_of_sat_test_takers", String.valueOf(val.getSatTestTakers()));

		if(val.getSatReadingAvg() == -1)
			obj.put("sat_critical_reading_avg_score", "s");
		else
			obj.put("sat_critical_reading_avg_score", String.valueOf(val.getSatReadingAvg()));

		if(val.getSatMathAvg() == -1)
			obj.put("sat_math_avg_score", "s");
		else
			obj.put("sat_math_avg_score", String.valueOf(val.getSatMathAvg()));

		if(val.getSatWritingAvg() == -1)
			obj.put("sat_writing_avg_score", "s");
		else
			obj.put("sat_writing_avg_score", String.valueOf(val.getSatWritingAvg()));

		return obj;
	}

	public Class<? extends SchoolScore> getObjClass()
	{
		return SchoolScore.class;
	}
}