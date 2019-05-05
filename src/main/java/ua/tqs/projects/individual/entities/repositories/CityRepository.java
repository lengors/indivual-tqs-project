package ua.tqs.projects.individual.entities.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import ua.tqs.projects.individual.entities.City;

public interface CityRepository extends JpaRepository<City, Integer>
{
}
