package ua.tqs.projects.individual.utils;

import java.util.Map;

import org.json.JSONObject;

import org.junit.Test;
import org.junit.Before;

import org.junit.runner.RunWith;

import org.junit.jupiter.api.Assertions;

import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class RequesterTests
{
	private Map<String, Object> expectedMap;
	private JSONObject expectedJson;
	private String expectedString;
	
	@Before
	public void Setup()
	{
		expectedString = "{ \"owner\": \"IPMA\",  \"country\": \"PT\",  \"data\": [{\"idRegiao\": 1, \"idAreaAviso\": \"AVR\", \"idConcelho\": 5, \"globalIdLocal\": 1010500, \"latitude\": \"40.6413\", \"idDistrito\": 1, \"local\": \"Aveiro\", \"longitude\": \"-8.6535\"}, {\"idRegiao\": 1, \"idAreaAviso\": \"BJA\", \"idConcelho\": 5, \"globalIdLocal\": 1020500, \"latitude\": \"38.0200\", \"idDistrito\": 2, \"local\": \"Beja\", \"longitude\": \"-7.8700\"}, {\"idRegiao\": 1, \"idAreaAviso\": \"BRG\", \"idConcelho\": 3, \"globalIdLocal\": 1030300, \"latitude\": \"41.5475\", \"idDistrito\": 3, \"local\": \"Braga\", \"longitude\": \"-8.4227\"},  {\"idRegiao\": 1, \"idAreaAviso\": \"BGC\", \"idConcelho\": 2, \"globalIdLocal\": 1040200, \"latitude\": \"41.8076\", \"idDistrito\": 4, \"local\": \"Bragan\\u00e7a\", \"longitude\": \"-6.7606\"},{\"idRegiao\": 1, \"idAreaAviso\": \"CBO\", \"idConcelho\": 2, \"globalIdLocal\": 1050200, \"latitude\": \"39.8217\", \"idDistrito\": 5, \"local\": \"Castelo Branco\", \"longitude\": \"-7.4957\"},  {\"idRegiao\": 1, \"idAreaAviso\": \"CBR\", \"idConcelho\": 3, \"globalIdLocal\": 1060300, \"latitude\": \"40.2081\", \"idDistrito\": 6, \"local\": \"Coimbra\", \"longitude\": \"-8.4194\"}, {\"idRegiao\": 1, \"idAreaAviso\": \"EVR\", \"idConcelho\": 5, \"globalIdLocal\": 1070500, \"latitude\": \"38.5701\", \"idDistrito\": 7, \"local\": \"\\u00c9vora\", \"longitude\": \"-7.9104\"},  {\"idRegiao\": 1, \"idAreaAviso\": \"FAR\", \"idConcelho\": 5, \"globalIdLocal\": 1080500, \"latitude\": \"37.0146\", \"idDistrito\": 8, \"local\": \"Faro\", \"longitude\": \"-7.9331\"}, {\"idRegiao\": 1, \"idAreaAviso\": \"GDA\", \"idConcelho\": 7, \"globalIdLocal\": 1090700, \"latitude\": \"40.5379\", \"idDistrito\": 9, \"local\": \"Guarda\", \"longitude\": \"-7.2647\"}, {\"idRegiao\": 1, \"idAreaAviso\": \"LRA\", \"idConcelho\": 9, \"globalIdLocal\": 1100900, \"latitude\": \"39.7473\", \"idDistrito\": 10, \"local\": \"Leiria\", \"longitude\": \"-8.8069\"},  {\"idRegiao\": 1, \"idAreaAviso\": \"LSB\", \"idConcelho\": 6, \"globalIdLocal\": 1110600, \"latitude\": \"38.7660\", \"idDistrito\": 11, \"local\": \"Lisboa\", \"longitude\": \"-9.1286\"}, {\"idRegiao\": 1, \"idAreaAviso\": \"LSB\", \"idConcelho\": 6, \"globalIdLocal\": 1110622, \"latitude\": \"38.7190\", \"idDistrito\": 11, \"local\": \"Lisboa - Jardim Bot\\u00e2nico\", \"longitude\": \"-9.1498\"}, {\"idRegiao\": 1, \"idAreaAviso\": \"PTG\", \"idConcelho\": 14, \"globalIdLocal\": 1121400, \"latitude\": \"39.2900\", \"idDistrito\": 12, \"local\": \"Portalegre\", \"longitude\": \"-7.4200\"},  {\"idRegiao\": 1, \"idAreaAviso\": \"PTO\", \"idConcelho\": 12, \"globalIdLocal\": 1131200, \"latitude\": \"41.1580\", \"idDistrito\": 13, \"local\": \"Porto\", \"longitude\": \"-8.6294\"},  {\"idRegiao\": 1, \"idAreaAviso\": \"STM\", \"idConcelho\": 16, \"globalIdLocal\": 1141600, \"latitude\": \"39.2000\", \"idDistrito\": 14, \"local\": \"Santar\\u00e9m\", \"longitude\": \"-8.7400\"}, {\"idRegiao\": 1, \"idAreaAviso\": \"STB\", \"idConcelho\": 12, \"globalIdLocal\": 1151200, \"latitude\": \"38.5246\", \"idDistrito\": 15, \"local\": \"Set\\u00fabal\", \"longitude\": \"-8.8856\"}, {\"idRegiao\": 1, \"idAreaAviso\": \"VCT\", \"idConcelho\": 9, \"globalIdLocal\": 1160900, \"latitude\": \"41.6952\", \"idDistrito\": 16, \"local\": \"Viana do Castelo\", \"longitude\": \"-8.8365\"}, {\"idRegiao\": 1, \"idAreaAviso\": \"VRL\", \"idConcelho\": 14, \"globalIdLocal\": 1171400, \"latitude\": \"41.3053\", \"idDistrito\": 17, \"local\": \"Vila Real\", \"longitude\": \"-7.7440\"}, {\"idRegiao\": 1, \"idAreaAviso\": \"VIS\", \"idConcelho\": 23, \"globalIdLocal\": 1182300, \"latitude\": \"40.6585\", \"idDistrito\": 18, \"local\": \"Viseu\", \"longitude\": \"-7.9120\"},  {\"idRegiao\": 2, \"idAreaAviso\": \"MCS\", \"idConcelho\": 3, \"globalIdLocal\": 2310300, \"latitude\": \"32.6485\", \"idDistrito\": 31, \"local\": \"Funchal\", \"longitude\": \"-16.9084\"}, {\"idRegiao\": 2, \"idAreaAviso\": \"MPS\", \"idConcelho\": 1, \"globalIdLocal\": 2320100, \"latitude\": \"33.0700\", \"idDistrito\": 32, \"local\": \"Porto Santo\", \"longitude\": \"-16.3400\"}, {\"idRegiao\": 3, \"idAreaAviso\": \"AOR\", \"idConcelho\": 1, \"globalIdLocal\": 3410100, \"latitude\": \"36.9563\", \"idDistrito\": 41, \"local\": \"Vila do Porto\", \"longitude\": \"-25.1409\"}, {\"idRegiao\": 3, \"idAreaAviso\": \"AOR\", \"idConcelho\": 3, \"globalIdLocal\": 3420300, \"latitude\": \"37.7415\", \"idDistrito\": 42, \"local\": \"Ponta Delgada\", \"longitude\": \"-25.6677\"}, {\"idRegiao\": 3, \"idAreaAviso\": \"ACE\", \"idConcelho\": 1, \"globalIdLocal\": 3430100, \"latitude\": \"38.6700\", \"idDistrito\": 43, \"local\": \"Angra do Hero\\u00edsmo\", \"longitude\": \"-27.2200\"},{\"idRegiao\": 3, \"idAreaAviso\": \"ACE\", \"idConcelho\": 1, \"globalIdLocal\": 3440100, \"latitude\": \"39.0800\", \"idDistrito\": 44, \"local\": \"Santa Cruz da Graciosa\", \"longitude\": \"-28.0000\"}, {\"idRegiao\": 3, \"idAreaAviso\": \"ACE\", \"idConcelho\": 2, \"globalIdLocal\": 3450200, \"latitude\": \"38.6842\", \"idDistrito\": 45, \"local\": \"Velas\", \"longitude\": \"-28.2133\"}, {\"idRegiao\": 3, \"idAreaAviso\": \"ACE\", \"idConcelho\": 2, \"globalIdLocal\": 3460200, \"latitude\": \"38.5325\", \"idDistrito\": 46, \"local\": \"Madalena\", \"longitude\": \"-28.5237\"}, {\"idRegiao\": 3, \"idAreaAviso\": \"ACE\", \"idConcelho\": 1, \"globalIdLocal\": 3470100, \"latitude\": \"38.5363\", \"idDistrito\": 47, \"local\": \"Horta\", \"longitude\": \"-28.6315\"}, {\"idRegiao\": 3, \"idAreaAviso\": \"AOC\", \"idConcelho\": 2, \"globalIdLocal\": 3480200, \"latitude\": \"39.4500\", \"idDistrito\": 48, \"local\": \"Santa Cruz das Flores\", \"longitude\": \"-31.1300\"}, {\"idRegiao\": 3, \"idAreaAviso\": \"AOC\", \"idConcelho\": 1, \"globalIdLocal\": 3490100, \"latitude\": \"39.6700\", \"idDistrito\": 49, \"local\": \"Vila do Corvo\", \"longitude\": \"-31.1200\"  } ] }";
		expectedJson = new JSONObject(expectedString);
		expectedMap = expectedJson.toMap();
	}
	
	@Test
	public void getAsMapTest()
	{
		Map<String, Object> result = Requester.Get("http://api.ipma.pt/open-data/distrits-islands.json", Requester.AS_MAP);
		Assertions.assertEquals(expectedMap, result);
	}
	
	@Test
	public void getAsJsonRequesterTest()
	{
		JSONObject result = Requester.Get("http://api.ipma.pt/open-data/distrits-islands.json", Requester.AS_JSON);
		Assertions.assertTrue(expectedJson.similar(result));
	}
	
	@Test
	public void getAsStringTest()
	{
		String result = Requester.Get("http://api.ipma.pt/open-data/distrits-islands.json", Requester.AS_STRING);
		Assertions.assertEquals(expectedString, result);
	}
	
	@Test
	public void getTest()
	{
		JSONObject result = Requester.Get("http://api.ipma.pt/open-data/distrits-islands.json");
		Assertions.assertTrue(expectedJson.similar(result));
	}
}
