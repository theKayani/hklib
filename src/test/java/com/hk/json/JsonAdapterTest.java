package com.hk.json;

import junit.framework.TestCase;

public class JsonAdapterTest extends TestCase
{
	@Override
	public void setUp()
	{
//		Json.registerAdapter(new InterfaceAdapter<>(Point.class));

//		InterfaceAdapter<MyInterface> adapter = new InterfaceAdapter<>(MyInterface.class, true);
//		Json.registerAdapter(adapter);

//		File file1 = new File("test\\assets\\my_interface.json");
//		File file2 = new File("test\\assets\\my_interface2.json");
//		
//		MyInterface myImpl1 = Json.read(file1).get(adapter);
//		System.out.println(point.myByte());
//		System.out.println(point.myShort());
//		System.out.println(point.myInt());
//		System.out.println(point.myLong());
//		System.out.println(point.myChar());
//		System.out.println(point.myString());
//		System.out.println(point.myFloat());
//		System.out.println(point.myDouble());
//		System.out.println(point.myBoolean());
//		System.out.println(point.myPoint());
//
//		System.out.println("------------------------");
//
//		System.out.println(Arrays.toString(point.myBytes()));
//		System.out.println(Arrays.toString(point.myShorts()));
//		System.out.println(Arrays.toString(point.myInts()));
//		System.out.println(Arrays.toString(point.myLongs()));
//		System.out.println(Arrays.toString(point.myChars()));
//		System.out.println(Arrays.toString(point.myStrings()));
//		System.out.println(Arrays.toString(point.myFloats()));
//		System.out.println(Arrays.toString(point.myDoubles()));
//		System.out.println(Arrays.toString(point.myBooleans()));
//		System.out.println(Arrays.toString(point.myPoints()));
//
//		System.out.println("------------------------");
//
//		System.out.println(Arrays.deepToString(point.myBytess()));
//		System.out.println(Arrays.deepToString(point.myShortss()));
//		System.out.println(Arrays.deepToString(point.myIntss()));
//		System.out.println(Arrays.deepToString(point.myLongss()));
//		System.out.println(Arrays.deepToString(point.myCharss()));
//		System.out.println(Arrays.deepToString(point.myStringss()));
//		System.out.println(Arrays.deepToString(point.myFloatss()));
//		System.out.println(Arrays.deepToString(point.myDoubless()));
//		System.out.println(Arrays.deepToString(point.myBooleanss()));
//		System.out.println(Arrays.deepToString(point.myPointss()));
//
//		System.out.println("------------------------");

//		file2.delete();
//		Json.writer(file2).setPrettyPrint().put(adapter.toJson(myImpl1)).close();
//		MyInterface myImpl2 = Json.read(file1).get(adapter);
//		
//		System.out.println(myImpl1);
//		System.out.println(myImpl2);
	}

	public void testDoNotFail()
	{
		assertTrue(true);
	}

	@Override
	public void tearDown()
	{
	}
}