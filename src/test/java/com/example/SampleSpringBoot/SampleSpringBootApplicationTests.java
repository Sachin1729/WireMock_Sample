package com.example.SampleSpringBoot;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import com.github.tomakehurst.wiremock.junit.WireMockRule;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SampleSpringBootApplicationTests {

	public int port = 8082;
	public int mainPort = 8081;
	private String realServer = "http://localhost:" + mainPort;
	private String fakeServer = "http://localhost:" + port;
	private String body = "Hello";
	
	@Rule
	public WireMockRule wireMockRule = new WireMockRule(port);

	@Test
	public void contextLoads() {
	}

	@Test
	public void firstTest() {
		// wireMockServer.start();
		stubFor(get(urlEqualTo("/hello"))
				.willReturn(aResponse().withStatus(200).withHeader("Content-Type", "application/json").withBody(body)));

		RestTemplate restTemplate = new RestTemplate();
		String resourceUrl = fakeServer;
		ResponseEntity<String> response = restTemplate.getForEntity(resourceUrl + "/hello", String.class);

		assertNotNull(response);
		assertTrue("Status code not equals to 200", response.getStatusCode().equals(HttpStatus.OK));
		assertTrue("Contains fail", response.getBody().contains("Hello"));
	}
}
