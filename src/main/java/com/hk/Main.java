package com.hk;

import java.util.List;

import com.hk.abs.Lockable;
import com.hk.collections.lists.LockableList;

public class Main
{
	public static void main(String[] args) throws Exception
	{
		List<Integer> list = new LockableList<>();

		list.add(3);
		list.add(1);
		list.add(2);
		
		System.out.println(list);
		
		list.add(2, 5);
		
		System.out.println(list);
		
		((Lockable) list).lock();

		System.out.print("[ ");
		for(Integer i : list)
			System.out.print(i + " ");
		System.out.println("]");
	}
}