package uk.co.furiodenardis.springexercise.gateway.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@Getter
@Setter
public class ApiOfficerSearchResponseDto {
    private int page_number;
    private int total_results;
    private List<ApiOfficerDto> items;
}
