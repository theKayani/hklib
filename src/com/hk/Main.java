package com.hk;
import com.hk.json.InterfaceAdapter;
import com.hk.json.Json;
import com.hk.json.JsonAdapter;

public class Main
{
	public static void main(String[] args) throws Exception
	{
		JsonAdapter<IntSupplier> adapter = new InterfaceAdapter<>(IntSupplier.class);
		
		IntSupplier sup = adapter.fromJson(Json.read("{ \"get\": \"1\" }"));
		
		System.out.println(sup.get());
				
//		adapter.toJson();
	}
	
	public static interface IntSupplier
	{
		char get();
	}
}