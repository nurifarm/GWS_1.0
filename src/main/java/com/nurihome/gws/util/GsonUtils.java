package com.nurihome.gws.util;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

/**
 * Gson utility class.
 */
public final class GsonUtils {

	/**
	 * <p>The Gson instance does not maintain any state while invoking Json operation.</p>
	 * 
	 * @see https://sites.google.com/site/gson/gson-user-guide
	 * @see https://google-gson.googlecode.com/svn/trunk/gson/docs/javadocs/com/google/gson/GsonBuilder.html#serializeNulls()
	 */
	private static final Gson gson = new GsonBuilder().disableHtmlEscaping().serializeNulls().create();	

	/**
	 * private constructor
	 */
	private GsonUtils() {}

	private static boolean isEmptyJsonElement(final JsonElement jsonElement) {
		boolean isEmpty = true;
		
		if (jsonElement != null) {
			isEmpty = false;
		}
		
		return isEmpty;
	}

	private static JsonElement parse(final JsonObject jsonObject, final String part) {
		JsonElement jsonElement = null;

		if ((jsonObject != null) && (!Utils.isEmpty(part))) {
			if (jsonObject.has(part)) {
				jsonElement = jsonObject.get(part);
			}
		}
		
		return jsonElement;
	}

	private static JsonElement parseJsonTree(final JsonObject jsonTree, final String path) {
		try {
			String[] parts = path.split("\\.");

			if ((parts != null) && (parts.length != 0)) {
				JsonElement jsonElement = null;
				int currentIndex = 1;
				
				for (String part : parts) {
					if (currentIndex == 1) {
						jsonElement = parse(jsonTree, part);
					}else
					{
						jsonElement = parse(jsonElement.getAsJsonObject(), part);
					}
					
					if (isEmptyJsonElement(jsonElement)) {
						break;
					}
					
					currentIndex++;
				}

				if (jsonElement != null) {
					if ((currentIndex - 1) == parts.length) {
						return jsonElement;
					}
				}
			}
		}catch (Exception ignore)
		{
			//--- nothing
		}
		
		return null;
	}

	/**
	 * <p>convert json string to java object</p>
	 * 
	 * <blockquote>
	 * <pre>{@code
	 * Usage:
	 *     ClassObject classObject = GsonUtils.parseJsonTree2Object("json string", "path", ClassObject.class);
	 * }</pre>
	 * </blockquote>
	 */
	public static <T> T parseJsonTree2Object(final String json, final String path, final Class<T> type) {
		try {
			if ((!Utils.isEmpty(json)) && (!Utils.isEmpty(path))) {
				JsonObject jsonTree = gson.fromJson(json, JsonObject.class);
				
				if (jsonTree != null) {
					JsonElement jsonElement = parseJsonTree(jsonTree, path);
					
					if ((jsonElement != null) && (!jsonElement.isJsonArray()) && (!jsonElement.isJsonNull())) {
						return convertJson2Object(jsonElement.toString(), type);
					}
				}
			}
		}catch (Exception ignore)
		{
			//--- nothing
		}

		return null;
	}

	/**
	 * <p>convert json string to map</p>
	 * 
	 * <blockquote>
	 * <pre>{@code
	 *     Map<String, String> map = GsonUtils.parseJsonTree2Map("json string", "path");
	 * }</pre>
	 * </blockquote>
	 */
	public static <K, V> Map<K, V> parseJsonTree2Map(final String json, final String path) {
		Map<K, V> map = new HashMap<K, V>();
		
		try {
			if ((!Utils.isEmpty(json)) && (!Utils.isEmpty(path))) {
				JsonObject jsonTree = gson.fromJson(json, JsonObject.class);
				
				if (jsonTree != null) {
					JsonElement jsonElement = parseJsonTree(jsonTree, path);
					
					if ((jsonElement != null) && (jsonElement.isJsonObject()) && (!jsonElement.isJsonNull())) {
						map = convertJson2Map(jsonElement.toString());
					}
				}
			}
		}catch (Exception ignore)
		{
			//--- nothing
		}
		
		return map;
	}

	/**
	 * <p>convert json string to list of map</p>
	 * 
	 * <blockquote>
	 * <pre>{@code
	 *     List<Map<String, String>> rs = GsonUtils.parseJsonTree2List("json string", "path");
	 * }</pre>
	 * </blockquote>
	 */
	public static <K, V> List<Map<K, V>> parseJsonTree2List(final String json, final String path) {
		List<Map<K, V>> rs = new ArrayList<Map<K, V>>();
		
		try {
			if ((!Utils.isEmpty(json)) && (!Utils.isEmpty(path))) {
				JsonObject jsonTree = gson.fromJson(json, JsonObject.class);
				
				if (jsonTree != null) {
					JsonElement jsonElement = parseJsonTree(jsonTree, path);
					
					if ((jsonElement != null) && (jsonElement.isJsonArray()) && (!jsonElement.isJsonNull())) {
						rs = convertJson2List(jsonElement.toString());
					}
				}
			}
		}catch (Exception ignore)
		{
			//--- nothing
		}
		
		return rs;
	}

	/**
	 * <p>convert json string to list of generic type</p>
	 * 
	 * <blockquote>
	 * <pre>{@code
	 *     List<ClassObject> rs = GsonUtils.parseJsonTree2List("json string", "path", ClassObject[].class);
	 * }</pre>
	 * </blockquote>
	 */
	public static <T> List<T> parseJsonTree2List(final String json, final String path, final Class<T[]> type) {
		List<T> rs = new ArrayList<T>();
		
		try {
			if ((!Utils.isEmpty(json)) && (!Utils.isEmpty(path))) {
				JsonObject jsonTree = gson.fromJson(json, JsonObject.class);
				
				if (jsonTree != null) {
					JsonElement jsonElement = parseJsonTree(jsonTree, path);
					
					if ((jsonElement != null) && (jsonElement.isJsonArray()) && (!jsonElement.isJsonNull())) {
						rs = convertJson2List(jsonElement.toString(), type);
					}
				}
			}
		}catch (Exception ignore)
		{
			//--- nothing
		}

		return rs;
	}


	/**
	 * <p>convert json string to java object</p>
	 * 
	 * <blockquote>
	 * <pre>{@code
	 * Usage:
	 *     ClassObject classObject = GsonUtils.convertJson2Object("json string", ClassObject.class);
	 * }</pre>
	 * </blockquote>
	 */
	public static <T> T convertJson2Object(final String json, final Class<T> type) {
		// ------------------------------------------------------------
		// JSON 형식의 스트링 객체를 파라미터로 전달받은 클래스 형식의 객체로 변환후 반환
		// ------------------------------------------------------------
		try {
			return gson.fromJson(json, type);
			
		}catch (Exception ignore)
		{
			//--- nothing
		}

		return null;
	}

	public static <T> T convertJson2ObjectParser(final String json, final Class<T> type) {
		// ------------------------------------------------------------
		// JSON 형식의 스트링 객체를 파라미터로 전달받은 클래스 형식의 객체로 변환후 반환
		// ------------------------------------------------------------
		try {
			JsonObject jsonObject = JsonParser.parseString(json).getAsJsonObject();
			
			return gson.fromJson(jsonObject.toString(), type);
			
		}catch (Exception ignore)
		{
			//--- nothing
		}

		return null;
	}

	/**
	 * <p>convert json string to map</p>
	 * 
	 * <blockquote>
	 * <pre>{@code
	 *     Map<String, String> map = GsonUtils.convertJson2Map("json string");
	 * }</pre>
	 * </blockquote>
	 */
	public static <K, V> Map<K, V> convertJson2Map(final String json) {
		// ------------------------------------------------------------
		// JSON 형식의 스트링 객체를 맵(Map) 형식의 객체로 변환후 반환
		// ------------------------------------------------------------
		Map<K, V> map = new HashMap<K, V>();
		
		try {
			if (!Utils.isEmpty(json)) {
				Type type = new TypeToken<HashMap<K, V>>(){}.getType();
				map = gson.fromJson(json, type);			
			}
		}catch (Exception ignore)
		{
			//--- nothing
		}

		return map;
	}

	/**
	 * <p>convert json string to list of map</p>
	 * 
	 * <blockquote>
	 * <pre>{@code
	 *     List<Map<String, String>> rs = GsonUtils.convertJson2List("json string");
	 * }</pre>
	 * </blockquote>
	 */
	public static <K, V> List<Map<K, V>> convertJson2List(final String json) {
		// ------------------------------------------------------------
		// JSON 형식의 스트링 객체를 리스트 맵(Map) 형식의 객체로 변환후 반환
		// ------------------------------------------------------------
		List<Map<K, V>> rs = new ArrayList<Map<K, V>>();
		
		try {
			if (!Utils.isEmpty(json)) {
				Type type = new TypeToken<List<Map<K, V>>>(){}.getType();
				rs = gson.fromJson(json, type);
			}
		}catch (Exception ignore)
		{
			//--- nothing
		}

		return rs;
	}

	/**
	 * <p>convert json string to list of generic type</p>
	 * 
	 * <blockquote>
	 * <pre>{@code
	 *     List<ClassObject> rs = GsonUtils.convertJson2List("json string", ClassObject[].class);
	 * }</pre>
	 * </blockquote>
	 */
	public static <T> List<T> convertJson2List(final String json, final Class<T[]> type) {
		// ------------------------------------------------------------
		// JSON 형식의 스트링 객체를 파라미터로 전달받은 클래스 형식(Generic Type)의 리스트 객체로 변환후 반환 
		// ------------------------------------------------------------
		List<T> rs = new ArrayList<T>();
		
		try {
			if (!Utils.isEmpty(json)) {
				rs = Arrays.asList(gson.fromJson(json, type));			
			}
		}catch (Exception ignore)
		{
			//--- nothing
		}

		return rs;
	}

	/**
	 * <p>convert java object to json format</p>
	 * 
	 * <blockquote>
	 * <pre>{@code
	 * Usage:
	 *     Map<String, String> map = new HashMap<String, String>();
	 *     map.put("foo", "bar");
	 * 
	 *     GsonUtils.convertObject2Json(map) = "{foo: "bar"}";
	 * }</pre>
	 * </blockquote>
	 */
	public static String convertObject2Json(final Object o) {
		// ------------------------------------------------------------
		// 파라미터로 전달받은 객체를 JSON 형식의 스트링 객체로 변환후 반환
		// ------------------------------------------------------------
		// https://google-gson.googlecode.com/svn/trunk/gson/docs/javadocs/com/google/gson/Gson.html#toJson(java.lang.Object)
		// ------------------------------------------------------------
		return gson.toJson(o);
	}

}
