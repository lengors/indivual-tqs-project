package ua.tqs.projects.individual.utils;

import java.util.Arrays;

import org.junit.Test;
import org.awaitility.Awaitility;
import org.junit.After;
import org.junit.Before;

import org.junit.jupiter.api.Assertions;

import org.mockito.Mock;

import ua.tqs.projects.individual.entities.City;

public class TTLCacheTests
{
	private TTLCache<String, City> cache;
	private Thread thread;
	
	@Mock
	private City a, b, c;
	
	@Before
	public void setup()
	{
		cache = new TTLCache<>(2, TTLCache.SECOND);
		thread = new Thread(cache);
		thread.start();
	}
	
	@After
	public void after() throws InterruptedException
	{
		thread.interrupt();
		thread.join();
	}
	
	@Test
	public void test() throws InterruptedException
	{
		cache.put("a", a);
		cache.put("b", b);
		
		Assertions.assertEquals(b, cache.get("b"));
		Assertions.assertEquals(a, cache.get("a"));
		
		cache.put("c", c);
		
		Assertions.assertEquals(a, cache.get("a"));
		Assertions.assertEquals(null, cache.get("b"));
		Assertions.assertEquals(c, cache.get("c"));
		
		Awaitility.await().until(() -> cache.size() == 0);
		
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
