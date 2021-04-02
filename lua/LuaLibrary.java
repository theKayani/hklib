package com.hk.lua;

import com.hk.func.BiConsumer;

public class LuaLibrary<T extends Enum<T> & BiConsumer<Environment, LuaObject>>
{
	 /********************************************* BUILT-IN *********************************************/
	public static final LuaLibrary<LuaLibraryBasic> BASIC = new LuaLibrary<>(null, LuaLibraryBasic.class);
	public static final LuaLibrary<LuaLibraryCoroutine> COROUTINE = new LuaLibrary<>("coroutine", LuaLibraryCoroutine.class);
//	public static final LuaLibrary<LuaLibraryPackage> PACKAGE = new LuaLibrary<>("package", LuaLibraryPackage.class);
	public static final LuaLibrary<LuaLibraryString> STRING = new LuaLibrary<>("string", LuaLibraryString.class);
//	public static final LuaLibrary<LuaLibraryUTF8> UTF8 = new LuaLibrary<>("utf8", LuaLibraryUTF8.class);
	public static final LuaLibrary<LuaLibraryTable> TABLE = new LuaLibrary<>("table", LuaLibraryTable.class);
	public static final LuaLibrary<LuaLibraryMath> MATH = new LuaLibrary<>("math", LuaLibraryMath.class);
	public static final LuaLibrary<LuaLibraryIO> IO = new LuaLibrary<>("io", LuaLibraryIO.class);
	public static final LuaLibrary<LuaLibraryOS> OS = new LuaLibrary<>("os", LuaLibraryOS.class);
//	public static final LuaLibrary<LuaLibraryDegub> DEGUB = new LuaLibrary<>("degub", LuaLibraryDegub.class);

	 /********************************************* CUSTOM *********************************************/
	public static final LuaLibrary<LuaLibraryJson> JSON = new LuaLibrary<>("json", LuaLibraryJson.class);
	public static final LuaLibrary<LuaLibraryHash> HASH = new LuaLibrary<>("hash", LuaLibraryHash.class);

	final String table;
	final BiConsumer<Environment, LuaObject>[] consumers;
	
	public LuaLibrary(String table, Class<T> cls)
	{
		this.table  = table;
		consumers = cls.getEnumConstants();
	}
}