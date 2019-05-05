package ua.tqs.projects.individual.controllers;

import java.util.Map.Entry;

import org.hamcrest.Matchers;

import org.json.JSONObject;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;

import org.springframework.http.MediaType;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@SpringBootTest
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
public class StatisticsControllerTests
{
	@Autowired
	private MockMvc mvc;

	@Test
	public void getHitsTest() throws Exception
	{
		JSONObject o = new JSONObject(mvc.perform(MockMvcRequestBuilders.get("/hits"))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andReturn().getResponse().getContentAsString());
		for (Entry<String, Object> entry : o.toMap().entrySet())
			Assertions.assertTrue(entry.getValue() instanceof Integer);
	}

	@Test
	public void getHitsSumTest() throws Exception
	{
		mvc.perform(MockMvcRequestBuilders.get("/hits/sum")).andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.jsonPath("$",
						Matchers.hasEntry(Matchers.is("sum"), Matchers.isA(Integer.class))));
	}

	@Test
	public void getHitsCitiesTest() throws Exception
	{
		mvc.perform(MockMvcRequestBuilders.get("/hits/cities")).andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.jsonPath("$",
						Matchers.hasEntry(Matchers.is("hits"), Matchers.isA(Integer.class))));
	}

	@Test
	public void getMissesTest() throws Exception
	{
		JSONObject o = new JSONObject(mvc.perform(MockMvcRequestBuilders.get("/misses"))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andReturn().getResponse().getContentAsString());
		for (Entry<String, Object> entry : o.toMap().entrySet())
			Assertions.assertTrue(entry.getValue() instanceof Integer);
	}

	@Test
	public void getMissesSumTest() throws Exception
	{
		mvc.perform(MockMvcRequestBuilders.get("/misses/sum")).andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.jsonPath("$",
						Matchers.hasEntry(Matchers.is("sum"), Matchers.isA(Integer.class))));
	}

	@Test
	public void getMissesCitiesTest() throws Exception
	{
		mvc.perform(MockMvcRequestBuilders.get("/misses/cities")).andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.jsonPath("$",
						Matchers.hasEntry(Matchers.is("misses"), Matchers.isA(Integer.class))));
	}

	@Test
	public void getRequestsTest() throws Exception
	{
		JSONObject o = new JSONObject(mvc.perform(MockMvcRequestBuilders.get("/requests"))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andReturn().getResponse().getContentAsString());
		for (Entry<String, Object> entry : o.toMap().entrySet())
			Assertions.assertTrue(entry.getValue() instanceof Integer);
	}

	@Test
	public void getRequestsSumTest() throws Exception
	{
		mvc.perform(MockMvcRequestBuilders.get("/requests/sum")).andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.jsonPath("$",
						Matchers.hasEntry(Matchers.is("sum"), Matchers.isA(Integer.class))));
	}

	@Test
	public void getRequestsCitiesTest() throws Exception
	{
		mvc.perform(MockMvcRequestBuilders.get("/requests/cities")).andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.jsonPath("$",
						Matchers.hasEntry(Matchers.is("requests"), Matchers.isA(Integer.class))));
	}

	@Test
	public void getRatioTest() throws Exception
	{
		mvc.perform(MockMvcRequestBuilders.get("/ratio")).andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.jsonPath("$",
						Matchers.hasEntry(Matchers.is("ratio"), Matchers.isA(Number.class))));
	}

	@Test
	public void getRatioCitiesTest() throws Exception
	{
		mvc.perform(MockMvcRequestBuilders.get("/ratio/cities")).andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.jsonPath("$",
						Matchers.hasEntry(Matchers.is("ratio"), Matchers.isA(Number.class))));
	}
}
