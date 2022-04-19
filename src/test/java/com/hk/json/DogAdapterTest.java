package com.hk.json;

import java.io.FileNotFoundException;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.hk.Assets;

import junit.framework.TestCase;

public class DogAdapterTest extends TestCase
{
	private DogAdapter dogAdapter;

	@Override
	public void setUp()
	{
		dogAdapter = Json.registerAdapter(new DogAdapter());
	}

	public void testAdapter()
	{
		assertNotNull(dogAdapter);

		assertSame(Dog.class, dogAdapter.getObjClass());
	}

	public void testReadSingle() throws FileNotFoundException
	{
		JsonValue value = Json.read(Assets.get("json/dog_single.json"));

		assertNotNull(value);
		assertTrue(value.isObject());

		Dog dog = value.get(Dog.class);

		assertNotNull(dog);

		assertEquals("Bolt", dog.name);
		assertEquals("Terrier", dog.breed);
		assertEquals(12, dog.age);
		assertEquals("white", dog.color);
	}

	public void testReadList() throws FileNotFoundException
	{
		JsonValue value = Json.read(Assets.get("json/dog_array.json"));

		assertNotNull(value);
		assertTrue(value.isArray());

		List<Dog> dogs = value.getArray().toList(Dog.class);

		assertNotNull(dogs);
		assertEquals(6, dogs.size());

		Dog dog;

		dog = dogs.get(0);
		assertNotNull(dog);
		assertEquals("Rocky", dog.name);
		assertEquals("German Shepherd", dog.breed);
		assertEquals(9, dog.age);
		assertEquals("black", dog.color);

		dog = dogs.get(1);
		assertNotNull(dog);
		assertEquals("Charlie", dog.name);
		assertEquals("Golden Retriever", dog.breed);
		assertEquals(11, dog.age);
		assertEquals("golden", dog.color);

		dog = dogs.get(2);
		assertNotNull(dog);
		assertEquals("Balto", dog.name);
		assertEquals("Siberian Husky", dog.breed);
		assertEquals(8, dog.age);
		assertEquals("light brown", dog.color);

		dog = dogs.get(3);
		assertNotNull(dog);
		assertEquals("Bear", dog.name);
		assertEquals("Bulldog", dog.breed);
		assertEquals(9, dog.age);
		assertEquals("gray", dog.color);

		dog = dogs.get(4);
		assertNull(dog);

		dog = dogs.get(5);
		assertNotNull(dog);
		assertEquals("Fluffles", dog.name);
		assertEquals("Poodle", dog.breed);
		assertEquals(12, dog.age);
		assertEquals("light gray", dog.color);
	}

	public void testReadMap() throws FileNotFoundException
	{
		JsonValue value = Json.read(Assets.get("json/dog_map.json"));

		assertNotNull(value);
		assertTrue(value.isObject());

		Map<String, Dog> dogOwners = value.getObject().toMap(Dog.class);

		assertNotNull(dogOwners);
		assertEquals(3, dogOwners.size());

		Dog dog;

		assertTrue(dogOwners.containsKey("Jim Halpert"));
		dog = dogOwners.get("Jim Halpert");
		assertNotNull(dog);
		assertEquals("Max", dog.name);
		assertEquals("Border Collie", dog.breed);
		assertEquals(7, dog.age);
		assertEquals("black", dog.color);

		assertTrue(dogOwners.containsKey("Pam Beesly"));
		dog = dogOwners.get("Pam Beesly");
		assertNull(dog);

		assertTrue(dogOwners.containsKey("Michael Scott"));
		dog = dogOwners.get("Michael Scott");
		assertNotNull(dog);
		assertEquals("Luna", dog.name);
		assertEquals("Great Dane", dog.breed);
		assertEquals(6, dog.age);
		assertEquals("black", dog.color);
	}

	public void testReadListMap() throws FileNotFoundException
	{
		JsonValue value = Json.read(Assets.get("json/dog_array_map.json"));

		assertNotNull(value);
		assertTrue(value.isObject());

		JsonObject kennels = value.getObject();
		List<Dog> kennel;
		Dog dog;

		assertEquals(3, kennels.size());

		assertTrue(kennels.contains("Josh's Dogs"));
		assertTrue(kennels.isArray("Josh's Dogs"));
		kennel = kennels.getArray("Josh's Dogs").toList(Dog.class);
		assertEquals(3, kennel.size());

		dog = kennel.get(0);
		assertNotNull(dog);
		assertEquals("Chi", dog.name);
		assertEquals("Chihuahua", dog.breed);
		assertEquals(6, dog.age);
		assertEquals("golden", dog.color);

		dog = kennel.get(1);
		assertNotNull(dog);
		assertEquals("Daisy", dog.name);
		assertEquals("Chihuahua", dog.breed);
		assertEquals(8, dog.age);
		assertEquals("white", dog.color);

		dog = kennel.get(2);
		assertNotNull(dog);
		assertEquals("Jack", dog.name);
		assertEquals("Dachshund", dog.breed);
		assertEquals(5, dog.age);
		assertEquals("black", dog.color);

		assertTrue(kennels.contains("Kim's Kennel"));
		assertTrue(kennels.isArray("Kim's Kennel"));
		kennel = kennels.getArray("Kim's Kennel").toList(Dog.class);
		assertEquals(2, kennel.size());

		dog = kennel.get(0);
		assertNotNull(dog);
		assertEquals("Zeus", dog.name);
		assertEquals("Rottweiler", dog.breed);
		assertEquals(9, dog.age);
		assertEquals("brown", dog.color);

		dog = kennel.get(1);
		assertNotNull(dog);
		assertEquals("Bella", dog.name);
		assertEquals("Labrador Retreiver", dog.breed);
		assertEquals(8, dog.age);
		assertEquals("light golden", dog.color);

		assertTrue(kennels.contains("Pete's Pack"));
		assertTrue(kennels.isArray("Pete's Pack"));
		kennel = kennels.getArray("Pete's Pack").toList(Dog.class);
		assertEquals(3, kennel.size());

		dog = kennel.get(0);
		assertNotNull(dog);
		assertEquals("Fritz", dog.name);
		assertEquals("Siberian Husky", dog.breed);
		assertEquals(7, dog.age);
		assertEquals("light gray", dog.color);

		dog = kennel.get(1);
		assertNotNull(dog);
		assertEquals("Gus", dog.name);
		assertEquals("Siberian Husky", dog.breed);
		assertEquals(7, dog.age);
		assertEquals("white", dog.color);

		dog = kennel.get(2);
		assertNotNull(dog);
		assertEquals("Mishka", dog.name);
		assertEquals("Siberian Husky", dog.breed);
		assertEquals(5, dog.age);
		assertEquals("black", dog.color);
	}

	public void testReadObject()
	{
		JsonObject obj;
		JsonValue value;
		Dog dog;

		String[] names = { "Max", "Kobe", "Oscar", "Cooper", "Oakley", "Mac", "Charlie", "Rex", "Rudy" };
		String[] breeds = { "German Shepherd", "Bulldog", "Labrador Retreiver", "Poodle", "Golden Retreiver", "Chihuahua", "Siberian Husky", "Great Dane", "Greyhound" };
		int[] ages = new int[25];
		for(int i = 0; i < ages.length; i++)
			ages[i] = i;
		String[] colors = { "red", "blue", "green", "black", "golden", "purple", "white", "orange", "gray" };

		String name = null;
		String breed = null;
		int age = 0;
		String color = null;

		int breedCounter = 0;
		int ageCounter = 0;
		int colorCounter = 0;

		for(int i = 0; i <= names.length; i++)
		{
			obj = null;

			if(i < names.length)
			{
				obj = new JsonObject();

				name = names[i];
				breed = breeds[breedCounter];
				age = ages[ageCounter];
				color = colors[colorCounter];

				obj.put("name", name);
				obj.put("breed", breed);
				obj.put("age", age);
				obj.put("color", color);

				breedCounter++;
				if(breedCounter == breeds.length)
				{
					breedCounter = 0;
					ageCounter++;
					if(ageCounter == ages.length)
					{
						ageCounter = 0;
						colorCounter++;
						if(colorCounter == colors.length)
						{
							colorCounter = 0;
							i++;
						}
					}
				}
				i--;
			}

			value = obj == null ? JsonNull.NULL : obj;

			assertNotNull(value);
			assertTrue(i < names.length && value.isObject() || value.isNull());

			dog = value.get(Dog.class);
			if(i < names.length)
			{
				assertEquals(name, dog.name);
				assertEquals(breed, dog.breed);
				assertEquals(age, dog.age);
				assertEquals(color, dog.color);
			}
			else
				assertNull(dog);
		}
	}

	public void testWriteSingle()
	{
		Dog dog = new Dog("Chichi", "Chihuahua", 12, "light golden");

		assertNotNull(dog);

		JsonValue value = Json.toJson(dog);

		assertNotNull(value);
		assertTrue(value.isObject());

		JsonObject obj = value.getObject();

		assertNotNull(obj);

		assertEquals("{\"name\":\"Chichi\",\"breed\":\"Chihuahua\",\"age\":12,\"color\":\"light golden\"}", Json.write(value));
	}

	public void testWriteList()
	{
		List<Dog> dogs = new LinkedList<>();
		dogs.add(new Dog("Tiny", "French Bulldog", 3, "black"));
		dogs.add(new Dog("Mini", "Dalmation", 3, "black/white"));
		dogs.add(new Dog("Biggie", "Pit Bull Terrier", 6, "white"));

		assertNotNull(dogs);

		JsonValue value = Json.toJson(dogs);

		assertNotNull(value);
		assertTrue(value.isArray());
		assertEquals(3, value.getArray().size());

		String str = "[";
		str += "{\"name\":\"Tiny\",\"breed\":\"French Bulldog\",\"age\":3,\"color\":\"black\"},";
		str += "{\"name\":\"Mini\",\"breed\":\"Dalmation\",\"age\":3,\"color\":\"black/white\"},";
		str += "{\"name\":\"Biggie\",\"breed\":\"Pit Bull Terrier\",\"age\":6,\"color\":\"white\"}";
		str += "]";
		assertEquals(str, Json.write(value));
	}

	public void testWriteMap()
	{
		Map<String, Dog> dogOwners = new LinkedHashMap<>();
		dogOwners.put("Brad's Dogs", new Dog("Doge", "Shiba Inu", 17, "light yellow"));
		dogOwners.put("Katie's Dogs", new Dog("Sparky", "Pomeranian", 3, "white"));
		dogOwners.put("Joseph's Dogs", new Dog("Jack", "Jack Russell Terrier", 5, "brown"));
		dogOwners.put("Samantha's Dogs", new Dog("Rex", "English Cocker Spaniel", 4, "brown"));
		dogOwners.put("Jennifer's Dogs", new Dog("Max", "English Springer Spaniel", 4, "gray"));

		JsonValue value = Json.toJson(dogOwners);

		assertNotNull(value);
		assertTrue(value.isObject());
		assertEquals(5, value.getObject().size());

		String str = "{";
		str += "\"Brad's Dogs\":";
		str += "{\"name\":\"Doge\",\"breed\":\"Shiba Inu\",\"age\":17,\"color\":\"light yellow\"},";
		str += "\"Katie's Dogs\":";
		str += "{\"name\":\"Sparky\",\"breed\":\"Pomeranian\",\"age\":3,\"color\":\"white\"},";
		str += "\"Joseph's Dogs\":";
		str += "{\"name\":\"Jack\",\"breed\":\"Jack Russell Terrier\",\"age\":5,\"color\":\"brown\"},";
		str += "\"Samantha's Dogs\":";
		str += "{\"name\":\"Rex\",\"breed\":\"English Cocker Spaniel\",\"age\":4,\"color\":\"brown\"},";
		str += "\"Jennifer's Dogs\":";
		str += "{\"name\":\"Max\",\"breed\":\"English Springer Spaniel\",\"age\":4,\"color\":\"gray\"}";
		str += "}";
		assertEquals(str, Json.write(value));
	}

	public void testWriteListMap()
	{
		List<Dog> dogs;
		Map<String, List<Dog>> shelters = new LinkedHashMap<>();

		dogs = new LinkedList<>();
		dogs.add(new Dog("Bunz of the Bunny Ears", "Pit Bull Terrier", 3, "brown"));
		dogs.add(new Dog("Angel", "Pit Bull Terrier", 5, "light brown"));
		shelters.put("Adopt an Angel", dogs);

		dogs = new LinkedList<>();
		dogs.add(new Dog("Doug", "German Shepherd", 6, "black/white"));
		dogs.add(new Dog("James T", "Labrador Retreiver", 2, "light brown"));
		dogs.add(new Dog("Petal", "Boxer", 9, "white"));
		shelters.put("ASPCA", dogs);

		dogs = new LinkedList<>();
		dogs.add(new Dog("Deidre", "American Bulldog", 4, "white"));
		dogs.add(new Dog("Ernest", "Rottweiler", 6, "black"));
		dogs.add(new Dog("Grumpy", "Pointer", 3, "white/light brown"));
		shelters.put("Hounds for a Home Animal Shelter", dogs);

		JsonValue value = Json.toJson(shelters);

		assertNotNull(value);
		assertTrue(value.isObject());
		assertEquals(3, value.getObject().size());

		String str = "{";
		str += "\"Adopt an Angel\":[";
		str += "{\"name\":\"Bunz of the Bunny Ears\",\"breed\":\"Pit Bull Terrier\",\"age\":3,\"color\":\"brown\"},";
		str += "{\"name\":\"Angel\",\"breed\":\"Pit Bull Terrier\",\"age\":5,\"color\":\"light brown\"}";
		str += "],";
		str += "\"ASPCA\":[";
		str += "{\"name\":\"Doug\",\"breed\":\"German Shepherd\",\"age\":6,\"color\":\"black/white\"},";
		str += "{\"name\":\"James T\",\"breed\":\"Labrador Retreiver\",\"age\":2,\"color\":\"light brown\"},";
		str += "{\"name\":\"Petal\",\"breed\":\"Boxer\",\"age\":9,\"color\":\"white\"}";
		str += "],";
		str += "\"Hounds for a Home Animal Shelter\":[";
		str += "{\"name\":\"Deidre\",\"breed\":\"American Bulldog\",\"age\":4,\"color\":\"white\"},";
		str += "{\"name\":\"Ernest\",\"breed\":\"Rottweiler\",\"age\":6,\"color\":\"black\"},";
		str += "{\"name\":\"Grumpy\",\"breed\":\"Pointer\",\"age\":3,\"color\":\"white/light brown\"}";
		str += "]";
		str += "}";
		assertEquals(str, Json.write(value));
	}

	public void testWriteObject()
	{
		JsonObject obj;
		JsonValue value;
		Dog dog;

		String[] names = { "Max", "Kobe", "Oscar", "Cooper", "Oakley", "Mac", "Charlie", "Rex", "Rudy" };
		String[] breeds = { "German Shepherd", "Bulldog", "Labrador Retreiver", "Poodle", "Golden Retreiver", "Chihuahua", "Siberian Husky", "Great Dane", "Greyhound" };
		int[] ages = new int[25];
		for(int i = 0; i < ages.length; i++)
			ages[i] = i;
		String[] colors = { "red", "blue", "green", "black", "golden", "purple", "white", "orange", "gray" };

		String name = null;
		String breed = null;
		int age = 0;
		String color = null;

		int breedCounter = 0;
		int ageCounter = 0;
		int colorCounter = 0;

		for(int i = 0; i <= names.length; i++)
		{
			dog = null;

			if(i < names.length)
			{
				name = names[i];
				breed = breeds[breedCounter];
				age = ages[ageCounter];
				color = colors[colorCounter];

				dog = new Dog(name, breed, age, color);

				breedCounter++;
				if(breedCounter == breeds.length)
				{
					breedCounter = 0;
					ageCounter++;
					if(ageCounter == ages.length)
					{
						ageCounter = 0;
						colorCounter++;
						if(colorCounter == colors.length)
						{
							colorCounter = 0;
							i++;
						}
					}
				}
				i--;
			}

			value = dog == null ? JsonNull.NULL : Json.toJson(dog);

			assertNotNull(value);
			assertTrue(i < names.length && value.isObject() || value.isNull());

			if(i < names.length)
			{
				obj = value.getObject();
				assertTrue(obj.isString("name"));
				assertEquals(name, obj.getString("name"));
				assertTrue(obj.isString("breed"));
				assertEquals(breed, obj.getString("breed"));
				assertTrue(obj.isNumber("age"));
				assertEquals(age, obj.getInt("age"));
				assertTrue(obj.isString("color"));
				assertEquals(color, obj.getString("color"));
			}
			else
				assertEquals(JsonNull.NULL, value);
		}
	}

	@Override
	public void tearDown()
	{
		Json.unregisterAdapter(dogAdapter);
	}
}