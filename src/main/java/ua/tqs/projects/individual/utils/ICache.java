package ua.tqs.projects.individual.utils;

import java.util.List;

public interface ICache<K, V>
{
	public void clear();
	public V get(K key);
	public List<K> keys();
	public void put(K key, V value);
	public void remove(K key);
	public int size();
}
