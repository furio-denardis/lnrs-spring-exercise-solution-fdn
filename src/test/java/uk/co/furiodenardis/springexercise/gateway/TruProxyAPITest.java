package uk.co.furiodenardis.springexercise.gateway;

import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.client.RestClient;
import uk.co.furiodenardis.springexercise.gateway.response.ApiCompanyDto;
import uk.co.furiodenardis.springexercise.gateway.response.ApiOfficerDto;

import java.util.List;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlMatching;
import static com.github.tomakehurst.wiremock.client.WireMock.urlPathEqualTo;
import static com.github.tomakehurst.wiremock.client.WireMock.urlPathMatching;
import static org.junit.jupiter.api.Assertions.assertEquals;


@WireMockTest(httpPort = 9100)
class TruProxyAPITest {

    private TruProxyAPI truProxyAPI;

    private String truProxyBaseUrl = "http://localhost:9100/";

    private String searchEndpoint = "Search";

    private String officersEndpoint = "Officers";

    public static final String API_KEY = "api-key";
    public static final String ACME_REG_NO = "12345";
    public static final String ACME = "acme";

    @BeforeEach
    public void setup() {
        truProxyAPI = new TruProxyAPI(
                RestClient.create(),
                truProxyBaseUrl,
                searchEndpoint,
                officersEndpoint
        );
    }

    @Test
    public void testSearchCompanies_Success() throws Exception {

        stubFor(WireMock.get(urlMatching("/Search\\?Query=" + ACME))
                .withHeader(TruProxyAPI.apiKeyHeader, WireMock.matching(API_KEY))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBodyFile("responses/companies_success.json")));


        List<ApiCompanyDto> result = truProxyAPI.searchCompanies(API_KEY, ACME);
        assertEquals(1, result.size());
    }

    @Test
    public void testSearchOfficers_Success() throws Exception {

        stubFor(WireMock.get(urlMatching("/Officers\\?CompanyNumber=" + ACME_REG_NO))
                .withHeader(TruProxyAPI.apiKeyHeader, WireMock.matching(API_KEY))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBodyFile("responses/officers_success.json")));


        List<ApiOfficerDto> result = truProxyAPI.getOfficers(API_KEY, ACME_REG_NO);
        assertEquals(4, result.size());
    }


}
