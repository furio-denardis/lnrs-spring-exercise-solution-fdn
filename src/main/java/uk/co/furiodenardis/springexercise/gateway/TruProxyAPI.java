package uk.co.furiodenardis.springexercise.gateway;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.util.UriComponentsBuilder;
import uk.co.furiodenardis.springexercise.gateway.response.ApiCompanyDto;
import uk.co.furiodenardis.springexercise.gateway.response.ApiCompanySearchResponseDto;
import uk.co.furiodenardis.springexercise.gateway.response.ApiOfficerDto;
import uk.co.furiodenardis.springexercise.gateway.response.ApiOfficerSearchResponseDto;


import java.util.List;
import java.util.Optional;

@Service
public class TruProxyAPI {

    @Value("${gateway.truproxyapi.baseurl}")
    private String truProxyBaseUrl;

    @Value("${gateway.truproxyapi.endpoint.search}")
    private String truProxySearchEndpoint;

    @Value("${gateway.truproxyapi.endpoint.officers}")
    private String truProxyOfficersEndpoint;

    private static final String parameterQuery = "Query";
    private static final String parameterCompanyNo = "CompanyNumber";
    public static final String apiKeyHeader = "x-api-key";

    private final RestClient restClient;

    public TruProxyAPI(@Autowired RestClient restClient) {
        this.restClient = restClient;
    }

    public List<ApiCompanyDto> searchCompanies(final String apiKey,
                                               final String searchString) {
        final String url = UriComponentsBuilder.fromUriString(truProxyBaseUrl)
                .path(truProxySearchEndpoint)
                .queryParam(parameterQuery, searchString)
                .build()
                .toUriString();

        ApiCompanySearchResponseDto response = restClient.get()
                .uri(url)
                .header(apiKeyHeader, apiKey)
                .retrieve()
                .body(ApiCompanySearchResponseDto.class);

        return Optional.ofNullable(response.getItems()).orElse(List.of());
    }

    public List<ApiOfficerDto> getOfficers(final String apiKey, final String companyNumber) {
        final String url = UriComponentsBuilder.fromUriString(truProxyBaseUrl)
                .path(truProxyOfficersEndpoint)
                .queryParam(parameterCompanyNo, companyNumber)
                .build()
                .toUriString();

        ApiOfficerSearchResponseDto response = restClient.get()
                .uri(url)
                .header(apiKeyHeader, apiKey)
                .retrieve()
                .body(ApiOfficerSearchResponseDto.class);

        return Optional.ofNullable(response.getItems()).orElse(List.of());
    }

}
