package ua.tqs.projects.individual.entities;

import javax.persistence.Id;
import javax.persistence.Entity;

@Entity
public class Statistics
{
	@Id
	private String key;

	private int hits = 0;
	private int misses = 0;
	private int requests = 0;

	public String getKey()
	{
		return key;
	}

	public Statistics setKey(String key)
	{
		this.key = key;
		return this;
	}

	public int getHits()
	{
		return hits;
	}

	public Statistics setHits(int hits)
	{
		this.hits = hits;
		return this;
	}

	public int getMisses()
	{
		return misses;
	}

	public Statistics setMisses(int misses)
	{
		this.misses = misses;
		return this;
	}

	public int getRequests()
	{
		return requests;
	}

	public Statistics setRequests(int requests)
	{
		this.requests = requests;
		return this;
	}
}
