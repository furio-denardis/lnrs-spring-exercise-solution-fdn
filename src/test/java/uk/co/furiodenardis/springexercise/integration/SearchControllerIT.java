package uk.co.furiodenardis.springexercise.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import lombok.SneakyThrows;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import uk.co.furiodenardis.springexercise.controller.request.SearchCompanyRequest;
import uk.co.furiodenardis.springexercise.gateway.TruProxyAPI;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlMatching;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class SearchControllerIT {

    public static final String API_KEY = "api-key";
    public static final String ACME_REG_NO = "12345";
    public static final String ACME = "acme";
    private static final int WIREMOCK_PORT = 9100;
    private static WireMockServer wireMockServer;
    private static ObjectMapper objectMapper = new ObjectMapper();
    @Autowired
    private TestRestTemplate restTemplate;

    @BeforeAll
    static void init() {
        wireMockServer = new WireMockServer(new WireMockConfiguration().port(WIREMOCK_PORT));
        wireMockServer.start();
        WireMock.configureFor(WIREMOCK_PORT);
    }

    @AfterAll
    static void tearDown() {
        wireMockServer.stop();
    }

    @AfterEach
    void cleanUp() {
        wireMockServer.resetToDefaultMappings();
    }

    @Test
    @SneakyThrows
    public void searchCompanyByName() {

        String url = "/companies/search";

        stubFor(WireMock.get(urlMatching("/Search\\?Query=" + ACME))
                .withHeader(TruProxyAPI.apiKeyHeader, WireMock.matching(API_KEY))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBodyFile("responses/companies_success.json")));

        stubFor(WireMock.get(urlMatching("/Officers\\?CompanyNumber=" + ACME_REG_NO))
                .withHeader(TruProxyAPI.apiKeyHeader, WireMock.matching(API_KEY))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBodyFile("responses/officers_success.json")));

        SearchCompanyRequest requestBody = SearchCompanyRequest.builder()
                .companyName(ACME)
                .companyNumber(null)
                .build();

        RequestEntity requestEntity = RequestEntity.method(HttpMethod.POST, url)
                .header(TruProxyAPI.apiKeyHeader, API_KEY)
                .body(requestBody);

        ResponseEntity<String> response = restTemplate.exchange(requestEntity, String.class);
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());

        String expectedJson = loadJsonFromResources("responses/acme_company_response.json");

        JSONAssert.assertEquals(expectedJson, response.getBody(), false);

    }

    @Test
    @SneakyThrows
    public void searchCompanyByRegistration() {

        String url = "/companies/search";

        stubFor(WireMock.get(urlMatching("/Search\\?Query=" + ACME_REG_NO))
                .withHeader(TruProxyAPI.apiKeyHeader, WireMock.matching(API_KEY))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBodyFile("responses/companies_success.json")));

        stubFor(WireMock.get(urlMatching("/Officers\\?CompanyNumber=" + ACME_REG_NO))
                .withHeader(TruProxyAPI.apiKeyHeader, WireMock.matching(API_KEY))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBodyFile("responses/officers_success.json")));

        SearchCompanyRequest requestBody = SearchCompanyRequest.builder()
                .companyName(null)
                .companyNumber(ACME_REG_NO)
                .build();

        RequestEntity requestEntity = RequestEntity.method(HttpMethod.POST, url)
                .header(TruProxyAPI.apiKeyHeader, API_KEY)
                .body(requestBody);

        ResponseEntity<String> response = restTemplate.exchange(requestEntity, String.class);
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());

        String expectedJson = loadJsonFromResources("responses/acme_company_response.json");

        JSONAssert.assertEquals(expectedJson, response.getBody(), false);

    }

    @Test
    @SneakyThrows
    public void searchCompanyByNameNoOfficers() {

        String url = "/companies/search";

        stubFor(WireMock.get(urlMatching("/Search\\?Query=" + ACME))
                .withHeader(TruProxyAPI.apiKeyHeader, WireMock.matching(API_KEY))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBodyFile("responses/companies_success.json")));

        stubFor(WireMock.get(urlMatching("/Officers\\?CompanyNumber=" + ACME_REG_NO))
                .withHeader(TruProxyAPI.apiKeyHeader, WireMock.matching(API_KEY))
                .willReturn(aResponse()
                        .withStatus(HttpStatus.NOT_FOUND.value())
                        .withHeader("Content-Type", "application/json")
                        .withBody("not found")));

        SearchCompanyRequest requestBody = SearchCompanyRequest.builder()
                .companyName(ACME)
                .companyNumber(null)
                .build();

        RequestEntity requestEntity = RequestEntity.method(HttpMethod.POST, url)
                .header(TruProxyAPI.apiKeyHeader, API_KEY)
                .body(requestBody);

        ResponseEntity<String> response = restTemplate.exchange(requestEntity, String.class);
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());

        String expectedJson = loadJsonFromResources("responses/acme_no_officers.json");

        JSONAssert.assertEquals(expectedJson, response.getBody(), false);

    }


    private String loadJsonFromResources(String location) throws IOException {
        return new String(Files.readAllBytes(Paths.get("src/test/resources/" + location)));
    }
}
