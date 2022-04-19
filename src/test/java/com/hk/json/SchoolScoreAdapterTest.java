package com.hk.json;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import com.hk.Assets;

import junit.framework.TestCase;

public class SchoolScoreAdapterTest extends TestCase
{
	private SchoolScoreAdapter schoolScoreAdapter;

	@Override
	public void setUp()
	{
		schoolScoreAdapter = new SchoolScoreAdapter();
	}

	public void testAdapter()
	{
		assertNotNull(schoolScoreAdapter);

		assertSame(SchoolScore.class, schoolScoreAdapter.getObjClass());
	}

	public void test() throws FileNotFoundException
	{
		JsonValue value = Json.read(Assets.get("json/school_scores.json"));

		assertNotNull(value);
		assertTrue(value.isArray());

		JsonArray arrayOrig = value.getArray();

		assertNotNull(arrayOrig);

		List<SchoolScore> scores = arrayOrig.toList(schoolScoreAdapter);

		assertNotNull(scores);
		assertEquals(100, scores.size());

		List<String> dbns = new ArrayList<>(scores.size());

		for(SchoolScore score : scores)
			dbns.add(score.dbn);

		assertEquals(scores.size(), dbns.size());

		for(int i = 0; i < arrayOrig.size(); i++)
			assertEquals(arrayOrig.get(i).getObject().getString("dbn"), dbns.get(i));

		assertEquals(arrayOrig, new JsonArray().addAll(scores, schoolScoreAdapter));
	}

	public void testExceptions()
	{
		String expMsg1 = "can only convert objects";
		String expMsg2 = "Expected exception";
		String expMsg3 = "not a string";

		try
		{
			JsonNull.NULL.get(schoolScoreAdapter);

			fail(expMsg2);
		}
		catch(JsonAdaptationException ex)
		{
			assertTrue(ex.getLocalizedMessage().contains(expMsg1));
		}

		try
		{
			new JsonString("null").get(schoolScoreAdapter);

			fail(expMsg2);
		}
		catch(JsonAdaptationException ex)
		{
			assertTrue(ex.getLocalizedMessage().contains(expMsg1));
		}

		try
		{
			JsonBoolean.TRUE.get(schoolScoreAdapter);

			fail(expMsg2);
		}
		catch(JsonAdaptationException ex)
		{
			assertTrue(ex.getLocalizedMessage().contains(expMsg1));
		}

		try
		{
			new JsonNumber(1.0).get(schoolScoreAdapter);

			fail(expMsg2);
		}
		catch(JsonAdaptationException ex)
		{
			assertTrue(ex.getLocalizedMessage().contains(expMsg1));
		}

		try
		{
			new JsonNumber(1L).get(schoolScoreAdapter);

			fail(expMsg2);
		}
		catch(JsonAdaptationException ex)
		{
			assertTrue(ex.getLocalizedMessage().contains(expMsg1));
		}

		try
		{
			new JsonArray().get(schoolScoreAdapter);

			fail(expMsg2);
		}
		catch(JsonAdaptationException ex)
		{
			assertTrue(ex.getLocalizedMessage().contains(expMsg1));
		}

		try
		{
			new JsonArray().add(JsonNull.NULL).toList(schoolScoreAdapter);

			fail(expMsg2);
		}
		catch(JsonAdaptationException ex)
		{
			assertTrue(ex.getLocalizedMessage().contains(expMsg1));
		}

		try
		{
			new JsonObject().put("NU", "LL").toMap(schoolScoreAdapter);

			fail(expMsg2);
		}
		catch(JsonAdaptationException ex)
		{
			assertTrue(ex.getLocalizedMessage().contains(expMsg1));
		}

		try
		{
			JsonObject obj = new JsonObject();
			obj.put("dbn", 12345);
			obj.put("school_name", "string");
			obj.put("num_of_sat_test_takers", "string");
			obj.put("sat_critical_reading_avg_score", "string");
			obj.put("sat_math_avg_score", "string");
			obj.put("sat_writing_avg_score", "string");

			obj.get(schoolScoreAdapter);

			fail(expMsg2);
		}
		catch(IllegalStateException ex)
		{
			assertTrue(ex.getLocalizedMessage().contains(expMsg3));
		}

		try
		{
			JsonObject obj = new JsonObject();
			obj.put("dbn", "string");
			obj.put("school_name", 12345);
			obj.put("num_of_sat_test_takers", "string");
			obj.put("sat_critical_reading_avg_score", "string");
			obj.put("sat_math_avg_score", "string");
			obj.put("sat_writing_avg_score", "string");

			obj.get(schoolScoreAdapter);

			fail(expMsg2);
		}
		catch(IllegalStateException ex)
		{
			assertTrue(ex.getLocalizedMessage().contains(expMsg3));
		}

		try
		{
			JsonObject obj = new JsonObject();
			obj.put("dbn", "string");
			obj.put("school_name", "string");
			obj.put("num_of_sat_test_takers", 12345);
			obj.put("sat_critical_reading_avg_score", "string");
			obj.put("sat_math_avg_score", "string");
			obj.put("sat_writing_avg_score", "string");

			obj.get(schoolScoreAdapter);

			fail(expMsg2);
		}
		catch(IllegalStateException ex)
		{
			assertTrue(ex.getLocalizedMessage().contains(expMsg3));
		}

		try
		{
			JsonObject obj = new JsonObject();
			obj.put("dbn", "string");
			obj.put("school_name", "string");
			obj.put("num_of_sat_test_takers", "string");
			obj.put("sat_critical_reading_avg_score", 12345);
			obj.put("sat_math_avg_score", "string");
			obj.put("sat_writing_avg_score", "string");

			obj.get(schoolScoreAdapter);

			fail(expMsg2);
		}
		catch(IllegalStateException ex)
		{
			assertTrue(ex.getLocalizedMessage().contains(expMsg3));
		}

		try
		{
			JsonObject obj = new JsonObject();
			obj.put("dbn", "string");
			obj.put("school_name", "string");
			obj.put("num_of_sat_test_takers", "string");
			obj.put("sat_critical_reading_avg_score", "string");
			obj.put("sat_math_avg_score", 12345);
			obj.put("sat_writing_avg_score", "string");

			obj.get(schoolScoreAdapter);

			fail(expMsg2);
		}
		catch(IllegalStateException ex)
		{
			assertTrue(ex.getLocalizedMessage().contains(expMsg3));
		}

		try
		{
			JsonObject obj = new JsonObject();
			obj.put("dbn", "string");
			obj.put("school_name", "string");
			obj.put("num_of_sat_test_takers", "string");
			obj.put("sat_critical_reading_avg_score", "string");
			obj.put("sat_math_avg_score", "string");
			obj.put("sat_writing_avg_score", 12345);

			obj.get(schoolScoreAdapter);

			fail(expMsg2);
		}
		catch(IllegalStateException ex)
		{
			assertTrue(ex.getLocalizedMessage().contains(expMsg3));
		}
	}

	@Override
	public void tearDown()
	{
		schoolScoreAdapter = null;
	}
}