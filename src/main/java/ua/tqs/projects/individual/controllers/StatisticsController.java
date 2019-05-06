package ua.tqs.projects.individual.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ua.tqs.projects.individual.entities.Statistics;
import ua.tqs.projects.individual.entities.repositories.StatisticsRepository;

@RestController
public class StatisticsController
{
	@Autowired
	private StatisticsRepository statisticsRepository;

	static final String CITIES = "cities";
	static final String BY_ID = "meteorology?id=%d";
	static final String BY_DAY = "meteorology?day=%d";
	static final String BY_ID_DAY = "meteorology?id=%d&days=%d";
	
	static final String HITS = "hits";
	static final String RATIO = "ratio";
	static final String MISSES = "misses";
	static final String REQUESTS = "requests";
	
	@GetMapping(value = "/hits")
	public Map<String, Object> getHits()
	{
		return statisticsRepository.findAll().stream().collect(Collectors.toMap(Statistics::getKey, Statistics::getHits));
	}
	
	@GetMapping(value = "/hits/sum")
	public Map<String, Object> getHitsSum()
	{
		Map<String, Object> map = new HashMap<>();
		map.put("sum", statisticsRepository.findAll().stream().collect(Collectors.summingInt(Statistics::getHits)));
		return map;
	}
	
	@GetMapping(value = "/hits/cities")
	public Map<String, Object> getHitsCities()
	{
		Map<String, Object> map = new HashMap<>();
		map.put(HITS, statisticsRepository.findById(CITIES).orElseGet(Statistics::new).getHits());
		return map;
	}
	
	@GetMapping(value = "/hits/meteorology", params = "id")
	public Map<String, Object> getHitsById(@RequestParam int id)
	{
		Map<String, Object> map = new HashMap<>();
		map.put(HITS, statisticsRepository.findById(String.format(BY_ID, id)).orElseGet(Statistics::new).getHits());
		return map;
	}
	
	@GetMapping(value = "/hits/meteorology", params = "day")
	public Map<String, Object> getHitsByDay(@RequestParam int day)
	{
		Map<String, Object> map = new HashMap<>();
		map.put(HITS, statisticsRepository.findById(String.format(BY_DAY, day)).orElseGet(Statistics::new).getHits());
		return map;
	}
	
	@GetMapping(value = "/hits/meteorology", params = { "id", "days" })
	public Map<String, Object> getHitsByIdDay(@RequestParam int id, @RequestParam int days)
	{
		Map<String, Object> map = new HashMap<>();
		map.put(HITS, statisticsRepository.findById(String.format(BY_ID_DAY, id, days)).orElseGet(Statistics::new).getHits());
		return map;
	}
	
	@GetMapping(value = "/misses")
	public Map<String, Object> getMisses()
	{
		return statisticsRepository.findAll().stream().collect(Collectors.toMap(Statistics::getKey, Statistics::getMisses));
	}

	@GetMapping(value = "/misses/sum")
	public Map<String, Object> getMissesSum()
	{
		Map<String, Object> map = new HashMap<>();
		map.put("sum", statisticsRepository.findAll().stream().collect(Collectors.summingInt(Statistics::getMisses)));
		return map;
	}

	@GetMapping(value = "/misses/cities")
	public Map<String, Object> getMissesCities()
	{
		Map<String, Object> map = new HashMap<>();
		map.put(MISSES, statisticsRepository.findById(CITIES).orElseGet(Statistics::new).getMisses());
		return map;
	}
	
	@GetMapping(value = "/misses/meteorology", params = "id")
	public Map<String, Object> getMissesById(@RequestParam int id)
	{
		Map<String, Object> map = new HashMap<>();
		map.put(MISSES, statisticsRepository.findById(String.format(BY_ID, id)).orElseGet(Statistics::new).getMisses());
		return map;
	}
	
	@GetMapping(value = "/misses/meteorology", params = "day")
	public Map<String, Object> getMissesByDay(@RequestParam int day)
	{
		Map<String, Object> map = new HashMap<>();
		map.put(MISSES, statisticsRepository.findById(String.format(BY_DAY, day)).orElseGet(Statistics::new).getMisses());
		return map;
	}

	@GetMapping(value = "/misses/meteorology", params = { "id", "days" })
	public Map<String, Object> getMissesByIdDay(@RequestParam int id, @RequestParam int days)
	{
		Map<String, Object> map = new HashMap<>();
		map.put(MISSES, statisticsRepository.findById(String.format(BY_ID_DAY, id, days)).orElseGet(Statistics::new).getMisses());
		return map;
	}

	@GetMapping(value = "/requests")
	public Map<String, Object> getRequests()
	{
		return statisticsRepository.findAll().stream().collect(Collectors.toMap(Statistics::getKey, Statistics::getRequests));
	}

	@GetMapping(value = "/requests/sum")
	public Map<String, Object> getRequestsSum()
	{
		Map<String, Object> map = new HashMap<>();
		map.put("sum", statisticsRepository.findAll().stream().collect(Collectors.summingInt(Statistics::getRequests)));
		return map;
	}
	
	@GetMapping(value = "/requests/cities")
	public Map<String, Object> getRequestsCities()
	{
		Map<String, Object> map = new HashMap<>();
		map.put(REQUESTS, statisticsRepository.findById(CITIES).orElseGet(Statistics::new).getRequests());
		return map;
	}

	@GetMapping(value = "/requests/meteorology", params = "id")
	public Map<String, Object> getRequestsById(@RequestParam int id)
	{
		Map<String, Object> map = new HashMap<>();
		map.put(REQUESTS, statisticsRepository.findById(String.format(BY_ID, id)).orElseGet(Statistics::new).getRequests());
		return map;
	}

	@GetMapping(value = "/requests/meteorology", params = "day")
	public Map<String, Object> getRequestsByDay(@RequestParam int day)
	{
		Map<String, Object> map = new HashMap<>();
		map.put(REQUESTS, statisticsRepository.findById(String.format(BY_DAY, day)).orElseGet(Statistics::new).getRequests());
		return map;
	}

	@GetMapping(value = "/requests/meteorology", params = { "id", "days" })
	public Map<String, Object> getRequestsByIdDay(@RequestParam int id, @RequestParam int days)
	{
		Map<String, Object> map = new HashMap<>();
		map.put(REQUESTS, statisticsRepository.findById(String.format(BY_ID_DAY, id, days)).orElseGet(Statistics::new).getRequests());
		return map;
	}

	@GetMapping(value = "/ratio")
	public Map<String, Object> getRatio()
	{
		List<Statistics> statistics = statisticsRepository.findAll();
		Map<String, Object> map = new HashMap<>();
		map.put(RATIO, statistics.stream().collect(Collectors.summingInt(Statistics::getHits)).doubleValue() / statistics.stream().collect(Collectors.summingInt(Statistics::getMisses)).doubleValue());
		return map;
	}

	@GetMapping(value = "/ratio/cities")
	public Map<String, Object> getRatioCities()
	{
		Statistics statistics = statisticsRepository.findById(CITIES).orElseGet(Statistics::new);
		Map<String, Object> map = new HashMap<>();
		map.put(RATIO, (double) statistics.getHits() / statistics.getMisses());
		return map;
	}

	@GetMapping(value = "/ratio/meteorology", params = "id")
	public Map<String, Object> getRatioById(@RequestParam int id)
	{
		Statistics statistics = statisticsRepository.findById(String.format(BY_ID, id)).orElseGet(Statistics::new);
		Map<String, Object> map = new HashMap<>();
		map.put(RATIO, (double) statistics.getHits() / statistics.getMisses());
		return map;
	}

	@GetMapping(value = "/ratio/meteorology", params = "day")
	public Map<String, Object> getRatioByDay(@RequestParam int day)
	{
		Statistics statistics = statisticsRepository.findById(String.format(BY_DAY, day)).orElseGet(Statistics::new);
		Map<String, Object> map = new HashMap<>();
		map.put(RATIO, (double) statistics.getHits() / statistics.getMisses());
		return map;
	}

	@GetMapping(value = "/ratio/meteorology", params = { "id", "days" })
	public Map<String, Object> getRatioByIdDay(@RequestParam int id, @RequestParam int days)
	{
		Statistics statistics = statisticsRepository.findById(String.format(BY_ID_DAY, id, days)).orElseGet(Statistics::new);
		Map<String, Object> map = new HashMap<>();
		map.put(RATIO, (double) statistics.getHits() / statistics.getMisses());
		return map;
	}
}
