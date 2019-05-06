package ua.tqs.projects.individual.utils;

import java.util.Arrays;

import org.junit.Test;
import org.junit.Before;

import org.junit.runner.RunWith;

import org.junit.jupiter.api.Assertions;

import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import ua.tqs.projects.individual.entities.City;
import ua.tqs.projects.individual.utils.Cache;

@RunWith(MockitoJUnitRunner.class)
public class CacheTests
{
	private Cache<String, City> cache;
	
	@Mock
	private City a, b, c;
	
	@Before
	public void setup()
	{
		cache = new Cache<>(2);
	}
	
	@Test
	public void test()
	{
		cache.put("a", a);
		cache.put("b", b);
		
		Assertions.assertEquals(b, cache.get("b"));
		Assertions.assertEquals(a, cache.get("a"));
		
		cache.put("c", c);
		
		Assertions.assertEquals(a, cache.get("a"));
		Assertions.assertEquals(null, cache.get("b"));
		Assertions.assertEquals(c, cache.get("c"));
	}
	
	@Test
	public void getTest()
	{
		cache.put("a", a);
		Assertions.assertEquals(a, cache.get("a"));
		Assertions.assertEquals(null, cache.get("b"));
	}
	
	@Test
	public void keysTest()
	{
		cache.put("a", a);
		Assertions.assertEquals(Arrays.asList("a"), cache.keys());
	}
	
	@Test
	public void putTest()
	{
		cache.put("a", a);
		Assertions.assertEquals(1, cache.size());
	}

	@Test
	public void removeTest()
	{
		cache.put("a", a);
		cache.remove("a");
		Assertions.assertEquals(0, cache.size());
	}
}
