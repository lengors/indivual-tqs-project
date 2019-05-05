package ua.tqs.projects.individual.utils;

import java.util.List;

public class TTLCache<K, V> implements ICache<K, V>, Runnable
{
	public static final long SECOND = SECOND(1);
	public static final long MINUTE = MINUTE(1);
	public static final long HOUR = HOUR(1);
	public static final long DAY = DAY(1);

	public static long DEFAULT_TTL = SECOND(5);
	
	public static long DAY(long day)
	{
		return HOUR(24 * day);
	}
	
	public static long HOUR(long hour)
	{
		return MINUTE(60 * hour);
	}
	
	public static long MINUTE(long minute)
	{
		return SECOND(60 * minute);
	}
	
	public static long SECOND(long second)
	{
		return second * (long) 1e9;
	}

	private Cache<K, Pair<Long, V>> cache;
	private long cacheTTL;
	
	public TTLCache(int maxCacheSize, long cacheTTL)
	{
		cache = new Cache<>(maxCacheSize);
		this.cacheTTL = cacheTTL;
	}
	
	public TTLCache(long cacheTTL)
	{
		this.cacheTTL = cacheTTL;
		cache = new Cache<>();
	}

	public TTLCache()
	{
		this(DEFAULT_TTL);
	}

	@Override
	public void clear()
	{
		cache.clear();
	}
	
	@Override
	public synchronized V get(K key)
	{
		checkTTL(key);
		Pair<Long, V> value = cache.get(key);
		return value != null ? value.setKey(System.nanoTime() + cacheTTL).getValue() : null;
	}

	@Override
	public synchronized List<K> keys()
	{
		return cache.keys();
	}
	
	@Override
	public synchronized void put(K key, V value)
	{
		cache.put(key, new Pair<>(System.nanoTime() + cacheTTL, value));
	}

	@Override
	public synchronized void remove(K key)
	{
		cache.remove(key);
	}

	@Override
	public void run()
	{
		while (!Thread.interrupted())
		{
			synchronized (this)
			{
				for (K key : cache.keys())
					checkTTL(key);
			}
			try
			{
				Thread.sleep(1000);
			}
			catch (InterruptedException e)
			{
				Thread.currentThread().interrupt();
			}
		}
	}

	@Override
	public synchronized int size()
	{
		return cache.size();
	}
	
	private void checkTTL(K key)
	{
		Pair<Long, V> value = cache.raw(key);
		if (value != null && System.nanoTime() > value.getKey())
			cache.remove(key);
	}
}
