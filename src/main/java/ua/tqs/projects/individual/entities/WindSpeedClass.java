package ua.tqs.projects.individual.entities;

import javax.persistence.Id;
import javax.persistence.Entity;

@Entity
public class WindSpeedClass
{
	@Id
	private Integer classWindSpeed;

	private String descClassWindSpeedDailyEN;
	private String descClassWindSpeedDailyPT;

	public Integer getClassWindSpeed()
	{
		return classWindSpeed;
	}

	public void setClassWindSpeed(Integer classWindSpeed)
	{
		this.classWindSpeed = classWindSpeed;
	}

	public String getDescClassWindSpeedDailyEN()
	{
		return descClassWindSpeedDailyEN;
	}

	public void setDescClassWindSpeedDailyEN(String descClassWindSpeedDailyEN)
	{
		this.descClassWindSpeedDailyEN = descClassWindSpeedDailyEN;
	}

	public String getDescClassWindSpeedDailyPT()
	{
		return descClassWindSpeedDailyPT;
	}

	public void setDescClassWindSpeedDailyPT(String descClassWindSpeedDailyPT)
	{
		this.descClassWindSpeedDailyPT = descClassWindSpeedDailyPT;
	}
}
