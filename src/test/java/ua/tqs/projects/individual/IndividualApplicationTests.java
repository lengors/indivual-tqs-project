package ua.tqs.projects.individual;

import java.util.List;

import org.hamcrest.Matchers;

import org.json.JSONObject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.jupiter.api.Assertions;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.http.MediaType;

import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import ua.tqs.projects.individual.controllers.MeteorologyController;

@SpringBootTest
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
public class IndividualApplicationTests
{
	@Autowired
	private MockMvc mvc;

	@Autowired
	private MeteorologyController meteorologyController;
	
	@Test
	public void allTest() throws Exception
	{
		// Ensures that the cache is empty before any test
		meteorologyController.getCache().clear();

		int hits = getStatistic("hits", "cities");
		int misses = getStatistic("misses", "cities");
		int requests = getStatistic("requests", "cities");

		int hitsSum = getStatistic("hits", "sum", "sum");
		int missesSum = getStatistic("misses", "sum", "sum");
		int requestsSum = getStatistic("requests", "sum", "sum");

		getCities();
		getByDay(0);

		int postHits = getStatistic("hits", "cities");
		int postMisses = getStatistic("misses", "cities");
		int postRequests = getStatistic("requests", "cities");

		int postHitsSum = getStatistic("hits", "sum", "sum");
		int postMissesSum = getStatistic("misses", "sum", "sum");
		int postRequestsSum = getStatistic("requests", "sum", "sum");

		Assertions.assertEquals(hits, postHits);
		Assertions.assertEquals(misses + 1, postMisses);
		Assertions.assertEquals(requests + 1, postRequests);

		Assertions.assertEquals(hitsSum, postHitsSum);
		Assertions.assertEquals(missesSum + 2, postMissesSum);
		Assertions.assertEquals(requestsSum + 2, postRequestsSum);

		getCities();
		getByDay(0);

		postHits = getStatistic("hits", "cities");
		postMisses = getStatistic("misses", "cities");
		postRequests = getStatistic("requests", "cities");

		postHitsSum = getStatistic("hits", "sum", "sum");
		postMissesSum = getStatistic("misses", "sum", "sum");
		postRequestsSum = getStatistic("requests", "sum", "sum");

		Assertions.assertEquals(hits + 1, postHits);
		Assertions.assertEquals(misses + 1, postMisses);
		Assertions.assertEquals(requests + 2, postRequests);

		Assertions.assertEquals(hitsSum + 2, postHitsSum);
		Assertions.assertEquals(missesSum + 2, postMissesSum);
		Assertions.assertEquals(requestsSum + 4, postRequestsSum);
	}

	private int getStatistic(String what, String key, String getter) throws Exception
	{
		return (Integer) new JSONObject(mvc.perform(MockMvcRequestBuilders.get(String.format("/%s/%s", what, key)))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.jsonPath("$",
						Matchers.hasEntry(Matchers.is(getter), Matchers.isA(Integer.class))))
				.andReturn().getResponse().getContentAsString()).toMap().get(getter);
	}

	private int getStatistic(String what, String key) throws Exception
	{
		return getStatistic(what, key, what);
	}

	private void getCities() throws Exception
	{
		mvc.perform(MockMvcRequestBuilders.get("/cities")).andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(MockMvcResultMatchers.jsonPath("$.data", Matchers.isA(List.class)))
				.andExpect(MockMvcResultMatchers.jsonPath("$.data.length()", Matchers.greaterThan(0)))
				.andExpect(MockMvcResultMatchers.jsonPath("$.data",
						Matchers.everyItem(Matchers.hasEntry(Matchers.is("local"), Matchers.isA(String.class)))))
				.andExpect(MockMvcResultMatchers.jsonPath("$.data",
						Matchers.everyItem(
								Matchers.hasEntry(Matchers.is("globalIdLocal"), Matchers.isA(Integer.class)))))
				.andReturn().getResponse().getContentAsString();
	}

	private void getByDay(int day) throws Exception
	{
		mvc.perform(MockMvcRequestBuilders.get(String.format("/meteorology?day=%d", day)))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.jsonPath("$.data", Matchers.isA(List.class)))
				.andExpect(MockMvcResultMatchers.jsonPath("$.data", Matchers.everyItem(Matchers.hasKey("tMin"))))
				.andExpect(MockMvcResultMatchers.jsonPath("$.data", Matchers.everyItem(Matchers.hasKey("tMax"))))
				.andExpect(MockMvcResultMatchers.jsonPath("$.data", Matchers.everyItem(Matchers.hasKey("predWindDir"))))
				.andExpect(
						MockMvcResultMatchers.jsonPath("$.data", Matchers.everyItem(Matchers.hasKey("precipitaProb"))))
				.andExpect(MockMvcResultMatchers.jsonPath("$.data",
						Matchers.everyItem(Matchers.hasEntry(Matchers.is("weatherType"),
								Matchers.allOf(Matchers.hasKey("descIdWeatherTypePT"),
										Matchers.hasKey("idWeatherType"))))))
				.andExpect(
						MockMvcResultMatchers.jsonPath("$.data",
								Matchers.everyItem(Matchers.hasEntry(Matchers.is("classWindSpeed"),
										Matchers.allOf(Matchers.hasKey("classWindSpeed"),
												Matchers.hasKey("descClassWindSpeedDailyPT"))))));
	}
}
