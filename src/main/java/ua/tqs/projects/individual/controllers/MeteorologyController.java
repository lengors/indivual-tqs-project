package ua.tqs.projects.individual.controllers;

import java.util.Map;
import java.util.List;
import java.util.HashMap;
import java.util.Optional;
import java.util.ArrayList;

import java.util.function.Consumer;
import java.util.function.Supplier;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ua.tqs.projects.individual.utils.TTLCache;
import ua.tqs.projects.individual.utils.Converter;
import ua.tqs.projects.individual.utils.Requester;

import ua.tqs.projects.individual.entities.City;
import ua.tqs.projects.individual.entities.Statistics;
import ua.tqs.projects.individual.entities.WeatherType;
import ua.tqs.projects.individual.entities.WindSpeedClass;

import ua.tqs.projects.individual.entities.repositories.CityRepository;
import ua.tqs.projects.individual.entities.repositories.StatisticsRepository;
import ua.tqs.projects.individual.entities.repositories.WeatherTypeRepository;
import ua.tqs.projects.individual.entities.repositories.WindSpeedClassRepository;

@RestController
public class MeteorologyController
{
	@Autowired
	private CityRepository cityRepository;

	@Autowired
	private StatisticsRepository statisticsRepository;

	@Autowired
	private WeatherTypeRepository weatherTypeRepository;

	@Autowired
	private WindSpeedClassRepository windSpeedClassRepository;
	
	private TTLCache<String, Map<String, Object>> cache;
	private Thread thread;

	@PostConstruct
	public void Init()
	{
		cache = new TTLCache<>();
		thread = new Thread(cache);
		thread.start();
	}

	@Override
	public void finalize()
	{
		thread.interrupt();
		try
		{
			thread.join();
		} catch (InterruptedException e)
		{
			throw new RuntimeException(e);
		}
	}

	@SuppressWarnings("serial")
	@GetMapping(value = "/cities")
	public Map<String, Object> GetCities()
	{
		Map<String, Object> result = Get("cities", () ->
		{
			List<Object> list = new ArrayList<>();
			cityRepository.findAll().forEach(city -> list.add(Converter.ToMap(city)));
			return new HashMap<String, Object>()
			{
				{
					put("data", list);
				}
			};
		});
		return result;
	}

	@GetMapping(value = "/meteorology", params = "day")
	public Map<String, Object> GetByDay(@RequestParam int day)
	{
		return Get(String.format("meteorology?day=%d", day),
				String.format(
						"http://api.ipma.pt/open-data/forecast/meteorology/cities/daily/hp-daily-forecast-day%d.json",
						day),
				this::Fix);
	}

	@GetMapping(value = "/meteorology", params = "id")
	public Map<String, Object> GetById(@RequestParam int id)
	{
		return Get(String.format("meteorology?id=%d", id),
				String.format("http://api.ipma.pt/open-data/forecast/meteorology/cities/daily/%d.json", id),
				(Map<String, Object> result) ->
				{
					Fix(result, id);
				});
	}

	@SuppressWarnings("unchecked")
	@GetMapping(value = "/meteorology", params =
	{
			"id", "days"
	})
	public Map<String, Object> GetByIdDay(@RequestParam int id, @RequestParam int days)
	{
		return Get(String.format("meteorology?id=%d&days=%d", id, days),
				String.format("http://api.ipma.pt/open-data/forecast/meteorology/cities/daily/%d.json", id),
				(Map<String, Object> result) ->
				{
					result.put("data",
							((List<Object>) result.get("data")).subList(0, days >= 1 && days <= 5 ? days : 5));
					Fix(result, id);
				});
	}

	@SuppressWarnings("unchecked")
	private Map<String, Object> Fix(Map<String, Object> result, Object... args)
	{
		((List<Map<String, Object>>) result.get("data")).forEach((Map<String, Object> instance) ->
		{
			instance.put("weatherType", Converter.ToMap(
					weatherTypeRepository.getOne((Integer) instance.get("idWeatherType")), "hibernateLazyInitializer"));
			instance.put("classWindSpeed",
					Converter.ToMap(windSpeedClassRepository.getOne((Integer) instance.get("classWindSpeed")),
							"hibernateLazyInitializer"));
			Optional<City> city = cityRepository
					.findById(args.length == 0 ? (Integer) instance.get("globalIdLocal") : (Integer) args[0]);
			if (city.isPresent())
				instance.put("city", Converter.ToMap(city.get()));
		});
		return result;
	}

	private Map<String, Object> Get(String key, String url, Consumer<Map<String, Object>> consumer)
	{
		Map<String, Object> result;
		Statistics statistics = statisticsRepository.findById(key).orElseGet(() -> new Statistics().setKey(key));
		if ((result = cache.get(key)) == null)
		{
			cache.put(key, result = Requester.Get(url, Requester.AS_MAP));
			statistics.setMisses(statistics.getMisses() + 1);
			consumer.accept(result);
		} else
			statistics.setHits(statistics.getHits() + 1);
		statistics.setRequests(statistics.getRequests() + 1);
		statisticsRepository.saveAndFlush(statistics);
		return result;
	}

	private Map<String, Object> Get(String key, Supplier<Map<String, Object>> supplier)
	{
		Map<String, Object> result;
		Statistics statistics = statisticsRepository.findById(key).orElseGet(() -> new Statistics().setKey(key));
		if ((result = cache.get(key)) == null)
		{
			cache.put(key, result = supplier.get());
			statistics.setMisses(statistics.getMisses() + 1);
		} else
			statistics.setHits(statistics.getHits() + 1);
		statistics.setRequests(statistics.getRequests() + 1);
		statisticsRepository.saveAndFlush(statistics);
		return result;
	}

	@Autowired
	@SuppressWarnings(
	{
			"unchecked", "rawtypes"
	})
	private void UpdateRepository(PlatformTransactionManager transactionManager)
	{
		new TransactionTemplate(transactionManager).execute(new TransactionCallback()
		{
			@Override
			public Object doInTransaction(TransactionStatus status)
			{
				Map<String, Object> result = Requester.Get("http://api.ipma.pt/open-data/weather-type-classe.json",
						Requester.AS_MAP);
				List<Object> weatherTypes = (List<Object>) result.get("data");
				for (Object weatherType : weatherTypes)
				{
					Map<String, Object> weatherTypeObject = (Map<String, Object>) weatherType;
					Integer id = (Integer) weatherTypeObject.get("idWeatherType");
					WeatherType weatherTypeInstance = weatherTypeRepository.findById(id)
							.orElseGet(() -> new WeatherType());
					Converter.FromMap(weatherTypeInstance, weatherTypeObject);
					weatherTypeRepository.saveAndFlush(weatherTypeInstance);
				}
				result = Requester.Get("http://api.ipma.pt/open-data/wind-speed-daily-classe.json", Requester.AS_MAP);
				List<Object> windSpeedClasses = (List<Object>) result.get("data");
				for (Object windSpeedClass : windSpeedClasses)
				{
					Map<String, Object> windSpeedClassObject = (Map<String, Object>) windSpeedClass;
					windSpeedClassObject.put("classWindSpeed",
							Integer.parseInt((String) windSpeedClassObject.get("classWindSpeed")));
					Integer id = (Integer) windSpeedClassObject.get("classWindSpeed");
					WindSpeedClass windSpeedClassInstance = windSpeedClassRepository.findById(id)
							.orElseGet(() -> new WindSpeedClass());
					Converter.FromMap(windSpeedClassInstance, windSpeedClassObject);
					windSpeedClassRepository.saveAndFlush(windSpeedClassInstance);
				}
				result = Requester.Get("http://api.ipma.pt/open-data/distrits-islands.json", Requester.AS_MAP);
				List<Object> cities = (List<Object>) result.get("data");
				for (Object city : cities)
				{
					Map<String, Object> cityObject = (Map<String, Object>) city;
					Integer id = (Integer) cityObject.get("globalIdLocal");
					City cityInstance = cityRepository.findById(id).orElseGet(() -> new City());
					Converter.FromMap(cityInstance, cityObject);
					cityRepository.saveAndFlush(cityInstance);
				}
				return null;
			}
		});
	}
}
