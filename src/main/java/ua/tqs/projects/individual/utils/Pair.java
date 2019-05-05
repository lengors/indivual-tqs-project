package ua.tqs.projects.individual.utils;

public class Pair<K, V>
{
	private V value;
	private K key;

	public Pair(K key, V value)
	{
		this.key = key;
		this.value = value;
	}
	
	public Pair()
	{
		this(null, null);
	}
	
	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((key == null) ? 0 : key.hashCode());
		result = prime * result + ((value == null) ? 0 : value.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		@SuppressWarnings("rawtypes")
		Pair other = (Pair) obj;
		if (key == null)
		{
			if (other.key != null)
				return false;
		} else if (!key.equals(other.key))
			return false;
		if (value == null)
		{
			if (other.value != null)
				return false;
		} else if (!value.equals(other.value))
			return false;
		return true;
	}

	@Override
	public String toString()
	{
		return "Pair [value=" + value + ", key=" + key + "]";
	}

	public V getValue()
	{
		return value;
	}

	public Pair<K, V> setValue(V value)
	{
		this.value = value;
		return this;
	}

	public K getKey()
	{
		return key;
	}

	public Pair<K, V> setKey(K key)
	{
		this.key = key;
		return this;
	}
}
