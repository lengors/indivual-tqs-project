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

	@GetMapping(value = "/hits")
	public Map<String, Object> getHits()
	{
		return statisticsRepository.findAll().stream().collect(Collectors.toMap(Statistics::getKey, Statistics::getHits));
	}
	
	@SuppressWarnings("serial")
	@GetMapping(value = "/hits/sum")
	public Map<String, Object> getHitsSum()
	{
		return new HashMap<String, Object>() {{
			put("sum", statisticsRepository.findAll().stream().collect(Collectors.summingInt(Statistics::getHits)));
		}};
	}
	
	@GetMapping(value = "/hits/cities")
	public Map<String, Object> getHitsCities()
	{
		Map<String, Object> map = new HashMap<>();
		map.put("hits", statisticsRepository.findById("cities").orElseGet(() -> new Statistics()).getHits());
		return map;
	}
	
	@GetMapping(value = "/hits/meteorology", params = "id")
	public Map<String, Object> getHitsById(@RequestParam int id)
	{
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("hits", statisticsRepository.findById(String.format("meteorology?id=%d", id)).orElseGet(() -> new Statistics()).getHits());
		return map;
	}
	
	@SuppressWarnings("serial")
	@GetMapping(value = "/hits/meteorology", params = "day")
	public Map<String, Object> GetHitsByDay(@RequestParam int day)
	{
		return new HashMap<String, Object>() {{
			put("hits", statisticsRepository.findById(String.format("meteorology?day=%d", day)).orElseGet(() -> new Statistics()).getHits());
		}};
	}
	
	@SuppressWarnings("serial")
	@GetMapping(value = "/hits/meteorology", params = { "id", "days" })
	public Map<String, Object> GetHitsByIdDay(@RequestParam int id, @RequestParam int days)
	{
		return new HashMap<String, Object>() {{
			put("hits", statisticsRepository.findById(String.format("meteorology?id=%d&days=%d", id, days)).orElseGet(() -> new Statistics()).getHits());
		}};
	}
	
	@GetMapping(value = "/misses")
	public Map<String, Object> GetMisses()
	{
		return statisticsRepository.findAll().stream().collect(Collectors.toMap(x -> x.getKey(), x -> x.getMisses()));
	}
	
	@SuppressWarnings("serial")
	@GetMapping(value = "/misses/sum")
	public Map<String, Object> GetMissesSum()
	{
		return new HashMap<String, Object>() {{
			put("sum", statisticsRepository.findAll().stream().collect(Collectors.summingInt(x -> x.getMisses())));
		}};
	}
	
	@SuppressWarnings("serial")
	@GetMapping(value = "/misses/cities")
	public Map<String, Object> GetMissesCities()
	{
		return new HashMap<String, Object>() {{
			put("misses", statisticsRepository.findById("cities").orElseGet(() -> new Statistics()).getMisses());
		}};
	}
	
	@SuppressWarnings("serial")
	@GetMapping(value = "/misses/meteorology", params = "id")
	public Map<String, Object> GetMissesById(@RequestParam int id)
	{
		return new HashMap<String, Object>() {{
			put("misses", statisticsRepository.findById(String.format("meteorology?id=%d", id)).orElseGet(() -> new Statistics()).getMisses());
		}};
	}
	
	@SuppressWarnings("serial")
	@GetMapping(value = "/misses/meteorology", params = "day")
	public Map<String, Object> GetMissesByDay(@RequestParam int day)
	{
		return new HashMap<String, Object>() {{
			put("misses", statisticsRepository.findById(String.format("meteorology?day=%d", day)).orElseGet(() -> new Statistics()).getMisses());
		}};
	}
	
	@SuppressWarnings("serial")
	@GetMapping(value = "/misses/meteorology", params = { "id", "days" })
	public Map<String, Object> GetMissesByIdDay(@RequestParam int id, @RequestParam int days)
	{
		return new HashMap<String, Object>() {{
			put("misses", statisticsRepository.findById(String.format("meteorology?id=%d&days=%d", id, days)).orElseGet(() -> new Statistics()).getMisses());
		}};
	}

	@GetMapping(value = "/requests")
	public Map<String, Object> GetRequests()
	{
		return statisticsRepository.findAll().stream().collect(Collectors.toMap(x -> x.getKey(), x -> x.getRequests()));
	}
	
	@SuppressWarnings("serial")
	@GetMapping(value = "/requests/sum")
	public Map<String, Object> GetRequestsSum()
	{
		return new HashMap<String, Object>() {{
			put("sum", statisticsRepository.findAll().stream().collect(Collectors.summingInt(x -> x.getRequests())));
		}};
	}
	
	@SuppressWarnings("serial")
	@GetMapping(value = "/requests/cities")
	public Map<String, Object> GetRequestsCities()
	{
		return new HashMap<String, Object>() {{
			put("requests", statisticsRepository.findById("cities").orElseGet(() -> new Statistics()).getRequests());
		}};
	}
	
	@SuppressWarnings("serial")
	@GetMapping(value = "/requests/meteorology", params = "id")
	public Map<String, Object> GetRequestsById(@RequestParam int id)
	{
		return new HashMap<String, Object>() {{
			put("requests", statisticsRepository.findById(String.format("meteorology?id=%d", id)).orElseGet(() -> new Statistics()).getRequests());
		}};
	}
	
	@SuppressWarnings("serial")
	@GetMapping(value = "/requests/meteorology", params = "day")
	public Map<String, Object> GetRequestsByDay(@RequestParam int day)
	{
		return new HashMap<String, Object>() {{
			put("requests", statisticsRepository.findById(String.format("meteorology?day=%d", day)).orElseGet(() -> new Statistics()).getRequests());
		}};
	}
	
	@SuppressWarnings("serial")
	@GetMapping(value = "/requests/meteorology", params = { "id", "days" })
	public Map<String, Object> GetRequestsByIdDay(@RequestParam int id, @RequestParam int days)
	{
		return new HashMap<String, Object>() {{
			put("requests", statisticsRepository.findById(String.format("meteorology?id=%d&days=%d", id, days)).orElseGet(() -> new Statistics()).getRequests());
		}};
	}
	
	@SuppressWarnings("serial")
	@GetMapping(value = "/ratio")
	public Map<String, Object> GetRatio()
	{
		List<Statistics> statistics = statisticsRepository.findAll();
		return new HashMap<String, Object>()
		{{
			put("ratio", statistics.stream().collect(Collectors.summingInt(x -> x.getHits())).doubleValue() / statistics.stream().collect(Collectors.summingInt(x -> x.getMisses())).doubleValue());
		}};
	}
	
	@SuppressWarnings("serial")
	@GetMapping(value = "/ratio/cities")
	public Map<String, Object> GetRatioCities()
	{
		Statistics statistics = statisticsRepository.findById("cities").orElseGet(() -> new Statistics());
		return new HashMap<String, Object>()
		{{
			put("ratio", (double) statistics.getHits() / statistics.getMisses());
		}};
	}
	
	@SuppressWarnings("serial")
	@GetMapping(value = "/ratio/meteorology", params = "id")
	public Map<String, Object> GetRatioById(@RequestParam int id)
	{
		Statistics statistics = statisticsRepository.findById(String.format("meteorology?id=%d", id)).orElseGet(() -> new Statistics());
		return new HashMap<String, Object>()
		{{
			put("ratio", (double) statistics.getHits() / statistics.getMisses());
		}};
	}
	
	@SuppressWarnings("serial")
	@GetMapping(value = "/ratio/meteorology", params = "day")
	public Map<String, Object> GetRatioByDay(@RequestParam int day)
	{
		Statistics statistics = statisticsRepository.findById(String.format("meteorology?day=%d", day)).orElseGet(() -> new Statistics());
		return new HashMap<String, Object>()
		{{
			put("ratio", (double) statistics.getHits() / statistics.getMisses());
		}};
	}
	
	@SuppressWarnings("serial")
	@GetMapping(value = "/ratio/meteorology", params = { "id", "days" })
	public Map<String, Object> GetRatioByIdDay(@RequestParam int id, @RequestParam int days)
	{
		Statistics statistics = statisticsRepository.findById(String.format("meteorology?id=%d&days=%d", id, days)).orElseGet(() -> new Statistics());
		return new HashMap<String, Object>()
		{{
			put("ratio", (double) statistics.getHits() / statistics.getMisses());
		}};
	}
}
