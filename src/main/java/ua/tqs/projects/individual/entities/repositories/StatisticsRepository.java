package ua.tqs.projects.individual.entities.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import ua.tqs.projects.individual.entities.Statistics;

public interface StatisticsRepository extends JpaRepository<Statistics, String>
{
}