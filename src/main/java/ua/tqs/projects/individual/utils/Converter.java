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
	{
		Class<?> clazz = object.getClass();
		List<String> exceptions = Arrays.asList(except);
		for (Map.Entry<String, Object> entry : map.entrySet())
			if (!exceptions.contains(entry.getKey()))
				try
				{
					if (!set(getDeclaredField(clazz, entry.getKey()), object, entry.getValue()))
						set(getDeclaredMethod(clazz,
								String.format("set%s",
										entry.getKey().substring(0, 1).toUpperCase() + entry.getKey().substring(1)),
								entry.getValue().getClass()), object, entry.getValue());
				} catch (IllegalAccessException | InvocationTargetException e)
				{
					throw new RethrowException(e);
				}
		return object;
	}

	public static Map<String, Object> toMap(Object object, String... except)
	{
		Class<?> clazz = object.getClass();
		Map<String, Object> map = new HashMap<>();
		List<String> exceptions = Arrays.asList(except);
		try
		{
			for (Field field : clazz.getDeclaredFields())
				if (!Modifier.isStatic(field.getModifiers()) && Modifier.isPublic(field.getModifiers())
						&& !exceptions.contains(field.getName()))
					map.put(field.getName(), field.get(object));
			for (Method method : clazz.getDeclaredMethods())
			{
				String name = method.getName();
				if (name.startsWith("get") && !Modifier.isStatic(method.getModifiers())
						&& Modifier.isPublic(method.getModifiers()))
				{
					name = name.substring(3);
					name = name.substring(0, 1).toLowerCase() + name.substring(1);
					if (!exceptions.contains(name) && !map.containsKey(name))
						map.put(name, method.invoke(object));
				}
			}
		} catch (IllegalAccessException | InvocationTargetException e)
		{
			throw new RethrowException(e);
		}
		return map;
	}

	private static Field getDeclaredField(Class<?> clazz, String name)
	{
		try
		{
			return clazz.getDeclaredField(name);
		} catch (NoSuchFieldException e)
		{
			return null;
		}
	}

	private static Method getDeclaredMethod(Class<?> clazz, String name, Class<?>... classes)
	{
		try
		{
			return clazz.getDeclaredMethod(name, classes);
		} catch (NoSuchMethodException e)
		{
			return null;
		}
	}

	private static boolean set(Field field, Object self, Object argument) throws IllegalAccessException
	{
		if (field != null && Modifier.isPublic(field.getModifiers()))
		{
			field.set(self, argument);
			return true;
		}
		return false;
	}

	private static boolean set(Method method, Object self, Object argument) throws IllegalAccessException, InvocationTargetException
	{
		if (method != null && Modifier.isPublic(method.getModifiers()))
		{
			method.invoke(self, argument);
			return true;
		}
		return false;
	}
}
