package ua.tqs.projects.individual.utils;

import java.awt.Point;

import java.util.Map;
import java.util.HashMap;

import org.junit.Test;
import org.junit.Before;

import org.junit.jupiter.api.Assertions;

public class ConverterTests
{
	private Map<String, Object> map = new HashMap<>();
	private Point point = new Point(1, 2);
	
	
	@Before
	public void Setup()
	{
		map.put("x", point.x + 2);
		map.put("y", point.y + 1);
	}
	
	@Test
	public void fromMapTest()
	{
		Point p = (Point) Converter.FromMap(new Point(), map);
		Assertions.assertEquals(map.get("x"), p.x);
		Assertions.assertEquals(map.get("y"), p.y);
	}
	
	@Test
	public void toMapTest()
	{
		Map<String, Object> result = Converter.ToMap(point);
		Assertions.assertTrue(result.containsKey("x"));
		Assertions.assertTrue(result.containsKey("y"));
		Assertions.assertEquals(point.x, result.get("x"));
		Assertions.assertEquals(point.y, result.get("y"));
	}
}
