package com.hk.json;

import com.hk.json.JsonAdaptationException;
import com.hk.json.JsonAdapter;
import com.hk.json.JsonObject;
import com.hk.json.JsonValue;

public class DogAdapter extends JsonAdapter<Dog>
{
	public static final DogAdapter instance = new DogAdapter();
	
	private DogAdapter()
	{
		super(Dog.class);
	}
	
	@Override
	public Dog fromJson(JsonValue val) throws JsonAdaptationException
	{
		if(val.isObject())
		{
			JsonObject obj = val.getObject();
			String name = obj.getString("name");
			String breed = obj.getString("breed");
			int age = obj.getInt("age");
			String color = obj.getString("color");
			
			return new Dog(name, breed, age, color);
		}
		else if(val.isNull())
			return null;
		else
			throw new JsonAdaptationException();
	}

	@Override
	public JsonValue toJson(Dog val) throws JsonAdaptationException
	{
		if(val == null)
			return JsonNull.NULL;
		else
		{
			JsonObject obj = new JsonObject();
			
			obj.put("name", val.name);
			obj.put("breed", val.breed);
			obj.put("age", val.age);
			obj.put("color", val.color);
			
			return obj;
		}
	}
}
