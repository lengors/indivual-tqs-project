package ua.tqs.projects.individual.utils;

import java.util.Arrays;

import org.junit.Test;
import org.junit.Before;

import org.junit.jupiter.api.Assertions;

import org.mockito.Mock;

import ua.tqs.projects.individual.entities.City;

public class TTLCacheTests
{
	private TTLCache<String, City> cache;
	private long ttl;
	
	@Mock
	private City a, b, c;
	
	@Before
	public void Setup()
	{
		cache = new TTLCache<>(2, ttl = TTLCache.SECOND);
	}
	
	@Test
	public void Test() throws InterruptedException
	{
		cache.put("a", a);
		cache.put("b", b);
		
		Assertions.assertEquals(b, cache.get("b"));
		Assertions.assertEquals(a, cache.get("a"));
		
		cache.put("c", c);
		
		Assertions.assertEquals(a, cache.get("a"));
		Assertions.assertEquals(null, cache.get("b"));
		Assertions.assertEquals(c, cache.get("c"));
		
		long current = System.nanoTime(), target = System.nanoTime() + ttl;
		long amount = target - current;
		while (amount > 0)
		{
			Thread.sleep(amount / (long) 1e6);
			amount = target - System.nanoTime();
		}
		
		Assertions.assertEquals(null, cache.get("a"));
		Assertions.assertEquals(null, cache.get("b"));
		Assertions.assertEquals(null, cache.get("c"));
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
