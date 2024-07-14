package uk.co.furiodenardis.springexercise.gateway;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.util.UriComponentsBuilder;
import uk.co.furiodenardis.springexercise.exceptions.DownstreamServiceException;
import uk.co.furiodenardis.springexercise.gateway.response.ApiCompanyDto;
import uk.co.furiodenardis.springexercise.gateway.response.ApiCompanySearchResponseDto;
import uk.co.furiodenardis.springexercise.gateway.response.ApiOfficerDto;
import uk.co.furiodenardis.springexercise.gateway.response.ApiOfficerSearchResponseDto;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
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

        try {
            ApiCompanySearchResponseDto response = restClient.get()
                    .uri(url)
                    .header(apiKeyHeader, apiKey)
                    .retrieve()
                    .body(ApiCompanySearchResponseDto.class);
            return Optional.ofNullable(response.getItems()).orElse(List.of());

        } catch (RestClientResponseException rse) {
            log.error("Call to TruAPIProxy - search companies failed with response status {} for search string: {}", rse.getStatusCode().value(), searchString);
            throw new DownstreamServiceException("TruProxyAPI: " + rse.getStatusCode());
        }
    }

    public List<ApiOfficerDto> getOfficers(final String apiKey, final String companyNumber) {
        final String url = UriComponentsBuilder.fromUriString(truProxyBaseUrl)
                .path(truProxyOfficersEndpoint)
                .queryParam(parameterCompanyNo, companyNumber)
                .build()
                .toUriString();

        List<ApiOfficerDto> officers = List.of();
        try {
            ApiOfficerSearchResponseDto response = restClient.get()
                    .uri(url)
                    .header(apiKeyHeader, apiKey)
                    .retrieve()
                    .body(ApiOfficerSearchResponseDto.class);
            officers = response.getItems();

        } catch (RestClientResponseException rse) {
            if (rse.getStatusCode().value() == HttpStatus.NOT_FOUND.value()) {
                log.warn("Endpoint {} for company number {} returned NOT FOUND", url, companyNumber);
            } else {
                log.error("Call to TruAPIProxy - get officers failed with response status {} for company {}", rse.getStatusCode().value(), companyNumber);
                throw new DownstreamServiceException("TruProxyAPI: " + rse.getStatusCode());
            }
        }
        return officers;

    }

}
