package com.hk.json;

class Dog
{
	String name;
	String breed;
	int age;
	String color;

	Dog(String name, String breed, int age, String color)
	{
		this.name = name;
		this.breed = breed;
		this.age = age;
		this.color = color;
	}

	String getName()
	{
		return name;
	}

	String getBreed()
	{
		return breed;
	}

	int getAge()
	{
		return age;
	}

	String getColor()
	{
		return color;
	}

	@Override
	public String toString()
	{
		return("Hi my name is "+ this.getName()+
			".\nMy breed,age and color are " +
			this.getBreed()+"," + this.getAge()+
			","+ this.getColor());
	}
}
