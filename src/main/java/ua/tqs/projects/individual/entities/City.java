package ua.tqs.projects.individual.entities;

import javax.persistence.Id;
import javax.persistence.Entity;

@Entity
public class City
{
	@Id
	private Integer globalIdLocal;

	private Integer idRegiao;
	private Integer idConcelho;
	private Integer idDistrito;

	private String latitude;
	private String longitude;
	private String idAreaAviso;
	private String local;

	public Integer getGlobalIdLocal()
	{
		return globalIdLocal;
	}

	public void setGlobalIdLocal(Integer globalIdLocal)
	{
		this.globalIdLocal = globalIdLocal;
	}

	public Integer getIdRegiao()
	{
		return idRegiao;
	}

	public void setIdRegiao(Integer idRegiao)
	{
		this.idRegiao = idRegiao;
	}

	public Integer getIdConcelho()
	{
		return idConcelho;
	}

	public void setIdConcelho(Integer idConcelho)
	{
		this.idConcelho = idConcelho;
	}

	public Integer getIdDistrito()
	{
		return idDistrito;
	}

	public void setIdDistrito(Integer idDistrito)
	{
		this.idDistrito = idDistrito;
	}

	public String getLatitude()
	{
		return latitude;
	}

	public void setLatitude(String latitude)
	{
		this.latitude = latitude;
	}

	public String getLongitude()
	{
		return longitude;
	}

	public void setLongitude(String longitude)
	{
		this.longitude = longitude;
	}

	public String getIdAreaAviso()
	{
		return idAreaAviso;
	}

	public void setIdAreaAviso(String idAreaAviso)
	{
		this.idAreaAviso = idAreaAviso;
	}

	public String getLocal()
	{
		return local;
	}

	public void setLocal(String local)
	{
		this.local = local;
	}
}
