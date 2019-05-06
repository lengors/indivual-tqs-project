package ua.tqs.projects.individual.controllers;

import java.util.List;

import org.hamcrest.Matchers;

import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;

import org.springframework.http.MediaType;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;

import org.springframework.test.context.junit4.SpringRunner;

import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@SpringBootTest
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
public class MeteorologyControllerTests
{
	@Autowired
	private MockMvc mvc;

	private int id = 1010500;

	@Test
	public void getCitiesTest() throws Exception
	{
		mvc.perform(MockMvcRequestBuilders.get("/cities")).andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.jsonPath("$.data", Matchers.isA(List.class)))
				.andExpect(MockMvcResultMatchers.jsonPath("$.data.length()", Matchers.greaterThan(0)))
				.andExpect(MockMvcResultMatchers.jsonPath("$.data",
						Matchers.everyItem(Matchers.hasEntry(Matchers.is("local"), Matchers.isA(String.class)))))
				.andExpect(MockMvcResultMatchers.jsonPath("$.data", Matchers
						.everyItem(Matchers.hasEntry(Matchers.is("globalIdLocal"), Matchers.isA(Integer.class)))));
	}

	@Test
	public void getByDayTest() throws Exception
	{
		for (int i = 0; i < 3; i++)
			mvc.perform(MockMvcRequestBuilders.get(String.format("/meteorology?day=%d", i)))
					.andExpect(MockMvcResultMatchers.status().isOk())
					.andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
					.andExpect(MockMvcResultMatchers.jsonPath("$.data", Matchers.isA(List.class)))
					.andExpect(MockMvcResultMatchers.jsonPath("$.data", Matchers.everyItem(Matchers.hasKey("tMin"))))
					.andExpect(MockMvcResultMatchers.jsonPath("$.data", Matchers.everyItem(Matchers.hasKey("tMax"))))
					.andExpect(MockMvcResultMatchers.jsonPath("$.data",
							Matchers.everyItem(Matchers.hasKey("predWindDir"))))
					.andExpect(MockMvcResultMatchers.jsonPath("$.data",
							Matchers.everyItem(Matchers.hasKey("precipitaProb"))))
					.andExpect(MockMvcResultMatchers.jsonPath("$.data",
							Matchers.everyItem(Matchers.hasEntry(Matchers.is("weatherType"),
									Matchers.allOf(Matchers.hasKey("descIdWeatherTypePT"),
											Matchers.hasKey("idWeatherType"))))))
					.andExpect(MockMvcResultMatchers.jsonPath("$.data",
							Matchers.everyItem(Matchers.hasEntry(Matchers.is("classWindSpeed"),
									Matchers.allOf(Matchers.hasKey("classWindSpeed"),
											Matchers.hasKey("descClassWindSpeedDailyPT"))))));
	}

	@Test
	public void getByIdTest() throws Exception
	{
		mvc.perform(MockMvcRequestBuilders.get(String.format("/meteorology?id=%d", id)))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.jsonPath("$.data", Matchers.isA(List.class)))
				.andExpect(MockMvcResultMatchers.jsonPath("$.data", Matchers.hasSize(5)))
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

	@Test
	public void getByIdDaysTest() throws Exception
	{
		for (int i = 1; i <= 5; i++)
			mvc.perform(MockMvcRequestBuilders.get(String.format("/meteorology?id=%d&days=%d", id, i)))
					.andExpect(MockMvcResultMatchers.status().isOk())
					.andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
					.andExpect(MockMvcResultMatchers.jsonPath("$.data", Matchers.isA(List.class)))
					.andExpect(MockMvcResultMatchers.jsonPath("$.data", Matchers.hasSize(i)))
					.andExpect(MockMvcResultMatchers.jsonPath("$.data", Matchers.everyItem(Matchers.hasKey("tMin"))))
					.andExpect(MockMvcResultMatchers.jsonPath("$.data", Matchers.everyItem(Matchers.hasKey("tMax"))))
					.andExpect(MockMvcResultMatchers.jsonPath("$.data",
							Matchers.everyItem(Matchers.hasKey("predWindDir"))))
					.andExpect(MockMvcResultMatchers.jsonPath("$.data",
							Matchers.everyItem(Matchers.hasKey("precipitaProb"))))
					.andExpect(MockMvcResultMatchers.jsonPath("$.data",
							Matchers.everyItem(Matchers.hasEntry(Matchers.is("weatherType"),
									Matchers.allOf(Matchers.hasKey("descIdWeatherTypePT"),
											Matchers.hasKey("idWeatherType"))))))
					.andExpect(MockMvcResultMatchers.jsonPath("$.data",
							Matchers.everyItem(Matchers.hasEntry(Matchers.is("classWindSpeed"),
									Matchers.allOf(Matchers.hasKey("classWindSpeed"),
											Matchers.hasKey("descClassWindSpeedDailyPT"))))));
	}
}
