package ua.tqs.projects.individual.entities.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import ua.tqs.projects.individual.entities.WeatherType;

public interface WeatherTypeRepository extends JpaRepository<WeatherType, Integer>
{
}
