package ua.tqs.projects.individual.utils;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;

public class PairTests
{
	@Test
	public void test()
	{
		Pair<String, String> pair = new Pair<>("a", "b");
		
		Assertions.assertEquals("a", pair.getKey());
		Assertions.assertEquals("b", pair.getValue());
		
		pair.setKey("c");
		pair.setValue("d");
		
		Assertions.assertEquals("c", pair.getKey());
		Assertions.assertEquals("d", pair.getValue());
	}
}
