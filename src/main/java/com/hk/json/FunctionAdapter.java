package com.hk.json;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;
import java.util.function.Function;

/**
 * This is just a compatibility/convenience method for 1.8 so that
 * method references are easier.
 *
 * @param <T> class to use to convert from and to JSON values
 */
public class FunctionAdapter<T> extends JsonAdapter<T>
{
	private Function<T, JsonValue> toJson;
	private Function<JsonValue, T> fromJson;

	public FunctionAdapter(@NotNull Class<T> cls)
	{
		this(cls, null, null);
	}

	public FunctionAdapter(@NotNull Class<T> cls, @Nullable Function<T, JsonValue> toJson, @Nullable Function<JsonValue, T> fromJson)
	{
		this.cls = Objects.requireNonNull(cls);
		this.toJson = toJson;
		this.fromJson = fromJson;
	}

	@NotNull
	public FunctionAdapter<T> setToJson(@Nullable Function<T, JsonValue> toJson)
	{
		this.toJson = toJson;
		return this;
	}

	@Nullable
	public Function<T, JsonValue> getToJson()
	{
		return toJson;
	}

	@NotNull
	public FunctionAdapter<T> setFromJson(@Nullable Function<JsonValue, T> fromJson)
	{
		this.fromJson = fromJson;
		return this;
	}

	@Nullable
	public Function<JsonValue, T> getFromJson()
	{
		return fromJson;
	}

	@Override
	public T fromJson(@NotNull JsonValue val) throws JsonAdaptationException
	{
		return fromJson.apply(val);
	}

	@NotNull
	@Override
	public JsonValue toJson(T val) throws JsonAdaptationException
	{
		return toJson.apply(val);
	}
}
