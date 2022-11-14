package com.hk.blok;

import junit.framework.TestCase;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class BloksTest extends TestCase
{
	public void test() throws FileNotFoundException
	{
		Blok blok = Bloks.read(new File("src/test/resources/assets/blok/stuff.blok"));

		System.out.println("\n---------\n");
		System.out.println(blok);
	}
}