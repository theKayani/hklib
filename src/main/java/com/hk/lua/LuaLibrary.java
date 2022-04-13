package com.hk.lua;

import com.hk.func.BiConsumer;

/**
 * <p>LuaLibrary objects wrap a set of library methods to be imported
 * into a Lua environment.</p>
 *
 * @author theKayani
 */
public class LuaLibrary<T extends Enum<T> & BiConsumer<Environment, LuaObject>>
{
	//******************************************** BUILT-IN *********************************************/
	/** Constant <code>BASIC</code> */
	public static final LuaLibrary<LuaLibraryBasic> BASIC = new LuaLibrary<>(null, LuaLibraryBasic.class);
	/** Constant <code>COROUTINE</code> */
	public static final LuaLibrary<LuaLibraryCoroutine> COROUTINE = new LuaLibrary<>("coroutine", LuaLibraryCoroutine.class);
	//** Constant <code>PACKAGE</code> */
	public static final LuaLibrary<LuaLibraryPackage> PACKAGE = new LuaLibrary<>("package", LuaLibraryPackage.class);
	/** Constant <code>STRING</code> */
	public static final LuaLibrary<LuaLibraryString> STRING = new LuaLibrary<>("string", LuaLibraryString.class);
//	public static final LuaLibrary<LuaLibraryUTF8> UTF8 = new LuaLibrary<>("utf8", LuaLibraryUTF8.class);
	/** Constant <code>TABLE</code> */
	public static final LuaLibrary<LuaLibraryTable> TABLE = new LuaLibrary<>("table", LuaLibraryTable.class);
	/** Constant <code>MATH</code> */
	public static final LuaLibrary<LuaLibraryMath> MATH = new LuaLibrary<>("math", LuaLibraryMath.class);
	/** Constant <code>IO</code> */
	public static final LuaLibrary<LuaLibraryIO> IO = new LuaLibrary<>("io", LuaLibraryIO.class);
	/** Constant <code>OS</code> */
	public static final LuaLibrary<LuaLibraryOS> OS = new LuaLibrary<>("os", LuaLibraryOS.class);
//	public static final LuaLibrary<LuaLibraryDegub> DEGUB = new LuaLibrary<>("degub", LuaLibraryDegub.class);

	//******************************************** CUSTOM *********************************************/
	/** Constant <code>JSON</code> */
	public static final LuaLibrary<LuaLibraryJson> JSON = new LuaLibrary<>("json", LuaLibraryJson.class);
	/** Constant <code>HASH</code> */
	public static final LuaLibrary<LuaLibraryHash> HASH = new LuaLibrary<>("hash", LuaLibraryHash.class);
	/** Constant <code>DATE</code> */
	public static final LuaLibrary<LuaLibraryDate> DATE = new LuaLibrary<>("date", LuaLibraryDate.class);
	/** Constant <code>REFLECT</code> */
	public static final LuaLibrary<LuaLibraryReflect> REFLECT = new LuaLibrary<>("java", LuaLibraryReflect.class);

	final String table;
	final BiConsumer<Environment, LuaObject>[] consumers;
	
	/**
	 * <p>Constructor for LuaLibrary.</p>
	 *
	 * @param table a {@link java.lang.String} object
	 * @param cls a {@link java.lang.Class} object
	 */
	public LuaLibrary(String table, Class<T> cls)
	{
		this.table  = table;
		consumers = cls.getEnumConstants();
	}

	/**
	 * @deprecated {@link Lua#importStandard(LuaInterpreter)}
	 */
	@Deprecated
	public static void importStandard(LuaInterpreter interp)
	{
		Lua.importStandard(interp);
	}
}
