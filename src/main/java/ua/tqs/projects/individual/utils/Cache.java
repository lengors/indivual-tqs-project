package ua.tqs.projects.individual.utils;

import java.util.ArrayList; 	
import java.util.HashMap;
import java.util.List;

public class Cache<K, V> implements ICache<K, V>
{
	public static int DEFAULT_MAX_SIZE = 10;
	
	private HashMap<K, Node<Pair<K, V>>> map = new HashMap<>();
	private Node<Pair<K, V>> start, end;
	private int maxCacheSize;
	
	public Cache(int maxCacheSize)
	{
		this.maxCacheSize = maxCacheSize;
	}
	
	public Cache()
	{
		this(DEFAULT_MAX_SIZE);
	}
	
	@Override
	public void clear()
	{
		map.clear();
		start = end = null;
	}
	
	@Override
	public V get(K key)
	{
		Node<Pair<K, V>> node = map.get(key);
		if (node != null)
		{
			remove(node);
			add(node);
			return node.getValue().getValue();
		}
		return null;
	}
	
	@Override
	public List<K> keys()
	{
		return new ArrayList<>(map.keySet());
	}
	
	@Override
	public void put(K key, V value)
	{
		Node<Pair<K, V>> node = map.get(key);
		if (node != null)
		{
			node.getValue().setValue(value);
			remove(node);
			add(node);
		}
		else
		{
			if (map.size() >= maxCacheSize)
				remove(map.remove(start.getValue().getKey()));
			map.put(key, add(key, value));
		}
	}
	
	@Override
	public void remove(K key)
	{
		Node<Pair<K, V>> node = map.remove(key);
		if (node != null)
			remove(node);
	}
	
	@Override
	public int size()
	{
		return map.size();
	}
	
	protected V raw(K key)
	{
		Node<Pair<K, V>> node = map.get(key);
		if (node != null)
			return node.getValue().getValue();
		return null;
	}
	
	private void add(Node<Pair<K, V>> node)
	{
		if (end != null)
			end.setNext(node.setPrevious(end));
		if (start == null)
			start = node;
		end = node;
	}
	
	private Node<Pair<K, V>> add(K key, V value)
	{
		Node<Pair<K, V>> node = new Node<>(new Pair<>(key, value));
		add(node);
		return node;
	}
	
	private void remove(Node<Pair<K, V>> node)
	{
		Node<Pair<K, V>> previous = node.getPrevious();
		Node<Pair<K, V>> next = node.getNext();
		if (previous != null)
			previous.setNext(next);
		else
			start = next;
		if (next != null)
			next.setPrevious(previous);
		else
			end = previous;
		node.setPrevious(null).setNext(null);
	}

	public static class Node<V>
	{
		private Node<V> previous = null, next = null;
		private V value;

		public Node(V value)
		{
			this.value = value;
		}

		public Node<V> getPrevious()
		{
			return previous;
		}

		public Node<V> setPrevious(Node<V> previous)
		{
			this.previous = previous;
			return this;
		}

		public Node<V> getNext()
		{
			return next;
		}

		public Node<V> setNext(Node<V> next)
		{
			this.next = next;
			return this;
		}

		public V getValue()
		{
			return value;
		}

		public Node<V> setValue(V value)
		{
			this.value = value;
			return this;
		}
	}
}
