package com.cduvvuri.hqutam.utils;

import java.util.Collection;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.JavaType;

public class JsonUtils {
	public static <T> Collection<T> convertJSON(String jsonStr,
			Class<? extends Collection> u, Class<T> t) {
		try {
			ObjectMapper objMapper = new ObjectMapper();

			JavaType type = objMapper.getTypeFactory().constructCollectionType(
					u, t);

			return objMapper.readValue(jsonStr, type);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static <T> Collection<T> convertJSON(byte[] bytes,
			Class<? extends Collection> u, Class<T> t) {
		try {
			ObjectMapper objMapper = new ObjectMapper();

			JavaType type = objMapper.getTypeFactory().constructCollectionType(
					u, t);

			return objMapper.readValue(bytes, type);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static <T> T convertJSON(byte[] bytes, Class<T> t) {
		try {
			ObjectMapper objMapper = new ObjectMapper();

			return objMapper.readValue(bytes, t);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static <T> T convertJSON(String jsonStr, Class<T> t) {
		try {
			ObjectMapper objMapper = new ObjectMapper();
			return objMapper.readValue(jsonStr, t);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
