package ua.tqs.projects.individual.utils;

import java.util.Map;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.InvocationTargetException;

public class Converter
{
	private Converter()
	{
	}

	public static Object fromMap(Object object, Map<String, Object> map, String... except)
			throws IllegalAccessException, InvocationTargetException
	{
		Class<?> clazz = object.getClass();
		List<String> exceptions = Arrays.asList(except);
		for (Map.Entry<String, Object> entry : map.entrySet())
			if (!exceptions.contains(entry.getKey()))
				try
				{
					Field field = clazz.getDeclaredField(entry.getKey());
					if (Modifier.isPublic(field.getModifiers()))
						field.set(object, entry.getValue());
					else
					{
						Method method = clazz.getDeclaredMethod(
								String.format("set%s",
										entry.getKey().substring(0, 1).toUpperCase() + entry.getKey().substring(1)),
								entry.getValue().getClass());
						if (Modifier.isPublic(method.getModifiers()))
							method.invoke(object, entry.getValue());
					}
				} catch (NoSuchFieldException | NoSuchMethodException e)
				{
				}
		return object;
	}

	public static Map<String, Object> ToMap(Object object, String... except)
	{
		Class<?> clazz = object.getClass();
		Map<String, Object> map = new HashMap<>();
		List<String> exceptions = Arrays.asList(except);
		for (Field field : clazz.getDeclaredFields())
			try
			{
				if (!Modifier.isStatic(field.getModifiers()) && Modifier.isPublic(field.getModifiers())
						&& !exceptions.contains(field.getName()))
					map.put(field.getName(), field.get(object));
			} catch (IllegalArgumentException | IllegalAccessException e)
			{
				throw new RuntimeException(e);
			}
		for (Method method : clazz.getDeclaredMethods())
		{
			String name = method.getName();
			if (name.startsWith("get") && !Modifier.isStatic(method.getModifiers())
					&& Modifier.isPublic(method.getModifiers()))
			{
				name = name.substring(3);
				name = name.substring(0, 1).toLowerCase() + name.substring(1);
				if (!exceptions.contains(name) && !map.containsKey(name))
					try
					{
						map.put(name, method.invoke(object));
					} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e)
					{
						throw new RuntimeException(e);
					}
			}
		}
		return map;
	}
}
