package com.hk.json;

import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.*;

/**
 * <p>InterfaceAdapter class.</p>
 *
 * @author theKayani
 */
public class InterfaceAdapter<T> extends JsonAdapter<T>
{
	private final Class<?> proxyCls;
	private final Constructor<?> constructor;
	private final Map<String, JsonField> fields;
	private final boolean dynamicAddition;
	private Set<JsonAdapter<?>> adapters;
	
	/**
	 * <p>Constructor for InterfaceAdapter.</p>
	 *
	 * @param interfaceCls a {@link java.lang.Class} object
	 */
	public InterfaceAdapter(Class<? extends T> interfaceCls)
	{
		this(interfaceCls, false);
	}

	/**
	 * <p>Constructor for InterfaceAdapter.</p>
	 *
	 * @param interfaceCls a {@link java.lang.Class} object
	 * @param dynamicAddition a boolean
	 */
	public InterfaceAdapter(Class<? extends T> interfaceCls, boolean dynamicAddition)
	{
		super(interfaceCls);
		
		if(!interfaceCls.isInterface())
			throw new IllegalArgumentException("class must be an interface");

		try
		{
			// TODO: Deprecated in the newer versions of JDK
			proxyCls = Proxy.getProxyClass(interfaceCls.getClassLoader(), interfaceCls);
			constructor = proxyCls.getConstructor(InvocationHandler.class);
		}
		catch (NoSuchMethodException e)
		{
			throw new Error(e); // shouldn't be possible...
		}
		catch (SecurityException e)
		{
			throw new RuntimeException(e);
		}
		
		fields = new HashMap<>();
		adapters = null;
		this.dynamicAddition = dynamicAddition;

		setupFields();
	}
	
	/**
	 * <p>Getter for the field <code>dynamicAddition</code>.</p>
	 *
	 * @return a boolean
	 */
	public boolean getDynamicAddition()
	{
		return dynamicAddition;
	}
	
	/**
	 * <p>addAdapter.</p>
	 *
	 * @param adapter a {@link com.hk.json.JsonAdapter} object
	 * @return a {@link com.hk.json.InterfaceAdapter} object
	 */
	public InterfaceAdapter<T> addAdapter(JsonAdapter<?> adapter)
	{
		if(adapters == null)
			adapters = new HashSet<>();
		
		if(adapter instanceof InterfaceAdapter)
			((InterfaceAdapter<?>) adapter).adapters = adapters;

		adapters.add(adapter);
		return this;
	}
	
	/**
	 * <p>removeAdapter.</p>
	 *
	 * @param adapter a {@link com.hk.json.JsonAdapter} object
	 * @return a {@link com.hk.json.InterfaceAdapter} object
	 */
	public InterfaceAdapter<T> removeAdapter(JsonAdapter<?> adapter)
	{
		if(adapters != null)
			adapters.remove(adapter);
		return this;
	}
	
	/**
	 * <p>setAdapterSet.</p>
	 *
	 * @param adapterSet a {@link java.util.Set} object
	 * @return a {@link com.hk.json.InterfaceAdapter} object
	 */
	public InterfaceAdapter<T> setAdapterSet(Set<JsonAdapter<?>> adapterSet)
	{
		adapters = adapterSet;
		
		if(adapterSet != null)
		{
			for(JsonAdapter<?> adapter : adapters)
			{
				if(adapter instanceof InterfaceAdapter)
					((InterfaceAdapter<?>) adapter).adapters = adapters;
			}
		}
		
		return this;
	}
	
	/**
	 * <p>getAdapterSet.</p>
	 *
	 * @return a {@link java.util.Set} object
	 */
	public Set<JsonAdapter<?>> getAdapterSet()
	{
		return adapters;
	}

	private void setupFields()
	{
		Method[] methods = cls.getMethods();
		
		for(Method method : methods)
		{
			if(method.getParameterTypes().length != 0)
				throw new JsonAdaptationException("Interface methods shouldn't take any parameters");

			String fieldName = method.getName();
			InterfaceAdapter.JsonField field = toField(fieldName, method.getReturnType());
//			System.out.println("Added field " + fieldName + ", " + field);
			fields.put(fieldName, field);
		}
	}

	/** {@inheritDoc} */
	@Override
	public InterfaceAdapter<T> setPriority(int priority)
	{
		super.setPriority(priority);
		return this;
	}

	/** {@inheritDoc} */
	@SuppressWarnings("unchecked")
	@Override
	public T fromJson(JsonValue val) throws JsonAdaptationException
	{
		try
		{
			return (T) constructor.newInstance(new Object[] { new InterfaceHandler(val) });
		}
		catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e)
		{
			throw new RuntimeException(e);
		}
	}

	/** {@inheritDoc} */
	@SuppressWarnings("unchecked")
	@Override
	public JsonValue toJson(T val) throws JsonAdaptationException
	{
		if(proxyCls == val.getClass())
		{
			InterfaceHandler handler = (InterfaceHandler) Proxy.getInvocationHandler(val);
			JsonObject obj = new JsonObject();
			
			for(Map.Entry<String, Object> ent : handler.map.entrySet())
				obj.put(ent.getKey(), fields.get(ent.getKey()).convertBack(ent.getValue()));
			
			return obj;
		}
		else
			throw new JsonAdaptationException("Cannot serialize implementation of " + cls + ", " + val);
	}
	
	private JsonField toField(String name, Class<?> cls)
	{
		if(cls == void.class)
		{
			throw new UnsupportedOperationException("Method has no return type, '" + name + "'");
		}
		else if(cls.isPrimitive())
		{
			switch(cls.getName())
			{
			case "byte":
				return new JsonField(name, TYPE_BYTE);
			case "short":
				return new JsonField(name, TYPE_SHORT);
			case "int":
				return new JsonField(name, TYPE_INT);
			case "long":
				return new JsonField(name, TYPE_LONG);
			case "char":
				return new JsonField(name, TYPE_CHAR);
			case "float":
				return new JsonField(name, TYPE_FLOAT);
			case "double":
				return new JsonField(name, TYPE_DOUBLE);
			case "boolean":
				return new JsonField(name, TYPE_BOOLEAN);
			}
		}
		else if(cls == Byte.class)
			return new JsonField(name, TYPE_BYTE);
		else if(cls == Short.class)
			return new JsonField(name, TYPE_SHORT);
		else if(cls == Integer.class)
			return new JsonField(name, TYPE_INT);
		else if(cls == Long.class)
			return new JsonField(name, TYPE_LONG);
		else if(cls == Character.class)
			return new JsonField(name, TYPE_CHAR);
		else if(cls == String.class)
			return new JsonField(name, TYPE_STRING);
		else if(cls == Float.class)
			return new JsonField(name, TYPE_FLOAT);
		else if(cls == Double.class)
			return new JsonField(name, TYPE_DOUBLE);
		else if(cls == JsonValue.class)
			return new JsonField(name, TYPE_JSON);
		else if(cls.isArray())
			return new JsonField(name, toField(name, cls.getComponentType()));
		else
		{
			Set<JsonAdapter<?>> adps = adapters;
			
			if(adps == null)
				adps = Json.getGlobalAdapters();
			
			for(JsonAdapter<?> adapter : adps)
			{
				if(adapter.getObjClass().isAssignableFrom(cls))
					return new JsonField(name, cls, adapter);
			}
		}

		if(dynamicAddition && cls.isInterface())
		{
			JsonAdapter<?> adapter = new InterfaceAdapter<Object>(cls);
			addAdapter(adapter);
			return new JsonField(name, cls, adapter);
		}

		throw new UnsupportedOperationException("Cannot deserialize " + cls);
	}
	
	private class InterfaceHandler implements InvocationHandler
	{
//		private final JsonObject obj;
		private final Map<String, Object> map;

		private InterfaceHandler(JsonValue val)
		{
			JsonObject obj = val.getObject();
			Map<JsonField, JsonValue> fieldAdapters = new HashMap<>();
			
			for(Map.Entry<String, JsonValue> ent : obj)
			{
				JsonField field = fields.get(ent.getKey());
				if(field != null)
					fieldAdapters.put(field, ent.getValue());
				else
					throw new JsonAdaptationException("Unexpected field in JSON object '" + ent.getKey() + "'");
			}
			
			if(fieldAdapters.size() != fields.size())
			{
				Set<JsonField> missing = new HashSet<>(fields.values());
				missing.removeAll(fieldAdapters.keySet());

				throw new JsonAdaptationException("Missing attributes for interface: " + missing);
			}
			
			map = new HashMap<>();
			
			for(Map.Entry<JsonField, JsonValue> ent : fieldAdapters.entrySet())
				map.put(ent.getKey().name, ent.getKey().convert(ent.getValue()));
		}
		
		@SuppressWarnings("unchecked")
		@Override
		public Object invoke(Object proxy, Method method, Object[] args) throws Throwable
		{
			Class<?> decCls = method.getDeclaringClass();
			if(decCls == Object.class)
			{
				switch(method.getName())
				{
				case "equals":
					if(args[0] != null && proxyCls == args[0].getClass())
						return map.equals(((InterfaceHandler) Proxy.getInvocationHandler(args[0])).map);
					else
						return false;
				case "getClass":
					return cls;
				case "hashCode":
					return map.hashCode();
				case "notify":
					notify();
					return null;
				case "notifyAll":
					notifyAll();
					return null;
				case "toString":
					return map.toString();
				case "wait":
					if(args == null || args.length == 0)
						wait();
					else if(args.length == 1)
						wait((long) args[0]);
					else if(args.length == 2)
						wait((long) args[0], (int) args[1]);
					return null;
				default:
					throw new NoSuchMethodError(method.toString()); // shouldn't be possible
				}
			}
			else
				return map.get(method.getName());
		}
	}
	
	private static class JsonField
	{
		private final String name;
		private final int type;
		private final JsonField arrayField;
		private final Class<?> otherCls;
		private final JsonAdapter<?> adapter;
		
		private JsonField(String name, Class<?> otherCls, JsonAdapter<?> adapter)
		{
			this.name = name;
			type = TYPE_ADAPTER;
			this.arrayField = null;
			this.otherCls = otherCls;
			this.adapter = adapter;
		}

		private JsonField(String name, JsonField arrayField)
		{
			this.name = name;
			type = TYPE_ARRAY;
			this.arrayField = arrayField;
			this.otherCls = null;
			this.adapter = null;
		}
		
		private JsonField(String name, int type)
		{
			this.name = name;
			switch(type)
			{
			case TYPE_JSON:
			case TYPE_BYTE:
			case TYPE_SHORT:
			case TYPE_INT:
			case TYPE_LONG:
			case TYPE_CHAR:
			case TYPE_STRING:
			case TYPE_FLOAT:
			case TYPE_DOUBLE:
			case TYPE_BOOLEAN:
				break;
			default:
				throw new Error();
			}

			arrayField = null;
			otherCls = null;
			adapter = null;
			this.type = type;
		}
		
		private Object convert(JsonValue val)
		{
			switch(type)
			{
			case TYPE_JSON:
				return val;
			case TYPE_BYTE:
				return val.getNumber().byteValue();
			case TYPE_SHORT:
				return val.getNumber().shortValue();
			case TYPE_INT:
				return val.getNumber().intValue();
			case TYPE_LONG:
				return val.getNumber().longValue();
			case TYPE_CHAR:
				if(val.getString().length() != 1)
					throw new JsonAdaptationException("'" + name + "' should be a string with a single character");
				
				return val.getString().charAt(0);
			case TYPE_STRING:
				return val.isNull() ? null : val.getString();
			case TYPE_FLOAT:
				return val.getNumber().floatValue();
			case TYPE_DOUBLE:
				return val.getNumber().doubleValue();
			case TYPE_BOOLEAN:
				return val.getBoolean();
			case TYPE_ARRAY:
				List<JsonValue> lst = val.getArray().list;
				Object arr = Objects.requireNonNull(arrayField).array(lst.size());
				
				for(int i = 0; i < lst.size(); i++)
					Array.set(arr, i, arrayField.convert(lst.get(i)));
				
				return arr;
			case TYPE_ADAPTER:
				return Objects.requireNonNull(adapter).fromJson(val);
			default:
				throw new Error();
			}
		}
		
		private JsonValue convertBack(Object val)
		{
			switch(type)
			{
			case TYPE_JSON:
				return (JsonValue) val;
			case TYPE_BYTE:
			case TYPE_SHORT:
			case TYPE_INT:
			case TYPE_LONG:
				return new JsonNumber(((Number) val).longValue());
			case TYPE_CHAR:
				return new JsonString(String.valueOf((char) val));
			case TYPE_STRING:
				return val == null ? JsonNull.NULL : new JsonString((String) val);
			case TYPE_FLOAT:
			case TYPE_DOUBLE:
				return new JsonNumber(((Number) val).doubleValue());
			case TYPE_BOOLEAN:
				return JsonBoolean.valueOf((Boolean) val);
			case TYPE_ARRAY:
				int len = Array.getLength(val);
				JsonArray arr = new JsonArray(len);
				
				for(int i = 0; i < len; i++)
					arr.add(Objects.requireNonNull(arrayField).convertBack(Array.get(val, i)));
				
				return arr;
			case TYPE_ADAPTER:
				return Objects.requireNonNull(adapter).tryTo(val);
			default:
				throw new Error();
			}
		}
		
		private Object array(int size)
		{
			switch(type)
			{
			case TYPE_JSON:
				return new JsonValue[size];
			case TYPE_BYTE:
				return new byte[size];
			case TYPE_SHORT:
				return new short[size];
			case TYPE_INT:
				return new int[size];
			case TYPE_LONG:
				return new long[size];
			case TYPE_CHAR:
				return new char[size];
			case TYPE_STRING:
				return new String[size];
			case TYPE_FLOAT:
				return new float[size];
			case TYPE_DOUBLE:
				return new double[size];
			case TYPE_BOOLEAN:
				return new boolean[size];
			case TYPE_ARRAY:
			case TYPE_ADAPTER:
				return Array.newInstance(toClass(), size);
			default:
				throw new Error();
			}
		}
		
		private Class<?> toClass()
		{
			switch(type)
			{
			case TYPE_JSON:
				return JsonValue.class;
			case TYPE_BYTE:
				return byte.class;
			case TYPE_SHORT:
				return short.class;
			case TYPE_INT:
				return int.class;
			case TYPE_LONG:
				return long.class;
			case TYPE_CHAR:
				return char.class;
			case TYPE_STRING:
				return String.class;
			case TYPE_FLOAT:
				return float.class;
			case TYPE_DOUBLE:
				return double.class;
			case TYPE_BOOLEAN:
				return boolean.class;
			case TYPE_ARRAY:
				return Array.newInstance(Objects.requireNonNull(arrayField).toClass(), 0).getClass();
			case TYPE_ADAPTER:
				return otherCls;
			default:
				throw new Error();
			}
		}
		
		@Override
		public String toString()
		{
			return name;
		}
	}
	
	private static final int TYPE_JSON = 0;
	private static final int TYPE_BYTE = 1;
	private static final int TYPE_SHORT = 2;
	private static final int TYPE_INT = 3;
	private static final int TYPE_LONG = 4;
	private static final int TYPE_CHAR = 5;
	private static final int TYPE_STRING = 6;
	private static final int TYPE_FLOAT = 7;
	private static final int TYPE_DOUBLE = 8;
	private static final int TYPE_BOOLEAN = 9;
	private static final int TYPE_ARRAY = 10;
	private static final int TYPE_ADAPTER = 11;
}
