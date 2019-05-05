package ua.tqs.projects.individual.entities;

import javax.persistence.Id;

import javax.persistence.Entity;

@Entity
public class WeatherType
{
	@Id
	private int idWeatherType;
	private String descIdWeatherTypePT;
	private String descIdWeatherTypeEN;

	public Integer getIdWeatherType()
	{
		return idWeatherType;
	}

	public void setIdWeatherType(Integer idWeatherType)
	{
		this.idWeatherType = idWeatherType;
	}

	public String getDescIdWeatherTypePT()
	{
		return descIdWeatherTypePT;
	}

	public void setDescIdWeatherTypePT(String descIdWeatherTypePT)
	{
		this.descIdWeatherTypePT = descIdWeatherTypePT;
	}

	public String getDescIdWeatherTypeEN()
	{
		return descIdWeatherTypeEN;
	}

	public void setDescIdWeatherTypeEN(String descIdWeatherTypeEN)
	{
		this.descIdWeatherTypeEN = descIdWeatherTypeEN;
	}
}