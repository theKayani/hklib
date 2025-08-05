package com.hk.util;

import com.hk.collections.lists.ListUtil;
import com.hk.math.vector.Point;
import junit.framework.TestCase;

import java.io.File;
import java.util.Iterator;
import java.util.List;

public class PairTest extends TestCase
{
	public void testJoin()
	{
		List<Integer> ints = ListUtil.newArrayList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
		List<Pair<Integer, Integer>> pairs = Pair.join(ints, ints);
		assertNotNull(pairs);
		assertFalse(pairs.isEmpty());
		assertEquals(10, pairs.size());
		for (int i = 0; i < 10; i++)
		{
			Pair<Integer, Integer> pair = pairs.get(i);
			assertNotNull(pair);
			assertTrue(pair.hasA());
			assertTrue(pair.hasB());
			assertEquals(pair.getA(), pair.a);
			assertEquals(pair.getB(), pair.b);
			pair.setA(pair.getB() - 10);
			assertEquals(pair.getA(), pair.a);
			assertEquals(pair.getB(), pair.b);
			Pair<Integer, Integer> copy = pair.copy();
			assertEquals(pair, copy);
			copy.setB(1000);
			assertFalse(pair.equals(copy));
		}

		Point[] points = { new Point(0, 0), new Point(1, -2), new Point(3, 4), new Point(12, 17), new Point(-9, 11) };
		File[] files = { new File("main"), new File("southwest"), new File("northeast"), new File("farnortheast"), new File("northwest") };
		List<Pair<Point, File>> pointFiles = Pair.join(points, files);
		List<Pair<File, Point>> filePoints = Pair.join(files, points);
		assertEquals(5, filePoints.size());
		assertEquals(5, pointFiles.size());
		assertEquals(Pair.flip(pointFiles), filePoints);
		assertEquals(Pair.flip(filePoints), pointFiles);

		for (int i = 0; i < 5; i++)
		{
			Pair<Point, File> pointFile = pointFiles.get(i);
			Pair<File, Point> filePoint = filePoints.get(i);

			assertEquals(pointFile, filePoint.flipped());
			assertEquals(filePoint, pointFile.flipped());

			assertSame(pointFile.getA(), filePoint.getB());
			assertSame(filePoint.getA(), pointFile.getB());
		}

		int max = 10000;
		Iterator<Integer> aItr = new Iterator<Integer>() {
			int start = max;

			@Override
			public boolean hasNext()
			{
				return start > 0;
			}

			@Override
			public Integer next()
			{
				return start--;
			}
		};

		Iterator<Integer> bItr = new Iterator<Integer>() {
			int start = 0;

			@Override
			public boolean hasNext()
			{
				return start < max;
			}

			@Override
			public Integer next()
			{
				return start++;
			}
		};

		List<Pair<Integer, Integer>> intPairs = Pair.join(aItr, bItr);
		assertEquals(max, intPairs.size());

		for (int i = 0; i < max; i++)
		{
			Pair<Integer, Integer> intPair = intPairs.get(i);
			assertNotNull(intPair);
			assertTrue(intPair.hasA());
			assertTrue(intPair.hasB());
			assertEquals(i, (int) intPair.b);
			assertEquals(max - i, (int) intPair.a);
		}
	}
}