package com.example.SampleSpringBoot;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.junit.WireMockRule;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;
import static com.github.tomakehurst.wiremock.client.WireMock.*;

//@RunWith(SpringRunner.class)
//@SpringBootTest
public class TestingWireMock {

	public int port = 8083;
	public int mainPort = 8080;
	private String realServer = "http://localhost:" + mainPort;
	private String fakeServer = "http://localhost:" + port;
	private String body = "{\n" +
	        "  \"id\": 1,\n" +
	        "  \"name\": \"Leanne Graham\",\n" +
	        "  \"username\": \"Bret\",\n" +
	        "  \"email\": \"Sincere@april.biz\",\n" +
	        "  \"address\": {\n" +
	        "    \"street\": \"Kulas Light\",\n" +
	        "    \"suite\": \"Apt. 556\",\n" +
	        "    \"city\": \"Gwenborough\",\n" +
	        "    \"zipcode\": \"92998-3874\",\n" +
	        "    \"geo\": {\n" +
	        "      \"lat\": \"-37.3159\",\n" +
	        "      \"lng\": \"81.1496\"\n" +
	        "    }\n" +
	        "  },\n" +
	        "  \"phone\": \"1-770-736-8031 x56442\",\n" +
	        "  \"website\": \"hildegard.org\",\n" +
	        "  \"company\": {\n" +
	        "    \"name\": \"Romaguera-Crona\",\n" +
	        "    \"catchPhrase\": \"Multi-layered client-server neural-net\",\n" +
	        "    \"bs\": \"harness real-time e-markets\"\n" +
	        "  }\n" +
	        "}";

	@Rule
	public WireMockRule wireMockRule = new WireMockRule(port);
//	WireMockServer wireMockServer = new WireMockServer(wireMockConfig().port(8084));

	@Test
	public void firstTest() {
//		wireMockServer.start();
		stubFor(get(urlEqualTo("/hello"))
				.willReturn(aResponse()
				.withStatus(200)
				.withHeader("Content-Type", "application/json")
				.withBody(body)));

		RestTemplate restTemplate = new RestTemplate();
		String resourceUrl = fakeServer;
		ResponseEntity<String> response = restTemplate.getForEntity(resourceUrl + "/hello", String.class);

		assertNotNull(response);
		assertTrue("Status code not equals to 200", response.getStatusCode().equals(HttpStatus.OK));
		assertTrue("Contains fail", response.getBody().contains("\"name\": \"Leanne Graham\""));
	}

	// public static void main(String args[]){
	//
	// WireMockServer wireMockServer = new WireMockServer(options().port(9999));
	// wireMockServer.start();
	// WireMock.configureFor("localhost", wireMockServer.port());
	//
	// stubFor(get(urlEqualTo("/test"))
	// .willReturn(aResponse()
	// .withBody("Hello")));
	//
	// }
}
